import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthorComponent} from "./author/author.component";
import {AuhtorFormComponent} from "./auhtor-form/auhtor-form.component";

const routes: Routes = [
  { path: 'authors', component: AuthorComponent },
  { path: 'form', component: AuhtorFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
