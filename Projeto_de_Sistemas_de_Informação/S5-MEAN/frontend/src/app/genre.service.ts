import { Injectable, Inject } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Genre } from './genre';

@Injectable({
  providedIn: 'root'
})

export class GenreService {

  private backendUrl = 'http://localhost:3000/catalog/genre';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient) { }

  /** GET authors from the server */
  getGenres (): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.backendUrl+"s")
      .pipe(
        tap(_ => console.log('Teste')),
        catchError(this.handleError<Genre[]>('getGenre', []))
      );
  }

    /** GET authors from the server */
    getGenreDetails(id): Observable<Genre> {
      return this.http.get<Genre>(this.backendUrl+"/"+id)
        .pipe(
          tap(_ => console.log('Genre Details')),
          catchError(this.handleError<Genre>('getGenreDetails', ))
        );
    }

    genreCreateStart(): Observable<Genre> {
      const url = '${this.backendUrl}/create';
      return this.http.get<Genre>(url)
        .pipe(
          tap(_ => console.log('Genre Create')),
          catchError(this.handleError<Genre>('getGenreCreate', ))
        );
    }

    genreCreate(genre): Observable<Genre> {
      const url = `${this.backendUrl}/create`;;
      return this.http.post<Genre>(url,genre,this.httpOptions)
        .pipe(
          tap(_ => console.log('Genre Create')),
          catchError(this.handleError<Genre>('postGenreCreate', ))
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
