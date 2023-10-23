import './App.css';

import {Component, useState} from 'react';

class App extends Component {
  state = {
    clients: []
  };

  async componentDidMount() {
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name: 'Player 1', 'points': 0, cards: [] })
    };

    const response = await fetch('/game/create', requestOptions);
    const body = await response.json();
    this.setState({clients: body});
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          Test
        </header>
      </div>
    );
  }
}

export default App;
