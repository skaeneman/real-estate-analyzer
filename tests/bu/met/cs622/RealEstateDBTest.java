package bu.met.cs622;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RealEstateDBTest {

    @Test
    void establishConnection() {
        RealEstateDB db = new RealEstateDB();
        Connection isConnected = db.establishConnection();
        assertNotNull(isConnected);
    }

    @Test
    void doesTableExist() throws SQLException {
        RealEstateDB db = new RealEstateDB();
        db.dropTable("business");
        db.createBusinessAndLocationTables();
        boolean exists = db.doesTableExist("business");
        assertTrue(exists);
    }

    @Test
    void dropTable() throws SQLException {
        RealEstateDB db = new RealEstateDB();
        db.createBusinessAndLocationTables();
        db.dropTable("business");
    }

    @Test
    void queryTable() throws SQLException {
        RealEstateDB db = new RealEstateDB();
        db.dropTable("business");
        db.createBusinessAndLocationTables();
        db.insertBusinessAndLocationTableData("test biz1", "http://biz2.com", 4.23, '5', false);
        ResultSet query = db.queryTable("business");
        assertNotNull(query);
    }

    @Test
    void createBusinessTable() throws SQLException {
        RealEstateDB db = new RealEstateDB();
        db.dropTable("business");
        db.createBusinessAndLocationTables();
    }

    @Test
    void insertBusinessTableData() throws SQLException {
        RealEstateDB db = new RealEstateDB();
        db.dropTable("business");
        db.createBusinessAndLocationTables();
        db.insertBusinessAndLocationTableData("test biz2", "http://biz2.com", 3.23, '5', true);
        ResultSet query = db.queryTable("business");
        assertNotNull(query);
    }
}