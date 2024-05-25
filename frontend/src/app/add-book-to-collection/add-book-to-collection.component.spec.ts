import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBookToCollectionComponent } from './add-book-to-collection.component';

describe('AddBookToCollectionComponent', () => {
  let component: AddBookToCollectionComponent;
  let fixture: ComponentFixture<AddBookToCollectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddBookToCollectionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AddBookToCollectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
