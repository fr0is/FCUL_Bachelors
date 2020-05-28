#! /usr/bin/env node

console.log('PSI here we go boyz');

var userArgs = process.argv.slice(2);
/*
if (!userArgs[0].startsWith('mongodb')) {
    console.log('ERROR: You need to specify a valid mongodb URL as the first argument');
    return
}
*/
var async = require('async')
var Hotel = require('./models/hotel')
var Quarto = require('./models/quarto')
var QuartoInstance = require('./models/quartoInstance')
var Utilizador = require('./models/utilizador')
var Cartao = require('./models/cartaoMB');


var mongoose = require('mongoose');
var MongoClient = require('mongodb').MongoClient;
var mongoDB = userArgs[0];
mongoose.connect(mongoDB, { useNewUrlParser: true });
mongoose.Promise = global.Promise;
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'MongoDB connection error:'));

var hoteis = [];
var quartos = [];
var quartosInstance = [];
var users = [];
var cartoes = [];


function hotelCreate(nome, descricao, local, zona, codigoPostal, pais, latitude, longitude, codigoRegiao, telefone, email, servicos, fotos, fotoPath, cb) {
    hotelDetail = {
        nome: nome,
        descricao: descricao,
        local: local,
        zona: zona,
        codigoPostal: codigoPostal,
        pais: pais,
        latitude: latitude,
        longitude: longitude,
        codigoRegiao: codigoRegiao,
        telefone: telefone,
        email: email,
        servicos: servicos,
        fotos: fotos,
        fotoPath: fotoPath
    }

    var hotel = new Hotel(hotelDetail);

    hotel.save(function(err) {
        if (err) {
            cb(err, null)
            return
        }
        console.log('Novo Hotel: ' + hotel);
        hoteis.push(hotel)
        cb(null, hotel)
    });
}

function quartoCreate(tipo, indice, nrQuartos, precoAlta, precoBaixa, hotel, servicos, foto, cb) {
    quartoDetail = {
        tipo: tipo,
        indice: indice,
        nrQuartos: nrQuartos,
        precoAlta: precoAlta,
        precoBaixa: precoBaixa,
        hotel: hotel,
        servicos: servicos,
        foto: foto
    }

    var quarto = new Quarto(quartoDetail);

    quarto.save(function(err) {
        if (err) {
            cb(err, null)
            return
        }
        console.log('Novo Quarto: ' + quarto);
        quartos.push(quarto)
        cb(null, quarto)
    });
}

function quartoInstanceCreate(numeroQuarto, quarto, reservas, cb) {
    quartoInstanceDetail = {
        numeroQuarto: numeroQuarto,
        quarto: quarto,
        reservas: reservas,
    }

    var quartoInstance = new QuartoInstance(quartoInstanceDetail);

    quartoInstance.save(function(err) {
        if (err) {
            cb(err, null)
            return
        }
        console.log('Novo Quarto: ' + quartoInstance);
        quartosInstance.push(quartoInstance)
        cb(null, quartoInstance)
    });
}

function userCreate(nome, email, password, cb) {
    userDetail = {
        nome: nome,
        email: email,
        password: password,
    }

    var user = new Utilizador(userDetail);

    user.save(function(err) {
        if (err) {
            cb(err, null)
            return
        }
        console.log('Novo User: ' + user);
        users.push(user)
        cb(null, user)
    });
}

function cartaoCreate(nr, prazoAno, prazoMes, cvv, user, cb) {
    cartaoDetail = {
        numero: nr,
        prazoAno: prazoAno,
        prazoMes: prazoMes,
        cvv: cvv,
        userEmail: user
    }
    var cartao = new Cartao(cartaoDetail);

    cartao.save(function(err) {
        if (err) {
            cb(err, null)
            return
        }
        console.log('Novo Cartao: ' + cartao);
        cartoes.push(cartao)
        cb(null, cartao)
    });
}

