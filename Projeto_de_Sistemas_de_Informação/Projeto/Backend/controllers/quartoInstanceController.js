var QuartoInstance = require('../models/quartoInstance');

const { body, validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

var async = require('async');

exports.quartoInstance_get = function(req, res, next) {
    QuartoInstance.find({ 'quarto': req.params.quartoId })
        .populate('quarto')
        .populate('reservas')
        .exec(function(err, listInstances) {
            if (err) { return next(err); } // Error in API usage.
            res.json(listInstances);
        });
};