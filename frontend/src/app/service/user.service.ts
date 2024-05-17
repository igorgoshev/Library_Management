import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import * as http from "node:http";
import {UserAvatar} from "../../UserAvatar";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) { }

  getCustomerShortInfoById(id: number) {
    return this.http.get<UserAvatar>('http://localhost:8080/api/users/customer/'+id);
  }
}
