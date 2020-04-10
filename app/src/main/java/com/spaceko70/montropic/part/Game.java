package com.spaceko70.montropic.part;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Chronometer;

import com.spaceko70.montropic.contr.PlayerMoodController;
import com.spaceko70.montropic.EngineActivity;
import com.spaceko70.montropic.tools.DrawerAndPainter;
import com.spaceko70.montropic.models.OneBoom;
import com.spaceko70.montropic.models.FanstasticShip;
import com.spaceko70.montropic.models.Meteor;
import com.spaceko70.montropic.models.Boom2;
import com.spaceko70.montropic.models.Shield;
import com.spaceko70.montropic.R;
import com.spaceko70.montropic.setting.AudioConfiguration;
import com.spaceko70.montropic.setting.EndGame;
import com.spaceko70.montropic.setting.PointsHandler;
import com.spaceko70.montropic.setting.BoomsHandler;
import com.spaceko70.montropic.setting.TimeHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Game extends SurfaceView implements Runnable {

    EngineActivity engineActivity;
    Context context;

    private SharedPreferences highestScoreLoader;
    private SharedPreferences.Editor highestScoreWritter;
    private int highestScore;

    private volatile boolean playing;
    private volatile boolean gameEnded = false;
    private volatile static int numberOfShoots;

    Thread gameThread = null;

    private Random randomGenerator = new Random();

    private Boom2 boom2;
    private Meteor meteor;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private int screenWidth;
    private int screenHeight;

    private final int xLeftCorner = 0;
    private int framesPerSecond = 0;
    private long timeWhenStopped;
    private long levelTime;

    private static ArrayList<Boom2> listOfBoom2s = new ArrayList<>() ;
    private static ArrayList<OneBoom> enemyShip1LaserBlasts = new ArrayList<>();
    private static ArrayList<OneBoom> enemyShip2LaserBlasts = new ArrayList<>();
    private static ArrayList<OneBoom> enemyShip3LaserBlasts = new ArrayList<>();
    private static ArrayList<Shield> shields = new ArrayList<>();

    AudioConfiguration audioConfig;
    Chronometer chronometer;
    EndGame endGame;
    DrawerAndPainter drawerAndPainter = new DrawerAndPainter();
    PlayerMoodController controller = new PlayerMoodController();
    BoomsHandler boomsHandler = new BoomsHandler();
    PointsHandler pointsHandler = new PointsHandler();

    GameBtns gameBtns = new GameBtns();
    GameBoats gameBoats = new GameBoats();
    GameBooms gameBooms = new GameBooms();

    public Game(EngineActivity engineActivity, int screenWidth, int screenHeight)
    {
        super(engineActivity);
        this.engineActivity = engineActivity;
        this.context = engineActivity;

        //Getting Highest Score file and if it does not exist creating it
        highestScoreLoader = context.getSharedPreferences("HiScores", context.MODE_PRIVATE);
        highestScoreWritter = highestScoreLoader.edit();
        //Getting Highest Score and if its not there then result is 0
        highestScore = highestScoreLoader.getInt("highestScore", 0);

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        surfaceHolder = getHolder();
        paint = new Paint();

        startGame();
    }

    public void startGame()
    {
        pointsHandler.setPoints();
        boomsHandler.setShieldsLeft();

        gameBtns.createButtons(context, screenWidth, screenHeight);
        gameBoats.createShips(context, screenWidth, screenHeight);
        gameBooms.removeAllShields(shields);
        gameBooms.createShields(shields, context, screenWidth, screenHeight);

        boom2 = new Boom2(engineActivity, screenWidth, screenHeight, R.drawable.boom3);
        meteor = new Meteor(context, randomGenerator.nextInt(engineActivity.getScreenWidth() + 1), 0);
        listOfBoom2s = new ArrayList<>();
        enemyShip1LaserBlasts = new ArrayList<>();
        audioConfig = new AudioConfiguration(context);
        endGame = new EndGame(engineActivity);
        chronometer = new Chronometer(context);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        numberOfShoots = 0;
        timeWhenStopped = 0;
        levelTime = 10;
    }

    @Override
    public void run()
    {
        int frames = 0;
        long startTime = System.nanoTime();
        long currTime = 0;
        long lastTime = System.nanoTime();

        while (playing) {
            //Getting current time
            currTime = System.nanoTime();   //1  //2
            //here we calculate the difference between current time and last time
            //this helps to see how much time does it take to do update() method
            //this difference is used in update() methods
            //if difference is 2 then it will multiple for example the distance
            //that enemyShip has to move by 2. If the difference is 5, then it
            //will multiple the distance to move by 5.
            //it will mean that when the difference is 2 then it updates fast
            //therefore you dont need the enemyShip to move by that much, but
            //if the difference is 5, then it means t
            // hat it updates a bit
            //slow, therefore it means that you have to move the enemyShip by
            //more positions (x5 time more). See the code in enemyShip class
            //where I use deltaTime which is the difference between currentTime
            //and lastTime
            update((currTime - lastTime) / 1000000000.0f); //updates the game data
            //here we are setting lastTime
            lastTime = currTime; //1

            draw();

            generateEnemyLaserBlasts(enemyShip1LaserBlasts, gameBoats.getFanstasticShip1().getxCoordinate(), gameBoats.getFanstasticShip1().getyCoordinate());
            generateEnemyLaserBlasts(enemyShip2LaserBlasts, gameBoats.getFanstasticShip2().getxCoordinate(), gameBoats.getFanstasticShip2().getyCoordinate());
            generateEnemyLaserBlasts(enemyShip3LaserBlasts, gameBoats.getFanstasticShip3().getxCoordinate(), gameBoats.getFanstasticShip3().getyCoordinate());

            frames = frames + 1;
            if (System.nanoTime() - startTime > 100000000) {
                framesPerSecond = frames;
                frames = 0;
                startTime = System.nanoTime();
            }
        }
    }

    private void update(float deltaTime)
    {
        if (TimeHandler.TimeLeft(levelTime, chronometer.getBase()) == 0)
            gameEnded = true;

        decrementPointsScored();

        gameBoats.getGamerShip().update(deltaTime, screenHeight - gameBtns.getTargetButton().getButton().getHeight() - gameBoats.getGamerShip().getRawPlayerShip().getHeight());

        for (FanstasticShip fanstasticShip : gameBoats.getAllEnemyShips())
            fanstasticShip.update(deltaTime);

        meteor.update(deltaTime);

        //handles enemy ship laser blasts
        //checks if laser blasts are still within the screen
        //and if they leave the screen they are removed from arrayList
        //if they are still on screen they are updated

        for (int i = 0; i < enemyShip1LaserBlasts.size(); i++)
        {
            if (enemyShip1LaserBlasts.get(i).getCoordinateY() > engineActivity.getScreenHeight())
                enemyShip1LaserBlasts.remove(enemyShip1LaserBlasts.get(i));
            else
                enemyShip1LaserBlasts.get(i).update(deltaTime);
        }

        for (int i = 0; i < enemyShip2LaserBlasts.size(); i++)
        {
            if (enemyShip2LaserBlasts.get(i).getCoordinateY() > engineActivity.getScreenHeight())
                enemyShip2LaserBlasts.remove(enemyShip2LaserBlasts.get(i));
            else
                enemyShip2LaserBlasts.get(i).update(deltaTime);
        }

        for (int i = 0; i < enemyShip3LaserBlasts.size(); i++)
        {
            if (enemyShip3LaserBlasts.get(i).getCoordinateY() > engineActivity.getScreenHeight())
                enemyShip3LaserBlasts.remove(enemyShip3LaserBlasts.get(i));
            else
                enemyShip3LaserBlasts.get(i).update(deltaTime);
        }

        //ToDo: Uncomment when ready
        //enemyShipsLaserBlasts.clear();

        //checking if enemy ship and players ship collides with each other
        //if they collide then player looses 1 shield and 1 point
        //enemy ship is destroyed

        if (Rect.intersects(gameBoats.getFanstasticShip1().getEnemyShipRect(), gameBoats.getGamerShip().getHitBox()))
        {
            audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
            gameBoats.getFanstasticShip1().setyCoordinate(0);
            boomsHandler.decrementShieldsLeft();
            gameBooms.removeShield(shields);
            decrementPointsScored();

            if (!boomsHandler.areShieldsLeft())
                gameEnded = true;
        }

        if (Rect.intersects(gameBoats.getFanstasticShip2().getEnemyShipRect(), gameBoats.getGamerShip().getHitBox()))
        {
            audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
            gameBoats.getFanstasticShip2().setyCoordinate(0);
            boomsHandler.decrementShieldsLeft();
            gameBooms.removeShield(shields);
            decrementPointsScored();

            if (!boomsHandler.areShieldsLeft())
                gameEnded = true;
        }
        if (Rect.intersects(gameBoats.getFanstasticShip3().getEnemyShipRect(), gameBoats.getGamerShip().getHitBox()))
        {
            audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
            gameBoats.getFanstasticShip3().setyCoordinate(0);
            boomsHandler.decrementShieldsLeft();
            gameBooms.removeShield(shields);
            decrementPointsScored();

            if (!boomsHandler.areShieldsLeft())
                gameEnded = true;
        }


        //Check if Player Ship and Meteor collided
        if (controller.isMeteorPlayerShipCollided(meteor, gameBoats.getGamerShip()))
        {   //Handle consequences
            audioConfig.playExplosionSound();
            meteor.setYCoorinate();
            boomsHandler.decrementShieldsLeft();
            gameBooms.removeShield(shields);

            if (!boomsHandler.areShieldsLeft())
                gameEnded = true;
        }

        //checking if enemy ship laser blasts have hit the players space ship
        //if so then player looses 1 shield and 1 point
        for (int i = 0; i < enemyShip1LaserBlasts.size(); i++) {
            if (Rect.intersects(gameBoats.getGamerShip().getHitBox(), enemyShip1LaserBlasts.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getHitSound(), 1, 1, 0, 0, 1);
                enemyShip1LaserBlasts.get(i).setCoordinateY();
                decrementPointsScored();
                boomsHandler.decrementShieldsLeft();
                gameBooms.removeShield(shields);

                if (!boomsHandler.areShieldsLeft())
                    gameEnded = true;
            }
        }

        for (int i = 0; i < enemyShip2LaserBlasts.size(); i++) {
            if (Rect.intersects(gameBoats.getGamerShip().getHitBox(), enemyShip2LaserBlasts.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getHitSound(), 1, 1, 0, 0, 1);
                enemyShip2LaserBlasts.get(i).setCoordinateY();
                decrementPointsScored();
                boomsHandler.decrementShieldsLeft();
                gameBooms.removeShield(shields);

                if (!boomsHandler.areShieldsLeft())
                    gameEnded = true;
            }
        }

        for (int i = 0; i < enemyShip3LaserBlasts.size(); i++) {
            if (Rect.intersects(gameBoats.getGamerShip().getHitBox(), enemyShip3LaserBlasts.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getHitSound(), 1, 1, 0, 0, 1);
                enemyShip3LaserBlasts.get(i).setCoordinateY();
                decrementPointsScored();
                boomsHandler.decrementShieldsLeft();
                gameBooms.removeShield(shields);

                if (!boomsHandler.areShieldsLeft())
                    gameEnded = true;
            }
        }

        //checking if players laser blast has hit enemy's space ship
        //if so then player gets 1 point, and enemy ship is destroyed
        for (int i = 0; i < listOfBoom2s.size(); i++) {

            if (Rect.intersects(gameBoats.getFanstasticShip1().getEnemyShipRect(), listOfBoom2s.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
                listOfBoom2s.get(i).setCoordinateY();
                gameBoats.getFanstasticShip1().setyCoordinate(0);
                pointsHandler.incrementPoints();
            }

            if (Rect.intersects(gameBoats.getFanstasticShip2().getEnemyShipRect(), listOfBoom2s.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
                listOfBoom2s.get(i).setCoordinateY();
                gameBoats.getFanstasticShip2().setyCoordinate(0);
                pointsHandler.incrementPoints();
            }

            if (Rect.intersects(gameBoats.getFanstasticShip3().getEnemyShipRect(), listOfBoom2s.get(i).getRectLaser())) {
                audioConfig.getSoundPool().play(audioConfig.getExplosionSound(), 1, 1, 0, 0, 1);
                listOfBoom2s.get(i).setCoordinateY();
                gameBoats.getFanstasticShip3().setyCoordinate(0);
                pointsHandler.incrementPoints();
            }

            //if players space ship's laser blast is out of the screen
            //then remove it, otherwise - update it's position
            if (listOfBoom2s.get(i).getCoordinateY() < 0)
                listOfBoom2s.remove(listOfBoom2s.get(i));
            else
                listOfBoom2s.get(i).update(deltaTime);
        }
    }

    private void draw()
    {
        if (surfaceHolder.getSurface().isValid())
        {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));

            //Draw Ships
            drawerAndPainter.drawPlayerShip(paint, canvas, gameBoats.getGamerShip(), gameBtns.getTargetButton(), screenHeight);
            drawerAndPainter.drawEnemyShips(paint, canvas, gameBoats.getFanstasticShip1(), gameBoats.getFanstasticShip2(), gameBoats.getFanstasticShip3());

            //Draw Buttons
            drawerAndPainter.drawLeftButton(paint, canvas, gameBtns.getLBtn(), xLeftCorner, screenHeight);
            drawerAndPainter.drawRightButton(paint, canvas, gameBtns.getRBtn(), screenWidth, screenHeight);
            drawerAndPainter.drawTargetButton(paint, canvas, gameBtns.getTargetButton(), screenWidth, screenHeight);
            drawerAndPainter.drawStopButton(paint, canvas, gameBtns.getStopButton(), screenWidth);

            //Draw Shields
            drawerAndPainter.drawShields(paint, canvas, shields, screenHeight);

            //Draw Laser blasts
            drawerAndPainter. drawPlayerLaserBlasts(paint, canvas, listOfBoom2s);
            drawerAndPainter. drawEnemyShip1LaserBlasts(paint, canvas, enemyShip1LaserBlasts);
            drawerAndPainter.drawEnemyShip2LaserBlasts(paint, canvas, enemyShip2LaserBlasts);
            drawerAndPainter.drawEnemyShip3LaserBlasts(paint, canvas, enemyShip3LaserBlasts);

            //Draw meteor
            drawerAndPainter.drawMeteor(paint, canvas, meteor);

            //Information showed if game is still played or ended
            if (!gameEnded)
                endGame.gamePlay(paint, canvas, pointsHandler.getPoints(), TimeHandler.TimeLeft(levelTime, chronometer.getBase()), screenWidth );
            else {

                endGame.gameEnd(paint, canvas, pointsHandler.getPoints(), highestScore, highestScoreWritter, screenWidth, screenHeight);
                pause();

            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        playing = false;
        timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
        chronometer.stop();

        try {
            gameThread.join(10);
        } catch (InterruptedException e) {

        }
    }

    //Makes new thread and starts it
    public void resume() {
        playing = true;

        chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        chronometer.start();

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if (playing == false) {
            resume();
        }

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            //player touched the screen
            case MotionEvent.ACTION_DOWN:

                //when game is ended touch the screen and it starts again
                if (gameEnded)
                {
                    startGame();
                    gameEnded = false;
                }

                //x and y coordinates of touch
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();

                //checking if we have pressed LEFT button
                if ((x > xLeftCorner && x < xLeftCorner + gameBtns.getLBtn().getButton().getWidth()) &&
                        (y > screenHeight - gameBtns.getLBtn().getButton().getHeight() && y < screenHeight)) {
                    gameBoats.getGamerShip().goLeft();
                    //pause();
                }

                //checking if player clicked RIGHT button
                else if ((x <= screenWidth && x >= screenWidth - gameBtns.getRBtn().getButton().getWidth()) &&
                        (y <= screenHeight && y >= screenHeight - gameBtns.getRBtn().getButton().getHeight())) {
                    gameBoats.getGamerShip().goRight();

                    //resume();
                }

                //cheking if player clicked SHOOT button
                else if ((x <= screenWidth / 2 + gameBtns.getTargetButton().getButton().getWidth() / 2) &&
                        ((x >= screenWidth / 2 - (gameBtns.getTargetButton().getButton().getWidth() / 2)))
                        && (y <= screenHeight && (y >= screenHeight - gameBtns.getRBtn().getButton().getHeight()))) {
                    audioConfig.getSoundPool().play(audioConfig.getLaserBlastSound(), 1, 1, 0, 0, 1);
                    numberOfShoots++;
                    if (numberOfShoots > 150) {
                        gameEnded = true;
                    }
                    Log.i("Number of laser Shoots" + numberOfShoots, "+++++++++");
                    listOfBoom2s.add(new Boom2(engineActivity, (gameBoats.getGamerShip().getxCoordinate() + (gameBoats.getGamerShip().getBitmapWidth() / 2)) - (boom2.getLaser().getWidth() / 2)
                            , gameBoats.getGamerShip().getyCoordinate()
                            - gameBtns.getTargetButton().getButton().getHeight() - gameBoats.getGamerShip().getRawPlayerShip().getHeight(), R.drawable.boom3));

                } else if ((y > 0 && y < gameBtns.getStopButton().getButton().getHeight()) && (x < screenWidth && x >
                        screenWidth - gameBtns.getStopButton().getButton().getWidth())) {
                    if (playing == false) {
                        resume();
                    } else if (playing == true) {
                        pause();
                    }
                }
                //if player clicked somewhere else
                else {
                    //do nothing
                }

                break;

            //player not touching the screen anymore
            case MotionEvent.ACTION_UP:
                //when screen is stoped to be touched playersShip doesnt go rbtn anymore
                gameBoats.getGamerShip().standStill();
                break;
        }
        return true;
    }

    //Decrement points scored when enemy ship leaves screen
    private void decrementPointsScored()
    {
        if (gameBoats.getFanstasticShip1().getyCoordinate() > engineActivity.getScreenHeight())
            pointsHandler.decrementPoints();

        if (gameBoats.getFanstasticShip2().getyCoordinate() > engineActivity.getScreenHeight())
            pointsHandler.decrementPoints();

        if (gameBoats.getFanstasticShip3().getyCoordinate() > engineActivity.getScreenHeight())
            pointsHandler.decrementPoints();
    }

    //Generate random number to decide if enemy ship shoots
    private void generateEnemyLaserBlasts(List<OneBoom> listOfLaserBlasts, int x, int y)
    {
        int number = randomGenerator.nextInt(125);

        if (number == 1) {
            listOfLaserBlasts.add(new OneBoom(engineActivity, (x + gameBoats.getFanstasticShip1().getRawEnemyShip().getWidth() / 2) -
                    boom2.getLaser().getWidth() / 2,
                    y + gameBoats.getFanstasticShip1().getRawEnemyShip().getHeight() / 2, R.drawable.laser1));
        }
    }
}

