import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Book } from '../Book';
import { ReactiveFormsModule } from '@angular/forms';
import { Category } from '../Category';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  constructor(private http: HttpClient) { }
  
  getBooks() {
    return this.http.get<Book[]>('http://localhost:8080/api/books');
  }

  getAvailableCategories() {
    return this.http.get<Category[]>('http://localhost:8080/api/categories');
  }

}
