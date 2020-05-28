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
import { Reserva } from 'src/reserva';
import { Quarto } from 'src/quarto';

@Component({
  selector: 'app-cliente-reservas',
  templateUrl: './cliente-reservas.component.html',
  styleUrls: ['./cliente-reservas.component.css','editarReserva.css','livro.css']
})
export class ClienteReservasComponent implements OnInit {

  alterarDatas=true;
  cartaoMB: CartaoMB ={
    _id: "",
    numero: "",
    nome: "",
    prazoAno: 0,
    prazoMes: 0,
    cvv: "",
    userEmail: "",
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
  reserva: Reserva = {
    _id: "",
    userEmail: "",
    emailReserva: "",
    indicativoReserva: "",
    nifReserva: "",
    nomeReserva: "",
    telefoneReserva: "",
    quarto: null,
    morada: null,
    metodoDePagamento: null,
    checkIn: null,
    checkOut: null,
    preco: 0
  }
  atualizarReservaSucesso = false;
  atualizarReservaErro = false;
  precoAntigo = 0;
  precoNovaReserva = 0;
  reservaEdicao: Reserva = {
    _id: "",
    userEmail: "",
    emailReserva: "",
    indicativoReserva: "",
    nifReserva: "",
    nomeReserva: "",
    telefoneReserva: "",
    quarto: null,
    morada: null,
    metodoDePagamento: null,
    checkIn: null,
    checkOut: null,
    preco: 0
  }
  alterarDadosPagamento = false;
  editar = false;
  quartos: Quarto[] = [];
  reservas: Reserva[] = [];
  cartoes: CartaoMB[] = [];
  cartoesUser: CartaoMB[] = [];
  down = [];
  precoFiltro: Number = 0;
  dataFiltro: Date = new Date(1900, 2, 1);
  checkIn: Date = new Date(2000);
  hotel: Hotel;
  moradas: Morada[] = [];
  filtrar: FormGroup;
  datasForm: FormGroup;
  pagamentoForm: FormGroup;
  checkInNovaReserva= new Date();
  checkOutNovaReserva= new Date();
  cartaoId= "";
  message="";
  datasErro= false;
  confirmarCartao = false;
  link='hoteisPSI/' + sessionStorage.getItem('hotelNome') + '/cliente';

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private hotelService: HotelService,
    private cartaoMbService: CartaoMBService
  ) { 
    this.filtrar = this.formBuilder.group({
      precoFiltro: this.formBuilder.control(this.precoFiltro),
      dataFiltro: this.formBuilder.control(new Date(1900, 2, 1)),
    });
    this.datasForm = this.formBuilder.group({
      dataCheckIn: this.formBuilder.control(new Date()),
      dataCheckOut: this.formBuilder.control(new Date()),
    });
  }

  ngOnInit(): void {
    this.getReservas();
    this.getCartoes();
  }

  updateFiltro(formData){
    this.precoFiltro = formData.precoFiltro;
    this.dataFiltro = formData.dataFiltro;
  }

  dataNice(data){
    if(new Date(data).getTime() >= new Date(this.dataFiltro).getTime()){
      return true;
    }else{
      return false;
    }
  }

  getCartoes(){
    this.cartaoMbService.getCartaoEmail(localStorage.getItem('userAtual')).subscribe(listCartao => {
      this.cartoesUser = listCartao as CartaoMB[];
    });
  }

  changeSucessoReserva(){
    this.atualizarReservaSucesso = !this.atualizarReservaSucesso;
    window.location.href = 'hoteisPSI/' + sessionStorage.getItem('hotelNome')+'/cliente/reservas';
  }

  changeErroReserva(){
    this.atualizarReservaErro = !this.atualizarReservaErro;
    window.location.href = 'hoteisPSI/' + sessionStorage.getItem('hotelNome')+'/cliente/reservas';
  }

  changeDatasErro(){
    this.datasErro = !this.datasErro;
  }

