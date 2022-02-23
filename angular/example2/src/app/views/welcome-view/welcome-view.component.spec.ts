import * as $ from 'jquery';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { WelcomeViewComponent } from './welcome-view.component';

describe('WelcomeViewComponent', () => {
    let component: WelcomeViewComponent;
    let fixture: ComponentFixture<WelcomeViewComponent>;

    beforeEach(
        waitForAsync(async () => {
            await TestBed.configureTestingModule({
                declarations: [WelcomeViewComponent],
            }).compileComponents();
        }),
    );

    beforeEach(() => {
        fixture = TestBed.createComponent(WelcomeViewComponent);
        component = fixture.componentInstance;
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should call document ready', () => {
        const spy = spyOn($.fn, 'ready')
            .withArgs(jasmine.any(Function))
            .and.callThrough();
        fixture.detectChanges();
        expect(spy).toHaveBeenCalledWith(jasmine.any(Function));
        // const compiled = fixture.nativeElement as HTMLElement;
        // expect(compiled.querySelector('h1')?.textContent).toContain(
        //     'Jquery is working!!!',
        // );
    });
});
