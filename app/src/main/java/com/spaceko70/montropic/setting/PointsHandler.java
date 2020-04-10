package com.spaceko70.montropic.setting;

public class PointsHandler
{
    private volatile static int points;

    public void decrementPoints()
    {
        points--;
    }

    public void incrementPoints()
    {
        points++;
    }

    public void setPoints()
    {
        points = 0;
    }

    public int getPoints()
    {
        return points;
    }
}
