<script lang="ts">
  import {onMount} from 'svelte';
  import Header from '../components/Header.svelte';
  import ProfileEditor from '../components/ProfileEditor.svelte';
  import {profileService, profile} from "../stores/ProfileStore";

  onMount(profileService.fetchProfile);
</script>

<main class="flex-shrink-0">
  <Header activeView="profile"/>
  <div class="container">
    <h1 class="mt-5 mb-3">Profile</h1>
    {#if $profile}
      <ProfileEditor profile={$profile}/>
    {:else}
      <div class="row">
        <div class="alert alert-info">
          Waiting for profile data...
        </div>
      </div>
    {/if}
  </div>
</main>
