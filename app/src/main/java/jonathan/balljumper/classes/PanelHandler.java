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

    private Point screenSize;
    private Random random;

    private Panel[] panelList;
    private int lastPanelIndex = -1;

    public PanelHandler(int panelCount, int panelWidth, int panelHeight, float panelSpeed, Point screenSize) {
        this.screenSize = screenSize;

        random = new Random();

        // Initialize all panels.
        panelList = new Panel[panelCount];

        Random random = new Random();

        for (int i = 0; i < panelList.length; ++i) {
            panelList[i] = new Panel(
                    random.nextInt(screenSize.x - panelWidth),
                    getNewPanelHeight(lastPanelIndex),
                    panelWidth,
                    panelHeight,
                    Color.GRAY,
                    panelSpeed
            );
            lastPanelIndex = i;
        }
    }

    private void resetPanel(int index) {
        panelList[index].setY(getNewPanelHeight(lastPanelIndex));

        lastPanelIndex = index;
    }

    private float getNewPanelHeight(int index) {
        float newPanelHeight;
        if (index == -1) {
            newPanelHeight = panelFirstPosition;
        } else {
            float diff = (random.nextInt(screenSize.y / 5) + 250);
            newPanelHeight = panelList[index].getY() - diff;
        }

        return newPanelHeight;
    }

    public void resetAllPanels() {
        lastPanelIndex = -1;
        for (int i = 0; i < panelList.length; ++i) {
            panelList[i].setX(screenSize.x - panelList[i].getWidth());
            panelList[i].setY(getNewPanelHeight(lastPanelIndex));
        }
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

    /**
     * Move the panel.
     * @param index The panel index to move.
     */
    public void move(int index) {
        move(index, panelList[index].getSpeed());
    }

    /**
     *
     * @param index The panel index to move.
     * @param speed The speed to move the panel.
     */
    public void move(int index, float speed) {
        // If a panel is outside of the screen reset it at the top.
        if (panelList[index].getY() + speed > screenSize.y) {
            resetPanel(index);
        } else {
            // Move the panel down.
            panelList[index].setY(panelList[index].getY() + speed);
        }
    }
}
