import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { AppComponent } from '../app.component';
import { MockComponent } from 'ng-mocks';
import { NavigationBarComponent } from '../components/navigation-bar/navigation-bar.component';
import { RouterTestingModule } from '@angular/router/testing';

describe('AppComponent', () => {
  let fixture: ComponentFixture<AppComponent>;
  let app: AppComponent;

  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({
        imports: [RouterTestingModule],
        declarations: [AppComponent, MockComponent(NavigationBarComponent)],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    app = fixture.componentInstance;
  });

  it('should create the app', () => {
    expect(app).toBeTruthy();
  });

  describe('template rendering', () => {
    it(`should have as title 'example1'`, () => {
      expect(app.title).toEqual('TDD Example 1');
    });
  });
});
