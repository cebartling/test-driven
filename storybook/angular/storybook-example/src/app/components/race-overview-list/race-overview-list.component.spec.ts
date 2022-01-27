import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterModule } from '@angular/router';
import { RaceOverviewListComponent } from './race-overview-list.component';
import { RaceOverviewCardComponent } from '../race-overview-card/race-overview-card.component';
import { races } from '../../../test-data/race-test-data';

describe('RaceOverviewListComponent', () => {
  let component: RaceOverviewListComponent;
  let fixture: ComponentFixture<RaceOverviewListComponent>;

  beforeEach(
    waitForAsync(async () => {
      await TestBed.configureTestingModule({
        imports: [RouterModule.forRoot([]), HttpClientTestingModule],
        declarations: [RaceOverviewListComponent, RaceOverviewCardComponent],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(RaceOverviewListComponent);
    component = fixture.componentInstance;
    component.races = races;
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
        rootElement.querySelector('div.d-flex.flex-column')
      ).not.toBeNull();
    });
  });
});
