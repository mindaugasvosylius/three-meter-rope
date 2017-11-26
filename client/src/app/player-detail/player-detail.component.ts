import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Player} from "../player";

import { Http } from '@angular/http';

import { Router } from '@angular/router';

@Component({
  selector: 'app-player-detail',
  templateUrl: './player-detail.component.html',
  styleUrls: ['./player-detail.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class PlayerDetailComponent implements OnInit {

  player: Player;
  http: Http;
  private router: Router;

  constructor(http: Http, router: Router) {
    this.http = http;
    this.router = router;
    this.player = {
      id: 1,
      name: ''
    };
  }

  ngOnInit() {
  }

  submit(player: Player) {
    this.player = player;
    this.http.post('http://localhost:8088/player/edit', '', {params: {'name': player.name}, withCredentials: true})
      .subscribe(data => {
      this.router.navigateByUrl('game-list');
    });
  }

}
