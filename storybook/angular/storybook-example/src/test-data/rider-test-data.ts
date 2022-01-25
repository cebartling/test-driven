import { Rider, RiderImpl } from '../app/types/rider';
import { Gender } from '../app/types/gender';

export const rider1 = new RiderImpl({
  id: '9a46a083-3d8e-451f-aeca-c4c1b0d98951',
  givenName: 'John',
  surname: 'Jones',
  birthDate: new Date('1986-01-02'),
  gender: Gender.Male,
} as Rider);

export const rider2 = new RiderImpl({
  id: 'cf70aa7a-61aa-4d5d-b312-427249b4088a',
  givenName: 'Jack',
  surname: 'Smith',
  birthDate: new Date('1987-10-22'),
  gender: Gender.Male,
} as Rider);

export const riders = [rider1, rider2] as Rider[];
