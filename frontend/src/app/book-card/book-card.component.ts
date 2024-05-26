import { Component, Input, input } from '@angular/core';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { BookCard } from '../Book-Card';
import {CustomerBookCard} from "../CustomerBookCard";
import {BadgeModule} from "primeng/badge";
import {ImageBaseUrlPipe} from "../pipes/image-base-url.pipe";
import {ImageModule} from "primeng/image";


@Component({
  selector: 'book-card',
  standalone: true,
  imports: [CardModule, ButtonModule, BadgeModule, ImageBaseUrlPipe, ImageModule],
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.css'
})
export class BookCardComponent {

@Input() book: CustomerBookCard | undefined

}
