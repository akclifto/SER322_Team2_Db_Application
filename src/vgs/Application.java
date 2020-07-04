package vgs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
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
 * @version 2020.07.01
 */
public class Application {

    private static ResultSet rs = null;
    private static Statement stmt = null;
    private static Connection conn = null;


    /**
     * Method to run manual queries on database.  The user will type a manual query, and it will be
     * executed on the given databse.
     *
     * @param scan : scanner for user input.
     * @return void.
     */
    private static void writeQuery(Scanner scan) {
        
        PreparedStatement pStmt = null;

        System.out.println("\n----------------------");
        System.out.println("WRITE YOU OWN QUERY");
        System.out.println("----------------------");
        System.out.println("Here, you can manually write your own query to access information from the "
                + "VGS database.");

        try {
            String query;

            while (!scan.nextLine().equalsIgnoreCase("Q")) {

                System.out.println("To exit to Main Menu, press \"Q\".");
                System.out.println("Write a new query:");

                query = scan.nextLine();

                //check base cases for query
                if(checkString(query)) {
                    break;
                }
                //trim query if necessary to fit prepared statement format.
                if (query.endsWith(";")) {
//                    System.out.println("Debug:  query before: " + query);
                    query = query.substring(0, query.length() -1);
//                    System.out.println("Debug: query after: " + query);
                }

                System.out.println("Executing user query: " + query + ";\n");
                //prepare the statement and execute
                pStmt = conn.prepareStatement(query);
                rs = pStmt.executeQuery();

                //print results
                displayQueryResults(rs);
                System.out.println("Press Enter to Continue.");
            }
        } catch (SQLException se) {
            System.out.println("Improper Query statement.  Please check syntax and/or database tables."
                    + "  Returning to Main Menu.");
            //close the prepared statement
        } finally {
            try {
                if (pStmt != null) {
                    pStmt.close();
                    System.out.println("Prepared Statement closed!");
                }
            } catch (SQLException ps) {
                ps.printStackTrace();
            }
        }
        //after method finished, get user input
        System.out.println("\n");
        getUserInput();
    }


    /**
     * Helper method to check String query base cases.
     *
     * @param query :  String query from user input to check
     * @return true if user opts to exit to main menu or empty string, false otherwise.
     * */
    private static boolean checkString (String query) {

        if (query.equalsIgnoreCase("Q")) {
            System.out.println("Returning to Main Menu.");
            return true;
        }
        if (query.equals("")) {
            System.out.println("Query statement is empty!  Returning to Main Menu.");
            return true;
        }

    return false;
    }

    /**
     * Helper Method to display query results.
     *
     * @param rs : Result set from database query
     * @return void.
     */
    private static void displayQueryResults(ResultSet rs) {

        //TODO: format print for all display types (game description long, messes up print formatting).

        try {
            //get object of rs meta data
            ResultSetMetaData rsmd = rs.getMetaData();
            //get number of columns
            int colCount = rsmd.getColumnCount();
//            System.out.println("Debug: number of columns: " + colCount);
            //print out column labels
            for (int i = 1; i <= colCount; i++) {

                String label = rsmd.getColumnLabel(i);
                if (label.equalsIgnoreCase("Genre_Description")) {
                    System.out.format("%-66s", rsmd.getColumnLabel(i));
                } else if (label.equalsIgnoreCase("Description")) {
                    System.out.format("%-97s", rsmd.getColumnLabel(i));
                } else {
                    System.out.format("%-26s", rsmd.getColumnLabel(i));
                }
            }
            System.out.println();
            //get the rs data and print out
            while (rs.next()) {

                for (int i = 1; i <= colCount; i++) {

                    Object obj = rs.getObject(i);
                    if (obj != null) {

                        String label = rs.getObject(i).toString();
                        if (label.equalsIgnoreCase("Genre_Description")) {
                            System.out.format("%-66s", rs.getObject(i).toString());
                        } else if (label.equalsIgnoreCase("Description")) {
                            System.out.format("%-97s", rs.getObject(i).toString());
                        } else {
                            System.out.format("%-26s", rs.getObject(i).toString());
                        }
                    }
                }
                System.out.println();
            }
            System.out.println();
        } catch (SQLException se) {
            System.out.println("There was problem displaying query results.");
            se.printStackTrace();
        }
    }


