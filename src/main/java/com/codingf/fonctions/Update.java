package com.codingf.fonctions;
import java.sql.*;

public class Update {
    public static void update(Connection con, String table, String column, String value) {

        try {
            //System.out.println("We got here");
            Statement statement = con.createStatement();

            statement.executeUpdate("UPDATE " + table + "  SET " + column + " = '" + value + "' WHERE country=" );

        } catch (SQLException e) {
            System.err.println("Erreur : " + e);
        }
    }
}

