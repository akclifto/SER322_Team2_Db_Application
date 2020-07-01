package vgs;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * This application for Video Game Services (VGS) will be used as an interface to display a user menu that will
 * load the VGS database and allow the user to run various related actions on the given database, implemented with
 * JDBC.
 * <p>
 *
 * @author Adam Clifton (akclifto@asu.edu)
 * @author Anne Landrum (aelandru@asu.edu)
 * @author Ivan Fernandez (iafernan@asu.edu)
 * @author Robert Ibarra (rnibarra@asu.edu)
 * <p>
 * SER 322 Project Team 2 Deliverable 4
 * @version 2020.06.29
 */
public class Application {

    //TODO:
    private static ResultSet rs = null;
    private static Statement stmt = null;
    private static Connection conn = null;

    /**
     * Method to run manual queries on database.
     *
     * @param scan : scanner for user input.
     * @return void.
     */
    private static void writeQuery(Scanner scan) {
        //TODO: manual queries
        System.out.println("placeholder writeQuery");

        //after method finished, get user input
        getUserInput();
    }

    /**
     * Method to run select.sql presets.
     *
     * @param scan : scanner for user input.
     * @return void.
     */
    private static void selectPreset(Scanner scan) {
        //TODO: select presets
        System.out.println("placeholder selectPreset");

        //after method finished, get user input
        getUserInput();
    }

    /**
     * Method to insert into database.
     *
     * @param scan : scanner for user input.
     * @return void.
     */
    private static void insertData(Scanner scan) {
        //TODO: insert manual data
        System.out.println("placeholder insertData");

        //after method finished, get user input
        getUserInput();
    }

    /**
     * Method to remove data from database.
     *
     * @param scan : scanner for user input.
     * @return void.
     */
    private static void removeData(Scanner scan) {
        //TODO: remove data with query command
        System.out.println("placeholder removeData");

        //after method finished, get user input
        getUserInput();
    }


    /**
     * Method to exit program and close the scanner.
     *
     * @param scan : scanner to close
     * @return void.
     */
    private static void exitProgram(Scanner scan) {

        System.out.println("Exiting Program.");
        scan.close();
    }

    /**
     * Method to get input for application via CLI.  Method show main menu and passes arguments based on user input.
     *
     * @return void.
     */
    private static void getUserInput() {

        //show menu options
        System.out.println("Main Menu: ");
        System.out.println("1 - Write your own query");
        System.out.println("2 - Use preset queries provided with application"); //TODO: use default _select.sql
        System.out.println("3 - Insert data into database");
        System.out.println("4 - Remove data from database"); //TODO:  maybe do this one?
        System.out.println("Q - Exit Program\n");
        System.out.print("Please select an option: ");

        //set scanner and get input
        try (Scanner scan = new Scanner(System.in)) {

            // TODO: user inputs, then send off to either other class or methods.
            while (scan.hasNext()) {

                String in = scan.next();

                if (in.equals("1")) {

                    writeQuery(scan);
                } else if (in.equals("2")) {

                    selectPreset(scan);
                } else if (in.equals(("3"))) {

                    insertData(scan);
                } else if (in.equals(("4"))) {

                    removeData(scan);
                } else if (in.equalsIgnoreCase(("Q"))) {

                    exitProgram(scan);
                    break;
                } else {

                    System.out.println("Invalid user input.  Please choose a valid option.\n");
                    getUserInput();
                }
            }
        } catch (Exception ex) {
            System.out.println("There was a problem getting user input.");
            ex.printStackTrace();
        }
    }

    /**
     * Entry point of the program. Loads database and passes to get user input; closes all connections on exit.
     *
     * @param args : args specified by user, includes sql server url, username, password, and jdbc driver.
     * @return void.
     */
    public static void main(String[] args) {

        //TODO: if using CLI for app can use initial args from cli launch for url, user, pw, driver

        System.out.println("---------------------------------------------------");
        System.out.println("*** Welcome to the Video Game Services Database ***");
        System.out.println("---------------------------------------------------\n");
        System.out.print("Connecting to server and loading database...");

        String _url = args[0];

        try {
            // load the JDBC Driver
            Class.forName(args[3]);

            //make a connection
            conn = DriverManager.getConnection(_url, args[1], args[2]);

        } catch (ClassNotFoundException | SQLException se) {
            System.out.println("Error load the JDBC driver and/or connecting to server.");
            se.printStackTrace();
        }
        System.out.println("done!\n");

        // show menu and get user input
        try {
            getUserInput();
        } catch (Exception e) {
            e.printStackTrace();
            // close everything related to sql server
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    System.out.println("ResultSet closed!");
                }
                if (stmt != null) {
                    stmt.close();
                    System.out.println("Query Statement closed!");
                }
            } catch (SQLException se) {
                System.out.println("There is a problem closing database resources!");
                se.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Connection closed!");
                }
            } catch (Throwable se) {
                System.out.println("There is a leak!");
                se.printStackTrace();
            }
        }
    }
}
