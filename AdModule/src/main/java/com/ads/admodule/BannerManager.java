package com.ads.admodule;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;

public class BannerManager {

    private static AdView adView;
    private static boolean isAdLoaded = false;

    private static String str_banner_id;
    private static AdEventListener adEventListener; // Interface for event callbacks

    // ✅ Define an interface for event callbacks
    public interface AdEventListener {
        void onAdLoaded();
        void onAdFailedToLoad(String error);
        void onAdClicked();
        void onAdImpression();
    }

    // ✅ Set the callback listener
    public static void setAdEventListener(AdEventListener listener) {
        adEventListener = listener;
    }

    // ✅ Initialize AdMob (Call this in Application class or MainActivity once)
    public static void initializeAdMob(Activity activity) {
        MobileAds.initialize(activity, initializationStatus -> Log.d("AdMobBanner", "AdMob Initialized"));
    }

    // ✅ Load Ad once to avoid multiple requests
    public static void loadBannerAd(String Ads_Id ,Activity activity, LinearLayout adContainer) {
        str_banner_id = Ads_Id;
        if (adView == null) {
            adView = new AdView(activity);
            adView.setAdUnitId(Ads_Id);
            adView.setAdSize(AdSize.BANNER);

            // Ensure the ad is inside the container
            if (adView.getParent() != null) {
                ((ViewGroup) adView.getParent()).removeView(adView);
            }
            adContainer.addView(adView);
        }


        if (NetworkUtils.isInternetAvailable(activity)) {
            Log.d("NetworkCheck", "Internet is available. Checking stability...");

            // Now check for network stability in the background
            NetworkUtils.checkInternetStability(isStable -> {
                if (isStable) {
                    String speed = NetworkUtils.getInternetSpeed(activity);
                    Log.d("NetworkCheck", "Internet Speed: " + speed);

                    if (!speed.equals("No Internet")) {
                        AdRequest adRequest = new AdRequest.Builder().build();
                        adView.loadAd(adRequest);
                        Log.d("NetworkCheck", "adRequest " + adRequest);
                    } else {
                        Log.e("NetworkCheck", "Internet too slow. Ads not requested.");
                    }
                } else {
                    Log.e("NetworkCheck", "Internet is not stable. Ads not requested.");
                }
            });

        } else {
            Log.e("NetworkCheck", "No internet connection. Ads not requested.");
        }


        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //Toast.makeText(activity, "Banner load", Toast.LENGTH_SHORT).show();
                isAdLoaded = true;
                Log.d("AdMobBanner", "Banner Ad Loaded Successfully");
                if (adEventListener != null) {
                    adEventListener.onAdLoaded();
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                isAdLoaded = false;
               // Toast.makeText(activity, "Banner onAdFailedToLoad", Toast.LENGTH_SHORT).show();
                Log.e("AdMobBanner", "Banner Ad Failed: " + loadAdError.getMessage());
                if (adEventListener != null) {
                    adEventListener.onAdFailedToLoad(loadAdError.getMessage());
                }
                retryLoadBannerAd(activity, adContainer);
            }

            @Override
            public void onAdClicked() {
               // Toast.makeText(activity, "Banner onAdClicked", Toast.LENGTH_SHORT).show();
                Log.d("AdMobBanner", "Banner Ad Clicked");
                if (adEventListener != null) {
                    adEventListener.onAdClicked();
                }
            }

            @Override
            public void onAdImpression() {
              //  Toast.makeText(activity, "Banner onAdImpression", Toast.LENGTH_SHORT).show();
                Log.d("AdMobBanner", "Banner Ad Impression Recorded");
                if (adEventListener != null) {
                    adEventListener.onAdImpression();
                }
            }
        });
    }

    // ✅ Retry mechanism to load the ad again in case of failure
    private static void retryLoadBannerAd(Activity activity, LinearLayout adContainer) {
        adContainer.postDelayed(() -> {
           // Toast.makeText(activity, "Banner retryLoadBannerAd", Toast.LENGTH_SHORT).show();
            Log.d("AdMobBanner", "Retrying Ad Load...");
            loadBannerAd(str_banner_id,activity, adContainer);
        }, 3000); // Retry after 3 seconds
    }

    // ✅ Show Ad in any Activity
    public static void showBannerAd(Activity activity, LinearLayout adContainer) {
        if (isAdLoaded && adView != null) {
            if (adView.getParent() != null) {
                ((ViewGroup) adView.getParent()).removeView(adView);
            }
            adContainer.addView(adView);
        } else {
            Log.d("AdMobBanner", "Ad not ready, loading new Ad...");
            loadBannerAd(str_banner_id,activity, adContainer);
        }
    }
}
