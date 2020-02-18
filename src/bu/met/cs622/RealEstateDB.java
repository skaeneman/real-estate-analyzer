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


    public boolean doesTableExist(String tableName) throws SQLException {
        Connection connection = establishConnection();
        if (connection != null) {
            // check if the table already exists
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(),null);

            if (rs.next()) {
                System.out.println("The table named: " + rs.getString("TABLE_NAME") + " was already created...");
                connection.close();
                return true;
            }
        }
        connection.close();
        return false;
    }


    public void dropTable(String tableName) {
        try {
            Connection connection = establishConnection();

            PreparedStatement prepstmt =
                    connection.prepareStatement("DROP TABLE " + tableName);

            prepstmt.executeUpdate();
            connection.close();

        } catch (SQLException  e) {
            System.err.println("Could not create database table...");
            e.printStackTrace();
        }
    }


    public void createBusinessTable() throws SQLException {
        // check if the table was already created
        boolean doesTableExist = doesTableExist("business");
        if (doesTableExist != true) {
            // create the table
            try {
                Connection connection = establishConnection();

                PreparedStatement prepstmt =
                        connection.prepareStatement("CREATE TABLE business(id int NOT NULL, business_name varchar(30) not null, primary key (id))");

                prepstmt.executeUpdate();
                connection.close();

            } catch (SQLException  e) {
                System.err.println("Could not create database table...");
                e.printStackTrace();
            }
        }
    }

    public void insertBusinessTableData() {
        try {
            Connection connection = establishConnection();
            PreparedStatement prepstmt =
                    connection.prepareStatement("INSERT INTO business VALUES (id 1, business_name 'test biz 1'), (id 2, business_name 'test biz 2')");

            prepstmt.executeUpdate();
            connection.close();

        } catch (SQLException  e) {
            System.err.println("Could not insert data into table...");
            e.printStackTrace();
        }
    }



}
