import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaceDetailsViewComponent } from './race-details-view.component';

describe('RaceDetailsViewComponent', () => {
  let component: RaceDetailsViewComponent;
  let fixture: ComponentFixture<RaceDetailsViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RaceDetailsViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RaceDetailsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
