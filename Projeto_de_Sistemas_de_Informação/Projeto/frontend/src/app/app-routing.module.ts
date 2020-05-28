import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomePageComponent} from './home-page/home-page.component';
import { HotelComponent } from './hotel/hotel.component';
import { HotelHomePageComponent } from './hotel-home-page/hotel-home-page.component';
import { QuartosPesquisaComponent } from './quartos-pesquisa/quartos-pesquisa.component';
import { QuartoPaginaDetalhesComponent } from './quarto-pagina-detalhes/quarto-pagina-detalhes.component';
import { GaleriaComponent } from './galeria/galeria.component';
import { QuartosServicosComponent } from './quartos-servicos/quartos-servicos.component';
import { LoginRegistoComponent } from './login-registo/login-registo.component';
import { ClienteComponent } from './cliente/cliente.component';
import { ClienteDadosComponent } from './cliente-dados/cliente-dados.component';
import { ClienteReservasComponent } from './cliente-reservas/cliente-reservas.component';
import { ReservaQuartoComponent } from './reserva-quarto/reserva-quarto.component';


const routes: Routes = [
  { path: '', redirectTo: '/hoteisPSI', pathMatch: 'full' },
  { path: 'hoteisPSI', component: HomePageComponent },
  { path: 'hoteisPSI/login-registo', component: LoginRegistoComponent},
  { path: 'hoteisPSI/:nomeHotel', component: HotelComponent,
  children: [
    {
      path: '', // child route path
      component: HotelHomePageComponent // child route component that the router renders
    },
    {
      path: 'galeria', // child route path
      component: GaleriaComponent // child route component that the router renders
    },
    {
      path: 'quartos', // child route path
      component: QuartosPesquisaComponent // child route component that the router renders
    },
    {
    path: 'quartos/servicos', // child route path
    component: QuartosServicosComponent // child route component that the router renders
    },
    {
      path: 'quartos/:id', // child route path
      component: QuartoPaginaDetalhesComponent // child route component that the router renders
    },
    {
      path: 'cliente',
      component: ClienteComponent, 
      children: [
        {
          path: '',
          component: ClienteDadosComponent
        },
        {
          path: 'reservas',
          component: ClienteReservasComponent
        }
      ]
    },
    {
      path: 'reservar',
      component: ReservaQuartoComponent
    },
  ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
