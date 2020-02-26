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
     * @param orderBy       the column to order the results by
     * @return ResultSet    returns the results of the query
     */
    public ResultSet queryTable(String tableName, String orderBy) {
        ResultSet output = null;
        try {
            Connection connection = establishConnection();

            PreparedStatement prepstmt =
                    connection.prepareStatement("SELECT * FROM " + tableName + " ORDER BY " + orderBy + " ASC");

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
    public void createBusinessAndLocationTables() throws SQLException {
        // check if the tables were already created
        boolean doesBizTableExist = doesTableExist("business");
        boolean doesLocTableExist = doesTableExist("location");

        if (!doesBizTableExist && !doesLocTableExist) {
            // try to create the tables
            try {
                Connection connection = establishConnection();

                if (connection != null) {
                    PreparedStatement prepstmt1 =
                        // if testing with Derby use the below to auto increment the id's
                        // connection.prepareStatement("CREATE TABLE business(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +

                        // create the business table
                        connection.prepareStatement("CREATE TABLE business(id serial," +
                                "business_name varchar(500) NOT NULL, " +
                                "url TEXT NOT NULL, " +
                                "distance varchar(500) NOT NULL, " +
                                "rating varchar(500) NOT NULL, " +
                                "is_closed BOOLEAN NOT NULL, " +
                                "PRIMARY KEY (id))");
                        prepstmt1.executeUpdate();

                    // create the location table with foreign key referencing business table
                    PreparedStatement prepstmt2 =
                        connection.prepareStatement("CREATE TABLE location(id serial," +
                                "business_id int NOT NULL, " +
                                "city varchar(500) NOT NULL, " +
                                "country varchar(500) NOT NULL, " +
                                "address1 varchar(500) NOT NULL, " +
                                "state varchar(500) NOT NULL, " +
                                "zip_code varchar(500) NOT NULL, " +
                                "PRIMARY KEY (id), " +
                                "FOREIGN KEY (business_id) REFERENCES business (id))");

                prepstmt2.executeUpdate();
                connection.close();
            } else {
                    System.err.println("Could not establish connection.  Table not created");
                }
            } catch (SQLException  e) {
                System.err.println("Could not create database table...");
                e.printStackTrace();
            }
        }
    }

    /**
     * Inserts data into the business and location database tables.  The location table takes a
     * foreign key from the business table.
     * @param business_name     the name of the company
     * @param url               the url opf the company's web site
     * @param distance          the distance from the investment property to businesses
     * @param rating            the Yelp star rating of the business
     * @param is_closed         is the business currently closed\open
     * @param city              the city the business is located in
     * @param country           the country the business is in
     * @param address1          the address of the business
     * @param state             the state the business is in
     * @param zipCode           the zip code of the business
     */
    public void insertBusinessAndLocationTableData(String business_name, String url, double distance, double rating,
                                                   Boolean is_closed, String city, String country, String address1,
                                                   String state, String zipCode) {
        try {
            Connection connection = establishConnection();

            // insert data into the business table.
            // return the auto generated index id's so that they can be passed to location table's foreign key
            PreparedStatement prepstmt =
                    connection.prepareStatement("INSERT INTO business (business_name, url, distance, rating, is_closed)" +
                            " VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            prepstmt.setString(1, business_name);
            prepstmt.setString(2, url);
            prepstmt.setDouble(3, distance);
            prepstmt.setDouble(4, rating);
            prepstmt.setBoolean(5, is_closed);

            prepstmt.executeUpdate(); // insert data

            ResultSet rs = prepstmt.getGeneratedKeys(); // get the foreign keys

            // insert data into the location table and set the business_id foreign key
            while (rs.next()) {
                int business_id = rs.getInt(1);

                PreparedStatement prepstmt2 =
                    connection.prepareStatement("INSERT INTO location (business_id, city, country, address1, state, zip_code)" +
                            " VALUES (?, ?, ?, ?, ?, ?)");

                    prepstmt2.setInt(1, business_id); // insert foreign key
                    prepstmt2.setString(2, city);
                    prepstmt2.setString(3, country);
                    prepstmt2.setString(4, address1);
                    prepstmt2.setString(5, state);
                    prepstmt2.setString(6, zipCode);

                    prepstmt2.executeUpdate();
            }
            connection.close();
        } catch (SQLException  e) {
            System.err.println("Could not insert data into table...");
            e.printStackTrace();
        }
    }

    /**
     * Joins both the 'Business' and 'Location' database tables and returns name and location
     * */
    public ResultSet queryBusinessAndLocationData() {
        ResultSet output = null;
        try {
            Connection connection = establishConnection();

            // Join both the business and location tables
            PreparedStatement prepstmt =
                    connection.prepareStatement("SELECT business_name, address1, rating " +
                            "FROM business, location Where business.id = location.business_id " +
                            "ORDER BY rating DESC;");

            ResultSet rset =  prepstmt.executeQuery();
            output = rset;

            while (rset.next()) {
                System.out.printf("name: %s%n", rset.getString(1)); // business_name from business table
                System.out.printf("address: %s%n", rset.getString(2)); // address from location table
                System.out.printf("rating: %s%n", rset.getString(3)); // rating from business table
            }
            connection.close();
        } catch (SQLException  e) {
            System.err.println("Could not query database table...");
            e.printStackTrace();
        }
        return output;
    }

}
