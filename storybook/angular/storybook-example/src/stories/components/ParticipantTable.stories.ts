import { Meta, Story } from '@storybook/angular';
import { riders } from '../../test-data/rider-test-data';
import { participants } from '../../test-data/participant-test-data';
import { race1 } from '../../test-data/race-test-data';
import { ParticipantTableComponent } from '../../app/components/participant-table/participant-table.component';

// More on default export: https://storybook.js.org/docs/angular/writing-stories/introduction#default-export
export default {
  title: 'Components/Participant table',
  component: ParticipantTableComponent,
  // More on argTypes: https://storybook.js.org/docs/angular/api/argtypes
  argTypes: {
    // backgroundColor: { control: 'color' },
  },
} as Meta;

// More on component templates: https://storybook.js.org/docs/angular/writing-stories/introduction#using-args
const Template: Story<ParticipantTableComponent> = (
  args: ParticipantTableComponent
) => ({
  props: arg,
});

export const Default = Template.bind({});
// More on args: https://storybook.js.org/docs/angular/writing-stories/args
Default.args = {
  riders: riders,
  raceParticipants: participants,
  race: race1,
};
