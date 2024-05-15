import {Component, Input} from '@angular/core';
import {Book} from "../../Book";
import {AccordionModule} from "primeng/accordion";
import {BookCard} from "../../Book-Card";
import {JsonPipe} from "@angular/common";

@Component({
  selector: 'app-lending-book-details',
  standalone: true,
  imports: [
    AccordionModule,
    JsonPipe
  ],
  templateUrl: './lending-book-details.component.html',
  styleUrl: './lending-book-details.component.css'
})
export class LendingBookDetailsComponent {
  @Input() book: BookCard | undefined;
}
