import type { Gender } from './gender';

export interface Rider {
  id: string;
  givenName: string;
  surname: string;
  birthDate: Date;
  gender: Gender;

  calculateAgeAtDate(referenceDateString: any): number;
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

  calculateAgeAtDate(referenceDateString: any): number {
    const birthDate = new Date(this.birthDate);
    const referenceDate = new Date(referenceDateString);
    let age = referenceDate.getFullYear() - birthDate.getFullYear();
    const m = referenceDate.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && referenceDate.getDate() < birthDate.getDate())) {
      age--;
    }
    return age;
  }
}
