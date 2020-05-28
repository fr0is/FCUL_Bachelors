import { Quarto } from './quarto';
import { Reserva } from './reserva';

export interface QuartoInstance{
    _id: String,
    numeroQuarto: Number,
    quarto: string,
    reservas: Reserva[]
}