import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { BookInstance } from "src/bookinstance";

@Injectable({
  providedIn: "root"
})
export class BookinstanceService {
  baseUrl = "http://localhost:3000/catalog/";
  bookinstanceUrl = this.baseUrl + "bookinstance/";
  bookinstancesUrl = this.baseUrl + "bookinstances/";
  countUrl = this.bookinstancesUrl + "count/";
  availableUrl = this.countUrl + "available";
  deleteUrl = this.bookinstanceUrl + "delete/";
  updateUrl = this.bookinstanceUrl + "update/";
  createUrl = this.bookinstanceUrl + "create/";
  instancesofbookUrl = this.bookinstanceUrl + "book/";

  constructor(private http: HttpClient) {}

  getBookInstanceCount() {
    return this.http.get(this.countUrl);
  }

  getBookInstanceAvailableCount() {
    return this.http.get(this.availableUrl);
  }

  getBookInstancesOfBook(book: string) {
    const url = this.instancesofbookUrl + book;
    return this.http.get<BookInstance[]>(url);
  }

  getBookInstances() {
    return this.http.get(this.bookinstancesUrl);
  }

  getBookInstance(id: string) {
    const url = this.bookinstanceUrl + id;
    return this.http.get<BookInstance>(url);
  }

  deleteBookInstance(bookInstance: BookInstance) {
    return this.http.post<{ message: string }>(this.deleteUrl, bookInstance);
  }

  updateBookInstance(bookInstance: BookInstance) {
    return this.http.post<{ message: string }>(this.updateUrl, bookInstance);
  }

  createBookInstance(bookInstance: BookInstance) {
    return this.http.post<{ message: string }>(this.createUrl, bookInstance);
  }
}
