import { Component, Input, OnInit } from '@angular/core';
import { TabMenuModule } from 'primeng/tabmenu';
import { AccordionComponent } from '../accordion/accordion.component';
import { ReviewComponent } from '../review/review.component';
import { MenuItem } from 'primeng/api';
import { Book } from '../Book';
import {NgIf} from "@angular/common";
import {AddBookToCollectionComponent} from "../add-book-to-collection/add-book-to-collection.component";
import {UserService} from "../service/user.service";
import {UserResponse} from "../UserResponse";
import { LendBookComponent } from '../lend-book/lend-book.component';

@Component({
  selector: 'tab-menu',
  standalone: true,
  imports: [TabMenuModule, AccordionComponent, ReviewComponent, NgIf, AddBookToCollectionComponent, LendBookComponent],
  templateUrl: './tab-menu.component.html',
  styleUrl: './tab-menu.component.css'
})
export class TabMenuComponent implements OnInit{
  items: MenuItem[] | undefined
  activeItem: MenuItem | undefined

  @Input() book?: Book;
  user: UserResponse | undefined
  constructor(private userService: UserService,) {
  }

  ngOnInit(): void {
    this.items = this.getItems();
    this.activeItem = this.items[0]
    this.userService.getPrincipal().subscribe(x => {
      this.user = x;
    })
  }

  getItems(): MenuItem[]{
    return [
      { label: 'Description', icon: 'pi pi-book', command: (event) => {
        this.activeItem = event.item
      },},
      { label: 'Availability', icon: 'pi pi-check-circle', command: (event) => {
        this.activeItem = event.item
      },},
      { label: 'Leave Review', icon: 'pi pi-comment', command: (event) => {
        this.activeItem = event.item
      },},
      { label: 'Add Book to Your Collection', icon: 'pi pi-folder-plus', command: (event) => {
          this.activeItem = event.item
      },},
      {
        label: 'Lend Book', icon: 'pi pi-arrow-right-arrow-left', command: (event) => {
          this.activeItem = event.item
        }
      }
    ]
  }

}
