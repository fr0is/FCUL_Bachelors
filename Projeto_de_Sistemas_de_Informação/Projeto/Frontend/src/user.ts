export interface User {
    _id: string;
    nome: string;
    email: string;
    password: string;
    indicativo: string;
    telefone: string;
    nif: string;
    morada: [];
    cartaoMB: [];
    reservas: [];
  }