import { Component, OnInit } from '@angular/core';
import { Options, LabelType } from 'ng5-slider';

import { Quarto } from '../../quarto';
import { HotelService } from '../hotel.service';
import { FormGroup, FormControl, FormArray, FormBuilder, Validators } from "@angular/forms";
import { QuartoService } from '../quarto.service';
import { QuartoInstance } from 'src/quartoInstance';
import { UserService } from '../user.service';
import { Reserva } from 'src/reserva';


@Component({
  selector: 'app-quartos-pesquisa',
  templateUrl: './quartos-pesquisa.component.html',
  styleUrls: ['./quartos-pesquisa.component.css','./filtroReserva.css']
})
export class QuartosPesquisaComponent implements OnInit {
  quartos: Quarto[] = [];
  quartoInstances: any[];
  reservaForm: FormGroup;
  datasValidas = true;
  precoFinal = [];
  showPreco = false;
  dataCheckInFalta= false;
  dataCheckOutFalta= false;
  quartoEmFalta= false;
  quartosDisponiveisInicial = [];
  quartosDisponiveis = [];
  quartoSelecionado: Quarto;
  minValue: number = 100;
  maxValue: number = 450;
  options: Options = {
    floor: 0,
    ceil: 500,
    step: 50,
    translate: (value: number, label: LabelType): string => {
      switch (label) {
        case LabelType.Low:     
          return value + '€';
        case LabelType.High:
          return value + '€';
        default:
          return '€' + value;
      }
    }
  }
  datasValidasToday = true;
  checkIn = null;
  checkOut = null;

  epoca = 0;
  EpBaixa(){
    this.epoca =1;
  }
  
  EpAlta(){
    this.epoca =0;
  }
  

  constructor(
    private hotelService: HotelService,
    private formBuilder: FormBuilder,
    private quartoService: QuartoService,
    private userService: UserService
    ) {
    this.reservaForm = this.formBuilder.group({
      checkInReserva: this.formBuilder.control(this.checkIn),
      checkOutReserva: this.formBuilder.control(this.checkOut),
      quartoReserva: this.formBuilder.control("")
    })
   }

  ngOnInit(): void {
   this.showQuartos();
  }

  showQuartos() {
    this.hotelService.getHotelQuartos(sessionStorage.getItem("hotelAtual")).subscribe(quartoList => {
      this.quartos = quartoList as Quarto[];
      var instancias = [];
      for(let i = 0; i < this.quartos.length; i++){
        this.quartoService.getInstances(this.quartos[i]._id).subscribe(listInstances => {
            instancias.push(listInstances as []);
            console.log(this.quartos[i].nrQuartos);
            this.quartosDisponiveis.push(this.quartos[i].nrQuartos);
            this.quartosDisponiveisInicial.push(this.quartos[i].nrQuartos);
            console.log(this.quartosDisponiveis);
            console.log(this.quartosDisponiveis);
            this.quartoInstances = instancias;
        });
      }
    });
  }

  setQuarto(quarto){
    localStorage.setItem('quartoR',(quarto.tipo));
  }

  reservar(reservaData){
    this.compareTwoDates();
    if(this.datasValidas){
      localStorage.setItem('checkInR',(reservaData.checkInReserva));
      localStorage.setItem('checkOutR',(reservaData.checkOutReserva));
      this.reservaForm.reset();
      window.location.href = 'hoteisPSI/' + sessionStorage.getItem('hotelNome') + '/reservar';
    }else{
     alert("erro");
    }
  }

  compareTwoDates(){
    if(new Date(this.reservaForm.controls['checkOutReserva'].value)<new Date(this.reservaForm.controls['checkInReserva'].value)){
       this.datasValidas = false;
    }else{
      this.datasValidas = true;
    }
  }

  compareDates(date1, date2){
    if(new Date(date1)<=new Date(date2)){
      this.datasValidas = false;
      return false;
   }else{
     this.datasValidas = true;
     return true;
   }
  }

  //ve se data1 e menor q data2 
  //se data1 for menor entao da false
  compareDatesInstances(date1, date2){
    if(new Date(date1)<=new Date(date2)){
      this.datasValidas = false;
      return false;
   }else{
     this.datasValidas = true;
     return true;
   }
  }

  compareDatesToToday(date){
    if(new Date(date) < new Date()){
      this.datasValidasToday = false;
      return false;
   }else{
     this.datasValidasToday = true;
     return true;
   }
  }

  mudaCheckOut(newDate){
    if(this.compareDatesToToday(new Date(newDate))){
      if(this.compareDates(new Date(newDate),new Date(this.checkIn))){
        this.checkOut = newDate;
        this.calcularPreco();
        this.verficarQuartosDisponiveis();
      }
    }
    console.log(this.quartosDisponiveis);
  }