function createHoteis(cb) {
    async.parallel([
        function(callback) {
            hotelCreate('Douro Vinhas', ['Com uma vista de cortar o fôlego para o Rio Douro e para o Rio Tedo, é no coração do Douro Vinhateiro que surge o Hotel Douro Vinhas. Com uma forte componente de agro e enoturismo, esta unidade estende-se pela centenária Quinta do Moreira.', 'Na margem sul do Douro, perto da pitoresca aldeia do Marmelal, a propriedade que acolhe o Hotel Douro Vinhas fica muito próxima de um dos dois marcos mandados construir pelo Marquês de Pombal em 1757. Classificados como imóveis de interesse público, serviam para demarcar a zona dos vinhos generosos do Douro, à época sob jurisdição da Companhia Geral da Agricultura das Vinhas Douro. Nascia assim a primeira região demarcada de vinhos do mundo. Hoje, os vinhedos em socalcos tornam única a paisagem que rodeia esta unidade.', 'Numa primeira fase com apenas sete quartos, o Hotel Douro Vinhas distingue-se pela localização, pelo charme e pela exclusividade. Aqui poderá desfrutar da calma e do silêncio, do cenário, mas também a piscina exterior, da gastronomia regional do restaurante Moreira, cujos grandes janelões permitem admirar a envolvente. Mas também das visitas à adega e provas de vinhos do Porto, produzidos no local.', 'Poderá ainda aproveitar para passear entre as vinhas, pelo olival ou pelo amendoal (particularmente bonito durante as amendoeiras em flor), sempre com o Tedo e o Douro como companhia. Para completar a estadia, faça um cruzeiro fluvial, visite as quintas vinícolas da região ou faça um passeio de comboio. Ver as amendoeiras em flor ou participar nas vindimas são outras sugestões.', ], 'Hotel Douro Vinhas', 'Quinta do Moreira – Marmelal', '5110-672 Armamar', 'Portugal', '41°09\'26.0"N', '7°38\'26.0"W', 351, 254249000, 'dourovinhas@hoteispsi.com', ['Adega Moreira', 'Acesso gratuito à internet via wi-fi', 'Serviço de lavandaria', 'Acessos para pessoas com mobilidade reduzida', 'Receção 24 horas', 'Biblioteca', ], ['/assets/Imagens/HotelDV/lounge.jpg', '/assets/Imagens/HotelDV/piscina.jpg', '/assets/Imagens/HotelDV/rececao.jpg', '/assets/Imagens/HotelDV/restaurante.jpg', '/assets/Imagens/HotelDV/standard.jpg', '/assets/Imagens/HotelDV/suite.jpg', '/assets/Imagens/HotelDV/suiteDeluxe.jpg', '/assets/Imagens/HotelDV/suiteDuplex.jpg', '/assets/Imagens/HotelDV/varanda.jpg', '/assets/Imagens/HotelDV/vinha.jpg', '/assets/Imagens/HotelDV/wc.jpg', ], '/assets/Imagens/HotelDV/', callback);
        },
        function(callback) {
            hotelCreate('A Ver o Mar', ['Situado na pitoresca vila da Ericeira, mesmo em cima da praia, este hotel com história e tradição, que resulta da reabilitação do marcante Hotel de Turismo da Ericeira, tem como cenário o Oceano Atlântico.', 'A 30 minutos de Lisboa, com acesso direto por autoestrada, o hotel A Ver o Mar dispõe de quatro tipologias de quarto, destacando-se os que têm varanda e vista para o mar. Este hotel na Ericeira inclui um restaurante, dois bares, salas para eventos e reuniões empresariais, clube de crianças e parque infantil e um moderno clube de saúde com salas de massagens, jacuzzi, sauna, banho turco e ginásio.', 'Durante a sua estadia no hotel A Ver o Mar, não deixe de dar um mergulho nas duas piscinas para adultos, uma das quais de água salgada. Já as crianças vão adorar os escorregas aquáticos.', 'Partindo deste hotel na Ericeira, aventure-se a conhecer as praias da região. E saiba que se apreciar desportos de ondas, está em plena reserva mundial de surf, e palco de uma das etapas do circuito WSL World Tour que junta os melhores surfistas do mundo. Pode ainda visitar o Palácio Nacional de Mafra ou Sintra, a 20 minutos de distância do hotel A Ver o Mar, de carro.', 'No verão, a animação da vila aumenta graças a vários festivais,entre os quais um dedicado exclusivamente à música reggae.', ], 'Hotel A Ver o Mar', 'Largo dos Navegantes', '2655-320 Ericeira', 'Portugal', '38°57\'56.0"N', '9°25\'09.0"W', 351, 261869700, 'averomar@hoteispsi.com', ['Jardins e espaços exteriores', 'Piscina exterior para adultos e crianças', 'Acesso gratuito à internet via wi-fi', 'Parque de estacionamento', 'Nep Kids Club', 'Parque infantil', 'Sala de jogos', 'Serviço de lavandaria', 'Acessos para pessoas com mobilidade reduzida', 'Clube de saúde', 'Receção 24 horas', ], ['/assets/Imagens/HotelAVerOMar/comida.jpg', '/assets/Imagens/HotelAVerOMar/lounge.jpg', '/assets/Imagens/HotelAVerOMar/massagem.jpg', '/assets/Imagens/HotelAVerOMar/piscina.jpg', '/assets/Imagens/HotelAVerOMar/quarto2.jpg', '/assets/Imagens/HotelAVerOMar/rececao.jpg', '/assets/Imagens/HotelAVerOMar/restaurante.jpg', '/assets/Imagens/HotelAVerOMar/standard.jpg', '/assets/Imagens/HotelAVerOMar/suiteJunior.jpg', '/assets/Imagens/HotelAVerOMar/suiteJuniorSuperior.jpg', '/assets/Imagens/HotelAVerOMar/vistaCimaHotel.jpg', '/assets/Imagens/HotelAVerOMar/vistaPraia.jpg', ], '/assets/Imagens/HotelAVerOMar/', callback);
        },
        function(callback) {
            hotelCreate('Mediterrâneo', ['Encontrará o Hotel Mediterrâneo mesmo junto à praia e apenas a cinco minutos do centro de Albufeira, no Algarve.', 'Este hotel em Albufeira oferece quartos modernos e amplos, dos quais se destacam os com vista para o mar ou as suítes. Todos têm kitchenette, sendo ideais para famílias com crianças.', 'Conta ainda com um bar, um restaurante com buffet internacional e piscinas exteriores para adultos e crianças, prometendo dias de muito sol e animação. Tem ainda uma sala de jogos, parque infantil, clube de crianças com atividades para os mais novos e animadores próprios, spa com piscina interior, banho turco e jacuzzi, salas de massagem, ginásio e wi-fi gratuito em todas as zonas.', 'Durante a sua estadia aproveite para conhecer as irresistíveis praias de Albufeira, aventurar-se em desportos náuticos ou desfrutar das animadas ruas da cidade, repletas de bares, restaurantes e comércio, ou passear na marina.', 'Pode também visitar diferentes parques temáticos como o Zoomarine, Aqualand e Aquashow.', ], 'Hotel Mediterrâneo', 'Praia da Galé', '8200-995 Albufeira', 'Portugal', '37°04\'55.0"N', '8°19\'03.0"W', 351, 289570700, 'mediterraneo@hoteispsi.com', ['Jardins e espaços exteriores', 'Piscina exterior para adultos e crianças', 'Acesso gratuito à internet via wi-fi', 'Parque de estacionamento', 'Nep Kids Club', 'Parque infantil', 'Sala de jogos', 'Serviço de lavandaria', 'Acessos para pessoas com mobilidade reduzida', 'Clube de Saúde', 'Receção 24 horas', 'Lojas', ], ['/assets/Imagens/HotelMediterraneo/lounge.jpg', '/assets/Imagens/HotelMediterraneo/piscina.jpg', '/assets/Imagens/HotelMediterraneo/piscinaInterior.jpg', '/assets/Imagens/HotelMediterraneo/quarto.jpg', '/assets/Imagens/HotelMediterraneo/rececao.jpg', '/assets/Imagens/HotelMediterraneo/restaurante.jpg', '/assets/Imagens/HotelMediterraneo/suiteJunior.jpg', '/assets/Imagens/HotelMediterraneo/suiteSenior.jpg', '/assets/Imagens/HotelMediterraneo/vista.jpg', '/assets/Imagens/HotelMediterraneo/vistaCima.jpg', ], '/assets/Imagens/HotelMediterraneo/', callback);
        },
    ], cb);
}

