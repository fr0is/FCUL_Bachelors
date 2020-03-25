var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var ReaderSchema = new Schema({
    name: { type: String, required: true, max: 50 },
    phone: { type: Number, required: true, min: 000000000, max: 999999999 },
    books: [{ type: Schema.Types.ObjectId, ref: 'Book', required: true }],
});

//Export model
module.exports = mongoose.model('Reader', ReaderSchema);