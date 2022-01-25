import { Meta, Story } from '@storybook/angular';
import { ParticipantListCardComponent } from '../../app/components/participant-list-card/participant-list-card.component';

// More on default export: https://storybook.js.org/docs/angular/writing-stories/introduction#default-export
export default {
  title: 'Components/Participant list card',
  component: ParticipantListCardComponent,
  // More on argTypes: https://storybook.js.org/docs/angular/api/argtypes
  argTypes: {
    // backgroundColor: { control: 'color' },
  },
} as Meta;

// More on component templates: https://storybook.js.org/docs/angular/writing-stories/introduction#using-args
const Template: Story<ParticipantListCardComponent> = (
  args: ParticipantListCardComponent
) => ({
  props: args,
});

export const Default = Template.bind({});
// More on args: https://storybook.js.org/docs/angular/writing-stories/args
Default.args = {};
