package abstractions;

import java.awt.event.ActionListener;

import all.GameViewer;

public abstract class Controller {

    private GameViewer gui;
    private Viewer viewer;
    private Config config;

    public abstract void playControls();
    public abstract void refreshControls();
    public abstract void setOnWinRunnable(Runnable onWinRunnable);

    public Controller(GameViewer gui, Viewer viewer, Config config) {
        this.gui = gui;
        this.viewer = viewer;
        this.config = config;
    }

    public void setGamePanel() {
        gui.cleanGamePanel();
        gui.fillGamePanelHeader(config.getTitle(), config.getHowToDesc());
        gui.fillGamePanelFooter();
        gui.fillGamePanelCenter(viewer.getGamePanelCenter());
        gui.showGameScreen();
        viewer.showLoadScreen();
    }

    public void showSettingsPopUp() {
        gui.showSettingsPopUp(viewer.getSettingsPanel());
    }

    public void closeSettingsPopUp() {
        gui.closeSettingsPopUp();
    }

    public void addGoToHomeListener(ActionListener al) {
        gui.addGoToHomeListener(al);
    }

    public void addSettingsListener(ActionListener al) {
        gui.addSettingsListener(al);
    }

    public void addPlayAgainListener(ActionListener al) {
        gui.addPlayAgainListener(al);
    }

    public void addSaveSettingsListener(ActionListener al) {
        gui.addSaveSettingsListener(al);
    }
}