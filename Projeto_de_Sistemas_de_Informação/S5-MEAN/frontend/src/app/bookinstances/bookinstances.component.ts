import { Component, OnInit } from "@angular/core";
import { BookinstanceService } from "../bookinstance.service";
import { BookService } from "../book.service";
import { BookInstance } from 'src/bookinstance';
import { Book } from 'src/book';

@Component({
  selector: "app-bookinstances",
  templateUrl: "./bookinstances.component.html",
  styleUrls: ["./bookinstances.component.css"]
})
export class BookinstancesComponent implements OnInit {

  copies: BookInstanceDisplay[] = [];

  constructor(
    private bookinstanceService: BookinstanceService,
    private bookService: BookService
  ) {}

  ngOnInit(): void {
    this.showBookInstances();
  }

  showBookInstances() {
    this.bookinstanceService.getBookInstances().subscribe(copiesList => {
      const copiesA = copiesList as BookInstance[];
      copiesA.map(copy => {
        this.bookService.getBook(copy.book).subscribe(book => {
          const copyD = new BookInstanceDisplay(copy, book);
          this.copies.push(copyD);
        });
      });
    });
  }
}

class BookInstanceDisplay {
  bookInstance: BookInstance;
  book: Book;

  constructor(bi: BookInstance, b: Book) {
    this.bookInstance = bi;
    this.book = b;
  }

}