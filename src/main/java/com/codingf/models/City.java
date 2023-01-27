package com.codingf.models;

import com.codingf.fonctions.Create;
import com.codingf.fonctions.Read;
import com.codingf.interfaces.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class City implements Tables {


    @Override
    public boolean create(Connection con, String table) {

        String city = "";
        String country_id = "";

        Scanner input = new Scanner(System.in);
        boolean exists = false;
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        try {

            System.out.println("Quel ville voulez vous ajouter ?");
            city = input.nextLine();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM city");

            System.out.println("Donnez l'id du pays dans lequel cette ville se trouve");
            country_id = input.nextLine();
            statement = con.createStatement();
            ResultSet countryExists = statement.executeQuery("SELECT country_id FROM country where `country_id` = '" + country_id + "'");

            if (!countryExists.next()) {
                System.err.println("Il n'existe pas de pays ayant l'id " + country_id);
                System.err.println("Vous devez d'abord créer le pays correspondant pour pouvoir créer cette ville");
                return true;
            }

            while (result.next()) {
                if (result.getString("city").equals(city) && result.getString("country_id").equals(countryExists.getString("country_id"))) {
                    System.err.println("Cette ville existe déjà");
                    return true;
                }
            }

            country_id = countryExists.getString("country_id");

            String columns = "";
            String values = "";

            column = Arrays.asList("city", "country_id");
            columns = String.join(",", column);
            value = Arrays.asList(city, country_id);
            values = String.join("','", value);
            Create.create(con, "city", columns, values);

            System.out.println(green + "La ville " + city + " a bien été ajouté à la table city" + reset);

            return false;

        }

        catch (SQLException e) {
            System.err.println("Erreur" + e);
            return true;
        }

    }

}
