import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthorComponent} from "./author/author.component";
import {AuthorFormComponent} from "./author-form/author-form.component";

const routes: Routes = [
  { path: 'authors', component: AuthorComponent },
  { path: 'form', component: AuthorFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
