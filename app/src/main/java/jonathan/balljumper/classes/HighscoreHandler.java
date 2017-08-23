package jonathan.balljumper.classes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jonathan on 23/08/2017.
 */

public class HighscoreHandler {
    private Paint paint;

    private long currentScore;

    public HighscoreHandler() {
        currentScore = 0;

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);
    }

    public void draw(Canvas canvas) {
        canvas.drawText(String.format("Score: %d", currentScore), 6, 30, paint);
    }

    public void addScore(long score) {
        currentScore += score;
    }

    public long getScore() {
        return currentScore;
    }
}
