package com.backend.trackmywords;

import com.backend.trackmywords.Controllers.AdminProvider;
import com.backend.trackmywords.Controllers.FileServer;
import com.backend.trackmywords.Controllers.UserProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class MainApplication extends Application {

    public Set<Class<?>> getClasses(){
        return new HashSet<Class<?>>(Arrays.asList(UserProvider.class, AdminProvider.class, FileServer.class));
    }

    public static Connection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName ("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://45.55.169.232/trackmylyrics", "root", "Z1gvmNRU43");
        return con;
    }

}
