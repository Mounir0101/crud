package com.codingf.models;

import com.codingf.fonctions.Create;
import com.codingf.interfaces.Tables;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Staff implements Tables {
    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String first_name = "";
        String last_name = "";
        String address = "";
        String email = "";
        String store_id = "";
        String username = "";
        String password = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        try {
            Scanner input = new Scanner(System.in);

            System.out.println("Indiquez votre prénom");
            first_name = input.nextLine();
            System.out.println("Indiquez votre nom");
            last_name = input.nextLine();
            System.out.println("Indiquez l'id de votre adresse");
            address = input.nextLine();
            System.out.println("Indiquez votre adresse mail");
            email = input.nextLine();
            System.out.println("Indiquez l'id de votre magasin");
            store_id = input.nextLine();
            System.out.println("Indiquez votre pseudo");
            username = input.nextLine();
            System.out.println("Indiquez votre mot de passe");
            password = input.nextLine();

            /*try {
                MessageDigest msg = MessageDigest.getInstance("SHA-256");
                byte[] hash = msg.digest(password.getBytes(StandardCharsets.UTF_8));
                // convertir bytes en hexadécimal
                StringBuilder s = new StringBuilder();
                for (byte b : hash) {
                    s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
                }
                password = s.toString();
            }
            catch (NoSuchAlgorithmException e) {
                System.err.println("Erreur : " + e);

        }*/

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM staff");

            while (result.next()) {
                if (result.getString("first_name").equals(first_name) && result.getString("last_name").equals(last_name)) {
                    System.err.println("Cette personne est déjà enregistrée");
                    return true;
                }
            }

            String columns = "";
            String values = "";

            column = Arrays.asList("first_name", "last_name", "address_id", "email", "store_id", "username", "password");
            columns = String.join(",", column);
            value = Arrays.asList(first_name, last_name, address, email, store_id, username, password);
            values = String.join("','", value);
            Create.create(con, "staff", columns, values);

            System.out.println(green + "Le staff " + first_name + " " + last_name + " a bien été ajouté à la table staff" + reset);

            return false;


        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
            return true;
        }
    }
}
