package com.trackmywords.testdata;

import java.util.List;

public class SongSet {

    private List<Song> songs;

    public void setSongs(List<Song> songs){
        this.songs = songs;
    }

    public List<Song> getSongs(){
        return songs;
    }

}
