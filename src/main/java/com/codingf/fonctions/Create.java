package com.codingf.fonctions;

import java.sql.*;

public class Create {

    public static void create(Connection con, String table, String column, String value) {

        try {
            Statement statement = con.createStatement();

            System.out.println("INSERT INTO " + table + " (" + column + ") VALUES ('" + value + "')");

            statement.execute("INSERT INTO " + table + " (" + column + ") VALUES ('" + value + "')");

        }
        catch (SQLException e) {
            System.err.println("Erreur : " + e);
        }

    }

}
