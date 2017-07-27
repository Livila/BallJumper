package jonathan.balljumper.classes;

import android.graphics.Bitmap;

/**
 * Created by Jonathan on 27/07/2017.
 */

public class Sprite {
    protected int x, y, width, height;
    protected int directionX = 1;
    protected int directionY = 1;
    protected int speed = 10;
    protected int color = 0;
    protected Bitmap image;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Sprite(int x, int y, int width, int height) {
        this(x, y);
        this.width = width;
        this.height = height;
    }

    public Sprite(int x, int y, int width, int height, int color) {
        this(x, y, width, height);
        this.color = color;
    }

    public Sprite(int x, int y, Bitmap image) {
        this(x, y);
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public Sprite(int x, int y, Bitmap image, int color) {
        this(x, y, image);
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDirectionX() {
        return directionX;
    }

    public void setDirectionX(int directionX) {
        this.directionX = directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
