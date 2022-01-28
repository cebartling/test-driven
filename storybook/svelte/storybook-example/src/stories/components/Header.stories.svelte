<script>
  import {Meta, Story, Template} from "@storybook/addon-svelte-csf";
  import Header from "../../components/Header.svelte";
</script>

<Meta
    title="Components/Header"
    component={Header}
    argTypes={
    {
    }
  }
/>

<Template let:args>
  <Header {...args} />
</Template>

<Story
    name="HomeActive"
    args={{activeView: 'home'}}
/>
<Story
    name="ProfileActive"
    args={{activeView: 'profile'}}
/>

