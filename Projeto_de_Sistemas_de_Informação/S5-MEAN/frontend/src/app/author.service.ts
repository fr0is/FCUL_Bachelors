import { Injectable, Inject } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Author } from './author';

@Injectable({
  providedIn: 'root'
})

export class AuthorService {

  private backendUrl = 'http://localhost:3000/catalog/author';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient) { }

  /** GET authors from the server */
  getAuthors (): Observable<Author[]> {
    return this.http.get<Author[]>(this.backendUrl+"s")
      .pipe(
        tap(_ => console.log('Teste')),
        catchError(this.handleError<Author[]>('getAuthors', []))
      );
  }

  /** GET authorDetails from the server */
  getAuthorDetails (): Observable<Author> {
    return this.http.get<Author>(this.backendUrl+"detail/:id")
      .pipe(
        tap(_ => console.log('Teste')),
        catchError(this.handleError<Author>('getAuthorDetails', ))
      );
  }
  
  /**addPet (author: Author): Observable<Author> {
    return this.http.post<Author>(this.backendUrl, author, this.httpOptions).pipe(
      tap((newAuthor: Author) => console.log(`added author w/ name=${author.first_name}`)),
      catchError(this.handleError<Author>('addAuthor'))
    );
  }*/

 /**  deletePet (pet: Pet | number): Observable<Pet> {
    const id = typeof pet === 'number' ? pet : pet.id;
    const url = `${this.petsUrl}/${id}`;

    return this.http.delete<Pet>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted pet id=${id}`)),
      catchError(this.handleError<Pet>('deletePet'))
    );
  }*/

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
