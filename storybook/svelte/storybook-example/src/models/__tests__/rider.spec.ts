import { Rider, RiderImpl } from '../rider';
import { Gender } from '../gender';

describe('RiderImpl model class', () => {
  const rider = new RiderImpl({
    id: '9a46a083-3d8e-451f-aeca-c4c1b0d98951',
    givenName: 'John',
    surname: 'Jones',
    birthDate: new Date('1986-01-02'),
    gender: Gender.Male,
  } as Rider);

  describe('calculateAgeAtDate method', () => {
    it('should calculate the appropriate age for date right before birthday', () => {
      const today = new Date('1988-01-01');
      expect(rider.calculateAgeAtDate(today)).toEqual(1);
    });

    it('should calculate the appropriate age for date right after birthday', () => {
      const today = new Date('1988-01-03');
      expect(rider.calculateAgeAtDate(today)).toEqual(2);
    });

    it('should calculate the appropriate age for date on birthday', () => {
      const today = new Date('1988-01-02');
      expect(rider.calculateAgeAtDate(today)).toEqual(2);
    });
  });
});
