import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GenreFormComponent } from './genre-form/genre-form.component';
import { GenreDetailComponent } from './genre-detail/genre-detail.component';
import { GenreListComponent } from './genre-list/genre-list.component';
import { ErrorComponent } from './error/error.component';
import { BookListComponent } from './book-list/book-list.component';
import { BookInstanceListComponent } from './book-instance-list/book-instance-list.component';
import { BookInstanceFormComponent } from './book-instance-form/book-instance-form.component';
import { BookInstanceDetailComponent } from './book-instance-detail/book-instance-detail.component';
import { BookFormComponent } from './book-form/book-form.component';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { AuthorListComponent } from './author-list/author-list.component';
import { AuthorFormComponent } from './author-form/author-form.component';
import { AuthorDeleteComponent } from './author-delete/author-delete.component';
import { AuthorDetailComponent } from './author-detail/author-detail.component';
import { HomePageComponent } from './home-page/home-page.component';

const routes: Routes = [
  { path: '', redirectTo: '/home-page', pathMatch: 'full' },
  { path: 'home-page', component: HomePageComponent },
  { path: 'genre-list', component: GenreListComponent },
  { path: 'genre-detail/:id', component: GenreDetailComponent },
  { path: 'genre-form', component: GenreFormComponent },
  { path: 'error', component: ErrorComponent },
  { path: 'book-list', component: BookListComponent },
  { path: 'book-instance-list', component: BookInstanceListComponent },
  { path: 'book-instance-form', component: BookInstanceFormComponent },
  { path: 'book-instance-detail/:id', component: BookInstanceDetailComponent },
  { path: 'book-form', component: BookFormComponent },
  { path: 'book-detail/:id', component: BookDetailComponent },
  { path: 'author-list', component: AuthorListComponent },
  { path: 'author-form', component: AuthorFormComponent },
  { path: 'author-delete/:id', component: AuthorDeleteComponent },
  { path: 'author/:id', component: AuthorDetailComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
