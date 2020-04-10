package com.spaceko70.montropic.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.spaceko70.montropic.EngineActivity;
import com.spaceko70.montropic.R;


public class GamerShip
{
    private Bitmap rawPlayerShip;
    private Bitmap playerShip;

    private int xCoordinate;
    private int yCoordinate;
    private int speed = 0;

    //stop playerShip from leaving the screen
    private int maxX;
    private int minX = 0;

    private boolean goingRight;
    private boolean goingLeft;

    private Rect hitBox;

    EngineActivity engineActivity = new EngineActivity();

    public GamerShip(Context context, int xCoordinate, int yCoordinate)
    {
        speed = 1;
        rawPlayerShip = BitmapFactory.decodeResource(context.getResources(), R.drawable.plaer);

        //counting 3% of all screen area
        int areaSize = (int)(engineActivity.getScreenArea()*(2.5f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        playerShip = Bitmap.createScaledBitmap(rawPlayerShip, root, root, true);

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        hitBox = new Rect(getxCoordinate(), getyCoordinate(), playerShip.getWidth() , playerShip.getHeight());
    }

    //incrementing ships yCoordinate by 1 every time the method is called
    public void update(float deltaTime, int newY)
    {
        //yCoordinate--;
        if (goingRight)
        {
            //playerShip goes rbtn
            xCoordinate += 1 * (engineActivity.getScreenWidth() / 2) *deltaTime;
        }

        else if (goingLeft){
            //playerShip goes back to rbtn
            xCoordinate -= 1 * (engineActivity.getScreenWidth() / 2) *deltaTime;
        }

        else {
            //do nothing
        }

        // Refresh hit box location
        hitBox.left = getxCoordinate();
        hitBox.top =  newY;
        hitBox.right = getxCoordinate() + playerShip.getWidth();
        hitBox.bottom = newY + playerShip.getHeight();

        if  (xCoordinate >getMaxX() )
        {
            xCoordinate = getMaxX();
        }

        if (xCoordinate < minX)
        {
            xCoordinate = minX;
        }
    }

    public Bitmap getRawPlayerShip()
    {
        return playerShip;
    }

    public int getBitmapWidth()
    {
        return playerShip.getWidth();
    }

    public int getMaxX()
    {
        maxX = engineActivity.getScreenWidth()  - playerShip.getWidth();

        return maxX;
    }

    public int getSpeed()
    {
        return speed;
    }

    public int getxCoordinate()
    {
        return xCoordinate;
    }

    public int getyCoordinate()
    {
        return yCoordinate;
    }

    //move ship to rbtn
    public void goRight()
    {
        goingRight = true;
        goingLeft = false;
    }

    //move ship to lbtn
    public void goLeft()
    {
        goingLeft = true;
        goingRight = false;
    }

    public void standStill()
    {
        goingRight = false;
        goingLeft = false;
    }

    public Rect getHitBox()
    {
        return hitBox;
    }
}

