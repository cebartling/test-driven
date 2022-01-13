import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationalHeaderComponent } from './navigational-header.component';

describe('NavigationalHeaderComponent', () => {
  let component: NavigationalHeaderComponent;
  let fixture: ComponentFixture<NavigationalHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavigationalHeaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavigationalHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
