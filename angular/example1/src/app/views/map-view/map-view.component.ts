import { Component, OnInit } from '@angular/core';
import { EarthquakeDataService } from '../../services/earthquake-data.service';
import { DateTime } from 'luxon';
import { FeatureCollection } from '../../models/earthquake/feature-collection';
import { Observable } from 'rxjs';

@Component({
  selector: 'tdd-example1-map-view',
  templateUrl: './map-view.component.html',
  styleUrls: ['./map-view.component.scss'],
})
export class MapViewComponent implements OnInit {
  featureCollection$: Observable<FeatureCollection> | undefined;

  constructor(public earthquakeDataService: EarthquakeDataService) {}

  ngOnInit(): void {
    const startDateTime: DateTime = DateTime.now().minus({ days: 1 });
    const endDateTime: DateTime = DateTime.now();
    this.featureCollection$ = this.earthquakeDataService.query(startDateTime, endDateTime);
  }
}
