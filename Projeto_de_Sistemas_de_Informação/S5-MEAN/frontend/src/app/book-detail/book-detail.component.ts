import { Component, OnInit, Input } from '@angular/core';
import { BookService } from '../book.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Location } from '@angular/common';
import { Book } from '../book';
import { BookInstance } from '../book-instance';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  id:String;
  @Input() book: Book;
  bookInstances: BookInstance[];

  constructor(
    private route: ActivatedRoute,
    private bookService: BookService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getBookDetail(this.id);
  }
  getBookDetail(id: String) {
    this.bookService.getBookDetails(id)
  .subscribe(intel => {this.book = intel['book']; this.bookInstances=intel['book_instances']});
  }

}
