<script lang="ts">
  import type {ZipCodeLookupResult} from '../models/ZipCodeLookupResult';
  import ZipCodeLookupResultsTable from './ZipCodeLookupResultsTable.svelte';

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
    <div class="row">
      <div class="col">
        <input type="text" bind:value={zipCode} class="form-control">
      </div>
      <div class="col">
        <button type="button"
                class="btn btn-primary"
                on:click={handleOnClick}
                data-testid="zip-code-lookup-button">
          Search
        </button>
      </div>
    </div>
  </form>
  <div class="row">
    {#if zipCodeLookupResults}
      <ZipCodeLookupResultsTable zipCodeLookupResults={zipCodeLookupResults}/>
    {:else}
      <div class="p-3">
        <div class="alert alert-info">
          No results available!
        </div>
      </div>
    {/if}
  </div>
</div>

<style>
    /* styles go here */
</style>
