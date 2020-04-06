import { AuthorService } from "./../author.service";
import { Component, OnInit } from "@angular/core";
import { Author } from "../../author";

@Component({
  selector: "app-authors",
  templateUrl: "./authors.component.html",
  styleUrls: ["./authors.component.css"]
})
export class AuthorsComponent implements OnInit {
  authors: Author[] = [];

  constructor(private authorService: AuthorService) {}

  ngOnInit(): void {
    this.showAuthors();
  }

  showAuthors() {
    this.authorService.getAuthors().subscribe(authorsList => {
      this.authors = authorsList as Author[];
    });
  }
}
