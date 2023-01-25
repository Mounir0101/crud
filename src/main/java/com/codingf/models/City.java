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

public class City implements Tables {


    @Override
    public boolean create(Connection con, String table) {

        String city = "";
        String country = "";
        Scanner input = new Scanner(System.in);
        boolean exists = false;
        String country_id = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        try {

            System.out.println("Quel ville voulez vous ajouter ?");
            city = input.nextLine();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM city");

            System.out.println("Dans quel pays se trouve cette ville ?");
            country = input.nextLine();
            statement = con.createStatement();
            ResultSet countryExists = statement.executeQuery("SELECT country_id FROM country where `country` = '" + country + "'");

            if (countryExists.next()) {
                while (result.next()) {
                    if (result.getString("city").equals(city) && result.getString("country_id").equals(countryExists.getString("country_id"))) {
                        System.err.println("Cette ville existe déjà");
                        return true;
                    }
                }
            }

            else {
                System.err.println("Ce pays n'existe pas");
                System.out.println("Voulez vous créer le pays " + country + " ? (Y / N)");
                String createCountry = input.nextLine();
                if (createCountry.equals("Y") || createCountry.equals("y")) {
                    Create.create(con, "country", "country", country);
                    this.create(con, "city");
                }
                else {
                    return true;
                }
            }

            country_id = countryExists.getString("country_id");

        }

        catch (SQLException e) {
            System.err.println("Erreur" + e);
        }

        String columns = "";
        String values = "";

        System.out.println(country_id + " is the country id");

        column = Arrays.asList("city", "country_id");
        columns = String.join(",", column);
        value = Arrays.asList(city, country_id);
        values = String.join("','", value);
        Create.create(con, "city", columns, values);

        System.out.println(green + "La ville " + city + " a bien été ajouté à la table city" + reset);

        return false;

    }
}
