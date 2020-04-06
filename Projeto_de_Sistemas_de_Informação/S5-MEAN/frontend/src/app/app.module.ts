import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HttpClientJsonpModule } from '@angular/common/http'

import { AppComponent } from './app.component';
import { AuthorsComponent } from './authors/authors.component';
import { AppRoutingModule } from './app-routing.module';
import { AuthorComponent } from './author/author.component';
import { ReactiveFormsModule } from '@angular/forms';
import { GenresComponent } from './genres/genres.component';
import { GenreDetailComponent } from './genre-detail/genre-detail.component';
import { BooksComponent } from './books/books.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BookinstancesComponent } from './bookinstances/bookinstances.component';
import { BookinstanceDetailComponent } from './bookinstance-detail/bookinstance-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthorsComponent,
    AuthorComponent,
    GenresComponent,
    GenreDetailComponent,
    BooksComponent,
    BookDetailComponent,
    BookinstancesComponent,
    BookinstanceDetailComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpClientJsonpModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
