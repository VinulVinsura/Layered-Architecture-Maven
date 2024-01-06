package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static DBconnection dBconnection;
    private Connection connection;

    private DBconnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade","root","*8MA1SW#RI@%");


    }
    public static DBconnection getInstance() throws ClassNotFoundException, SQLException {
        return dBconnection != null ? dBconnection : (dBconnection=new DBconnection());
    }
    public Connection getConnection(){
        return connection;
    }
}
