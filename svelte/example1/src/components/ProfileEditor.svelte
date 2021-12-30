<script lang="ts">
  import {createForm} from 'svelte-forms-lib';
  import * as yup from 'yup';
  import type {Profile} from '../models/Profile';
  import {ProfileServices} from "../services/ProfileServices";

  export let profile: Profile;

  const profileSchema = yup.object().shape({
    emailAddress: yup.string()
      .required('The email address is a required field!')
      .email('The email address doesn\'t quite look right!'),
    givenName: yup.string().required('The given name is a required field!'),
    surname: yup.string().required('The surname is a required field!'),
  });


  export const doOnSubmit = async (values) => {
    profile.surname = values.surname;
    profile.givenName = values.givenName;
    profile.emailAddress = values.emailAddress;

    const response = await ProfileServices.updateProfile(profile);
    if (!response.ok) {
      throw new Error(`Error updating profile. Status code: ${response.status}`)
    }
  }

  export const {form, handleChange, handleSubmit, isValid, isSubmitting, errors} = createForm({
    initialValues: {
      emailAddress: profile.emailAddress,
      givenName: profile.givenName,
      surname: profile.surname,
    },
    validationSchema: profileSchema,
    onSubmit: doOnSubmit
  });

  export const handleOnClickResetProfileButton = () => {
    form.set({
      emailAddress: profile.emailAddress,
      givenName: profile.givenName,
      surname: profile.surname,
    });
  }

</script>

<div class="row">
  <hr/>
</div>
<form id="profileForm"
      on:submit|preventDefault={handleSubmit}>
  <div class="row">
    <div class="mb-3 col-4">
      <label for="emailAddressFormControlInput"
             class="form-label">
        Email address
      </label>
      <input type="email"
             class="form-control"
             id="emailAddressFormControlInput"
             name="emailAddressFormControlInput"
             placeholder="name@example.com"
             class:is-invalid={$errors.emailAddress}
             on:change={handleChange}
             on:blur={handleChange}
             bind:value={$form.emailAddress}>
    </div>
    <div id="emailAddressErrorMessage" class="mb-3 col-8 error-message">
      {#if $errors.emailAddress}{$errors.emailAddress}{/if}
    </div>
  </div>
  <div class="row">
    <div class="mb-3 col-4">
      <label for="givenNameFormControlInput"
             class="form-label">
        Given name
      </label>
      <input type="text"
             class="form-control"
             id="givenNameFormControlInput"
             name="givenNameFormControlInput"
             class:is-invalid={$errors.givenName}
             on:change={handleChange}
             on:blur={handleChange}
             bind:value={$form.givenName}>
    </div>
    <div class="mb-3 col-8 error-message">
      {#if $errors.givenName}{$errors.givenName}{/if}
    </div>
  </div>
  <div class="row">
    <div class="mb-3 col-4">
      <label for="surnameFormControlInput"
             class="form-label">
        Surname
      </label>
      <input type="text"
             class="form-control"
             name="surnameFormControlInput"
             id="surnameFormControlInput"
             class:is-invalid={$errors.surname}
             on:change={handleChange}
             on:blur={handleChange}
             bind:value={$form.surname}>
    </div>
    <div class="mb-3 col-8 error-message">
      {#if $errors.surname}{$errors.surname}{/if}
    </div>
  </div>
  <div class="row">
    <hr/>
  </div>
  <div class="row">
    <div class="col-6">
      <button type="button"
              on:click={handleOnClickResetProfileButton}
              class="btn btn-secondary"
              id="resetProfileButton">
        Reset form fields
      </button>
      <button type="submit"
              class="btn btn-success"
              disabled={$isSubmitting}
              id="saveProfileButton">
        {#if $isSubmitting}Saving...{:else}Save{/if}
      </button>
    </div>
  </div>
</form>


<style>
    .error-message {
        font-size: 0.9rem;
        font-weight: 700;
        color: darkred;
        vertical-align: center;
        margin-top: 2.6rem;
    }
</style>
