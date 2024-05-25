import {Component, OnInit} from '@angular/core';
import {ButtonModule} from "primeng/button";
import {FileUploadModule} from "primeng/fileupload";
import {RippleModule} from "primeng/ripple";
import {SharedModule} from "primeng/api";
import {ToolbarModule} from "primeng/toolbar";
import {CustomerBookCard} from "../CustomerBookCard";
import {BookService} from "../service/book.service";
import {Router, RouterLink} from "@angular/router";
import {BookCardComponent} from "../book-card/book-card.component";

@Component({
  selector: 'app-customer-books',
  standalone: true,
  imports: [
    ButtonModule,
    FileUploadModule,
    RippleModule,
    SharedModule,
    ToolbarModule,
    BookCardComponent,
    RouterLink
  ],
  templateUrl: './customer-books.component.html',
  styleUrl: './customer-books.component.css'
})
export class CustomerBooksComponent implements OnInit{

  books: CustomerBookCard[] = []

  constructor(private bookService: BookService, private router: Router) {

  }

  ngOnInit(): void {
    this.bookService.getAllCustomerBooks().subscribe(x => {
      this.books = x
    })
  }



  openNew() {

  }
}
