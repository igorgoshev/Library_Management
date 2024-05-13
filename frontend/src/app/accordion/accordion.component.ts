import { Component } from '@angular/core';
import { AccordionModule } from 'primeng/accordion';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'accordion',
  standalone: true,
  imports: [ButtonModule, AccordionModule],
  templateUrl: './accordion.component.html',
  styleUrl: './accordion.component.css'
})
export class AccordionComponent {

}
