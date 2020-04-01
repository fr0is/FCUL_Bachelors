import { Component, OnInit } from '@angular/core';
import { getAllLifecycleHooks } from '@angular/compiler/src/lifecycle_reflector';
import { BookService } from '../book.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  results: any[];
  error: any;
  bookService: BookService;

  constructor() { }

  ngOnInit(): void {
    this.getAll();
  }

  getAll(){
    this.bookService.getAll()
    .subscribe(intel =>{this.results = intel['data'], this.error = intel['error']});
    console.log(this.results  )
  }

}
