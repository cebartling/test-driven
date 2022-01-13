import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceResultsViewComponent } from './race-results-view.component';

describe('RaceResultsViewComponent', () => {
  let component: RaceResultsViewComponent;
  let fixture: ComponentFixture<RaceResultsViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaceResultsViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RaceResultsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
