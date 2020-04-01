import { Component, OnInit } from '@angular/core';
import { GenreService } from '../genre.service';
import { Genre } from '../genre';
import { RouterLink, Router, ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-genre-update',
  templateUrl: './genre-update.component.html',
  styleUrls: ['./genre-update.component.css']
})
export class GenreUpdateComponent implements OnInit {

  genre: Genre;
  errors: any[];
  generoR: Genre;
  id: String;

  constructor(
    private genreService: GenreService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.updateGenreStart();
  }

  updateGenreStart(){
    this.genreService.updateGenreStart(this.id)
    .subscribe(intel => this.genre= intel['genre']);
  }

  updateGenre(gName){
    const nomeA = this.genre.name;
    this.genre.name = gName;
    this.genreService.updateGenre(this.id,this.genre)
    .subscribe(intel =>{this.genre= intel['genre'], this.errors = intel['errors']});
    if(this.genre !== null){
      alert("Genre "+nomeA+" updated.");
      this.router.navigate(['/genre-list']);
    }
  }

}
