import { Component, OnInit } from '@angular/core';
import { HotelService } from '../hotel.service';
import { Hotel } from 'src/hotel';
import { ActivatedRoute } from "@angular/router";
import { UserService } from '../user.service';
import { User } from 'src/user';

@Component({
  selector: 'app-top-bar-hotel',
  templateUrl: './top-bar-hotel.component.html',
  styleUrls: ['../../app/top-bar-hotel/top-bar-hotel.component.css']
})
export class TopBarHotelComponent implements OnInit {

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

    cliente = "";

    userAtual: User = {
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
    };

  constructor(
    private route: ActivatedRoute,
    private hotelService: HotelService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.getHotel();
    this.getUserAtual();
  }

  getUserAtual(){
    this.cliente = localStorage.getItem('cliente');
    this.userService.getUser(localStorage.getItem('userAtual')).subscribe(user => {
      this.userAtual = user[0];
    }); 
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
