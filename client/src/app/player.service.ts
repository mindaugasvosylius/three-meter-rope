import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Game} from "./game";
import {Player} from "./player";

@Injectable()
export class PlayerService {

  http: Http;

  constructor(http: Http) {
    this.http = http;
  }

  getPlayer(): Promise<Player> {
    return this.http.get('http://localhost:8088/player/get', {withCredentials: true})
      .map(res => res.json())
      .toPromise();
  }
}
