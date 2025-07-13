package all;

import enums.Game;

public class GameModel {

    private Game currentGame;

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public Game getCurrentGame() {
        return currentGame;
    }
}