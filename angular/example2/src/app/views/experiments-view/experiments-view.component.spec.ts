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
            selector = jasmine.createSpyObj<JQuery<HTMLElement>>(
                'ContentUpdateSection',
                ['css'],
            );
            spyOn($.fn, 'find')
                .withArgs('div#content-update-section')
                .and.returnValue(selector);
            component.onClickSaveButton();
        });

        it('should find the content update section div via jQuery', () => {
            expect($.fn.find).toHaveBeenCalledWith(
                'div#content-update-section',
            );
        });

        it('should set the background color to red for the content update section', () => {
            expect(selector.css).toHaveBeenCalled();
            const cssSpy = selector.css as jasmine.Spy;
            const argsFor = cssSpy.calls.argsFor(0);
            expect(argsFor.length).toEqual(2);
            expect(argsFor[0]).toEqual('background-color');
            expect(argsFor[1]).toEqual('red');
        });
    });
});
