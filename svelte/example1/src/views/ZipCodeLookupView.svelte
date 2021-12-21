<script lang="ts">
  import ZipCodeLookup from '../components/ZipCodeLookup.svelte';
  import Header from '../components/Header.svelte';
</script>

<main class="flex-shrink-0">
  <Header activeView="zipcodelookup" />
  <div class="container">
    <h1 class="mt-5">Zip code lookup</h1>
    <ZipCodeLookup/>
  </div>
</main>
