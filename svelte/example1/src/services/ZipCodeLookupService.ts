import axios from 'axios';




export const lookupZipCode = function(zipCode: string): Promise<any> {
  const url = `/zipCodes?zipCode=${zipCode}`;
  const config = {};
  return axios.get(url, config);
};
