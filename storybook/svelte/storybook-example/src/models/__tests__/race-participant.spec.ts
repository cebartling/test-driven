import { RaceParticipant, RaceParticipantImpl } from '../race-participant';

describe('RaceParticipantImpl model class', () => {
  describe('calculateCompletedRaceTimeInSeconds method', () => {
    describe('when startDateTime and endDateTime are populated', () => {
      it('should calculate an appropriate value', () => {
        const raceParticipant = new RaceParticipantImpl({
          id: '2ab31c4d-d920-4818-812a-b55edf2f8bb8',
          raceId: 'bb54c76f-3c78-40e3-808b-75dec4986c0e',
          riderId: '9a46a083-3d8e-451f-aeca-c4c1b0d98951',
          startedRace: true,
          finishedRace: true,
          startDateTime: new Date('2022-01-22T10:02:34'),
          endDateTime: new Date('2022-01-22T11:47:02'),
        } as RaceParticipant);
        expect(raceParticipant.calculateCompletedRaceTimeInSeconds()).toEqual(
          6268
        );
      });
    });

    describe('when startDateTime is null and endDateTime is populated', () => {
      it('should return a value of 0', () => {
        const raceParticipant = new RaceParticipantImpl({
          id: '2ab31c4d-d920-4818-812a-b55edf2f8bb8',
          raceId: 'bb54c76f-3c78-40e3-808b-75dec4986c0e',
          riderId: '9a46a083-3d8e-451f-aeca-c4c1b0d98951',
          startedRace: true,
          finishedRace: true,
          startDateTime: null,
          endDateTime: new Date('2022-01-22T11:47:02'),
        } as RaceParticipant);
        expect(raceParticipant.calculateCompletedRaceTimeInSeconds()).toEqual(
          0
        );
      });
    });

    describe('when startDateTime is populated and endDateTime is null', () => {
      it('should return a value of 0', () => {
        const raceParticipant = new RaceParticipantImpl({
          id: '2ab31c4d-d920-4818-812a-b55edf2f8bb8',
          raceId: 'bb54c76f-3c78-40e3-808b-75dec4986c0e',
          riderId: '9a46a083-3d8e-451f-aeca-c4c1b0d98951',
          startedRace: true,
          finishedRace: true,
          startDateTime: new Date('2022-01-22T10:02:34'),
          endDateTime: null,
        } as RaceParticipant);
        expect(raceParticipant.calculateCompletedRaceTimeInSeconds()).toEqual(
          0
        );
      });
    });
  });
});
