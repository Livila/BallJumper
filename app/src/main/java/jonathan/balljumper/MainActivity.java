package jonathan.balljumper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jonathan.balljumper.engine.GameEngine;
import jonathan.balljumper.views.PlayerView;

public class MainActivity extends AppCompatActivity {

    private GameEngine gameEngine;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameEngine = new GameEngine();
        playerView = (PlayerView)findViewById(R.id.playerView);
        playerView.invalidate();
    }
}
