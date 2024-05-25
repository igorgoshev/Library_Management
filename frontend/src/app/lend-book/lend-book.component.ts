import { CustomerBook } from './../CustomerBook';
import { Component, Input, OnInit } from '@angular/core';
import { Book } from '../Book';
import { BookService } from '../service/book.service';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'lend-book',
  standalone: true,
  imports: [CardModule, ButtonModule],
  templateUrl: './lend-book.component.html',
  styleUrl: './lend-book.component.css'
})
export class LendBookComponent implements OnInit {
  
  constructor(private service: BookService){

  }
  isLoading = false;
  isDisabled = false;
  @Input() book: Book | undefined
  customerBooks: CustomerBook[] | undefined

  ngOnInit(): void {
  
    this.service.getCustomerBooks(this.book?.id!)
      .subscribe( res => this.customerBooks = res)
  }

  onLend(cb: CustomerBook){
    this.isLoading = true
    this.service.lendBookToCustomer(cb.id)
      .subscribe(
        res => {
          this.isLoading = false
          this.isDisabled = true
        }
      )
    
  }



}
