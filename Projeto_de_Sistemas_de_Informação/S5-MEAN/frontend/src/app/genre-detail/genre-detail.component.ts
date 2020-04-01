import { Component, OnInit, Input } from '@angular/core';
import { AuthorService } from '../author.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Location } from '@angular/common';
import { Genre } from '../genre';
import { GenreService } from '../genre.service';
import { Book } from '../book';

@Component({
  selector: 'app-genre-detail',
  templateUrl: './genre-detail.component.html',
  styleUrls: ['./genre-detail.component.css']
})
export class GenreDetailComponent implements OnInit {

  @Input() genre: Genre;
  id: String;
  books: Book[];

  constructor(
    private route: ActivatedRoute,
    private genreService: GenreService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getGenreDetails(this.id);
  }
  getGenreDetails(id: String) {
    this.genreService.getGenreDetails(id)
    .subscribe(intel => {this.genre = intel['genre'],this.books = intel['genre_books']})
  }

}
