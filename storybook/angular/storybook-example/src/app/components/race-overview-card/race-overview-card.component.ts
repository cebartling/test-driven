import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Race} from "../../types/race";

@Component({
  selector: 'app-race-overview-card[race]',
  templateUrl: './race-overview-card.component.html',
  styleUrls: ['./race-overview-card.component.css']
})
export class RaceOverviewCardComponent implements OnInit, OnChanges {

  @Input()  race!: Race;

  constructor() {
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
  }
}
