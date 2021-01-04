import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {of, Observable} from "rxjs";
import {Author} from "./author";
import { catchError, map} from 'rxjs/operators';
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
    return this.http.get<Author[]>(this.usersUrl).pipe(
      catchError(this.handleError<Author[]>("findAll()",[])),
      map((response)=>response.content as Author[])
    );
  }
  public findById(id:number):Observable<Author>{
    return this.http.get<Author>(`${this.usersUrl}/${id}`).pipe(
      catchError(this.handleError<Author>("get()")),
      map((response)=>response.content as Author)
    );
  }
  public create(author:Author):Observable<any>{
    return this.http.put(this.usersUrl, author, this.httpOptions).pipe(
      catchError(this.handleError<Author>("create()"))
    );
  }

  public save(author: Author) {
    return this.http.post<Author>(this.usersUrl, author).pipe(
      catchError(this.handleError<Author>("save()"))
    );
  }
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      console.error(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
