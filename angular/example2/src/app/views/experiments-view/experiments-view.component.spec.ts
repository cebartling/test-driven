import * as $ from 'jquery';
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { ExperimentsViewComponent } from './experiments-view.component';
import JQuery from 'jquery';

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
        let selector: JQuery<HTMLElement>;

        beforeEach(() => {
            selector = {
                css: (propertyName, propertyValue) => {},
            } as JQuery<HTMLElement>;
            spyOn($.fn, 'find')
                .withArgs('div#content-update-section')
                .and.returnValue(selector);
            spyOn(selector, 'css').and.callThrough();
            component.onClickSaveButton();
        });

        it('should find the content update section div via jQuery', () => {
            expect($.fn.find).toHaveBeenCalledWith(
                'div#content-update-section',
            );
        });

        it('should set the background color to red for the content update section', () => {
            expect(selector.css).toHaveBeenCalled();
        });
    });
});
