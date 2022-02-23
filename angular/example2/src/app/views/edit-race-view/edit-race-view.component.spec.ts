import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditRaceViewComponent } from './edit-race-view.component';

describe('EditRaceViewComponent', () => {
  let component: EditRaceViewComponent;
  let fixture: ComponentFixture<EditRaceViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditRaceViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditRaceViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
