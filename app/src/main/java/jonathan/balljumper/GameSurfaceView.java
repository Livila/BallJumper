package jonathan.balljumper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import jonathan.balljumper.classes.Ball;
import jonathan.balljumper.classes.HighscoreHandler;
import jonathan.balljumper.classes.Panel;
import jonathan.balljumper.classes.PanelHandler;
import jonathan.balljumper.classes.SoundController;
import jonathan.balljumper.enums.GameState;

/**
 * Created by Jonathan on 27/07/2017.
 */

public class GameSurfaceView extends SurfaceView implements Runnable {
    private boolean isRunning = false;
    private Thread gameThread;
    private final SurfaceHolder holder;
    private GameState gameState;

    private final static Point screenSize = new Point();
    public static Point getScreenSize() { return screenSize; }

    private boolean isTouchDown = false;
    private long touchStartTime;
    private float touchMoveDeltaX, touchMoveDeltaY;

    private final Ball ball;
    private final PanelHandler panelHandler;
    private final HighscoreHandler highscoreHandler;
    private final SoundController soundController;

    private final static int MAX_FPS = 40;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    public GameSurfaceView(Context context) {
        super(context);

        // Get and set window size.
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

        this.soundController = new SoundController(context);

        // Initialize ball.
        int ballRadius = 35;

        ball = new Ball(screenSize.x / 2 - ballRadius,
                        (screenSize.y / 3) * 2 - ballRadius,
                        ballRadius,
                        0.2f, // Velocity
                        0.5f, // Gravity
                        17f, // Speed
                        Color.argb(255, 200, 34, 34)
        );

        // Initialize panelHandler.
        panelHandler = new PanelHandler(10, 150, 20, 5f);

        highscoreHandler = new HighscoreHandler();

        gameState = GameState.Highscore;
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
        highscoreHandler.addHeight(panelHandler.getPanelSpeed());
        highscoreHandler.addScore(1);

        // Check if the ball is going too high, if so move the screen with the ball.
        if (ball.getVelocity() < ball.getSpeed() && ball.getTop() < (screenSize.y / 3) * 2) {
            // Move the ball first to set the new velocity and to get the delta values.
            // Reset the ball's Y position.
            ballHeightLimit = (ball.getDeltaY() * (1f / (Math.max((ball.getY() - 150), 100) / 100)));
            ball.setY(ball.getY() - ballHeightLimit);
            ball.setDeltaY(ball.getDeltaY() - ballHeightLimit);

            // If you fly up faster, add the extra height.
            highscoreHandler.addHeight(-ballHeightLimit);

            // Give normal score and some extra score for speeding up the game.
            // -ballHeightLimit: The normal score added for gaining height.
            highscoreHandler.addScore((-ballHeightLimit * 1.25f) / panelHandler.getPanelSpeed());
        }

        for (int i = 0; i < panelHandler.getPanelList().length; ++i) {
            Panel panel = panelHandler.getPanelList()[i];

            // Move the panel at normal speed and reset it if it comes below the screen.
            panelHandler.move(i);

            // When the ball moves upwards too much, move with the ball.
            if (ballHeightLimit != -1) {
                panelHandler.move(i, -ballHeightLimit);
            }

            // Don't jump if you recently jumped.
            if (!ball.getHasJumped()) {
                // Bounce if the ball intersects with a panel.
                if (ball.intersects(panel.getX(), panel.getY(), panel.getWidth(), panel.getHeight())) {
                    // Do not bounce if you're above the screen.
                    if (ball.getBottom() > 0) {
                        ball.bounce();
                        soundController.playBounce();
                        highscoreHandler.addBounce();

                        // If the ball is falling. TODO
                        if (ball.getDeltaY() > 0) {
                            ball.setY(ball.getY() - (ball.getBottom() - panel.getTop()));
                        }
                    }
                }
            }
        }

        highscoreHandler.update();
    }

    /**
     * Render everything to the screen.
     * @param canvas Canvas to be used when rendering.
     */
    protected final void render(Canvas canvas) {
        // Reset background
        canvas.drawColor(Color.GREEN);

        switch (gameState) {
            case Running:
                // Render panels
                panelHandler.draw(canvas);

                // Render ball
                ball.draw(canvas);

                // Render score in-game.
                highscoreHandler.drawScoreInGame(canvas);
                break;
            case GameOver:
                // Render the panels and the ball in the background.
                panelHandler.draw(canvas);
                ball.draw(canvas);
                // Render the final score.
                highscoreHandler.drawScoreFinal(canvas);
                break;
            case Highscore:
                highscoreHandler.drawHighscore(canvas);
                break;
        }
    }

    @Override
    public void run() {
        while(isRunning) {
            // Make sure the surface is ready.
            if (! holder.getSurface().isValid()) {
                continue;
            }
            long started = System.currentTimeMillis();

            // Update all objects when running the game.
            if (gameState == GameState.Running) {
                update();
            }

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
                if (gameState == GameState.Running) {
                    update();
                }
                sleepTime += FRAME_PERIOD;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();

        switch (gameState) {
            case Running:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:

                        // Calculate delta x and y.
                        touchMoveDeltaX = event.getX() - touchMoveDeltaX;
                        touchMoveDeltaY = event.getY() - touchMoveDeltaY;

                        // Skip first time you press down.
                        if (!isTouchDown) {
                            isTouchDown = true;
                        } else {
                            // Correct delta here.

                            // Move the ball as you move the finger.
                            ball.setX(ball.getX() + touchMoveDeltaX * 1.5f);
                        }

                        touchMoveDeltaX = event.getX();
                        touchMoveDeltaY = event.getY();

                        break;
                    case MotionEvent.ACTION_DOWN:
                        touchStartTime = System.currentTimeMillis();
                        break;

                    case MotionEvent.ACTION_UP:
                        // If you quickly press with your finger, move the ball to that position.
                        if ((System.currentTimeMillis() - touchStartTime) < 125) {
                            ball.setX(event.getX());
                        }
                        touchStartTime = 0;
                        isTouchDown = false;
                        break;
                }
                break;
            case GameOver:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    gameState = GameState.Highscore;
                    highscoreHandler.highscoreUpdate();
                }
                break;
            case Highscore:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    gameState = GameState.Running;
                    resetGame();
                    ball.setX(x); // Move ball to where your finger points.
                }
                break;
        }

        return true;
    }

    public void lose() {
        gameState = GameState.GameOver; // Show the highscore screen.
    }

    public void resetGame() {
        highscoreHandler.saveThenReset();

        ball.bounce();
        ball.setX(screenSize.x / 2 - ball.getWidth() / 2);
        ball.setY((screenSize.y / 3) * 2 - ball.getWidth() / 2);
        ball.setDeltaX(0);
        ball.setDeltaY(0);

        panelHandler.resetAllPanels();
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
     * Pause the game loop.
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
}