  getReservas(){
    this.userService.getUserReservas(localStorage.getItem('userAtual')).subscribe(listReservas => {
      this.reservas = listReservas as Reserva[];
      for(let i = 0; i < this.reservas.length; i++){
        this.hotelService.getHotelQuarto(this.reservas[i].quarto.quarto).subscribe(results => {
          this.quartos[i] = results;
          this.down[i] = false;
        });
        this.userService.getMorada(this.reservas[i].morada._id).subscribe(morada => {
          this.moradas[i] = morada;
        });
        this.cartaoMbService.getCartao(this.reservas[i].metodoDePagamento._id).subscribe(cartao => {
          this.cartoes[i] = cartao;
        });
      }
    });
  }

  format(s) {
    return s.toString().replace(/\d{4}(?=.)/g, '$& ');
  }

  showDropdown(i){
    this.down[i] = !this.down[i];
  }

  onItemChangeCMB(valor){
    this.cartaoId = valor;
    console.log(valor);
  }

  confirmarReserva(){
    this.cartaoId = this.reservaEdicao.metodoDePagamento._id;
    this.updateReserva();
  }

  delete(reserva){
    alert("delete");
  }

  edit(reserva){
    this.editar = true;
    this.checkInNovaReserva = reserva.checkIn;
    this.checkInNovaReserva = reserva.checkOut;
    this.precoAntigo = reserva.preco;
    this.reservaEdicao = reserva;
  }

  change(datasValue){
    this.reservaEdicao.checkIn = new Date(datasValue.dataCheckIn);
    this.reservaEdicao.checkOut = new Date(datasValue.dataCheckOut);
    this.calcularPreco(this.reservaEdicao);
    this.reservaEdicao.preco = this.precoNovaReserva;
    if(new Date(this.reservaEdicao.checkIn) < new Date(this.reservaEdicao.checkOut)){
      this.alterarDatas = false;
      this.confirmarCartao = true;
    } else{
      this.datasErro = true;
    }
  }

  mudaCI(data){
    this.checkInNovaReserva = data;
    this.calcularPreco(this.reservaEdicao);
  }

  mudaCO(data){
    this.checkOutNovaReserva = data;
    this.calcularPreco(this.reservaEdicao)
  }

  calcularPreco(reserva){
    var dates = [],
      currentDate = new Date(this.checkInNovaReserva),
      addDays = function(days) {
        var date = new Date(this.valueOf());
        date.setDate(date.getDate() + days);
        return date;
      };
    while (currentDate <=  new Date(this.checkOutNovaReserva)) {
      dates.push(currentDate);
      currentDate = addDays.call(currentDate, 1);
    }
    var preco = 0;
    this.hotelService.getHotelQuarto(reserva.quarto.quarto).subscribe(results => {
      const quarto = results as Quarto;
      for(let i = 0; i < dates.length-1; i++){
        if((dates[i].getDate() >= 15 && dates[i].getMonth() >= 1) && (dates[i].getDate() <= 31  && dates[i].getMonth() <= 5) || (dates[i].getDate() >= 30 && dates[i].getMonth() >= 9) && (dates[i].getDate() <= 15  && dates[i].getMonth() <= 12)){
          preco += quarto.precoBaixa;
        }else{
          preco += quarto.precoAlta;
        }
      }
      this.precoNovaReserva = preco;
    });
  }

  updateReserva(){
    this.reservaEdicao.quarto = this.reservaEdicao.quarto._id;
    this.reservaEdicao.morada = this.reservaEdicao.morada._id;
    this.reservaEdicao.metodoDePagamento = this.cartaoId;
    console.log(this.cartaoId);
    console.log(this.reservaEdicao);
    this.userService.updateReserva(this.reservaEdicao).subscribe(result =>   {
      if(result.message == "success"){
        this.atualizarReservaSucesso = true;
      }else{
        this.atualizarReservaErro = true;
      }
    });
  }
}
