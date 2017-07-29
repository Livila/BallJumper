package jonathan.balljumper.classes;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

/**
 * Created by Jonathan on 27/07/2017.
 */

public class Ball extends Sprite {
    private float radius;

    public Ball(float x, float y, float radius, int color) {
        super(x, y, radius * 2, radius * 2);
        this.radius = radius;
        setColor(color);
        setSpeed(10f);
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(getColor(), 0);
        p.setColorFilter(filter);
        p.setColor(getColor());
        canvas.drawCircle(getX(), getY(), getRadius(), p);
    }

    public void bounce() {
        // Simple bounce.
        getDirection().y *= -1;
    }

    public boolean intersects(float x, float y, float w, float h) {
        return x < this.getLeft() + this.width &&
                x + w > this.getLeft() &&
                y < this.getTop() + this.height &&
                y + h > this.getTop();
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public float getTop() {
        return y - radius;
    }

    @Override
    public float getBottom() {
        return y + radius;
    }

    @Override
    public float getLeft() {
        return x - radius;
    }

    @Override
    public float getRight() {
        return x + radius;
    }
}
