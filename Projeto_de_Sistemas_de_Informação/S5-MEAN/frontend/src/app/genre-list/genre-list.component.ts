import { Component, OnInit } from '@angular/core';
import { Genre } from '../genre';
import { GenreService } from '../genre.service';

@Component({
  selector: 'app-genre-list',
  templateUrl: './genre-list.component.html',
  styleUrls: ['./genre-list.component.css']
})
export class GenreListComponent implements OnInit {

  genres: Genre[];

  constructor(private genreService: GenreService) { }

  ngOnInit(): void {
    this.getGenres();
  }

  getGenres(){
    this.genreService.getGenres()
    .subscribe(intel => this.genres = intel['list_genres']);
  }

}
