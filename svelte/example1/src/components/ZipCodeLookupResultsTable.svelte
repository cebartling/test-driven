<script lang="ts">
  import {ZipCodeLookupResult} from "../models/ZipCodeLookupResult";

  export let zipCodeLookupResults: ZipCodeLookupResult[] = undefined;
</script>

<table class="table">
  <tbody>
  {#each zipCodeLookupResults as result (result.id)}
    <tr>
      <td>{result.id}</td>
      <td>{result.zipCode}</td>
      <td>{result.area}</td>
    </tr>
  {/each}
  </tbody>
</table>
