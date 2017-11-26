import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Game} from "../game";
import {GamesService} from "../games.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css'],
  encapsulation: ViewEncapsulation.None,
  providers: [GamesService]
})
export class GameListComponent implements OnInit {

  games: Array<Game> = [];

  constructor(private gamesService: GamesService, private router: Router) { }

  ngOnInit() {
    this.gamesService.getGames().then(games => {
      this.games = games;
      console.log('finished loading games');
    });
  }

  joinGame(game: Game) {
    this.gamesService.joinGame(game).then(game => {
      console.log('joined new game');
      this.router.navigateByUrl('game/' + game.id);
    })
  }

  createGame() {
    this.gamesService.createGame().then(game => {
      console.log('created new game');
      this.router.navigateByUrl('game/' + game.id);
    })
  }
}
