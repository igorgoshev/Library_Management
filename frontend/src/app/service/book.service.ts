import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Book } from '../Book';
import { ReactiveFormsModule } from '@angular/forms';
import { Category } from '../Category';
import { BookCard } from '../Book-Card';
import { Author } from '../Author';
import { Publisher } from '../Publisher';
import { catchError, Observable, Subject, throwError } from 'rxjs';
import { Review } from '../Review';
import { BookAvailability } from '../BookAvailability';
import {BookLendingDetails} from "../BookLendingDetails";

@Injectable({
  providedIn: 'root'
})
export class BookService {
  constructor(private http: HttpClient) {
    this.refreshEvent.subscribe(() => this.getBooks())
  }
  public refreshEvent = new Subject<void>();

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type':  'application/json',
      Authorization: 'my-auth-token'
    })
  };

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('An error occurred:', error.error);
    } else {
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }

  getBooks() {
    return this.http.get<Book[]>('http://localhost:8080/api/books');
  }

  getAvailableCategories() {
    return this.http.get<Category[]>('http://localhost:8080/api/categories');
  }

  getAvailableAuthors() {
    return this.http.get<Author[]>('http://localhost:8080/api/authors');
  }

  getAvailablePublishers() {
    return this.http.get<Publisher[]>('http://localhost:8080/api/publishers');
  }

  getTopBooksByLetter() {
    return this.http.get<Map<String, BookCard[]>>('http://localhost:8080/api/books/getTopByLetters')
  }

  addBook(book: Book): Observable<Book> {
    return this.http.post<Book>('http://localhost:8080/api/books/add', book)
      .pipe(
        catchError(this.handleError)
      );
  }

  getBookDetails(id: number){
    return this.http.get<Book>(`http://localhost:8080/api/books/${id}`)
  }

  deleteBook(id: number): Observable<Book> {
    return this.http.delete<Book>(`http://localhost:8080/api/books/delete/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  addReview(id: number, review: Review){
    return this.http.post<Review>(`http://localhost:8080/api/books/review/${id}`, review)
      .pipe(
        catchError(this.handleError)
      )
  }

  getBookAvailability(id: number){
    return this.http.get<BookAvailability[]>(`http://localhost:8080/api/books/availability/${id}`)
  }

  getBooksByLetter(letter: string | undefined) {
    return this.http.get<Map<String, BookLendingDetails[]>>('http://localhost:8080/api/books/getAllByLetters' + (letter ? `?letter=${letter}` : ""))
  }

  getReviewsByBook(id: number){
    return this.http.get<Review[]>(`http://localhost:8080/api/books/reviews/${id}`)
  }

  lendBook(userId: number, copyId: number) {
    return this.http.post(`http://localhost:8080/api/books/lend`, {
      userId: userId,
      copyId: copyId
    }).pipe(
      catchError(this.handleError)
    )
  }

  addBookToWishlist(id: number){
    return this.http.get(`http://localhost:8080/api/books/wishlist/add/${id}`)
  }

  bookExistsInWishlist(id: number){
    return this.http.get<boolean>(`http://localhost:8080/api/books/wishlist/exist/${id}`)
  }

}
