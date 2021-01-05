import { Component, OnInit } from '@angular/core';
import { ToggleService } from '../services/toggle.service';
import { AuthorService } from "../services/author.service";

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {
  searched:boolean = false;
  searchCategories:Array<string> = ['Autorzy','Posty'];
  // @ts-ignore
  searchValue:string;
  constructor(
    private toggleService:ToggleService,
    private  authorService:AuthorService
    ) { }

  ngOnInit(): void {
    console.log("nav works");
  }
  switch(){
    this.toggleService.switch("sidebar");
  }
  search(value:string){
    this.authorService.getByName(value);
  }
}
