package com.codingf.fonctions;

import java.sql.*;

public class Create {

    public static void create(Connection con, String table, String name) {

        try {
            System.out.println("We got here");
            Statement statement = con.createStatement();

            System.out.println("We create statement");

            System.out.println("Table is " + table + ", value is " + name);

            statement.execute("INSERT INTO " + table + "(country) VALUES('" + name + "')");

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
