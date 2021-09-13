import { Component, Input, OnInit } from '@angular/core';
import { circle, latLng, MapOptions, polygon, tileLayer } from 'leaflet';
import { FeatureCollection } from '../../models/earthquake/feature-collection';
import { LeafletControlLayersConfig } from '@asymmetrik/ngx-leaflet';

@Component({
  selector: 'tdd-example1-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
})
export class MapComponent implements OnInit {
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
        'Open Cycle Map': tileLayer('http://{s}.tile.opencyclemap.org/cycle/{z}/{x}/{y}.png', {
          maxZoom: 18,
          attribution: '...',
        }),
      },
      overlays: {
        'Big Circle': circle([46.95, -122], { radius: 5000 }),
        'Big Square': polygon([
          [46.8, -121.55],
          [46.9, -121.55],
          [46.9, -121.7],
          [46.8, -121.7],
        ]),
      },
    } as LeafletControlLayersConfig;
  }

  ngOnInit(): void {}
}
