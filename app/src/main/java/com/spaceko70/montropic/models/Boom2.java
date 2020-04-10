package com.spaceko70.montropic.models;


import com.spaceko70.montropic.EngineActivity;
import com.spaceko70.montropic.models.firstelem.FirstLaser;


public class Boom2 extends FirstLaser
{
    public Boom2(EngineActivity engineActivity, int xCoordinate, int yCoordinate, int drawableId)
    {
        super(engineActivity, xCoordinate, yCoordinate, drawableId);
    }

    public void setCoordinateY()
    {
        yCoordinate = -10;
    }

    public void update(float deltaTime)
    {
        yCoordinate -= 800 * deltaTime;

        // Refresh laser location
        rectLaser.left = xCoordinate;
        rectLaser.top = yCoordinate;
        rectLaser.right = xCoordinate + laser.getWidth();
        rectLaser.bottom = yCoordinate + laser.getHeight();
    }
}
