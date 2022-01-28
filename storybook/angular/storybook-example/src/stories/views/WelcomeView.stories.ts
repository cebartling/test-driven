import { RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";
import { Meta, moduleMetadata, Story } from "@storybook/angular";
import { WelcomeViewComponent } from "../../app/views/welcome-view/welcome-view.component";

// More on default export: https://storybook.js.org/docs/angular/writing-stories/introduction#default-export
export default {
  title: 'Views/Welcome view',
  component: WelcomeViewComponent,
  // More on argTypes: https://storybook.js.org/docs/angular/api/argtypes
  argTypes: {
    // backgroundColor: { control: 'color' },
  },
  decorators: [
    moduleMetadata({
      declarations: [WelcomeViewComponent],
      imports: [CommonModule, RouterModule.forRoot([], { useHash: true })],
      providers: [],
    }),
  ],
} as Meta;

// More on component templates: https://storybook.js.org/docs/angular/writing-stories/introduction#using-args
const Template: Story<WelcomeViewComponent> = (args: WelcomeViewComponent) => ({
  props: args,
});

export const Default = Template.bind({});
// More on args: https://storybook.js.org/docs/angular/writing-stories/args
Default.args = {};
