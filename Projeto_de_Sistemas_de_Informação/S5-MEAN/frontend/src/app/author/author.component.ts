import { Book } from "src/book";
import { AuthorService } from "../author.service";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Author } from "src/author";
import { Location, formatDate } from "@angular/common";
import { FormBuilder } from "@angular/forms";

@Component({
  selector: "app-author",
  templateUrl: "./author.component.html",
  styleUrls: ["./author.component.css"]
})
export class AuthorComponent implements OnInit {
  // Author data
  author: Author = {
    _id: "",
    first_name: "",
    family_name: "",
    date_of_birth: new Date(),
    date_of_death: new Date()
  };
  books: Book[] = [];

  // feedback and display control
  errorMessage = "";
  updateOrCreate = false;
  buttonLabel = "Update";

  // Author details form
  authorForm;

  constructor(
    private route: ActivatedRoute,
    private authorService: AuthorService,
    private formBuilder: FormBuilder,
    private location: Location
  ) {
    this.authorForm = this.formBuilder.group({
      first_name: "",
      family_name: "",
      date_of_birth: "",
      date_of_death: "",
      _id: ""
    });
  }

  ngOnInit(): void {
    this.getAuthor();
    this.getAuthorBooks();
  }

  getAuthor() {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.updateOrCreate = false;
      this.authorService.getAuthor(id).subscribe(author => {
        this.author = author;
      });
    } else {
      this.updateOrCreate = true;
      this.buttonLabel = "Create";
    }
  }

  getAuthorBooks() {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.authorService.getAuthorBooks(id).subscribe(books => {
        this.books = books;
      });
    }
  }

  deleteAuthor() {
    this.authorService.deleteAuthor(this.author).subscribe(result => {
      if (result.message === "success") {
        this.location.back();
      } else {
        this.errorMessage = result.message;
      }
    });
  }

  updateAuthor() {
    let date_of_birth = "";
    let date_of_death = "";
    if (this.author.date_of_birth) {
      date_of_birth = formatDate(
        this.author.date_of_birth,
        "yyyy-MM-dd",
        "en-US"
      );
    }
    if (this.author.date_of_death) {
      date_of_death = formatDate(
        this.author.date_of_death,
        "yyyy-MM-dd",
        "en-US"
      );
    }

    this.authorForm.patchValue({
      first_name: this.author.first_name,
      family_name: this.author.family_name,
      date_of_birth: date_of_birth,
      date_of_death: date_of_death,
      _id: this.author._id
    });
    this.updateOrCreate = true;
  }

  onSubmit(authorData) {
    this.author = authorData;
    this.authorForm.reset();
    if (this.buttonLabel === "Create") {
      this.authorService.createAuthor(this.author).subscribe(result => {
        this.errorMessage = result.message;
        this.updateOrCreate = false;
      });
    } else {
      this.authorService.updateAuthor(this.author).subscribe(result => {
        this.errorMessage = result.message;
        this.updateOrCreate = false;
      });
    }
  }
}
