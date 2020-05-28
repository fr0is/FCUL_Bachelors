var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var MoradaSchema = new Schema({
    rua: { type: String, required: true, min: 3, max: 100 },
    codigoPostal: { type: String, required: true, min: 3, max: 100 },
    cidade: { type: String, required: true, min: 3, max: 100 },
    pais: { type: String, required: true, min: 3, max: 100 },
    userEmail: { type: String, required: true }
});

MoradaSchema
    .virtual('url')
    .get(function() {
        return '/catalog/user/morada/' + this._id;
    });

module.exports = mongoose.model('morada', MoradaSchema);