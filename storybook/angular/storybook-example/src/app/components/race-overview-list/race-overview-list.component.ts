import { Component, Input, OnInit } from '@angular/core';
import { Race } from '../../types/race';

@Component({
  selector: 'app-race-overview-list',
  templateUrl: './race-overview-list.component.html',
  styleUrls: ['./race-overview-list.component.css'],
})
export class RaceOverviewListComponent implements OnInit {
  @Input() races!: Race[];

  ngOnInit(): void {}
}
