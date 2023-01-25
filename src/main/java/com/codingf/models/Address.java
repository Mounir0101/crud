package com.codingf.models;

import com.codingf.fonctions.Create;
import com.codingf.interfaces.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        try {
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
            Statement statement = con.createStatement();
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

            System.out.println(green + "Votre adresse a bien été ajoutée à la table language" + reset);

            return false;

        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
            return true;
        }
    }
}
