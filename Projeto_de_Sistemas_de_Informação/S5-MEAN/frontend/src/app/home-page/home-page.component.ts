import { Component, OnInit } from '@angular/core';
import { getAllLifecycleHooks } from '@angular/compiler/src/lifecycle_reflector';
import { BookService } from '../book.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  books: number;
  copies: number;
  copiesAv: number;
  authors: number;
  genres: number;
  error: any;

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.getAll();
  }

  getAll(){
    this.bookService.getAll()
    .subscribe(intel =>{this.books = intel['data'].book_count;
    this.copies = intel['data'].book_instance_count;this.copiesAv = intel['data'].book_instance_available_count;
    this.authors = intel['data'].author_count;this.genres = intel['data'].genre_count;this.error = intel['error']});
  }

}
