import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditRiderViewComponent } from './edit-rider-view.component';

describe('EditRiderViewComponent', () => {
  let component: EditRiderViewComponent;
  let fixture: ComponentFixture<EditRiderViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditRiderViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditRiderViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
