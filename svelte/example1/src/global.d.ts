/// <reference types="svelte" />

declare global {
  interface Window {
    MyNamespace: {
      showAlert: (message: string) => void;
    }
  }
}

window.MyNamespace = window.MyNamespace || {};
