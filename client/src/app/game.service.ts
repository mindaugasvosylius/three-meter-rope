import {Injectable} from "@angular/core";
import {WhiteCard} from "./white-card";
import {WebSocketTemplate} from "./game/websocket_template";
import {WebsocketService} from "app/websocket.service";
import {Subject} from "rxjs/Subject";

export interface EventMessage {
  eventType: string,
  properties: any
}

@Injectable()
export class GameService {

  ws: WebSocketTemplate;

  public messageSubject: Subject<EventMessage>;

  constructor(wsService: WebsocketService) {
    this.messageSubject = <Subject<EventMessage>>wsService
      .connect("ws://localhost:8088/game-handler")
      .map((response: MessageEvent): EventMessage => {
        let data = JSON.parse(response.data);
        return {
          eventType: data.eventType,
          properties: data.properties
        }
      });
  }

  playCard(whiteCard: WhiteCard) {
    console.log('play card: ' + whiteCard.text);
    this.messageSubject.next({
      eventType: 'WHITE_CARD_PLAYED',
      properties: {
        ID: whiteCard.id
      }
    });
  }

  chooseWinner(whiteCard: WhiteCard) {
    this.messageSubject.next({
      eventType: 'WHITE_CARD_CHOSEN',
      properties: {
        ID: whiteCard.id
      }
    });
  }

  start() {
    this.messageSubject.next({
      eventType: 'GAME_STARTED',
      properties: {}
    });
  }
}
