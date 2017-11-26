import {AfterContentInit, Component, Input, OnInit} from '@angular/core';
import {WhiteCard} from "../white-card";
import {GameService} from "../game.service";
import {BlackCard} from "../black-card";
import {WebsocketService} from "../websocket.service";
import {isUndefined} from "util";
import {ActivatedRoute, Router} from "@angular/router";
import {Http} from "@angular/http";
import {Player} from "../player";
import {PlayerService} from "../player.service";
import {GameStatus} from "../game-status";
import {Game} from "../game";
import {GamesService} from "../games.service";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css'],
  providers: [GameService, WebsocketService, PlayerService, GamesService]
})

export class GameComponent implements OnInit, AfterContentInit {

  whiteCards: Array<WhiteCard> = [];

  blackCard: BlackCard;
  playedWhiteCards: WhiteCard[] = [];

  id: string;
  showPlayedCards: boolean;
  game: Game;
  status: GameStatus;

  player: Player;

  private sub: any;

  constructor(private gameService: GameService, private playerService: PlayerService,
              private gamesService: GamesService,
              private route: ActivatedRoute, private http: Http,
            private router: Router) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
    this.id = params['id']; // (+) converts string 'id' to a number

      // In a real app: dispatch action to load the details here.
      this.playerService.getPlayer().then(player => this.player = player);
      this.gamesService.getGame(this.id).then(game => this.game = game);
    });

    this.subscribe();
  }

  ngAfterContentInit() {
    this.loadState();
  }

  play(whiteCard: WhiteCard) {
    if (this.status != GameStatus.WHITE_CARD_REQUEST) {
      return;
    }
    this.gameService.playCard(whiteCard);
    this.status = GameStatus.WAIT_FOR_BLACK_CARD_REQUEST;
  }

  chooseWinner(whiteCard: WhiteCard) {
    this.gameService.chooseWinner(whiteCard);
  }

  start() {
    this.gameService.start();
  }

  leave() {
    this.gamesService.leaveGame(this.game).then(game => this.router.navigateByUrl('game-list'));
  }

  subscribe() {
    var handlers = new Map();
    handlers.set('WHITE_CARD_PLAYED', res => this.handleWhiteCardPlayedEvent(res));
    handlers.set('WHITE_CARD_NEW', res => this.handleWhiteCardNewEvent(res));
    handlers.set('PLAYER_JOINED', res => this.playerJoined(res));
    handlers.set('BLACK_CARD_NEW', res => this.handleBlackCardEvent(res));
    handlers.set('WHITE_CARD_WINNER', res => this.handleWinnerChosenEvent(res));
    handlers.set('WHITE_CARD_REQUEST', res => this.handleWhiteCardRequest(res));
    handlers.set('WINNER_CARD_REQUEST', res => this.handleWinnerCardRequest(res));
    handlers.set('REQUESTING_CHOICE', res => this.handleRequestingChoice(res));

    this.gameService.messageSubject.subscribe(msg => {
        this.handleResponse(msg, handlers);
    });
  }

  loadState() {
    // TODO: load current game state
  }

  handleResponse(gameData : any, handlers : Map<String, (any) => void>) {
    // var gameData = JSON.parse(res.data);
    var eventType = gameData.eventType;
    var handler: (value: any) => void = handlers.get(eventType);
    if (isUndefined(handler)) {
      return;
    }
    handler(gameData);
  }

  private handleWhiteCardRequest(res: any) {
    this.status = GameStatus.WHITE_CARD_REQUEST;
  }

  private handleWinnerCardRequest(res: any) {
    this.status = GameStatus.WINNER_CARD_REQUEST;
  }

  private handleRequestingChoice(res: any) {
    this.showPlayedCards = true;
  }

  private handleWhiteCardPlayedEvent(res: any) {
    var cardId = res.properties["ID"];
    this.playedWhiteCards.push(new WhiteCard(cardId, res.properties["CARD_TEXT"]));
    if (res.properties["PLAYER_ID"] == this.player.id) {
      var indexToRemove = this.whiteCards.findIndex(card => card.id == cardId);
      this.whiteCards.splice(indexToRemove, 1);
    }
  }

  private handleWhiteCardNewEvent(res: any) {
    this.whiteCards.push(new WhiteCard(res.properties["ID"], res.properties["CARD_TEXT"]));
  }

  private playerJoined(res: any) {
    console.log('joined: ' + res.properties["PLAYER_NAME"]);
  }

  private handleBlackCardEvent(res: any) {
    this.blackCard = new BlackCard(res.properties["ID"], res.properties["CARD_TEXT"], res.properties["CARD_TURNS"]);
  }

  private handleWinnerChosenEvent(res: any) {
    this.playedWhiteCards = [];
    alert('winner: ' + res.properties["CARD_TEXT"]);
  }
}
