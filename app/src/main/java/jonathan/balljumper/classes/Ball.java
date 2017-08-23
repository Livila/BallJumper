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
    private float velocity, startVelocity;
    private float gravity;
    private float deltaX, deltaY;

    public Ball(float x, float y, float radius, float velocity, float gravity, float speed, int color) {
        super(x, y, radius * 2, radius * 2);
        this.radius = radius;
        this.gravity = gravity;
        this.startVelocity = velocity;
        this.velocity = velocity;
        this.speed = speed;
        setColor(color);
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 0);
        p.setColorFilter(filter);
        p.setColor(color);
        canvas.drawCircle(x, y, radius, p);
    }

    /**
     * Bounce the ball.
     */
    public void bounce() {
        y-=speed; // Give it some speed up, so it doesn't get stuck.
        velocity = this.startVelocity; // Reset velocity
    }

    public void move() {
        this.deltaY = y;
        this.deltaX = x;

        velocity = velocity + gravity;
        y = y - speed + velocity;

        this.deltaY = y - this.deltaY;
        this.deltaX = x - this.deltaX;
    }

    public boolean intersects(float x, float y, float w, float h) {
        return x < this.getLeft() + this.width &&
                x + w > this.getLeft() &&
                y < this.getTop() + this.height &&
                y + h > this.getTop();
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

    public void setDeltaX(float newDeltaX) { this.deltaX = newDeltaX; }

    public float getDeltaX() {
        return deltaX;
    }

    public void setDeltaY(float newDeltaY) { this.deltaY = newDeltaY; }

    public float getDeltaY() {
        return deltaY;
    }

    public float getVelocity() { return velocity; }
}
