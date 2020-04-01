import { Component, OnInit } from '@angular/core';
import { AuthorService } from '../author.service';
import { Author } from '../author';
import { RouterLink, Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-author-update',
  templateUrl: './author-update.component.html',
  styleUrls: ['./author-update.component.css']
})
export class AuthorUpdateComponent implements OnInit {

  author: Author;
  errors: any[];
  authorR: Author;
  id: String;


  constructor(
    private authorService: AuthorService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.updateAuthorStart();
  }

  updateAuthorStart(){
    this.authorService.updateAuthorStart(this.id)
    .subscribe(intel => this.author= intel['author']);
  }

  authorUpdate(first_name, family_name, dateBirth, dateDeath){
    const nomeA = this.author.first_name +' '+ this.author.family_name;
    if(first_name !=null){this.author.first_name = first_name};
    if(family_name !=null){this.author.family_name = family_name};
    if(dateBirth !=null){this.author.date_of_birth = dateBirth};
    if(dateDeath !=null){this.author.date_of_death = dateDeath};
    this.authorService.updateAuthor(this.id,this.author)
    .subscribe(intel =>{this.author= intel['author'], this.errors = intel['errors']});
    if(this.author !== null){
      alert("Author "+nomeA+" updated.");
      this.router.navigate(['/author-list']);
    }
  }

}
