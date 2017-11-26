import {Card} from "./card";

export class BlackCard extends Card {
  requiredTurns: number;
  constructor(id: number, text: string, requiredTurns: number) {
    super(id, text);
    this.requiredTurns = requiredTurns;
  }
}
