var Reader = require('../models/reader');
exports.reader_books_get = function(req, res, next) {
    Reader.findById(req.params.id)
        .populate('reader')
        .populate('books')
        .exec(function(err, reader) {
            if (err) { return next(err); }
            if (reader == null) { // No results.
                var err = new Error('Reader not found');
                err.status = 404;
                return next(err);
            }
            // Successful, so render.
            res.send(reader.books);
        })
};

exports.reader_books_post = function(req, res, next) {
    Reader.findById(req.params.id)
        .populate('reader')
        .populate('books')
        .exec(function(err, reader) {
            if (err) { return next(err); }
            if (reader == null) { // No results.
                var err = new Error('Reader not found');
                err.status = 404;
                return next(err);
            }
            // Successful, so render.
            res.send(reader.books);
        })
};


exports.book_readers_get = function(req, res, next) {
    Reader.find({ 'books': req.params.id })
        .populate(book_readers)
        .exec(function(err, book_readers) {
            if (err) { return next(err); }
            if (book_readers == null) { // No results.
                var err = new Error('Book not found');
                err.status = 404;
                return next(err);
            }
            // Successful, so render
            res.send(book_readers);
        })

};

exports.book_readers_post = function(req, res, next) {
    Reader.find({ 'books': req.params.id })
        .populate(book_readers)
        .exec(function(err, book_readers) {
            if (err) { return next(err); }
            if (book_readers == null) { // No results.
                var err = new Error('Book not found');
                err.status = 404;
                return next(err);
            }
            // Successful, so render
            res.send(book_readers);
        })

};