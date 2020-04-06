import { Book } from 'src/book';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Genre } from 'src/genre';

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  baseUrl = 'http://localhost:3000/catalog/';
  genreUrl = this.baseUrl + 'genre/';
  genresUrl = this.baseUrl + 'genres/';
  countUrl = this.genresUrl + 'count/';
  deleteUrl = this.genreUrl + 'delete/';
  updateUrl = this.genreUrl + 'update/';
  createUrl = this.genreUrl + 'create/';

  constructor(private http: HttpClient) { }

  getGenreCount() {
    return this.http.get(this.countUrl);
  }

  getGenres() {
    return this.http.get(this.genresUrl);
  }

  getGenre(id: string) {
    const url = this.genreUrl + id;
    return this.http.get<Genre>(url);
  }

  getGenreBooks(id: string) {
    const url = this.genreUrl + id + "/books";
    return this.http.get<Book[]>(url);
  }

  deleteGenre(genre: Genre) {
    return this.http.post<{message: string}>(this.deleteUrl, genre);
  }

  updateGenre(genre: Genre) {
    return this.http.post<{message: string}>(this.updateUrl, genre);
  }

  createGenre(genre: Genre) {
    return this.http.post<{message: string}>(this.createUrl, genre);
  }

}
