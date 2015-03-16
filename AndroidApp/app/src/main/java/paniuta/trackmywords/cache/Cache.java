package paniuta.trackmywords.cache;

import android.content.Context;

import java.sql.SQLException;

/**
 * Cache class is used to keep a static reference to the CacheDAO.
 */
public class Cache {

    private static CacheDAO dao;
    private static Context _context;

    /**
     * Takes in the context of the current activity and either returns an existing instance
     * of the CacheDAO or, if a new context is found, creates a new instance with that context.
     * @param context The context of the current activity.
     * @return A new or existing instance of the CacheDAO.
     */
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
