import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RacesViewComponent } from './races-view.component';

describe('RacesViewComponent', () => {
  let component: RacesViewComponent;
  let fixture: ComponentFixture<RacesViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RacesViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RacesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
