package jonathan.balljumper.classes;

import android.graphics.Paint;

/**
 * Created by Jonathan on 24/07/2017.
 */

public class IObject {
    private int x;
    private int y;
    private int width;
    private int height;
    private Paint paint;

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

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}
