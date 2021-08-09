import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapComponent } from '../map.component';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { featureCollection } from '../../../__tests__/data/feature-collection';

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
      beforeEach(() => {
        component.featureCollection = undefined;
        fixture.detectChanges();
      });

      it('should render loading message', () => {
        const compiled = fixture.nativeElement as HTMLElement;
        expect(compiled.querySelector('.alert-info')?.textContent).toContain('Loading earthquake data...');
      });
    });

    describe('when featureCollection is defined', () => {
      beforeEach(() => {
        component.featureCollection = featureCollection;
        fixture.detectChanges();
      });

      it('should render Leaflet map', () => {
        const compiled = fixture.nativeElement as HTMLElement;
        // expect(compiled.querySelector('.leaflet')).toBeDefined();
      });
    });
  });
});
