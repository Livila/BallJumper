package jonathan.balljumper.classes;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

/**
 * Created by Jonathan on 27/07/2017.
 */

public class Ball extends Sprite {
    private final int animationSizeMax = 6;

    private final SoundController soundController;
    private final Paint paint;

    private float radius;
    private float velocity, startVelocity;
    private float gravity;
    private float deltaX, deltaY;
    private int jumpBoost;

    private boolean isAnimationRunning;
    private float animationSize;

    public Ball(float x, float y, float radius, float velocity, float gravity, float speed, int color, SoundController soundController) {
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

        this.soundController = soundController;
    }

    public final void draw(Canvas canvas) {
        canvas.drawOval(
                getLeft() - animationSize / 2,
                getTop() + animationSize * 2,
                getRight() + animationSize / 2,
                getBottom() - animationSize,
                paint
        );
    }

    /**
     * Bounce the ball.
     */
    public void bounce() {
        if (jumpBoost < 0) {
            jumpBoost = (int)speed; // Give the ball a boost.
            velocity = this.startVelocity; // Reset velocity
        }

        isAnimationRunning = true;
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

        if (isAnimationRunning) {
            animationSize += velocity * 2;
            if (animationSize >= animationSizeMax) {
                isAnimationRunning = false;
                soundController.playBounce();
            }
        } else if (animationSize > 0) {
            animationSize -= velocity * 2;
            if (animationSize < 0) animationSize = 0;
        }

    }

    public final boolean intersects(float x, float y, float w, float h) {
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
    public final float getBottom() {
        return y + radius;
    }

    @Override
    public final float getLeft() {
        return x - radius;
    }

    @Override
    public final float getRight() {
        return x + radius;
    }

    public void setDeltaX(float newDeltaX) {
        this.deltaX = newDeltaX;
    }

    public final float getDeltaX() {
        return deltaX;
    }

    public void setDeltaY(float newDeltaY) {
        this.deltaY = newDeltaY;
    }

    public final float getDeltaY() {
        return deltaY;
    }

    public final float getVelocity() {
        return velocity;
    }
}
