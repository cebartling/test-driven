import { Gender } from './gender';

export interface Rider {
  id: string;
  givenName: string;
  surname: string;
  birthDate: Date;
  gender: Gender;

  calculateAgeAtDate(referenceDate: Date): number;
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

  calculateAgeAtDate(referenceDate: Date): number {
    const birthDate = new Date(this.birthDate);
    let age = referenceDate.getFullYear() - birthDate.getFullYear();
    const m = referenceDate.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && referenceDate.getDate() < birthDate.getDate())) {
      age--;
    }
    return age;
  }
}
