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

  export function handleOnClickResetFormButton() {
    profileForm.reset();
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
           class:is-invalid={!$emailAddress.valid}
           id="emailFormControlInput"
           placeholder="name@example.com"
           bind:value={$emailAddress.value}>
  </div>
  <div class="mb-3 col-8 error-message">
    {#if $profileForm.hasError('emailAddress.required')}
      The email address is a required field!
    {/if}
    {#if $profileForm.hasError('emailAddress.not_an_email')}
      The email address that you provided doesn't look right!
    {/if}
  </div>
</div>
<div class="row">
  <div class="mb-3 col-4">
    <label for="givenNameFormControlInput" class="form-label">Given name</label>
    <input type="text"
           class="form-control"
           class:is-invalid={!$givenName.valid}
           id="givenNameFormControlInput"
           bind:value={$givenName.value}>
  </div>
  <div class="mb-3 col-8 error-message">
    {#if $profileForm.hasError('givenName.required')}The given name is a required field!{/if}
  </div>
</div>
<div class="row">
  <div class="mb-3 col-4">
    <label for="surnameFormControlInput" class="form-label">Surname</label>
    <input type="text"
           class="form-control"
           class:is-invalid={!$surname.valid}
           id="surnameFormControlInput"
           bind:value={$surname.value}>
  </div>
  <div class="mb-3 col-8 error-message">
    {#if $profileForm.hasError('surname.required')}The surname is a required field!{/if}
  </div>
</div>
<div class="row">
  <hr/>
</div>
<div class="row">
  <div class="col-6">
    <button type="button"
            class="btn btn-secondary"
            id="resetProfileFormButton"
            on:click={handleOnClickResetFormButton}>
      Reset form to original values
    </button>
    <button type="button"
            class="btn btn-success ml-2"
            id="saveProfileButton"
            disabled={!$profileForm.valid}
            on:click={handleOnClickSaveButton}>
      Save
    </button>
  </div>
</div>


<style>
  .error-message {
      font-size: 0.9rem;
      font-weight: 600;
      color: darkred;
      margin-top: 2.5rem;
  }
</style>
