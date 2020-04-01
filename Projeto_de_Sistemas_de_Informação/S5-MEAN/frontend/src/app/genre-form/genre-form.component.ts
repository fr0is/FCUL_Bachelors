import { Component, OnInit } from '@angular/core';
import { GenreService } from '../genre.service';
import { Genre } from '../genre';
import { RouterLink, Router } from '@angular/router';

@Component({
  selector: 'app-genre-form',
  templateUrl: './genre-form.component.html',
  styleUrls: ['./genre-form.component.css']
})
export class GenreFormComponent implements OnInit {

  genre: Genre;
  errors: any[];
  generoR: Genre;

  constructor(
    private genreService: GenreService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  createGenre(gname){
    this.genre = {
      name: gname,
      _id : null
    }
    this.genreService.genreCreate(this.genre)
    .subscribe(intel =>{ this.errors = intel["errors"];this.generoR = intel["genre"]})
    if(this.generoR !== null){
      alert("Genre "+this.genre.name+" created.");
      this.router.navigate(['/genre-list']);
    }
  }

}
