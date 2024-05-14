import { Component, OnInit } from '@angular/core';
import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag'
import { ReviewListingComponent } from '../review-listing/review-listing.component';
import { Review } from '../Review';

@Component({
  selector: 'review-carousel',
  standalone: true,
  imports: [CarouselModule, ButtonModule, TagModule, ReviewListingComponent],
  templateUrl: './review-carousel.component.html',
  styleUrl: './review-carousel.component.css'
})
export class ReviewCarouselComponent implements OnInit {
  
  reviews: Review[] | any
  
  ngOnInit(): void {

    this.reviews = [
      {
        userId: 0,
        value: 3,
        description: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since" +
        "the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the" + 
        " leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum "+
        "passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
        dateReviewed: new Date()
      },
      {
        userId: 0,
        value: 4,
        description: "KJNIGAVA E PREDOBRAAAAAAAAAAA AUUUUUUUUUUUUUU BAUUUUUUUUUUU",
        dateReviewed: new Date()
      },
      {
        userId: 0,
        value: 5,
        description: "IDEMOOOOOO IDE GAS GASIRAME BRATUUUUUUUUUUUUUUUU",
        dateReviewed: new Date()
      },
    ]

  }

}
