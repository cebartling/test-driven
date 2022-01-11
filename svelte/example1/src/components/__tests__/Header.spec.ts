import {cleanup, render, type RenderResult} from '@testing-library/svelte'
import Header from "../Header.svelte";

describe('Header.svelte component', () => {
  let renderResult: RenderResult;

  describe('when activeView property is set to "home"', () => {
    const activeView = 'home';
    const props = {activeView};

    beforeEach(() => {
      renderResult = render(Header, {props});
    });

    afterEach(() => {
      cleanup();
    })

    describe('home navigation link', () => {
      let anchorElement: HTMLAnchorElement;

      beforeEach(() => {
        anchorElement = renderResult.container.querySelector('a#homeNav');
      });

      it('should be present in the DOM', () => {
        expect(anchorElement).toBeInTheDocument();
      });

      it('should have a "nav-link" CSS class', () => {
        expect(anchorElement.className).toMatch(/nav-link/);
      });

      it('should have an "active" CSS class', () => {
        expect(anchorElement.className).toMatch(/active/);
      });
    });

    describe('profile navigation link', () => {
      let anchorElement: HTMLAnchorElement;

      beforeEach(() => {
        anchorElement = renderResult.container.querySelector('a#profileNav');
      });

      it('should be present in the DOM', () => {
        expect(anchorElement).toBeInTheDocument();
      });

      it('should have a "nav-link" CSS class', () => {
        expect(anchorElement.className).toMatch(/nav-link/);
      });

      it('should not have an "active" CSS class', () => {
        expect(anchorElement.className).not.toMatch(/active/);
      });
    });
  });

  describe('when activeView property is set to "profile"', () => {
    const activeView = 'profile';
    const props = {activeView};

    beforeEach(() => {
      renderResult = render(Header, {props});
    });

    afterEach(() => {
      cleanup();
    })

    describe('home navigation link', () => {
      let anchorElement: HTMLAnchorElement;

      beforeEach(() => {
        anchorElement = renderResult.container.querySelector('a#homeNav');
      });

      it('should be present in the DOM', () => {
        expect(anchorElement).toBeInTheDocument();
      });

      it('should have a "nav-link" CSS class', () => {
        expect(anchorElement.className).toMatch(/nav-link/);
      });

      it('should not have an "active" CSS class', () => {
        expect(anchorElement.className).not.toMatch(/active/);
      });
    });

    describe('profile navigation link', () => {
      let anchorElement: HTMLAnchorElement;

      beforeEach(() => {
        anchorElement = renderResult.container.querySelector('a#profileNav');
      });

      it('should be present in the DOM', () => {
        expect(anchorElement).toBeInTheDocument();
      });

      it('should have a "nav-link" CSS class', () => {
        expect(anchorElement.className).toMatch(/nav-link/);
      });

      it('should have an "active" CSS class', () => {
        expect(anchorElement.className).toMatch(/active/);
      });
    });
  });
});
