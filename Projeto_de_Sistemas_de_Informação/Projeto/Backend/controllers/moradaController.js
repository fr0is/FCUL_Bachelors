var Morada = require('../models/morada');

const { body, validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

var async = require('async');

exports.morada_get_email = function(req, res, next) {
    Morada.find({ 'userEmail': req.params.userEmail })
        .exec(function(err, listMorada) {
            if (err) { return next(err); } // Error in API usage.
            res.json(listMorada);
        });
};

exports.morada_get = function(req, res, next) {
    Morada.findById(req.params.moradaId)
        .exec(function(err, results) {
            if (err) { return next(err); }
            if (results == null) {
                var err = new Error('Morada nao encontrada');
                err.status = 404;
                return next(err);
            }
            res.json(results);
        });
};

// Handle Morada create.
exports.morada_create = [

    // Validate fields.
    body('rua', 'rua must not be empty.').isLength({ min: 1 }).trim(),
    body('codigoPostal', 'codigoPostal must not be empty.').isLength({ min: 1 }).trim(),
    body('cidade', 'cidade must not be empty.').isLength({ min: 1 }).trim(),
    body('pais', 'pais must not be empty.').isLength({ min: 1 }).trim(),
    body('userEmail', 'userEmail must not be empty.').isLength({ min: 1 }).trim(),

    // Sanitize fields.
    sanitizeBody('*').escape(),
    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        // Create a Morada object with escaped and trimmed data.
        var morada = new Morada({
            rua: req.body.rua,
            codigoPostal: req.body.codigoPostal,
            cidade: req.body.cidade,
            pais: req.body.pais,
            userEmail: req.body.userEmail
        });
        if (!errors.isEmpty()) {
            res.json({ 'message': 'Validation errors' });
        } else {
            // Data from form is valid. Save Morada.
            morada.save(function(err) {
                if (err) { return next(err); }
                // Successful - redirect to new Morada record.
                res.json({ 'message': 'success' });
            });
        }
    }
];

exports.morada_delete = function(req, res, next) {

    async.parallel({
        morada: function(callback) {
            Morada.findById(req.body._id).exec(callback);
        }
    }, function(err, morada) {
        if (err) { return next(err); }
        Morada.deleteOne({ _id: req.body._id }, function deleteMorada(err) {
            if (err) { return next(err); }
            res.json({ 'message': 'success' })
        });
    });
};