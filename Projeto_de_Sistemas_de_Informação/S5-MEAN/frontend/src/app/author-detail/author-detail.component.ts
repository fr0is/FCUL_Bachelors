import { Component, OnInit, Input } from '@angular/core';
import { AuthorService } from '../author.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Location } from '@angular/common';
import { Author } from '../author';
import { Book } from '../book';

@Component({
  selector: 'app-author-detail',
  templateUrl: './author-detail.component.html',
  styleUrls: ['./author-detail.component.css']
})
export class AuthorDetailComponent implements OnInit {

  @Input() author: Author;
  books: Book[];
  id: String;

  constructor(
    private route: ActivatedRoute,
    private authorService: AuthorService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getAuthorDetails(this.id);
  }
  
  getAuthorDetails(id) {
    this.authorService.getAuthorDetails(id)
    .subscribe(intel => {this.author = intel['author']; this.books = intel['author_books']})
  }

}
