package jonathan.balljumper.engine;

import android.graphics.Color;

import java.util.ArrayList;

import jonathan.balljumper.classes.Panel;

/**
 * Created by Jonathan on 24/07/2017.
 */

public class GameEngine {
    public static final int GameWidth = 28;
    public static final int GameHeight = 42;

    private ArrayList<Panel> panelList = new ArrayList<>();

    public GameEngine() {
        panelList.add(new Panel(50, 50, 100, 30, Color.BLUE));


    }
}
