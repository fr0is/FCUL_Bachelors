import { Component, OnInit } from '@angular/core';
import { GenreService } from '../genre.service';
import { Genre } from '../genre';
import { RouterLink, Router, ActivatedRoute } from '@angular/router';
import { Book } from '../book';


@Component({
  selector: 'app-genre-delete',
  templateUrl: './genre-delete.component.html',
  styleUrls: ['./genre-delete.component.css']
})
export class GenreDeleteComponent implements OnInit {

  genre: Genre;
  errors: any[];
  books: Book[];
  generoR: Genre;
  id: String;

  constructor(
    private genreService: GenreService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.deleteGenreStart();
  }

  deleteGenreStart(){
    this.genreService.deleteGenreStart(this.id)
    .subscribe(intel => {this.genre= intel['genre'],this.books=intel['genre_books']});
  }

  deleteGenre(){
    const nomeG = this.genre.name;
    this.deleteGenreStart();
    if(this.books.length > 0){
      alert("Delete the books first.");
    }else{
      this.genreService.deleteGenre(this.id,this.genre)
      .subscribe(intel =>{this.genre= intel['genre'], this.errors = intel['errors']});
      if(this.genre !== null){
        alert("Genre "+nomeG+" deleted.");
        this.router.navigate(['/genre-list']);
      }
    }
  }
}
