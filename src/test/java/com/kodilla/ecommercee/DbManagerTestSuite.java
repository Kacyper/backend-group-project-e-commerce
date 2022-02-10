package com.kodilla.ecommercee;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class DbManagerTestSuite {
    @Test
    public void testConnectionToDataBase() throws SQLException {
        //Given
        //When
        DbManager dbMenager = DbManager.INSTANCE;
        //Then
        assertNotNull(dbMenager.getConnection());
    }
}
