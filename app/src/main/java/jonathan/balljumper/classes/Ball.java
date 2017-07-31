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
    private float velocity;
    private float gravity;

    public Ball(float x, float y, float radius, float velocity, float gravity, int color) {
        super(x, y, radius * 2, radius * 2);
        this.radius = radius;
        this.gravity = gravity;
        this.velocity = velocity;
        setColor(color);
        setSpeed(17f);
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(getColor(), 0);
        p.setColorFilter(filter);
        p.setColor(getColor());
        canvas.drawCircle(getX(), getY(), getRadius(), p);
    }

    /**
     * Bounce the ball.
     */
    public void bounce() {
        // Simple bounce.
        y-=speed; // Give it some speed up, so it doesn't get stuck.
        velocity = -.3f; // Reset velocity
        getDirection().y *= -1; // Change ball direction.
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

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
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
