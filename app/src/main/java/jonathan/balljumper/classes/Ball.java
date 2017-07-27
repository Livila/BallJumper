package jonathan.balljumper.classes;

/**
 * Created by Jonathan on 27/07/2017.
 */

public class Ball extends Sprite {
    private float radius;

    public Ball(int x, int y, float radius, int color) {
        super(x, y);
        this.radius = radius;
        setColor(color);
    }

    public void bounce() {
        // Simple bounce.
        setY(getY() * -1);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
