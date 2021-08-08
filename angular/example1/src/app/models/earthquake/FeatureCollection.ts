export interface FeatureCollectionMetadata {
  generated: number;
  url: string;
  title: string;
  status: number;
  api: string;
  count: number;
}

export interface FeatureProperties {
  mag: number;
  place: string;
  time: number;
  updated: number;
  tz: string | null;
  url: string;
  detail: string;
  felt: any;
  cdi: any;
  mmi: any;
  alert: string | null;
  status: string;
  tsunami: number;
  sig: number;
  net: string;
  code: string;
  ids: string;
  sources: string;
  types: string;
  nst: number;
  dmin: any;
  rms: number;
  gap: number;
  magType: string;
  type: string;
  title: string;
}

export interface FeatureGeometry {
  type: string;
  coordinates: number[];
}

export interface Feature {
  type: string;
  properties: FeatureProperties;
  geometry: FeatureGeometry;
  id: string;
}

export interface FeatureCollection {
  type: string;
  metadata: FeatureCollectionMetadata;
  features: Feature[];
  bbox: number[];
}
