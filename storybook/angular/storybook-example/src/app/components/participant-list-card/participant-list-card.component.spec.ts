import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipantListCardComponent } from './participant-list-card.component';

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
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
