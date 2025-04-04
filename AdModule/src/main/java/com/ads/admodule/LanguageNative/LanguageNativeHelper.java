package com.ads.admodule.LanguageNative;

import android.content.Context;
import android.util.Log;

import com.ads.admodule.Utils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;

public class LanguageNativeHelper {
    public void shownativeadsbiggg(String AdsID,Context activity) {


        String admobnative = AdsID;
        AdLoader.Builder builder = new AdLoader.Builder(activity, admobnative);
        builder.forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded( com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                Log.d("checklangnative","adsloaded");
                LangNativeHealper.nativeAd=nativeAd;
                LangNativeHealper.nativeloadd=true;
                new InflatAds(activity).inflat_admobnativebigg(activity,nativeAd);
            }
        });
        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.d("checklangnative","onAdFailedToLoad");
                AdLoader.Builder builder = new AdLoader.Builder(activity, admobnative);
                builder.forNativeAd(new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded( com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                        Log.d("checklangnative","onNativeAdLoaded222");
                        LangNativeHealper.nativeloadd=false;
                     //   shownativeads11(activity);

                    }

                });
                AdLoader adLoader = builder.withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {

                        Log.d("checklangnative","onAdFailedToLoad1121111111");
                    }
                }).build();
                adLoader.loadAd(new AdRequest.Builder().build());
            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());

    }
}
