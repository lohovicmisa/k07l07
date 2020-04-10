package com.spaceko70.montropic.contr;

import android.graphics.Rect;

import com.spaceko70.montropic.models.Meteor;
import com.spaceko70.montropic.models.GamerShip;

public class PlayerMoodController
{
    private volatile static int shieldsLeft;

    //ToDo more abstract method which fits all Objects
    public boolean isMeteorPlayerShipCollided(Meteor meteor, GamerShip ship)
    {
        if(Rect.intersects(meteor.getHitBox(), ship.getHitBox()))
            return true;
        else
            return false;
    }
}
