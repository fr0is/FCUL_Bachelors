var Reader = require('../models/reader');
var Book = require('../models/book');
var BookInstance = require('../models/bookinstance');
var async = require('async');
exports.reader_list_get = function(req, res, next) {

    Reader.find()
        .populate('reader')
        .sort([
            ['name', 'ascending']
        ])
        .exec(function(err, list_reader) {
            if (err) { return next(err); }
            //Successful, so render
            res.render('reader_list', { title: 'Readers List', reader_list: list_reader });
        });
};


exports.reader_books_get = function(req, res, next) {
    Reader.findById(req.params.id)
        .populate('reader')
        .populate('books')
        .populate('author')
        .exec(function(err, reader) {
            if (err) { return next(err); }
            if (reader == null) { // No results.
                var err = new Error('Reader not found');
                err.status = 404;
                return next(err);
            }
            // Successful, so render.
            res.render('reader_detail', { title: "Reader's books", reader: reader });
        })
};

exports.reader_books_post = function(req, res, next) {
    Reader.findById(req.params.id)
        .populate('reader')
        .populate('books')
        .populate('author')
        .exec(function(err, reader) {
            if (err) { return next(err); }
            if (reader == null) { // No results.
                var err = new Error('Reader not found');
                err.status = 404;
                return next(err);
            }
            // Successful, so render.
            res.render('reader_detail', { title: "Reader's books", reader: reader });
        })
};


exports.book_readers_get = function(req, res, next) {

    async.parallel({
        book: function(callback) {
            Book.findById(req.params.id)
                .exec(callback);
        },

        book_readers: function(callback) {
            Reader.find({ 'books': req.params.id })
                .exec(callback);
        },

    }, function(err, results) {
        if (err) { return next(err); }
        if (results.book == null) { // No results.
            var err = new Error('Book not found');
            err.status = 404;
            return next(err);
        }
        // Successful, so render
        res.render('reader_books', { title: "Book's Readers", book: results.book, books_readers: results.book_readers });
    });

};

exports.book_readers_post = function(req, res, next) {

    async.parallel({
        book: function(callback) {
            Book.findById(req.params.id)
                .exec(callback);
        },

        book_readers: function(callback) {
            Reader.find({ 'books': req.params.id })
                .exec(callback);
        },

    }, function(err, results) {
        if (err) { return next(err); }
        if (results.book == null) { // No results.
            var err = new Error('Book not found');
            err.status = 404;
            return next(err);
        }
        // Successful, so render
        res.render('reader_books', { title: "Book's Readers", book: results.book, books_readers: results.book_readers });
    });

};