import { Meta, moduleMetadata, Story } from '@storybook/angular';
import { CommonModule } from '@angular/common';
import { RaceOverviewListComponent } from '../../app/components/race-overview-list/race-overview-list.component';
import { RaceOverviewCardComponent } from '../../app/components/race-overview-card/race-overview-card.component';
import { races } from '../../test-data/race-test-data';

// More on default export: https://storybook.js.org/docs/angular/writing-stories/introduction#default-export
export default {
  title: 'Components/Race overview list',
  component: RaceOverviewListComponent,
  decorators: [
    moduleMetadata({
      //👇 Imports both components to allow component composition with storybook
      declarations: [RaceOverviewListComponent, RaceOverviewCardComponent],
      imports: [CommonModule],
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
const Template: Story<RaceOverviewListComponent> = (
  args: RaceOverviewListComponent
) => ({
  props: args,
});

export const Default = Template.bind({});
// More on args: https://storybook.js.org/docs/angular/writing-stories/args
Default.args = {
  races: races,
};
