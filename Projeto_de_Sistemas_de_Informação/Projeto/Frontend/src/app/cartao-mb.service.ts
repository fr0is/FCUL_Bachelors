import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CartaoMB } from 'src/cartaoMB';

@Injectable({
  providedIn: 'root'
})
export class CartaoMBService {

  //appserver
  //baseUrl = 'http://appserver.alunos.di.fc.ul.pt:3071/catalog/';
  //locahost
  baseUrl = 'http://localhost:3071/catalog/';
  usersUrl = this.baseUrl + 'users/';
  createUrl = this.usersUrl + 'cartao/create';
  updateUrl = this.usersUrl + 'update';

  constructor(private http: HttpClient) { }

  createCartao(cartao: CartaoMB) {
    return this.http.post<{ message: string }>(this.createUrl, cartao);
  }

  getCartao(id){
    const url = this.usersUrl + 'reserva/' + id;
    return this.http.get<CartaoMB>(url);
  }

  getCartaoEmail(email){
    const url = this.usersUrl + email + '/cartoes';
    return this.http.get(url);
  }

  apagarCartao(cartaoMB: CartaoMB){
    const url = this.usersUrl + 'cartao/delete';
    return this.http.post<{message: string}>(url, cartaoMB);
  }

}