package com.spaceko70.montropic.part;

import android.content.Context;

import com.spaceko70.montropic.models.Shield;
import com.spaceko70.montropic.R;

import java.util.ArrayList;
import java.util.Arrays;

public class GameBooms
{
    private Shield shield1;
    private Shield shield2;
    private Shield shield3;

    public void createShields(ArrayList<Shield> shields, Context context, int screenWidth, int screenHeight)
    {
        shield1 = new Shield(context, screenWidth, screenHeight, R.drawable.hield);
        shield2 = new Shield(context, screenWidth, screenHeight, R.drawable.hield);
        shield3 = new Shield(context, screenWidth, screenHeight, R.drawable.hield);

        shields.addAll(Arrays.asList(shield1, shield2, shield3));
    }

    public void removeShield(ArrayList<Shield> shields)
    {
        if (shields.size() > 0)
            shields.remove(shields.size() - 1);
    }

    public void removeAllShields(ArrayList<Shield> shields)
    {
        shields.clear();
    }
}
