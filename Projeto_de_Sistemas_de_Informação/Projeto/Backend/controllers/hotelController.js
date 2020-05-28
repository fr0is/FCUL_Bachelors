var async = require('async');
var Hotel = require('../models/hotel');

const { body, validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

exports.hotel_list = function(req, res, next) {

    Hotel.find()
        .exec(function(err, list_hoteis) {
            if (err) { return next(err); }
            res.json(list_hoteis);
        })

};

exports.hotel_detail = function(req, res, next) {
    Hotel.findById(req.params.hotelId)
        .exec(function(err, results) {
            if (err) { return next(err); }
            if (results == null) {
                var err = new Error('Hotel nao encontrado');
                err.status = 404;
                return next(err);
            }
            res.json(results);
        });
};