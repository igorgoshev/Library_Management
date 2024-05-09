import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccordionModule } from 'primeng/accordion';
import { BookService } from '../service/book.service';
import { Book } from '../Book';

@Component({
  selector: 'book-details',
  standalone: true,
  imports: [AccordionModule,],
  templateUrl: './book-details.component.html',
  styleUrl: './book-details.component.css'
})
export class BookDetailsComponent implements OnInit{
  constructor(
    private route: ActivatedRoute,
    private service: BookService
    ){

  }

  bookId: number | undefined
  loading = true
  book: Book | undefined

  ngOnInit(): void {
    this.bookId = Number.parseInt(this.route.snapshot.paramMap.get('id')!)
    this.service.getBookDetails(this.bookId).subscribe(res=> {
      this.book = res
      this.loading = false
    })
  }

  


  

  
  

}
