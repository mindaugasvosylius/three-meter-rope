import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Game} from "./game";

@Injectable()
export class GamesService {

  http: Http;

  constructor(http: Http) {
    this.http = http;
  }

  getGames(): Promise<Array<Game>> {
    return this.http.get('http://localhost:8088/game-list', {withCredentials: true})
      .map(res => res.json())
      .toPromise();
  }

  getGame(id: string): Promise<Game> {
    return this.http.get('http://localhost:8088/game/get', {withCredentials: true, params: {id: id}})
      .map(res => res.json())
      .toPromise();
  }

  createGame(): Promise<Game> {
    return this.http.post('http://localhost:8088/game', '', {withCredentials: true})
      .map(res => res.json())
      .toPromise();
  }

  joinGame(game: Game): Promise<Game> {
    return this.http.post('http://localhost:8088/game/join', '', {withCredentials: true,
    params: {id: game.id}})
      .map(res => res.json())
      .toPromise();
  }

  leaveGame(game: Game): Promise<Game> {
    return this.http.post('http://localhost:8088/game/leave', '', {withCredentials: true,
      params: {id: game.id}})
      .map(res => res.json())
      .toPromise();
  }
}
