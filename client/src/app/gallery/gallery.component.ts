import { Component, OnInit } from '@angular/core';
import {Author} from "../defs/author";
import {AuthorService} from "../services/author.service";

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.css']
})
export class GalleryComponent implements OnInit {
  constructor(public authorService:AuthorService) { }
  authors:Author[]=[];
  ngOnInit(): void {
    this.authorService.getAll();
  }

}
