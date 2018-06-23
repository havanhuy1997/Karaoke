package com.huyha.huyha.fragments.favoriteFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.huyha.huyha.activities.lyric.LyricActivity;
import com.huyha.huyha.adapters.SongAdapter;
import com.huyha.huyha.fragments.MainPresenter;
import com.huyha.huyha.models.Song;
import com.huyha.huyha.models.localData.Database;
import com.huyha.van.karaoke.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huyva on 1/11/2018.
 */

public class FavoriteFragment extends android.app.Fragment {
    private static final String TAG = "FavoriteFragment";

    @BindView(R.id.rb5)
    RadioButton rb5;
    @BindView(R.id.rb6)
    RadioButton rb6;
    @BindView(R.id.rvFavorite)
    RecyclerView rvFavorite;

    private Context mContext;

    List<Song> mSonglist = new ArrayList<>();
    SongAdapter mSongAdapter;
    MainPresenter mMainPresenter;
    SongAdapter.onItemClickListener listener;
    Intent intent;

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        addEvent();
    }

    @Override
    public void onStop() {
        super.onStop();
        new Database().getInstance().close();
    }

    private void init(){
        mMainPresenter = new MainPresenter(new Database().getInstance());
        if (rb5.isChecked()){
            mSonglist = mMainPresenter.findFavoriteSong(5);
        }
        else{
            mSonglist = mMainPresenter.findFavoriteSong(6);
        }
        intent = new Intent(mContext, LyricActivity.class);

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
                Log.d(TAG,"button Favorite click");
                Song songClick =  mSonglist.get(positon);
                String id = songClick.getID();
                mMainPresenter.updateFavorite(id,0);
                songClick.setLike(false);
                mSonglist.remove(positon);
                mSongAdapter.notifyDataSetChanged();

            }
        };
        mSongAdapter = new SongAdapter(mSonglist, listener);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rvFavorite.setLayoutManager(mLayoutManager);
        rvFavorite.setAdapter(mSongAdapter);
    }

    private void addEvent(){
        rb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSonglist.clear();
                if (rb5.isChecked()){
                    mSonglist.addAll(mMainPresenter.findFavoriteSong(5));
                }
                else{
                    mSonglist.addAll(mMainPresenter.findFavoriteSong(6));
                }
                mSongAdapter.notifyDataSetChanged();
            }
        });
    }

}
