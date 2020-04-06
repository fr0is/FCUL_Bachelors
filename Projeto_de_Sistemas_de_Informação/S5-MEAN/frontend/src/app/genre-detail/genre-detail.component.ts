import { Book } from 'src/book';
import { Location } from '@angular/common';
import { FormBuilder } from '@angular/forms';
import { GenreService } from "../genre.service";
import { Component, OnInit } from "@angular/core";
import { Genre } from "src/genre";
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: "app-genre-detail",
  templateUrl: "./genre-detail.component.html",
  styleUrls: ["./genre-detail.component.css"]
})
export class GenreDetailComponent implements OnInit {
  genre: Genre = {
    _id: "",
    name: ""
  };
  books: Book[] = [];

  errorMessage = "";
  updateOrCreate = false;
  buttonLabel = "Update";

  genreForm;

  constructor(
    private route: ActivatedRoute,
    private genreService: GenreService,
    private formBuilder: FormBuilder,
    private location: Location
  ) {
    this.genreForm = this.formBuilder.group({
      name: "",
      _id: ""
    });
  }

  ngOnInit(): void {
    this.getGenre();
    this.getGenreBooks();
  }

  getGenre() {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.updateOrCreate = false;
      this.genreService.getGenre(id).subscribe(genre => {
        this.genre = genre;
      });
    } else {
      this.updateOrCreate = true;
      this.buttonLabel = "Create";
    }
  }

  getGenreBooks() {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.genreService.getGenreBooks(id).subscribe(books => {
        this.books = books;
      });
    }
  }

  deleteGenre() {
    this.genreService.deleteGenre(this.genre).subscribe(result => {
      if (result.message === "success") {
        this.location.back();
      } else {
        this.errorMessage = result.message;
      }
    });
  }

  updateGenre() {
    this.genreForm.patchValue({
      name: this.genre.name,
      _id: this.genre._id
    });
    this.updateOrCreate = true;
  }

  onSubmit(genreData) {
    this.genre = genreData;
    this.genreForm.reset();
    if (this.buttonLabel === "Create") {
      this.genreService.createGenre(this.genre).subscribe(result => {
        this.errorMessage = result.message;
        this.updateOrCreate = false;
      });
    } else {
      this.genreService.updateGenre(this.genre).subscribe(result => {
        this.errorMessage = result.message;
        this.updateOrCreate = false;
      });
    }
  }

}
