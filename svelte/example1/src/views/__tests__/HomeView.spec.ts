import {cleanup, render, type RenderResult} from "@testing-library/svelte";
import HomeView from "../HomeView.svelte";

describe('HomeView.svelte component', () => {
  let renderResult: RenderResult;

  beforeEach(() => {
    renderResult = render(HomeView);
  });

  afterEach(() => {
    cleanup();
  })

  describe('component rendered tree', () => {
    it('should render the component', () => {
      const el = renderResult.container.querySelector('h1#homeHeading');
      expect(el.textContent).toEqual('Home');
    });
  });
});
