import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RiderDetailViewComponent } from './rider-detail-view.component';

describe('RiderDetailViewComponent', () => {
  let component: RiderDetailViewComponent;
  let fixture: ComponentFixture<RiderDetailViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RiderDetailViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RiderDetailViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
