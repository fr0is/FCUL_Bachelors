import { Component, OnInit } from '@angular/core';

import { Quarto } from '../../quarto';
import { HotelService } from '../hotel.service';

@Component({
  selector: 'app-quartos-home-page',
  templateUrl: './quartos-home-page.component.html',
  styleUrls: ['./quartos-home-page.component.css']
})
export class QuartosHomePageComponent implements OnInit {

  quartos: Quarto[] = [];
  selectedQuarto = "Standard";

  constructor(private hotelService: HotelService) { }

  ngOnInit(): void {
   this.showQuartos();
  }

  hotelId=this.hotelService.getHotelId();


  mudaQuarto (quarto: any) {
    this.selectedQuarto = quarto;
    console.log(this.selectedQuarto);
  }

  showQuartos() {
    this.hotelService.getHotelQuartos(sessionStorage.getItem("hotelAtual")).subscribe(quartoList => {
      this.quartos = quartoList as Quarto[];
    });
  }
}