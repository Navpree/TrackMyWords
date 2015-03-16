package paniuta.trackmywords.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import paniuta.trackmywords.beans.Lyrics;
import paniuta.trackmywords.beans.Song;
import paniuta.trackmywords.beans.SongSet;

public class CacheDAO {

    private SQLiteDatabase database;
    private CacheHelper helper;
    private boolean open = false;

    public CacheDAO(Context context) {
        helper = new CacheHelper(context);
    }

    public void open() throws SQLException {
        database = helper.getWritableDatabase();
        open = true;
    }

    public void close() throws SQLException {
        helper.close();
        open = false;
    }

    public boolean isOpen(){
        return open;
    }

    public void clear(String tableName) {
        TableInfo info = CacheHelper.getInfoByName(tableName);
        if(info == null){
            return;
        }
        database.execSQL("delete from " + info.name);
    }

    public void insertSongSet(SongSet set){
        for(Song s : set.getSongs()){
            insertSong(s);
        }
    }

    public void insertSong(Song song) {
        TableInfo info = CacheHelper.getInfoByName(CacheHelper.RESULT_TABLE_NAME);
        ContentValues values = new ContentValues();
        values.put(info.columns[0], song.getId());
        values.put(info.columns[1], song.getTitle());
        database.insert(info.name, null, values);
    }

    public SongSet getResultList() {
        SongSet set =  new SongSet();
        List<Song> results = new ArrayList<Song>();
        Cursor cursor = database.query(CacheHelper.TABLES[0].name, CacheHelper.TABLES[0].columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Song song = Converter.cursorToResult(cursor);
            if(song != null) {
                results.add(song);
            }
            cursor.moveToNext();
        }
        set.setSongs(results);
        return set;
    }

}
