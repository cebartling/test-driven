import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';
import { RaceViewComponent } from './race-view.component';
import { RaceDetailComponent } from '../../components/race-detail/race-detail.component';
import { ParticipantTableComponent } from '../../components/participant-table/participant-table.component';
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
        declarations: [
          RaceViewComponent,
          RaceDetailComponent,
          ParticipantTableComponent,
        ],
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
    spyOn(raceService, 'getRace').and.returnValue(of(race1));
    spyOn(riderService, 'getRiders').and.returnValue(of(riders));
    spyOn(raceParticipantService, 'getRaceParticipantsByRace').and.returnValue(
      of(participants)
    );
    fixture = TestBed.createComponent(RaceViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should invoke RaceService.getRace method', () => {
    expect(raceService.getRace).toHaveBeenCalledWith(race1.id);
  });

  it('should invoke RiderService.getRiders method', () => {
    expect(riderService.getRiders).toHaveBeenCalled();
  });

  it('should invoke RaceParticipantService.getRaceParticipantsByRace method', () => {
    expect(
      raceParticipantService.getRaceParticipantsByRace
    ).toHaveBeenCalledWith(race1.id);
  });
});
