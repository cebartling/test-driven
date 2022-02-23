import * as $ from 'jquery';
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

    it('should call document ready', () => {
        spyOn($.fn, 'ready').withArgs(jasmine.any(Function)).and.callThrough();
        const fixture = TestBed.createComponent(AppComponent);
        fixture.detectChanges();
        expect($.fn.ready).toHaveBeenCalledWith(jasmine.any(Function));
        // const compiled = fixture.nativeElement as HTMLElement;
        // expect(compiled.querySelector('h1')?.textContent).toContain(
        //     'Jquery is working!!!',
        // );
    });
});
