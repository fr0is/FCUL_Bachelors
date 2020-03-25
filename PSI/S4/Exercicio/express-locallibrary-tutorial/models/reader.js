var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var ReaderSchema = new Schema({
    name: { type: String, required: true, max: 50 },
    phone: { type: Number, required: true, min: 000000000, max: 999999999 },
    books: [{ type: Schema.Types.ObjectId, ref: 'Book', required: true }],
});

// Virtual for reader's URL
ReaderSchema
    .virtual('url')
    .get(function() {
        return '/catalog/reader/' + this._id;
    });

ReaderSchema
    .virtual('book')
    .get(function() {
        return '/catalog/book/readers/';
    });

//Export model
module.exports = mongoose.model('Reader', ReaderSchema);