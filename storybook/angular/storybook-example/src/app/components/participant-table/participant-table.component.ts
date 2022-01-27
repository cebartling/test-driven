import { Component, Input, OnInit } from '@angular/core';
import { Race } from '../../types/race';
import { Rider } from '../../types/rider';
import { RaceParticipant } from '../../types/race-participant';

@Component({
  selector: 'app-participant-table',
  templateUrl: './participant-table.component.html',
  styleUrls: ['./participant-table.component.css'],
})
export class ParticipantTableComponent implements OnInit {
  @Input() race!: Race;
  @Input() riders!: Rider[];
  @Input() raceParticipants!: RaceParticipant[];

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  ngOnInit(): void {}

  getRaceParticipant(rider: Rider) {
    return this.raceParticipants.find(
      (raceParticipant: RaceParticipant) => raceParticipant.riderId === rider.id
    );
  }
}
