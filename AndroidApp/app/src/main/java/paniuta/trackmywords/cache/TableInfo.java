package paniuta.trackmywords.cache;

/**
 * Provides cache table information such as the name of the table and
 * an array of the table's column names.
 */
public class TableInfo {

    /**
     * The name of the table in the cache db.
     */
    public final String name;

    /**
     * An array of all the column names of the corresponding db table.
     */
    public final String[] columns;

    public TableInfo(String name, String... columns){
        this.name = name;
        this.columns = columns;
    }

}
