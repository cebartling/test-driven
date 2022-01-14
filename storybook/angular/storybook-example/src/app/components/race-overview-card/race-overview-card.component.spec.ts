import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceOverviewCardComponent } from './race-overview-card.component';

describe('RaceOverviewCardComponent', () => {
  let component: RaceOverviewCardComponent;
  let fixture: ComponentFixture<RaceOverviewCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaceOverviewCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RaceOverviewCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
