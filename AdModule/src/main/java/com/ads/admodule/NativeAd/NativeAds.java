package com.ads.admodule.NativeAd;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ads.admodule.NetworkUtils;
import com.ads.admodule.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;


public class NativeAds {

    private static NativeAds EInstance;
    private NativeAd EnativeAd;

    public static NativeAds getInstance() {
        if (EInstance == null) {
            EInstance = new NativeAds();
        }
        return EInstance;


    }

    public void showSmallNative(String Ads_ID,final Activity activity, final FrameLayout frameLayout) {
        String admobnative = Ads_ID;
        Log.e("native1", admobnative);

        // Inflate and add shimmer effect
        LayoutInflater inflater = activity.getLayoutInflater();
        ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) inflater.inflate(R.layout.shimmer_layout, null);
        frameLayout.removeAllViews();
        frameLayout.addView(shimmerFrameLayout);
        shimmerFrameLayout.startShimmer();

        AdLoader.Builder builder = new AdLoader.Builder(activity, admobnative);

        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(NativeAd nativeAdd) {
                        boolean isDestroyed = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            isDestroyed = activity.isDestroyed();
                        }
                        if (isDestroyed || activity.isFinishing() || activity.isChangingConfigurations()) {
                            nativeAdd.destroy();
                            return;
                        }
                        if (EnativeAd != null) {
                            EnativeAd.destroy();
                        }
                        EnativeAd = nativeAdd;

                        NativeAdView adView = (NativeAdView) inflater.inflate(R.layout.extra_small_native_ads, null);
                        populateUnifiedNativeAdView100(nativeAdd, adView, activity);

                        frameLayout.removeAllViews();
                        frameLayout.addView(adView);
                    }
                });

        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.e("AdError", "Failed to load native ad: " + loadAdError.getMessage());
            }
        }).build();
        if (NetworkUtils.isInternetAvailable(activity)) {
            Log.d("NetworkCheck", "Internet is available. Checking stability...");

            // Now check for network stability in the background
            NetworkUtils.checkInternetStability(isStable -> {
                if (isStable) {
                    String speed = NetworkUtils.getInternetSpeed(activity);
                    Log.d("NetworkCheck", "Internet Speed: " + speed);

                    if (!speed.equals("No Internet")) {
                        adLoader.loadAd(new AdRequest.Builder().build());

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

    //Native  Big
    public void showNativeBig(String Ads_ID,final Activity activity, final FrameLayout frameLayout) {

        String admobnative01 = Ads_ID;
        LayoutInflater inflater = activity.getLayoutInflater();
        ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) inflater.inflate(R.layout.big_native_shimmer_layout, null);
        frameLayout.removeAllViews();
        frameLayout.addView(shimmerFrameLayout);
        shimmerFrameLayout.startShimmer();
        AdLoader.Builder builder = new AdLoader.Builder(activity, admobnative01);

        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {

            @Override
            public void onNativeAdLoaded(NativeAd nativeAdd) {

                boolean isDestroyed = false;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    isDestroyed = activity.isDestroyed();
                }
                if (isDestroyed || activity.isFinishing() || activity.isChangingConfigurations()) {
                    nativeAdd.destroy();
                    return;
                }

                if (EnativeAd != null) {
                    EnativeAd.destroy();
                }
                EnativeAd = nativeAdd;

                NativeAdView adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.unifiednativead, null);
                populateUnifiedNativeAdView(nativeAdd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }
        });

        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();

        NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder.withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {


                                    }
                                })
                        .build();

        if (NetworkUtils.isInternetAvailable(activity)) {
            Log.d("NetworkCheck", "Internet is available. Checking stability...");

            // Now check for network stability in the background
            NetworkUtils.checkInternetStability(isStable -> {
                if (isStable) {
                    String speed = NetworkUtils.getInternetSpeed(activity);
                    Log.d("NetworkCheck", "Internet Speed: " + speed);

                    if (!speed.equals("No Internet")) {
                        adLoader.loadAd(new AdRequest.Builder().build());

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


    public void showNativeMedium(String Ads_ID,final Activity activity, final FrameLayout frameLayout) {

        String admobnative01 = Ads_ID;
        LayoutInflater inflater = activity.getLayoutInflater();
        ShimmerFrameLayout shimmerFrameLayout = (ShimmerFrameLayout) inflater.inflate(R.layout.medium_shimmer_layout, null);
        frameLayout.removeAllViews();
        frameLayout.addView(shimmerFrameLayout);
        shimmerFrameLayout.startShimmer();

        AdLoader.Builder builder = new AdLoader.Builder(activity, admobnative01);

        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {

            @Override
            public void onNativeAdLoaded(NativeAd nativeAdd) {

                boolean isDestroyed = false;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    isDestroyed = activity.isDestroyed();
                }
                if (isDestroyed || activity.isFinishing() || activity.isChangingConfigurations()) {
                    nativeAdd.destroy();
                    return;
                }

                if (EnativeAd != null) {
                    EnativeAd.destroy();
                }
                EnativeAd = nativeAdd;

                NativeAdView adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.medium_native_ads, null);
                populateUnifiedNativeAdView(nativeAdd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }
        });

        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader =
                builder
                        .withAdListener(
                                new AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(LoadAdError loadAdError) {


                                    }
                                })
                        .build();



        if (NetworkUtils.isInternetAvailable(activity)) {
            Log.d("NetworkCheck", "Internet is available. Checking stability...");

            // Now check for network stability in the background
            NetworkUtils.checkInternetStability(isStable -> {
                if (isStable) {
                    String speed = NetworkUtils.getInternetSpeed(activity);
                    Log.d("NetworkCheck", "Internet Speed: " + speed);

                    if (!speed.equals("No Internet")) {
                        adLoader.loadAd(new AdRequest.Builder().build());
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


    private void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {
        adView.setMediaView((com.google.android.gms.ads.nativead.MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        RatingBar ratingBar = adView.findViewById(R.id.ad_stars);
        ratingBar.setRating(nativeAd.getStarRating() != null ? nativeAd.getStarRating().floatValue() : 0);
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
    }


    private void populateUnifiedNativeAdView100(NativeAd nativeAd, NativeAdView adView, Activity activity) {

        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));

        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));

        RatingBar ratingBar = adView.findViewById(R.id.ad_stars);
        ratingBar.setRating(nativeAd.getStarRating() != null ? nativeAd.getStarRating().floatValue() : 0);

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());


        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
    }

}
