import javax.swing.SwingUtilities;

import all.GameController;
import all.GameViewer;

public class MemoryGame {
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            GameViewer gui = new GameViewer();

            new GameController(gui);

            gui.show();
        });
    }
}