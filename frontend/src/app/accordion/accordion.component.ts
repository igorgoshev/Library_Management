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

  ngOnInit(): void {
    this.service.getBookAvailability(this.book?.id!!)
      .subscribe( 
        res => this.bookAvailability = res 
      )
  }

}
