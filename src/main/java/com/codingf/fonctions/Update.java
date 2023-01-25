package com.codingf.fonctions;
import java.sql.*;

public class Update {
    public static void update(Connection con, String table, String columns, String values) {

        try {
            //System.out.println("We got here");
            Statement statement = con.createStatement();

            //System.out.println("We create statement");

            //System.out.println("Table is " + table + ", value is " + name);

            //System.out.println("Update" + table + "  SET (" + column + ") VALUES ('" + value + "')");

            //statement.executeUpdate("UPDATE " + table + "  SET " + columns + " = '" + values + "' WHERE id="  );

            /*ResultSet result = statement.executeQuery("SELECT * FROM country");

            while (result.next()) {
                System.out.println("Pays : " + result.getString("country"));
            }*/

        } catch (SQLException e) {
            System.err.println("Erreur : " + e);
        }
    }
}

