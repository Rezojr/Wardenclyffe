import { Component, OnInit } from '@angular/core';
import { ToggleService } from '../services/toggle.service';
@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  constructor(private toggleService:ToggleService) { }

  ngOnInit(): void {
  }
  active(offset:string):string{
    let status = this.toggleService.toggleMap.get("sidebar");
    if(status==undefined) throw new Error("No key for sidebar!");
    return status?"initial" : (`translate(${offset})`);
  }
}
