package com.spaceko70.montropic.part;

import android.content.Context;

import com.spaceko70.montropic.models.FanstasticShip;
import com.spaceko70.montropic.models.GamerShip;

import java.util.ArrayList;
import java.util.Arrays;

public class GameBoats
{
    private GamerShip gamerShip;
    private FanstasticShip fanstasticShip1;
    private FanstasticShip fanstasticShip2;
    private FanstasticShip fanstasticShip3;
    private ArrayList<FanstasticShip> fanstasticShips = new ArrayList<>();


    public void createShips(Context context, int screenWidth, int screenHeight)
    {
        createPlayerShip(context, screenWidth, screenHeight);
        createEnemyShips(context);
    }

    private void createPlayerShip(Context context, int screenWidth, int screenHeight)
    {
        gamerShip = new GamerShip(context, screenWidth/2 , screenHeight);
    }

    private void createEnemyShips(Context context)
    {
        fanstasticShip1 = new FanstasticShip(context);
        fanstasticShip2 = new FanstasticShip(context);
        fanstasticShip3 = new FanstasticShip(context);
        fanstasticShips.addAll(Arrays.asList(fanstasticShip1, fanstasticShip2, fanstasticShip3));
    }

    public GamerShip getGamerShip()
    {
        return gamerShip;
    }

    public FanstasticShip getFanstasticShip1()
    {
        return fanstasticShip1;
    }
    public FanstasticShip getFanstasticShip2()
    {
        return fanstasticShip2;
    }
    public FanstasticShip getFanstasticShip3()
    {
        return fanstasticShip3;
    }

    public ArrayList<FanstasticShip> getAllEnemyShips()
    {
        return fanstasticShips;
    }
}
