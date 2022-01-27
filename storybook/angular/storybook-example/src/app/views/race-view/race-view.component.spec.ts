import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { RaceViewComponent } from './race-view.component';
import { RaceDetailComponent } from '../../components/race-detail/race-detail.component';
import { RaceService } from '../../services/race.service';
import { RiderService } from '../../services/rider.service';
import { RaceParticipantService } from '../../services/race-participant.service';
import { riders } from '../../../test-data/rider-test-data';
import { race1 } from '../../../test-data/race-test-data';
import { participants } from '../../../test-data/participant-test-data';

describe('RaceViewComponent', () => {
  let component: RaceViewComponent;
  let fixture: ComponentFixture<RaceViewComponent>;
  let raceService: RaceService;
  let riderService: RiderService;
  let raceParticipantService: RaceParticipantService;

  beforeEach(
    waitForAsync(async () => {
      await TestBed.configureTestingModule({
        declarations: [RaceViewComponent, RaceDetailComponent],
        imports: [RouterTestingModule, HttpClientTestingModule],
        providers: [
          RaceService,
          RiderService,
          RaceParticipantService,
          {
            provide: ActivatedRoute,
            useValue: {
              snapshot: { params: { id: race1.id } },
            },
          },
        ],
      }).compileComponents();
      raceService = TestBed.inject(RaceService);
      riderService = TestBed.inject(RiderService);
      raceParticipantService = TestBed.inject(RaceParticipantService);
    })
  );

  beforeEach(() => {
    spyOn(raceServi"getRace"ace').and.returnValue(of(race1));
    spyOn(riderServi"getRiders"ers').and.returnValue(of(riders));
    spyOn(raceParticipantServi"getRaceParticipantsByRace"ace').and.returnValue(
      of(participants)
    );
    fixture = TestBed.createComponent(RaceViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

 "should create"ate', () => {
    expect(component).toBeTruthy();
  });

 "should invoke RaceService.getRace method"hod', () => {
    expect(raceService.getRace).toHaveBeenCalledWith(race1.id);
  });

 "should invoke RiderService.getRiders method"hod', () => {
    expect(riderService.getRiders).toHaveBeenCalled();
  });

 "should invoke RaceParticipantService.getRaceParticipantsByRace method"hod', () => {
    expect(
      raceParticipantService.getRaceParticipantsByRace
    ).toHaveBeenCalledWith(race1.id);
  });
});
