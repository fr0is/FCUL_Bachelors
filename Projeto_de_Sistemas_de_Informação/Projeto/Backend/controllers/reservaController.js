var Reserva = require('../models/reserva');
var QuartoInstance = require('../models/quartoInstance');
var moment = require('moment');

const { body, validationResult } = require('express-validator/check');
const { sanitizeBody } = require('express-validator/filter');

var async = require('async');

exports.reserva_get_email = function(req, res, next) {
    Reserva.find({ 'userEmail': req.params.userEmail })
        .populate('quarto')
        .populate('metodoDePagamento')
        .populate('morada')
        .exec(function(err, listReservas) {
            if (err) { return next(err); } // Error in API usage.
            res.json(listReservas);
        });
};

exports.reserva_get_quarto = function(req, res, next) {
    Reserva.find({ 'quarto': req.params.quartoId })
        .populate('quarto')
        .populate('metodoDePagamento')
        .populate('morada')
        .exec(function(err, listReservas) {
            if (err) { return next(err); } // Error in API usage.
            res.json(listReservas);
        });
};

exports.reserva_get_id = function(req, res, next) {
    Reserva.findById(req.params.reservaId)
        .populate('quarto')
        .populate('metodoDePagamento')
        .populate('morada')
        .exec(function(err, results) {
            if (err) { return next(err); }
            if (results == null) {
                var err = new Error('Reserva nao encontrada');
                err.status = 404;
                return next(err);
            }
            res.json(results);
        });
};

// Handle Utilizador create.
exports.reserva_create = [
    body('userEmail', 'userEmail must not be empty.').isLength({ min: 1 }).trim(),
    body('emailReserva', 'emailReserva must not be empty.').isLength({ min: 1 }).trim(),
    body('nomeReserva', 'nomeReserva must not be empty.').isLength({ min: 1 }).trim(),
    body('indicativoReserva', 'indicativoReserva must not be empty.').isLength({ min: 1 }).trim(),
    body('telefoneReserva', 'telefoneReserva must not be empty.').isLength({ min: 1 }).trim(),
    body('nifReserva', 'nifReserva must not be empty.').isLength({ min: 1 }).trim(),
    body('quarto', 'quarto must not be empty.').isLength({ min: 1 }).trim(),
    body('metodoDePagamento', 'metodoDePagamento must not be empty.').isLength({ min: 1 }).trim(),
    body('morada', 'morada must not be empty.').isLength({ min: 1 }).trim(),
    body('checkIn', 'checkIn must not be empty.').optional({ checkFalsy: true }).isISO8601(),
    body('checkOut', 'checkOut must not be empty.').optional({ checkFalsy: true }).isISO8601(),
    body('preco', 'preco must not be empty.').isLength({ min: 1 }).trim(),

    sanitizeBody('userEmail').escape(),
    sanitizeBody('emailReserva').escape(),
    sanitizeBody('nomeReserva').escape(),
    sanitizeBody('indicativoReserva').escape(),
    sanitizeBody('telefoneReserva').escape(),
    sanitizeBody('nifReserva').escape(),
    sanitizeBody('quarto').escape(),
    sanitizeBody('metodoDePagamento').escape(),
    sanitizeBody('morada').escape(),
    sanitizeBody('preco').escape(),
    sanitizeBody('checkIn').toDate(),
    sanitizeBody('checkOut').toDate(),


    (req, res, next) => {

        console.log(req.body);

        const errors = validationResult(req);

        if (!errors.isEmpty()) {
            res.json({ 'message': 'Validation errors' });
            console.log("validationErrors");
        } else {
            async.parallel({
                instances: function(callback) {
                    QuartoInstance.find({ 'quarto': req.body.quarto }).populate('reservas').exec(callback);
                }
            }, function(err, results) {
                if (err) { return next(err); }
                var quartoinstance;
                for (let inst of results.instances) {
                    var listReservas = inst.reservas;
                    if (quartoinstance == null) {
                        if (listReservas.length > 0) {
                            for (var i = -1; i < listReservas.length; i++) {
                                if (i === -1) {
                                    if (moment(listReservas[i + 1].checkIn).isAfter(moment(req.body.checkOut))) {
                                        quartoinstance = inst;
                                        break;
                                    }
                                } else if (i + 1 >= listReservas.length) {
                                    if (moment(listReservas[i].checkOut).isBefore(moment(req.body.checkIn))) {
                                        quartoinstance = inst;
                                        break;
                                    }
                                } else {
                                    if (moment(listReservas[i].checkOut).isBefore(moment(req.body.checkIn)) && moment(listReservas[i + 1].checkIn).isAfter(moment(req.body.checkOut))) {
                                        quartoinstance = inst;
                                        break;
                                    }
                                }
                            }
                        } else {
                            quartoinstance = inst;
                        }
                        if (quartoinstance) { break; }
                    }
                }
                if (quartoinstance == null) {
                    res.json({ 'message': 'Nao ha quartos disponiveis' });
                    console.log("quartoSearchErrror");
                } else {
                    var reserva = new Reserva({
                        userEmail: req.body.userEmail,
                        emailReserva: req.body.emailReserva,
                        nomeReserva: req.body.nomeReserva,
                        indicativoReserva: req.body.indicativoReserva,
                        telefoneReserva: req.body.telefoneReserva,
                        nifReserva: req.body.nifReserva,
                        quarto: quartoinstance,
                        metodoDePagamento: req.body.metodoDePagamento,
                        morada: req.body.morada,
                        checkIn: req.body.checkIn,
                        checkOut: req.body.checkOut,
                        preco: req.body.preco
                    });
                    quartoinstance.reservas.push(reserva);
                    QuartoInstance.replaceOne({ _id: quartoinstance._id }, quartoinstance, function(err, theInstance) {
                        if (err) { return next(err); }
                    });

                    reserva.save(function(err) {
                        if (err) { return next(err); }
                        res.json({ 'message': 'Quarto Reservado' });
                        console.log("Reservou");
                    });
                }
            });
        }
    }
];

