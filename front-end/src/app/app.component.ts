import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { LocalStorageService } from './core/services/local-storage-service/local-storage.service';
import { ToolbarModule } from 'primeng/toolbar';
import { MatExpansionModule } from "@angular/material/expansion";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet, RouterLink, ToolbarModule,
    MatExpansionModule
],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  constructor(private localStorageService:LocalStorageService) {
  }
  ngOnInit(): void {
  }

  deleted():boolean|undefined{
    switch (this.localStorageService.getItem("deleted")) {
      case "false":
        return false;

      case "true":
        return true;
      
      default:
        return undefined;
    }
  }

  role():string|undefined{
    return this.localStorageService.getItem("role")??undefined;
  }

  logOut() {
    this.localStorageService.clear();
  }

  
}
