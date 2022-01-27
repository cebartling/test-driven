import { Meta, moduleMetadata, Story } from '@storybook/angular';
import { RaceViewComponent } from '../../app/views/race-view/race-view.component';
import { RaceService } from '../../app/services/race.service';
import { Observable, of } from 'rxjs';
import { Race } from '../../app/types/race';
import { race1 } from '../../test-data/race-test-data';
import { RiderService } from '../../app/services/rider.service';
import { Rider } from '../../app/types/rider';
import { riders } from '../../test-data/rider-test-data';
import { RaceParticipantService } from '../../app/services/race-participant.service';
import { RaceParticipant } from '../../app/types/race-participant';
import { participants } from '../../test-data/participant-test-data';
import { RouterModule } from '@angular/router';
import { APP_BASE_HREF, CommonModule } from '@angular/common';

const raceServiceMock = {
  getRace: (id: string): Observable<Race> => {
    return of(race1);
  },
} as RaceService;

const riderServiceMock = {
  getRiders: (): Observable<Rider[]> => {
    return of(riders);
  },
} as RiderService;

const raceParticipantServiceMock = {
  getRaceParticipantsByRace: (
    raceId: string
  ): Observable<RaceParticipant[]> => {
    return of(participants);
  },
} as RaceParticipantService;

// More on default export: https://storybook.js.org/docs/angular/writing-stories/introduction#default-export
export default {
  title: 'Views/Race view',
  component: RaceViewComponent,
  // More on argTypes: https://storybook.js.org/docs/angular/api/argtypes
  argTypes: {
    // backgroundColor: { control: 'color' },
  },
  decorators: [
    moduleMetadata({
      declarations: [RaceViewComponent],
      imports: [CommonModule, RouterModule.forRoot([])],
      providers: [
        { provide: APP_BASE_HREF, useValue: '/' },
        { provide: RaceService, useValue: raceServiceMock },
        { provide: RiderService, useValue: riderServiceMock },
        {
          provide: RaceParticipantService,
          useValue: raceParticipantServiceMock,
        },
      ],
    }),
  ],
} as Meta;

// More on component templates: https://storybook.js.org/docs/angular/writing-stories/introduction#using-args
const Template: Story<RaceViewComponent> = (args: RaceViewComponent) => ({
  props: args,
});

export const Default = Template.bind({});
// More on args: https://storybook.js.org/docs/angular/writing-stories/args
Default.args = {};
