<script lang="ts">
  import Header from '../components/Header.svelte';
  import ProfileEditor from "../components/ProfileEditor.svelte";
  import {onMount} from "svelte";
  import type {Profile} from "../models/Profile";

  export let profileId = "78b6c7b2-6c30-4604-b7cd-56e6cecaae83";
  let profile: Profile;

  onMount(async () => {
    const res = await fetch(`/api/profiles/${profileId}`);
    profile = await res.json();
  });
</script>

<main class="flex-shrink-0">
  <Header activeView="profile"/>
  <div class="container">
    <h1 class="mt-5 mb-3">Profile</h1>
    {#if profile}
      <ProfileEditor profile={profile}/>
    {:else}
      <div class="row">
        <div class="alert alert-info">
          Waiting for profile data...
        </div>
      </div>
    {/if}
  </div>
</main>
