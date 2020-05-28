var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var CartaoMBSchema = new Schema({
    numero: { type: String, required: true, min: 16, max: 16 },
    nome: { type: String, required: true },
    prazoAno: { type: String, required: true },
    prazoMes: { type: String, required: true },
    cvv: { type: String, required: true, min: 3, max: 3 },
    userEmail: { type: String, required: true }
});

CartaoMBSchema
    .virtual('url')
    .get(function() {
        return '/catalog/user/cartaoMB/' + this._id;
    });

module.exports = mongoose.model('cartaoMB', CartaoMBSchema);