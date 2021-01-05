import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { catchError, map} from 'rxjs/operators';
import {Observable, of} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RequestService {
  private usersUrl = 'http://localhost:8080/';
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  constructor(private http:HttpClient) { }

  get<T>(relativeUrl:string, result?:T ):Observable<any>{
    return this.http.get(this.usersUrl+relativeUrl).pipe(
      catchError(this.handleError<T>(`get ${relativeUrl}`, result))
    )
  }
  getAndMap<T>(relativeUrl:string, mapFunc:Function, result?:T):Observable<T>{
    return this.http.get(this.usersUrl+relativeUrl).pipe(
      catchError(this.handleError<T>(`getAndMap ${relativeUrl}`, result)),
      map(response => mapFunc(response) as T)
    )
  }
  create<T>(relativeUrl:string,data:T, result?:T):Observable<any>{
    return this.http.put(this.usersUrl + relativeUrl, data, this.httpOptions).pipe(
      catchError(this.handleError<T>(`create ${relativeUrl}`, result))
    )
  }
  save<T>(relativeUrl:string, data:T, result?:T):Observable<any>{
    return this.http.post(this.usersUrl + relativeUrl, data).pipe(
      catchError(this.handleError<T>(`save ${relativeUrl}`, result))
    );
  }
  delete<T>(relativeUrl:string):Observable<any>{
    return this.http.delete(this.usersUrl+relativeUrl).pipe(
      catchError(this.handleError<T>(`delete ${relativeUrl}`))
    )
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
