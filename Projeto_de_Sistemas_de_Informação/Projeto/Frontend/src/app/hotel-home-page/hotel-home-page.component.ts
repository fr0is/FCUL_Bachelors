import { Component, OnInit, HostListener } from '@angular/core';
import { HotelService } from '../hotel.service';
import { Hotel } from 'src/hotel';
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-hotel-home-page',
  templateUrl: './hotel-home-page.component.html',
  styleUrls: ['./hotel-home-page.component.css']
})
export class HotelHomePageComponent implements OnInit {

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
  public innerWidth: any;

constructor(
  private route: ActivatedRoute,
  private hotelService: HotelService
) { 
}

ngOnInit(): void {
  this.getHotel();
  this.innerWidth = window.innerWidth;
}

@HostListener('window:resize', ['$event'])
onResize(event) {
  this.innerWidth = window.innerWidth;
}

getHotel() {
  const id = sessionStorage.getItem("hotelAtual");
  if (id) {
    this.hotelService.getHotel(id).subscribe(results => {
      this.hotel = results;
    });
  }
}


}
