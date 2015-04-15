package com.backend.trackmywords.beans;

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
