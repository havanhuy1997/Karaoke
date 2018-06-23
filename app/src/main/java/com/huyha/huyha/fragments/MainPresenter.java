package com.huyha.huyha.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.huyha.huyha.models.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyva on 1/7/2018.
 */

public class MainPresenter{
    private static final String TAG = "MainPresenter";
    private static int typeSong;
    private SQLiteDatabase mDatabase;

    public MainPresenter(SQLiteDatabase database){
        mDatabase = database;
    }

    public int getTypeSong() {
        return typeSong;
    }

    public void setTypeSong(int typeSong) {
        this.typeSong = typeSong;
    }

    // Select all from database
    public List<Song> selectAll(){
        List<Song> songList = new ArrayList<>();
        String sqlQuery = "select * from songs where type_song="+typeSong+" order by name_song";
        if (mDatabase != null){
            Cursor cursor = mDatabase.rawQuery(sqlQuery,null);
            if (cursor == null){
                Log.d(TAG,"Null cursor");
                return null;
            }
            int count = 0;
            while (cursor.moveToNext()){
                if (++count >10) break;
                String id = cursor.getString(0);
                String name = cursor.getString(2);
                String lyric = cursor.getString(3);
                String composer = cursor.getString(4);
                Boolean favorite = cursor.getInt(6) > 0;
                Song song = new Song(id,name.toUpperCase(),lyric,composer,favorite);
                songList.add(song);
            }
        }
        return songList;
    }

    //change  vietnamaese name to english
    String[] a = new String[]{"a","à","ả", "ạ","ã", "á", "ă", "ắ", "ẳ", "ẵ", "ặ", "ằ", "â" ,"ấ", "ầ", "ẩ", "ẫ", "ậ"};
    String[] e = new String[]{"e","é","è","ẻ","ẽ","ẹ","ê","ế","ề","ể","ễ"};
    String[] i = new String[]{"i","í","ì","ỉ","ĩ","ị"};
    String[] o = new String[]{"o","ó","ò","ỏ","õ","ọ","ô","ố","ồ","ổ","ỗ","ộ","ơ","ớ","ờ","ở","ỡ","ợ"};
    String[] u = new String[]{"u","ú","ù","ủ","ũ","ụ","ư","ứ","ừ","ử","ữ","ự"};
    String[] y = new String[]{"y","ý","ỳ","ỹ","ỷ","ỵ"};
    String[] d = new String[]{"đ"};
    private String changeNameSong(String nameSong, String[] originalChars, String changeChar){
        String nameSongNoSignal = nameSong;
        for (String ch : originalChars){
            nameSongNoSignal = nameSongNoSignal.replaceAll(ch,changeChar);
        }
        return  nameSongNoSignal;
    }

    public List<Song> findByName(String nameSong){

        // BO DAU nameSong
        String nameSongNoSignal = nameSong;
        nameSongNoSignal = changeNameSong(nameSongNoSignal, a , "a");
        nameSongNoSignal = changeNameSong(nameSongNoSignal, i , "i");
        nameSongNoSignal = changeNameSong(nameSongNoSignal, e , "e");
        nameSongNoSignal = changeNameSong(nameSongNoSignal, u , "u");
        nameSongNoSignal = changeNameSong(nameSongNoSignal, y , "y");
        nameSongNoSignal = changeNameSong(nameSongNoSignal, d , "d");
        nameSongNoSignal = changeNameSong(nameSongNoSignal, o , "o");
        Log.d(TAG,nameSongNoSignal);

        List<Song> songList = new ArrayList<>();
        if (mDatabase != null && nameSongNoSignal != ""){
            Cursor cursor = mDatabase.rawQuery("SELECT  * FROM songs WHERE vietnamese_song_name LIKE '%"+
                                                nameSongNoSignal +"%' AND type_song="+typeSong+" order by vietnamese_song_name",null);
            if (cursor == null){
                Log.d(TAG,"Null cursor");
                return null;
            }
            while (cursor.moveToNext()){
                String id = cursor.getString(0);
                Log.d(TAG,id);
                String name = cursor.getString(2).toUpperCase();
                String lyric = cursor.getString(3);
                String composer = cursor.getString(4);
                Boolean favorite = cursor.getInt(6) > 0;
                Song song = new Song(id,name,lyric,composer,favorite);
                songList.add(song);
            }
        }
        return songList;
    }

    public boolean updateFavorite(String id,int like){
        ContentValues contentValues = new ContentValues();
        contentValues.put("favorite",like);
        int rowEffect = mDatabase.update("songs",contentValues,"id="+id,null);
        if (rowEffect > 0){
            return true;
        }
        else{
            Log.d(TAG,"Error updateFavorite");
        }
        return false;
    }

    public List<Song> findFavoriteSong(int typeSong){
        List<Song> songList = new ArrayList<>();
        String sqlQuery = "select * from songs where type_song="+typeSong+" AND favorite=1 order by name_song";
        if (mDatabase != null){
            Cursor cursor = mDatabase.rawQuery(sqlQuery,null);
            if (cursor == null){
                Log.d(TAG,"Null cursor in findFavoriteSong");
                return null;
            }
            while (cursor.moveToNext()){
                String id = cursor.getString(0);
                String name = cursor.getString(2);
                String lyric = cursor.getString(3);
                String composer = cursor.getString(4);
                Boolean favorite = cursor.getInt(6) > 0;
                Song song = new Song(id,name.toUpperCase(),lyric,composer,favorite);
                songList.add(song);
            }
        }
        return songList;
    }

}
