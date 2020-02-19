package bu.met.cs622;

import java.sql.*;

public class RealEstateDB {

    public Connection establishConnection() {
        Connection connection = null;

        try {
//          final String DATABASE_URL = "jdbc:derby:RealEstateAnalyzerDB;create=true";
            final String DATABASE_URL = "jdbc:postgresql://127.0.0.1:5432/RealEstateAnalyzerDB";

            final String DATABASE_USERNAME = "student622";
            final String DATABASE_PASSWORD = "nopasswd";

            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
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
            System.err.println("Could not drop database table...");
            e.printStackTrace();
        }
    }

    public String queryTable(String tableName) {
        String output = null;
        try {
            Connection connection = establishConnection();

//            PreparedStatement prepstmt =
//                    connection.prepareStatement("SELECT * FROM " + tableName);

            PreparedStatement prepstmt =
                    connection.prepareStatement("Select * from business ");

            ResultSet rset =  prepstmt.executeQuery();
            output = rset.getString(1);

            while (rset.next())
            {
                output += rset.getString(1);
            }


            connection.close();

        } catch (SQLException  e) {
            System.err.println("Could not query database table...");
            e.printStackTrace();
        }
        return output;
    }

    public void createBusinessTable() throws SQLException {
        // check if the table was already created
        boolean doesTableExist = doesTableExist("business");
        if (doesTableExist != true) {
            // create the table
            try {
                Connection connection = establishConnection();

                PreparedStatement prepstmt =
                        connection.prepareStatement("CREATE TABLE business(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                                "business_name varchar(30) not null, " +
                                "primary key (id))");

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
                    connection.prepareStatement("INSERT INTO business (business_name) VALUES ('test biz 1'), ('test biz 2')");

            prepstmt.executeUpdate();
            connection.close();

        } catch (SQLException  e) {
            System.err.println("Could not insert data into table...");
            e.printStackTrace();
        }
    }



}
