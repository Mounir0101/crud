package com.codingf.fonctions;
import java.sql.*;

public class Delete {

    public static void delete( Connection con, String table, String column, String value){
        try {
            //ystem.out.println("We got here");
            Statement statement = con.createStatement();

            //System.out.println("We delete statement");

            System.out.println("Table is " + table + ", column is " + column + " and value is " + value);

            statement.execute("DELETE FROM " + table + " WHERE " + column + " = '" + value + "'");

            //ResultSet result = statement.executeQuery("SELECT * FROM " + table);

            //while (result.next()) {
            //    System.out.println("Pays : " + result.getString("country"));
            //}

        } catch (SQLException e) {
            System.err.println("Erreur : " + e);
        }


    }
}
