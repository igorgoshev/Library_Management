import {Component, Input, OnInit} from '@angular/core';
import { BookCardComponent } from '../book-card/book-card.component';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { BookService } from '../service/book.service';
import { BookCard } from '../Book-Card';
import { CarouselBookCardComponent } from '../carousel-book-card/carousel-book-card.component';
import { KeyValuePipe } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import {routes} from "../app.routes";
import { Subject, debounceTime, distinctUntilChanged, switchMap } from 'rxjs';
@Component({
  selector: 'list-books',
  standalone: true,
  imports: [CarouselBookCardComponent, KeyValuePipe, InputTextModule, DropdownModule, RouterLink],
  templateUrl: './list-books.component.html',
  styleUrl: './list-books.component.css'

})
export class ListBooksComponent implements OnInit {

  @Input() listType: string | undefined;
  loading = true;
  topBookByLetters: Map<String, BookCard[]> | undefined
  selectedLetter: string = '';
  query$: Subject<string> = new Subject();

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: BookService,
    ) {
    route.data.subscribe(x => {
      this.listType = x['listType']
    })
  }

  search(book: string){

    this.query$.next(book)

  }


  ngOnInit(): void {

    this.query$.pipe(
      debounceTime(400),
      distinctUntilChanged(),
      switchMap(query => this.service.searchBookByName(query))
    ).subscribe(result => this.topBookByLetters = result);

    this.fetchData()

    this.service.getTopBooksByLetter()
    .subscribe(
      res => {
        this.topBookByLetters = res
        this.loading = false
      }
    )
  }

  //=============================

  fetchData(letter: string | undefined = undefined) {




    this.service.getAllBooksByLetter(letter)
      .subscribe(
        res => {
          this.topBookByLetters = res
          this.loading = false
        }
      )
  }


  alphabet: String[] = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '#'];
  categories: String[] = ['Horror', 'Drama', 'Action'];


  filterByLetter(letter: string) {
    this.selectedLetter = letter;
    this.fetchData(this.selectedLetter)
  }




}
