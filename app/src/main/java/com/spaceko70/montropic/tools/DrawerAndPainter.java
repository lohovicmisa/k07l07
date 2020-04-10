package com.spaceko70.montropic.tools;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.spaceko70.montropic.models.OneBoom;
import com.spaceko70.montropic.models.FanstasticShip;
import com.spaceko70.montropic.models.LBtn;
import com.spaceko70.montropic.models.Meteor;
import com.spaceko70.montropic.models.GamerShip;
import com.spaceko70.montropic.models.Boom2;
import com.spaceko70.montropic.models.RBtn;
import com.spaceko70.montropic.models.Shield;
import com.spaceko70.montropic.models.StopButton;
import com.spaceko70.montropic.models.TargetButton;

import java.util.ArrayList;
import java.util.List;

public class DrawerAndPainter
{
    public void drawPlayerShip(Paint paint, Canvas canvas, GamerShip gamerShip, TargetButton button, int screenHeight)
    {
        canvas.drawBitmap(gamerShip.getRawPlayerShip(), gamerShip.getxCoordinate(),
                screenHeight - button.getButton().getHeight() - gamerShip.getRawPlayerShip().getHeight(), paint);
    }

    //ToDo: Change to List of Enemy Ships
    public void drawEnemyShips(Paint paint, Canvas canvas, FanstasticShip ship1, FanstasticShip ship2, FanstasticShip ship3)
    {
        canvas.drawBitmap(ship1.getRawEnemyShip(), ship1.getxCoordinate(), ship1.getyCoordinate(), paint);
        canvas.drawBitmap(ship2.getRawEnemyShip(), ship2.getxCoordinate(), ship2.getyCoordinate(), paint);
        canvas.drawBitmap(ship3.getRawEnemyShip(), ship3.getxCoordinate(), ship3.getyCoordinate(), paint);
    }

    //ToDo: Change to List of Meteors
    public void drawMeteor(Paint paint, Canvas canvas, Meteor meteor1)
    {
        canvas.drawBitmap(meteor1.getRawMeteor(), meteor1.getXCoordinate(), meteor1.getYCoordinate(), paint);
    }

    public void drawLeftButton(Paint paint, Canvas canvas, LBtn button, int right, int screenHeight)
    {
        canvas.drawBitmap(button.getButton(), right, screenHeight - button.getButton().getHeight(), paint);
    }

    public void drawRightButton(Paint paint, Canvas canvas, RBtn button, int screenWidth, int screenHeight)
    {
        canvas.drawBitmap(button.getButton(), screenWidth -
                button.getButton().getWidth(), screenHeight -
                button.getButton().getHeight(), paint);
    }

    public void drawTargetButton(Paint paint, Canvas canvas, TargetButton button, int screenWidth, int screenHeight)
    {
        canvas.drawBitmap(button.getButton(), ((screenWidth / 2) - (button.getButton().getWidth() / 2)),
                screenHeight - button.getButton().getHeight(), paint);
    }

    public void drawShields(Paint paint, Canvas canvas, ArrayList<Shield> shields, int screenHeight)
    {
        if (shields.size() > 0)
        {
            int height = 0;

            for (int i = 0; i < shields.size(); i++)
            {
                canvas.drawBitmap(shields.get(i).getShield(), 0 , screenHeight/6 - height, paint);
                height += shields.get(i).getShield().getWidth();
            }
        }
    }

    public void drawStopButton(Paint paint, Canvas canvas, StopButton button, int screenWidth)
    {
        canvas.drawBitmap(button.getButton(), screenWidth - button.getButton().getWidth(), 0, paint);
    }

    public void drawPlayerLaserBlasts(Paint paint, Canvas canvas, List<Boom2> listOfBoom2s)
    {
        for (int i = 0; i < listOfBoom2s.size(); i++) {
            canvas.drawBitmap(listOfBoom2s.get(i).getLaser(), listOfBoom2s.get(i).getCoordinateX(),
                    listOfBoom2s.get(i).getCoordinateY(), paint);
        }
    }

    public void drawEnemyShip1LaserBlasts(Paint paint, Canvas canvas, List<OneBoom> enemyShip1LaserBlasts)
    {
        for (int i = 0; i < enemyShip1LaserBlasts.size(); i++) {
            canvas.drawBitmap(enemyShip1LaserBlasts.get(i).getLaser(), enemyShip1LaserBlasts.get(i).getCoordinateX(),
                    enemyShip1LaserBlasts.get(i).getCoordinateY(), paint);
        }
    }

    public void drawEnemyShip2LaserBlasts(Paint paint, Canvas canvas, List<OneBoom> enemyShip2LaserBlasts)
    {
        for (int i = 0; i < enemyShip2LaserBlasts.size(); i++) {
            canvas.drawBitmap(enemyShip2LaserBlasts.get(i).getLaser(), enemyShip2LaserBlasts.get(i).getCoordinateX(),
                    enemyShip2LaserBlasts.get(i).getCoordinateY(), paint);
        }
    }

    public void drawEnemyShip3LaserBlasts(Paint paint, Canvas canvas, List<OneBoom> enemyShip3LaserBlasts)
    {
        for (int i = 0; i < enemyShip3LaserBlasts.size(); i++) {
            canvas.drawBitmap(enemyShip3LaserBlasts.get(i).getLaser(), enemyShip3LaserBlasts.get(i).getCoordinateX(),
                    enemyShip3LaserBlasts.get(i).getCoordinateY(), paint);
        }
    }
}