  mudaCheckIn(newDate){
    if(this.compareDatesToToday(new Date(newDate))){
        this.checkIn = newDate;
        this.calcularPreco();
        this.verficarQuartosDisponiveis();
    }
  }

  clearArray<T>(array: T[]) {
    while (array.length > 0) {
      array.pop();
    }
  }

  verficarQuartosDisponiveis(){
    if(this.checkIn === new Date() || this.checkIn === null){
      this.dataCheckInFalta = true;
    }else{
      if(this.checkOut === new Date() || this.checkOut === null){
        this.dataCheckOutFalta = true;
      }else{
        this.clearArray(this.quartosDisponiveis);
        this.quartosDisponiveisInicial.forEach((v, i) => {
          this.quartosDisponiveis.push(this.quartosDisponiveisInicial[i]);
        });
        //Ver instancias quartos
        for(let i = 0; i < this.quartoInstances.length; i++){
          var  boolean = false;
          for(let j = 0; j < this.quartoInstances[i].length; j++){
            //ha reservas
            if(typeof this.quartoInstances[i][j].reservas !== 'undefined' && this.quartoInstances[i][j].reservas.length > 0){
              //se ha reservas vamos buscar as reservas
              for(let h = 0; h < this.quartoInstances[i][j].reservas.length; h++){
                this.userService.getReservaId(this.quartoInstances[i][j].reservas[h]._id).subscribe(results => {
                var reservas: Reserva[];
                reservas = [results] as Reserva[];
                //comparar as datas das reservas todas
                for(let b = 0; b < reservas.length; b++){
                  console.log(this.quartos[i]);
                  var contagem = this.quartosDisponiveis[i];
                  if(this.compareDatesInstances(new Date(reservas[b].checkIn), this.checkIn) && this.compareDatesInstances(new Date(reservas[b].checkOut),this.checkIn)
                  && this.compareDatesInstances(new Date(reservas[b].checkIn), this.checkOut) && this.compareDatesInstances(new Date(reservas[b].checkOut),this.checkOut)){
                    boolean = true;
                  }else{
                    boolean = false;
                    contagem--;
                    console.log("pimba menos um");
                    break;
                  }
                }
                this.quartosDisponiveis[i] = contagem;
                });
              }
            }else{
              this.quartosDisponiveis[i] = this.quartosDisponiveis[i];
            }
          }
        }
      }
    }
  }

  calcularPreco(){
    if(this.checkIn === new Date() || this.checkIn === null){
        this.dataCheckInFalta = true;
    }else{
      if(this.checkOut === new Date() || this.checkOut === null){
        this.dataCheckOutFalta = true;
      }else{
          this.calcularPrecoFunction();
      }
    }
  }

  changeQuarto(valor){
    this.hotelService.getHotelQuartos(sessionStorage.getItem("hotelAtual")).subscribe(quartoList => {
      this.quartos = quartoList as Quarto[];
      for (let i = 0; i < this.quartos.length; i++) {
        if(this.quartos[i].tipo === valor){
          this.quartoSelecionado = this.quartos[i];
        }
      }
    });
  }

  calcularPrecoFunction(){
    var dates = [],
      currentDate = new Date(this.checkIn),
      addDays = function(days) {
        var date = new Date(this.valueOf());
        date.setDate(date.getDate() + days);
        return date;
      };
    while (currentDate <=  new Date(this.checkOut)) {
      dates.push(currentDate);
      currentDate = addDays.call(currentDate, 1);
    }
    this.clearArray(this.precoFinal);
    for(let j= 0; j < this.quartos.length; j++){
      var preco = 0;
      for(let i = 0; i < dates.length-1; i++){
        if((dates[i].getDate() >= 15 && dates[i].getMonth() >= 1) && (dates[i].getDate() <= 31  && dates[i].getMonth() <= 5) || (dates[i].getDate() >= 30 && dates[i].getMonth() >= 9) && (dates[i].getDate() <= 15  && dates[i].getMonth() <= 12)){
          preco += this.quartos[j].precoBaixa;
        }else{
          preco += this.quartos[j].precoAlta;
        }
      }
      this.precoFinal.push(preco);
  }
    this.showPreco = true;
  }

  changeStateDateErro(){
    this.datasValidas = !this.datasValidas;
  }

  changeStateDateErroToday(){
    this.datasValidasToday = !this.datasValidasToday;
  }

  changeStateDateErroCI(){
    this.dataCheckInFalta = !this.dataCheckInFalta;
  }

  changeStateDateErroCO(){
    this.dataCheckOutFalta = !this.dataCheckOutFalta;
  }

}
