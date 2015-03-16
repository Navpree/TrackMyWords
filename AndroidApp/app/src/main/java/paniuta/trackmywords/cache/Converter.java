package paniuta.trackmywords.cache;

import android.database.Cursor;

import paniuta.trackmywords.beans.Song;

/**
 * Converts cursors to an existing bean or pojo model.
 */
public class Converter {

    /**
     * Converts a cursor to a Song instance.
     * @param cursor The cursort currently pointing to the row with the song data.
     * @return Returns a new instance of Song with an id and title set based on the cursor data.
     */
    public static Song cursorToResult(Cursor cursor){
        Song s = new Song();
        s.setId(cursor.getInt(0));
        s.setTitle(cursor.getString(1));
        return s;
    }

}
