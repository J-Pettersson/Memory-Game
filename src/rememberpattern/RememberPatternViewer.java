package rememberpattern;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;

import abstractions.Config;
import abstractions.Viewer;
import exceptions.IllegalConfigException;

public class RememberPatternViewer extends Viewer {

    private RememberPatternConfig config;
    private JPanel centerPanel;
    private JPanel playPanel;
    private CardLayout centerPanelCard;
    private JProgressBar loadBar;

    private int[][] pattern;
    private int gridSize;
    private LinkedList<Entry<JButton, Integer>> buttons;
    private ActionListener clickOnButtonListener;
    private ActionListener clickOffButtonListener;
    private ChangeListener gridSizeSettingListener;

    public RememberPatternViewer(Config config) {
        super(config);

        if (!(config instanceof RememberPatternConfig)) {
            throw new IllegalConfigException("Expected " +
                "RememberPatternConfig");
        }

        this.config = (RememberPatternConfig) config;
    }

    @Override
    public JPanel getGamePanelCenter() {
        return getCenterPanel();
    }

    @Override
    public void showLoadScreen() {
        centerPanelCard.first(centerPanel);
    }

    @Override
    public void showPlayScreen() {
        centerPanelCard.last(centerPanel);
    }

    public void updateLoadProgressBar(int updateTime) {
        loadBar.setValue(updateTime);
    }

    @Override
    public void initGame() {
        int gap = 100 / gridSize;

        buttons = new LinkedList<>();

        playPanel.setLayout(new GridLayout(gridSize, gridSize, gap, gap));

        for (int x = 0 ; x < pattern.length ; x++) {
            for (int y = 0 ; y < pattern[0].length ; y++) {

                JButton button = new JButton();
                JPanel buttonPanel = new JPanel(new GridLayout());
                
                button.setEnabled(false);

                if (pattern[x][y] == config.getOnSign()) {
                    addPatternButton(button, true, config);
                } else {
                    addPatternButton(button, false, config);
                }
                buttonPanel.add(button);

                playPanel.add(buttonPanel);
            }
        }
    }

    @Override
    public JPanel getSettingsPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel settingsPanel;
        JLabel sizeLabel;
        JSlider sizeSlider;

        sizeLabel = new JLabel("Grid Size:");

        sizeSlider = new JSlider(
            JSlider.HORIZONTAL,
            config.getGridMinSize(),
            config.getGridMaxSize(),
            gridSize
        );
        sizeSlider.setMajorTickSpacing(1);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);
        sizeSlider.addChangeListener(gridSizeSettingListener);

        settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());
        settingsPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        settingsPanel.add(sizeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        settingsPanel.add(sizeSlider, gbc);

        return settingsPanel;
    }

    public void setPattern(int[][] pattern) {
        this.pattern = pattern;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public void addClickOnButtonListener(ActionListener al) {
        this.clickOnButtonListener = al;
    }

    public void addClickOffButtonListener(ActionListener al) {
        this.clickOffButtonListener = al;
    }

    public void addGridSizeSettingListener(ChangeListener al) {
        this.gridSizeSettingListener = al;
    }

    private void addPatternButton(JButton button, boolean onButton,
            RememberPatternConfig config) {

        ActionListener al;
        Color bc;
        int sign;

        if (onButton) {
            al = clickOnButtonListener;
            bc = config.getOnColor();
            sign = config.getOnSign();
        } else {
            al = clickOffButtonListener;
            bc = config.getOffColor();
            sign = config.getOffSign();
        }
        button.addActionListener(al);
        button.setBackground(bc);
        buttons.add(new AbstractMap.SimpleEntry<>(button, sign));
    }

    public void showMissedPattern() {
        Entry<JButton, Integer> entry;
        JButton button;
        int size = buttons.size();
        int sign = config.getOnSign();
        Color offColor = config.getOffColor();
        Color missedColor = config.getMissedColor();

        for (int i = 0 ; i < size ; i++) {
            entry = buttons.get(i);
            button = entry.getKey();

            if (entry.getValue() == sign && button.getBackground() == offColor) {
                button.setBackground(missedColor);
            }
            button.setEnabled(false);
        }
    }

    public void showPattern() {
        displayPattern(true);
    }

    public void hidePattern() {
        displayPattern(false);
    }

    private void displayPattern(boolean show) {
        Entry<JButton, Integer> entry;
        JButton button;
        int size = buttons.size();
        int sign = config.getOnSign();
        Color color = (show ? config.getOnColor() : config.getOffColor());
        boolean enable = !show;

        for (int i = 0 ; i < size ; i++) {
            entry = buttons.get(i);
            button = entry.getKey();

            if (entry.getValue() == sign) {
                button.setBackground(color);
            }
            button.setEnabled(enable);
        }
    }

    private JPanel getCenterPanel() {
        centerPanel = new JPanel();
        centerPanelCard = new CardLayout();

        centerPanel.setLayout(centerPanelCard);
        centerPanel.setBorder(new EmptyBorder(25, 200, 25, 200));

        setLoadPanel();
        setPlayPanel();

        return centerPanel;
    }

    private void setLoadPanel() {
        JPanel loadPanel = new JPanel();

        loadBar = new JProgressBar();
        loadBar.setValue(0);
        loadBar.setStringPainted(true);
        loadBar.setString(config.getLoadText());
        loadBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadPanel.setLayout(new GridBagLayout());
        loadPanel.add(loadBar);

        centerPanel.add(loadPanel);
    }

    private void setPlayPanel() {
        playPanel = new JPanel();
        playPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        centerPanel.add(playPanel);
    }
}
