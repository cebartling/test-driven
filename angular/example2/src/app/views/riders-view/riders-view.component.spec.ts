import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RidersViewComponent } from './riders-view.component';

describe('RidersViewComponent', () => {
  let component: RidersViewComponent;
  let fixture: ComponentFixture<RidersViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RidersViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RidersViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
