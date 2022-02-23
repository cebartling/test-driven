import { AppComponent } from './app.component';
import { RouterTestingModule } from '@angular/router/testing';
import { TestBed } from '@angular/core/testing';

describe('AppComponent', () => {
    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [RouterTestingModule],
            declarations: [AppComponent],
        }).compileComponents();
    });

    it('should create the app', () => {
        const fixture = TestBed.createComponent(AppComponent);
        const app = fixture.componentInstance;
        expect(app).toBeTruthy();
    });

    // it('should call document ready', () => {
    //     const spy = spyOn($.fn, 'ready')
    //         .withArgs(jasmine.any(Function))
    //         .and.callThrough();
    //     const fixture = TestBed.createComponent(AppComponent);
    //     fixture.detectChanges();
    //     expect(spy).toHaveBeenCalledWith(jasmine.any(Function));
    //     // const compiled = fixture.nativeElement as HTMLElement;
    //     // expect(compiled.querySelector('h1')?.textContent).toContain(
    //     //     'Jquery is working!!!',
    //     // );
    // });
});
