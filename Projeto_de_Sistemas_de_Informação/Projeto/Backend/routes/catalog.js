var express = require('express');
var router = express.Router();

//Controllers
var quarto_controller = require('../controllers/quartoController');
var hotel_controller = require('../controllers/hotelController');
var utilizador_controller = require('../controllers/utilizadorController');
var cartaoMB_controller = require('../controllers/cartaoMBController');
var morada_controller = require('../controllers/moradaController');
var reserva_controller = require('../controllers/reservaController');
var quartoInstance_controller = require('../controllers/quartoInstanceController');

router.get('/hoteis', hotel_controller.hotel_list); // lista de hoteis
router.get('/hoteis/:hotelId', hotel_controller.hotel_detail); // hotel
router.get('/hoteis/:hotelId/quartos', quarto_controller.room_list); // lista de quartos de um hotel
router.get('/hoteis/:hotelId/quartos/:quartoId', quarto_controller.quarto_detail); // quarto

/********************** User **********************/
router.get('/users/:email', utilizador_controller.utilizador_get); // user get
router.post('/users/create', utilizador_controller.utilizador_create); //user create
router.post('/users/update', utilizador_controller.utilizador_update); //user update

/********************** CartaoMB **********************/
router.get('/users/:userEmail/cartoes', cartaoMB_controller.cartaoMb_get_cartao_email); //cartao get email
router.get('/users/reserva/:cartaoId', cartaoMB_controller.cartaoMb_get); //cartao get id
router.post('/users/cartao/create', cartaoMB_controller.cartaoMb_create); //cartao create
router.post('/users/cartao/delete', cartaoMB_controller.cartaoMb_delete); //cartao delete

/********************** Morada **********************/
router.get('/morada/:userEmail', morada_controller.morada_get_email); //cartao get email
router.get('/morada/getOne/:moradaId', morada_controller.morada_get); //cartao get id
router.post('/morada/create', morada_controller.morada_create); //cartao create
router.post('/morada/delete', morada_controller.morada_delete); //cartao delete

/********************** Reserva **********************/
router.get('/reserva/email/:userEmail', reserva_controller.reserva_get_email); //reserva get email
router.get('/reserva/quarto/:quarto', reserva_controller.reserva_get_quarto); //reserva get quarto
router.get('/reserva/:reservaId', reserva_controller.reserva_get_id); //reserva get quarto
router.post('/reserva/create', reserva_controller.reserva_create); //reserva create
router.post('/reserva/delete', reserva_controller.reserva_delete); //reserva delete
router.post('/reserva/update', reserva_controller.reserva_update); //reserva update

/*************** Quarto Instance ************/
router.get('/quartoInstance/:quartoId', quartoInstance_controller.quartoInstance_get) //quartoInstance get

module.exports = router;