exports.reserva_delete = function(req, res, next) {
    async.parallel({
        reserva: function(callback) {
            Reserva.findById(req.body._id).exec(callback);
        },
    }, function(err, results) {
        if (err) { return next(err); }
        var instance = results.reserva.quarto;
        var reserva = results.reserva;

        const ind = quartoinstance.reservas.indexOf(reserva)
        if (ind > -1) {
            quartoinstance.reservas.splice(ind, 1);
        }
        QuartoInstance.replaceOne({ _id: instance._id }, instance, function(err, theInstance) {
            if (err) { return next(err); }
        });

        Reserva.deleteOne({ _id: reserva._id }, function deleteReserva(err) {
            if (err) { return next(err); }
            res.json({ 'message': 'Reserva apagada' })
        });
    });
};

exports.reserva_update = [
    body('userEmail', 'userEmail must not be empty.').isLength({ min: 1 }).trim(),
    body('emailReserva', 'emailReserva must not be empty.').isLength({ min: 1 }).trim(),
    body('nomeReserva', 'nomeReserva must not be empty.').isLength({ min: 1 }).trim(),
    body('indicativoReserva', 'indicativoReserva must not be empty.').isLength({ min: 1 }).trim(),
    body('telefoneReserva', 'telefoneReserva must not be empty.').isLength({ min: 1 }).trim(),
    body('nifReserva', 'nifReserva must not be empty.').isLength({ min: 1 }).trim(),
    body('quarto', 'quarto must not be empty.').isLength({ min: 1 }).trim(),
    body('metodoDePagamento', 'metodoDePagamento must not be empty.').isLength({ min: 1 }).trim(),
    body('morada', 'morada must not be empty.').isLength({ min: 1 }).trim(),
    body('checkIn', 'checkIn must not be empty.').optional({ checkFalsy: true }).isISO8601(),
    body('checkOut', 'checkOut must not be empty.').optional({ checkFalsy: true }).isISO8601(),
    body('preco', 'preco must not be empty.').isLength({ min: 1 }).trim(),

    sanitizeBody('userEmail').escape(),
    sanitizeBody('emailReserva').escape(),
    sanitizeBody('nomeReserva').escape(),
    sanitizeBody('indicativoReserva').escape(),
    sanitizeBody('telefoneReserva').escape(),
    sanitizeBody('nifReserva').escape(),
    sanitizeBody('quarto').escape(),
    sanitizeBody('metodoDePagamento').escape(),
    sanitizeBody('morada').escape(),
    sanitizeBody('preco').escape(),
    sanitizeBody('checkIn').toDate(),
    sanitizeBody('checkOut').toDate(),
    (req, res, next) => {
        const errors = validationResult(req);
        if (!errors.isEmpty()) {
            res.json({ 'message': 'Validation errors' });
        } else {
            async.parallel({
                atual: function(callback) {
                    QuartoInstance.findById(req.body.quarto).exec(callback);
                }
            }, function(err, results) {
                if (err) { return next(err); }
                async.parallel({
                    instances: function(callback) {
                        QuartoInstance.find({ 'quarto': results.atual.quarto }).populate('reservas').exec(callback);
                    }
                }, function(err, results) {
                    if (err) { return next(err); }
                    var quartoinstance;
                    for (let inst of results.instances) {
                        var listReservas = inst.reservas;
                        if (quartoinstance == null) {
                            if (listReservas.length > 0) {
                                for (var i = -1; i < listReservas.length; i++) {
                                    if (i === -1) {
                                        if (moment(listReservas[i + 1].checkIn).isAfter(moment(req.body.checkOut))) {
                                            quartoinstance = inst;
                                            break;
                                        }
                                    } else if (i + 1 >= listReservas.length) {
                                        if (moment(listReservas[i].checkOut).isBefore(moment(req.body.checkIn))) {
                                            quartoinstance = inst;
                                            break;
                                        }
                                    } else {
                                        if (moment(listReservas[i].checkOut).isBefore(moment(req.body.checkIn)) && moment(listReservas[i + 1].checkIn).isAfter(moment(req.body.checkOut))) {
                                            quartoinstance = inst;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                quartoinstance = inst;
                            }
                            if (quartoinstance) { break; }
                        }
                    }
                    if (quartoinstance == null) {
                        res.json({ 'message': 'Nao foi possivel atualizar a reserva' });
                    } else {

                        var reserva = new Reserva({
                            _id: req.body._id,
                            userEmail: req.body.userEmail,
                            emailReserva: req.body.emailReserva,
                            nomeReserva: req.body.nomeReserva,
                            indicativoReserva: req.body.indicativoReserva,
                            telefoneReserva: req.body.telefoneReserva,
                            nifReserva: req.body.nifReserva,
                            quarto: quartoinstance,
                            metodoDePagamento: req.body.metodoDePagamento,
                            morada: req.body.morada,
                            checkIn: req.body.checkIn,
                            checkOut: req.body.checkOut,
                            preco: req.body.preco
                        });
                        Reserva.replaceOne({ _id: req.body._id }, reserva, function(err, theReserva) {
                            if (err) { return next(err); }
                            res.json({ 'message': 'success' });
                        });
                    }
                });
            });
        }
    }
];