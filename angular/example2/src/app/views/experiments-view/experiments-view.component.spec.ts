import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExperimentsViewComponent } from './experiments-view.component';

describe('ExperimentsViewComponent', () => {
  let component: ExperimentsViewComponent;
  let fixture: ComponentFixture<ExperimentsViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExperimentsViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExperimentsViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
