package com.example.huyha.activities.lyric;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.huyha.models.Song;
import com.example.huyva.karaoke.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LyricActivity extends AppCompatActivity {
    private static final String TAG = "LyricActivity";

    @BindView(R.id.txtComposer)
    TextView txtComposer;
    @BindView(R.id.txtLyric2)
    TextView txtLyric2;
    @BindView(R.id.adViewLyric)
    AdView adViewLyric;

    Song song;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric);
        ButterKnife.bind(this);

        song = (Song) getIntent().getSerializableExtra("song");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(getResources().getString(R.string.title_actionbar_lyric_activity));
        actionBar.setSubtitle(song.getName());

        txtComposer.setText(song.getAuthor());
        txtLyric2.setText(song.getLyric());

        initAd();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        initAdInterstitial();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        initAdInterstitial();
    }

    void initAd(){
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adViewLyric.loadAd(adRequest);
    }
    void initAdInterstitial(){
        final InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.ad_fullscreen));
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Log.d(TAG,"OnAdClosed");
                finish();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG,"OnAdLoad");
                if (interstitialAd!=null){
                    interstitialAd.show();
                }
            }
        });
    }
}
