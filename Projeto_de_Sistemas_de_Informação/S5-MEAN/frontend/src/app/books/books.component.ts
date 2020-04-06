import { AuthorService } from "./../author.service";
import { BookService } from "./../book.service";
import { Book } from "src/book";
import { Component, OnInit } from "@angular/core";
import { Author } from "src/author";

@Component({
  selector: "app-books",
  templateUrl: "./books.component.html",
  styleUrls: ["./books.component.css"]
})
export class BooksComponent implements OnInit {
  books: BookDisplay[] = [];

  constructor(
    private bookService: BookService,
    private authorService: AuthorService
  ) {}

  ngOnInit(): void {
    this.showBooks();
  }

  showBooks() {
    this.bookService.getBooks().subscribe(bookList => {
      const bookA = bookList as Book[];
      bookA.map((bookL) => {
        this.authorService.getAuthor(bookL.author).subscribe(author => {
          const bookD = new BookDisplay(bookL, author);
          this.books.push(bookD);
        });
      });
    });
  }
}

class BookDisplay {
  book: Book;
  author: Author;

  constructor(b: Book, a: Author) {
    this.book = b;
    this.author = a;
  }
}
