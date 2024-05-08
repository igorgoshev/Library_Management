import { Component, OnInit } from '@angular/core';
import { Book } from '../Book';
import { BookService } from '../service/book.service';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';
import { TableModule } from 'primeng/table';
import { ToolbarModule } from 'primeng/toolbar';
import { RatingModule } from 'primeng/rating';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { DropdownModule } from 'primeng/dropdown';
import { DialogModule } from 'primeng/dialog';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { FileUploadModule } from 'primeng/fileupload';
import { TagModule } from 'primeng/tag';
import { MessagesModule } from 'primeng/messages';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-books-listing',
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
    TagModule
  ],
  providers: [MessageService],
  templateUrl: './books-listing.component.html',
  styleUrl: './books-listing.component.css',
})
export class BooksListingComponent implements OnInit {
  productDialog: boolean = false;

  deleteProductDialog: boolean = false;

  deleteProductsDialog: boolean = false;

  books$: Observable<Book[]> = this.bookService.getBooks();
  books: Book[] | undefined;

  book: Book = {
    isbn: '',
    name: '',
    description: '',
    imgUrl: '',
    publisher: '',
    id: -1,
    categories: [],
    authors: [],
    averageRating: 0
  };

  selectedBooks: Book[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  statuses: any[] = [];

  rowsPerPageOptions = [5, 10, 20];

  constructor(
    private bookService: BookService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.books$.subscribe((x) => {
      this.books = x;
    });

    this.cols = [
      { field: 'name', header: 'Book' },
      { field: 'isbn', header: 'ISBN' },
      // { field: 'category', header: 'Category' },
      { field: 'author', header: 'Authors' },
      { field: 'averageRating', header: 'Rating' },
      { field: 'inventoryStatus', header: 'Status' },
    ];

    this.statuses = [
      { label: 'INSTOCK', value: 'instock' },
      { label: 'LOWSTOCK', value: 'lowstock' },
      { label: 'OUTOFSTOCK', value: 'outofstock' },
    ];
  }

  openNew() {
    this.book = {
      isbn: '',
      name: '',
      description: '',
      imgUrl: '',
      publisher: '',
      id: -1,
      categories: [],
      authors: [],
      averageRating: 0
    };
    this.submitted = false;
    this.productDialog = true;
  }

  deleteSelectedProducts() {
    this.deleteProductsDialog = true;
  }

  editProduct(book: Book) {
    this.book = { ...book };
    this.productDialog = true;
  }

  deleteProduct(book: Book) {
    this.deleteProductDialog = true;
    this.book = { ...book };
  }

  confirmDeleteSelected() {
    this.deleteProductsDialog = false;
    this.books = this.books?.filter((val) => !this.selectedBooks.includes(val));
    this.messageService.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Products Deleted',
      life: 3000,
    });
    this.selectedBooks = [];
  }

  confirmDelete() {
    this.deleteProductDialog = false;
    this.books = this.books?.filter((val) => val.id !== this.book?.id);
    this.messageService.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Product Deleted',
      life: 3000,
    });
    this.book = {
      isbn: '',
      name: '',
      description: '',
      imgUrl: '',
      publisher: '',
      id: -1,
      categories: [],
      authors: [],
      averageRating: 0
    };
  }

  hideDialog() {
    this.productDialog = false;
    this.submitted = false;
  }

  saveProduct() {
    // this.submitted = true;
    // if (this.book?.name?.trim()) {
    //     if (this.product.id) {
    //         // @ts-ignore
    //         this..inventoryStatus = this.product.inventoryStatus.value ? this.product.inventoryStatus.value : this.product.inventoryStatus;
    //         this.products[this.findIndexById(this.product.id)] = this.product;
    //         this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
    //     } else {
    //         this.product.id = this.createId();
    //         this.product.code = this.createId();
    //         this.product.image = 'product-placeholder.svg';
    //         // @ts-ignore
    //         this.product.inventoryStatus = this.product.inventoryStatus ? this.product.inventoryStatus.value : 'INSTOCK';
    //         this.products.push(this.product);
    //         this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000 });
    //     }
    //     this.products = [...this.products];
    //     this.productDialog = false;
    //     this.product = {};
    // }
  }

  findIndexById(id: string): number {
    let index = -1;
    // for (let i = 0; i < this.products.length; i++) {
    //     if (this.products[i].id === id) {
    //         index = i;
    //         break;
    //     }
    // }

    return index;
  }

  createId(): string {
    let id = '';
    const chars =
      'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (let i = 0; i < 5; i++) {
      id += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return id;
  }

  // onGlobalFilter(table: Table, event: Event) {
  //     table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  // }
}
