import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Hero } from './hero';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const heroes = [
      { id: 11, name: 'Dr Nice', pet: null },
      { id: 12, name: 'Narco', pet: null },
      { id: 13, name: 'Bombasto', pet: null },
      { id: 14, name: 'Celeritas', pet: null },
      { id: 15, name: 'Magneta', pet: null },
      { id: 16, name: 'RubberMan', pet: null },
      { id: 17, name: 'Dynama', pet: null },
      { id: 18, name: 'Dr IQ', pet: null },
      { id: 19, name: 'Magma' , pet: null},
      { id: 20, name: 'Tornado' , pet: null}
    ];

    const pets = [
      {id:1, name: "capitao cuecas"},
      {id:2, name: "camiao"},
      {id:3, name: "covid-20"}
    ]

    return {heroes,pets};
  }

  // Overrides the genId method to ensure that a hero always has an id.
  // If the heroes array is empty,
  // the method below returns the initial number (11).
  // if the heroes array is not empty, the method below returns the highest
  // hero id + 1.
  genId(heroes: Hero[]): number {
    return heroes.length > 0 ? Math.max(...heroes.map(hero => hero.id)) + 1 : 11;
  }
}