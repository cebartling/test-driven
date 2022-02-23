import * as $ from 'jquery';
import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
    isJQueryWorking: string = '';

    ngOnInit(): void {
        // eslint-disable-next-line no-undef
        $(document).ready(() => {
            this.isJQueryWorking = 'Jquery is working!!!';
        });
    }
}
