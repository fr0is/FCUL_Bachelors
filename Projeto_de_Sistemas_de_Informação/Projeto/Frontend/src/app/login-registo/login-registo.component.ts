import { Component, OnInit } from '@angular/core';
import { HostListener } from "@angular/core";
import { FormGroup, FormControl, FormArray, FormBuilder } from "@angular/forms";

import { User } from 'src/user';
import { UserService } from '../user.service';
import { HotelService } from '../hotel.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-registo',
  templateUrl: './login-registo.component.html',
  styleUrls: ['./login-registo.component.css','./login-registo-telemovel.css']
})
export class LoginRegistoComponent implements OnInit {

  screenWidth: number;
  registoForm: FormGroup;
  loginForm: FormGroup;
  userR: User = {
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
  errorMessage = "";

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    public router: Router
  ) {
      this.getScreenSize();
      this.registoForm = this.formBuilder.group({
        nomeRegisto: this.formBuilder.control(""),
        emailRegisto: this.formBuilder.control(""),
        passwordRegisto: this.formBuilder.control("")
      })
      this.loginForm = this.formBuilder.group({
        emailLogin: this.formBuilder.control(""),
        passwordLogin: this.formBuilder.control("")
      })
  }

  ngOnInit(){};

  @HostListener('window:resize', ['$event'])
  getScreenSize(event?) {
        this.screenWidth = window.innerWidth;
        console.log(this.screenWidth);
  }

  changeForm(){
    document.querySelector('.cont').classList.toggle('s--signup');
  }

  create(registoData) {
    this.userR.nome = registoData.nomeRegisto;
    this.userR.email = registoData.emailRegisto;
    this.userR.password = registoData.passwordRegisto;
    this.registoForm.reset();
  
    this.userService.getUser(this.userR.email).subscribe(user => {
      if(!user[0]){
        this.userService.createUser(this.userR).subscribe(result => {
          this.errorMessage = result.message;
          alert("User registado!")
        });
      }else{
        alert("Email em Uso");
      }
    });
  }

  login(login){
    this.userR.nome = "";
    this.userR.email = login.emailLogin;
    this.userR.password = login.passwordLogin;
    this.loginForm.reset();
  
    this.userService.getUser(this.userR.email).subscribe(user => {
      if(!user[0]){
        alert("Email Inválido");
      }else{
        if(user[0].password !== this.userR.password){
          alert("Password Inválida");
        }else{
          localStorage.setItem('userAtual',user[0].email);
          localStorage.removeItem('cliente');
          localStorage.setItem('cliente','t');
          this.router.navigate(['hoteisPSI/',sessionStorage.getItem('hotelNome')]);
        }
      }
    });
  } 
}