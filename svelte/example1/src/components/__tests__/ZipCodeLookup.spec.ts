import {render, RenderResult} from '@testing-library/svelte'
import ZipCodeLookup from "../ZipCodeLookup.svelte";

describe('ZipCodeLookup.svelte component', () => {
  let renderedComponent: RenderResult;

  beforeEach(() => {
    renderedComponent = render(ZipCodeLookup);
  });

  it('should successfully render the component', () => {
    expect(renderedComponent).toBeDefined();
  });
});
