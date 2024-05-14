import { Component } from '@angular/core';
import {InputTextModule} from "primeng/inputtext";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-lending',
  standalone: true,
  imports: [
    InputTextModule,
    RouterLink
  ],
  templateUrl: './lending.component.html',
  styleUrl: './lending.component.css'
})
export class LendingComponent {
  alphabet: String[] = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '#', 'ALL'];

}
