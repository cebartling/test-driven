import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceListingViewComponent } from './race-listing-view.component';

describe('RaceListingViewComponent', () => {
  let component: RaceListingViewComponent;
  let fixture: ComponentFixture<RaceListingViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RaceListingViewComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RaceListingViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
