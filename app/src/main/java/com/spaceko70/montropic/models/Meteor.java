package com.spaceko70.montropic.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.spaceko70.montropic.EngineActivity;
import com.spaceko70.montropic.R;

import java.util.Random;


public class Meteor
{
    private Bitmap rawMeteor;
    private Bitmap meteor;

    private int xCoordinate;
    private int yCoordinate;

    private Rect hitBox;

    EngineActivity engineActivity = new EngineActivity();

    Random generator = new Random();

    public Meteor(Context context, int xCoordinate, int yCoordinate)
    {
        rawMeteor = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_boom);
        //counting 3% of all screen area
        int areaSize = (int)(engineActivity.getScreenArea()*(0.5f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        meteor = Bitmap.createScaledBitmap(rawMeteor, root, root, true);

        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        hitBox = new Rect(getXCoordinate(), getYCoordinate(), meteor.getWidth(), meteor.getHeight());
    }

    public void update(float deltaTime)
    {
        if (yCoordinate > engineActivity.getScreenHeight())
        {
            //makes meteor appear only within screen
            xCoordinate = generator.nextInt(engineActivity.getScreenWidth() - getRawMeteor().getWidth());
            yCoordinate = 0;
        } else {
            yCoordinate += 3 * (engineActivity.getScreenHeight() / 15) * deltaTime;
        }

        // Refresh hit box location
        hitBox.left = getXCoordinate();
        hitBox.top =  getYCoordinate();
        hitBox.right = getXCoordinate() + meteor.getWidth();
        hitBox.bottom = getYCoordinate() + meteor.getHeight();
    }

    public Bitmap getRawMeteor()
    {
        return meteor;
    }

    public int getXCoordinate()
    {
        return xCoordinate;
    }

    public int getYCoordinate()
    {
        return yCoordinate;
    }

    public void setYCoorinate()
    {
        yCoordinate = -10;
    }

    public Rect getHitBox()
    {
        return hitBox;
    }
}
