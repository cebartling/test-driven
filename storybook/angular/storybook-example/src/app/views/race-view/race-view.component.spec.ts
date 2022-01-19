import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { RaceViewComponent } from './race-view.component';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { Race } from '../../types/race';
import { RaceService } from '../../services/race.service';
import { of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RaceDetailComponent } from '../../components/race-detail/race-detail.component';

const race = {
  id: 'bb54c76f-3c78-40e3-808b-75dec4986c0e',
  name: 'Fat Bike Birkie 2022',
  location: 'Seeley',
  state: 'WI',
  startDateTime: new Date('2022-03-12T09:00:00'),
  year: 2022,
  description:
    'The Fat Bike Birkie, presented by Freewheel Bike, is the premier on snow bike event in North America.',
} as Race;

describe('RaceViewComponent', () => {
  let component: RaceViewComponent;
  let fixture: ComponentFixture<RaceViewComponent>;
  let raceService: RaceService;

  beforeEach(
    waitForAsync(async () => {
      await TestBed.configureTestingModule({
        declarations: [RaceViewComponent, RaceDetailComponent],
        imports: [RouterTestingModule, HttpClientTestingModule],
        providers: [
          RaceService,
          {
            provide: ActivatedRoute,
            useValue: {
              snapshot: { params: { id: race.id } },
            },
          },
        ],
      }).compileComponents();
      raceService = TestBed.inject(RaceService);
    })
  );

  beforeEach(() => {
    spyOn(raceService, 'getRace').and.returnValue(of(race));
    fixture = TestBed.createComponent(RaceViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should invoke RaceService.getRace method', () => {
    expect(raceService.getRace).toHaveBeenCalledWith(race.id);
  });
});
