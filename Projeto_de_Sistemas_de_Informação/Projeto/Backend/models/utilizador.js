var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var UtilizadorSchema = new Schema({
    nome: { type: String, required: true, min: 3, max: 100 },
    email: { type: String, required: true, min: 3, max: 100 },
    password: { type: String, required: true, min: 3, max: 100 },
    indicativo: { type: String },
    telefone: { type: String, min: 9, max: 9 },
    nif: { type: String, min: 9, max: 9 },
    morada: [{ type: Schema.ObjectId, ref: 'morada' }],
    cartaoMB: [{ type: Schema.ObjectId, ref: 'cartaoMB' }],
    reservas: [{ type: Schema.ObjectId, ref: 'reserva' }]
});

UtilizadorSchema
    .virtual('url')
    .get(function() {
        return '/catalog/utilizador/' + this._id;
    });

module.exports = mongoose.model('utilizador', UtilizadorSchema);