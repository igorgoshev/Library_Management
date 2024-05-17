import { BookAvailability } from './../BookAvailability';
import { Component, Input, OnInit } from '@angular/core';
import { AccordionModule } from 'primeng/accordion';
import { ButtonModule } from 'primeng/button';
import { Book } from '../Book';
import { BookService } from '../service/book.service';

@Component({
  selector: 'accordion',
  standalone: true,
  imports: [ButtonModule, AccordionModule],
  templateUrl: './accordion.component.html',
  styleUrl: './accordion.component.css'
})

export class AccordionComponent implements OnInit {

  constructor(private service: BookService){

  }

  @Input() book: Book | undefined
  bookAvailability: BookAvailability[] | undefined

  loading = false;
  icon = "pi pi-heart"
  label = "Add to Wish list"
  description = "This book is currently unavailable! Please come back later or Add to wishlist!"
  isInWishList = false;
  loadingButton = false;
  isDisabled = false;


  getStatusColor(statusCode: number): string {
    if (statusCode === 0) {
      return 'red';
    } else if (statusCode === 1) {
      console.log('red')
      return '#FFBF00';
    } else {
      return 'green';
    }
  }

  setButtonProperties(){
    if (this.isInWishList){
      this.icon = "pi pi-heart-fill"
      this.label = "Already added to Wish list"
      this.description = "This book is currently unavailable! Please come back later!"
      this.isDisabled = true
    }
  }

  

  ngOnInit(): void {
    this.service.getBookAvailability(this.book?.id!!)
      .subscribe(
        res => this.bookAvailability = res
      )

      this.service.bookExistsInWishlist(this.book?.id!!)
        .subscribe(
          res => {
            console.log(res)
            this.isInWishList = res
            this.setButtonProperties()
          }
        )
  }

  onSubmit(){
    this.loadingButton = true
    this.service.addBookToWishlist(this.book?.id!!)
      .subscribe(
        res => {
          console.log(res)
          this.loadingButton = false
        }
      )
  }

}
