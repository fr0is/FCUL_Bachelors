import { GenreService } from '../genre.service';
import { Component, OnInit } from '@angular/core';
import { Genre } from 'src/genre';

@Component({
  selector: 'app-genres',
  templateUrl: './genres.component.html',
  styleUrls: ['./genres.component.css']
})
export class GenresComponent implements OnInit {

  genres: Genre[] = [];

  constructor(private genreService: GenreService) { }

  ngOnInit(): void {
    this.showGenres();
  }

  showGenres() {
    this.genreService.getGenres().subscribe(genreList => {
      this.genres = genreList as Genre[];
    });
  }

}
