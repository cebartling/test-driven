import {
  RaceParticipant,
  RaceParticipantImpl,
} from '../app/types/race-participant';

export const participant1 = new RaceParticipantImpl({
  id: '2ab31c4d-d920-4818-812a-b55edf2f8bb8',
  raceId: 'bb54c76f-3c78-40e3-808b-75dec4986c0e',
  riderId: '9a46a083-3d8e-451f-aeca-c4c1b0d98951',
  startedRace: true,
  finishedRace: true,
  startDateTime: new Date('2022-01-22T10:02:34'),
  endDateTime: new Date('2022-01-22T11:47:02'),
} as RaceParticipant);

export const participant2 = new RaceParticipantImpl({
  id: '2ab31c4d-d920-4818-812a-b55edf2f8bb8',
  raceId: 'bb54c76f-3c78-40e3-808b-75dec4986c0e',
  riderId: 'cf70aa7a-61aa-4d5d-b312-427249b4088a',
  startedRace: true,
  finishedRace: true,
  startDateTime: new Date('2022-01-22T10:02:34'),
  endDateTime: new Date('2022-01-22T11:37:36'),
} as RaceParticipant);

export const participants = [participant1, participant2] as RaceParticipant[];
