import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Author} from "./author";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  private usersUrl: string;

  constructor(private http: HttpClient) {
    this.usersUrl = 'http://localhost:8080/authors';
  }

  public findAll(): Observable<Author[]> {
    return this.http.get<Author[]>(this.usersUrl);
  }

  public save(user: Author) {
    return this.http.post<Author>(this.usersUrl, user);
  }
}
