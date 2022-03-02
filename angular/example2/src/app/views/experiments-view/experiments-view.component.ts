import * as $ from 'jquery';
import { Component } from '@angular/core';

@Component({
    selector: 'app-experiments-view',
    templateUrl: './experiments-view.component.html',
    styleUrls: ['./experiments-view.component.css'],
})
export class ExperimentsViewComponent {
    onClickSaveButton() {
        $('div#messages')
            .find('div#content-update-section')
            .css('background-color', 'red');
    }
}
