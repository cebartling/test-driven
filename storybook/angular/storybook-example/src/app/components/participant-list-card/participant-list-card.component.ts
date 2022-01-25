import { Component, Input, OnInit } from '@angular/core';
import { Rider } from '../../types/rider';
import { RaceParticipant } from '../../types/race-participant';
import { Race } from '../../types/race';

@Component({
  selector: 'app-participant-list-card',
  templateUrl: './participant-list-card.component.html',
  styleUrls: ['./participant-list-card.component.css'],
})
export class ParticipantListCardComponent implements OnInit {
  @Input() rider!: Rider;
  @Input() raceParticipant!: RaceParticipant;
  @Input() race!: Race;

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  ngOnInit(): void {}

  get ageOnRaceDay(): number {
    return this.rider.calculateAgeAtDate(this.race.startDateTime);
  }
}
