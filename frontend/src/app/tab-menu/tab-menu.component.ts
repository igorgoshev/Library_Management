import { Component, Input, OnInit } from '@angular/core';
import { TabMenuModule } from 'primeng/tabmenu';
import { AccordionComponent } from '../accordion/accordion.component';
import { ReviewComponent } from '../review/review.component';
import { MenuItem } from 'primeng/api';
import { Book } from '../Book';

@Component({
  selector: 'tab-menu',
  standalone: true,
  imports: [TabMenuModule, AccordionComponent, ReviewComponent],
  templateUrl: './tab-menu.component.html',
  styleUrl: './tab-menu.component.css'
})
export class TabMenuComponent implements OnInit{
  items: MenuItem[] | undefined
  activeItem: MenuItem | undefined

  @Input() book: Book | undefined

  ngOnInit(): void {
    this.items = this.getItems();
    this.activeItem = this.items[0]
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
      },}
    ]
  }

}
