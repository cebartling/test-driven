import { Story, Meta } from '@storybook/angular';
import {RaceOverviewCardComponent} from "../../app/components/race-overview-card/race-overview-card.component";
import {Race} from "../../app/types/race";

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
const Template: Story<RaceOverviewCardComponent> = (args: RaceOverviewCardComponent) => ({
  props: args,
});

export const Default = Template.bind({});
// More on args: https://storybook.js.org/docs/angular/writing-stories/args
Default.args = {
  race: {
    id: "bb54c76f-3c78-40e3-808b-75dec4986c0e",
    name:  "Fat Bike Birkie 2022",
    location: "Seeley",
    state: "WI",
    startDateTime:  new Date("2022-03-12T09:00:00"),
    year: 2022,
    description: "The Fat Bike Birkie, presented by Freewheel Bike, is the premier on snow bike event in North America. Riders test their skill and endurance on the professionally groomed American Birkebeiner Ski Trail! THREE events to choose from: The Big Fat 47K, The Half Fat 21K, and the un-timed Fun Fat 10K Tour – there is something for every rider!"
  } as Race,
};
