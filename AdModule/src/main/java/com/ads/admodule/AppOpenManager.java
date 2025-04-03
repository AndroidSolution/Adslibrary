package com.ads.admodule;

import static androidx.lifecycle.Lifecycle.Event.ON_START;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

public class AppOpenManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    private static final String LOG_TAG = "AppOpenManager";
    
    private AppOpenAd appOpenAd = null;
    private static boolean isShowingAd = false;
    private long loadTime = 0;
    private Activity currentActivity;
    private final Application application;

    String ads_id;
    public interface AdListener {
        void onAdDismissed();
        void onAdFailedToLoad();
    }

    private final AdListener adListener;

    public AppOpenManager(String AdsID,Application application, AdListener listener) {
        ads_id = AdsID;
        this.application = application;
        this.adListener = listener;
        this.application.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(ON_START)
    public void onStart() {
        if (Utils.is_admob_inter == true) {
            showAdIfAvailable();

        } else {

        }
    }

    public void showAdIfAvailable() {
        if (!isShowingAd && isAdAvailable() && currentActivity != null) {
            Log.d(LOG_TAG, "Showing App Open Ad.");

            appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    appOpenAd = null;
                    isShowingAd = false;
                    fetchAd();
                    if (adListener != null) {
                        adListener.onAdDismissed();
                    }
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    if (adListener != null) {
                        adListener.onAdFailedToLoad();
                    }
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingAd = true;
                }
            });

            appOpenAd.show(currentActivity);
        } else {
            fetchAd();
        }
    }

    public void fetchAd() {
        if (isAdAvailable()) {
            return;
        }

        AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(AppOpenAd ad) {
                appOpenAd = ad;
                loadTime = new Date().getTime();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                if (adListener != null) {
                    adListener.onAdFailedToLoad();
                }
            }
        };

        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(application, ads_id, request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    private boolean wasLoadTimeLessThanNHoursAgo(long hours) {
        long timeDiff = new Date().getTime() - loadTime;
        return timeDiff < (hours * 3600000);
    }

    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }
}
