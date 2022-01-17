import { Meta, Story } from '@storybook/angular';
import { RaceOverviewCardComponent } from '../../app/components/race-overview-card/race-overview-card.component';
import { race1 } from '../data/race-test-data';

// More on default export: https://storybook.js.org/docs/angular/writing-stories/introduction#default-export
export default {
  title: 'Components/Race overview card',
  component: RaceOverviewCardComponent,
  // More on argTypes: https://storybook.js.org/docs/angular/api/argtypes
  argTypes: {
    // backgroundColor: { control: 'color' },
  },
} as Meta;

// More on component templates: https://storybook.js.org/docs/angular/writing-stories/introduction#using-args
const Template: Story<RaceOverviewCardComponent> = (
  args: RaceOverviewCardComponent
) => ({
  props: arg,
});

export const Default = Template.bind({});
// More on args: https://storybook.js.org/docs/angular/writing-stories/args
Default.args = {
  race: race1,
};
