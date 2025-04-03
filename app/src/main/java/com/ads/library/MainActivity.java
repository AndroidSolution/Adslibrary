package com.ads.library;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ads.admodule.BannerManager;
import com.ads.admodule.InterstitialAds;
import com.ads.admodule.NativeAd.NativeAds;
import com.ads.admodule.PreloadedInterstitial;
import com.ads.admodule.CounterInterstitial;

public class MainActivity extends AppCompatActivity {
    private InterstitialAds adMob;
    private LinearLayout adContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //For Banner Ads
        BannerManager.initializeAdMob(MainActivity.this);
        BannerManager.loadBannerAd("ca-app-pub-3940256099942544/9214589741",MainActivity.this,findViewById(R.id.banner_container));
        BannerManager.setAdEventListener(new BannerManager.AdEventListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(String error) {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onAdImpression() {

            }
        });


        //Preloaded Interstitial Ads
        PreloadedInterstitial.loadInterstitialAd("ca-app-pub-3940256099942544/1033173712",this);
        Button btn_preload_inter = findViewById(R.id.preload_Interstitial);
        btn_preload_inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreloadedInterstitial.showInterstitialAd(MainActivity.this, () -> {
                    Toast.makeText(MainActivity.this, "Preloaded Interstitial Ad Closed.", Toast.LENGTH_SHORT).show();
                });
            }
        });



        //Regular Interstitial Ads
        InterstitialAds adMob = new InterstitialAds(this, "ca-app-pub-3940256099942544/1033173712");

        Button btn_regular_inter = findViewById(R.id.regular_Interstitial);
        btn_regular_inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adMob.showInterstitialAd(new InterstitialAds.AdsListener() {
                    @Override
                    public void onAdDismissed() {

                        Toast.makeText(MainActivity.this, "Ad Dismissed!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdFailed() {

                        Toast.makeText(MainActivity.this, "Ad Failed to Load!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        //counter Interstitial Ads
        CounterInterstitial.getInstance().loadInterOne(getApplicationContext(),"ca-app-pub-3940256099942544/1033173712");
        Button btn_counter_inter = findViewById(R.id.counter_Interstitial);
        btn_counter_inter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CounterInterstitial.getInstance().showInter(MainActivity.this,2, new CounterInterstitial.MyCallback()
                {
                    @Override
                    public void callbackCall()
                    {


                        Toast.makeText(MainActivity.this, "Interstitial Closed", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


        //Small native ads

        NativeAds.getInstance().showSmallNative("ca-app-pub-3940256099942544/2247696110",MainActivity.this, findViewById(R.id.native_small));


        //Big Native
        NativeAds.getInstance().showNativeBig("ca-app-pub-3940256099942544/2247696110",MainActivity.this, findViewById(R.id.native_big));


        //Medium Native
        NativeAds.getInstance().showNativeMedium("ca-app-pub-3940256099942544/2247696110",MainActivity.this, findViewById(R.id.native_medium));





    }
}