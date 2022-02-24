import * as $ from 'jquery';
import { Component } from '@angular/core';

@Component({
    selector: 'app-experiments-view',
    templateUrl: './experiments-view.component.html',
    styleUrls: ['./experiments-view.component.css'],
})
export class ExperimentsViewComponent {
    onClickSaveButton() {
        $('div#content-update-section').css('background-color', 'red');
        // eslint-disable-next-line no-undef
        // setTimeout(function () {
        //     $('div#content-update-section').css('background-color', 'white');
        // }, 1200);
    }
}
