var Book = require('../models/book');
var Author = require('../models/author');
var Genre = require('../models/genre');
var BookInstance = require('../models/bookinstance');

const { body, validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

var async = require('async');

exports.index = function (req, res) {

    async.parallel({
        book_count: function (callback) {
            Book.count(callback);
        },
        book_instance_count: function (callback) {
            BookInstance.count(callback);
        },
        book_instance_available_count: function (callback) {
            BookInstance.count({ status: 'Available' }, callback);
        },
        author_count: function (callback) {
            Author.count(callback);
        },
        genre_count: function (callback) {
            Genre.count(callback);
        },
    }, function (err, results) {
        res.render('index', { title: 'Local Library Home', error: err, data: results });
    });
};

// Display count of all Books.
exports.book_count = function (req, res, next) {

    Book.countDocuments()
        .exec(function (err, count) {
            if (err) { return next(err); }
            res.json(count);
        })

};


// Display list of all books.
exports.book_list = function (req, res, next) {

    Book.find({})
        .sort([['title', 'ascending']])
        .exec(function (err, list_books) {
            if (err) { return next(err) }
            res.json(list_books);
        });

};

// Display detail page for a specific book.
exports.book_detail = function (req, res, next) {

    Book.findById(req.params.id)
        .exec(function (err, results) {
            if (err) { return next(err); } // Error in API usage.
            if (results == null) { // No results.
                var err = new Error('Book not found');
                err.status = 404;
                return next(err);
            }
            // Successful, so render.
            res.json(results);
        });

};

// Handle book create on POST.
exports.book_create_post = [
    // Convert the genre to an array.
    (req, res, next) => {
        if (!(req.body.genre instanceof Array)) {
            if (typeof req.body.genre === 'undefined')
                req.body.genre = [];
            else
                req.body.genre = new Array(req.body.genre);
        }
        next();
    },

    // Validate fields.
    body('title', 'Title must not be empty.').isLength({ min: 1 }).trim(),
    body('author', 'Author must not be empty.').isLength({ min: 1 }).trim(),
    body('summary', 'Summary must not be empty.').isLength({ min: 1 }).trim(),
    body('isbn', 'ISBN must not be empty').isLength({ min: 1 }).trim(),

    // Sanitize fields.
    sanitizeBody('*').escape(),
    sanitizeBody('genre.*').escape(),
    // Process request after validation and sanitization.
    (req, res, next) => {


        // Extract the validation errors from a request.
        const errors = validationResult(req);

        // Create a Book object with escaped and trimmed data.
        var book = new Book(
            {
                title: req.body.title,
                author: req.body.author,
                summary: req.body.summary,
                isbn: req.body.isbn,
                genre: req.body.genre
            });
        console.log(book);
        if (!errors.isEmpty()) {
            res.json({ 'message': 'Validation errors' });
        }
        else {
            // Data from form is valid. Save book.
            book.save(function (err) {
                if (err) { return next(err); }
                // Successful - redirect to new book record.
                res.json({ 'message': 'success' });
            });
        }
    }
];


// Handle book delete on POST.
exports.book_delete_post = function (req, res, next) {

    // Assume the post has valid id (ie no validation/sanitization).

    async.parallel({
        book: function (callback) {
            Book.findById(req.body._id).populate('author').populate('genre').exec(callback);
        },
        book_bookinstances: function (callback) {
            BookInstance.find({ 'book': req.body._id }).exec(callback);
        },
    }, function (err, results) {
        if (err) { return next(err); }
        // Success
        if (results.book_bookinstances.length > 0) {
            // Book has book_instances. Render in same way as for GET route.
            res.json({ 'message': 'Book has copies. Cannot delete.' })
        }
        else {
            // Book has no BookInstance objects. Delete object and redirect to the list of books.
            Book.deleteOne({ _id: req.body._id }, function deleteBook(err) {
                if (err) { return next(err); }
                res.json({ 'message': 'success' });
            });

        }
    });

};

// Handle book update on POST.
exports.book_update_post = [

    // Convert the genre to an array.
    (req, res, next) => {
        if (!(req.body.genre instanceof Array)) {
            if (typeof req.body.genre === 'undefined')
                req.body.genre = [];
            else
                req.body.genre = new Array(req.body.genre);
        }
        next();
    },

    // Validate fields.
    body('title', 'Title must not be empty.').isLength({ min: 1 }).trim(),
    body('author', 'Author must not be empty.').isLength({ min: 1 }).trim(),
    body('summary', 'Summary must not be empty.').isLength({ min: 1 }).trim(),
    body('isbn', 'ISBN must not be empty').isLength({ min: 1 }).trim(),

    // Sanitize fields.
    sanitizeBody('title').escape(),
    sanitizeBody('author').escape(),
    sanitizeBody('summary').escape(),
    sanitizeBody('isbn').escape(),
    sanitizeBody('genre.*').escape(),

    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        // Create a Book object with escaped/trimmed data and old id.
        var book = new Book(
            {
                title: req.body.title,
                author: req.body.author,
                summary: req.body.summary,
                isbn: req.body.isbn,
                genre: (typeof req.body.genre === 'undefined') ? [] : req.body.genre,
                _id: req.body._id // This is required, or a new ID will be assigned!
            });

        if (!errors.isEmpty()) {
            res.json({ 'message': 'Validation errors' });
        }
        else {
            // Data from form is valid. Update the record.
            Book.replaceOne({ _id: req.body._id }, book, function (err, thebook) {
                if (err) { return next(err); }
                res.json({ 'message': 'success' });
            });
        }
    }
];

