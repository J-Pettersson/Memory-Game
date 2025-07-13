package rememberpattern;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

import abstractions.Config;
import abstractions.Controller;
import abstractions.Viewer;
import all.GameViewer;
import exceptions.IllegalConfigException;
import exceptions.IllegalViewerException;

public class RememberPatternController extends Controller {

    private RememberPatternViewer viewer;
    private RememberPatternConfig config;
    private RememberPatternModel model;
    private SwingWorker<Void, Void> worker;
    private boolean isWorkerWorking;
    private Timer loadTimer;
    private Timer memorizeTimer;
    private Runnable onWinRunnable;
    private int gridSize;

    public RememberPatternController(GameViewer gui, Viewer viewer,
        Config config) {

        super(gui, viewer, config);

        if (!(viewer instanceof RememberPatternViewer)) {
            throw new IllegalViewerException("Expected " +
                "RememberPatternViewer");
        }

        if (!(config instanceof RememberPatternConfig)) {
            throw new IllegalConfigException("Expected " +
                "RememberPatternConfig");
        }

        this.viewer = (RememberPatternViewer) viewer;
        this.config = (RememberPatternConfig) config;
        this.isWorkerWorking = false;
        this.gridSize = this.config.getGridDefaultSize();
    }

    @Override
    public void playControls() {
        
        isWorkerWorking = true;

        worker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() {

                model = new RememberPatternModel(gridSize, config.getLevel(),
                    config.getOnSign(), config.getOffSign());

                viewer.setPattern(model.getPattern());
                viewer.setGridSize(gridSize);
                viewer.addClickOnButtonListener(e -> handleClickOnButton(e));
                viewer.addClickOffButtonListener(e -> handleClickOffButton(e));
                viewer.addGridSizeSettingListener(e -> handleGridSizeSetting(e));
                viewer.initGame();

                loadTimer = new Timer(50, new LoadListener());
                memorizeTimer = new Timer(3000, new MemorizeListener());
                memorizeTimer.setRepeats(false);

                return null;
            }

            @Override
            protected void done() {
                loadTimer.start();
            }
        };
        worker.execute();
    }

    @Override
    public void refreshControls() {
        if (isWorkerWorking) {
            worker.cancel(true);
        }

        if (memorizeTimer != null && memorizeTimer.isRunning()) {
            memorizeTimer.stop();
        }

        if (loadTimer != null && loadTimer.isRunning()) {
            loadTimer.stop();
        }
        isWorkerWorking = false;
    }

    @Override
    public void setOnWinRunnable(Runnable onWinRunnable) {
        this.onWinRunnable = onWinRunnable;
    }
    
    private void handleClickOnButton(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        button.setEnabled(false);
        button.setBackground(config.getOnColor());
        model.setCorrectClickCount();

        if (model.isCorrectLimitReached()) {
            onWinRunnable.run();
        }
    }

    private void handleClickOffButton(ActionEvent e) {
        JButton button = (JButton) e.getSource();

        button.setEnabled(false);
        button.setBackground(config.getWrongColor());
        model.setWrongClickCount();

        if (model.isWrongLimitReached(config.getWrongLimit())) {
            viewer.showMissedPattern();
        }
    }

    private void handleGridSizeSetting(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();

        if (!slider.getValueIsAdjusting()) {
            gridSize = slider.getValue();
        }
    }

    private class LoadListener implements ActionListener {

        private int loadTime;

        public LoadListener() {
            super();
            loadTime = 0;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            loadTime += 5;
            viewer.updateLoadProgressBar(loadTime);

            if (loadTime >= 100) {
                loadTimer.stop();
                viewer.showPlayScreen();
                viewer.showPattern();
                memorizeTimer.start();
            }
        }
    }

    private class MemorizeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            viewer.hidePattern();
        }
    }
}
