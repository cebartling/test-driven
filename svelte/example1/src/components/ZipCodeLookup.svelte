<script lang="ts">
  import type {ZipCodeLookupResult} from '../models/ZipCodeLookupResult';
  import axios from 'axios';

  export let zipCode: string = undefined;
  export let zipCodeLookupResults: ZipCodeLookupResult[] = undefined;

  async function handleOnSubmit() {
    const url = `/zipCodes?zipCode=${zipCode}`;
    const config = {};
    zipCodeLookupResults = await axios.get(url, config);
  }
</script>

<div>
  <form on:submit={handleOnSubmit}
        data-testid="zip-code-lookup-form">
    <div class="">
      <input type="text">
    </div>
    <div class="">
      <button type="submit">
        Search
      </button>
    </div>
  </form>
</div>

<style>
    /* styles go here */
</style>
