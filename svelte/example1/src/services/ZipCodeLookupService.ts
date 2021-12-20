import axios from 'axios';
import type {ZipCodeLookupResult} from "../models/ZipCodeLookupResult";

export const lookupZipCode = function(zipCode: string): Promise<ZipCodeLookupResult[]> {
  const url = `/zipCodes?zipCode=${zipCode}`;
  const config = {};
  return axios.get(url, config);
};
