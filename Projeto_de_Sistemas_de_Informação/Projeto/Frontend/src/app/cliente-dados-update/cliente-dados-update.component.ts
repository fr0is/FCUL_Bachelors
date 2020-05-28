import { Component, OnInit } from '@angular/core';
import { User } from 'src/user';
import { FormGroup, FormBuilder } from '@angular/forms';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cliente-dados-update',
  templateUrl: './cliente-dados-update.component.html',
  styleUrls: ['./cliente-dados-update.component.css']
})
export class ClienteDadosUpdateComponent implements OnInit {


  cliente: User;
  updateForm: FormGroup;
  clienteUpdate: User = {
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
  }
  errorMessage = "";
  show = false;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    public router: Router
  ) {}

  ngOnInit(): void {
    this.getCliente();
  }

  password(){
    this.show = !this.show;
    console.log(this.show);
  }

  getCliente(){
    this.userService.getUser(localStorage.getItem('userAtual')).subscribe(user => {
      this.cliente = user[0];
      this.updateForm = this.formBuilder.group({
        nomeUpdate: this.formBuilder.control(this.cliente.nome),
        emailUpdate: this.formBuilder.control(this.cliente.email),
        indicativoUpdate: this.formBuilder.control(this.cliente.indicativo),
        telefoneUpdate: this.formBuilder.control(this.cliente.telefone),
        passwordUpdate: this.formBuilder.control(this.cliente.password),
        nifUpdate: this.formBuilder.control(this.cliente.nif),
      })
    }); 
  }


  updateCliente(updateData){
    console.log("iniciou update")
    //Update Form Data  
    this.clienteUpdate.nome = updateData.nomeUpdate;
    this.clienteUpdate.email = updateData.emailUpdate;
    this.clienteUpdate.indicativo = updateData.indicativoUpdate;
    this.clienteUpdate.telefone = updateData.telefoneUpdate;
    this.clienteUpdate.password = updateData.passwordUpdate;
    this.clienteUpdate.nif = updateData.nifUpdate;
    //Data que nao muda
    this.clienteUpdate._id = this.cliente._id;
    localStorage.setItem('userAtual', updateData.emailUpdate);
    this.updateForm.reset();

    this.userService.updateUser(this.clienteUpdate).subscribe(result => {
      this.errorMessage = result.message;
      window.location.href = 'hoteisPSI/' + sessionStorage.getItem('hotelNome')+'/cliente';
    });
  }
  
}