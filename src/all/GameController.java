package all;

import abstractions.Config;
import abstractions.Controller;
import abstractions.Viewer;
import enums.Game;
import exceptions.IllegalGameException;
import rememberpattern.RememberPatternConfig;
import rememberpattern.RememberPatternController;
import rememberpattern.RememberPatternViewer;

public class GameController {

    GameViewer gui;
    GameModel gameModel;
    Viewer viewer;
    Config config;
    Controller controller;
    RememberPatternConfig rpConfig;

    public GameController(GameViewer gui) {
        this.gui = gui;
        this.gameModel = new GameModel();
        this.rpConfig = new RememberPatternConfig();

        initAllGameOption();
    }

    private void initAllGameOption() {
        gui.initGameOptionPanel(
            rpConfig.getTitle(),
            rpConfig.getHowToDesc(),
            e -> play(Game.REMEMBER_PATTERN)
        );
    }

    private void goToHome() {
        gui.showHomeScreen();
    }

    private void openSettings() {
        controller.showSettingsPopUp();
        controller.addSaveSettingsListener(e -> playGame());
        controller.addSaveSettingsListener(e -> closeSettings());
    }

    private void closeSettings() {
        controller.closeSettingsPopUp();
    }

    private void playGame() {
        play(gameModel.getCurrentGame());
    }

    private void play(Game game) {
        initGame(game);

        controller.refreshControls();
        controller.setGamePanel();
        controller.addGoToHomeListener(e -> goToHome());
        controller.addSettingsListener(e -> openSettings());
        controller.addPlayAgainListener(e -> playGame());
        controller.setOnWinRunnable(() -> playGame());
        controller.playControls();
    }

    private void initRememberPattern(Game game) {
        if (controller == null || 
            !(controller instanceof RememberPatternController)) {
            
            viewer = new RememberPatternViewer(rpConfig);
            controller = new RememberPatternController(gui, viewer, rpConfig);
        }
    }

    private void initGame(Game game) {
        gameModel.setCurrentGame(game);

        switch (game) {
            case REMEMBER_PATTERN:
                initRememberPattern(game);
                break;
        
            default:
                throw new IllegalGameException("Game does not exist");
        }
    }
}