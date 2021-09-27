import { ApplicationDate } from '../application-date';
import * as luxon from 'luxon';

describe('ApplicationDate class', () => {
  let applicationDate: ApplicationDate;

  describe('now static method', () => {
    beforeEach(() => {
      const hardCodedDateTime = luxon.DateTime.utc(2020, 9, 1, 0, 0, 0, 0);
      spyOn(luxon.DateTime, 'now').and.returnValue(hardCodedDateTime);
      applicationDate = ApplicationDate.now();
    });

    it('should create a new ApplicationDate', () => {
      expect(applicationDate).toBeDefined();
    });

    it('should represent the current time and date', () => {
      const expected = '2020-09-01T00:00:00.000Z';
      expect(applicationDate.toISO8601Format()).toEqual(expected);
    });
  });
});