function createQuartos(cb) {
    async.parallel([
        function(callback) {
            quartoCreate('Standard', 1, 3, 270, 180, hoteis[0], ['Telefone', 'Wi-fi gratuito', 'Ar condicionado', 'Televisão LED', 'Canais por cabo', 'Mini-bar', 'Cofre', 'Casa de banho privativa com telefone', 'Secador de cabelo', 'Espelho de maquilhagem', 'Produtos de higiene pessoal gratuitos', '-', 'Fechadura eletrónica de segurança', '-', 'Roupão e chinelos', 'Maquina de café', ], '/assets/Imagens/HotelDV/standard.jpg', callback);
        },
        function(callback) {
            quartoCreate('Suite', 2, 1, 330, 250, hoteis[0], ['Telefone', 'Wi-fi gratuito', 'Ar condicionado', 'Televisão LED', 'Canais por cabo', '-', 'Cofre', 'Casa de banho privativa com telefone', 'Secador de cabelo', 'Espelho de maquilhagem', 'Produtos de higiene pessoal gratuitos', '-', 'Fechadura eletrónica de segurança', 'Sala-de-estar', 'Roupão e chinelos', 'Maquina de café', ], '/assets/Imagens/HotelDV/suite.jpg', callback);
        },
        function(callback) {
            quartoCreate('Suite Duplex', 3, 1, 350, 270, hoteis[0], ['Telefone', 'Wi-fi gratuito', 'Ar condicionado', 'Televisão LED', 'Canais por cabo', '-', 'Cofre', 'Casa de banho privativa com telefone', 'Secador de cabelo', 'Espelho de maquilhagem', 'Produtos de higiene pessoal gratuitos', 'Serviço de quarto 24 horas', 'Fechadura eletrónica de segurança', 'Sala-de-estar', 'Roupão e chinelos', 'Maquina de café', ], '/assets/Imagens/HotelDV/suiteDuplex.jpg', callback);
        },
        function(callback) {
            quartoCreate('Suite Deluxe', 4, 1, 450, 310, hoteis[0], ['Telefone', 'Wi-fi gratuito', 'Ar condicionado', 'Televisão LED', 'Canais por cabo', '-', 'Cofre', 'Casa de banho privativa com telefone', 'Secador de cabelo', 'Espelho de maquilhagem', 'Produtos de higiene pessoal gratuitos', 'Serviço de quarto 24 horas', 'Fechadura eletrónica de segurança', 'Sala-de-estar', 'Roupão e chinelos', 'Maquina de café', ], '/assets/Imagens/HotelDV/suiteDeluxe.jpg', callback);
        },
        function(callback) {
            quartoCreate('Standard', 1, 182, 160, 90, hoteis[1], ['Telefone', 'Wi-fi gratuito', 'Ar condicionado', 'Televisão LCD', 'Canais por cabo', 'Mini-bar', 'Cofre', 'Casa de banho privativa com telefone', 'Secador de cabelo', 'Produtos de higiene pessoal gratuitos', 'Fechadura eletrónica de segurança', 'Serviço de quarto 24 horas', '-', '-', 'Chaleira', ], '/assets/Imagens/HotelAVerOMar/standard.jpg', callback);
        },
        function(callback) {
            quartoCreate('Suite Junior', 2, 5, 180, 120, hoteis[1], ['Telefone', 'Wi-fi gratuito', 'Ar condicionado', 'Televisão LCD', 'Canais por cabo', 'Mini-bar', 'Cofre', 'Casa de banho privativa com telefone', 'Secador de cabelo', 'Produtos de higiene pessoal gratuitos', 'Fechadura eletrónica de segurança', 'Serviço de quarto 24 horas', 'Sala-de-estar', '-', 'Chaleira', ], '/assets/Imagens/HotelAVerOMar/suiteJunior.jpg', callback);
        },
        function(callback) {
            quartoCreate('Suite Junior Superior', 3, 15, 210, 130, hoteis[1], ['Telefone', 'Wi-fi gratuito', 'Ar condicionado', 'Televisão LCD', 'Canais por cabo', 'Cofre', 'Casa de banho privativa com telefone', 'Secador de cabelo', 'Produtos de higiene pessoal gratuitos', 'Fechadura eletrónica de segurança', 'Serviço de quarto 24 horas', 'Sala-de-estar', 'Varanda', 'Chaleira', ], '/assets/Imagens/HotelAVerOMar/suiteJuniorSuperior.jpg', callback);
        },
        function(callback) {
            quartoCreate('Standard', 1, 114, 210, 70, hoteis[2], ['Telefone', 'Wi-fi grátis', 'Ar Condicionado', 'Televisão LCD', 'Canais por Cabo', 'Cofre', 'Casa de banho privativa com telefone', 'Secador de Cabelo', 'Espelho de maquilhagem', 'Produtos de Higiene Pessoal Gratuitos', 'Kitchenette', 'Fechadura Eletrónica de Segurança', 'Serviço de Quarto 24 horas', '-', '-', 'Frigorífico', 'Micro-ondas', '-', 'Chaleira', ], '/assets/Imagens/HotelMediterraneo/quarto.jpg', callback);
        },
        function(callback) {
            quartoCreate('Suite Junior', 2, 98, 250, 90, hoteis[2], ['Telefone', 'Wi-fi grátis', 'Ar Condicionado', 'Televisão LCD', 'Canais por Cabo', 'Cofre', 'Casa de banho privativa com telefone', 'Secador de Cabelo', 'Espelho de maquilhagem', 'Produtos de Higiene Pessoal Gratuitos', 'Kitchenette', 'Fechadura Eletrónica de Segurança', 'Serviço de Quarto 24 horas', '-', 'Varanda', 'Frigorífico', 'Micro-ondas', 'Sala-de-estar', 'Chaleira', ], '/assets/Imagens/HotelMediterraneo/suiteJunior.jpg', callback);
        },
        function(callback) {
            quartoCreate('Suite Senior', 3, 8, 240, 120, hoteis[2], ['Telefone', 'Wi-fi grátis', 'Ar Condicionado', 'Televisão LCD', 'Canais por Cabo', 'Cofre', 'Casa de banho privativa com telefone', 'Secador de Cabelo', 'Espelho de maquilhagem', 'Produtos de Higiene Pessoal Gratuitos', 'Kitchenette', 'Fechadura Eletrónica de Segurança', 'Serviço de Quarto 24 horas', 'Sofá-cama', 'Varanda', 'Frigorífico', 'Micro-ondas', 'Sala-de-estar', 'Chaleira', ], '/assets/Imagens/HotelMediterraneo/suiteSenior.jpg', callback);
        },
    ], cb);
}

