import * as $ from 'jquery';
import JQuery from 'jquery';
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
        let messagesElement: JQuery<HTMLElement>;
        let contentUpdateSectionElement: JQuery<HTMLElement>;

        beforeEach(() => {
            messagesElement = jasmine.createSpyObj<JQuery<HTMLElement>>(
                'Messages',
                ['find'],
            );
            contentUpdateSectionElement = jasmine.createSpyObj<
                JQuery<HTMLElement>
            >('ContentUpdateSection', ['css']);
            spyOn($.fn, 'find')
                .withArgs('div#messages')
                .and.returnValue(messagesElement);
            (messagesElement.find as jasmine.Spy)
                .withArgs('div#content-update-section')
                .and.returnValue(contentUpdateSectionElement);
            component.onClickSaveButton();
        });

        it('should find the messages div via jQuery', () => {
            expect($.fn.find).toHaveBeenCalledWith('div#messages');
        });

        it('should find the content update section div via jQuery', () => {
            expect(messagesElement.find).toHaveBeenCalledWith(
                'div#content-update-section',
            );
        });

        it('should set the background color to red for the content update section', () => {
            expect(contentUpdateSectionElement.css).toHaveBeenCalled();
            const cssSpy = contentUpdateSectionElement.css as jasmine.Spy;
            const argsFor = cssSpy.calls.argsFor(0);
            expect(argsFor.length).toEqual(2);
            expect(argsFor[0]).toEqual('background-color');
            expect(argsFor[1]).toEqual('red');
        });
    });
});
