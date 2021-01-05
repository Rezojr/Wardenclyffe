import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthorService } from '../services/author.service';
import { Author } from '../defs/author';

@Component({
  selector: 'app-author-details',
  templateUrl: './author-details.component.html',
  styleUrls: ['./author-details.component.css']
})
export class AuthorDetailsComponent implements OnInit {

  author: Author = new Author;
  constructor(
    private route:ActivatedRoute,
    private authorService:AuthorService
  ) { }

  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.authorService.getById(id).subscribe(a=>{
      this.author=a;
      this.check();
    });
  }
  check(){
    console.log(this.author);
    
  }
}
