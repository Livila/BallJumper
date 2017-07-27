package jonathan.balljumper;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private GameSurfaceView gameSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove the title bar.
        getSupportActionBar().hide();

        // Make the game full screen.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initialize game view.
        //setContentView(R.layout.activity_main);
        gameSurfaceView = new GameSurfaceView(this);
        setContentView(gameSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameSurfaceView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameSurfaceView.pause();
    }
}
