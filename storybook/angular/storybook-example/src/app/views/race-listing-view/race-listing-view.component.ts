import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Race } from '../../types/race';
import { RaceService } from '../../services/race.service';

@Component({
  selector: 'app-race-listing-view',
  templateUrl: './race-listing-view.component.html',
  styleUrls: ['./race-listing-view.component.css'],
})
export class RaceListingViewComponent implements OnInit {
  races$: Observable<Race[]> | undefined;

  constructor(private raceService: RaceService) {}

  ngOnInit(): void {
    this.races$ = this.raceService.getRaces();
  }
}
