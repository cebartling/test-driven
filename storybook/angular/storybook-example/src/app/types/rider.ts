import { Gender } from './gender';

export interface Rider {
  id: string;
  givenName: string;
  surname: string;
  birthDate: Date;
  gender: Gender;

  calculateAgeToday(): number;
}

export class RiderImpl implements Rider {
  id: string;
  givenName: string;
  surname: string;
  birthDate: Date;
  gender: Gender;

  constructor(
    id: string,
    givenName: string,
    surname: string,
    birthDateString: string,
    genderString: string
  ) {
    this.id = id;
    this.givenName = givenName;
    this.surname = surname;
    this.birthDate = new Date(birthDateString);
    this.gender = genderString === 'MALE' ? Gender.Male : Gender.Female;
  }

  calculateAgeToday(): number {
    const today = new Date();
    const birthDate = new Date(this.birthDate);
    let age = today.getFullYear() - birthDate.getFullYear();
    const m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
      age--;
    }
    return age;
  }
}
