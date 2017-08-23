package jonathan.balljumper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import jonathan.balljumper.classes.Ball;
import jonathan.balljumper.classes.Panel;
import jonathan.balljumper.classes.PanelHandler;

/**
 * Created by Jonathan on 27/07/2017.
 */

public class GameSurfaceView extends SurfaceView implements Runnable {
    private boolean isRunning = false;
    private Thread gameThread;
    private SurfaceHolder holder;

    private Point screenSize;

    private Ball ball;
    private PanelHandler panelHandler;

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
        ball = new Ball(screenSize.x / 2 - ballRadius,
                       (screenSize.y / 3) * 2 - ballRadius,
                        ballRadius,
                        0.2f, // Velocity
                        0.5f, // Gravity
                        17f, // Speed
                        Color.argb(255, 200, 34, 34));

        // Initialize panelHandler.
        panelHandler = new PanelHandler(10, 150, 20, 5f, screenSize);
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

        // Delta value. If this is set, then move the screen accordingly.
        float ballHeightLimit = -1;

        // If the ball goes below the screen, you lose.
        if (ball.getBottom() >= screenSize.y) {
            lose();
        }

        ball.move();

        // Check if the ball is going too high, if so move the screen with the ball.
        if (ball.getVelocity() < ball.getSpeed() && ball.getTop() < 1000) {
            // Move the ball first to set the new velocity and to get the delta values.
            // Reset the ball's Y position.
            ballHeightLimit = (ball.getDeltaY() * (1f / ((ball.getY() - 150) / 100)));
            ball.setY(ball.getY() - ballHeightLimit);
            ball.setDeltaY(ball.getDeltaY() - ballHeightLimit);
        }

        for (int i = 0; i < panelHandler.getPanelList().length; ++i) {
            Panel panel = panelHandler.getPanelList()[i];

            // Move the panel at normal speed and reset it if it comes below the screen.
            panelHandler.move(i);

            // Bounce if the ball intersects with a panel.
            if (ball.intersects(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight())) {
                // Do not bounce if you're above the screen.
                if (ball.getBottom() > 0) {
                    ball.bounce();
                }
            }

            // When the ball moves upwards too much, move panels down by the same speed.
            if (ballHeightLimit != -1) {
                panelHandler.move(i, -ballHeightLimit);
            }
        }
    }

    /**
     * Render everything to the screen.
     * @param canvas Canvas to be used when rendering.
     */
    protected void render(Canvas canvas) {
        // Reset background
        canvas.drawColor(Color.GREEN);

        // Render ball
        ball.draw(canvas);

        // Render panels
        panelHandler.draw(canvas);
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
            int sleepTime = (int) ((FRAME_PERIOD - deltaTime) / 2);
            if (sleepTime > 0) {
                try {
                    gameThread.sleep(sleepTime);
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

    public void lose() {
        isRunning = false;

        try {
            gameThread.sleep(1000);
        } catch (InterruptedException e) { }

        ball.bounce();
        ball.setX(screenSize.x / 2 - ball.getWidth() / 2);
        ball.setY((screenSize.y / 3) * 2 - ball.getWidth() / 2);
        isRunning = true;
    }
}
