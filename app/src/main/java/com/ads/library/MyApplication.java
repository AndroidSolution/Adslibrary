package com.ads.library;

import android.app.Application;
import android.util.Log;

import com.ads.admodule.AppOpenManager;
import com.ads.admodule.LanguageNative.LanguageNativeHelper;
import com.google.android.gms.ads.MobileAds;

public class MyApplication extends Application {
    private AppOpenManager appOpenManager;

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, initializationStatus -> {});
        new LanguageNativeHelper().shownativeadsbiggg("ca-app-pub-3940256099942544/2247696110",MyApplication.this);

        appOpenManager = new AppOpenManager("ca-app-pub-3940256099942544/9257395921",this, new AppOpenManager.AdListener() {
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
