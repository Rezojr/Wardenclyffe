import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthorFormComponent} from "./author-form/author-form.component";
import {GalleryComponent} from "./gallery/gallery.component";
import {AuthorDetailsComponent} from "./author-details/author-details.component";

const routes: Routes = [
  { path: '', component: GalleryComponent },
  { path: 'form', component: AuthorFormComponent },
  { path: 'authors/:id', component: AuthorDetailsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
