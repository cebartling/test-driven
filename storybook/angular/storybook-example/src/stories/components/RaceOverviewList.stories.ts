import {Meta, moduleMetadata, Story} from '@storybook/angular';
import {CommonModule} from "@angular/common";
import {of} from "rxjs";
import {RaceOverviewListComponent} from "../../app/components/race-overview-list/race-overview-list.component";
import {RaceOverviewCardComponent} from "../../app/components/race-overview-card/race-overview-card.component";
import {RaceService} from "../../app/services/race.service";
import {races} from "../data/races-test-data";

const raceServiceMock = {
  getRaces: () => {
    return of(races);
  }
};

// More on default export: https://storybook.js.org/docs/angular/writing-stories/introduction#default-export
export default {
  title: 'Components/Race list',
  component: RaceOverviewListComponent,
  decorators: [
    moduleMetadata({
      //👇 Imports both components to allow component composition with storybook
      declarations: [RaceOverviewListComponent, RaceOverviewCardComponent],
      imports: [
        CommonModule
      ],
      providers: [
        {
          provide: RaceService,
          useValue: raceServiceMock
        }
      ]
    }),
    //👇 Wraps our stories with a decorator
    // componentWrapperDecorator(story => `<div style="margin: 3em">${story}</div>`),
  ],
  // More on argTypes: https://storybook.js.org/docs/angular/api/argtypes
  argTypes: {
    // backgroundColor: { control: 'color' },
  },
} as Meta;

// More on component templates: https://storybook.js.org/docs/angular/writing-stories/introduction#using-args
const Template: Story<RaceOverviewListComponent> = (args: RaceOverviewListComponent) => ({
  props: args,
});

export const Default = Template.bind({});
// More on args: https://storybook.js.org/docs/angular/writing-stories/args
Default.args = {};
