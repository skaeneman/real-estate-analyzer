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
        db.createBusinessTable();
        boolean exists = db.doesTableExist("business");
        assertTrue(exists);
    }

    @Test
    void dropTable() throws SQLException {
        RealEstateDB db = new RealEstateDB();
        db.createBusinessTable();
        db.dropTable("business");
    }

    @Test
    void queryTable() throws SQLException {
        RealEstateDB db = new RealEstateDB();
        db.dropTable("business");
        db.createBusinessTable();
        db.insertBusinessTableData();
        ResultSet query = db.queryTable("business");
        assertNotNull(query);
    }

    @Test
    void createBusinessTable() throws SQLException {
        RealEstateDB db = new RealEstateDB();
        db.dropTable("business");
        db.createBusinessTable();
    }

    @Test
    void insertBusinessTableData() throws SQLException {
        RealEstateDB db = new RealEstateDB();
        db.dropTable("business");
        db.createBusinessTable();
        db.insertBusinessTableData();
        ResultSet query = db.queryTable("business");
        assertNotNull(query);
    }
}