import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { RaceService } from '../../services/race.service';
import { RiderService } from '../../services/rider.service';
import { Race } from '../../types/race';
import { Rider } from '../../types/rider';
import { RaceParticipantService } from '../../services/race-participant.service';
import { RaceParticipant } from '../../types/race-participant';

@Component({
  selector: 'app-race-view',
  templateUrl: './race-view.component.html',
  styleUrls: ['./race-view.component.css'],
})
export class RaceViewComponent implements OnInit {
  raceId: string | null | undefined;
  race$: Observable<Race> | undefined;
  riders$: Observable<Rider[]> | undefined;
  raceParticipants$: Observable<RaceParticipant[]> | undefined;

  constructor(
    private route: ActivatedRoute,
    private raceService: RaceService,
    private riderService: RiderService,
    private raceParticipantService: RaceParticipantService
  ) {}

  ngOnInit(): void {
    this.raceId = this.route.snapshot.params.id;
    if (this.raceId) {
      this.race$ = this.raceService.getRace(this.raceId);
      this.raceParticipants$ =
        this.raceParticipantService.getRaceParticipantsByRace(this.raceId);
      this.riders$ = this.riderService.getRiders();
    }
  }
}
