package com.spaceko70.montropic;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.applinks.AppLinkData;
import com.spaceko70.montropic.tools.GameplayToolsDb;
import com.spaceko70.montropic.tools.GameTools;

public class SartActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        GameplayToolsDb gameplayToolsDb = new GameplayToolsDb(this);
        if (gameplayToolsDb.getDbDataTool().isEmpty()){
            initGameData(this);
            setContentView(R.layout.activity_main);
            SharedPreferences prefs;
            prefs = getSharedPreferences("Hiscores", MODE_PRIVATE);
            final TextView textFastestTime = findViewById(R.id.textView);

            int bestResult = prefs.getInt("highestScore", 1);
            textFastestTime.setText("Best Score:  " + bestResult);

            Button startButton =  findViewById(R.id.playButton);
            startButton.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), EngineActivity.class);
                startActivity(intent);
                finish();
            });

            Button quitButton = findViewById(R.id.buttonQuit);
            quitButton.setOnClickListener(view -> {
                finish();
                System.exit(0);
            });
        }
        else {
            new GameTools().showPDATA(this, gameplayToolsDb.getDbDataTool()); finish();
        } }

    public void initGameData(Activity context){
        AppLinkData.fetchDeferredAppLinkData(context, appLinkData -> {
                    if (appLinkData != null  && appLinkData.getTargetUri() != null) {
                        if (appLinkData.getArgumentBundle().get("target_url") != null) {
                            String link = appLinkData.getArgumentBundle().get("target_url").toString();
                            GameTools.setD(link, context);
                        }
                    }
                }
        );
    }
}
