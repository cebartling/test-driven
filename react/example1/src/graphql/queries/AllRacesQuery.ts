import { gql } from '@apollo/client';

export const ALL_RACES_QUERY = gql`
  query AllRaces {
    allRaces {
      id
      name
      raceDate
    }
  }
`;
