var Genre = require('../models/genre');
var Book = require('../models/book');
var async = require('async');

const { body, validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

// Display count of all Genres.
exports.genre_count = function (req, res, next) {

    Genre.countDocuments()
        .exec(function (err, count) {
            if (err) { return next(err); }
            res.json(count);
        })

};

// Display list of all Genre.
exports.genre_list = function (req, res, next) {

    Genre.find()
        .sort([['name', 'ascending']])
        .exec(function (err, list_genres) {
            if (err) { return next(err); }
            res.json(list_genres);
        });

};

// Display detail page for a specific Genre.
exports.genre_detail = function (req, res, next) {
    Genre.findById(req.params.id)
        .exec(function (err, results) {
            if (err) { return next(err); } // Error in API usage.
            if (results == null) { // No results.
                var err = new Error('Genre not found');
                err.status = 404;
                return next(err);
            }
            // Successful, so render.
            res.json(results);
        });

};

exports.genre_detail_books = function (req, res, next) {
    Book.find({ 'genre': req.params.id })
        .exec(function (err, results) {
            if (err) { return next(err); } // Error in API usage.
            res.json(results);
        });

};

// Handle Genre create on POST.
exports.genre_create_post = [

    // Validate that the name field is not empty.
    body('name', 'Genre name required').isLength({ min: 1 }).trim(),

    // Sanitize (trim) the name field.
    sanitizeBody('name').escape(),

    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        // Create a genre object with escaped and trimmed data.
        var genre = new Genre(
            { name: req.body.name }
        );


        if (!errors.isEmpty()) {
            res.json({ 'message': 'Validation errors' });
        }
        else {
            // Data from form is valid.
            // Check if Genre with same name already exists.
            Genre.findOne({ 'name': req.body.name })
                .exec(function (err, found_genre) {
                    if (err) { return next(err); }

                    if (found_genre) {
                        // Genre exists, redirect to its detail page.
                        res.json({ 'message': 'Genre already exists' });
                    }
                    else {

                        genre.save(function (err) {
                            if (err) { return next(err); }
                            res.json({ 'message': 'success' });
                        });

                    }

                });
        }
    }
];

// Handle Genre delete on POST.
exports.genre_delete_post = function (req, res, next) {

    async.parallel({
        genre: function (callback) {
            Genre.findById(req.body._id).exec(callback);
        },
        genre_books: function (callback) {
            Book.find({ 'genre': req.body._id }).exec(callback);
        },
    }, function (err, results) {
        if (err) { return next(err); }
        // Success
        if (results.genre_books.length > 0) {
            // Genre has books. Render in same way as for GET route.
            res.json({ 'message': 'Genre has books. Cannot delete.' })
        }
        else {
            // Genre has no books. Delete object and redirect to the list of genres.
            Genre.deleteOne({ _id: req.body._id }, function deleteGenre(err) {
                if (err) { return next(err); }
                res.json({ 'message': 'success' })
            });

        }
    });

};

// Handle Genre update on POST.
exports.genre_update_post = [

    // Validate that the name field is not empty.
    body('name', 'Genre name required').isLength({ min: 1 }).trim(),

    // Sanitize (escape) the name field.
    sanitizeBody('name').escape(),

    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request .
        const errors = validationResult(req);

        // Create a genre object with escaped and trimmed data (and the old id!)
        var genre = new Genre(
            {
                name: req.body.name,
                _id: req.body._id
            }
        );


        if (!errors.isEmpty()) {
            res.json({'message': 'Validation errors'});        }
        else {
            // Data from form is valid. Update the record.
            Genre.replaceOne({_id: req.body._id}, genre, function (err, thegenre) {
                if (err) { return next(err); }
                res.json({ 'message': 'success' });
            });
        }
    }
];
