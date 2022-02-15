import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MockComponent } from 'ng-mocks';
import { of } from 'rxjs';
import { DateTime } from 'luxon';
import { MapViewComponent } from '../map-view.component';
import { EarthquakeDataService } from '../../../services/earthquake-data.service';
import { featureCollection } from '../../../__tests__/data/feature-collection-test-data';
import { MapComponent } from '../../../components/map/map.component';

describe('MapViewComponent', () => {
  let component: MapViewComponent;
  let fixture: ComponentFixture<MapViewComponent>;
  let earthquakeDataServiceSpyObject: jasmine.SpyObj<EarthquakeDataService>;

  beforeEach(
    waitForAsync(() => {
      earthquakeDataServiceSpyObject = jasmine.createSpyObj<EarthquakeDataService>('EarthquakeDataService', ['query']);
      TestBed.configureTestingModule({
        declarations: [MapViewComponent, MockComponent(MapComponent)],
        providers: [{ provide: EarthquakeDataService, useValue: earthquakeDataServiceSpyObject }],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(MapViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  describe('constructor', () => {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    describe('dependency injection', () => {
      it('earthquakeService', () => {
        expect(component.earthquakeDataService).toBe(earthquakeDataServiceSpyObject);
      });
    });
  });

  describe('Angular lifecycle', () => {
    describe('ngOnInit', () => {
      const featureCollection$ = of(featureCollection);

      beforeEach(() => {
        earthquakeDataServiceSpyObject.query.and.returnValue(featureCollection$);
        component.ngOnInit();
      });

      it('should invoke query on the earthquakeService', () => {
        expect(earthquakeDataServiceSpyObject.query).toHaveBeenCalledWith(jasmine.any(DateTime), jasmine.any(DateTime));
      });

      it('should set the featureCollection$ property on the component', () => {
        expect(component.featureCollection$).toBe(featureCollection$);
      });
    });
  });
});
