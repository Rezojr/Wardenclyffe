import { Component, OnInit } from '@angular/core';
import {Author} from "../author";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthorService} from "../author.service";

@Component({
  selector: 'app-auhtor-form',
  templateUrl: './auhtor-form.component.html',
  styleUrls: ['./auhtor-form.component.css']
})
export class AuhtorFormComponent implements OnInit {

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
