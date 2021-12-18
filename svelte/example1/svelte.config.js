const sveltePreprocess = require("svelte-preprocess");

// See https://github.com/sveltejs/svelte-preprocess/blob/main/docs/preprocessing.md#typescript for options
module.exports = {
  preprocess: sveltePreprocess({}),
};

