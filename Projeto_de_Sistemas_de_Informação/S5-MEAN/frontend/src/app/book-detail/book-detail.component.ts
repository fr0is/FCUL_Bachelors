import { BookinstanceService } from "./../bookinstance.service";
import { Location } from "@angular/common";
import { FormGroup, FormControl, FormArray, FormBuilder } from "@angular/forms";
import { GenreService } from "./../genre.service";
import { AuthorService } from "./../author.service";
import { BookService } from "./../book.service";
import { Component, OnInit } from "@angular/core";
import { Book } from "src/book";
import { Author } from "src/author";
import { Genre } from "src/genre";
import { ActivatedRoute } from "@angular/router";
import { BookInstance } from "src/bookinstance";

@Component({
  selector: "app-book-detail",
  templateUrl: "./book-detail.component.html",
  styleUrls: ["./book-detail.component.css"]
})
export class BookDetailComponent implements OnInit {
  book: Book = {
    _id: "",
    title: "",
    summary: "",
    author: "",
    isbn: "",
    genre: []
  };
  bookAuthor: Author = {
    _id: "",
    first_name: "",
    family_name: "",
    date_of_birth: new Date(),
    date_of_death: new Date()
  };
  authors: Author[] = [];
  bookGenres: Genre[] = [];
  genres: Genre[] = [];
  copies: BookInstance[] = [];

  errorMessage = "";
  updateOrCreate = false;
  buttonLabel = "Update";

  bookForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private bookService: BookService,
    private authorService: AuthorService,
    private genreService: GenreService,
    private bookinstanceService: BookinstanceService,
    private formBuilder: FormBuilder,
    private location: Location
  ) {
    this.bookForm = this.formBuilder.group({
      title: this.formBuilder.control(""),
      summary: this.formBuilder.control(""),
      author: this.formBuilder.control(this.authors),
      isbn: this.formBuilder.control(""),
      genresF: this.formBuilder.array([]),
      _id: this.formBuilder.control("")
    });
  }

  ngOnInit(): void {
    this.getBook();
    this.getCopies();
  }

  get genresF(): FormArray {
    return this.bookForm.get("genresF") as FormArray;
  }

  getBook() {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.updateOrCreate = false;
      this.bookService.getBook(id).subscribe(book => {
        this.book = book;
        this.authorService.getAuthors().subscribe(authorList => {
          this.authors = authorList as Author[];
          this.bookAuthor = this.authors.filter(
            a => a._id === this.book.author
          )[0];
        });
        this.genreService.getGenres().subscribe(genreList => {
          this.genres = genreList as Genre[];
          this.bookGenres = this.genres.filter(g =>
            this.book.genre.includes(g._id)
          );

          this.bookForm = this.formBuilder.group({
            title: this.formBuilder.control(""),
            summary: this.formBuilder.control(""),
            author: this.formBuilder.control(this.authors),
            isbn: this.formBuilder.control(""),
            genresF: this.formBuilder.array([]),
            _id: this.formBuilder.control("")
          });

          this.genres.forEach((g, i) => {
            const control = new FormControl(false);
            (this.bookForm.controls.genresF as FormArray).push(control);
          });
        });
      });
    } else {
      this.authorService.getAuthors().subscribe(authorList => {
        this.authors = authorList as Author[];
        this.genreService.getGenres().subscribe(genreList => {
          this.genres = genreList as Genre[];
          this.bookForm = this.formBuilder.group({
            title: this.formBuilder.control(""),
            summary: this.formBuilder.control(""),
            author: this.formBuilder.control(this.authors),
            isbn: this.formBuilder.control(""),
            genresF: this.formBuilder.array([]),
            _id: this.formBuilder.control("")
          });

          this.genres.forEach((g, i) => {
            const control = new FormControl(false);
            (this.bookForm.controls.genresF as FormArray).push(control);
          });
        });
        this.updateOrCreate = true;
        this.buttonLabel = "Create";
      });
    }
  }

  getCopies() {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.bookinstanceService
        .getBookInstancesOfBook(id)
        .subscribe(instancesList => {
          this.copies = instancesList;
        });
    }
  }

  deleteBook() {
    this.bookService.deleteBook(this.book).subscribe(result => {
      if (result.message === "success") {
        this.location.back();
      } else {
        this.errorMessage = result.message;
      }
    });
  }

  updateBook() {
    this.bookForm.patchValue({
      title: this.book.title,
      summary: this.book.summary,
      isbn: this.book.isbn,
      _id: this.book._id
    });
    this.bookForm.controls.author.setValue(this.bookAuthor._id);
    this.genresF.controls.map((c, i) => {
      if (this.bookGenres.includes(this.genres[i])) {
        c.setValue(true);
      } else {
        c.setValue(false);
      }
    });
    this.updateOrCreate = true;
  }

  onSubmit(bookData) {
    this.book.title = bookData.title;
    this.book.summary = bookData.summary;
    this.book.isbn = bookData.isbn;
    this.book.author = bookData.author;
    this.book.genre = [];
    bookData.genresF.forEach((g, i) => {
      if (g) {
        this.book.genre.push(this.genres[i]._id);
      }
    });
    this.bookGenres = this.genres.filter(g => this.book.genre.includes(g._id));
    this.bookAuthor = this.authors.filter(a => a._id === this.book.author)[0];

    this.bookForm.reset();

    if (this.buttonLabel === "Create") {
      this.bookService.createBook(this.book).subscribe(result => {
        this.errorMessage = result.message;
        this.updateOrCreate = false;
      });
    } else {
      this.bookService.updateBook(this.book).subscribe(result => {
        this.errorMessage = result.message;
        this.updateOrCreate = false;
      });
    }
  }
}
