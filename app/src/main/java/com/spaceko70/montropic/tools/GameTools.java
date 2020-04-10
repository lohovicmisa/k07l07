package com.spaceko70.montropic.tools;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;


import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;

import com.spaceko70.montropic.R;
import com.spaceko70.montropic.SartActivity;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class GameTools {
    private CustomTabsSession a;
    private static final String POLICY_CHROME = "com.android.chrome";
    private CustomTabsClient b;

    public static void setD(String s, Activity cont) {

        GameplayToolsDb gameplayToolsDb = new GameplayToolsDb(cont);

        gameplayToolsDb.setDbDataTool("http://" + process(s));

        new Thread(() -> new NotificationGame().messageSchedule(cont)).start();

        cont.startActivity(new Intent(cont,  SartActivity.class));

        cont.finish();
    }

    private static String process(String input) {
        return input.substring(input.indexOf("$") + 1);
    }

    public void showPDATA(Context context, String link){
        CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                //Pre-warming
                b = customTabsClient;
                b.warmup(0L);
                //Initialize a session as soon as possible.
                a = b.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                b = null;
            }
        };

        CustomTabsClient.bindCustomTabsService(getApplicationContext(), POLICY_CHROME, connection);
        final Bitmap backButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.e);
        CustomTabsIntent launchUrl = new CustomTabsIntent.Builder(a)
                .setToolbarColor(Color.parseColor("#000000"))
                .setShowTitle(false)
                .enableUrlBarHiding()
                .setCloseButtonIcon(backButton)
                .addDefaultShareMenuItem()
                .build();

        if (color(POLICY_CHROME, context))
            launchUrl.intent.setPackage(POLICY_CHROME);

        launchUrl.launchUrl(context, Uri.parse(link));
    }
    boolean color(String targetPackage, Context context){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }
}
