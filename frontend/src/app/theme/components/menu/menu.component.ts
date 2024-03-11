import { Component, OnInit } from '@angular/core';
import { MENU_ITEMS } from '../theme-menu';


@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }
  menu = MENU_ITEMS;
}
