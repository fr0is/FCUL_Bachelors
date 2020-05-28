export interface Hotel {
    _id: string;
    nome: string;
    descricao: string[];
    //morada
    local:  string;
    zona:  string;
    codigoPostal: string;
    pais: string;
    //coordenadas
    latitude: string;
    longitude: string;
    //contactos
    codigoRegiao: string;
    telefone: string;
    email: string;
    //servicos
    servicos: string[];
    fotos: string[];
    fotoPath: string;
  }