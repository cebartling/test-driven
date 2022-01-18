import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { RaceOverviewListComponent } from './race-overview-list.component';
import { races } from '../../../stories/data/races-test-data';
import { RaceService } from '../../services/race.service';

describe('RaceOverviewListComponent', () => {
  let component: RaceOverviewListComponent;
  let fixture: ComponentFixture<RaceOverviewListComponent>;

  const raceServiceMock = {
    getRaces: () => {
      return of(races);
    },
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RaceOverviewListComponent],
      imports: [HttpClientTestingModule],
      providers: [
        {
          provide: RaceService,
          useValue: raceServiceMock,
        },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RaceOverviewListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
