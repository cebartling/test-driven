import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceOverviewCardComponent } from './race-overview-card.component';
import {Race} from "../../types/race";

describe('RaceOverviewCardComponent', () => {
  let component: RaceOverviewCardComponent;
  let fixture: ComponentFixture<RaceOverviewCardComponent>;
  const race = {
    id: "bb54c76f-3c78-40e3-808b-75dec4986c0e",
    name:  "Fat Bike Birkie 2022",
    location: "Seeley",
    state: "WI",
    startDateTime:  new Date("2022-03-12T09:00:00"),
    year: 2022,
    description: "The Fat Bike Birkie is the premier on snow bike event in North America."
  } as Race;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaceOverviewCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RaceOverviewCardComponent);
    component = fixture.componentInstance;
    component.race = race;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
