package paniuta.trackmywords.beans;

import java.util.List;

/**
 * Created by joannetanson on 3/15/15.
 */
public class SongSet {
    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    private List<Song> songs;

}
