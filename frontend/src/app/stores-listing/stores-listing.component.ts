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
import {FileSelectEvent, FileUploadEvent, FileUploadModule} from 'primeng/fileupload';
import {TagModule} from 'primeng/tag';
import {MessageService} from 'primeng/api';
import {Author} from '../Author';
import {TableLoaderComponent} from '../loaders/table-loader/table-loader.component';
import {AuthorService} from "../service/author.service";
import {PublisherService} from "../service/publisher.service";
import {Publisher} from "../Publisher";
import {StoreDetails} from "../StoreDetails";
import {LibraryService} from "../service/library.service";
import {ImageBaseUrlPipe} from "../pipes/image-base-url.pipe";

interface UploadEvent {
  originalEvent: Event;
  files: File[];
}

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
    TableLoaderComponent,
    ImageBaseUrlPipe
  ],
  // providers: [MessageService],
  templateUrl: './stores-listing.component.html',
  styleUrl: './stores-listing.component.css',
})


export class StoresListingComponent implements OnInit {
  productDialog: boolean = false;
  isLoading: Boolean = true;

  deleteProductDialog: boolean = false;

  deleteProductsDialog: boolean = false;

  stores$: Observable<StoreDetails[]> = this.libraryService.getLocationsForLibrary();
  stores: StoreDetails[] | undefined;

  refreshEvent = this.libraryService.refreshEvent;

  emptyStore: StoreDetails = {
    id: 0,
    name: '',
    address: '',
    libraryName: '',
    imgUrl: ''
  };

  store: StoreDetails = {...this.emptyStore}
  uploadedFiles: any[] = [];

  selectedStores: StoreDetails[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  statuses: any[] = [];


  rowsPerPageOptions = [5, 10, 20];

  constructor(
    private libraryService: LibraryService,
    private messageService: MessageService
  ) {
  }

  onUpload(event: FileSelectEvent) {
    console.log(event)
    for(let file of event.files) {
      this.uploadedFiles.push(file);
    }
    console.log(this.uploadedFiles)
    this.messageService.add({severity: 'info', summary: 'File Uploaded', detail: ''});
  }

  ngOnInit() {
    this.isLoading = true;

    this.cols = [
      {field: 'id', header: 'ID'},
      {field: 'name', header: 'Name'},
      {field: 'address', header: 'Address'},
      {field: 'libraryName', header: 'Library'},
      {field: 'imgUrl', header: 'Image'},
    ];



    this.stores$.subscribe(x => {
      this.stores = x;
      this.isLoading = false;
    })

    this.refreshEvent.subscribe(() => this.fetchData())

    this.fields = this.cols.map(x => x.header)
  }

  fields: string[] = []

  fetchData() {

    this.stores$.subscribe(x => {
      this.stores = x;
    })

  }

  openNew() {
    this.store = {...this.emptyStore}
    this.submitted = false;
    this.productDialog = true;
    this.uploadedFiles = [];
  }

  deleteSelectedProducts() {
    this.deleteProductsDialog = true;
  }

  editProduct(store: StoreDetails) {
    this.store = {...store}
    this.productDialog = true;
  }

  deleteProduct(store: StoreDetails) {
    this.deleteProductDialog = true;
    this.store = {...store}
  }

  confirmDeleteSelected() {
    this.deleteProductsDialog = false;
    this.stores = this.stores?.filter((val) => !this.selectedStores.includes(val));
    this.messageService.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Products Deleted',
      life: 3000,
    });
    this.stores = [];
  }

  confirmDelete() {
    this.deleteProductDialog = false;
    this.libraryService.deleteLibraryStore(this.store.id).subscribe(
      res => {
        this.refreshEvent.next();
        this.messageService.add({severity: 'success', detail: `${this.store.name} is successfully deleted!`})
      },
      err => {
        this.messageService.add({severity: 'error', detail: 'An error occured while commiting the action!'})
      }
    )
    this.store = {...this.store}
  }

  hideDialog() {
    this.productDialog = false;
    this.submitted = false;
  }

  saveProduct() {
    this.submitted = true;
    this.libraryService.addStore(this.store, this.uploadedFiles[0]).subscribe(
      res => {
        this.refreshEvent.next();
        this.messageService.add({severity: 'success', detail: 'The book is successfully saved!'})
      },
      err => {
        this.messageService.add({severity: 'error', detail: JSON.stringify(err)})
      }
    )

    console.log(this.uploadedFiles)
    this.productDialog = false;

    this.store = {...this.emptyStore};
  }

  onGlobalFilter(table: Table, event: Event) {
      table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }
}
