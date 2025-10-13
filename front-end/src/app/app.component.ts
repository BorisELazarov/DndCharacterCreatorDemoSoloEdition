import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { MenubarModule } from 'primeng/menubar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet, MenubarModule
],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  protected items: MenuItem[] | undefined;

  constructor() {}

  ngOnInit(): void {
    this.items = [
      {
        label: "Home",
        icon: "pi pi-home",
        url: 'home'
      },
      {
        label: "Characters",
        icon: "pi pi-user",
        url: 'characters'
      },
      {
        label: "Proficiencies",
        icon: "pi pi-pencil",
        url: 'proficiencies'
      },
      {
        label: "Classes",
        icon: "pi pi-shield",
        url: 'classes'
      },
      {
        label: "Spells",
        icon: "pi pi-sparkles",
        url: 'spells'
      }
    ];
  }

  
}
