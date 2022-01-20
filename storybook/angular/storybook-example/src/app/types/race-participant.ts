export interface RaceParticipant {
  id: string;
  raceId: string;
  riderId: string;
  startedRace: boolean;
  finishedRace: boolean;
  startDateTime: Date | null;
  endDateTime: Date | null;
}
