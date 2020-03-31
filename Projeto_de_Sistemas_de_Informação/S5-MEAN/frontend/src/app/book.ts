import { Author } from './author';
import { Genre } from './genre';

export interface Book{
    title: String;
    author: Author;
    summary: String;
    isbn: String;
    genre: Genre;
    _id: number;
}