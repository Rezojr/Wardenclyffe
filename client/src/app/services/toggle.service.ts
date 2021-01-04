import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ToggleService {
  toggleMap=new Map<string, boolean>([
    ["sidebar", false]
  ]);
  switch(key:string){
    this.toggleMap.set(key,!this.toggleMap.get(key));
  }
  constructor() { }
}
