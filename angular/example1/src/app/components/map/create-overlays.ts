import { FeatureCollection } from '../../models/earthquake/feature-collection';
import { circle, CircleMarkerOptions, Layer } from 'leaflet';
import { Feature } from '../../models/earthquake/feature';

export function createOverlays(featureCollection: FeatureCollection | null | undefined): { [name: string]: Layer } {
  const result: { [name: string]: Layer } = {};
  if (featureCollection) {
    featureCollection.features.forEach((feature: Feature) => {
      const title = feature.properties.title;
      const longitude = feature.geometry.coordinates[0];
      const latitude = feature.geometry.coordinates[1];
      const circleMarkerOptions = {
        radius: 10000 * feature.properties.mag,
      } as CircleMarkerOptions;
      result[title] = circle([latitude, longitude], circleMarkerOptions);
    });
  }
  return result;
}
