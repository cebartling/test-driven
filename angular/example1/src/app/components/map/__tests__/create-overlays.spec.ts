import { Circle, Layer } from 'leaflet';
import { createOverlays } from '../create-overlays';
import { featureCollection } from '../../../__tests__/data/feature-collection-test-data';

describe('createOverlays function', () => {
  let overlays: { [name: string]: Layer };
  let layer: Layer;

  describe('when featureCollection is a valid FeatureCollection instance', () => {
    beforeEach(() => {
      overlays = createOverlays(featureCollection);
      layer = overlays[featureCollection.features[0].properties.title];
    });

    it('should contain layers for each feature', () => {
      expect(overlays[featureCollection.features[0].properties.title]).toBeDefined();
    });

    it('should contain circle layer', () => {
      expect(layer).toBeInstanceOf(Circle);
    });

    it('should set the circle layer latitude appropriately', () => {
      expect((layer as Circle).getLatLng().lat).toBe(featureCollection.features[0].geometry.coordinates[1]);
    });

    it('should set the circle layer longitude appropriately', () => {
      expect((layer as Circle).getLatLng().lng).toBe(featureCollection.features[0].geometry.coordinates[0]);
    });

    it('should set the circle radius option appropriately', () => {
      expect((layer as Circle).options.radius).toBe(featureCollection.features[0].properties.mag * 10000);
    });
  });

  describe('when featureCollection is null', () => {
    beforeEach(() => {
      overlays = createOverlays(null);
    });

    it('should not contain any layers', () => {
      // @ts-ignore
      expect(Object.keys(overlays).length).toBe(0);
    });
  });

  describe('when featureCollection is undefined', () => {
    beforeEach(() => {
      overlays = createOverlays(undefined);
    });

    it('should not contain any layers', () => {
      // @ts-ignore
      expect(Object.keys(overlays).length).toBe(0);
    });
  });
});
