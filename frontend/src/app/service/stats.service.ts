import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {StoreDetails} from "../StoreDetails";
import {LoansInLastDays} from "../LoansInLastDays";
import {YearlyStats} from "../YearlyStats";

@Injectable({
  providedIn: 'root'
})
export class StatsService {

  constructor(private http: HttpClient) {
  }

  getAuthToken() {
    return {
      headers: new HttpHeaders({
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      })
    }
  }

  getInventoryForStore() {
    return this.http.get<number>('http://localhost:8080/api/stats/inventory', this.getAuthToken());
  }

  getLoansPerDays() {
    return this.http.get<LoansInLastDays>(`http://localhost:8080/api/stats/loansPerDays`, this.getAuthToken());
  }

  getReservationsPerDays() {
    return this.http.get<LoansInLastDays>(`http://localhost:8080/api/stats/reservationsPerDays`, this.getAuthToken());
  }

  getYearlyReservations() {
    return this.http.get<YearlyStats>('http://localhost:8080/api/stats/yearlyReservations', this.getAuthToken());
  }

  getYearlyBorrows() {
    return this.http.get<YearlyStats>('http://localhost:8080/api/stats/yearlyBorrows', this.getAuthToken());
  }
}

