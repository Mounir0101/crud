package com.codingf.fonctions;
import java.sql.*;

public class Read {
    public static void read(Connection con, String table) {
        try {

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("select * from "+ table);

            while (result.next()){
                System.out.println(table +  " " + result.getString("country_id"));
            }
        }
        catch ( SQLException e ){
            System.out.println("erreur : "+e);

        }
    }

}
