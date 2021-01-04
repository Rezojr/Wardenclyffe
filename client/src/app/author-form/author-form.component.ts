import { Component, OnInit } from '@angular/core';
import {Author} from "../author";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthorService} from "../author.service";

@Component({
  selector: 'app-author-form',
  templateUrl: './author-form.component.html',
  styleUrls: ['./author-form.component.css']
})
export class AuthorFormComponent implements OnInit {
  ngOnInit(): void {
  }

  author: Author;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: AuthorService) {
    this.author = new Author();
  }

  onSubmit() {
    this.userService.save(this.author).subscribe(result => this.gotoUserList());
  }

  gotoUserList() {
    this.router.navigate(['/authors']);
  }

}
