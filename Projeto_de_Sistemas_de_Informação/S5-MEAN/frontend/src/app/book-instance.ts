import { Book } from './book';

export interface BookInstance{
    book: Book;
    imprint: String;
    status: String;
    due_back: Date;
    _id: number;
}