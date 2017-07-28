package jonathan.balljumper.classes;

import android.graphics.Point;

/**
 * Created by Jonathan on 27/07/2017.
 */

public class Ball extends Sprite {
    private float radius;

    public Ball(float x, float y, float radius, int color) {
        super(x, y, radius * 2, radius * 2);
        this.radius = radius;
        setColor(color);
        setSpeed(9);
    }

    public void bounce() {
        // Simple bounce.
        getDirection().x *= -1;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getTop() {
        return getY() - getRadius();
    }

    public float getBottom() {
        return getY() + getRadius();
    }
}
