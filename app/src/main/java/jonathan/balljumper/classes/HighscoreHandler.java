package jonathan.balljumper.classes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Locale;

import jonathan.balljumper.GameSurfaceView;

/**
 * Created by Jonathan on 23/08/2017.
 */

public class HighscoreHandler {
    private final Paint paint;

    private String[] highscoreText;
    private int[] efficiencySamples;

    private int currentBounces; // Keeps track of how many times you have bounced.
    private float currentHeight; // Keeps track of how far you've gotten.
    private float currentScore; // Keeps track of how much score you have.
    private long startTime; // Keeps track of when you started playing a game.
    private long currentTime; // Keeps track of how long you have played.

    public HighscoreHandler() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);

        reset();
        update();
        highscoreUpdate();
    }

    public void update() {
        currentTime = (System.currentTimeMillis() - startTime) / 1000;
        int currentScore = (int)(this.currentScore + currentTime + currentBounces * 20);

        efficiencySamples[(int)(currentTime % efficiencySamples.length)] = (int)(currentScore / (currentTime == 0 ? 1 : currentTime));

        // Calculate the average efficiency.
        int efficiencyAvg = 0;
        for (int i = 0; i < efficiencySamples.length; ++i) {
            efficiencyAvg += efficiencySamples[i];
        }
        efficiencyAvg = efficiencyAvg / efficiencySamples.length;

        String efficiency = "";
        if (efficiencyAvg < 88)
            efficiency = "No words...";
        else if (efficiencyAvg < 90)
            efficiency = "Very bad";
        else if (efficiencyAvg < 92)
            efficiency = "Bad";
        else if (efficiencyAvg < 94)
            efficiency = "Okay";
        else if (efficiencyAvg < 96)
            efficiency = "Good";
        else if (efficiencyAvg < 98)
            efficiency = "Very good";
        else if (efficiencyAvg < 100)
            efficiency = "Excellent!";
        else
            efficiency = "Spectacular!";


        highscoreText = new String[] {
                String.format(Locale.ENGLISH, "Height: %d meters", (int) (currentHeight / 40)),
                String.format(Locale.ENGLISH, "Bounces: %d", currentBounces),
                String.format(Locale.ENGLISH, "Score: %d", (int) (currentScore / 10)),
                String.format(Locale.ENGLISH, "Time: %s", getTimePlayed()),
                String.format(Locale.ENGLISH, "Efficiency (%d): %s", efficiencyAvg, efficiency),
        };
    }

    public final void drawScoreInGame(Canvas canvas) {
        for (int i = 0; i < highscoreText.length; ++i) {
            canvas.drawText(highscoreText[i], 7, 30 + 26 * i, paint);
        }
    }

    public final void drawScoreFinal(Canvas canvas) {
        Paint paint = new Paint(this.paint);
        paint.setTextSize(50f);
        paint.setColor(Color.WHITE);

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.BLACK);
        backgroundPaint.setAlpha(100);

        canvas.drawRect(
                25,
                GameSurfaceView.getScreenSize().y / 2 - (paint.getTextSize() * highscoreText.length) / 2 - paint.getTextSize() / 2 - 5,
                GameSurfaceView.getScreenSize().x - 25,
                GameSurfaceView.getScreenSize().y / 2 + (paint.getTextSize() * highscoreText.length) / 2 - 5,
                backgroundPaint);

        for (int i = 0; i < highscoreText.length; ++i) {
            canvas.drawText(
                    highscoreText[i],
                    40,
                    GameSurfaceView.getScreenSize().y / 2 - (paint.getTextSize() * highscoreText.length) / 2 + paint.getTextSize() / 2 + paint.getTextSize() * i,
                    paint);
        }
    }

    public final void drawHighscore(Canvas canvas) {
        Paint paint = new Paint(this.paint);
        paint.setTextSize(50f);

        canvas.drawText("Highscore", 50, 100, paint);
    }

    private String getTimePlayed() {
        int s = ((int)(currentTime % 60));
        int m = ((int)(currentTime / 60 % 60));
        int h = ((int)(currentTime / 60 / 60 % 60));
        int d = ((int)(currentTime / 60 / 60 / 60 % 24));

        if (d > 0) { return d + ":" + h + ":" + m + ":" + s; }
        else if (h > 0) { return h + ":" + m + ":" + s; }
        else if (m > 0) { return m + "m " + s + "s"; }
        else { return s + "s"; }
    }

    public void resetAndSave() {
        highscoreSave();

        reset();
        update();
    }

    public final void highscoreUpdate() {
        highscoreLoad();
    }

    public final void highscoreSave() {
        // Check if there is a new highscore, if so then save.
    }

    public final void highscoreLoad() {
        // Load highscore from disk.
    }

    public void reset() {
        currentHeight = 0;
        currentBounces = 0;
        currentScore = 0;
        startTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();

        efficiencySamples = new int[10];
        for (int i = 0; i < efficiencySamples.length; ++i) {
            efficiencySamples[i] = 95;
        }
    }

    public void addHeight(float height) {
        this.currentHeight += height;
    }

    public void addBounce() {
        this.currentBounces++;
    }

    public void addScore(float score) {
        this.currentScore += score;
    }
}
