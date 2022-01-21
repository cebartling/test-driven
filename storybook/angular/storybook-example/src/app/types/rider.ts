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

  constructor(rider: Rider) {
    this.id = rider.id;
    this.givenName = rider.givenName;
    this.surname = rider.surname;
    this.birthDate = rider.birthDate;
    this.gender = rider.gender;
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
