import { Component, Input, OnInit } from '@angular/core';
import { Race } from '../../types/race';
import { Rider } from '../../types/rider';
import { RaceParticipant } from '../../types/race-participant';

@Component({
  selector: 'app-race-detail',
  templateUrl: './race-detail.component.html',
  styleUrls: ['./race-detail.component.css'],
})
export class RaceDetailComponent implements OnInit {
  @Input() race!: Race;
  @Input() riders!: Rider[];
  @Input() raceParticipants!: RaceParticipant[];

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  constructor() {}

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  ngOnInit(): void {}
}
