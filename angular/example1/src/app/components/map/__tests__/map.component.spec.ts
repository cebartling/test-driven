import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapComponent } from '../map.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { featureCollection } from '../../../__tests__/data/feature-collection-test-data';

describe('MapComponent', () => {
  let component: MapComponent;
  let fixture: ComponentFixture<MapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MapComponent],
      imports: [LeafletModule],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('rendering template', () => {
    describe('when featureCollection is undefined', () => {
      let compiled: Element | null;

      beforeEach(() => {
        component.featureCollection = undefined;
        fixture.detectChanges();
        compiled = fixture.nativeElement as HTMLElement;
      });

      it('should render loading message', () => {
        expect(compiled?.querySelector('.alert-info')?.textContent).toContain('Loading earthquake data...');
      });
    });

    describe('when featureCollection is defined', () => {
      let compiled: Element | null;

      beforeEach(() => {
        component.featureCollection = featureCollection;
        fixture.detectChanges();
        compiled = fixture.nativeElement as HTMLElement;
      });

      it('should render Leaflet map', () => {
        expect(compiled?.querySelector('.leaflet')).toBeDefined();
      });
    });
  });
});
