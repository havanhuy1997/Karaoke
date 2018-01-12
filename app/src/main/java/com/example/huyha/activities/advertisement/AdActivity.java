package com.example.huyha.activities.advertisement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.huyva.karaoke.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdActivity extends AppCompatActivity {
    private InterstitialAd interstitialAd;
    private AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        initAd();
    }

    private void showInterstitial() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    void initAd(){
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.ad_fullscreen));
        adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener()
        {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                showInterstitial();
            }
        });
    }
}
