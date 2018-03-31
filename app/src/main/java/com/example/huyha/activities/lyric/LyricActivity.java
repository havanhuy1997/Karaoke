package com.example.huyha.activities.lyric;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.huyha.models.Song;
import com.example.huyva.karaoke.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LyricActivity extends AppCompatActivity {

    @BindView(R.id.txtComposer)
    TextView txtComposer;
    @BindView(R.id.txtLyric2)
    TextView txtLyric2;
    @BindView(R.id.adFrameLayoutLyric)
    FrameLayout adFrameLayoutLyric;

    Song song;
    InterstitialAd interstitialAd;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric);
        ButterKnife.bind(this);

        song = (Song) getIntent().getSerializableExtra("song");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.title_actionbar_lyric_activity));
        actionBar.setSubtitle(song.getName());

        txtComposer.setText(song.getAuthor());
        txtLyric2.setText(song.getLyric());

        initAd();
        initAdInterstitial();

    }


    @Override
    public void onBackPressed() {
        if (interstitialAd != null) {
            if (interstitialAd.isLoaded()) {
                interstitialAd.show();
            }
            else{
                super.onBackPressed();
            }
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adView.destroy();
    }

    void initAd(){
        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(getString(R.string.banner_home_footer));

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                adFrameLayoutLyric.removeAllViews();
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                adFrameLayoutLyric.removeAllViews();
                if (adView!= null) {
                    adFrameLayoutLyric.addView(adView);
                }
                super.onAdLoaded();
            }
        });
    }
    void initAdInterstitial(){
        interstitialAd = new InterstitialAd(getApplicationContext());
        interstitialAd.setAdUnitId(getString(R.string.ad_fullscreen));
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }
}
