import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/user';
import { Morada } from 'src/morada';
import { Reserva } from 'src/reserva';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  //appserver
  //baseUrl = 'http://appserver.alunos.di.fc.ul.pt:3071/catalog/';
  //locahost
  baseUrl = 'http://localhost:3071/catalog/';
  usersUrl = this.baseUrl + 'users/';
  createUrl = this.usersUrl + 'create';
  updateUrl = this.usersUrl + 'update';
  moradaUrl = this.baseUrl + 'morada/';
  reservaUrl = this.baseUrl + 'reserva/';

  user: User;

  constructor(private http: HttpClient) { }

  createUser(user: User) {
    return this.http.post<{ message: string }>(this.createUrl, user);
  }

  getUser(email){
    const url = this.usersUrl + email;
    return this.http.get<User>(url);
  }

  updateUser(user) {
    return this.http.post<{message: string}>(this.updateUrl, user);
  }

  createMorada(morada: Morada) {
    const createUrl = this.moradaUrl + 'create';
    return this.http.post<{ message: string }>(createUrl, morada);
  }

  getUserMoradas(email){
    const url = this.moradaUrl + email;
    return this.http.get(url);
  }

  getMorada(id){
    const url = this.moradaUrl +'getOne/' + id;
    console.log(url);
    return this.http.get<Morada>(url);
  }

  apagarMorada(morada: Morada){
    const url = this.moradaUrl + 'delete';
    return this.http.post<{message: string}>(url, morada);
  }

  getReservaId(id){
    const url = this.reservaUrl + id;
    return this.http.get(url);
  }

  createReserva(reserva: Reserva){
    const url = this.reservaUrl + 'create';
    return this.http.post<{message: string}>(url, reserva);
  }

  updateReserva(reserva: Reserva){
    const url = this.reservaUrl + 'update';
    return this.http.post<{message: string}>(url, reserva);
  }

  getUserReservas(email){
    const url = this.reservaUrl +'email/' + email;
    return this.http.get(url);
  }

}