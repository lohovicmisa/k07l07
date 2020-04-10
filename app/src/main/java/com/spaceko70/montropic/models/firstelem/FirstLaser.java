package com.spaceko70.montropic.models.firstelem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.spaceko70.montropic.EngineActivity;

public abstract class FirstLaser
{
    protected int xCoordinate;
    protected int yCoordinate;

    protected Bitmap laser;
    protected Rect rectLaser;

    EngineActivity engineActivity;

    public FirstLaser(EngineActivity engineActivity, int xCoordinate, int yCoordinate, int drawableId)
    {
        this.engineActivity = engineActivity;
        Bitmap rawLaser = BitmapFactory.decodeResource(engineActivity.getResources(), drawableId);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        //counting 3% of all screen area
        int areaSize = (int)(engineActivity.getScreenArea()*(0.5f/100.0f));
        //counting root of areaSize variable
        float root = (float) Math.sqrt(areaSize);

        laser = Bitmap.createScaledBitmap(rawLaser,(int) root, (int) root, true);
        rectLaser = new Rect(xCoordinate, yCoordinate, laser.getWidth(), laser.getHeight());
    }

    public Bitmap getLaser()
    {
        return laser;
    }

    public int getCoordinateX()
    {
        return xCoordinate;
    }

    public int getCoordinateY()
    {
        return yCoordinate;
    }

    public Rect getRectLaser()
    {
        return rectLaser;
    }

    public abstract void setCoordinateY();
    public abstract void update(float deltaTime);

}
