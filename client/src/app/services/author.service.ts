import { Injectable } from '@angular/core';
import { RequestService } from "./request.service";
import { Author } from "../defs/author";
import {Observable} from "rxjs";
import { Request } from "../defs/request";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {
  private url='/authors';
  authors:Author[]=[];
  constructor(private request:RequestService) {}
  getAll():void{
    // @ts-ignore
    this.request.getAndMap(this.url,this.strip).subscribe((result): Author[] => this.authors=result);
  }
  getById(id:number):Observable<Author>{
    return this.request.get(`${(this.url)}/${id}`);
  }
  getByName(name:string){
    this.request.getAndMap(this.url,(result:Request)=>
      result.content.filter((a:Author): boolean =>
        new RegExp(`\w*${name}\w*`,'i').test(a.nickname)
      )
      // @ts-ignore
    ).subscribe((result): Author[] => this.authors=result);
  }
  private strip(result:Request){
    return result.content;
  }
  private set(result:Author[]){
    this.authors=result;
  }

}
