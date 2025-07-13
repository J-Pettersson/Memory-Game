package rememberpattern;

import java.util.Random;

public class RememberPatternModel {

    private int gridSize;
    private double level;
    private int onSign;
    private int offSign;
    private int[][] pattern;
    private int patternSize;
    private int correctCount;
    private int wrongCount;

    public RememberPatternModel(int gridSize, double level,
        int onSign, int offSign) {
        
        this.gridSize = gridSize;
        this.level = level;
        this.onSign = onSign;
        this.offSign = offSign;
        this.correctCount = 0;
        this.wrongCount = 0;

        setPattern();
    }

    public int[][] getPattern() {
        return pattern;
    }

    private void setPattern() {
        Random rand = new Random();
        int count = 0;
        int x;
        int y;
        
        patternSize = (int) Math.floor((gridSize * gridSize) / level);

        pattern = new int[gridSize][gridSize];
        initPattern();
        
        while (count != patternSize) {
            x = rand.nextInt(gridSize);
            y = rand.nextInt(gridSize);

            if (pattern[x][y] != onSign) {
                pattern[x][y] = onSign;
                count++;
            }
        }
    }

    private void initPattern() {
        if (offSign != 0) {
            for (int x = 0 ; x < gridSize ; x++) {
                for (int y = 0 ; y < gridSize ; y++) {
                    pattern[x][y] = offSign;
                }
            }
        }
    }

    public void setCorrectClickCount() {
        correctCount++;
    }

    public void setWrongClickCount() {
        wrongCount++;
    }

    public boolean isCorrectLimitReached() {
        return (correctCount >= patternSize);
    }

    public boolean isWrongLimitReached(int wrongLimit) {
        return (wrongCount >= wrongLimit);
    }
}