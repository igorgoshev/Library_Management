import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Book } from '../Book';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  constructor(private http: HttpClient) { }
  
  getBooks() {
    return this.http.get<Book[]>('http://localhost:8080/api/books');
  }

}
