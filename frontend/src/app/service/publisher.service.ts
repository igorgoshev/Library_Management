import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Publisher} from "../Publisher";
import {catchError, Subject, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PublisherService {

  refreshEvent = new Subject<void>();

  constructor(private http: HttpClient) {
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

  getPublishers() {
    return this.http.get<Publisher[]>('http://localhost:8080/api/publishers');
  }

  deletePublisher(id: number) {
    return this.http.delete<Publisher>(`http://localhost:8080/api/publishers/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  addPublisher(publisher: Publisher) {
    return this.http.post(`http://localhost:8080/api/publishers`, publisher).pipe(
      catchError(this.handleError)
    );
  }
}
