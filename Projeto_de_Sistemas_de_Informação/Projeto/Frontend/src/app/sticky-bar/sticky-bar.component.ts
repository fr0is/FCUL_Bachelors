import { Component, OnInit, ViewChild, ElementRef, HostListener } from '@angular/core';
import { HotelService } from '../hotel.service';
import { Quarto } from 'src/quarto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sticky-bar',
  templateUrl: './sticky-bar.component.html',
  styleUrls: ['./sticky-bar.component.css']
})

export class StickyBarComponent implements OnInit {

  @ViewChild('stickyMenu') menuElement: ElementRef;

  sticky: boolean = false;
  elementPosition: any;
  quartos: Quarto[] = [];
  totalQuartos = 0;
  precoMaisBaixo = 500;


  constructor(  private hotelService: HotelService ) { }

 
  ngOnInit(): void {
    this.showQuartos();
   }

   ngAfterViewInit(){
    this.elementPosition = this.menuElement.nativeElement.offsetTop;
  }
 
  @HostListener('window:scroll', ['$event'])
    handleScroll(){
      const windowScroll = window.pageYOffset;
      if(windowScroll >= this.elementPosition){
        this.sticky = true;
      } else {
        this.sticky = false;
      }
    }
 
   showQuartos() {
     this.hotelService.getHotelQuartos(sessionStorage.getItem("hotelAtual")).subscribe(quartoList => {
       this.quartos = quartoList as Quarto[];
     });
   }

   countQuarto(item: Quarto){
    this.totalQuartos += item.nrQuartos;
    if(this.precoMaisBaixo > item.precoBaixa){
      this.precoMaisBaixo = item.precoBaixa;
    }
 }

}
