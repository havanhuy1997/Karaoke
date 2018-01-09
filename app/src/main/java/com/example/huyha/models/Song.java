package com.example.huyha.models;

import java.io.Serializable;

/**
 * Created by huyva on 7/9/2017.
 */

public class Song implements Serializable {
    private String ID;
    private String name;
    private String author;
    private  String lyric;
    private boolean like;

    public Song(String ID, String name,String lyric, String author, boolean like) {
        this.ID = ID;
        this.name = name;
        this.author = author;
        this.like = like;
        this.lyric = lyric;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public Song(String s, String s1, String s2) {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return author;
    }

    public void setSinger(String author) {
        this.author = author;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
