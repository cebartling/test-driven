import { Meta, moduleMetadata, Story } from '@storybook/angular';
import { RaceService } from '../../app/services/race.service';
import { Observable, of } from 'rxjs';
import { Race } from '../../app/types/race';
import { races } from '../../test-data/race-test-data';
import { RouterModule } from '@angular/router';
import { APP_BASE_HREF, CommonModule } from '@angular/common';
import { RaceListingViewComponent } from '../../app/views/race-listing-view/race-listing-view.component';
import { RaceOverviewListComponent } from '../../app/components/race-overview-list/race-overview-list.component';
import { RaceOverviewCardComponent } from '../../app/components/race-overview-card/race-overview-card.component';

const raceServiceMock = {
  getRaces: (): Observable<Race[]> => {
    return of(races);
  },
} as RaceService;

// More on default export: https://storybook.js.org/docs/angular/writing-stories/introduction#default-export
export default {
  title: 'Views/Race listing view',
  component: RaceListingViewComponent,
  // More on argTypes: https://storybook.js.org/docs/angular/api/argtypes
  argTypes: {
    // backgroundColor: { control: 'color' },
  },
  decorators: [
    moduleMetadata({
      declarations: [
        RaceListingViewComponent,
        RaceOverviewListComponent,
        RaceOverviewCardComponent,
      ],
      imports: [CommonModule, RouterModule.forRoot([], { useHash: true })],
      providers: [
        { provide: APP_BASE_HREF, useValue: '/' },
        { provide: RaceService, useValue: raceServiceMock },
      ],
    }),
  ],
} as Meta;

// More on component templates: https://storybook.js.org/docs/angular/writing-stories/introduction#using-args
const Template: Story<RaceListingViewComponent> = (
  args: RaceListingViewComponent
) => ({
  props: args,
});

export const Default = Template.bind({});
// More on args: https://storybook.js.org/docs/angular/writing-stories/args
Default.args = {};
