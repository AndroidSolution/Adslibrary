package com.ads.library;

import android.app.Application;
import android.util.Log;

import com.ads.admodule.AppOpenManager;
import com.google.android.gms.ads.MobileAds;

public class MyApplication extends Application {
    private AppOpenManager appOpenManager;

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, initializationStatus -> {});
        appOpenManager = new AppOpenManager(this, new AppOpenManager.AdListener() {
            @Override
            public void onAdDismissed() {
                Log.d("AdStatus", "Ad dismissed");
            }

            @Override
            public void onAdFailedToLoad() {
                Log.d("AdStatus", "Ad failed to load");
            }
        });
    }
}
