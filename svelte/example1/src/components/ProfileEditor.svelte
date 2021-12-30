<script lang="ts">
  import {ProfileServices} from '../services/ProfileServices';
  import type {Profile} from '../models/Profile';
  import { form, field } from 'svelte-forms';
  import { required, email } from 'svelte-forms/validators';

  export let profile: Profile;

  const emailAddress = field('emailAddress', profile.emailAddress, [required(), email()]);
  const givenName = field('givenName', profile.givenName, [required()]);
  const surname = field('surname', profile.surname, [required()]);
  const profileForm = form(emailAddress, givenName, surname);

  export async function handleOnClickSaveButton() {
    profile.emailAddress = $emailAddress.value;
    profile.givenName = $givenName.value;
    profile.surname = $surname.value;
    const response = await ProfileServices.updateProfile(profile);
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
           bind:value={$emailAddress.value}>
  </div>
</div>
<div class="row">
  <div class="mb-3 col-4">
    <label for="givenNameFormControlInput" class="form-label">Given name</label>
    <input type="text"
           class="form-control"
           id="givenNameFormControlInput"
           bind:value={$givenName.value}>
  </div>
</div>
<div class="row">
  <div class="mb-3 col-4">
    <label for="surnameFormControlInput" class="form-label">Surname</label>
    <input type="text"
           class="form-control"
           id="surnameFormControlInput"
           bind:value={$surname.value}>
  </div>
</div>
<div class="row">
  <hr/>
</div>
<div class="row">
  <div class="col-6">
    <button type="button"
            class="btn btn-success"
            id="saveProfileButton"
            disabled={!$profileForm.valid}
            on:click={handleOnClickSaveButton}>Save
    </button>
  </div>
</div>
