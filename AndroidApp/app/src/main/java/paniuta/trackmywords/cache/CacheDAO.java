package paniuta.trackmywords.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public <T> void insert(T data) {
        if (data instanceof Result) {
            insertResult((Result) data);
        } else if (data instanceof LyricResult) {
            insertLyricResult((LyricResult) data);
        }
    }

    public void clear(String tableName) {
        TableInfo info = CacheHelper.getInfoByName(tableName);
        if(info == null){
            return;
        }
        database.execSQL("delete from " + info.name);
    }

    private void insertResult(Result r) {
        TableInfo info = CacheHelper.getInfoByName(CacheHelper.RESULT_TABLE_NAME);
        ContentValues values = new ContentValues();
        values.put(info.columns[0], r.resultID);
        values.put(info.columns[1], r.resultTitle);
        values.put(info.columns[2], r.resultIndex);
        database.insert(info.name, null, values);
    }

    private void insertLyricResult(LyricResult r) {
        TableInfo info = CacheHelper.getInfoByName(CacheHelper.LYRIC_RESULT_TABLE_NAME);
        ContentValues values = new ContentValues();
        values.put(info.columns[0], r.songID);
        values.put(info.columns[1], r.songTitle);
        values.put(info.columns[2], r.albumName);
        values.put(info.columns[3], r.artistName);
        values.put(info.columns[4], r.songLyrics);
        database.insert(info.name, null, values);
    }

    public List<Result> getResultList() {
        List<Result> results = new ArrayList<Result>();
        Cursor cursor = database.query(CacheHelper.TABLES[0].name, CacheHelper.TABLES[0].columns, null, null, null, null, null);
        while (!cursor.isAfterLast()) {
            Result r = Result.cursorToResult(cursor);
            if(r != null) {
                results.add(r);
            }
            cursor.moveToNext();
        }
        return results;
    }

    public Result getResultAtIndex(int index) {
        Result r = null;
        TableInfo info = CacheHelper.getInfoByName(CacheHelper.RESULT_TABLE_NAME);
        Cursor cursor = database.query(info.name, info.columns, info.columns[0] + "=" + index, null, null, null, null);
        if(cursor.moveToFirst()){
            r = Result.cursorToResult(cursor);
        }
        return r;
    }

    public LyricResult getLyricResult() {
        LyricResult result = null;
        return result;
    }

}
