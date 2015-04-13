package com.backend.trackmywords.beans;

import java.util.List;

public class SongLyricSet {

    private List<Lyrics> songs;
    private int pages;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<Lyrics> getSongs() {
        return songs;
    }

    public void setSongs(List<Lyrics> songs) {
        this.songs = songs;
    }
}
