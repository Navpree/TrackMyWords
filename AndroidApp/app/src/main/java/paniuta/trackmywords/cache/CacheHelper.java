package paniuta.trackmywords.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

import paniuta.trackmywords.beans.SongSet;

/**
 * An SQLiteOpenHelper needed to construct an sqlite database.
 * Also provides some static helper methods for reading and writing to the
 * cache for the ui.
 */
public class CacheHelper extends SQLiteOpenHelper{

    /**
     * The sqlite database name. Literal value: app_cache
     */
    public static final String DATABASE_NAME = "app_cache";

    /**
     * The sqlite database version. Currently 1
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * The result table name, where the list of songs are stored. Literal value: songs
     */
    public static final String RESULT_TABLE_NAME = "songs";

    /**
     * An array of TableInfo instances containing the table name and columns.
     * Left as an array so the database can be expanded later on.
     */
    public static final TableInfo TABLES[] = {
            new TableInfo("songs", "song_id", "song_title"),
    };

    public CacheHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Returns an instance of TableInfo where the name property matches the provided name parameter.
     * @param name The name of the table.
     * @return A TableInfo instance with the name and columns of a table matching the provided name String parameter.
     */
    public static TableInfo getInfoByName(String name){
        for(TableInfo info : TABLES){
            if(info.name.equals(name)){
                return info;
            }
        }
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLES[0].name + " (song_id integer primary key, song_title varchar(50));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        for(TableInfo info : TABLES){
            db.execSQL("DROP TABLE IF EXISTS " + info.name);
        }
        onCreate(db);
    }

    /**
     * Takes in the context for the current activity and returns a SongSet instance
     * containing a list of all the current songs in the cache db.
     * @param con The context of the calling activity
     * @return A SongSet instance containing a list of the songs queried from the cache db.
     * @throws SQLException May be thrown by the sqlite database if a query is invalid.
     */
    public static SongSet helpReadCache(Context con) throws SQLException {
        CacheDAO cache = Cache.getInstance(con);
        if(!cache.isOpen()){
            cache.open();
        }
        SongSet set = cache.getResultList();
        cache.close();
        return set;
    }

    /**
     * Takes in the context for the current activity and a SongSet instance and writes all the
     * songs to the cache db.
     * @param con The context of the calling activity.
     * @param set A SongSet instance containing a list of the songs to be inserted into the db.
     * @throws SQLException
     */
    public static void helpWriteCache(Context con, SongSet set) throws SQLException{
        if (set.getSongs().size() > 0) {
            CacheDAO cache = Cache.getInstance(con);
            if (!cache.isOpen()) {
                cache.open();
            }
            cache.clear(CacheHelper.RESULT_TABLE_NAME);
            cache.insertSongSet(set);
            cache.close();
        }
    }
}
