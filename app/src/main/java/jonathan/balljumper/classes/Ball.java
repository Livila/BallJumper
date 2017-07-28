package jonathan.balljumper.classes;

/**
 * Created by Jonathan on 27/07/2017.
 */

public class Ball extends Sprite {
    private int radius;

    public Ball(int x, int y, int radius, int color) {
        super(x, y, radius * 2, radius * 2);
        this.radius = radius;
        setColor(color);
        setSpeed(8);
    }

    public void bounce() {
        // Simple bounce.
        setDirectionY(getDirectionY() * -1);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getTop() {
        return getY() - getRadius();
    }

    public int getBottom() {
        return getY() + getRadius();
    }
}
