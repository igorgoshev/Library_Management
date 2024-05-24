import {Component, Input, OnInit} from '@angular/core';
import { BookCardComponent } from '../book-card/book-card.component';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { BookService } from '../service/book.service';
import { BookCard } from '../Book-Card';
import { CarouselBookCardComponent } from '../carousel-book-card/carousel-book-card.component';
import { KeyValuePipe } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownChangeEvent, DropdownModule } from 'primeng/dropdown';
import {routes} from "../app.routes";
import { Subject, debounceTime, distinctUntilChanged, switchMap, filter } from 'rxjs';
import { Filter } from '../Filter';
import { Category } from '../Category';
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
  query$: Subject<Filter> = new Subject();
  selectedCategory: string | undefined

  filter: Filter = {
    book: "",
    category: ""
  }

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: BookService,
    ) {
    route.data.subscribe(x => {
      this.listType = x['listType']
    })
  }

  search(title: string){
    this.filter = {
      ...this.filter,
      book: title
    }

    this.query$.next(this.filter)
  }

  onChange(event: DropdownChangeEvent){
    
    this.filter = {
      ...this.filter,
      category: event.value 
    }

    this.query$.next(this.filter)
  }


  ngOnInit(): void {

    this.query$.pipe(
      debounceTime(400),
      distinctUntilChanged(),
      switchMap(f => this.service.searchBookByNameAndCategory(f))
    ).subscribe(result => this.topBookByLetters = result);

    this.fetchData()
    
    this.service.getAvailableCategories()
      .subscribe(res => {
        this.categories = res.map(c => c.name)
      })

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
  categories: String[] | undefined


  filterByLetter(letter: string) {
    this.selectedLetter = letter;
    this.fetchData(this.selectedLetter)
  }




}
