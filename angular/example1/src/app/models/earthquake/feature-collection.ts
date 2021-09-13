import { FeatureCollectionMetadata } from './feature-collection-metadata';
import { Feature } from './feature';

export interface FeatureCollection {
  type: string;
  metadata: FeatureCollectionMetadata;
  features: Feature[];
  bbox: number[];
}
