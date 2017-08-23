package jonathan.balljumper.classes;

import android.graphics.Bitmap;

/**
 * Created by Jonathan on 27/07/2017.
 */

public class Sprite {
    protected float x, y;
    protected float width, height;
    protected float speed = 0;
    protected int color = 0;
    protected Bitmap image;

    public Sprite(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Sprite(float x, float y, float width, float height) {
        this(x, y);
        this.width = width;
        this.height = height;
    }

    public Sprite(float x, float y, float width, float height, int color) {
        this(x, y, width, height);
        this.color = color;
    }

    public Sprite(float x, float y, Bitmap image) {
        this(x, y, image.getWidth(), image.getHeight());
        this.image = image;
    }

    public Sprite(float x, float y, Bitmap image, int color) {
        this(x, y, image);
        this.color = color;
    }

    public final float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public final float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public final float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public final float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getTop() {
        return y;
    }

    public float getBottom() {
        return y + height;
    }

    public float getLeft() {
        return x;
    }

    public float getRight() {
        return x + width;
    }

    public final float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public final int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public final Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
