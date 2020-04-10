package com.spaceko70.montropic.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.spaceko70.montropic.EngineActivity;

public class Shield
{
    protected int xCoordinate;
    protected int yCoordinate;

    protected Bitmap shield;

    protected EngineActivity engineActivity = new EngineActivity();

    public Shield(Context context, int xCoordinate, int yCoordinate, int drawableId)
    {
        Bitmap rawButton = BitmapFactory.decodeResource(context.getResources(), drawableId);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        //Counting 2% of all screen area
        int areaSize = (int)(engineActivity.getScreenArea()*(0.3f/100.0f));
        //Counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        shield = Bitmap.createScaledBitmap(rawButton, root, root, true);
    }

    public Bitmap getShield()
    {
        return shield;
    }

    public int getCoordinateX()
    {
        return xCoordinate;
    }

    public int getCoordinateY()
    {
        return yCoordinate;
    }
}
