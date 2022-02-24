import * as $ from 'jquery';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ExperimentsViewComponent } from './experiments-view.component';

describe('ExperimentsViewComponent', () => {
    let component: ExperimentsViewComponent;
    let fixture: ComponentFixture<ExperimentsViewComponent>;

    beforeEach(
        waitForAsync(async () => {
            await TestBed.configureTestingModule({
                declarations: [ExperimentsViewComponent],
            }).compileComponents();
        }),
    );

    beforeEach(() => {
        fixture = TestBed.createComponent(ExperimentsViewComponent);
        component = fixture.componentInstance;
        // fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    describe('onClickGradientButton function', () => {
        beforeEach(() => {
            spyOn($.fn, 'css').and.callThrough();
            component.onClickSaveButton();
        });

        it('should set the background color to red for the content update section', () => {
            // @ts-ignore
            expect($.fn.css).toHaveBeenCalledWith('background-color', 'red');
        });
    });
});
