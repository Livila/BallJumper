package jonathan.balljumper.classes;

/**
 * Created by Jonathan on 24/07/2017.
 */

public class Panel extends Sprite {
    public Panel(float x, float y, float width, float height, int color) {
        super(x, y, width, height, color);
        setSpeed(5f);
    }
}
