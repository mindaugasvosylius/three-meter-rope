export class Game {
  id: string;
  name: string;
  currentPlayerSize: number;
  maxPlayerSize: number;

  constructor(id: string, name: string, currentPlayerSize: number, maxPlayerSize: number) {
    this.id = id;
    this.name = name;
    this.currentPlayerSize = currentPlayerSize;
    this.maxPlayerSize = maxPlayerSize;
  }
}
