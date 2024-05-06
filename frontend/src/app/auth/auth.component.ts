import { Component } from '@angular/core';
import { PasswordModule } from 'primeng/password';
import { CheckboxModule } from 'primeng/checkbox';
import { LayoutService } from '../admin-layout/layout.service';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [PasswordModule, CheckboxModule, RouterLink, FormsModule, ButtonModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css', 
  styles: [`
  :host ::ng-deep .pi-eye,
  :host ::ng-deep .pi-eye-slash {
      transform:scale(1.6);
      margin-right: 1rem;
      color: var(--primary-color) !important;
  }
`]
})
export class AuthComponent {

  valCheck: string[] = ['remember'];

  password!: string;

  constructor(public layoutService: LayoutService) { }
}
