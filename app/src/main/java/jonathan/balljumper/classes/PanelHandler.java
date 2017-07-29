package jonathan.balljumper.classes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

/**
 * Created by Jonathan on 29/07/2017.
 */

public class PanelHandler {
    private final int panelFirstPosition = 1500; // Y-position (pixel) of the first panel.

    private int panelCount;
    private Point screenSize;
    private Random random;

    private Panel[] panelList;
    private int lastPanelIndex = -1;

    public PanelHandler(int panelCount, Point screenSize) {
        this.panelCount = panelCount;
        this.screenSize = screenSize;

        random = new Random();

        // Initialize all panels.
        panelList = new Panel[panelCount];

        Random random = new Random();

        int panelWidth = 150;
        for (int i = 0; i < panelList.length; ++i) {
            panelList[i] = new Panel(
                    random.nextInt(screenSize.x - panelWidth),
                    getNewPanelHeight(lastPanelIndex),
                    panelWidth,
                    30,
                    Color.GRAY);
            lastPanelIndex = i;
        }
    }

    public void resetPanel(int index) {
        panelList[index].setY(getNewPanelHeight(lastPanelIndex));

        lastPanelIndex = index;
    }

    private float getNewPanelHeight(int index) {
        float newPanelHeight;
        if (index == -1) {
            newPanelHeight = panelFirstPosition;
        } else {
            newPanelHeight = panelList[index].getY() - (random.nextInt(screenSize.y / 5) + 250);
        }

        return newPanelHeight;
    }

    public Panel[] getPanelList() {
        return panelList;
    }

    public void draw(Canvas canvas) {
        for (Panel panel : panelList) {
            Paint pPanel = new Paint();
            pPanel.setColor(panel.getColor());
            canvas.drawRect(panel.getX(), panel.getY(), panel.getWidth() + panel.getX(), panel.getHeight() + panel.getY(), pPanel);
        }
    }
}
