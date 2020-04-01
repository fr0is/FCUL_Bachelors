import { Injectable, Inject } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { BookInstance } from './book-instance';

@Injectable({
  providedIn: 'root'
})

export class BookInstanceService {
 
  private backendUrl = 'http://localhost:3000/catalog/bookinstance';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient) { }

  /** GET authors from the server */
  getBookInstances(): Observable<BookInstance[]> {
    return this.http.get<BookInstance[]>(this.backendUrl+"s")
      .pipe(
        tap(_ => console.log('Teste')),
        catchError(this.handleError<BookInstance[]>('getBookInstances', []))
      );
  }

  /** GET authors from the server */
  getBookInstanceDetails(id): Observable<BookInstance> {
    return this.http.get<BookInstance>(this.backendUrl+"/"+id)
      .pipe(
        tap(_ => console.log('Book Instance Details')),
        catchError(this.handleError<BookInstance>('getBookInstanceDetails', ))
      );
  }

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
