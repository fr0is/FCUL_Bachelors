var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var QuartoSchema = new Schema({
    tipo: { type: String, required: true, min: 3, max: 100 },
    indice: { type: Number, min: 0, required: true},
    nrQuartos: { type: Number, min: 0, required: true },
    precoAlta: { type: Number, min: 0, required: true },
    precoBaixa: { type: Number, min: 0, required: true },
    hotel: { type: Schema.ObjectId, ref: 'hotel', required: true },
    servicos: [{ type: String, required: true }],
    foto: { type: String, required: true }
});

QuartoSchema
    .virtual('url')
    .get(function() {
        return '/catalog/quarto/' + this._id;
    });

module.exports = mongoose.model('quarto', QuartoSchema);