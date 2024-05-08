import { Component, ElementRef, ViewChild } from '@angular/core';
import { MenuItem } from 'primeng/api/menuitem';
import { LayoutService } from '../../admin-layout/layout.service';
import { NgClass } from '@angular/common';

@Component({
  selector: 'user-top-bar',
  standalone: true,
  imports: [NgClass],
  templateUrl: './user-top-bar.component.html',
  styleUrl: './user-top-bar.component.css'
})
export class UserTopBarComponent {
  items!: MenuItem[];

  @ViewChild('menubutton') menuButton!: ElementRef;

  @ViewChild('topbarmenubutton') topbarMenuButton!: ElementRef;

  @ViewChild('topbarmenu') menu!: ElementRef;

  constructor(public layoutService: LayoutService) { }
}
