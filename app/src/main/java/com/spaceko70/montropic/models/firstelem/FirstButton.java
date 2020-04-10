package com.spaceko70.montropic.models.firstelem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.spaceko70.montropic.EngineActivity;

public abstract class FirstButton
{
    protected int xCoordinate;
    protected int yCoordinate;

    protected Bitmap button;

    protected EngineActivity engineActivity = new EngineActivity();

    public FirstButton(Context context, int xCoordinate, int yCoordinate, int drawableId)
    {
        Bitmap rawButton = BitmapFactory.decodeResource(context.getResources(), drawableId);
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        //counting 3% of all screen area
        int areaSize = (int)(engineActivity.getScreenArea()*(3.0f/100.0f));
        //counting root of areaSize variable
        int root = (int) Math.sqrt(areaSize);

        button = Bitmap.createScaledBitmap(rawButton, root, root, true);
    }

    public Bitmap getButton()
    {
        return button;
    }
}
