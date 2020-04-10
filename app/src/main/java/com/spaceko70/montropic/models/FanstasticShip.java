package com.spaceko70.montropic.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.spaceko70.montropic.EngineActivity;
import com.spaceko70.montropic.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FanstasticShip
{
    EngineActivity engineActivity = new EngineActivity();

    private Bitmap rawEnemyShip;
    private Bitmap enemyShip;

    private int xCoordinate;
    private int yCoordinate;

    private Rect enemyShipRect;

    private ArrayList<OneBoom> laserBlasts = new ArrayList<>();

    Random generator = new Random();

    public FanstasticShip(Context context) {

        int whichBitmap = generator.nextInt(3);
        switch (whichBitmap) {
            case 0:
                rawEnemyShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship11);
                break;
            case 1:
                rawEnemyShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship22);
                break;
            case 2:
                rawEnemyShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship33);
                break;
        }

        //counting 2,5% of all screen area
        int areaSize = (int) (engineActivity.getScreenArea() * (2.5f / 100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        //putting enemy ship in a random X spot
        enemyShip = Bitmap.createScaledBitmap(rawEnemyShip, root, root, true);

        xCoordinate = generator.nextInt(engineActivity.getScreenWidth() - getRawEnemyShip().getWidth());

        // Initialize the hit box
        enemyShipRect = new Rect(getxCoordinate(), (int) getyCoordinate(), enemyShip.getWidth(), enemyShip.getHeight());
    }

    //enemy ship is going down
    //when enemy ship leaves screen it
    //respawns at 0 position
    public void update(float deltaTime) {
        if (yCoordinate > engineActivity.getScreenHeight()) //if enemy ship lbtn screen
        {
            Random placeGenerator = new Random();
            //putting respawned enemy ship in a random X spot
            xCoordinate = placeGenerator.nextInt(engineActivity.getScreenWidth() - getRawEnemyShip().getWidth());
            yCoordinate = 0;

        } else {
            //here i use deltaTime
            //the longer it takes to do the updates, the more
            // i will multiple the distance the enemyShip has
            //to move
            //see the code in gameView class
            yCoordinate += 3 * (engineActivity.getScreenHeight() / 14) * deltaTime;
        }

        // Refresh enemyShipRect location
        enemyShipRect.left = getxCoordinate();
        enemyShipRect.top = getyCoordinate();
        enemyShipRect.right = getxCoordinate() + enemyShip.getWidth();
        enemyShipRect.bottom = (getyCoordinate() + enemyShip.getHeight());
    }

    public Bitmap getRawEnemyShip() {
        return enemyShip;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public List<OneBoom> getAllLaserBlasts()
    {
        return laserBlasts;
    }

    public void addLaserBlast(OneBoom laserBlast)
    {
        laserBlasts.add(laserBlast);
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Rect getEnemyShipRect() {
        return enemyShipRect;
    }

    //method is called when enemy ship is destroyed or leaves screen
    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
        Random placeGenerator = new Random();

        xCoordinate = placeGenerator.nextInt(engineActivity.getScreenWidth() - getRawEnemyShip().getWidth());
    }
}


