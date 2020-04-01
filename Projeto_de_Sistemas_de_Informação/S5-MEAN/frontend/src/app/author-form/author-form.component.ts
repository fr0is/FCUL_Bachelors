import { Component, OnInit } from '@angular/core';
import { Author } from '../author';
import { AuthorService } from '../author.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-author-form',
  templateUrl: './author-form.component.html',
  styleUrls: ['./author-form.component.css']
})
export class AuthorFormComponent implements OnInit {

  author: Author;
  errors: any[];
  authorR: Author;

  constructor(
    private authorService: AuthorService,
    private router: Router,
  ) { }

  ngOnInit(): void {
  }

  authorCreate(first_name, family_name, dateBirth, dateDeath){
    this.author = {
    _id: null,
    first_name: first_name,
    family_name: family_name,
    date_of_birth: dateBirth,
    date_of_death: dateDeath
    }
    this.authorService.authorCreate(this.author)
    .subscribe(intel =>{ this.errors = intel["errors"];this.authorR = intel["author"]})
    if(this.errors !== null){
      alert("Author "+this.author.first_name+" created.");
      this.router.navigate(['/author-list']);
    }
  }
}
