import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Author} from "./author";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  private usersUrl = 'http://localhost:8080/authors';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {}

  public findAll(): Observable<Author[]> {
    return this.http.get<Author[]>(this.usersUrl);
  }

  public save(user: Author) {
    return this.http.post<Author>(this.usersUrl, user);
  }
}
