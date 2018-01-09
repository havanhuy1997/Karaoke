package com.example.huyha.activities.lyric;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.huyha.models.Song;
import com.example.huyva.karaoke.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LyricActivity extends AppCompatActivity {
    private static final String TAG = "LyricActivity";

    @BindView(R.id.txtComposer)
    TextView txtComposer;
    @BindView(R.id.txtLyric2)
    TextView txtLyric2;

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       this.finish();
       return true;
    }
}
