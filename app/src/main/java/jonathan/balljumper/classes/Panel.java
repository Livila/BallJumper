package jonathan.balljumper.classes;

import android.graphics.Paint;

/**
 * Created by Jonathan on 24/07/2017.
 */

public class Panel extends IObject {
    public Panel(int x, int y, int width, int height, int color) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        Paint paint = new Paint();
        paint.setColor(color);
        setPaint(paint);
    }
}
