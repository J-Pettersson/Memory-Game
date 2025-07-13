package all;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GameViewer {

    private final static String HOME_LOGO = "Memory Games";
    private final static String GO_BACK_BUTTON_TEXT = "Go Back";
    private final static String SETTINGS_BUTTON_TEXT = "Settings";
    private final static String PLAY_AGAIN_BUTTON_TEXT = "Play Again";
    private final static String SETTINGS_DIALOG_TEXT = "Settings";
    private final static String SETTINGS_SAVE_TEXT = "Save";
    private final static String FONT_TYPE = "Times New Roman";
    private final static double FRAME_WIDTH_RATIO = 2.0;
    private final static double FRAME_HEIGHT_RATIO = 1.6;
    private final static double INITIAL_FRAME_PROPORTION = 0.65;
    private final static double INCREMENT_FRAME_PROPORTION = 0.90;

    private JFrame frame;
    private CardLayout frameCard;
    private JPanel framePanel;
    private JPanel homePanel;
    private JPanel optionsPanel;
    private JPanel gamePanel;
    private JButton goToHomeButton;
    private JButton settingsButton;
    private JButton playAgainButton;
    private JButton saveSettingsButton;
    private JDialog settingsDialog;

    public GameViewer() {
        setFrame();
    }

    public void show() {
        frame.setVisible(true);
    }

    public void initGameOptionPanel(String title, String howToDesc,
            ActionListener al) {
        optionsPanel.add(getGameOptionPanel(title, howToDesc, al));
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
    }

    private void setFrame() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(getFrameDimension());
        frame.setResizable(false);

        frameCard = new CardLayout();

        framePanel = new JPanel();
        framePanel.setLayout(frameCard);

        setHomeScreenPanel();
        setGamePanel();

        frame.add(framePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Dimension getFrameDimension() {
        Dimension currentDim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension targetDim = new Dimension();
        double currentWidth = currentDim.getWidth();
        double currentHeight = currentDim.getHeight();
        double currentTotal = currentWidth + currentHeight;
        double ratioTotal = FRAME_WIDTH_RATIO + FRAME_HEIGHT_RATIO;
        double widthScale = (FRAME_WIDTH_RATIO / ratioTotal) * currentTotal;
        double heightScale = (FRAME_HEIGHT_RATIO / ratioTotal) * currentTotal;
        double targetWidth = widthScale * INITIAL_FRAME_PROPORTION;
        double targetHeight = heightScale * INITIAL_FRAME_PROPORTION;

        while (targetWidth > currentWidth || targetHeight > currentHeight) {
            targetWidth *= INCREMENT_FRAME_PROPORTION;
            targetHeight *= INCREMENT_FRAME_PROPORTION;
        }
        
        targetDim.setSize(targetWidth, targetHeight);

        return targetDim;
    }

    private void setHomeScreenPanel() {
        homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());

        setHomeTitlePanel();
        setHomeOptionPanel();

        framePanel.add(homePanel);
    }

    private void setHomeTitlePanel() {
        JPanel titlePanel = new JPanel();
        JLabel label = new JLabel();

        label.setText(HOME_LOGO);
        label.setFont(new Font(FONT_TYPE, Font.PLAIN, 50));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBorder(new EmptyBorder(50, 0, 30, 0));
        titlePanel.add(label);

        homePanel.add(titlePanel, BorderLayout.PAGE_START);
    }

    private void setHomeOptionPanel() {
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        
        homePanel.add(optionsPanel, BorderLayout.CENTER);
    }

    private void setGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());

        framePanel.add(gamePanel);
    }

    private JPanel getGameOptionPanel(String title, String howToDesc,
            ActionListener al) {
        JPanel mainPanel = new JPanel();
        JButton button = new JButton();
        Font buttonFont = new Font(FONT_TYPE, Font.PLAIN, 20);

        mainPanel.setLayout(new GridLayout());
        mainPanel.setPreferredSize(new Dimension(280, 80));
        mainPanel.setMaximumSize(new Dimension(280, 80));
        mainPanel.setMinimumSize(new Dimension(280, 80));

        button = new JButton(title);
        button.setFont(buttonFont);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setToolTipText(howToDesc);
        button.addActionListener(al);

        mainPanel.add(button);

        return mainPanel;
    }

    private JButton getButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);  
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    public void addGoToHomeListener(ActionListener al) {
        goToHomeButton.addActionListener(al);
    }

    public void addSettingsListener(ActionListener al) {
        settingsButton.addActionListener(al);
    }

    public void addPlayAgainListener(ActionListener al) {
        playAgainButton.addActionListener(al);
    }

    public void addSaveSettingsListener(ActionListener al) {
        saveSettingsButton.addActionListener(al);
    }

    public void showSettingsPopUp(JPanel settingsPanel) {
        JPanel savePanel = new JPanel();
        
        settingsDialog = new JDialog(frame, SETTINGS_DIALOG_TEXT);
        saveSettingsButton = getButton(SETTINGS_SAVE_TEXT);

        savePanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        savePanel.add(saveSettingsButton);

        settingsDialog.setLayout(new BorderLayout());
        settingsDialog.add(settingsPanel, BorderLayout.CENTER);
        settingsDialog.add(savePanel, BorderLayout.PAGE_END);
        settingsDialog.pack();
        settingsDialog.setLocationRelativeTo(frame);
        settingsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        settingsDialog.setVisible(true);
    }

    public void closeSettingsPopUp() {
        settingsDialog.dispose();
    }

    public void showHomeScreen() {
        frameCard.first(framePanel);
    }

    public void showGameScreen() {
        frameCard.last(framePanel);
    }

    public void cleanGamePanel() {
        gamePanel.removeAll();
    }

    public void fillGamePanelHeader(String title, String howToDesc) {
        JPanel headerPanel = new JPanel();

        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.add(getLabel(title, 30, 30, 0, 0, 0));
        headerPanel.add(getLabel(howToDesc, 20, 10, 0, 0, 0));

        gamePanel.add(headerPanel, BorderLayout.NORTH);
    }

    private JLabel getLabel(String str, int size, int top, int left,
        int bottom, int right) {
        
        JLabel label = new JLabel(str);
        Font labelFont = new Font(FONT_TYPE, Font.PLAIN, size);

        label.setBorder(new EmptyBorder(top, left, bottom, right));
        label.setFont(labelFont);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        return label;
    }

    public void fillGamePanelCenter(JPanel centerPanel) {
        gamePanel.add(centerPanel, BorderLayout.CENTER);
    }

    public void fillGamePanelFooter() {
        JPanel footerPanel = new JPanel();

        goToHomeButton = getButton(GO_BACK_BUTTON_TEXT);
        settingsButton = getButton(SETTINGS_BUTTON_TEXT);
        playAgainButton = getButton(PLAY_AGAIN_BUTTON_TEXT);

        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        footerPanel.setBorder(new EmptyBorder(20, 0, 40, 0));
        footerPanel.add(goToHomeButton);
        footerPanel.add(settingsButton);
        footerPanel.add(playAgainButton);

        gamePanel.add(footerPanel, BorderLayout.SOUTH);
    }  
}