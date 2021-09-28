package test;

import main.Controller;
import main.Database;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class unitTest {
    Database database = new Database();
    Connection connection;
    String ID = "2";
    String Result;

    @Before
    public void init() throws SQLException {
        // this runs before the test
        // test vanda aagadi database connect garna ko lagi
        Connection connection = database.getConnection();
        PreparedStatement setHistory = connection.prepareStatement("select * from history where ID  = ?");
        setHistory.setString(1, ID);
        ResultSet resultset = setHistory.executeQuery();
        if (resultset.next()){
            Result = resultset.getString("result");
        }
        setHistory.close();
    }
    @Test
    public void checkRecentResult(){
        // expected
        String expectedResult= "2.0";
        assertEquals(expectedResult, Result);
    }

    @Test
    public void checkInput(){
        //expected
        String givenInput = "1";
        assertEquals(givenInput, Controller.getResult());
    }


}