    /**
     * Method to run select.sql presets via user input choice.
     *
     * @param scan : scanner for user input.
     * @return void.
     */
    private static void selectPreset(Scanner scan) {
        //select presets
        //TODO: will need to pretty print output.

        String query1 = "SELECT PLATFORM.Platform_Name FROM PLATFORM, PLATFORM_TYPE WHERE PLATFORM.Platform_Name = PLATFORM_TYPE.Platform_Name";

        String query2 = "SELECT TITLE.Title_Name, TITLE.Description, TITLE.ESRB_Rating, PLATFORM.Platform_Name " +
                "FROM TITLE, PLATFORM ORDER BY TITLE.Title_Name ASC";

        String query3 = "SELECT ACCOUNT.Member_ID, CUSTOMER.First_Name, CUSTOMER.Last_Name, CUSTOMER.Birthdate, " +
                "ACCOUNT.Credit_Card_Number, ACCOUNT.Subscription, ACCOUNT.Email, PLATFORM.Platform_Name " +
                "FROM ACCOUNT, PLATFORM, CUSTOMER WHERE ACCOUNT.Member_ID = CUSTOMER.Member_ID " +
                "ORDER BY ACCOUNT.Member_ID ASC";

        String query4 = "SELECT USER_LIBRARY.UPC, USER_LIBRARY.Member_ID, USER_LIBRARY.Date_Added, USER_LIBRARY.Date_Removed, " +
                "TITLE.Title_Name, TITLE.Description, TITLE.Year_Of_Release, TITLE.ESRB_Rating, TITLE.Metacritic_Rating, " +
                "TITLE.Genre_Name, TITLE.Genre_Description, PLATFORM.Platform_Name " +
                "FROM USER_LIBRARY, PLATFORM, TITLE WHERE USER_LIBRARY.UPC = TITLE.UPC AND PLATFORM.UPC = TITLE.UPC";

        String query5 = "SELECT acct.Member_ID, t.Title_Name FROM TITLE t JOIN USER_LIBRARY ul ON ul.UPC = t.UPC " +
                "JOIN ACCOUNT acct ON acct.Member_ID = ul.Member_ID WHERE acct.Member_ID = 'TheCrow'";

        String query6 = "SELECT acct.Member_ID, t.Title_Name, p.Platform_Name FROM  TITLE t JOIN USER_LIBRARY ul ON ul.UPC = t.UPC " +
                "JOIN SAVE_GAME_FILE sgf ON sgf.UPC = ul.UPC JOIN PLATFORM p ON p.Serial_Number = sgf.Serial_Number  " +
                "JOIN ACCOUNT acct ON acct.Member_ID = ul.Member_ID WHERE acct.Member_ID = 'TheDestroyer85' AND t.UPC = sgf.UPC " +
                "GROUP BY t.Title_Name, p.Platform_Name";

        int selection;

        do {
            System.out.println("Please enter the integer associated with a presetQuery");
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
        } while (selection < 1 || selection > 6);
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
                        System.out.print(rs.getString(2) + "\t");
                        System.out.print(rs.getString(3) + "\t");
                        System.out.print(rs.getDate(4) + "\t");
                        System.out.print(rs.getLong(5) + "\t");
                        System.out.print(rs.getBoolean(6) + "\t");
                        System.out.print(rs.getString(7) + "\t");
                        System.out.print(rs.getString(8) + "\t");
                    }
                    break;

                case 4:
                    // Setup a query
                    rs = stmt.executeQuery(query4);
                    while (rs.next()) {
                        System.out.print(rs.getLong("UPC") + "\t");
                        System.out.print(rs.getString("Member_ID") + "\t");
                        System.out.print(rs.getDate("Date_Added") + "\t");
                        System.out.print(rs.getDate("Date_Removed") + "\t");
                        System.out.print(rs.getString("Title_Name") + "\t");
                        System.out.print(rs.getString("Description") + "\t");
                        System.out.print(rs.getInt("Year_Of_Release") + "\t");
                        System.out.print(rs.getString("ESRB_Rating") + "\t");
                        System.out.print(rs.getDouble("Metacritic_Rating") + "\t");
                        System.out.print(rs.getString("Genre_Name") + "\t");
                        System.out.print(rs.getString("Genre_Description") + "\t");
                        System.out.print(rs.getString("Platform_Name") + "\t");
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
        } catch (Exception exc) {
            exc.printStackTrace();
        }
//        finally {  // ALWAYS clean up DB resources
//            try {
//                if (rs != null)
//                    rs.close();
//                if (stmt != null)
//                    stmt.close();
//                if (conn != null)
//                    conn.close();
//            }
//            catch (SQLException se) {
//                se.printStackTrace();
//            }
//        }

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
        System.out.println("2 - Use preset queries provided with application");
        System.out.println("3 - Insert data into database");
        System.out.println("4 - Remove data from database");
        System.out.println("Q - Exit Program\n");
        System.out.print("Please select an option (1, 2, 3, 4, Q): ");

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
            System.out.println("Error loading JDBC driver and/or connecting to server.");
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
