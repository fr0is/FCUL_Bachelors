import { GenreService } from './genre.service';
import { BookinstanceService } from "./bookinstance.service";
import { BookService } from "./book.service";
import { Component, OnInit } from "@angular/core";
import { AuthorService } from "./author.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"]
})
export class AppComponent implements OnInit {
  title = "Local Library";

  authorCount = 0;
  bookCount = 0;
  instanceCount = 0;
  availableCount = 0;
  genreCount = 0;

  constructor(
    private authorService: AuthorService,
    private bookService: BookService,
    private bookinstanceService: BookinstanceService,
    private genreService: GenreService
  ) {}

  ngOnInit(): void {
    this.getBookCount();
    this.getAuthorCount();
    this.getBookInstanceCount();
    this.getBookInstanceAvailableCount();
    this.getGenreCount();
  }

  getAuthorCount() {
    this.authorService.getAuthorCount().subscribe(count => {
      this.authorCount = count as number;
    });
  }

  getBookCount() {
    this.bookService.getBookCount().subscribe(count => {
      this.bookCount = count as number;
    });
  }

  getBookInstanceCount() {
    this.bookinstanceService.getBookInstanceCount().subscribe(count => {
      this.instanceCount = count as number;
    });
  }

  getBookInstanceAvailableCount() {
    this.bookinstanceService.getBookInstanceAvailableCount().subscribe(count => {
      this.availableCount = count as number;
    });
  }

  getGenreCount() {
    this.genreService.getGenreCount().subscribe(count => {
      this.genreCount = count as number;
    })
  }

}
