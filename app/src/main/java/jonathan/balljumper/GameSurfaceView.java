package jonathan.balljumper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import jonathan.balljumper.classes.Ball;
import jonathan.balljumper.classes.Panel;

/**
 * Created by Jonathan on 27/07/2017.
 */

public class GameSurfaceView extends SurfaceView implements Runnable {
    private boolean isRunning = false;
    private Thread gameThread;
    private SurfaceHolder holder;

    private Point screenSize;

    private Ball ball;
    private Panel[] panelList;

    private final static int MAX_FPS = 40;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    public GameSurfaceView(Context context) {
        super(context);

        // Get and set window size.
        screenSize = new Point();
        ((Activity)getContext()).getWindowManager()
                                .getDefaultDisplay()
                                .getSize(screenSize);

        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                screenSize.x = width;
                screenSize.y = height;
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });

        // Initialize ball.
        int ballRadius = 35;
        ball = new Ball(screenSize.x / 2 - ballRadius / 2,
                       (screenSize.y / 3) * 2 - ballRadius,
                        ballRadius,
                        Color.argb(255, 200, 34, 34));

        // Initialize all panels.
        panelList = new Panel[10];
    }

    /**
     * Start or resume the game.
     */
    public void resume() {
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Pause the game loop
     */
    public void pause() {
        isRunning = false;
        boolean retry = true;
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // Try shutting down the thread again.
            }
        }
    }

    /**
     * Updates each frame.
     */
    protected void update() {
        // Game mechanics
        if (ball.getBottom() >= screenSize.y) {
            ball.bounce();
        } else if (ball.getTop() <= 0) {
            ball.bounce();
        }

        ball.setY(ball.getY() + ball.getSpeed() * ball.getDirection().x);
    }

    /**
     * Render everything to the screen.
     * @param canvas
     */
    protected void render(Canvas canvas) {
        // Reset background
        canvas.drawColor(Color.GREEN);

        // Render ball
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(ball.getColor(), 0);
        p.setColorFilter(filter);
        p.setColor(ball.getColor());
        canvas.drawCircle(ball.getX(), ball.getY(), ball.getRadius(), p);
    }

    @Override
    public void run() {
        while(isRunning) {
            // Make sure the surface is ready
            if (! holder.getSurface().isValid()) {
                continue;
            }
            long started = System.currentTimeMillis();

            // Update objects.
            update();

            // Render objects to screen.
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                render(canvas);
                holder.unlockCanvasAndPost(canvas);
            }

            // Make sure update, updates accordingly to the FPS.
            float deltaTime = (System.currentTimeMillis() - started);
            int sleepTime = (int) (FRAME_PERIOD - deltaTime);
            if (sleepTime > 0) {
                try {
                    gameThread.sleep(sleepTime / 2);
                }
                catch (InterruptedException e) {
                }
            }
            while (sleepTime < 0) {
                update();
                sleepTime += FRAME_PERIOD;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                ball.setX(x);
                break;
        }

        return true;
    }
}
