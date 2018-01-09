package com.example.huyha.utils;

import android.os.AsyncTask;

import com.example.huyha.activities.main.MainPresenter;
import com.example.huyha.adapters.SongAdapter;
import com.example.huyha.models.Song;
import com.example.huyha.models.localData.Database;

import java.util.List;

/**
 * Created by huyva on 1/9/2018.
 */

public class AsynFindDatabase extends AsyncTask<String,Void,List<Song>> {
    List<Song> songList;
    SongAdapter songAdapter;
    public AsynFindDatabase(List<Song> songList, SongAdapter songAdapter){
        this.songAdapter = songAdapter;
        this.songList = songList;
    }
    @Override
    protected List<Song> doInBackground(String... strings) {
        return new MainPresenter(new Database().getInstance()).findByName(strings[0]);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Song> songList) {
        super.onPostExecute(songList);
        if ( songList != null && songList.size() > 0) {
            this.songList.clear();
            this.songList.addAll(songList);
            this.songAdapter.notifyDataSetChanged();
        }
    }
}
