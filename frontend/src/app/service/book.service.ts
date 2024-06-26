import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Injectable, OnInit} from '@angular/core';
import {Book} from '../Book';
import {ReactiveFormsModule} from '@angular/forms';
import {Category} from '../Category';
import {BookCard} from '../Book-Card';
import {Author} from '../Author';
import {Publisher} from '../Publisher';
import {catchError, Observable, Subject, switchMap, tap, throwError} from 'rxjs';
import {Review} from '../Review';
import {BookAvailability} from '../BookAvailability';
import {BookLendingDetails} from "../BookLendingDetails";
import {LentBookDetails} from "../LentBookDetails";
import {ReservedBookDetails} from "../ReservedBookDetails";
import {AvailableBook} from "../AvailableBook";
import { Filter } from '../Filter';
import {CustomerBookCard} from "../CustomerBookCard";
import { CustomerBook } from '../CustomerBook';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http: HttpClient) {
    this.refreshEvent.subscribe(() => this.getBooks())
    this.refreshWishlist.subscribe(() => this.getUsersWishlist())
  }

  public refreshEvent = new Subject<void>();
  public refreshWishlist = new Subject<void>();

  getAuthToken() {
    return {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      })
    };
  }

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
    return this.http.get<Map<String, BookCard[]>>('http://localhost:8080/api/books/getTopByLetters');
  }

  addBook(book: Book, file: File): Observable<Book> {
    const formData = new FormData();
    const bookBlob = new Blob([JSON.stringify(book)], { type: 'application/json' });

    formData.append('book', bookBlob);
    formData.append('imgFile', file);
    return this.http.post<Book>('http://localhost:8080/api/books/add', formData)
      .pipe(
        catchError(this.handleError)
      );
  }




  getBookDetails(id: number) {
    return this.http.get<Book>(`http://localhost:8080/api/books/${id}`);
  }

  deleteBook(id: number): Observable<Book> {
    return this.http.delete<Book>(`http://localhost:8080/api/books/delete/${id}`)
    .pipe(
      catchError(this.handleError)
    );
  }

  addReview(id: number, review: Review) {
  return this.http.post<Review>(`http://localhost:8080/api/books/review/${id}`, review, this.getAuthToken())
    .pipe(
     catchError(this.handleError)
    );
  }

  getBookAvailability(id: number) {
    return this.http.get<BookAvailability[]>(`http://localhost:8080/api/books/availability/${id}`)
  }

  getBooksByLetter(letter: string | undefined) {
    return this.http.get<Map<String, BookLendingDetails[]>>('http://localhost:8080/api/books/getAllByLetters' + (letter ? `?letter=${letter}` : ""));
  }


  searchBookByNameAndCategory(filter: Filter) {
    return this.http.get<Map<String, BookCard[]>>('http://localhost:8080/api/books/getBooksContaining' + `?query=${filter.book}&category=${filter.category}`)
  }


  searchBookByNameAdmin(query: string) {
    return this.http.get<Map<String, BookLendingDetails[]>>('http://localhost:8080/api/books/getBooksContainingAdmin' + `?query=${query}`)
  }

  getAllBooksByLetter(letter: string | undefined){
    return this.http.get<Map<String, BookCard[]>>('http://localhost:8080/api/books/getAllBooksByLetters' + (letter ? `?letter=${letter}` : ""))
  }


  getReviewsByBook(id: number) {
    return this.http.get<Review[]>(`http://localhost:8080/api/books/reviews/${id}`);
  }

  lendBook(userId: number, copyId: number) {
    return this.http.post(`http://localhost:8080/api/books/lend`, {
      userId: userId,
      copyId: copyId
    }).pipe(
      catchError(this.handleError)
    );
  }

  addBookToWishlist(id: number) {
    return this.http.get(`http://localhost:8080/api/books/wishlist/add/${id}`, this.getAuthToken());
  }

  bookExistsInWishlist(id: number) {
    return this.http.get<boolean>(`http://localhost:8080/api/books/wishlist/exist/${id}`, this.getAuthToken());
  }

  reserveBook(bookId: number, storeId: number) {
    return this.http.get<boolean>(`http://localhost:8080/api/books/reserve/${bookId}/${storeId}`, this.getAuthToken())
  }

  reservationExist(bookId: number) {
    return this.http.get<boolean>(`http://localhost:8080/api/books/reserve/exist/${bookId}`, this.getAuthToken())
  }

  getActiveLendingsForStore() {
    return this.http.get<LentBookDetails[]>(`http://localhost:8080/api/books/lent/active`, this.getAuthToken());
  }

  getAllLendingsForStore(id: number) {
    return this.http.get<LentBookDetails[]>(`http://localhost:8080/api/books/lent/${id}`);
  }

  finishLending(id: number) {
    return this.http.post(`http://localhost:8080/api/books/lent/${id}/finish`, null);
  }

  getUserReservations(id: number | undefined) {
    return this.http.get<ReservedBookDetails[]>(`http://localhost:8080/api/books/reservationsForUser`, this.getAuthToken());
  }

  cancelReservation(id: number) {
    return this.http.post(`http://localhost:8080/api/books/reservations/${id}/cancel`, null, this.getAuthToken());
  }

  getUsersWishlist() {
    return this.http.get<Book[]>('http://localhost:8080/api/books/wishlist', this.getAuthToken());
  }

  getUserLoansAsCards() {
    return this.http.get<BookCard[]>('http://localhost:8080/api/books/loans', this.getAuthToken())
  }

  getPopularBooks() {
    return this.http.get<BookCard[]>('http://localhost:8080/api/books/popular');
  }

  getPopularCategories() {
    return this.http.get<BookCard[]>('http://localhost:8080/api/categories/popular');
  }

  getCopiesForBook(bookId: number) {
    return this.http.get<AvailableBook[]>(`http://localhost:8080/api/books/${bookId}/copies`, this.getAuthToken());
  }

  addCopiesForBook(bookId: number, status: string, quantity: number) {
    return this.http.post(`http://localhost:8080/api/books/copies/add`, {
      bookId: bookId,
      status: status,
      quantity: quantity
    }, this.getAuthToken());
  }

  getStoreReservations() {
    return this.http.get<LentBookDetails[]>('http://localhost:8080/api/books/reservations', this.getAuthToken());
  }

  finishReservation(id: number) {
    return this.http.get(`http://localhost:8080/api/books/reservations/${id}/finish`, this.getAuthToken());
  }

  deleteFromWishlist(id: number) {
    return this.http.get(`http://localhost:8080/api/books/wishlist/delete/${id}`, this.getAuthToken());
  }

  getAllCustomerBooks() {
    return this.http.get<CustomerBookCard[]>('http://localhost:8080/api/books/customer/all', this.getAuthToken());
  }

  getAllTradesForCustomerBook(id: number) {
    return this.http.get<LentBookDetails[]>(`http://localhost:8080/api/books/customer/${id}`, this.getAuthToken());
  }

  addBookToCustomerCollection(id: number) {
    return this.http.get(`http://localhost:8080/api/books/customer/add/${id}`, this.getAuthToken());
  }

  getCustomerBooks(id: number){
    return this.http.get<CustomerBook[]>(`http://localhost:8080/api/books/customersContaining/${id}`)
  }

  lendBookToCustomer(id: number){
    return this.http.get(`http://localhost:8080/api/books/lend/${id}`, this.getAuthToken())
  }

  finishCustomerLending(id: number) {
      return this.http.get(`http://localhost:8080/api/books/customer/${id}/finish`, this.getAuthToken());
  }

  deleteCopy(id: number) {
    return this.http.get(`http://localhost:8080/api/books/copies/${id}/delete`, this.getAuthToken());
  }

}
