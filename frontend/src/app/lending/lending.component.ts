import {Component, Input} from '@angular/core';
import {InputTextModule} from "primeng/inputtext";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {CarouselBookCardComponent} from "../carousel-book-card/carousel-book-card.component";
import {KeyValuePipe} from "@angular/common";
import {SearchComponent} from "../search/search.component";
import {BookCard} from "../Book-Card";
import {BookService} from "../service/book.service";
import {Observable} from "rxjs";
import {Book} from "../Book";
import {LendingBookDetailsComponent} from "./lending-book-details/lending-book-details.component";
import {StepperModule} from "primeng/stepper";
import {ButtonModule} from "primeng/button";

@Component({
  selector: 'app-lending',
  standalone: true,
  imports: [
    InputTextModule,
    RouterLink,
    CarouselBookCardComponent,
    KeyValuePipe,
    SearchComponent,
    LendingBookDetailsComponent,
    StepperModule,
    ButtonModule
  ],
  templateUrl: './lending.component.html',
  styleUrl: './lending.component.css'
})
export class LendingComponent {
  alphabet: String[] = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '#', 'ALL'];
  selectedLetter: string = '';
  loading = true;
  topBookByLetters: Map<String, BookCard[]> | undefined

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: BookService,
  ) {
  }

  fetchData(letter: string | undefined = undefined) {
    this.service.getBooksByLetter(letter)
      .subscribe(
        res => {
          this.topBookByLetters = res
          this.loading = false
        }
      )
  }


  ngOnInit(): void {
    this.fetchData()
  }

  filterByLetter(letter: string) {
    this.selectedLetter = letter;
    this.fetchData(this.selectedLetter)
  }
}
