import { Component, OnInit } from '@angular/core';
import { BookCardComponent } from '../book-card/book-card.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'list-books',
  standalone: true,
  imports: [BookCardComponent],
  templateUrl: './list-books.component.html',
  styleUrl: './list-books.component.css'
})
export class ListBooksComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router
  ) {}
  
  
  ngOnInit(): void {
    console.log(this.route.snapshot.paramMap.get("letter"));
    // console.log(this.route.paramMap.pipe(
    //   switchMap(x=> )
    // ))
    
  }

}
