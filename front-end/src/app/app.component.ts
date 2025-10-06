import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MatToolbar, MatToolbarRow } from '@angular/material/toolbar';
import { MatIcon } from '@angular/material/icon';
import { LocalStorageService } from './core/services/local-storage-service/local-storage.service';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet, MatToolbar, MatToolbarRow, 
    MatIcon, MatButtonModule, RouterLink
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
