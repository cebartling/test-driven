import { differenceInSeconds } from 'date-fns';

export interface RaceParticipant {
  id: string;
  raceId: string;
  riderId: string;
  startedRace: boolean;
  finishedRace: boolean;
  startDateTime: Date | null;
  endDateTime: Date | null;

  calculateCompletedRaceTimeInSeconds(): number;
}

export class RaceParticipantImpl implements RaceParticipant {
  id: string;
  raceId: string;
  riderId: string;
  startedRace: boolean;
  finishedRace: boolean;
  startDateTime: Date | null;
  endDateTime: Date | null;

  constructor(raceParticipant: RaceParticipant) {
    this.id = raceParticipant.id;
    this.raceId = raceParticipant.raceId;
    this.riderId = raceParticipant.riderId;
    this.startedRace = raceParticipant.startedRace;
    this.finishedRace = raceParticipant.finishedRace;
    this.startDateTime = raceParticipant.startDateTime;
    this.endDateTime = raceParticipant.endDateTime;
  }

  calculateCompletedRaceTimeInSeconds(): number {
    let result = 0;
    if (this.startDateTime && this.endDateTime) {
      result = differenceInSeconds(this.endDateTime, this.startDateTime);
    }
    return result;
  }
}
