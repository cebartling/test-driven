// also exported from '@storybook/angular' if you can deal with breaking changes in 6.1
import { Meta, Story } from "@storybook/angular";
import { NavigationalHeaderComponent } from "../../app/components/navigational-header/navigational-header.component";

// More on default export: https://storybook.js.org/docs/angular/writing-stories/introduction#default-export
export default {
  title: "Components/Navigational header",
  component: NavigationalHeaderComponent,
  // More on argTypes: https://storybook.js.org/docs/angular/api/argtypes
  argTypes: {
    // backgroundColor: { control: 'color' },
  }
} as Meta;

// More on component templates: https://storybook.js.org/docs/angular/writing-stories/introduction#using-args
const Template: Story<NavigationalHeaderComponent> = (
  args: NavigationalHeaderComponent
) => ({
  props: args,
});

export const HomeActive = Template.bind({});
// More on args: https://storybook.js.org/docs/angular/writing-stories/args
HomeActive.args = {};

export const RacesActive = Template.bind({});
// More on args: https://storybook.js.org/docs/angular/writing-stories/args
RacesActive.args = {};
