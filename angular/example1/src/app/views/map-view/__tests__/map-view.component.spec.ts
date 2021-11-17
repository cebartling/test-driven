import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { MapViewComponent } from '../map-view.component';
import { EarthquakeDataService } from '../../../services/earthquake-data.service';
import { of } from 'rxjs';
import { featureCollection } from '../../../__tests__/data/feature-collection-test-data';
import { DateTime } from 'luxon';
import { MapComponent } from '../../../components/map/map.component';

describe('MapViewComponent', () => {
  let component: MapViewComponent;
  let fixture: ComponentFixture<MapViewComponent>;
  let earthquakeDataServiceMock: { query: jasmine.Spy };

  beforeEach(
    waitForAsync(() => {
      earthquakeDataServiceMock = jasmine.createSpyObj('EarthquakeDataService', ['query']);
      TestBed.configureTestingModule({
        declarations: [MapViewComponent, MapComponent],
        providers: [{ provide: EarthquakeDataService, useValue: earthquakeDataServiceMock }],
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
        expect(component.earthquakeDataService).toBe(earthquakeDataServiceMock);
      });
    });
  });

  describe('Angular lifecycle', () => {
    describe('ngOnInit', () => {
      const featureCollection$ = of(featureCollection);

      beforeEach(() => {
        earthquakeDataServiceMock.query.and.returnValue(featureCollection$);
        component.ngOnInit();
      });

      it('should invoke query on the earthquakeService', () => {
        expect(earthquakeDataServiceMock.query).toHaveBeenCalledWith(jasmine.any(DateTime), jasmine.any(DateTime));
      });

      it('should set the featureCollection$ property on the component', () => {
        expect(component.featureCollection$).toBe(featureCollection$);
      });
    });
  });
});
