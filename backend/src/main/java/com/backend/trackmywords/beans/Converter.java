package com.backend.trackmywords.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Converter {

    public static Song resultToSong(ResultSet set) throws SQLException {
        Song song = new Song();
        song.setId(set.getInt(1));
        song.setTitle(set.getString(2));
        song.setLyrics(set.getString(3));
        song.setReleaseDate(null);
        return song;
    }

    public static SongSet resultToSongSet(ResultSet set) throws SQLException {
        List<Song> songs = new ArrayList<Song>();
        while (set.next()) {
            Song s = null;
            try {
                s = resultToSong(set);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (s != null) {
                songs.add(s);
            }
        }
        SongSet songSet = new SongSet();
        songSet.setSongs(songs);
        return songSet;
    }

    public static SongLyricSet resultToSongAASet(ResultSet set) throws SQLException {
        List<Lyrics> songs = new ArrayList<Lyrics>();
        SongLyricSet songSet = new SongLyricSet();
        while (set.next()) {
            Lyrics s = null;
            try {
                s = resultToLyrics(set, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (s != null) {
                songs.add(s);
            }
        }
        songSet.setSongs(songs);
        return songSet;
    }

    public static Lyrics resultToLyrics(ResultSet set, boolean withId) throws SQLException {
        Lyrics l = new Lyrics();
        int mod = 0;
        if(withId){
            l.setId(set.getInt(1));
            mod = 1;
        }
        l.setSongTitle(set.getString(1 + mod));
        l.setAlbumTitle(set.getString(2 + mod));
        l.setArtistName(set.getString(3 + mod));
        l.setSongLyrics(set.getString(4 + mod));
        return l;
    }

}
