import { Component, OnInit, Input } from '@angular/core';
import { AuthorService } from '../author.service';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
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
  errors: any[];

  constructor(
    private route: ActivatedRoute,
    private authorService: AuthorService,
    private location: Location,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getAuthorDetails(this.id);
    this.deleteAuthorStart();
  }
  
  getAuthorDetails(id) {
    this.authorService.getAuthorDetails(id)
    .subscribe(intel => {this.author = intel['author']; this.books = intel['author_books']})
  }

  
  deleteAuthorStart(){
    this.authorService.deleteAuthorStart(this.id)
    .subscribe(intel => {this.author= intel['author'],this.books=intel['author_books']});
  }

  deleteAuthor(){
    const nomeA = this.author.first_name+' '+this.author.family_name;
    this.deleteAuthorStart();
    if(this.books.length > 0){
      alert("Delete the books first.");
    }else{
      this.authorService.deleteAuthor(this.id,this.author)
      .subscribe(intel =>{this.author= intel['author'], this.errors = intel['errors']});
      if(this.author !== null){
        alert("Author "+nomeA+" deleted.");
        this.router.navigate(['/author-list']);
      }
    }
  }

}
