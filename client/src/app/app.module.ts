import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { GameComponent } from './game/game.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FormsModule} from "@angular/forms";

import {
  RouterModule,
  Routes
} from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PlayerDetailComponent } from './player-detail/player-detail.component';
import { GameListComponent } from './game-list/game-list.component';
import {HttpModule} from "@angular/http";
import {HttpClientModule} from "@angular/common/http";

const routes: Routes = [
// basic routes
  { path: '', redirectTo: 'player-detail', pathMatch: 'full' },
  { path: 'player-detail', component: PlayerDetailComponent },
  { path: 'game-list', component: GameListComponent },
  { path: 'game/:id', component: GameComponent }
];

@NgModule({
  declarations: [
    AppComponent,
    GameComponent,
    HomeComponent,
    PlayerDetailComponent,
    GameListComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    RouterModule.forRoot(routes),
    HttpModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
