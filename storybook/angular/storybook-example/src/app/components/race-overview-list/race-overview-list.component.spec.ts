import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceOverviewListComponent } from './race-overview-list.component';

describe('RaceOverviewListComponent', () => {
  let component: RaceOverviewListComponent;
  let fixture: ComponentFixture<RaceOverviewListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RaceOverviewListComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RaceOverviewListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
