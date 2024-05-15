import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookService } from '../service/book.service';
import { Book } from '../Book';
import { RatingModule } from 'primeng/rating';
import { FormsModule } from '@angular/forms';
import {MenuItem, MessageService} from 'primeng/api';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ReactiveFormsModule } from '@angular/forms';
import { TabMenuComponent } from '../tab-menu/tab-menu.component';
import {NgIf, NgOptimizedImage} from "@angular/common";
import { ReviewCarouselComponent } from '../review-carousel/review-carousel.component';
import { BookLoaderComponent } from '../loaders/book-loader/book-loader.component';
import { ReviewLoaderComponent } from '../loaders/review-loader/review-loader.component';

@Component({
  selector: 'book-details',
  standalone: true,
  imports: [RatingModule, FormsModule, InputTextareaModule, ReactiveFormsModule, TabMenuComponent, NgOptimizedImage, NgIf, ReviewCarouselComponent, BookLoaderComponent, ReviewLoaderComponent],
  templateUrl: './book-details.component.html',
  styleUrl: './book-details.component.css',
  encapsulation: ViewEncapsulation.None,
})
export class BookDetailsComponent implements OnInit{
  constructor(
    private route: ActivatedRoute,
    private service: BookService
    ){}

  selectedLibraryStore: any;

  bookId: number | undefined
  loading = true
  book: Book | undefined

  items: MenuItem[]|  undefined
  activeItem: MenuItem | undefined;

  ngOnInit(): void {

    this.bookId = Number.parseInt(this.route.snapshot.paramMap.get('id')!)
    this.service.getBookDetails(this.bookId).subscribe({
      next: res => {
        this.book = res
      },
      error: err => {
        console.log(err)
      },
      complete: () => this.loading = false
  })
  
  }



}
