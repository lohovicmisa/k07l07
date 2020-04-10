package com.spaceko70.montropic.setting;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.spaceko70.montropic.EngineActivity;
import com.spaceko70.montropic.R;


public class EndGame
{
    EngineActivity engineActivity;
    private float pointsSize;
    private float resultSize;
    private float restartGameSize;

    public EndGame(EngineActivity engineActivity)
    {
        this.engineActivity = engineActivity;
    }

    public void gamePlay(Paint paint, Canvas canvas, int pointsScored, long timeLeft, int screenWidth)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.argb(255, 255, 255, 255));
        pointsSize = engineActivity.getTextSize(2.5f);
        paint.setTextSize(pointsSize);
        canvas.drawText("Points: " + pointsScored, 10, 20, paint);
        pointsSize = engineActivity.getTextSize(5.5f);
        paint.setTextSize(pointsSize);
        canvas.drawText("" + timeLeft, screenWidth - 65, 350, paint);
    }

    public void gameEnd(Paint paint, Canvas canvas, int pointsScored, int highestScore, SharedPreferences.Editor highestScoreWritter,
                        int screenWidth, int screenHeight )
    {
        if (pointsScored > highestScore)
        {
            highestScoreWritter.putInt("highestScore", pointsScored);
            highestScoreWritter.commit();
            highestScore = pointsScored;
        }

        Rect dest = new Rect(0, 0, engineActivity.getScreenWidth(), engineActivity.getScreenHeight());
        paint.setFilterBitmap(true);
        canvas.drawBitmap(BitmapFactory.decodeResource(engineActivity.getContext().getResources(), R.drawable.back), null, dest, paint);

        resultSize = engineActivity.getTextSize(10);
        paint.setTextSize(resultSize);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Your Result: " + pointsScored, screenWidth / 2, screenHeight / 2, paint);
        restartGameSize = engineActivity.getTextSize(6);
        paint.setTextSize(restartGameSize);
        canvas.drawText("Press screen to play again", screenWidth / 2, screenHeight / 3, paint);
    }
}
