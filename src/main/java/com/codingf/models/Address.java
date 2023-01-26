package com.codingf.models;

import com.codingf.fonctions.Create;
import com.codingf.fonctions.Read;
import com.codingf.interfaces.Tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Address implements Tables {
    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String address = "";
        String district = "";
        String postal_code = "";
        String phone = "";
        String location = "null";
        String city = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        Scanner input = new Scanner(System.in);

        System.out.println("Indiquez une adresse");
        address = input.nextLine();
        System.out.println("Indiquez une région/district");
        district = input.nextLine();
        System.out.println("Indiquez un code postal");
        postal_code = input.nextLine();
        System.out.println("Indiquez un numéro de téléphone (10 caractères max)");
        phone = input.nextLine();
        System.out.println("Indiquez l'id de la ville correspondant à l'adresse");
        city = input.nextLine();


        // Create an instance of the SQLXML class
            /*SQLXML sqlxml = con.createSQLXML();

            // Create a SQL geometry object
            sqlxml.setString("ST_GeomFromText('POINT(1 1)', 4326)");

            //Use the SQLXML object as parameter in sql statement

            location = sqlxml.getString();

            PreparedStatement stmt = con.prepareStatement("INSERT INTO mytable (geom) VALUES (?)");
            stmt.setSQLXML(1,sqlxml);
            stmt.executeUpdate();*/

        String wkt = "POINT(1 1)";

        // Use the ST_GeomFromText function to parse the string representation and create a binary representation
        String sql = "INSERT INTO mytable (geom) VALUES (ST_GeomFromText(?, 4326))";

        location = "ST_GeomFromText(" + wkt + ", 4326)";

            /*Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT location FROM address where address_id = 1");

            if (result.next()) {
                location = result.getString("location");
            }



            /*while (result.next()) {
                if (result.getString("name").equals(language)) {
                    System.err.println("Cette langue existe déjà");
                    return true;
                }
            }*/

        String columns = "";
        String values = "";

        column = Arrays.asList("address, district, city_id, postal_code, phone, location");
        columns = String.join(",", column);
        value = Arrays.asList(address, district, city, postal_code, phone, location);
        values = String.join("','", value);
        Create.create(con, "address", columns, values);

        System.out.println(green + "Votre adresse a bien été ajoutée à la table address" + reset);

        return false;

    }

    @Override
    public void read(Connection con, String table) {

        try {
            Statement stmt = con.createStatement();

            ResultSet country_table = stmt.executeQuery("SELECT * FROM " + table);

            System.out.println();

            List<String> column_list = new ArrayList<>();

            column_list.add("address_id");
            column_list.add("address");
            column_list.add("address_2");
            column_list.add("district");
            column_list.add("city_id");
            column_list.add("postal_code");
            column_list.add("phone");
            column_list.add("location");
            column_list.add("last_update");

            Read.read(con, table, country_table, column_list);
        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
        }
    }
}
