import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Author } from 'src/author';
import { Book } from 'src/book';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  baseUrl = 'http://localhost:3000/catalog/';
  authorUrl = this.baseUrl + 'author/';
  authorsUrl = this.baseUrl + 'authors/';
  countUrl = this.authorsUrl + 'count/';
  deleteUrl = this.authorUrl + 'delete/';
  updateUrl = this.authorUrl + 'update/';
  createUrl = this.authorUrl + 'create/';

  constructor(private http: HttpClient) { }

  getAuthorCount() {
    return this.http.get(this.countUrl);
  }

  getAuthors() {
    return this.http.get(this.authorsUrl);
  }

  getAuthor(id: string) {
    const url = this.authorUrl + id;
    return this.http.get<Author>(url);
  }

  getAuthorBooks(id: string) {
    const url = this.authorUrl + id + "/books";
    return this.http.get<Book[]>(url);
  }

  deleteAuthor(author: Author) {
    return this.http.post<{message: string}>(this.deleteUrl, author);
  }

  updateAuthor(author: Author) {
    return this.http.post<{message: string}>(this.updateUrl, author);
  }

  createAuthor(author: Author) {
    return this.http.post<{message: string}>(this.createUrl, author);
  }

}
