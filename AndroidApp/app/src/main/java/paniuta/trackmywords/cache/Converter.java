package paniuta.trackmywords.cache;

import android.database.Cursor;

import paniuta.trackmywords.beans.Song;

public class Converter {

    public static Song cursorToResult(Cursor cursor){
        Song s = new Song();
        s.setId(cursor.getInt(0));
        s.setTitle(cursor.getString(1));
        return s;
    }

}
