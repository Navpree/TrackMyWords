package com.backend.trackmywords;

import com.backend.trackmywords.beans.*;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class QueryService {

    public static final double RESULTS_PER_PAGE = 25.0;

    public static final String COUNT_NUMBER_SONGS = "select count(res.title) from (select Song.title from Song, Album, Artist, Artist_Album, Song_Album" +
            " where Song_Album.song_id = Song.song_id and Song_Album.album_id = Album.album_id and Artist_Album.album_id = Album.album_id" +
            " and Artist_Album.artist_id = Artist.artist_id %s) as res";

    public static final String SELECT_SONG_BETWEEN = "select Song.song_id, Song.title, Album.title, Artist.artist_name, Song.lyrics " +
            "from Song, Album, Artist, Artist_Album, Song_Album " +
            "where Song_Album.song_id = Song.song_id and Song_Album.album_id = Album.album_id and Artist_Album.album_id = Album.album_id and Artist_Album.artist_id = Artist.artist_id " +
            " %s limit ?, 25";

    public static final String SELECT_SONG_MATCHING_LYRICS = "select * from Song where match(lyrics) against (? in natural language mode)";

    public static final String SELECT_SONG_MATCHING_ID = "select Song.title, Album.title, Artist.artist_name, Song.lyrics " +
            "from Song, Album, Artist, Artist_Album, Song_Album " +
            "where Song.song_id = ? and Song_Album.song_id = Song.song_id and Song_Album.album_id = Album.album_id and Artist_Album.album_id = Album.album_id and Artist_Album.artist_id = Artist.artist_id";

    public static final String INSERT_SONG = "insert into Song (title, lyrics, release_date) values (?, ?, ?);";
    public static final String INSERT_ALBUM = "insert into Album(title, cover, release_date) select * from (select ?, ?, ?) as tmp where not exists(select title from Album where title = ?) limit 1;";
    public static final String INSERT_ARTIST = "insert into Artist (artist_name) select * from (select ?) as tmp where not exists(select artist_name from Artist where artist_name = ?) limit 1;";
    public static final String INSERT_SONG_ALBUM = "insert into Song_Album (song_id, album_id) select song_id, album_id from Song, Album where Song.title = ? and Album.title = ?;";
    public static final String INSERT_ARTIST_ALBUM = "insert into Artist_Album (album_id, artist_id) select album_id, artist_id from Album, Artist where Album.title = ? and Artist.artist_name = ?;";

    public static final String DELETE_SONG = "delete from Song where song_id = ?";

    public static final String UPDATE_SONG = "Update Song, Album, Artist, Artist_Album, Song_Album set Song.title = ?, Song.lyrics = ?, Album.title = ?, Artist.artist_name = ?" +
            " where Song_Album.song_id = Song.song_id and Song_Album.album_id = Album.album_id and Artist_Album.album_id = Album.album_id and Artist_Album.artist_id = Artist.artist_id" +
            " and Song.song_id = ?";

    public static boolean validString(String param){
        return !(param == null || param.trim().equals(""));
    }

    public static boolean validBooleanString(String param){
        if(param == null || param.trim().equals("")){
            return false;
        }
        try{
            Boolean.parseBoolean(param);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public static boolean validIntString(String param){
        if(param == null || param.trim().equals("")){
            return false;
        }
        try{
            Integer.parseInt(param);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public static boolean validDateString(String param){
        if(!validString(param)){
            return false;
        }
        try{
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter.parse(param);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    public static String getSongList(String query) throws SQLException, IOException, ClassNotFoundException {
        Connection con = MainApplication.createConnection();
        PreparedStatement statement = con.prepareStatement(SELECT_SONG_MATCHING_LYRICS);
        statement.setString(1, query);
        ResultSet set = statement.executeQuery();
        SongSet songSet = Converter.resultToSongSet(set);
        ObjectMapper mapper = new ObjectMapper();
        con.close();
        return mapper.writeValueAsString(songSet);
    }

    public static String getSongLyrics(String query) throws SQLException, IOException, ClassNotFoundException {
        Connection con = MainApplication.createConnection();
        int id = -1;
        try {
            id = Integer.parseInt(query);
        }catch(Exception e){
            throw new SQLException("Invalid id format for song.");
        }
        PreparedStatement statement = con.prepareStatement(SELECT_SONG_MATCHING_ID);
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        Lyrics l = null;
        if(set.next()){
            l = Converter.resultToLyrics(set, false);
        }
        ObjectMapper mapper =  new ObjectMapper();
        con.close();
        return mapper.writeValueAsString(l);
    }

    public static int getSongCount(String filter) throws SQLException, ClassNotFoundException {
        Connection con = MainApplication.createConnection();
        Statement statement = con.createStatement();
        if(!filter.startsWith(" group")){
            filter = "and " + filter;
        }
        ResultSet set = statement.executeQuery(String.format(COUNT_NUMBER_SONGS, ((filter != null) ? filter : "")));
        int count = 0;
        if(set.next()){
            count = set.getInt(1);
        }
        con.close();
        return count;
    }

    public static String getSongsBetween(int start, int pages, String filter) throws SQLException, ClassNotFoundException, IOException {
        Connection con = MainApplication.createConnection();
        if(!filter.startsWith(" group")){
            filter = "and " + filter;
        }
        PreparedStatement statement = con.prepareStatement(String.format(SELECT_SONG_BETWEEN, filter));
        statement.setInt(1, start);
        ResultSet set = statement.executeQuery();

        SongLyricSet songSet = Converter.resultToSongAASet(set);
        songSet.setPages(pages);

        ObjectMapper mapper = new ObjectMapper();
        con.close();
        return mapper.writeValueAsString(songSet);
    }

    private static void runSongQuery(Connection con, String title, String lyrics, String releaseDate) throws SQLException, ParseException {
        PreparedStatement statement = con.prepareStatement(INSERT_SONG);
        statement.setString(1, title);
        statement.setString(2, lyrics);
        statement.setDate(3, new java.sql.Date(new SimpleDateFormat("dd-MM-yyyy").parse(releaseDate).getTime()));
        statement.executeUpdate();
    }

    private static void runAlbumQuery(Connection con, String album) throws SQLException {
        PreparedStatement statement = con.prepareStatement(INSERT_ALBUM);
        statement.setString(1, album);
        statement.setString(2, "");
        statement.setString(3, "12-12-12");
        statement.setString(4, album);
        statement.executeUpdate();
    }

    private static void runArtistQuery(Connection con, String artist) throws SQLException {
        PreparedStatement statement = con.prepareStatement(INSERT_ARTIST);
        statement.setString(1, artist);
        statement.setString(2, artist);
        statement.executeUpdate();
    }

    private static void runSongAlbumQuery(Connection con, String song, String album) throws SQLException {
        PreparedStatement statement = con.prepareStatement(INSERT_SONG_ALBUM);
        statement.setString(1, song);
        statement.setString(2, album);
        statement.executeUpdate();
    }

    private static void runAlbumArtistQuery(Connection con, String album, String artist) throws SQLException {
        PreparedStatement statement = con.prepareStatement(INSERT_ARTIST_ALBUM);
        statement.setString(1, album);
        statement.setString(2, artist);
        statement.executeUpdate();
    }

    public static boolean insertSong(MultivaluedMap<String, String> map) throws SQLException, ClassNotFoundException, ParseException {
        Connection con = MainApplication.createConnection();
        runSongQuery(con, map.get("title").get(0), map.get("lyrics").get(0), map.get("release_date").get(0));
        runArtistQuery(con, map.get("artist").get(0));
        runAlbumQuery(con, map.get("album").get(0));
        runSongAlbumQuery(con, map.get("title").get(0), map.get("album").get(0));
        runAlbumArtistQuery(con, map.get("album").get(0), map.get("artist").get(0));
        con.close();
        return true;
    }

    public static boolean deleteSong(int id) throws SQLException, ClassNotFoundException {
        Connection con = MainApplication.createConnection();
        PreparedStatement statement = con.prepareStatement(DELETE_SONG);
        statement.setInt(1, id);
        statement.executeUpdate();
        return true;
    }

    public static boolean updateSong(Lyrics lyr) throws SQLException, ClassNotFoundException {
        Connection con = MainApplication.createConnection();
        PreparedStatement statement = con.prepareStatement(UPDATE_SONG);
        statement.setString(1, lyr.getSongTitle());
        statement.setString(2, lyr.getSongLyrics());
        statement.setString(3, lyr.getAlbumTitle());
        statement.setString(4, lyr.getArtistName());
        statement.setInt(5, lyr.getId());
        statement.executeUpdate();
        return true;
    }

}
