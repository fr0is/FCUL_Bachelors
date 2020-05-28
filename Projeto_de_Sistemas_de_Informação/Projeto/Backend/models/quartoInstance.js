var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var QuartoInstanceSchema = new Schema({
    numeroQuarto: { type: Number, required: true },
    quarto: { type: Schema.ObjectId, ref: 'quarto', required: true },
    reservas: [{ type: Schema.ObjectId, ref: 'reserva'}]
});

QuartoInstanceSchema
    .virtual('url')
    .get(function() {
        return '/catalog/quartoInstances/' + this._id;
    });

module.exports = mongoose.model('quartoInstance', QuartoInstanceSchema);