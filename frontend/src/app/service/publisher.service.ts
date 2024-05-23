import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Publisher} from "../Publisher";

@Injectable({
  providedIn: 'root'
})
export class PublisherService {

  constructor(private http: HttpClient) { }

  getPublishers() {
    return this.http.get<Publisher>('http://localhost:8080/api/publishers');
  }
}
