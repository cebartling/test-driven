<script lang="ts">
  import {Router} from "svelte-router-spa";
  import {routes} from "./routes";
</script>

<Router {routes} />

<footer class="footer mt-auto py-3 bg-light">
  <div class="container">
    <span class="text-muted">Pintail Consulting LLC &copy; 2021</span>
  </div>
</footer>


<style>
    .container {
        width: auto;
        max-width: 680px;
        padding: 0 15px;
    }
</style>
