import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class QuartoService {

  //appserver
  //baseUrl = 'http://appserver.alunos.di.fc.ul.pt:3071/catalog/';
  //locahost
  baseUrl = 'http://localhost:3071/catalog/';
  quartosUrl = this.baseUrl + 'quartos';
  instanceUrl = this.baseUrl + 'quartoInstance/';

  constructor(private http: HttpClient) { }

  getQuartos() {
    return this.http.get(this.quartosUrl);
  }

  getInstances(id){
    const url = this.instanceUrl + id;
    console.log(url);
    return this.http.get(url);
  }

}