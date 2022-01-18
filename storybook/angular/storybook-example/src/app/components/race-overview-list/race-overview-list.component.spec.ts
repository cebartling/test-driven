import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { RaceOverviewListComponent } from './race-overview-list.component';
import { races } from '../../../stories/data/races-test-data';
import { RaceService } from '../../services/race.service';
import { RaceOverviewCardComponent } from '../race-overview-card/race-overview-card.component';
import { RouterModule } from '@angular/router';

describe('RaceOverviewListComponent', () => {
  let component: RaceOverviewListComponent;
  let fixture: ComponentFixture<RaceOverviewListComponent>;
  let raceService: RaceService;

  beforeEach(
    waitForAsync(async () => {
      await TestBed.configureTestingModule({
        imports: [RouterModule.forRoot([]), HttpClientTestingModule],
        declarations: [RaceOverviewListComponent, RaceOverviewCardComponent],
        providers: [RaceService],
      }).compileComponents();
      raceService = TestBed.inject(RaceService);
    })
  );

  beforeEach(() => {
    spyOn(raceService, 'getRaces').and.returnValue(of(races));
    fixture = TestBed.createComponent(RaceOverviewListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('rendered template', () => {
    let rootElement: HTMLElement;

    beforeEach(() => {
      rootElement = fixture.nativeElement as HTMLElement;
    });

    it('should render main flexbox column div', () => {
      expect(
        rootElement.querySelector"div.d-flex.flex-column"')
      ).not.toBeNull();
    });
  });
});
