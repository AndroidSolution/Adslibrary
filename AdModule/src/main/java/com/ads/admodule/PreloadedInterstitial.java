package com.ads.admodule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class PreloadedInterstitial {

    private static InterstitialAd mInterstitialAd;
    private static Dialog dialog;
    private static String str_banner_id;
    public interface MyListener {
        void callback();
    }

    public static void loadInterstitialAd(String Ads_Id,Context context) {
        if (NetworkUtils.isInternetAvailable(context)) {
            Log.d("NetworkCheckVraj", "Internet is available. Checking stability...");

            // Now check for network stability in the background
            NetworkUtils.checkInternetStability(isStable -> {
                if (isStable) {
                    String speed = NetworkUtils.getInternetSpeed(context);
                    Log.d("NetworkCheck", "Internet Speed: " + speed);

                    if (!speed.equals("No Internet")) {
                        str_banner_id = Ads_Id;
                        AdRequest adRequest = new AdRequest.Builder().build();
                        InterstitialAd.load(
                                context,
                                Ads_Id,
                                adRequest,
                                new InterstitialAdLoadCallback() {
                                    @Override
                                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                        Log.d("AdMob", "Ad Loaded Successfully");
                                        mInterstitialAd = interstitialAd;
                                    }

                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                        Log.e("AdMob", "Failed to Load Ad: " + loadAdError.getMessage());
                                        mInterstitialAd = null;
                                    }
                                });
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

    }

    public static void showInterstitialAd(Activity context, MyListener myListener) {
        Utils.is_admob_inter = false;
        if (mInterstitialAd == null) {
            Log.e("AdMob", "Interstitial Ad Not Ready Yet");
            myListener.callback();
            return;
        }

        dialog = new Dialog(context, R.style.NewDialog);
        dialog.setContentView(R.layout.ads_load_dialog);
        dialog.setCancelable(false);
        dialog.show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (dialog.isShowing()) {
                dialog.dismiss();
                myListener.callback();
            }
        }, 60000);

        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                Utils.is_admob_inter = true;
                Log.d("AdMob", "Ad Dismissed");
                mInterstitialAd = null; // Clear reference to avoid using an old ad
                loadInterstitialAd(str_banner_id,context); // Preload the next ad
                dialog.dismiss();
                myListener.callback();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                Utils.is_admob_inter = true;
                Log.e("AdMob", "Failed to Show Ad: " + adError.getMessage());
                mInterstitialAd = null;
                dialog.dismiss();
                myListener.callback();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                Utils.is_admob_inter = false;
                Log.d("AdMob", "Ad is Showing");
                mInterstitialAd = null; // Ensure the ad is not reused
            }
        });

        mInterstitialAd.show(context);
    }
}
