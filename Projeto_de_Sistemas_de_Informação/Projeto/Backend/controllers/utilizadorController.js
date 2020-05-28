var Utilizador = require('../models/utilizador');

const { body, validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

var async = require('async');

exports.utilizador_get = function(req, res, next) {
    Utilizador.find({ 'email': req.params.email })
        .exec(function(err, user) {
            if (err) { return next(err); }
            res.json(user);
        })
};

// Handle Utilizador create.
exports.utilizador_create = [

    // Validate fields.
    body('nome', 'Nome must not be empty.').isLength({ min: 1 }).trim(),
    body('email', 'Email must not be empty.').isLength({ min: 1 }).trim(),
    body('password', 'Password must not be empty.').isLength({ min: 1 }).trim(),

    // Sanitize fields.
    sanitizeBody('*').escape(),
    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        // Create a Utilizador object with escaped and trimmed data.
        var utilizador = new Utilizador({
            nome: req.body.nome,
            email: req.body.email,
            password: req.body.password,
        });
        if (!errors.isEmpty()) {
            res.json({ 'message': 'Validation errors' });
        } else {
            // Data from form is valid. Save Utilizador.
            utilizador.save(function(err) {
                if (err) { return next(err); }
                // Successful - redirect to new Utilizador record.
                res.json({ 'message': 'success' });
            });
        }
    }
];

// Handle utilizador update.
exports.utilizador_update = [

    // Convert the cartaoMB to an array.
    (req, res, next) => {
        if (!(req.body.cartaoMB instanceof Array)) {
            if (typeof req.body.cartaoMB === 'undefined')
                req.body.cartaoMB = [];
            else
                req.body.cartaoMB = new Array(req.body.cartaoMB);
        }
        next();
    },

    // Convert the morada to an array.
    (req, res, next) => {
        if (!(req.body.morada instanceof Array)) {
            if (typeof req.body.morada === 'undefined')
                req.body.morada = [];
            else
                req.body.morada = new Array(req.body.morada);
        }
        next();
    },


    // Validate fields.
    body('nome', 'Nome must not be empty.').isLength({ min: 1 }).trim(),
    body('email', 'Email must not be empty.').isLength({ min: 1 }).trim(),
    body('indicativo', 'Nome must not be empty.').trim(),
    body('telefone', 'Email must not be empty.').trim(),
    body('nif', 'NIF must not be empty.').trim(),
    body('password', 'Password must not be empty.').isLength({ min: 1 }).trim(),

    // Sanitize fields.
    sanitizeBody('nome').escape(),
    sanitizeBody('email').escape(),
    sanitizeBody('indicativo').escape(),
    sanitizeBody('nif').escape(),
    sanitizeBody('telefone').escape(),
    sanitizeBody('password').escape(),

    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        // Create a utilizador object with escaped/trimmed data and old id.
        var utilizador = new Utilizador({
            nome: req.body.nome,
            email: req.body.email,
            password: req.body.password,
            indicativo: req.body.indicativo,
            telefone: req.body.telefone,
            nif: req.body.nif,
            morada: (typeof req.body.morada === 'undefined') ? [] : req.body.morada,
            cartaoMB: (typeof req.body.cartaoMB === 'undefined') ? [] : req.body.cartaoMB,
            _id: req.body._id // This is required, or a new ID will be assigned!
        });

        if (!errors.isEmpty()) {
            res.json({ 'message': 'Validation errors' });
        } else {
            // Data from form is valid. Update the record.
            Utilizador.replaceOne({ _id: req.body._id }, utilizador, function(err, theutilizador) {
                if (err) { return next(err); }
                res.json({ 'message': 'success' });
            });
        }
    }
];