package com.codingf.delete;

import com.codingf.Main;
import com.codingf.db.Query;


import java.sql.ResultSet;
import java.sql.SQLException;

public class Delete {
    public static void delete(String table_name, String condition) throws SQLException {
        String string = new String();
        Query selectQuery = new Query("db");

        try {
            String sql = "DELETE FROM " + table_name + " WHERE " + condition + ";";
            //" + condition + "
            System.out.println(sql);
            int result = selectQuery.executeUpdate(sql);
            System.out.println("Nombre de lignes affectées : " + result);

            ResultSet rs = selectQuery.executeQuery("SELECT * FROM" + table_name);
            while (rs.next()) {
                String city = rs.getString("country");
                System.out.println(city);
            }
        } catch (SQLException e) {
            System.out.println("Une information est incorrecte, veuillez réessayer\n" +
                    "ou le champ que vous essayez de supprimer est relié à une autre table");
        }
    }

}