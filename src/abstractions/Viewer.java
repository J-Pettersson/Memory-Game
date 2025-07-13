package abstractions;

import javax.swing.JPanel;

public abstract class Viewer {
    public abstract JPanel getGamePanelCenter();
    public abstract void showLoadScreen();
    public abstract void showPlayScreen();
    public abstract void initGame();   
    public abstract JPanel getSettingsPanel();

    public Viewer(Config config) {
        
    }
}