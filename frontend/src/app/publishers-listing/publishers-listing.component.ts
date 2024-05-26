import {Component, OnInit} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {ButtonModule} from 'primeng/button';
import {MultiSelectModule} from 'primeng/multiselect';
import {RippleModule} from 'primeng/ripple';
import {ToastModule} from 'primeng/toast';
import {Table, TableModule} from 'primeng/table';
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
import {Author} from '../Author';
import {TableLoaderComponent} from '../loaders/table-loader/table-loader.component';
import {AuthorService} from "../service/author.service";
import {PublisherService} from "../service/publisher.service";
import {Publisher} from "../Publisher";

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
    TagModule,
    MultiSelectModule,
    TableLoaderComponent
  ],
  // providers: [MessageService],
  templateUrl: './publishers-listing.component.html',
  styleUrl: './publishers-listing.component.css',
})
export class PublishersListingComponent implements OnInit {
  productDialog: boolean = false;
  isLoading: Boolean = true;

  deleteProductDialog: boolean = false;

  deleteProductsDialog: boolean = false;

  publishers$: Observable<Publisher[]> = this.publisherService.getPublishers();
  publishers: Publisher[] | undefined;

  refreshEvent = this.publisherService.refreshEvent;

  emptyPublisher: Publisher = {
    id: 0,
    name: '',
    address: '',
  };

  publisher: Publisher = {...this.emptyPublisher}

  selectedPublishers: Publisher[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  statuses: any[] = [];

  rowsPerPageOptions = [5, 10, 20];

  constructor(
    private publisherService: PublisherService,
    private messageService: MessageService
  ) {
  }

  ngOnInit() {
    this.isLoading = true;

    this.cols = [
      {field: 'id', header: 'ID'},
      {field: 'name', header: 'Name'},
      {field: 'address', header: 'Address'},
    ];

    this.publishers$.subscribe(x => {
      this.publishers = x;
      this.isLoading = false;
    })

    this.refreshEvent.subscribe(() => this.fetchData())

    this.fields = this.cols.map(x => x.header)
  }

  fields: string[] = []

  fetchData() {

    this.publishers$.subscribe(x => {
      this.publishers = x;
    })

  }

  openNew() {
    this.publisher = {...this.emptyPublisher}
    this.submitted = false;
    this.productDialog = true;
  }

  deleteSelectedProducts() {
    this.deleteProductsDialog = true;
  }

  editProduct(publisher: Publisher) {
    this.publisher = {...publisher}
    this.productDialog = true;
  }

  deleteProduct(publisher: Publisher) {
    this.deleteProductDialog = true;
    this.publisher = {...publisher}
  }

  confirmDeleteSelected() {
    this.deleteProductsDialog = false;
    this.publishers = this.publishers?.filter((val) => !this.selectedPublishers.includes(val));
    this.messageService.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Products Deleted',
      life: 3000,
    });
    this.publishers = [];
  }

  confirmDelete() {
    this.deleteProductDialog = false;
    this.publisherService.deletePublisher(this.publisher.id).subscribe(
      res => {
        this.refreshEvent.next();
        this.messageService.add({severity: 'success', detail: `${this.publisher.name} is successfully deleted!`})
      },
      err => {
        this.messageService.add({severity: 'error', detail: 'An error occured while commiting the action!'})
      }
    )
    this.publisher = {...this.publisher}
  }

  hideDialog() {
    this.productDialog = false;
    this.submitted = false;
  }

  saveProduct() {
    this.submitted = true;
    this.publisherService.addPublisher(this.publisher).subscribe(
      res => {
        this.refreshEvent.next();
        this.messageService.add({severity: 'success', detail: 'The book is successfully saved!'})
      },
      err => {
        this.messageService.add({severity: 'error', detail: JSON.stringify(err)})
      }
    )


    this.productDialog = false;

    this.publisher = {...this.emptyPublisher};
  }

  onGlobalFilter(table: Table, event: Event) {
      table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }
}
