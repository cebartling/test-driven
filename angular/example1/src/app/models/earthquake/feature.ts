import { FeatureProperties } from './feature-properties';
import { FeatureGeometry } from './feature-geometry';

export interface Feature {
  type: string;
  properties: FeatureProperties;
  geometry: FeatureGeometry;
  id: string;
}
