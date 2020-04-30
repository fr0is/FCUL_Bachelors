var express = require('express');
var router = express.Router();


// Require our controllers.
var book_controller = require('../controllers/bookController'); 
var author_controller = require('../controllers/authorController');
var genre_controller = require('../controllers/genreController');
var book_instance_controller = require('../controllers/bookinstanceController');


/// BOOK ROUTES ///

// GET catalog home page.
router.get('/', book_controller.index);  

// POST request for creating Book.
router.post('/book/create', book_controller.book_create_post);

// POST request to delete Book.
router.post('/book/delete', book_controller.book_delete_post);

// POST request to update Book.
router.post('/book/update', book_controller.book_update_post);

// GET request for one Book.
router.get('/book/:id', book_controller.book_detail);

// GET request for count of all Books.
router.get('/books/count', book_controller.book_count);

// GET request for list of all Book.
router.get('/books', book_controller.book_list);

/// AUTHOR ROUTES ///

// POST request for creating Author.
router.post('/author/create', author_controller.author_create_post);

// POST request to delete Author
router.post('/author/delete', author_controller.author_delete_post);

// POST request to update Author.
router.post('/author/update', author_controller.author_update_post);

// GET request for one Author books.
router.get('/author/:id/books', author_controller.author_detail_books);

// GET request for one Author.
router.get('/author/:id', author_controller.author_detail);

// GET request for count of all Authors.
router.get('/authors/count', author_controller.author_count);

// GET request for list of all Authors.
router.get('/authors', author_controller.author_list);


/// GENRE ROUTES ///

// POST request for creating Genre.
router.post('/genre/create', genre_controller.genre_create_post);

// POST request to delete Genre.
router.post('/genre/delete', genre_controller.genre_delete_post);

// POST request to update Genre.
router.post('/genre/update', genre_controller.genre_update_post);

// GET request for one Genre books.
router.get('/genre/:id/books', genre_controller.genre_detail_books);

// GET request for one Genre.
router.get('/genre/:id', genre_controller.genre_detail);

// GET request for count of all Genre.
router.get('/genres/count', genre_controller.genre_count);

// GET request for list of all Genre.
router.get('/genres', genre_controller.genre_list);


/// BOOKINSTANCE ROUTES ///

// POST request for creating BookInstance.
router.post('/bookinstance/create', book_instance_controller.bookinstance_create_post);

// POST request to delete BookInstance.
router.post('/bookinstance/delete', book_instance_controller.bookinstance_delete_post);

// POST request to update BookInstance.
router.post('/bookinstance/update', book_instance_controller.bookinstance_update_post);

// GET request for one BookInstance.
router.get('/bookinstance/:id', book_instance_controller.bookinstance_detail);

// GET request for BookInstances of one Book.
router.get('/bookinstance/book/:id', book_instance_controller.bookinstance_of_book_detail);

// GET request for count of all available BookInstance.
router.get('/bookinstances/count/available', book_instance_controller.bookinstance_count_available);

// GET request for count of all BookInstance.
router.get('/bookinstances/count', book_instance_controller.bookinstance_count);

// GET request for list of all BookInstance.
router.get('/bookinstances', book_instance_controller.bookinstance_list);


module.exports = router;
