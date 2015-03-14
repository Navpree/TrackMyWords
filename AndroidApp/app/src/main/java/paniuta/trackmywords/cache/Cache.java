package paniuta.trackmywords.cache;

import android.content.Context;

import java.sql.SQLException;

public class Cache {

    private static CacheDAO dao;
    private static Context _context;

    public static CacheDAO getInstance(Context context) {
        if (_context != context) {
            _context = context;
            if (dao != null && dao.isOpen()) {
                try {
                    dao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dao = null;
            }
        }
        if (dao == null) {
            dao = new CacheDAO(context);
        }
        return dao;
    }

}
