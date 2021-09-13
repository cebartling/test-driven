import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { latLng, MapOptions, tileLayer } from 'leaflet';
import { FeatureCollection } from '../../models/earthquake/feature-collection';
import { LeafletControlLayersConfig } from '@asymmetrik/ngx-leaflet';
import { createOverlays } from './create-overlays';

@Component({
  selector: 'tdd-example1-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements OnInit, OnChanges {
  @Input() featureCollection: FeatureCollection | null | undefined;

  options: MapOptions;
  layersControl: LeafletControlLayersConfig;

  constructor() {
    this.options = {
      layers: [tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', { maxZoom: 18, attribution: '...' })],
      zoom: 5,
      center: latLng(46.879966, -121.726909),
    } as MapOptions;
    this.layersControl = {
      baseLayers: {
        'Open Street Map': tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          maxZoom: 18,
          attribution: '...',
        }),
        // 'Open Cycle Map': tileLayer('http://{s}.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png', {
        //   maxZoom: 18,
        //   attribution: '...',
        // }),
      },
      overlays: {},
    } as LeafletControlLayersConfig;
  }

  ngOnInit(): void {
    this.layersControl.overlays = createOverlays(this.featureCollection);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.featureCollection) {
      this.layersControl.overlays = createOverlays(this.featureCollection);
    }
  }
}
