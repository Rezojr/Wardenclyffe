import { Component, OnInit } from '@angular/core';
import {Author} from "../defs/author";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthorService} from "../services/author.service";

@Component({
  selector: 'app-author-form',
  templateUrl: './author-form.component.html',
  styleUrls: ['./author-form.component.css']
})
export class AuthorFormComponent implements OnInit {
  ngOnInit(): void {
  }


  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authorService: AuthorService) {
  }



}
