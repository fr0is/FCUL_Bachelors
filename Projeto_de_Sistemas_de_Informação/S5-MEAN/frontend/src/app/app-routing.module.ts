import { RouterModule, Routes } from "@angular/router";
import { NgModule } from "@angular/core";
import { AuthorsComponent } from "./authors/authors.component";
import { AuthorComponent } from "./author/author.component";
import { GenresComponent } from './genres/genres.component';
import { GenreDetailComponent } from './genre-detail/genre-detail.component';
import { BooksComponent } from './books/books.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BookinstancesComponent } from './bookinstances/bookinstances.component';
import { BookinstanceDetailComponent } from './bookinstance-detail/bookinstance-detail.component';

const routes: Routes = [
  { path: "authors", component: AuthorsComponent },
  { path: "author/:id", component: AuthorComponent },
  { path: "author", component: AuthorComponent },
  { path: "genres", component: GenresComponent },
  { path: "genre/:id", component: GenreDetailComponent },
  { path: "genre", component: GenreDetailComponent },
  { path: "books", component: BooksComponent },
  { path: "book/:id", component: BookDetailComponent },
  { path: "book", component: BookDetailComponent },
  { path: "bookinstances", component: BookinstancesComponent },
  { path: "bookinstance/:id", component: BookinstanceDetailComponent },
  { path: "bookinstance", component: BookinstanceDetailComponent }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
