import { Component, OnInit } from '@angular/core';
import { Author } from '../author';
import { Genre } from '../genre';
import { Book } from '../book';
import { BookService } from '../book.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.css']
})
export class BookFormComponent implements OnInit {

  genres: Genre[];
  authors: Author[];
  book: Book;
  errors: any[];
  bookR: Book;

  constructor(
    private bookService: BookService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.createBookStart();
  }

  createBookStart(){
    this.bookService.createBookStart()
    .subscribe(intel => {this.authors = intel['authors'], this.genres = intel['genres']});
  }

  bookCreate(title,author,summary,isbn,genre){
    this.book = {
      _id: null,
      title: title,
      author: author,
      summary: summary,
      isbn: isbn,
      genre: genre
    }
    this.bookService.bookCreate(this.book)
    .subscribe(intel =>{ this.errors = intel["errors"];this.bookR = intel["book"]})
    if(this.bookR !== null){
      alert("book "+this.book.title+" created.");
      this.router.navigate(['/book-list']);
    }
  }
}


