package com.example.huyha.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.huyha.activities.lyric.LyricActivity;
import com.example.huyha.adapters.SongAdapter;
import com.example.huyha.fragments.MainPresenter;
import com.example.huyha.models.Song;
import com.example.huyha.models.localData.Database;
import com.example.huyha.utils.AsynFindDatabase;
import com.example.huyva.karaoke.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.txtKeySearch)
    EditText txtKeySearch;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.rvPage)
    RecyclerView rvPage;
    public static String keySearch;
    private int countSpaceKeySearch = 0;

    List<Song> mSonglist = new ArrayList<>();
    SongAdapter mSongAdapter;
    MainPresenter mMainPresenter;
    SongAdapter.onItemClickListener listener;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d(TAG,"onCreate");
        init();
        addEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"OnResume");
        init();
        mSongAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        new Database().getInstance().close();
    }

    private void init(){
        mMainPresenter = new MainPresenter(new Database().getInstance());
        mSonglist = mMainPresenter.selectAll();
        intent = new Intent(this, LyricActivity.class);

        listener = new SongAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Start Lyric Activity
                Song songClick =  mSonglist.get(position);
                Log.d(TAG,"name click" + mSonglist.get(position).getName());
                intent.putExtra("song",songClick);
                startActivity(intent);
            }

            @Override
            public void onButtonFavoriteClick(View v, int positon) {
                //Update favorite Song
                Log.d(TAG,"button click");
                Song songClick =  mSonglist.get(positon);
                String id = songClick.getID();
                if (songClick.isLike()){
                    mMainPresenter.updateFavorite(id,0);
                    songClick.setLike(false);
                    mSongAdapter.notifyDataSetChanged();
                }
                else{
                    mMainPresenter.updateFavorite(id,1);
                    songClick.setLike(true);
                    mSongAdapter.notifyDataSetChanged();
                }
            }
        };
        mSongAdapter = new SongAdapter(mSonglist, listener);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvPage.setLayoutManager(mLayoutManager);
        rvPage.setAdapter(mSongAdapter);
    }

    private void addEvent() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keySearch = txtKeySearch.getText().toString();
                new AsynFindDatabase(mSonglist, mSongAdapter).execute(keySearch);
            }
        });

        txtKeySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keySearch = txtKeySearch.getText().toString();
                int count = keySearch.length() - keySearch.replace(" ","").length();
                if (count != countSpaceKeySearch){
                    new AsynFindDatabase(mSonglist, mSongAdapter).execute(keySearch);
                }
            }
        });

        txtKeySearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtKeySearch.getText().toString() == ""){
                    countSpaceKeySearch = 0;
                }
            }
        });

    }
}
