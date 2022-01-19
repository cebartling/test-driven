import { Component, Input, OnInit } from '@angular/core';
import { Race } from '../../types/race';

@Component({
  selector: 'app-race-detail',
  templateUrl: './race-detail.component.html',
  styleUrls: ['./race-detail.component.css'],
})
export class RaceDetailComponent implements OnInit {
  @Input() race!: Race;

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  constructor() {}

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  ngOnInit(): void {}
}
