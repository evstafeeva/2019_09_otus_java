package ru.otus.bestorm;


import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;


public class DBTest {
    private Connection connection;

    @Test
    public void userTest() throws Exception {
        connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/bestorm?&serverTimezone=UTC", "root", "root");
        Registrar registrar = new Registrar(Arrays.asList(User.class), connection);
    }

    @Test
    public void foreignKeyTest() throws Exception {
        connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/bestorm?&serverTimezone=UTC", "root", "root");
        Registrar registrar = new Registrar(Arrays.asList(User.class), connection);
    }
}
