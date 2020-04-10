package com.spaceko70.montropic.models;


import com.spaceko70.montropic.EngineActivity;
import com.spaceko70.montropic.models.firstelem.FirstLaser;


public class OneBoom extends FirstLaser
{
    public OneBoom(EngineActivity engineActivity, int xCoordinate, int yCoordinate, int drawableId) {
        super(engineActivity, xCoordinate, yCoordinate, drawableId);
    }

    public void setCoordinateY() {
        yCoordinate = 5000;
    }

    public void update(float deltaTime) {
        yCoordinate += 600 * deltaTime;

        // Refresh hit box location
        rectLaser.left = xCoordinate;
        rectLaser.top = yCoordinate;
        rectLaser.right = xCoordinate + laser.getWidth();
        rectLaser.bottom = yCoordinate + laser.getHeight();
    }
}
