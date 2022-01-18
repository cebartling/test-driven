import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceOverviewCardComponent } from './race-overview-card.component';
import { Race } from '../../types/race';
import { RouterModule } from '@angular/router';

describe('RaceOverviewCardComponent', () => {
  let component: RaceOverviewCardComponent;
  let fixture: ComponentFixture<RaceOverviewCardComponent>;
  const race = {
    id: 'bb54c76f-3c78-40e3-808b-75dec4986c0e',
    name: 'Fat Bike Birkie 2022',
    location: 'Seeley',
    state: 'WI',
    startDateTime: new Date('2022-03-12T09:00:00'),
    year: 2022,
    description:
      'The Fat Bike Birkie is the premier on snow bike event in North America.',
  } as Race;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RaceOverviewCardComponent],
      imports: [RouterModule.forRoot([])],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RaceOverviewCardComponent);
    component = fixture.componentInstance;
    component.race = race;
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

    it('should render main overview card div', () => {
      expect(
        rootElement.querySelector('div.race-overview-card')
      ).not.toBeNull();
    });

    it('should render overview card body div', () => {
      expect(rootElement.querySelector('div.card-body')).not.toBeNull();
    });

    it('should render overview card title heading with appropriate textual content', () => {
      expect(rootElement.querySelector('h3.card-title')?.textContent).toEqual(
        race.name
      );
    });

    it('should render overview card subtitle heading with appropriate textual content', () => {
      expect(
        rootElement.querySelector('h4.card-subtitle')?.textContent
      ).toEqual(' March 12, 2022 at 9:00 AM ');
    });

    it('should render overview card second subtitle heading with appropriate textual content', () => {
      expect(
        rootElement.querySelector('h5.card-subtitle')?.textContent
      ).toEqual(` ${race.location}, ${race.state} `);
    });

    it('should render overview card text with appropriate textual content', () => {
      expect(rootElement.querySelector('p.card-text')?.textContent).toEqual(
        ` ${race.description} `
      );
    });

    it('should render details hyperlink', () => {
      const match = rootElement.querySelector('a.card-link');
      expect(match?.textContent).toEqual('Details');
      expect(match?.attributes.getNamedItem('href')?.value).toEqual(
        `/race/${race.id}`
      );
    });
  });
});
