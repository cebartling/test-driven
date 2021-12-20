<script lang="ts">
  import {lookupZipCode} from "../services/ZipCodeLookupService";

  export let zipCode: string = undefined;

  export async function handleOnSubmit() {
    console.log('handleOnSubmit');
    let zipCodeLookupResults = await lookupZipCode(zipCode);
  }
</script>

<div>
  <form on:submit={handleOnSubmit}
        data-testid="zip-code-lookup-form">
    <div class="">
      <input type="text">
    </div>
    <div class="">
      <button type="button"
              on:click={handleOnSubmit}
              data-testid="zip-code-lookup-submit-button">Search</button>
    </div>
  </form>
</div>

<style>
    /* styles go here */
</style>
