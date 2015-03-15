package paniuta.trackmywords.cache;

import android.database.Cursor;

public class Result {

    public final long resultID;
    public final String resultTitle;
    public final int resultIndex;

    public Result(long resultID, String resultTitle, int resultIndex){
        this.resultID = resultID;
        this.resultTitle = resultTitle;
        this.resultIndex = resultIndex;
    }

    public static Result cursorToResult(Cursor cursor){
        long id = cursor.getLong(0);
        String title = cursor.getString(1);
        int index = cursor.getInt(2);
        return new Result(id, title, index);
    }

}
