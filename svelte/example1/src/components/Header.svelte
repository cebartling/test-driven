<script lang="ts">
  export let activeView = 'home';
</script>

<div class="container">
  <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
    <a href="/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
      <span class="fs-4">Svelte Test-driven development</span>
    </a>
    <ul class="nav nav-pills">
      <li class="nav-item">
        <a id="homeNav"
           href="/"
           class="nav-link"
           class:active={activeView === 'home'}
           aria-current="page">Home</a>
      </li>
      <li class="nav-item">
        <a id="profileNav"
           href="/profile"
           class="nav-link"
           class:active={activeView === 'profile'}
           aria-current="page">Profile</a>
      </li>
    </ul>
  </header>
</div>

