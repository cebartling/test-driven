<script lang="ts">
  export let zipCode: string = undefined;

  export function handleOnSubmit() {
    console.log('handleOnSubmit');
  }
</script>

<div>
  <form on:submit={handleOnSubmit}>
    <div class="">
      <input type="text">
    </div>
    <div class="">
      <button type="submit">Search</button>
    </div>
  </form>
</div>

<style>
    /* styles go here */
</style>
