import { Component, OnInit } from '@angular/core';
import { BookInstance } from '../book-instance';
import { BookInstanceService } from '../book-instance.service';

@Component({
  selector: 'app-book-instance-instance-list',
  templateUrl: './book-instance-list.component.html',
  styleUrls: ['./book-instance-list.component.css']
})
export class BookInstanceListComponent implements OnInit {

  bookInstances: BookInstance[];

  constructor(private bookInstanceService: BookInstanceService) { }

  ngOnInit(): void {
    this.getBookInstances();
  }

  getBookInstances(){
    this.bookInstanceService.getBookInstances()
    .subscribe(intel => this.bookInstances = intel['bookinstance_list']);
  }

}
