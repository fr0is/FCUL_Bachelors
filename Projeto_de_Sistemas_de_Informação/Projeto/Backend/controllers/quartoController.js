var async = require('async');
var Quarto = require('../models/quarto');

const { body, validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

// List All Rooms
exports.room_list = function(req, res, next) {

    Quarto.find({ 'hotel': req.params.hotelId })
        .sort([['indice', 'ascending']])
        .exec(function(err, list_quartos) {
            if (err) { return next(err); }
            res.json(list_quartos);
        })

};

exports.quarto_detail = function(req, res, next) {
    Quarto.findById(req.params.quartoId)
        .exec(function(err, results) {
            if (err) { return next(err); }
            if (results == null) {
                var err = new Error('Quarto nao encontrado');
                err.status = 404;
                return next(err);
            }
            res.json(results);
        });
};