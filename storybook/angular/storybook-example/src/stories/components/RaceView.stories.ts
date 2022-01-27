import { Meta, moduleMetadata, Story } from '@storybook/angular';
import { RaceViewComponent } from '../../app/views/race-view/race-view.component';
import { APP_INITIALIZER } from '@angular/core';
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
import { HttpClientModule } from '@angular/common/http';

const createRaceService = (): RaceService => {
  return {
    getRace: (id: string): Observable<Race> => {
      return of(race1);
    },
  } as RaceService;
};

const createRiderService = (): RiderService => {
  return {
    getRiders: (): Observable<Rider[]> => {
      return of(riders);
    },
  } as RiderService;
};

const createRaceParticipantService = (): RaceParticipantService => {
  return {
    getRaceParticipantsByRace: (
      raceId: string
    ): Observable<RaceParticipant[]> => {
      return of(participants);
    },
  } as RaceParticipantService;
};

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
      imports: [RouterModule, HttpClientModule],
      declarations: [RaceViewComponent],
      // providers: [RaceService, RiderService, RaceParticipantService],
    }),
  ],
} as Meta;

// More on component templates: https://storybook.js.org/docs/angular/writing-stories/introduction#using-args
const Template: Story<RaceViewComponent> = (args: RaceViewComponent) => ({
  moduleMetadata: {
    providers: [
      {
        provide: APP_INITIALIZER,
        useFactory: createRaceService,
        multi: true,
        deps: [RaceService],
      },
      {
        provide: APP_INITIALIZER,
        useFactory: createRiderService,
        multi: true,
        deps: [RiderService],
      },
      {
        provide: APP_INITIALIZER,
        useFactory: createRaceParticipantService,
        multi: true,
        deps: [RaceParticipantService],
      },
    ],
  },
  props: args,
});

export const Default = Template.bind({});
// More on args: https://storybook.js.org/docs/angular/writing-stories/args
Default.args = {};
