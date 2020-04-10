package com.spaceko70.montropic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.spaceko70.montropic.part.Game;


public class EngineActivity extends Activity
{
    private static int screenWidth;
    private static int screenHeight;

    private static Context mContext;
    private Game game;

    //This is where Play button sends player from MainActivity class
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mContext = this;

        //Get display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        //Load resolution into Point object
        Point screenSize = new Point();
        display.getSize(screenSize);
        //Set screen size
        screenWidth = screenSize.x;
        screenHeight = screenSize.y;

        game = new Game(this, screenWidth, screenHeight);
        //Make game view the view for the activity
        setContentView(game);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        game.pause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        game.resume();
    }

    public int getScreenWidth()
    {
        return screenWidth;
    }

    public int getScreenHeight()
    {
        return screenHeight;
    }

    public int getScreenArea()
    {
        return screenWidth * screenHeight;
    }

    public float getTextSize(float percentage)
    {
        int widthPixels = getScreenWidth();
        return widthPixels*(percentage/100.0f);
    }

    public Context getContext(){
        return mContext;
    }
}


