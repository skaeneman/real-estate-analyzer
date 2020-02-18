package bu.met.cs622;

import java.sql.*;

public class RealEstateDB {

    public Connection establishConnection() {
        Connection connection = null;

        try {
            final String DATABASE_URL = "jdbc:derby:RealEstateAnalyzerDB;create=true";

            final String DATABASE_USERNAME = "student622";
            final String DATABASE_PASSWORD = "nopasswd";

            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException  e) {
            System.err.println("Could not create database connection...");
            e.printStackTrace();
        }
        return connection;
    }

    public void createBusinessTable() {
        try {
            Connection connection = establishConnection();
            PreparedStatement prepstmt =
                    connection.prepareStatement("CREATE TABLE IF NOT EXISTS business(id int NOT NULL AUTO_INCREMENT, name varchar(30) not null, primary key (id))");

            prepstmt.executeUpdate();

        } catch (SQLException  e) {
            System.err.println("Could not create database table...");
            e.printStackTrace();
        }
    }


}
