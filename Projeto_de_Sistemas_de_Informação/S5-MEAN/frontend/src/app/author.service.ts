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
        tap(_ => console.log('Get Authors')),
        catchError(this.handleError<Author[]>('getAuthors', []))
      );
  }

  /** GET authorDetails from the server */
  getAuthorDetails (id): Observable<Author> {
    return this.http.get<Author>(this.backendUrl+"/"+id)
      .pipe(
        tap(_ => console.log('Get Author Details')),
        catchError(this.handleError<Author>('getAuthorDetails', ))
      );
  }

   /** GET authorDetails from the server */
   authorDelete(id): Observable<Author> {
    return this.http.get<Author>(this.backendUrl+"-delete")
      .pipe(
        tap(_ => console.log('Delete Author')),
        catchError(this.handleError<Author>('getAuthorDetails', ))
      );
  }

  authorCreateStart(): Observable<Author> {
    const url = `${this.backendUrl}/create`;
    return this.http.get<Author>(url)
      .pipe(
        tap(_ => console.log('Author Create')),
        catchError(this.handleError<Author>('getAuthorCreate', ))
      );
  }

  authorCreate(author): Observable<Author> {
    const url = `${this.backendUrl}/create`;
    return this.http.post<Author>(url,author,this.httpOptions)
      .pipe(
        tap(_ => console.log('Author Create')),
        catchError(this.handleError<Author>('postAuthorCreate', ))
      );
  }
  
  updateAuthorStart(id): Observable<Author> {
    const url = `${this.backendUrl}/`+id+`/update`;
    return this.http.get<Author>(url)
      .pipe(
        tap(_ => console.log('Author Update')),
        catchError(this.handleError<Author>('getAuthorUpdate', ))
      );
  }

  updateAuthor(id,author): Observable<Author> {
    const url = `${this.backendUrl}/`+id+`/update`;
    return this.http.post<Author>(url,author,this.httpOptions)
      .pipe(
        tap(_ => console.log('Author Update')),
        catchError(this.handleError<Author>('postAuthorUpdate', ))
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
