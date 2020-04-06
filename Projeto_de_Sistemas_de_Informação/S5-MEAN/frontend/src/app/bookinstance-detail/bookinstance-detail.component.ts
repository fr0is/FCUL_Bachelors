import { Location, formatDate } from "@angular/common";
import { BookinstanceService } from "./../bookinstance.service";
import { Component, OnInit } from "@angular/core";
import { BookInstance } from "src/bookinstance";
import { Book } from "src/book";
import { ActivatedRoute } from "@angular/router";
import { BookService } from "../book.service";
import { FormBuilder, FormGroup } from "@angular/forms";

@Component({
  selector: "app-bookinstance-detail",
  templateUrl: "./bookinstance-detail.component.html",
  styleUrls: ["./bookinstance-detail.component.css"]
})
export class BookinstanceDetailComponent implements OnInit {
  bookInstance: BookInstance = {
    _id: "",
    book: "",
    imprint: "",
    status: "",
    due_back: new Date()
  };
  book: Book = {
    _id: "",
    title: "",
    summary: "",
    author: "",
    isbn: "",
    genre: []
  };
  books: Book[];

  errorMessage = "";
  updateOrCreate = false;
  buttonLabel = "Update";

  bookInstanceForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private bookInstanceService: BookinstanceService,
    private bookService: BookService,
    private formBuilder: FormBuilder,
    private location: Location
  ) {
    this.bookInstanceForm = this.formBuilder.group({
      book: this.formBuilder.control(this.books),
      imprint: this.formBuilder.control(""),
      status: this.formBuilder.control(""),
      due_back: this.formBuilder.control(""),
      _id: this.formBuilder.control("")
    });
  }

  ngOnInit(): void {
    this.getBookInstance();
    this.getBooks();
  }

  getBooks() {
    this.bookService
      .getBooks()
      .subscribe(booksList => (this.books = booksList as Book[]));
  }

  getBookInstance() {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.updateOrCreate = false;
      this.bookInstanceService.getBookInstance(id).subscribe(copy => {
        this.bookInstance = copy;
        this.bookService.getBook(copy.book).subscribe(book => {
          this.book = book;
        });
      });
    } else {
      this.updateOrCreate = true;
      this.buttonLabel = "Create";
    }
  }

  deleteBookInstance() {
    this.bookInstanceService
      .deleteBookInstance(this.bookInstance)
      .subscribe(result => {
        if (result.message === "success") {
          this.location.back();
        } else {
          this.errorMessage = result.message;
        }
      });
  }

  updateBookInstance() {
    let due_back = "";
    if (this.bookInstance.due_back) {
      due_back = formatDate(this.bookInstance.due_back, "yyyy-MM-dd", "en-US");
    }

    this.bookInstanceForm.patchValue({
      _id: this.bookInstance._id,
      imprint: this.bookInstance.imprint,
      status: this.bookInstance.status,
      due_back: due_back
    });
    this.bookInstanceForm.controls.book.setValue(this.book._id);

    this.updateOrCreate = true;
  }

  onSubmit(bookInstanceData) {
    if (this.bookInstance.book !== bookInstanceData.book) {
      this.bookService.getBook(bookInstanceData.book).subscribe(book => {
        this.book = book;
      });
    }
    this.bookInstance = bookInstanceData;

    this.bookInstanceForm.reset();

    if (this.buttonLabel === "Create") {
      this.bookInstanceService.createBookInstance(this.bookInstance).subscribe(result => {
        this.errorMessage = result.message;
        this.updateOrCreate = false;
      });
    } else {
      this.bookInstanceService.updateBookInstance(this.bookInstance).subscribe(result => {
        this.errorMessage = result.message;
        this.updateOrCreate = false;
      });
    }

  }
}
