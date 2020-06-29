package vgs;


import java.sql.*;

/**
 * This application for Video Game Services (VGS) will be used as an interface to display a user menu that will
 * load the VGS database and allow the user to run various related actions on the given database, implemented with
 * JDBC.
 *
 * @author Adam Clifton (akclifto@asu.edu)
 * @author Anne Landrum (aelandru@asu.edu)
 * @author Ivan Fernandez (iafernan@asu.edu)
 * @author Robert Ibarra (rnibarra@asu.edu)
 *
 * SER 322 Project Team 2 Deliverable 4
 * @version 2020.06.29
 * */
public class Application {

    //TODO:
    private static ResultSet rs = null;
    private static Statement stmt = null;
    private static Connection conn = null;


    /**
     * Entry point of the program.
     *
     * @param args : args specified by user, if any.
     * @return void.
     * */
    public static void main(String[] args) {

        //TODO: if using CLI for app can use initial args from cli launch for url, user, pdw, driver

        System.out.println("---------------------------------------------------");
        System.out.println("*** Welcome to the Video Game Services Database ***");
        System.out.println("---------------------------------------------------\n");
        System.out.print("Connecting to server and loading database...");

        String _url = args[0];

        //TODO: use the VGS-names sequel files in order: (1) create, (2) insert, and (3) altertable
        try {
            // load the JDBC Driver
            Class.forName(args[3]);

            //make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

        } catch (ClassNotFoundException | SQLException se) {
            System.out.println("Error load the JDBC Driver and/or connecting to server.");
            se.printStackTrace();
        }


        System.out.println("done!\n");

        System.out.println("Main Menu: ");
        // TODO: options: write a query, add to the db, remove from the db?, use default queries from select.sql file.

        // TODO: user inputs, then send off to either other class or methods.+


    }

}
