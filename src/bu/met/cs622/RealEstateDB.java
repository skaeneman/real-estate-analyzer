package bu.met.cs622;

import com.google.gson.annotations.SerializedName;

import java.sql.*;

public class RealEstateDB {

    /**
     * establishes a connection to the PostgreSQL database instance
     */
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
        System.out.printf(connection.toString());
        return connection;
    }

    /**
     * Checks if a database table exists in the database
     * @param tableName     the name of the database table to check
     * @return boolean      returns a boolean value of true if a table exists
     */
    public boolean doesTableExist(String tableName) throws SQLException {
        Connection connection = establishConnection();
        if (connection != null) {

             // check if the table already exists
             DatabaseMetaData dbmd = connection.getMetaData();

            // use this for Derby, table name needs to be capitalized
            // ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(),null);

            ResultSet rs = dbmd.getTables(null, null, tableName,null);

            if (rs.next()) {
                System.out.println("The table named: " + rs.getString("TABLE_NAME") + " was already created...");
                connection.close();
                return true;
            }
        }
        connection.close();
        return false;
    }

    /**
     * Drops a database table from the database if it exists
     * @param tableName     the name of the database table to drop
     */
    public void dropTable(String tableName) throws SQLException {
        // check if the table exists before trying to drop it
        boolean doesTableExist = doesTableExist(tableName);
        if (doesTableExist) {
            try {
                Connection connection = establishConnection();

                PreparedStatement prepstmt =
                        connection.prepareStatement("DROP TABLE " + tableName);

                prepstmt.executeUpdate();
                connection.close();

            } catch (SQLException e) {
                System.err.println("Could not drop database table...");
                e.printStackTrace();
            }
        } else {
            System.out.println(tableName + " table does not exist, cannot drop...");
        }
    }

    /**
     * Querries a database table
     * @param tableName     the name of the database table to query
     * @return ResultSet      returns the results of the query
     */
    public ResultSet queryTable(String tableName) {
        ResultSet output = null;
        try {
            Connection connection = establishConnection();

//            PreparedStatement prepstmt =
//                    connection.prepareStatement("SELECT * FROM " + tableName);

            PreparedStatement prepstmt =
                    connection.prepareStatement("SELECT * FROM " + tableName);

            ResultSet rset =  prepstmt.executeQuery();
            output = rset;

            while (rset.next()) {
                System.out.printf("id: %s%n", rset.getString(1)); // id
                System.out.printf("name: %s%n", rset.getString(2)); // business_name
            }
            connection.close();
        } catch (SQLException  e) {
            System.err.println("Could not query database table...");
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Creates a database table called 'Business'
\    */
    public void createBusinessTable() throws SQLException {
        // check if the table was already created
        boolean doesTableExist = doesTableExist("business");
        if (doesTableExist != true) {
            // create the table
            try {
                Connection connection = establishConnection();

                PreparedStatement prepstmt =
                        // if testing with Derby use the below to auto increment the id's
//                      connection.prepareStatement("CREATE TABLE business(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +

                        connection.prepareStatement("CREATE TABLE business(id serial," +
                                "business_name varchar(30) not null, " +
                                "url varchar(30) not null, " +
                                "distance varchar(30) not null, " +
                                "rating varchar(30) not null, " +
                                "is_closed BOOLEAN not null, " +
                                "primary key (id))");

                prepstmt.executeUpdate();
                connection.close();

            } catch (SQLException  e) {
                System.err.println("Could not create database table...");
                e.printStackTrace();
            }
        }
    }

    /**
     * inserts data into the business database table
     */
    public void insertBusinessTableData() {
        try {
            Connection connection = establishConnection();
            PreparedStatement prepstmt =
                    connection.prepareStatement("INSERT INTO business (business_name, url, distance, rating, is_closed)" +
                            " VALUES ('test biz1', 'http://biz1.com', '1.65543', '4.5', FALSE), ('test biz2', 'http://biz2.com', '3.343', '5', TRUE)");

            prepstmt.executeUpdate();
            connection.close();

        } catch (SQLException  e) {
            System.err.println("Could not insert data into table...");
            e.printStackTrace();
        }
    }



}
