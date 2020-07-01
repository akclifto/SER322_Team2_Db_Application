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
        //manual queries
        String query;
        int selection;

        do {
            System.out.println("Please enter the integer associated with the table that you want to query:");
            System.out.println("1. Genre, 2. Title, 3. Platform, 4. Platform Type, 5. Account, 6. Customer, 7. User_Library");
            System.out.println("8. Save_Game_File, 9. Logs_Into");
            while (!scan.hasNextInt()) {
                System.out.println("That's not a number!");
                scan.next();
            }
            selection = scan.nextInt();
        }while(selection < 1 || selection > 9);
        System.out.println("Selection was successful!");

        System.out.println("Please enter a SQL Query: ");
        // get user input
        query = scan.nextLine();

        try {
            // Create a statement
            stmt = conn.createStatement();

            // Setup a query
            rs = stmt.executeQuery(query);

            // Display the results
            switch (selection) {
                case 1:
                    while (rs.next()) {
                        System.out.print(rs.getString(1) + "\t");
                        System.out.println(rs.getString(2));
                    }
                    break;

                case 2:
                    while (rs.next()) {
                        System.out.print(rs.getLong(1) + "\t");
                        System.out.print(rs.getString(2) + "\t");
                        System.out.print(rs.getString(3) + "\t");
                        System.out.print(rs.getInt(4) + "\t");
                        System.out.print(rs.getString(5) + "\t");
                        System.out.print(rs.getDouble(6) + "\t");
                        System.out.print(rs.getString(7) + "\t");
                        System.out.println(rs.getString(8));

                    }
                    break;

                case 3:
                    while (rs.next()) {
                        System.out.print(rs.getLong(1) + "\t");
                        System.out.print(rs.getLong(2)+ "\t");
                        System.out.println(rs.getString(3));
                    }
                    break;

                case 4:
                    while (rs.next()) {
                        System.out.print(rs.getString(1) + "\t");
                        System.out.println(rs.getLong(2));
                    }
                    break;

                case 5:
                    while (rs.next()) {
                        System.out.print(rs.getString(1) + "\t");
                        System.out.print(rs.getString(2) + "\t");
                        System.out.print(rs.getLong(3)+ "\t");
                        System.out.print(rs.getBoolean(4) + "\t");
                        System.out.print(rs.getString(5) + "\t");
                        System.out.println(rs.getLong(6));

                    }
                    break;

                case 6:
                    while (rs.next()) {
                        System.out.print(rs.getString(1) + "\t");
                        System.out.print(rs.getString(2) + "\t");
                        System.out.print(rs.getString(3) + "\t");
                        System.out.print(rs.getString(4) + "\t");
                        System.out.print(rs.getDate(5) + "\t");
                        System.out.print(rs.getString(6) + "\t");
                        System.out.println(rs.getString(7));

                    }
                    break;

                case 7:
                    while (rs.next()) {
                        System.out.print(rs.getLong(1) + "\t");
                        System.out.print(rs.getString(2) + "\t");
                        System.out.print(rs.getString(3) + "\t");
                        System.out.print(rs.getDate(4) + "\t");
                        System.out.println(rs.getDate(5));

                    }
                    break;

                case 8:
                    while (rs.next()) {
                        System.out.print(rs.getLong(1) + "\t");
                        System.out.print(rs.getString(2) + "\t");
                        System.out.print(rs.getString(3) + "\t");
                        System.out.print(rs.getTime(4) + "\t");
                        System.out.print(rs.getTime(5) + "\t");
                        System.out.println(rs.getLong(6));

                    }
                    break;

                case 9:
                    while (rs.next()) {
                        System.out.print(rs.getString(1) + "\t");
                        System.out.print(rs.getString(2) + "\t");
                        System.out.println(rs.getString(3));

                    }
                    break;

            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
        finally {  // ALWAYS clean up DB resources
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    /**
     * Method to run select.sql presets.
     *
     * @param scan : scanner for user input.
     * @return void.
     */
    private static void selectPreset(Scanner scan) {
        //select presets

        String query1 ="SELECT PLATFORM.Platform_Name FROM PLATFORM, PLATFORM_TYPE WHERE PLATFORM.Platform_Name = PLATFORM_TYPE.Platform_Name";

        String query2 = "SELECT distinct TITLE.Title_Name, TITLE.Description, TITLE.ESRB_Rating, PLATFORM.Platform_Name" +
                      "FROM TITLE, PLATFORM ORDER BY TITLE.Title_Name ASC";

        String query3 = "SELECT ACCOUNT.Member_ID, CUSTOMER.First_Name, CUSTOMER.Last_Name, CUSTOMER.Birthdate, " +
                        "ACCOUNT.Credit_Card_Number, ACCOUNT.Subscrip2tion, ACCOUNT.Email, PLATFORM.Platform_Name" +
                        "FROM ACCOUNT, PLATFORM, CUSTOMER WHERE ACCOUNT.Member_ID = CUSTOMER.Member_ID ORDER BY ACCOUNT.Member_ID ASC";

        String query4 ="SELECT USER_LIBRARY.UPC, USER_LIBRARY.Member_ID, USER_LIBRARY.Date_Added, USER_LIBRARY.Date_Removed, " +
                        "TITLE.Title_Name, TITLE.Description, TITLE.Year_Of_Release, TITLE.ESRB_Rating, TITLE.Metacritic_Rating, " +
                        "TITLE.Genre_Name, TITLE.Genre_Description, PLATFORM.Platform_Name FROM USER_LIBRARY, PLATFORM, TITLE " +
                        "WHERE USER_LIBRARY.UPC = TITLE.UPC AND PLATFORM.UPC = TITLE.UPC";

        String query5 ="SELECT acct.Member_ID, t.Title_Name FROM TITLE t JOIN USER_LIBRARY ul ON ul.UPC = t.UPC " +
                       "JOIN ACCOUNT acct ON acct.Member_ID = ul.Member_ID WHERE acct.Member_ID = 'TheCrow'";

        String query6 ="SELECT acct.Member_ID, t.Title_Name, p.Platform_Name FROM  TITLE t JOIN USER_LIBRARY ul ON ul.UPC = t.UPC " +
                       "JOIN SAVE_GAME_FILE sgf ON sgf.UPC = ul.UPC JOIN PLATFORM p ON p.Serial_Number = sgf.Serial_Number  " +
                        "JOIN ACCOUNT acct ON acct.Member_ID = ul.Member_ID WHERE acct.Member_ID = 'TheDestroyer85' AND t.UPC = sgf.UPC " +
                        "GROUP BY t.Title_Name, p.Platform_Name";

        int selection;

        do {
        System.out.println("Please enter the integer associated with a preset Query");
        System.out.println("1. " + query1);
        System.out.println("2. " + query2);
        System.out.println("3. " + query3);
        System.out.println("4. " + query4);
        System.out.println("5. " + query5);
        System.out.println("6. " + query6);
        //get user input and validate selection
            while (!scan.hasNextInt()) {
                System.out.println("That's not a number!");
                scan.next();
            }
            selection = scan.nextInt();
        }while(selection < 1 || selection > 6);
        System.out.println("Selection was successful!");

        try {
            // Create a statement
            stmt = conn.createStatement();


            // Display the results
            switch (selection) {
                case 1:

                    // Setup a query
                    rs = stmt.executeQuery(query1);

                    while (rs.next()) {
                        System.out.print(rs.getString(1) + "\t");

                    }
                    break;

                case 2:

                    // Setup a query
                    rs = stmt.executeQuery(query2);

                    while (rs.next()) {
                        System.out.print(rs.getString(1) + "\t");
                        System.out.print(rs.getString(2) + "\t");
                        System.out.print(rs.getString(3) + "\t");
                        System.out.print(rs.getString(4) + "\t");


                    }
                    break;

                case 3:
                    // Setup a query
                    rs = stmt.executeQuery(query3);

                    while (rs.next()) {
                        System.out.print(rs.getString(1) + "\t");
                        System.out.print(rs.getString(2)+ "\t");
                        System.out.print(rs.getString(3)+ "\t");
                        System.out.print(rs.getDate(4)+ "\t");
                        System.out.print(rs.getLong(5)+ "\t");
                        System.out.print(rs.getBoolean(6)+ "\t");
                        System.out.print(rs.getString(7)+ "\t");
                        System.out.print(rs.getString(8)+ "\t");
                    }
                    break;

                case 4:
                    // Setup a query
                    rs = stmt.executeQuery(query4);
                    while (rs.next()) {
                        System.out.print(rs.getLong(1) + "\t");
                        System.out.print(rs.getString(2) + "\t");
                        System.out.print(rs.getDate(3)+ "\t");
                        System.out.print(rs.getDate(4)+ "\t");
                        System.out.print(rs.getString(5) + "\t");
                        System.out.print(rs.getString(6) + "\t");
                        System.out.print(rs.getDate(7)+ "\t");
                        System.out.print(rs.getString(8) + "\t");
                        System.out.print(rs.getDouble(9) + "\t");
                        System.out.print(rs.getString(10) + "\t");
                        System.out.print(rs.getString(11) + "\t");
                        System.out.print(rs.getString(12) + "\t");
                    }
                    break;

                case 5:
                    // Setup a query
                    rs = stmt.executeQuery(query5);
                    while (rs.next()) {
                        System.out.print(rs.getString(1) + "\t");
                        System.out.print(rs.getString(2) + "\t");

                    }
                    break;

                case 6:
                    // Setup a query
                    rs = stmt.executeQuery(query6);
                    while (rs.next()) {
                        System.out.print(rs.getString(1) + "\t");
                        System.out.print(rs.getString(2) + "\t");
                        System.out.print(rs.getString(3) + "\t");

                    }
                    break;

            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
        finally {  // ALWAYS clean up DB resources
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException se) {
                se.printStackTrace();
            }
        }



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
     * @return void.
     * */
    private static void getUserInput() {

        //show menu options
        System.out.println("Main Menu: ");
        System.out.println("1 - Write your own query");
        System.out.println("2 - Use preset queries provided with application");
        System.out.println("3 - Insert data into database");
        System.out.println("4 - Remove data from database"); //TODO:  maybe do this one?
        System.out.println("Q - Exit Program\n");
        System.out.print("Please select an option: ");

        //set scanner and get input
        try (Scanner scan = new Scanner(System.in)) {

            //user inputs, then send off to either other methods.
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
