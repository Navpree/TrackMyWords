package paniuta.trackmywords.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CacheHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "app_cache";
    public static final int DATABASE_VERSION = 1;
    public static final String RESULT_TABLE_NAME = "results", LYRIC_RESULT_TABLE_NAME = "lyric_results";
    public static final TableInfo TABLES[] = {
            new TableInfo("results", "result_id", "result_title", "result_index"),
            new TableInfo("lyric_results", "song_id", "song_title", "album_name", "artist_name", "song_lyrics")
    };

    public CacheHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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
        db.execSQL("create table " + TABLES[0].name + " (result_id integer primary key, result_title varchar(50), result_index integer not null);");
        db.execSQL("create table " + TABLES[1].name + " (song_id integer primary key, song_title varchar(50), album_name varchar(50), artist_name varchar(50), song_lyrics varchar(255));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        for(TableInfo info : TABLES){
            db.execSQL("DROP TABLE IF EXISTS " + info.name);
        }
        onCreate(db);
    }
}
