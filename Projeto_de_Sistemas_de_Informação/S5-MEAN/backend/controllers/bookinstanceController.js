var BookInstance = require('../models/bookinstance')
var Book = require('../models/book')
var async = require('async')

const { body, validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

// Display count of all BookInstances.
exports.bookinstance_count = function (req, res, next) {

    BookInstance.countDocuments()
        .exec(function (err, count) {
            if (err) { return next(err); }
            res.json(count);
        })

};

// Display count of all available BookInstances.
exports.bookinstance_count_available = function (req, res, next) {

    BookInstance.count({ status: 'Available' })
        .exec(function (err, count) {
            if (err) { return next(err); }
            res.json(count);
        })

};

// Display list of all BookInstances.
exports.bookinstance_list = function (req, res, next) {

    BookInstance.find()
        .exec(function (err, list_bookinstances) {
            if (err) { return next(err); }
            res.json(list_bookinstances);
        })

};

exports.bookinstance_of_book_detail = function (req, res, next) {

    BookInstance.find({ 'book': req.params.id })
        .exec(function (err, list) {
            if (err) { return next(err); }
            res.json(list);
        })
}

// Display detail page for a specific BookInstance.
exports.bookinstance_detail = function (req, res, next) {

    BookInstance.findById(req.params.id)
        .exec(function (err, bookinstance) {
            if (err) { return next(err); }
            if (bookinstance == null) { // No results.
                var err = new Error('Book copy not found');
                err.status = 404;
                return next(err);
            }
            res.json(bookinstance);
        })

};

// Handle BookInstance create on POST.
exports.bookinstance_create_post = [

    // Validate fields.
    body('book', 'Book must be specified').isLength({ min: 1 }).trim(),
    body('imprint', 'Imprint must be specified').isLength({ min: 1 }).trim(),
    body('due_back', 'Invalid date').optional({ checkFalsy: true }).isISO8601(),

    // Sanitize fields.
    sanitizeBody('book').escape(),
    sanitizeBody('imprint').escape(),
    sanitizeBody('status').escape(),
    sanitizeBody('due_back').toDate(),

    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        // Create a BookInstance object with escaped and trimmed data.
        var bookinstance = new BookInstance(
            {
                book: req.body.book,
                imprint: req.body.imprint,
                status: req.body.status,
                due_back: req.body.due_back
            });

        if (!errors.isEmpty()) {
            res.json({ 'message': 'Validation errors' });
        }
        else {
            // Data from form is valid
            bookinstance.save(function (err) {
                if (err) { return next(err); }
                res.json({ 'message': 'success' });
            });
        }
    }
];

// Handle BookInstance delete on POST.
exports.bookinstance_delete_post = function (req, res, next) {

    // Assume valid BookInstance id in field.
    BookInstance.deleteOne({ _id: req.body._id }, function deleteBookInstance(err) {
        if (err) { return next(err); }
        res.json({ 'message': 'success' });
    });

};

// Handle BookInstance update on POST.
exports.bookinstance_update_post = [

    // Validate fields.
    body('book', 'Book must be specified').isLength({ min: 1 }).trim(),
    body('imprint', 'Imprint must be specified').isLength({ min: 1 }).trim(),
    body('due_back', 'Invalid date').optional({ checkFalsy: true }).isISO8601(),

    // Sanitize fields.
    sanitizeBody('book').escape(),
    sanitizeBody('imprint').escape(),
    sanitizeBody('status').escape(),
    sanitizeBody('due_back').toDate(),

    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        // Create a BookInstance object with escaped/trimmed data and current id.
        var bookinstance = new BookInstance(
            {
                book: req.body.book,
                imprint: req.body.imprint,
                status: req.body.status,
                due_back: req.body.due_back,
                _id: req.body._id
            });

        if (!errors.isEmpty()) {
            res.json({ 'message': 'Validation errors' });
        }
        else {
            // Data from form is valid.
            BookInstance.replaceOne({ _id: req.body._id }, bookinstance, function (err, thebookinstance) {
                if (err) { return next(err); }
                res.json({ 'message': 'success' });
            });
        }
    }
];
