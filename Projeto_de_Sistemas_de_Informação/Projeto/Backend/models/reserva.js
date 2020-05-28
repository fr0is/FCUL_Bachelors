var mongoose = require('mongoose');
var moment = require('moment');

var Schema = mongoose.Schema;

var ReservaSchema = new Schema({
    userEmail: { type: String, required: true },
    emailReserva: { type: String, required: true },
    nomeReserva: { type: String, required: true },
    indicativoReserva: { type: String, required: true },
    telefoneReserva: { type: String, required: true },
    nifReserva: { type: String, required: true },
    quarto: { type: Schema.ObjectId, ref: 'quartoInstance', required: true },
    metodoDePagamento: { type: Schema.ObjectId, ref: 'cartaoMB', required: true },
    morada: { type: Schema.ObjectId, ref: 'morada', required: true },
    checkIn: { type: Date, required: true },
    checkOut: { type: Date, required: true },
    preco: { type: Number, required: true }
});

ReservaSchema
    .virtual('url')
    .get(function() {
        return '/catalog/reserva/' + this._id;
    });

module.exports = mongoose.model('reserva', ReservaSchema);