import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { User } from 'src/user';
import { FormGroup, FormControl, FormArray, FormBuilder } from "@angular/forms";
import { Router } from '@angular/router';
import { HotelService } from '../hotel.service';
import { CartaoMBService } from '../cartao-mb.service';
import { Hotel } from 'src/hotel';
import { CartaoMB } from 'src/cartaoMB';
import { Morada } from 'src/morada';

@Component({
  selector: 'app-cliente-dados',
  templateUrl: './cliente-dados.component.html',
  styleUrls: ['./cliente-dados.component.css', './cartoesMB.css', './deleteCreateCartao.css','./moradas.css']
})
export class ClienteDadosComponent implements OnInit {

  cartaoMB: CartaoMB ={
    _id: "",
    numero: "",
    nome: "",
    prazoAno: 0,
    prazoMes: 0,
    cvv: "",
    userEmail: "",
  }
  morada: Morada ={
    _id: "",
    rua: "",
    codigoPostal: "",
    cidade: "",
    pais: "",
    userEmail: ""
  }
  clienteAtual: User = {
    _id: "",
    nome: "",
    email: "",
    password: "",
    indicativo: "",
    telefone: "",
    nif: "",
    morada: [],
    cartaoMB: [],
    reservas: []
  }
  hotel: Hotel;
  moradas: any;
  cartoes: any;
  createCartao: FormGroup;
  createMorada: FormGroup;
  criar = false;
  apagar = false;
  criarM = false;
  apagarM = false;
  link='hoteisPSI/' + sessionStorage.getItem('hotelNome') + '/cliente';

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private cartaoMBService: CartaoMBService,
    private hotelService: HotelService,
    private router: Router
  ) { 
    this.createCartao = this.formBuilder.group({
      numeroCartao1: this.formBuilder.control(""),
      numeroCartao2: this.formBuilder.control(""),
      numeroCartao3: this.formBuilder.control(""),
      numeroCartao4: this.formBuilder.control(""),
      dataCartao: this.formBuilder.control(""),
      nomeCartao: this.formBuilder.control(""),
      cvvCartao: this.formBuilder.control(""),
      dataCartaoAno: this.formBuilder.control(""),
      dataCartaoMes: this.formBuilder.control("")
    });
    this.createMorada = this.formBuilder.group({
      rua: this.formBuilder.control(""),
      codigoPostal: this.formBuilder.control(""),
      cidade: this.formBuilder.control(""),
      pais: this.formBuilder.control(""),
    })
  }

  ngOnInit(): void {
    this.getCartoes();
    this.getUser();
    this.getHotel();
    this.getMoradasUser();
  }

  getCartoes(){
    this.cartaoMBService.getCartaoEmail(localStorage.getItem('userAtual')).subscribe(listCartao =>{
      this.cartoes = listCartao;
    });
  }

  getUser(){
    this.userService.getUser(localStorage.getItem('userAtual')).subscribe(user => {
      this.clienteAtual = user[0];
    });
  }

  getHotel() {
    this.hotelService.getHotel(sessionStorage.getItem("hotelAtual")).subscribe(results => {
      this.hotel = results;
    })
  }

  criarCartao(){
    this.criar = !this.criar;
  }

  criarMorada(){
    this.criarM = !this.criarM;
  }

  apagarCartao(cartao){
    this.cartaoMBService.apagarCartao(cartao).subscribe(result => {
      if(result.message === 'success'){
        this.apagar = true;
      }
    });
  }

  apagarCartaoFechar(){
    this.apagar = false;
  }

  apagarMoradaFechar(){
    this.apagarM = false;
  }

  create(cartaoData){
    const nrCartao = cartaoData.numeroCartao1 + cartaoData.numeroCartao2 + cartaoData.numeroCartao3 + cartaoData.numeroCartao4;
    this.criar = !this.criar;
    this.cartaoMB.numero = nrCartao;
    this.cartaoMB.nome = cartaoData.nomeCartao;
    this.cartaoMB.prazoAno = cartaoData.dataCartaoAno;
    this.cartaoMB.prazoMes = cartaoData.dataCartaoMes;
    this.cartaoMB.cvv = cartaoData.cvvCartao;
    this.cartaoMB.userEmail = localStorage.getItem('userAtual');
    this.createCartao.reset();
  
    this.cartaoMBService.createCartao(this.cartaoMB).subscribe(result => {
      if(result.message === "success"){
        window.location.href = 'hoteisPSI/' + sessionStorage.getItem('hotelNome')+'/cliente';
      }else{
        alert("Ocorreu um erro a criar o cartao!");
      }
    });
  }

  createM(moradaData){
    this.criarM = !this.criarM;
    this.morada.rua = moradaData.rua;
    this.morada.codigoPostal = moradaData.codigoPostal;
    this.morada.cidade = moradaData.cidade;
    this.morada.pais = moradaData.pais;
    this.morada.userEmail = localStorage.getItem('userAtual');
    this.createMorada.reset();
  
    this.userService.createMorada(this.morada).subscribe(result => {
      if(result.message === "success"){
        window.location.href = 'hoteisPSI/' + sessionStorage.getItem('hotelNome')+'/cliente';
      }else{
        alert("Ocorreu um erro a criar o cartao!");
      }
    });
  }

  getMoradasUser(){
    this.userService.getUserMoradas(localStorage.getItem('userAtual')).subscribe(listMorada =>{
      this.moradas = listMorada;
    });
  }

  apagarMorada(morada){
    this.userService.apagarMorada(morada).subscribe(result => {
      if(result.message === 'success'){
        this.apagarM = true;
      }
    });
  }

}
