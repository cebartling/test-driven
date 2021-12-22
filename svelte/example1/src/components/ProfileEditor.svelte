<script lang="ts">
  import type {Profile} from '../models/Profile';

  export let profile: Profile;

  const handleOnClickSaveButton = async () => {
    const requestInit = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(profile)
    } as RequestInit;
    const response = await fetch(`/api/profiles/${profile.id}`, requestInit);
    if (!response.ok) {
      throw new Error(`Error updating profile. Status code: ${response.status}`)
    }
  }
</script>

<div class="row">
  <hr/>
</div>
<div class="row">
  <div class="mb-3 col-4">
    <label for="emailFormControlInput" class="form-label">Email address</label>
    <input type="email"
           class="form-control"
           id="emailFormControlInput"
           placeholder="name@example.com"
           bind:value={profile.emailAddress}>
  </div>
</div>
<div class="row">
  <div class="mb-3 col-4">
    <label for="givenNameFormControlInput" class="form-label">Given name</label>
    <input type="text"
           class="form-control"
           id="givenNameFormControlInput"
           bind:value={profile.givenName}>
  </div>
</div>
<div class="row">
  <div class="mb-3 col-4">
    <label for="surnameFormControlInput" class="form-label">Surname</label>
    <input type="text"
           class="form-control"
           id="surnameFormControlInput"
           bind:value={profile.surname}>
  </div>
</div>
<div class="row">
  <hr/>
</div>
<div class="row">
  <div class="col-6">
    <button type="button"
            class="btn btn-success"
            on:click={handleOnClickSaveButton}>Save
    </button>
  </div>
</div>
