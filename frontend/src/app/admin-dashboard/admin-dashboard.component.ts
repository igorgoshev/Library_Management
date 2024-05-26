import {Component, OnInit} from '@angular/core';
import {CardModule} from "primeng/card";
import {ProgressBarModule} from "primeng/progressbar";
import {StatsService} from "../service/stats.service";
import {DecimalPipe} from "@angular/common";
import {ChartModule} from "primeng/chart";
import {LoansInLastDays} from "../LoansInLastDays";
import {forkJoin} from "rxjs";

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CardModule,
    ProgressBarModule,
    DecimalPipe,
    ChartModule
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent implements OnInit {


  constructor(private statsService: StatsService) {
  }

  inventoryPercentage: number = 0


  data: any;
  barChartData: any;
  barChartOptions: any;
  ratio: number = 0;
  options: any;
  loans: LoansInLastDays | undefined;
  reservations: LoansInLastDays | undefined;

  ngOnInit() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    this.statsService.getInventoryForStore().subscribe(x => {
      this.inventoryPercentage = x
    })

    forkJoin({
      loans: this.statsService.getLoansPerDays(),
      reservations: this.statsService.getReservationsPerDays(),
      yearlyLoans: this.statsService.getYearlyBorrows(),
      yearlyReservations: this.statsService.getYearlyReservations(),
      ratio: this.statsService.getRatioForStore()
    }).subscribe(
      x => {
        this.loans = x.loans;
        this.ratio = x.ratio;
        this.reservations = x.reservations;
        this.data = {
          labels: ['5 days ago', '4 days ago', '3 days ago', 'Yesterday', 'Today'],
          datasets: [
            {
              label: 'Lendings',
              data: [this.loans?.day4, this.loans?.day3, this.loans?.day2, this.loans?.day1, this.loans?.day0],
              fill: false,
              borderColor: documentStyle.getPropertyValue('--blue-500'),
              tension: 0.4
            },
            {
              label: 'Reservations',
              data: [this.reservations?.day4, this.reservations?.day3, this.reservations?.day2, this.reservations?.day1, this.reservations?.day0],
              fill: false,
              borderColor: documentStyle.getPropertyValue('--pink-500'),
              tension: 0.4
            }
          ]
        };

        this.barChartData = {
          labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
          datasets: [
            {
              label: 'Loans',
              backgroundColor: documentStyle.getPropertyValue('--blue-500'),
              borderColor: documentStyle.getPropertyValue('--blue-500'),
              data: [x.yearlyLoans.january, x.yearlyLoans.february, x.yearlyLoans.march, x.yearlyLoans.april, x.yearlyLoans.may, x.yearlyLoans.june, x.yearlyLoans.july, x.yearlyLoans.august, x.yearlyLoans.september, x.yearlyLoans.october, x.yearlyLoans.november, x.yearlyLoans.december]
            },
            {
              label: 'Reservations',
              backgroundColor: documentStyle.getPropertyValue('--pink-500'),
              borderColor: documentStyle.getPropertyValue('--pink-500'),
              data: [x.yearlyReservations.january, x.yearlyReservations.february, x.yearlyReservations.march, x.yearlyReservations.april, x.yearlyReservations.may, x.yearlyReservations.june, x.yearlyReservations.july, x.yearlyReservations.august, x.yearlyReservations.september, x.yearlyReservations.october, x.yearlyReservations.november, x.yearlyReservations.december]
            }
          ]
        };

      }
    )
    this.statsService.getLoansPerDays().subscribe(x => {
    })

    this.barChartOptions  = {
      maintainAspectRatio: false,
      aspectRatio: 0.8,
      plugins: {
        legend: {
          labels: {
            color: textColor
          }
        }
      },
      scales: {
        x: {
          ticks: {
            color: textColorSecondary,
            font: {
              weight: 500
            }
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
          }
        },
        y: {
          ticks: {
            color: textColorSecondary
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
          }
        }

      }
    };;



    this.options = {
      maintainAspectRatio: false,
      aspectRatio: 0.6,
      plugins: {
        legend: {
          labels: {
            color: textColor
          }
        }
      },
      scales: {
        x: {
          ticks: {
            color: textColorSecondary
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
          }
        },
        y: {
          ticks: {
            color: textColorSecondary
          },
          grid: {
            color: surfaceBorder,
            drawBorder: false
          }
        }
      }
    };
  }


}
