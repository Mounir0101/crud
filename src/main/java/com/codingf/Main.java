package com.codingf;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
        String host = "localhost";
        String dbname = "sakila";
        int port = 3306;

        String URL = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
        String username = "root";
        String password = "";

        Connection connection = DriverManager.getConnection(URL, username, password);
        if (connection == null) {
            System.out.println("Erreur de connexion !!");
        }else {
            System.out.println("connexion good !!");

        }
        Scanner nb = new Scanner(System.in);
        int table;

        while (true) {

            System.out.println("1: Country");
            System.out.println("2: City");
            System.out.println("Quelle table voulez vous choisir ?");

            String input = nb.nextLine();

            try {
                table = Integer.parseInt(input);
                break;
            }
            catch (Exception e) {
                System.err.println("nombre incorrect");
            }

        }

        switch (table) {

        }

        int choice;

        while (true) {
            System.out.println("1: Créer");
            System.out.println("2: Lire");
            System.out.println("3: Mettre à jour");
            System.out.println("4: Supprimer");
            System.out.println("que voulez vous faire avec cette table ?");

            String input = nb.nextLine();

            try {
                choice = Integer.parseInt(input);
                break;
            }
            catch (Exception e) {
                System.err.println("nombre incorrect");
            }
        }


        switch (table) {

            case 1:


        }

    }
}