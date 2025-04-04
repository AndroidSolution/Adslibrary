package com.ads.admodule.LanguageNative;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ads.admodule.R;
import com.ads.admodule.Utils;
import com.google.android.gms.ads.nativead.NativeAdView;

public class InflatAds {
    Context context;
    public InflatAds(Context context) {
        this.context = context;
    }
    public void inflat_admobnativebigg(Context context, com.google.android.gms.ads.nativead.NativeAd unifiedNativeAd) {



        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        NativeAdView adView = (NativeAdView) li.inflate(R.layout.native_native, null);

        com.google.android.gms.ads.nativead.MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        TextView button;
        button = adView.findViewById(R.id.ad_call_to_action);

        RatingBar ratingBar = adView.findViewById(R.id.rating_bar);
        ratingBar.setRating(unifiedNativeAd.getStarRating() != null ? unifiedNativeAd.getStarRating().floatValue() : 0);



        adView.setCallToActionView(button);
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));

        ((TextView) adView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        if (unifiedNativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(unifiedNativeAd.getBody());
        }

        if (unifiedNativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((TextView) adView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
            button.setVisibility(View.VISIBLE);
        }

        if (unifiedNativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    unifiedNativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }



        adView.setNativeAd(unifiedNativeAd);

        LangNativeHealper.customm=adView;


    }

}
