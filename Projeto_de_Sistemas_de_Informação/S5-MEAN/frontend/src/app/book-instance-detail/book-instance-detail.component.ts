import { Component, OnInit, Input } from '@angular/core';
import { BookInstanceService } from '../book-instance.service';
import { BookInstance } from '../book-instance';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-book-instance-detail',
  templateUrl: './book-instance-detail.component.html',
  styleUrls: ['./book-instance-detail.component.css']
})
export class BookInstanceDetailComponent implements OnInit {
  id: string;
  @Input() bookI: BookInstance;

  constructor(
    private route: ActivatedRoute,
    private bookInstanceService: BookInstanceService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getBookInstanceDetail(this.id);
  }

  getBookInstanceDetail(id) {
    this.bookInstanceService.getBookInstanceDetails(id)
    .subscribe(intel => this.bookI = intel['bookinstance']);
  }

}
