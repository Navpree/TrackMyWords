package paniuta.trackmywords.cache;

public class TableInfo {

    public final String name;
    public final String[] columns;

    public TableInfo(String name, String... columns){
        this.name = name;
        this.columns = columns;
    }

}
