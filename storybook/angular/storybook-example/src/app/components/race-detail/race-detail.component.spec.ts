import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceDetailComponent } from './race-detail.component';
import { race1 } from '../../../test-data/race-test-data';

describe('RaceDetailComponent', () => {
  let component: RaceDetailComponent;
  let fixture: ComponentFixture<RaceDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RaceDetailComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RaceDetailComponent);
    component = fixture.componentInstance;
    component.race = race1;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
