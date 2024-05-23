import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MenuItem} from 'primeng/api/menuitem';
import {LayoutService} from '../../admin-layout/layout.service';
import {NgClass, NgIf} from '@angular/common';
import {UserService} from "../../service/user.service";
import {UserResponse} from "../../UserResponse";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'user-top-bar',
  standalone: true,
  imports: [NgClass, NgIf, RouterLink],
  templateUrl: './user-top-bar.component.html',
  styleUrl: './user-top-bar.component.css'
})
export class UserTopBarComponent implements OnInit {
  items!: MenuItem[];

  @ViewChild('menubutton') menuButton!: ElementRef;

  @ViewChild('topbarmenubutton') topbarMenuButton!: ElementRef;

  @ViewChild('topbarmenu') menu!: ElementRef;

  user: UserResponse | undefined;

  constructor(public layoutService: LayoutService,
              private userService: UserService,) {

  }

  ngOnInit(): void {
    this.userService.getPrincipal().subscribe(user => {
      this.user = user;
    });
  }

  logout() {
    this.userService.logout()
    window.location.reload()
  }
}
