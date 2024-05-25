import {Component, Input} from '@angular/core';
import {ButtonModule} from "primeng/button";
import {BookService} from "../service/book.service";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";

@Component({
  selector: 'app-add-book-to-collection',
  standalone: true,
  imports: [
    ButtonModule,
    ToastModule
  ],
  templateUrl: './add-book-to-collection.component.html',
  styleUrl: './add-book-to-collection.component.css'
})
export class AddBookToCollectionComponent {
  @Input() bookId: number | undefined;
  loading: boolean = false;
  constructor(private bookService: BookService, private messageService: MessageService) {
  }

  onAddButtonClick() {
    this.loading = true;
    this.bookService.addBookToCustomerCollection(this.bookId ?? 0).subscribe(
      {
        next: (res) => this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Book successfully added to your collection!',
          life: 3000,
        }),
        error: (err) => this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error: The book could not be added',
          life: 3000,
        }),
        complete: () => this.loading = false
      }
    )
  }
}
