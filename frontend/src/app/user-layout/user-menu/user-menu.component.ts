import {Component} from '@angular/core';
import {LayoutService} from '../../admin-layout/layout.service';
import {NgFor, NgIf} from '@angular/common';
import {UserMenuItemComponent} from "../user-menu-item/user-menu-item.component";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'user-menu',
  standalone: true,
  templateUrl: './user-menu.component.html',
  styleUrl: './user-menu.component.css',
  imports: [NgFor, NgIf, UserMenuItemComponent]
})
export class UserMenuComponent {
  model: any[] = [];

  constructor(public layoutService: LayoutService,
              private userService: UserService) {
  }

  ngOnInit() {
    this.userService.getPrincipal().subscribe({
      next: (res) => {
        this.model = [
          {
            label: 'Home',
            items: [
              {label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: ['/']},
              {label: 'Books', icon: 'pi pi-fw pi-book', routerLink: ['/books']},
              {label: 'My Reservations', icon: 'pi pi-fw pi-calendar-clock', routerLink: ['/reservations']},
              {label: 'My Wishlist', icon: 'pi pi-fw pi-list-check', routerLink: ['/wishlist']},
              {label: 'Book Trade', icon: 'pi pi-fw pi-arrow-right-arrow-left', routerLink: ['/my']},
            ]
          },
        ];
      },
      error: err => {
        this.model = [
          {
            label: 'Home',
            items: [
              {label: 'Dashboard', icon: 'pi pi-fw pi-home', routerLink: ['/']},
              {label: 'Books', icon: 'pi pi-fw pi-book', routerLink: ['/books']},
            ]
          },
        ];
      }
    })

  }
}
