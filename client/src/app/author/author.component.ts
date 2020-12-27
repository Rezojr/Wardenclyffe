import { Component, OnInit } from '@angular/core';
import {Author} from "../author";
import {AuthorService} from "../author.service";

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  authors: Author[];

  constructor(private authorService: AuthorService) { }

  ngOnInit() {
    this.authorService.findAll().subscribe(data => {
      this.authors = data;
    });
  }

}
