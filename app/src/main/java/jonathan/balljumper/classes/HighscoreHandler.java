package jonathan.balljumper.classes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Locale;

/**
 * Created by Jonathan on 23/08/2017.
 */

public class HighscoreHandler {
    private final Paint paint;

    private int currentBounces; // Keeps track of how many times you have bounced.
    private float currentHeight; // Keeps track of how far you've gotten.
    private long startTime; // Keeps track of the time you've played.

    public HighscoreHandler() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);

        startTime = System.currentTimeMillis();
    }

    public final void draw(Canvas canvas) {
        String[] text = {
                String.format(Locale.ENGLISH, "Height: %d meters", (int) (currentHeight / 10)),
                String.format(Locale.ENGLISH, "Bounces: %d", (currentBounces)),
                String.format(Locale.ENGLISH, "Time: %s", getTimePlayed()),
        };

        for (int i = 0; i < text.length; ++i) {
            canvas.drawText(text[i], 7, 30 + 26 * i, paint);
        }
    }

    private String getTimePlayed() {
        long currentTime = (System.currentTimeMillis() - startTime) / 1000;
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
    }

    public void addHeight(float height) {
        this.currentHeight += height;
    }

    public void addBounce() {
        this.currentBounces++;
    }
}
