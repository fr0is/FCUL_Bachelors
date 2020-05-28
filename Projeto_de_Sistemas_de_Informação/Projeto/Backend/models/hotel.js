var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var HotelSchema = new Schema({
    nome: { type: String, required: true, min: 3, max: 100 },
    descricao: [{ type: String, required: true, min: 3 }],

    // morada
    local: { type: String, required: true, min: 3, max: 100 },
    zona: { type: String, required: true, min: 3, max: 100 },
    codigoPostal: { type: String, required: true, min: 3, max: 100 },
    pais: { type: String, required: true, min: 3, max: 100 },

    // coordenadas
    latitude: { type: String, required: true, min: 3, max: 100 },
    longitude: { type: String, required: true, min: 3, max: 100 },

    // numero de telefone
    codigoRegiao: { type: Number, required: true },
    telefone: { type: Number, required: true, min: 100000000, max: 999999999 },

    email: { type: String, required: true, min: 3, max: 100 },
    servicos: [{ type: String, required: true, min: 3, max: 150 }, ],

    fotos: [{ type: String, required: true}, ],
    fotoPath: { type: String, required: true}
});

HotelSchema
    .virtual('url')
    .get(function() {
        return '/catalog/hotel/' + this._id;
    });

HotelSchema.virtual('morada').get(function() {
    var morada = '';
    if (this.local) {
        morada += this.local;
    }
    morada += '<br/>';
    if (this.zona) {
        morada += this.zona;
    }
    morada += '<br/>';
    if (this.codigoPostal) {
        morada += this.codigoPostal;
    }
    morada += '<br/>';
    if (this.pais) {
        morada += this.pais;
    }
    return morada;
});

HotelSchema.virtual('numero').get(function() {
    var numero = '(+';
    if (this.codigoRegiao) {
        numero += this.codigoRegiao;
    }
    numero += ') ';
    if (this.telefone) {
        numero += this.telefone;
    }
    return numero;
});

HotelSchema.virtual('coordenadas').get(function() {
    var coordenadas = '';
    if (this.latitude) {
        coordenadas += this.latitude;
    }
    coordenadas += ' ';
    if (this.longitude) {
        coordenadas += this.longitude;
    }
    return coordenadas;
});

module.exports = mongoose.model('hotel', HotelSchema);