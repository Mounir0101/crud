package com.codingf.models;

import com.codingf.fonctions.Create;
import com.codingf.interfaces.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Payment implements Tables {
    @Override
    public boolean create(Connection con, String table) {

        boolean exists = false;
        String customer_id = "";
        String staff_id = "";
        String rental_id = "";
        String amount = "";
        List<String> column = null;
        List<String> value = null;
        final String green = "\u001B[32m";
        final String reset = "\u001B[0m";

        try {
            Scanner input = new Scanner(System.in);

            System.out.println("Donnez l'id du client");
            customer_id = input.nextLine();
            System.out.println("Donnez l'id du manager");
            staff_id = input.nextLine();
            System.out.println("Donnez l'id de la location");
            rental_id = input.nextLine();
            System.out.println("Donnez le montant à payer");
            amount = input.nextLine();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String payment_date = dtf.format(now);

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM customer WHERE customer_id = " + customer_id);

            if (!result.next()) {
                System.err.println("Il n'existe pas de client ayant l'id " + customer_id);
                System.err.println("Vous devez d'abord créer le client correspondant");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM staff WHERE staff_id = " + staff_id);

            if (!result.next()) {
                System.err.println("Il n'existe pas de manager ayant l'id " + staff_id);
                System.err.println("Vous devez d'abord créer le manager correspondant");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM rental WHERE rental_id = " + rental_id);

            if (!result.next()) {
                System.err.println("Il n'existe pas de location ayant l'id " + rental_id);
                System.err.println("Vous devez d'abord créer la location correspondante");
                return true;
            }

            statement = con.createStatement();
            result = statement.executeQuery("SELECT * FROM payment");

            //System.out.println(rental_id);

            while (result.next()) {
                //System.out.println(result.getString("rental_id"));
                if (result.getString("rental_id") != null) {
                    if (result.getString("rental_id").equals(rental_id)) {
                        System.err.println("Cette location a déjà été payée");
                        return true;
                    }
                }
            }

            String columns = "";
            String values = "";

            column = Arrays.asList("customer_id", "staff_id", "rental_id", "amount", "payment_date");
            columns = String.join(",", column);
            value = Arrays.asList(customer_id, staff_id, rental_id, amount, payment_date);
            values = String.join("','", value);
            Create.create(con, "payment", columns, values);

            System.out.println(green + "Le paiement a bien été ajouté à la table payment" + reset);

            return false;


        }

        catch (SQLException e) {
            System.err.println("Erreur : " + e);
            return true;
        }
    }
}
