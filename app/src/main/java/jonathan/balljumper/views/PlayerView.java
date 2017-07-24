package jonathan.balljumper.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import jonathan.balljumper.R;

/**
 * Created by Jonathan on 24/07/2017.
 */

public class PlayerView extends View {
    private Paint paint = new Paint();
    private static int circleRadius = 10;

    private float x = 50;
    private float y = 50;

    public PlayerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        paint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(x, y, circleRadius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                this.x = x;
                this.y = y;
                break;
        }

        PlayerView playerView = (PlayerView)findViewById(R.id.playerView);
        playerView.invalidate();

        return true;
    }
}
