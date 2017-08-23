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
    private int jumpBoost;

    private Paint paint;

    public Ball(float x, float y, float radius, float velocity, float gravity, float speed, int color) {
        super(x, y, radius * 2, radius * 2);
        this.radius = radius;
        this.gravity = gravity;
        this.startVelocity = velocity;
        this.velocity = velocity;
        this.speed = speed;
        this.color = color;
        this.jumpBoost = -1;

        paint = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 90);
        paint.setColorFilter(filter);
        paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        canvas.drawOval(getLeft(), getTop(), getRight(), getBottom(), paint);
    }

    /**
     * Bounce the ball.
     */
    public void bounce() {
        if (jumpBoost < 0) {
            jumpBoost = (int)speed; // Give the ball a boost.
            velocity = this.startVelocity; // Reset velocity
        }
    }

    public void move() {
        this.deltaY = y;
        this.deltaX = x;

        if (jumpBoost >= 0) {
            int change = 0;
            if (jumpBoost > ((int)speed / 3) * 2) {
                change = 4;
            } else if (jumpBoost > ((int)speed / 3)) {
                change = 2;
            } else {
                change = 1;
            }

            jumpBoost -= change;
            y -= change;
        }

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

    public void setDeltaX(float newDeltaX) {
        this.deltaX = newDeltaX;
    }

    public float getDeltaX() {
        return deltaX;
    }

    public void setDeltaY(float newDeltaY) {
        this.deltaY = newDeltaY;
    }

    public float getDeltaY() {
        return deltaY;
    }

    public float getVelocity() {
        return velocity;
    }
}
