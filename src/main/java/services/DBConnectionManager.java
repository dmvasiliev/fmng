package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by vasiliev on 5/31/2017.
 */
public class DBConnectionManager {

    private Connection connection;

    public DBConnectionManager(String dbURL, String user, String pwd) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        this.connection = DriverManager.getConnection(dbURL, user, pwd);
    }

    public Connection getConnection() {
        return this.connection;
    }
}
