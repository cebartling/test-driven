import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipantListCardComponent } from './participant-list-card.component';
import { race1 } from '../../../test-data/race-test-data';
import { rider1 } from '../../../test-data/rider-test-data';
import { participant1 } from '../../../test-data/participant-test-data';

describe('ParticipantListCardComponent', () => {
  let component: ParticipantListCardComponent;
  let fixture: ComponentFixture<ParticipantListCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParticipantListCardComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParticipantListCardComponent);
    component = fixture.componentInstance;
    component.race = race1;
    component.raceParticipant = participant1;
    component.rider = rider1;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
