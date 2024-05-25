import {Component, OnInit} from '@angular/core';
import {Book} from '../Book';
import {BookService} from '../service/book.service';
import {Observable, Subject} from 'rxjs';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {ButtonModule} from 'primeng/button';
import {MultiSelectModule} from 'primeng/multiselect';
import {RippleModule} from 'primeng/ripple';
import {ToastModule} from 'primeng/toast';
import {TableModule} from 'primeng/table';
import {ToolbarModule} from 'primeng/toolbar';
import {RatingModule} from 'primeng/rating';
import {InputTextModule} from 'primeng/inputtext';
import {InputNumberModule} from 'primeng/inputnumber';
import {DropdownModule} from 'primeng/dropdown';
import {DialogModule} from 'primeng/dialog';
import {RadioButtonModule} from 'primeng/radiobutton';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {FileUploadModule} from 'primeng/fileupload';
import {TagModule} from 'primeng/tag';
import {MessageService} from 'primeng/api';
import {Category} from '../Category';
import {Author} from '../Author';
import {Publisher} from '../Publisher';

@Component({
  selector: 'app-lendings-listing',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    FileUploadModule,
    FormsModule,
    ButtonModule,
    RippleModule,
    ToastModule,
    ToolbarModule,
    RatingModule,
    InputTextModule,
    InputTextareaModule,
    DropdownModule,
    RadioButtonModule,
    InputNumberModule,
    DialogModule,
    TagModule,
    MultiSelectModule,
    TableLoaderComponent,
    RouterLink,
    AvatarModule
  ],
  templateUrl: './trades-listing.component.html',
  styleUrl: './trades-listing.component.css',
})
export class TradesListingComponent implements OnInit {
  productDialog: boolean = false;
  isLoading: Boolean = true;

  deleteProductDialog: boolean = false;

  deleteProductsDialog: boolean = false;

  books$: Observable<LentBookDetails[]> = this.bookService.getActiveLendingsForStore();
  books: LentBookDetails[] | undefined;


  book: LentBookDetails | undefined;

  emptyBook: Book = {
    isbn: '',
    name: '',
    description: '',
    imgUrl: '',
    publisher: '',
    id: -1,
    categories: [],
    authors: [],
    averageRating: 0,
    numPages: 0,
    year: 0
  };

  selectedBooks: LentBookDetails[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  statuses: any[] = [];

  rowsPerPageOptions = [5, 10, 20];

  constructor(
    private bookService: BookService,
    private messageService: MessageService,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    this.isLoading = true;

    this.route.paramMap.subscribe(x => {
      if(x.get('id') != null)
      {
        this.bookService.getAllTradesForCustomerBook(parseInt(x.get('id') ?? '')).subscribe(x => {
          this.books = x;
          this.isLoading = false;
        })
      }
    })


    this.cols = [
      {field: 'id', header: 'ID'},
      {field: 'customer', header: 'Customer'},
      {field: 'name', header: 'Book Name'},
      {field: 'imgUrl', header: 'Image'},
      {field: 'isbn', header: 'ISBN'},
      {field: 'dateFrom', header: 'Lent on'},
      {field: 'dateTo', header: 'Lent to'},
    ];

    this.fields = this.cols.map(x => x.header)
  }

  fields: string[] = []

  fetchData() {
    this.books$.subscribe((x) => {
      this.books = x;
    });

  }


  deleteProduct(book: LentBookDetails) {
    this.deleteProductDialog = true;
    this.book = book
    // this.book = { ...book, categories: book.categories.map(x => this.categories?.find(cat => cat.name === x)?.id.toString() ?? "")};
  }

  confirmDelete() {
    this.deleteProductDialog = false;
    if (this.book) {
      this.bookService.finishLending(this.book.id).subscribe(
        next => {
          this.messageService.add({
            severity: 'success',
            summary: 'Successful',
            detail: 'Books Successfully returned',
            life: 3000,
          })
          window.location.reload()
        },
        error => this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'There was a problem while updateing the book state',
          life: 3000,
        })
      )
      this.book = undefined
    }
  }

  hideDialog() {
    this.productDialog = false;
    this.submitted = false;
  }


  // onGlobalFilter(table: Table, event: Event) {
  //     table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  // }
}

import {TableLoaderComponent} from '../loaders/table-loader/table-loader.component';
import {LentBookDetails} from "../LentBookDetails";
import {ActivatedRoute, RouterLink} from "@angular/router";
import {AvatarModule} from "primeng/avatar";
