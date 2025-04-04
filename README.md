# Prevent Ads


> Step 1. Add the JitPack repository to your build file

```gradle
     allprojects {
		repositories {
			
			maven { url 'https://jitpack.io' }
		}
	}
 ```
  
  > Step 2. Add the dependency

  
```gradle
dependencies {

      implementation 'com.github.AndroidSolution:Adslibrary:1.1.0'

            }
  ```
  # Open Ads 
  
  > Step 1. Add Permission
  ```gradle
  
 <uses-permission android:name="com.google.android.gms.permission.AD_ID" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 ```
  > Step 2. Create a class like MainClass.class
  
  ```gradle
   private AppOpenManager appOpenManager;

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
  ```
  
 > Step 3. Update menifest
  ```gradle
 name=".MyApplication"
 ```

  # Inter Ads 
  
  > Step 1. Inter Ads Load in MainActivity onCreate
  ```gradle
  Prevent_Interstitial.getInstance().loadInterOne(getApplicationContext(),"ca-app-pub-3940256099942544/1033173712");

  ```

  > Step 2. Inter Ads Show
  > You can use a counter simply to give the number of counters before giving context.
  > You not use counter simply write 1 before giving contect.

  ```gradle
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

  ```
  
# Banner Ads 
  
  > Step 1.Implement Banner Ads With Size
  ```gradle
  
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

 ```
 
  
 # Regular Native
  ![3324991](https://i.postimg.cc/fTfvdPts/redular-native.jpg)
  > Step 1.Implement Regular Native
  > Library set layout automatically
  ```gradle


          <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:orientation="vertical"
                android:padding="25dp">

                <FrameLayout
                    android:id="@+id/fl_adplaceholder"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center" />

            </LinearLayout>


        //Big Native
        NativeAds.getInstance().showNativeBig("ca-app-pub-3940256099942544/2247696110",MainActivity.this, findViewById(R.id.native_big));

 ```
  # Medium Native
 ![3324991](https://i.postimg.cc/RVBRNm0G/medium-native.jpg)
  > Step 1.Implement Medium Native
  > Library set layout automatically
  ```gradle


          <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:orientation="vertical"
                android:padding="25dp">

                <FrameLayout
                    android:id="@+id/fl_adplaceholder"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center" />

            </LinearLayout>


           //Medium Native
        NativeAds.getInstance().showNativeMedium("ca-app-pub-3940256099942544/2247696110",MainActivity.this, findViewById(R.id.native_medium));


 ```

 # Small Native
  ![3324991](https://i.postimg.cc/G3YKLtbH/small-native.jpg)
  > Step 1.Implement Small Native
  > Library set layout automatically
  ```gradle


          <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:gravity="center"
                android:orientation="vertical"
                android:padding="25dp">

                <FrameLayout
                    android:id="@+id/fl_adplaceholder"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center" />

            </LinearLayout>




          NativeAds.getInstance().showSmallNative("ca-app-pub-3940256099942544/2247696110",MainActivity.this, findViewById(R.id.native_small));


 ```

 # LanguageNativeHelper
  > step 1. Requst for ads in mainclass (previus act. also)

```gradle

        new LanguageNativeHelper().shownativeadsbiggg("ca-app-pub-3940256099942544/2247696110",MyApplication.this);

 ```
 > step 2. load ads

```gradle
 new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    FrameLayout r11 = (FrameLayout) findViewById(R.id.lnag_native_container);
                    if (LangNativeHealper.nativeloadd == true && LangNativeHealper.customm != null) {
                        if (LangNativeHealper.customm.getParent() != null) {
                            ((ViewGroup) LangNativeHealper.customm.getParent()).removeView(LangNativeHealper.customm);
                        }
                        r11.addView(LangNativeHealper.customm);
                    }
                }catch (Exception e){

                }

            }
        },5000);

 ```
