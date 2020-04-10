package com.spaceko70.montropic.part;

import android.content.Context;

import com.spaceko70.montropic.models.LBtn;
import com.spaceko70.montropic.models.RBtn;
import com.spaceko70.montropic.models.StopButton;
import com.spaceko70.montropic.models.TargetButton;
import com.spaceko70.montropic.R;

public class GameBtns
{
    private LBtn LBtn;
    private RBtn RBtn;
    private TargetButton targetButton;
    private StopButton stopButton;

    public void createButtons(Context context, int screenWidth, int screenHeight)
    {
        LBtn = new LBtn(context, screenWidth, 0, R.drawable.lbtn);
        RBtn = new RBtn(context, screenWidth, 0, R.drawable.rbtn);
        targetButton = new TargetButton(context, screenWidth, screenHeight, R.drawable.target);
        stopButton = new StopButton(context, screenWidth, screenHeight, R.drawable.pause);
    }

    public LBtn getLBtn()
    {
        return LBtn;
    }

    public RBtn getRBtn()
    {
        return RBtn;
    }

    public TargetButton getTargetButton()
    {
        return targetButton;
    }

    public StopButton getStopButton()
    {
        return stopButton;
    }
}
