import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {StoreDetails} from "../StoreDetails";
import * as http from "node:http";

@Injectable({
  providedIn: 'root'
})
export class LibraryService {

  refreshEvent = new Subject<void>();

  constructor(private http: HttpClient) { }

  getAuthToken() {
    return {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      })
    };
  }

  getPopularStores(): Observable<StoreDetails[]> {
    return this.http.get<StoreDetails[]>('http://localhost:8080/api/libraries/stores/popular');
  }

  getLocationsForLibrary(): Observable<StoreDetails[]> {
    return this.http.get<StoreDetails[]>('http://localhost:8080/api/libraries/stores', this.getAuthToken());
  }

  deleteLibraryStore(id: number) {
    return this.http.delete<StoreDetails[]>(`http://localhost:8080/api/libraries/stores/${id}`, this.getAuthToken());
  }

  addStore(store: StoreDetails) {
    return this.http.post<StoreDetails>('http://localhost:8080/api/libraries/stores', this.getAuthToken());
  }
}
