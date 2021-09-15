import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'tdd-example1-profile-view',
  templateUrl: './profile-view.component.html',
  styleUrls: ['./profile-view.component.scss'],
})
export class ProfileViewComponent implements OnInit {
  profileFormGroup: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.profileFormGroup = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
    });
  }

  ngOnInit(): void {}

  onFormSubmit() {
    console.info(this.profileFormGroup.value);
  }
}
