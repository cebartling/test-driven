<script lang="ts">
  import type {ZipCodeLookupResult} from '../models/ZipCodeLookupResult';

  export let zipCode: string = undefined;
  export let zipCodeLookupResults: ZipCodeLookupResult[] = undefined;

  async function handleOnClick() {
    const url = `/api/zipCodes?zipCode=${zipCode}`;

    try {
      const response = await fetch(url);
      zipCodeLookupResults = await response.json();
      console.log(zipCodeLookupResults);
    } catch (error) {
      console.error(error);
    }
  }
</script>

<div>
  <form>
    <div class="">
      <input type="text" bind:value={zipCode}>
    </div>
    <div class="">
      <button type="button" on:click={handleOnClick} data-testid="zip-code-lookup-button">
        Search
      </button>
    </div>
  </form>
</div>

<style>
    /* styles go here */
</style>
