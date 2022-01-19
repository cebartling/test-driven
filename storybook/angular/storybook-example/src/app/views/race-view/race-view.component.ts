import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RaceService } from '../../services/race.service';
import { Observable } from 'rxjs';
import { Race } from '../../types/race';

@Component({
  selector: 'app-race-view',
  templateUrl: './race-view.component.html',
  styleUrls: ['./race-view.component.css'],
})
export class RaceViewComponent implements OnInit {
  raceId: string | null | undefined;
  race$: Observable<Race> | undefined;

  constructor(
    private route: ActivatedRoute,
    private raceService: RaceService
  ) {}

  ngOnInit(): void {
    this.raceId = this.route.snapshot.params.id;
    if (this.raceId) {
      this.race$ = this.raceService.getRace(this.raceId);
    }
  }
}
