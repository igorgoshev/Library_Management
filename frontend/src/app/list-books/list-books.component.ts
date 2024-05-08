import { Component, OnInit } from '@angular/core';
import { BookCardComponent } from '../book-card/book-card.component';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../service/book.service';
import { BookCard } from '../Book-Card';
import { KeyValuePipe } from '@angular/common';

@Component({
  selector: 'list-books',
  standalone: true,
  imports: [BookCardComponent, KeyValuePipe],
  templateUrl: './list-books.component.html',
  styleUrl: './list-books.component.css'
})
export class ListBooksComponent implements OnInit {

  loading = true;
  topBookByLetters: Map<String, BookCard[]> | undefined

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: BookService
    ) {}
  
  
  ngOnInit(): void {
    this.service.getTopBooksByLetter()
    .subscribe(
      res => {
        this.topBookByLetters = res
        this.loading = false 
      }
    )
  }

}
