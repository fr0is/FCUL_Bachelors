import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomePageComponent } from './home-page/home-page.component';
import { GenreListComponent } from './genre-list/genre-list.component';
import { GenreFormComponent } from './genre-form/genre-form.component';
import { GenreDetailComponent } from './genre-detail/genre-detail.component';
import { ErrorComponent } from './error/error.component';
import { BookInstanceListComponent } from './book-instance-list/book-instance-list.component';
import { BookInstanceFormComponent } from './book-instance-form/book-instance-form.component';
import { BookInstanceDetailComponent } from './book-instance-detail/book-instance-detail.component';
import { BookListComponent } from './book-list/book-list.component';
import { BookFormComponent } from './book-form/book-form.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { AuthorListComponent } from './author-list/author-list.component';
import { AuthorDetailComponent } from './author-detail/author-detail.component';
import { AuthorDeleteComponent } from './author-delete/author-delete.component';
import { AuthorFormComponent } from './author-form/author-form.component';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    GenreListComponent,
    GenreFormComponent,
    GenreDetailComponent,
    ErrorComponent,
    BookInstanceListComponent,
    BookInstanceFormComponent,
    BookInstanceDetailComponent,
    BookListComponent,
    BookFormComponent,
    BookDetailComponent,
    AuthorListComponent,
    AuthorDetailComponent,
    AuthorDeleteComponent,
    AuthorFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
