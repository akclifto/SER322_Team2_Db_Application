package vgs;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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


        System.out.println("---------------------------------------------------");
        System.out.println("*** Welcome to the Video Game Services Database ***");
        System.out.println("---------------------------------------------------\n");
        System.out.print("Connecting to server and loading database...");

        //TODO: try/catch connection and load db


        System.out.println("done!\n");

        System.out.println("Main Menu: ");
        // TODO: write a query, add to the db, remove from the db?, use default queries from select.sql file.

        // TODO: user inputs.

    }

}
