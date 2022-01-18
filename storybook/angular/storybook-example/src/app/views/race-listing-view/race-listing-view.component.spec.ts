import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceListingViewComponent } from './race-listing-view.component';
import { RaceOverviewListComponent } from '../../components/race-overview-list/race-overview-list.component';
import { RouterModule } from '@angular/router';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RaceOverviewCardComponent } from '../../components/race-overview-card/race-overview-card.component';
import { RaceService } from '../../services/race.service';

describe('RaceListingViewComponent', () => {
  let component: RaceListingViewComponent;
  let fixture: ComponentFixture<RaceListingViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RaceService],
      declarations: [RaceListingViewComponent, RaceOverviewListComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RaceListingViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
