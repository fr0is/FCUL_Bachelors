import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HotelService } from '../hotel.service';
import { Hotel } from 'src/hotel';

@Component({
  selector: 'app-galeria',
  templateUrl: './galeria.component.html',
  styleUrls: ['./galeria.component.css']
})
export class GaleriaComponent implements OnInit {
  hotel: Hotel = {
    _id: "",
    nome: "",
    descricao: [],
    local:  "",
    zona:  "",
    codigoPostal: "",
    pais: "",
    latitude: "",
    longitude: "",
    codigoRegiao: "",
    telefone: "",
    email: "",
    servicos: [],
    fotos: [],
    fotoPath: ""
  };

  foto="";
  isShow = false;
  hotelId="";

  constructor(
    private route: ActivatedRoute,
    private hotelService: HotelService
    ) {}

  ngOnInit(): void {
    this.getHotel();
  }

  getHotel() {
    this.hotelService.getHotel(sessionStorage.getItem("hotelAtual")).subscribe(results => {
      this.hotel = results;
    })
  }

  show(foto){
    this.isShow = true;
    this.foto = foto;
  }

  closeFoto(){
    this.isShow = false;
  }

  goTo(i){
    window.location.href = '/hoteisPSI/'+localStorage.getItem('hotelNome')+'/galeria#lightbox-'+i;
  }

}

