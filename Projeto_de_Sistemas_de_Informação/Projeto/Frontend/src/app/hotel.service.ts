import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Hotel } from 'src/hotel';
import { Quarto } from 'src/quarto';

@Injectable({
  providedIn: 'root'
})

export class HotelService {

  //appserver
  //baseUrl = 'http://appserver.alunos.di.fc.ul.pt:3071/catalog/';
  //locahost
  baseUrl = 'http://localhost:3071/catalog/';
  hoteisUrl = this.baseUrl + 'hoteis/';
  hotelUrl = this.baseUrl + 'hoteis/';
  quartosUrl = this.baseUrl + 'hoteis/' + sessionStorage.getItem('hotelAtual') + '/quartos/';
  HotelId="";
  HotelNome ="";

  constructor(private http: HttpClient) { }

  getHotelId(){
    return this.HotelId;
  }

  getHotelNome(){
    return this.HotelNome;
  }

  getHoteis() {
    return this.http.get(this.hoteisUrl);
  }

  getHotel(id: string) {
    const url = this.hotelUrl + id;
    return this.http.get<Hotel>(url);
  }

  getHotelQuartos(id: string) {
    const url = this.hotelUrl + id +"/quartos";
    return this.http.get(url);
  }

  getHotelQuarto(id: string) {
    const url = this.quartosUrl + id;
    return this.http.get<Quarto>(url);
  }
}