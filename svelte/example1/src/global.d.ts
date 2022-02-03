/// <reference types="svelte" />

declare function updateShoppingCart(): void;

declare global {
  interface Window {
    MyNamespace: {
      showAlert: (message: string) => void;
    }
  }
}

window.MyNamespace = window.MyNamespace || {};

