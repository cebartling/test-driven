import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipantTableComponent } from './participant-table.component';
import { race1 } from '../../../test-data/race-test-data';
import {
  participant1,
  participants,
} from '../../../test-data/participant-test-data';
import { rider1, riders } from '../../../test-data/rider-test-data';

describe('ParticipantTableComponent', () => {
  let component: ParticipantTableComponent;
  let fixture: ComponentFixture<ParticipantTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ParticipantTableComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ParticipantTableComponent);
    component = fixture.componentInstance;
    component.race = race1;
    component.raceParticipants = participants;
    component.riders = riders;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('methods', () => {
    describe('getRaceParticipant', () => {
      it('should return a race participant associated to the specified rider', () => {
        expect(component.getRaceParticipant(rider1)).toEqual(participant1);
      });
    });
  });
});
