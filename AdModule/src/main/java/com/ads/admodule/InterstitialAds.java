package com.ads.admodule;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterstitialAds {

    private InterstitialAd mInterstitialAd;
    private boolean isLoading = false;
    private boolean isAdShowing = false;
    private String adUnitId;
    private Activity activity;
    private AdsListener buttonClickListener = null; // Callback for button click

    public interface AdsListener {
        void onAdDismissed();
        void onAdFailed();
    }

    public InterstitialAds(Activity activity, String adUnitId) {
        this.activity = activity;
        this.adUnitId = adUnitId;
        loadInterstitialAd(); // Load ad initially
    }

    private void loadInterstitialAd() {
        if (isLoading || mInterstitialAd != null) {
            return; // Avoid reloading if already loaded
        }

        isLoading = true;
        if (NetworkUtils.isInternetAvailable(activity)) {
            Log.d("NetworkCheckVraj", "Internet is available. Checking stability...");

            // Now check for network stability in the background
            NetworkUtils.checkInternetStability(isStable -> {
                if (isStable) {
                    String speed = NetworkUtils.getInternetSpeed(activity);
                    Log.d("NetworkCheck", "Internet Speed: " + speed);

                    if (!speed.equals("No Internet")) {
                        AdRequest adRequest = new AdRequest.Builder().build();
                        InterstitialAd.load(activity, adUnitId, adRequest, new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(InterstitialAd interstitialAd) {
                                Log.d("AdMobInterRegular", "Ad successfully loaded.");
                                isLoading = false;
                                mInterstitialAd = interstitialAd;

                                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        Log.d("AdMobInterRegular", "Ad dismissed.");
                                        Toast.makeText(activity, "Ad Dismissed", Toast.LENGTH_SHORT).show();
                                        Utils.is_admob_inter =true;
                                        if (buttonClickListener != null) {
                                            buttonClickListener.onAdDismissed();
                                            buttonClickListener = null; // Reset listener
                                        }

                                        mInterstitialAd = null;
                                        isAdShowing = false;
                                        loadInterstitialAd(); // Reload next ad
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(com.google.android.gms.ads.AdError adError) {
                                        Log.e("AdMobInterRegular", "Ad Failed to Show: " + adError.getMessage());
                                        Toast.makeText(activity, "Ad Failed to Show", Toast.LENGTH_SHORT).show();
                                        Utils.is_admob_inter =true;
                                        if (buttonClickListener != null) {
                                            buttonClickListener.onAdFailed();
                                            buttonClickListener = null;
                                        }

                                        isAdShowing = false;
                                        loadInterstitialAd();
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        Utils.is_admob_inter =false;
                                        isAdShowing = true;
                                        Log.d("AdMobInterRegular", "Ad is showing.");
                                    }
                                });
                            }

                            @Override
                            public void onAdFailedToLoad(LoadAdError loadAdError) {
                                Log.e("AdMobInterRegular", "Ad failed to load: " + loadAdError.getMessage());
                                isLoading = false;
                                if (buttonClickListener != null) {
                                    buttonClickListener.onAdFailed();
                                    buttonClickListener = null;
                                }
                            }
                        });
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

    public void showInterstitialAd(AdsListener listener) {
        if (mInterstitialAd != null && !isAdShowing) {
            buttonClickListener = listener;
            mInterstitialAd.show(activity);
        } else {
            Log.e("AdMobInterRegular", "Ad not loaded or already showing. Reloading...");
            if (listener != null) {
                listener.onAdFailed();
            }
            loadInterstitialAd();
        }
    }
}
