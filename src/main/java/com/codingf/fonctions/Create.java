package com.codingf.fonctions;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Create {

    public static void create(Connection con, String table, String column, String value) {

        try {
            //System.out.println("We got here");
            Statement statement = con.createStatement();

            //System.out.println("We create statement");

            //System.out.println("Table is " + table + ", value is " + name);

            System.out.println("INSERT INTO " + table + " (" + column + ") VALUES ('" + value + "')");

            statement.execute("INSERT INTO " + table + " (" + column + ") VALUES ('" + value + "')");

            /*ResultSet result = statement.executeQuery("SELECT * FROM country");

            while (result.next()) {
                System.out.println("Pays : " + result.getString("country"));
            }*/

        }
        catch (SQLException e) {
            System.err.println("Erreur : " + e);
        }

    }

}
