package rememberpattern;

import java.awt.Color;

import abstractions.Config;

public class RememberPatternConfig extends Config {

    private final static String TITLE = "REMEMBER PATTERN";
    private final static String HOW_TO_DESC = "Memorize the" +
        " dark patterns and replicate it after they hide";
    private final static int GRID_DEFAULT_SIZE = 5;
    private final static int GRID_MIN_SIZE = 3;
    private final static int GRID_MAX_SIZE = 10;
    private final static double LEVEL = 2.5;
    private final static int WRONG_LIMIT = 3;
    private final static int ON_SIGN = 1;
    private final static int OFF_SIGN = 0;
    private final static Color ON_COLOR = Color.DARK_GRAY;
    private final static Color OFF_COLOR = Color.LIGHT_GRAY;
    private final static Color MISSED_COLOR = Color.YELLOW;
    private final static Color WRONG_COLOR = Color.RED;
    private final static String LOAD_TEXT = "Loading . . .";

    public String getTitle() {
        return TITLE;
    }

    public String getHowToDesc() {
        return HOW_TO_DESC;
    }

    public String getLoadText() {
        return LOAD_TEXT;
    }

    public int getGridDefaultSize() {
        return GRID_DEFAULT_SIZE;
    }

    public int getGridMinSize() {
        return GRID_MIN_SIZE;
    }

    public int getGridMaxSize() {
        return GRID_MAX_SIZE;
    }

    public double getLevel() {
        return LEVEL;
    }

    public int getWrongLimit() {
        return WRONG_LIMIT;
    }

    public int getOnSign() {
        return ON_SIGN;
    }

    public int getOffSign() {
        return OFF_SIGN;
    }

    public Color getOnColor() {
        return ON_COLOR;
    }

    public Color getOffColor() {
        return OFF_COLOR;
    }

    public Color getMissedColor() {
        return MISSED_COLOR;
    }

    public Color getWrongColor() {
        return WRONG_COLOR;
    }
}
