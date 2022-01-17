import { Component, OnInit } from '@angular/core';
import { RaceService } from '../../services/race.service';
import { Observable } from 'rxjs';
import { Race } from '../../types/race';

@Component({
  selector: 'app-race-overview-list',
  templateUrl: './race-overview-list.component.html',
  styleUrls: ['./race-overview-list.component.css'],
})
export class RaceOverviewListComponent implements OnInit {
  races$: Observable<Race[]> | undefined;

  constructor(private raceService: RaceService) {}

  ngOnInit(): void {
    this.races$ = this.raceService.getRaces();
  }
}
