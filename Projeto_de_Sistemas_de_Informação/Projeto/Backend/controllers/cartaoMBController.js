var CartaoMB = require('../models/cartaoMB');

const { body, validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

var async = require('async');

exports.cartaoMb_get_cartao_email = function(req, res, next) {
    CartaoMB.find({ 'userEmail': req.params.userEmail })
        .exec(function(err, listCartao) {
            if (err) { return next(err); } // Error in API usage.
            res.json(listCartao);
        });
};

exports.cartaoMb_get = function(req, res, next) {
    CartaoMB.findById(req.params.cartaoId)
        .exec(function(err, results) {
            if (err) { return next(err); }
            if (results == null) {
                var err = new Error('Cartao nao encontrado');
                err.status = 404;
                return next(err);
            }
            res.json(results);
        });
};

// Handle CartaoMB create.
exports.cartaoMb_create = [

    // Validate fields.
    body('numero', 'Numero must not be empty.').isLength({ min: 1 }).trim(),
    body('nome', 'Nome must not be empty.').isLength({ min: 1 }).trim(),
    body('prazoAno', 'Ano must not be empty.').isLength({ min: 1 }).trim(),
    body('prazoMes', 'Mes must not be empty.').isLength({ min: 1 }).trim(),
    body('cvv', 'cvv must not be empty.').isLength({ min: 1 }).trim(),
    body('userEmail', 'user must not be empty.').isLength({ min: 1 }).trim(),

    // Sanitize fields.
    sanitizeBody('*').escape(),
    // Process request after validation and sanitization.
    (req, res, next) => {

        // Extract the validation errors from a request.
        const errors = validationResult(req);

        // Create a CartaoMB object with escaped and trimmed data.
        var cartao = new CartaoMB({
            numero: req.body.numero,
            nome: req.body.nome,
            prazoAno: req.body.prazoAno,
            prazoMes: req.body.prazoMes,
            cvv: req.body.cvv,
            userEmail: req.body.userEmail,
        });
        if (!errors.isEmpty()) {
            res.json({ 'message': 'Validation errors' });
        } else {
            // Data from form is valid. Save CartaoMB.
            cartao.save(function(err) {
                if (err) { return next(err); }
                // Successful - redirect to new CartaoMB record.
                res.json({ 'message': 'success' });
            });
        }
    }
];

exports.cartaoMb_delete = function(req, res, next) {

    async.parallel({
        cartao: function(callback) {
            CartaoMB.findById(req.body._id).exec(callback);
        }
    }, function(err, results) {
        if (err) { return next(err); }
        // Genre has no books. Delete object and redirect to the list of genres.
        CartaoMB.deleteOne({ _id: req.body._id }, function deleteCartao(err) {
            if (err) { return next(err); }
            res.json({ 'message': 'success' })
        });
    });
};