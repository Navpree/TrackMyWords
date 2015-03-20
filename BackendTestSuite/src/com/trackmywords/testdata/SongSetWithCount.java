package com.trackmywords.testdata;

import java.util.List;

public class SongSetWithCount {

    private List<Song> songs;
    private int pages;

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
