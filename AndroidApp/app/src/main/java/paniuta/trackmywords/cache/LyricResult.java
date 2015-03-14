package paniuta.trackmywords.cache;

public class LyricResult {

    public final long songID;
    public final String songTitle, albumName, songLyrics, artistName;

    public LyricResult(long songID, String songTitle, String albumName, String songLyrics, String artistName){
        this.songID = songID;
        this.songTitle = songTitle;
        this.albumName = albumName;
        this.songLyrics = songLyrics;
        this.artistName = artistName;
    }

}