function createQuartoInstances(cb) {
    async.parallel([
        //quartos do Douro Vinhas
        function(callback) {
            quartoInstanceCreate(1, quartos[0], [], callback);
        },
        function(callback) {
            quartoInstanceCreate(2, quartos[0], [], callback);
        },
        function(callback) {
            quartoInstanceCreate(3, quartos[0], [], callback);
        },
        function(callback) {
            quartoInstanceCreate(4, quartos[1], [], callback);
        },
        function(callback) {
            quartoInstanceCreate(5, quartos[2], [], callback);
        },
        function(callback) {
            quartoInstanceCreate(6, quartos[3], [], callback);
        },

        //quartos do A Ver o Mar
        function(callback) {
            var insts = [];
            for (i = 1; i <= 182; i++) {
                insts.push(new QuartoInstance({ numeroQuarto: i, quarto: quartos[4], reservas: [] }));
            }
            MongoClient.connect(mongoDB, { useUnifiedTopology: true }, function(err, db) {
                if (err) throw err;
                var dbo = db.db("");
                dbo.collection("quartoinstances").insertMany(insts, function(err, res) {
                    if (err) throw err;
                    db.close();
                });
            });
            callback();
        },
        function(callback) {
            var insts = [];
            for (i = 183; i <= 187; i++) {
                insts.push(new QuartoInstance({ numeroQuarto: i, quarto: quartos[5], reservas: [] }));
            }
            MongoClient.connect(mongoDB, { useUnifiedTopology: true }, function(err, db) {
                if (err) throw err;
                var dbo = db.db("");
                dbo.collection("quartoinstances").insertMany(insts, function(err, res) {
                    if (err) throw err;
                    db.close();
                });
            });
            callback();
        },
        function(callback) {
            var insts = [];
            for (i = 188; i <= 202; i++) {
                insts.push(new QuartoInstance({ numeroQuarto: i, quarto: quartos[6], reservas: [] }));
            }
            MongoClient.connect(mongoDB, { useUnifiedTopology: true }, function(err, db) {
                if (err) throw err;
                var dbo = db.db("");
                dbo.collection("quartoinstances").insertMany(insts, function(err, res) {
                    if (err) throw err;
                    db.close();
                });
            });
            callback();
        },

        //quartos do Mediterrâneo
        function(callback) {
            var insts = [];
            for (i = 1; i <= 114; i++) {
                insts.push(new QuartoInstance({ numeroQuarto: i, quarto: quartos[7], reservas: [] }));
            }
            MongoClient.connect(mongoDB, { useUnifiedTopology: true }, function(err, db) {
                if (err) throw err;
                var dbo = db.db("");
                dbo.collection("quartoinstances").insertMany(insts, function(err, res) {
                    if (err) throw err;
                    db.close();
                });
            });
            callback();
        },
        function(callback) {
            var insts = [];
            for (i = 115; i <= 212; i++) {
                insts.push(new QuartoInstance({ numeroQuarto: i, quarto: quartos[8], reservas: [] }));
            }
            MongoClient.connect(mongoDB, { useUnifiedTopology: true }, function(err, db) {
                if (err) throw err;
                var dbo = db.db("");
                dbo.collection("quartoinstances").insertMany(insts, function(err, res) {
                    if (err) throw err;
                    db.close();
                });
            });
            callback();
        },
        function(callback) {
            var insts = [];
            for (i = 213; i <= 220; i++) {
                insts.push(new QuartoInstance({ numeroQuarto: i, quarto: quartos[9], reservas: [] }));
            }
            MongoClient.connect(mongoDB, { useUnifiedTopology: true }, function(err, db) {
                if (err) throw err;
                var dbo = db.db("");
                dbo.collection("quartoinstances").insertMany(insts, function(err, res) {
                    if (err) throw err;
                    db.close();
                });
            });
            callback();
        },
    ], cb);
}

function createUsers(cb) {
    async.parallel([
        function(callback) {
            userCreate('Mena', 'mena@psihoteis.com', '123', callback)
        },
        function(callback) {
            userCreate('Dio', 'Dio@psihoteis.com', 'ZaWarudo', callback)
        },
    ], cb);
}




async.series([
        createHoteis,
        createQuartos,
        createQuartoInstances,
        createUsers
    ],
    function(err, results) {
        if (err) {
            console.log('FINAL ERR: ' + err);
        } else {
            console.log('Sucesso :D');

        }
        mongoose.connection.close();
    });