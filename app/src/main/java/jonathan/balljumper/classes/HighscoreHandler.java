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

    private int currentBounces; // Keeps track of how many times you have bounced.
    private float currentHeight; // Keeps track of how far you've gotten.
    private long startTime; // Keeps track of when you started playing a game.
    private long currentTime; // Keeps track of how long you have played.

    public HighscoreHandler() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);

        startTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();
    }

    public void update() {
        currentTime = (System.currentTimeMillis() - startTime) / 1000;

        highscoreText = new String[] {
                String.format(Locale.ENGLISH, "Height: %d meters", (int) (currentHeight / 10)),
                String.format(Locale.ENGLISH, "Bounces: %d", (currentBounces)),
                String.format(Locale.ENGLISH, "Time: %s", getTimePlayed()),
        };
    }

    public final void draw(Canvas canvas) {
        for (int i = 0; i < highscoreText.length; ++i) {
            canvas.drawText(highscoreText[i], 7, 30 + 26 * i, paint);
        }
    }

    public final void drawScore(Canvas canvas) {
        Paint paint = new Paint(this.paint);
        paint.setTextSize(50f);
        paint.setColor(Color.WHITE);

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.BLACK);
        backgroundPaint.setAlpha(100);

        canvas.drawRect(
                35,
                GameSurfaceView.getScreenSize().y / 2 - (paint.getTextSize() * highscoreText.length) / 2 - paint.getTextSize() / 2 - 5,
                GameSurfaceView.getScreenSize().x - 35,
                GameSurfaceView.getScreenSize().y / 2 + (paint.getTextSize() * highscoreText.length) / 2 - 5,
                backgroundPaint);

        for (int i = 0; i < highscoreText.length; ++i) {
            canvas.drawText(
                    highscoreText[i],
                    50,
                    GameSurfaceView.getScreenSize().y / 2 - (paint.getTextSize() * highscoreText.length) / 2 + paint.getTextSize() / 2 + paint.getTextSize() * i,
                    paint);
        }
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
        // TODO: Save function.

        currentHeight = 0;
        currentBounces = 0;
        startTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();
    }

    public void addHeight(float height) {
        this.currentHeight += height;
    }

    public void addBounce() {
        this.currentBounces++;
    }
}
