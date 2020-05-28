import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Book } from "src/book";

@Injectable({
  providedIn: "root"
})
export class BookService {
  baseUrl = "http://localhost:3000/catalog/";
  bookUrl = this.baseUrl + "book/";
  booksUrl = this.baseUrl + "books/";
  countUrl = this.booksUrl + "count/";
  deleteUrl = this.bookUrl + "delete/";
  updateUrl = this.bookUrl + "update/";
  createUrl = this.bookUrl + "create/";

  constructor(private http: HttpClient) {}

  getBookCount() {
    return this.http.get(this.countUrl);
  }

  getBooks() {
    return this.http.get(this.booksUrl);
  }

  getBook(id: string) {
    const url = this.bookUrl + id;
    return this.http.get<Book>(url);
  }

  deleteBook(book: Book) {
    return this.http.post<{ message: string }>(this.deleteUrl, book);
  }

  updateBook(book: Book) {
    return this.http.post<{ message: string }>(this.updateUrl, book);
  }

  createBook(book: Book) {
    return this.http.post<{ message: string }>(this.createUrl, book);
  }
}