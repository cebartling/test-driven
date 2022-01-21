import { Rider, RiderImpl } from '../rider';
import { Gender } from '../gender';

describe('RiderImpl model class', () => {
  const data = {
    id: '9a46a083-3d8e-451f-aeca-c4c1b0d98951',
    givenName: 'John',
    surname: 'Jones',
    birthDate: new Date('1986-01-02'),
    gender: Gender.Male,
  } as Rider;

  describe('calculateAgeToday method', () => {
    let rider: Rider;
    let age = 0;

    beforeEach(() => {
      rider = new RiderImpl(data);
      const today = new Date();
      const birthDate = new Date(data.birthDate);
      age = today.getFullYear() - birthDate.getFullYear();
      const m = today.getMonth() - birthDate.getMonth();
      if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }
    });

    it('should calculate the appropriate age for today', () => {
      expect(rider.calculateAgeToday()).toEqual(age);
    });
  });
});
