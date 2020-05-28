import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HotelService } from '../hotel.service';
import { Quarto } from 'src/quarto';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-quarto-pagina-detalhes',
  templateUrl: './quarto-pagina-detalhes.component.html',
  styleUrls: ['./quarto-pagina-detalhes.component.css']
})
export class QuartoPaginaDetalhesComponent implements OnInit {

  quarto: Quarto = {
    _id: "",
    servicos:[],
    indice: 0,
    tipo: "",
    nrQuartos: 0,
    precoAlta: 0,
    precoBaixa: 0,
    hotel:"",
    foto:"",
  };
  datasValidasToday = true;
  datasValidas = true;
  reservaForm: FormGroup;
  checkIn = null;
  checkOut = null
  constructor(    
    private route: ActivatedRoute,
    private hotelService: HotelService,
    private formBuilder: FormBuilder,
    ) {
      this.reservaForm = this.formBuilder.group({
        checkInReserva: this.formBuilder.control(this.checkIn),
        checkOutReserva: this.formBuilder.control(this.checkOut),
        quartoReserva: this.formBuilder.control("")
      })
     }

  ngOnInit(): void {
    this.showQuarto();
  }

  showQuarto() {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.hotelService.getHotelQuarto(id).subscribe(results => {
        this.quarto = results;
      });
    }else{
      alert("Quarto não encontrado");
    }
  }

  mudaCheckOut(newDate){
    if(this.compareDatesToToday(newDate)){
      if(this.compareDates(newDate,this.checkIn)){
        this.checkOut = newDate;
      }
    }
  }

  mudaCheckIn(newDate){
    if(this.compareDatesToToday(newDate)){
        this.checkIn = newDate;
    }
  }

  reservar(reservaData){
    this.compareTwoDates();
    if(this.datasValidas){
      localStorage.setItem('checkInR',(reservaData.checkInReserva));
      localStorage.setItem('checkOutR',(reservaData.checkOutReserva));
      this.reservaForm.reset();
      window.location.href = 'hoteisPSI/' + sessionStorage.getItem('hotelNome') + '/reservar';
    }else{
     alert("teste");
    }
  }
  
  compareTwoDates(){
    if(new Date(this.reservaForm.controls['checkOutReserva'].value)<new Date(this.reservaForm.controls['checkInReserva'].value)){
       this.datasValidas = false;
    }else{
      this.datasValidas = true;
    }
  }

  //ve se data1 e menor q data2 
  //se data1 for menor entao da false
  compareDates(date1, date2){
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

}
