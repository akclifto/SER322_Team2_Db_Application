package vgs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.Time;
import java.sql.Date;
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
 * @version 2020.07.07
 */
public class Application {

    private static ResultSet rs = null;
    private static Statement stmt = null;
    private static Connection conn = null;
    private static PreparedStatement ps = null;
    private static boolean debugFlag = false; //boolean for debug messages


    /**
     * Method to run manual queries on database.  The user will type a manual query, and it will be
     * executed on the given database.
     * <p>
     * Sample query format:
     * "select * from customer;"
     *
     * @param scan : scanner for user input.
     * @return void.
     * @author Adam Clifton (akclifto@asu.edu)
     */
    private static void writeQuery(Scanner scan) {

        PreparedStatement pStmt = null;

        System.out.println("\n----------------------");
        System.out.println("WRITE YOU OWN QUERY");
        System.out.println("-----------------------");
        System.out.println("Here, you can manually write your own query to access information from the "
                + "VGS database.");
        System.out.println("\nSample query format: "
                + "\n \t\tselect * from customer;\n");


        try {
            String query;

            while (!scan.nextLine().equalsIgnoreCase("Q")) {

                System.out.println("To exit to Main Menu, press \"Q\".");
                System.out.println("Write a new query:");

                query = scan.nextLine();

                //check base cases for query
                if (checkString(query)) {
                    break;
                }
                //trim query if necessary to fit prepared statement format.
                if (query.endsWith(";")) {
                    if (debugFlag) debug("Query before: " + query);
                    query = query.substring(0, query.length() - 1);
                    if (debugFlag) debug("Query after: " + query);
                }

                System.out.println("Executing user query: " + query + ";\n");
                //prepare the statement and execute
                pStmt = conn.prepareStatement(query);
                rs = pStmt.executeQuery();

                //print results
                displayQueryResults(rs);
                System.out.print("Press Enter to Continue.");
            }
        } catch (SQLException se) {
            System.out.println("Improper Query statement.  Please check syntax and/or database tables."
                    + "  Returning to Main Menu.");
            //close the prepared statement
        } finally {
            try {
                if (pStmt != null) {
                    pStmt.close();
                    if (debugFlag) debug("Prepared Statement closed!");
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
     * @return true if user opts to exit to main menu or query is empty string, false otherwise.
     * @author Adam Clifton (akclifto@asu.edu)
     */
    private static boolean checkString(String query) {

        if (query.equalsIgnoreCase("Q")) {
            System.out.println("Returning to Main Menu.");
            return true;
        }
        if (query.equals("")) {
            System.out.println("Statement is empty!  Returning to Main Menu.");
            return true;
        }
        return false;
    }

    /**
     * Helper Method to display query results.
     *
     * @param rs : Result set from database query
     * @return void.
     * @author Adam Clifton (akclifto@asu.edu)
     */
    private static void displayQueryResults(ResultSet rs) {

        try {
            //get object of rs meta data
            ResultSetMetaData rsmd = rs.getMetaData();
            //get number of columns
            int colCount = rsmd.getColumnCount();
            if (debugFlag) debug("Number of columns: " + colCount);
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
     * @author Ivan Ferndandez (iafernan@asu.edu)
     */
    private static void selectPreset(Scanner scan) {

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
            System.out.println("1. " + query1 + "\n");
            System.out.println("2. " + query2 + "\n");
            System.out.println("3. " + query3 + "\n");
            System.out.println("4. " + query4 + "\n");
            System.out.println("5. " + query5 + "\n");
            System.out.println("6. " + query6 + "\n");
            //get user input and validate selection
            while (!scan.hasNextInt()) {
                System.out.println("That's not a number!");
                scan.next();
            }
            selection = scan.nextInt();
        } while (selection < 1 || selection > 6);
        System.out.println("Selection was successful!" + "\n");

        try {
            // Create a statement
            stmt = conn.createStatement();


            // Display the results
            switch (selection) {
                case 1:

                    // Setup a query
                    rs = stmt.executeQuery(query1);

                    while (rs.next()) {
                        System.out.println(rs.getString("Platform_Name") + "\t");

                    }
                    break;

                case 2:

                    // Setup a query
                    rs = stmt.executeQuery(query2);

                    while (rs.next()) {
                        System.out.print(rs.getString("Title_Name") + "\t");
                        System.out.print(rs.getString("Description") + "\t");
                        System.out.print(rs.getString("ESRB_Rating") + "\t");
                        System.out.println(rs.getString("Platform_Name") + "\t");


                    }
                    break;

                case 3:
                    // Setup a query
                    rs = stmt.executeQuery(query3);

                    while (rs.next()) {
                        System.out.print(rs.getString("Member_ID") + "\t");
                        System.out.print(rs.getString("First_Name") + "\t");
                        System.out.print(rs.getString("Last_Name") + "\t");
                        System.out.print(rs.getDate("Birthdate") + "\t");
                        System.out.print(rs.getLong("Credit_Card_Number") + "\t");
                        System.out.print(rs.getBoolean("Subscription") + "\t");
                        System.out.print(rs.getString("Email") + "\t");
                        System.out.println(rs.getString("Platform_Name") + "\t");
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
                        System.out.println(rs.getString("Platform_Name") + "\t");
                    }
                    break;

                case 5:
                    // Setup a query
                    rs = stmt.executeQuery(query5);
                    while (rs.next()) {
                        System.out.print(rs.getString("Member_ID") + "\t");
                        System.out.println(rs.getString("Title_Name") + "\t");

                    }
                    break;

                case 6:
                    // Setup a query
                    rs = stmt.executeQuery(query6);
                    while (rs.next()) {
                        System.out.print(rs.getString("Member_ID") + "\t");
                        System.out.print(rs.getString("Title_Name") + "\t");
                        System.out.println(rs.getString("Platform_Name") + "\t");

                    }
                    break;

            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        //after method finished, get user input
        getUserInput();
    }

    /**
     * Method to insert into database.  Will insert a single tuple, to a single table.
     *
     * @param scan : scanner for user input.
     * @return void.
     * @author Ivan Ferndandez (iafernan@asu.edu)
     */
    private static void insertData(Scanner scan) {

        int integer = 0;
        String colString;
        String colString2;
        String colString3;
        String colString4;
        String colString5;
        String colString6;
        String colDate;
        String colTime;
        String colTime2;
        float colFloat;
        long colLong;
        long colLong2;
        boolean colBool;
        int colInt;


        do {
            System.out.println("Please select enter the integer associated with the table to insert a tuple into: ");
            System.out.println("1. Account, 2. Customer, 3. Genre, 4. Logs_Into, 5. Platform, 6. Platform_Type, 7. Save_Game_File, \n" +
                    "8. Title, 9. User_Library");

            //get user input and validate selection
            while (!scan.hasNextInt()) {
                System.out.println("That's not a number!");
                scan.next();
            }
            integer = scan.nextInt();
        } while (integer < 1 || integer > 9);

        System.out.println("Selection was successful!" + "\n");

        try {
            // Create a statement
            stmt = conn.createStatement();


            // Display the results
            switch (integer) {
                case 1:
                    //Ask user for input values
                    System.out.println("INSERT INTO ACCOUNT VALUES (Account_PW, Member_ID, Credit_Card_Number, Subscription, Email, Serial_Number)");
                    System.out.println("Please enter Account_PW: ");
                    colString = scan.next();
                    System.out.println("Please enter Member_ID: ");
                    colString2 = scan.next();
                    System.out.println("Please enter Credit_Card_Number: ");
                    colLong = scan.nextLong();
                    System.out.println("Please enter Subscription: ");
                    colBool = scan.nextBoolean();
                    System.out.println("Please enter Email: ");
                    colString3 = scan.next();
                    System.out.println("Please enter Serial_Number: ");
                    colLong2 = scan.nextLong();

                    // Setup prepared statement
                    ps = conn.prepareStatement("INSERT INTO ACCOUNT VALUES (?, ?, ?, ?, ?, ?)");
                    ps.setString(1, colString);
                    ps.setString(2, colString2);
                    ps.setLong(3, colLong);
                    ps.setBoolean(4, colBool);
                    ps.setString(5, colString3);
                    ps.setLong(6, colLong2);
                    if (ps.executeUpdate() > 0) {
                        System.out.println("Inserted tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

                case 2:

                    //Ask user for input values
                    System.out.println("INSERT INTO CUSTOMER VALUES (Email, First_Name, Middle_Name, Last_Name, Birthdate, Account_PW, Member_ID)");
                    System.out.println("Please enter Email: ");
                    colString = scan.next();
                    System.out.println("Please enter First_Name: ");
                    colString2 = scan.next();
                    System.out.println("Please enter Middle_Name: ");
                    colString3 = scan.next();
                    System.out.println("Please enter Last_Name: ");
                    colString4 = scan.next();
                    System.out.println("Please enter Birthdate (in format \"YYYY-MM-DD\"):");
                    colDate = scan.next();
                    System.out.println("Please enter Account_PW: ");
                    colString5 = scan.next();
                    System.out.println("Please enter Member_ID: ");
                    colString6 = scan.next();

                    // Setup prepared statement
                    ps = conn.prepareStatement("INSERT INTO CUSTOMER VALUES (?, ?, ?, ?, ?, ?, ?)");
                    ps.setString(1, colString);
                    ps.setString(2, colString2);
                    ps.setString(3, colString3);
                    ps.setString(4, colString4);
                    ps.setDate(5, Date.valueOf(colDate));
                    ps.setString(6, colString5);
                    ps.setString(7, colString6);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("Inserted tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();
                    break;

                case 3:
                    //Ask user for input values
                    System.out.println("INSERT INTO GENRE VALUES (Genre_Name, Genre_Description)");
                    System.out.println("Please enter Genre_Name: ");
                    colString = scan.next();
                    System.out.println("Please enter Genre_Description: ");
                    colString2 = scan.next();


                    // Setup prepared statement
                    ps = conn.prepareStatement("INSERT INTO Genre VALUES (?, ?)");
                    ps.setString(1, colString);
                    ps.setString(2, colString2);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("Inserted tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();
                    break;

                case 4:
                    //Ask user for input values
                    System.out.println("INSERT INTO LOGS_INTO (Account_PW, Member_ID, Customer_Email)");
                    System.out.println("Please enter Account_PW: ");
                    colString = scan.next();
                    System.out.println("Please enter Member_ID: ");
                    colString2 = scan.next();
                    System.out.println("Please enter Customer_Email: ");
                    colString3 = scan.next();

                    // Setup prepared statement
                    ps = conn.prepareStatement("INSERT INTO LOGS_INTO VALUES (?, ?, ?)");
                    ps.setString(1, colString);
                    ps.setString(2, colString2);
                    ps.setString(3, colString3);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("Inserted tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

                case 5:
                    //Ask user for input values
                    System.out.println("INSERT INTO PLATFORM (Serial_Number, UPC, Platform_Name)");
                    System.out.println("Please enter Serial_Number: ");
                    colLong = scan.nextLong();
                    System.out.println("Please enter UPC: ");
                    colLong2 = scan.nextLong();
                    System.out.println("Please enter Platform_Name: ");
                    colString = scan.next();

                    // Setup prepared statement
                    ps = conn.prepareStatement("INSERT INTO PLATFORM VALUES (?, ?, ?)");
                    ps.setLong(1, colLong);
                    ps.setLong(2, colLong2);
                    ps.setString(3, colString);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("Inserted tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

                case 6:
                    //Ask user for input values
                    System.out.println("INSERT INTO PLATFORM_TYPE (Platform_Name, Serial_Number)");

                    System.out.println("Please enter Platform_Name: ");
                    colString = scan.next();
                    System.out.println("Please enter Serial_Number: ");
                    colLong = scan.nextLong();

                    // Setup prepared statement
                    ps = conn.prepareStatement("INSERT INTO PLATFORM_TYPE VALUES (?, ?)");
                    ps.setString(1, colString);
                    ps.setLong(2, colLong);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("Inserted tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

                case 7:
                    //Ask user for input values
                    System.out.println("INSERT INTO SAVE_GAME_FILE (UPC, Account_PW, Member_ID, Start_Time, End_Time, Serial Number)");

                    System.out.println("Please enter UPC: ");
                    colLong = scan.nextLong();
                    System.out.println("Please enter Account_PW: ");
                    colString = scan.next();
                    System.out.println("Please enter Member_ID: ");
                    colString2 = scan.next();
                    System.out.println("Please enter Start_Time 00:00:00 : ");
                    colTime = scan.next();
                    System.out.println("Please enter End_Time 00:00:00 : ");
                    colTime2 = scan.next();
                    System.out.println("Please enter Serial_Number: ");
                    colLong2 = scan.nextLong();

                    // Setup prepared statement
                    ps = conn.prepareStatement("INSERT INTO SAVE_GAME_FILE VALUES (?, ?, ?, ?, ?, ?)");
                    ps.setLong(1, colLong);
                    ps.setString(2, colString);
                    ps.setString(3, colString2);
                    ps.setTime(4, Time.valueOf(colTime));
                    ps.setTime(5, Time.valueOf(colTime2));
                    ps.setLong(6, colLong2);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("Inserted tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

                case 8:
                    //Ask user for input values
                    System.out.println("INSERT INTO TITLE (UPC, Title_Name, Description, Year_Of_Release, ESRB_Rating, Metacritic_Rating, Genre_Name, Genre_Description)");

                    System.out.println("Please enter UPC: ");
                    colLong = scan.nextLong();
                    System.out.println("Please enter Title_Name: ");
                    colString = scan.next();
                    System.out.println("Please enter Description: ");
                    colString2 = scan.next();
                    System.out.println("Please enter Year_Of_Release YYYY : ");
                    colInt = scan.nextInt();
                    System.out.println("Please enter ESRB_Rating : ");
                    colString3 = scan.next();
                    System.out.println("Please enter Metacritic_Rating: ");
                    colFloat = scan.nextFloat();
                    System.out.println("Please enter Genre_Name: ");
                    colString4 = scan.next();
                    System.out.println("Please enter Genre_Description: ");
                    colString5 = scan.next();


                    // Setup prepared statement
                    ps = conn.prepareStatement("INSERT INTO TITLE VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setLong(1, colLong);
                    ps.setString(2, colString);
                    ps.setString(3, colString2);
                    ps.setInt(4, colInt);
                    ps.setString(5, colString3);
                    ps.setFloat(6, colFloat);
                    ps.setString(7, colString4);
                    ps.setString(8, colString5);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("Inserted tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

                case 9:
                    //Ask user for input values
                    System.out.println("INSERT INTO USER_LIBRARY (UPC, Account_PW, Member_ID, Date_Added, Date_Removed)");

                    System.out.println("Please enter UPC: ");
                    colLong = scan.nextLong();
                    System.out.println("Please enter Account_PW: ");
                    colString = scan.next();
                    System.out.println("Please enter Member_ID: ");
                    colString2 = scan.next();
                    System.out.println("Please enter Date_Added (in format \"YYYY-MM-DD\"):");
                    colDate = scan.next();
                    System.out.println("Please enter Date_Removed (in format \"YYYY-MM-DD\"):");
                    colString3 = scan.next();


                    // Setup prepared statement
                    ps = conn.prepareStatement("INSERT INTO USER_LIBRARY VALUES (?, ?, ?, ?, ?)");
                    ps.setLong(1, colLong);
                    ps.setString(2, colString);
                    ps.setString(3, colString2);
                    ps.setDate(4, Date.valueOf(colDate));
                    ps.setDate(5, Date.valueOf(colString3));

                    if (ps.executeUpdate() > 0) {
                        System.out.println("Inserted tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }


        //after method finished, get user input
        getUserInput();
    }

    /**
     * Method to remove data from database.  Will remove a single tuple, from a single table.
     *
     * @param scan : scanner for user input.
     * @return void.
     * @author Ivan Ferndandez (iafernan@asu.edu)
     */
    private static void removeData(Scanner scan) {

        int integer = 0;
        String colString;
        String colString2;
        String colString3;
        long colLong;

        do {
            System.out.println("Please select enter the integer corresponding to the table to DELETE a tuple from: ");
            System.out.println("1. Account, 2. Customer, 3. Genre, 4. Logs_Into, 5. Platform, 6. Platform_Type, 7. Save_Game_File, \n" +
                    "8. Title, 9. User_Library");

            //get user input and validate selection
            while (!scan.hasNextInt()) {
                System.out.println("That's not a number!");
                scan.next();
            }
            integer = scan.nextInt();
        } while (integer < 1 || integer > 9);

        System.out.println("Selection was successful!" + "\n");

        try {
            // Create a statement
            stmt = conn.createStatement();


            // Display the results
            switch (integer) {
                case 1:
                    //Ask user for input values
                    System.out.println("DELETE FROM ACCOUNT WHERE Account_PW = ? AND Member_ID ?");
                    System.out.println("Please enter Account_PW: ");
                    colString = scan.next();
                    System.out.println("Please enter Member_ID: ");
                    colString2 = scan.next();


                    // Setup prepared statement
                    ps = conn.prepareStatement("DELETE FROM ACCOUNT WHERE ACCOUNT.Account_PW = ? AND ACCOUNT.Member_ID = ?");
                    ps.setString(1, colString);
                    ps.setString(2, colString2);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("DELETED tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

                case 2:

                    //Ask user for input values
                    System.out.println("DELETE FROM CUSTOMER WHERE Email = ?");
                    System.out.println("Please enter Email: ");
                    colString = scan.next();

                    // Setup prepared statement
                    ps = conn.prepareStatement("DELETE FROM CUSTOMER WHERE CUSTOMER.Email = ?");
                    ps.setString(1, colString);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("DELETED tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();
                    break;

                case 3:
                    //Ask user for input values
                    System.out.println("DELETE FROM GENRE WHERE Genre_Name = ?");
                    System.out.println("Please enter Genre_Name: ");
                    colString = scan.next();


                    // Setup prepared statement
                    ps = conn.prepareStatement("DELETE FROM GENRE WHERE GENRE.Genre_Name = ?");
                    ps.setString(1, colString);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("DELETED tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();
                    break;

                case 4:
                    //Ask user for input values
                    System.out.println("DELETE FROM LOGS_INTO WHERE Account_PW = ? AND Member_ID = ? AND Customer_Email = ?)");
                    System.out.println("Please enter Account_PW: ");
                    colString = scan.next();
                    System.out.println("Please enter Member_ID: ");
                    colString2 = scan.next();
                    System.out.println("Please enter Customer_Email: ");
                    colString3 = scan.next();

                    // Setup prepared statement
                    ps = conn.prepareStatement("DELETE FROM LOGS_INTO WHERE LOGS_INTO.Account_PW = ? AND LOGS_INTO.Member_ID = ? AND LOGS_INTO.Customer_Email = ?");
                    ps.setString(1, colString);
                    ps.setString(2, colString2);
                    ps.setString(3, colString3);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("DELETED tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

                case 5:
                    //Ask user for input values
                    System.out.println("DELETE FROM PLATFORM WHERE Serial_Number = ?");
                    System.out.println("Please enter Serial_Number: ");
                    colLong = scan.nextLong();

                    // Setup prepared statement
                    ps = conn.prepareStatement("DELETE FROM PLATFORM WHERE PLATFORM.Serial_Number = ?");
                    ps.setLong(1, colLong);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("DELETED tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

                case 6:
                    //Ask user for input values
                    System.out.println("DELETE FROM PLATFORM_TYPE WHERE Platform_Name = ?");

                    System.out.println("Please enter Platform_Name: ");
                    colString = scan.next();

                    // Setup prepared statement
                    ps = conn.prepareStatement("DELETE FROM PLATFORM_TYPE WHERE PLATFORM_TYPE.Platform_Name = ?");
                    ps.setString(1, colString);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("DELETED tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

                case 7:
                    //Ask user for input values
                    System.out.println("DELETE FROM SAVE_GAME_FILE WHERE UPC = ? AND Account_PW = ? AND Member_ID = ?");

                    System.out.println("Please enter UPC: ");
                    colLong = scan.nextLong();
                    System.out.println("Please enter Account_PW: ");
                    colString = scan.next();
                    System.out.println("Please enter Member_ID: ");
                    colString2 = scan.next();

                    // Setup prepared statement
                    ps = conn.prepareStatement("DELETE FROM SAVE_GAME_FILE WHERE SAVE_GAME_FILE.UPC = ? AND SAVE_GAME_FILE.Account_PW = ? AND SAVE_GAME_FILE.Member_ID = ?");
                    ps.setLong(1, colLong);
                    ps.setString(2, colString);
                    ps.setString(3, colString2);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("DELETED tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

                case 8:
                    //Ask user for input values
                    System.out.println("DELETE FROM TITLE WHERE UPC = ?");

                    System.out.println("Please enter UPC: ");
                    colLong = scan.nextLong();


                    // Setup prepared statement
                    ps = conn.prepareStatement("DELETE FROM TITLE WHERE TITLE.UPC = ?");
                    ps.setLong(1, colLong);


                    if (ps.executeUpdate() > 0) {
                        System.out.println("DELETED tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

                case 9:
                    //Ask user for input values
                    System.out.println("DELETE FROM USER_LIBRARY WHERE UPC = ? AND Account_PW = ? AND Member_ID = ?");

                    System.out.println("Please enter UPC: ");
                    colLong = scan.nextLong();
                    System.out.println("Please enter Account_PW: ");
                    colString = scan.next();
                    System.out.println("Please enter Member_ID: ");
                    colString2 = scan.next();


                    // Setup prepared statement
                    ps = conn.prepareStatement("DELETE FROM USER_LIBRARY WHERE USER_LIBRARY.UPC =  ? AND USER_LIBRARY.Account_PW = ? AND USER_LIBRARY.Member_ID = ?");
                    ps.setLong(1, colLong);
                    ps.setString(2, colString);
                    ps.setString(3, colString2);

                    if (ps.executeUpdate() > 0) {
                        System.out.println("DELETED tuple SUCCESSFULLY");
                    }
                    ps.close();
                    // Have to do this to write changes to a DB
                    conn.commit();

                    break;

            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        //after method finished, get user input
        getUserInput();
    }


    /**
     * Method to update table in database.
     * <p>
     * example update statement:
     *      "update customer set middle_name="Testname" where first_name="brandon";"
     *
     * @param scan : scanner for user input
     * @return void.
     * @author Adam Clifton (akclifto@asu.edu)
     */
    private static void updateTable(Scanner scan) {

        PreparedStatement pStmt = null;

        System.out.println("\n----------------------");
        System.out.println("UPDATE TABLE");
        System.out.println("----------------------");
        System.out.println("Here, you can enter an update statemet to change existing data within the database.");
        System.out.println("\nSample update statement: "
                + "\n \t\t update customer set middle_name=\"Testname\" where first_name=\"brandon\";\n");

        try {
            String update;

            while (!scan.nextLine().equalsIgnoreCase("Q")) {

                System.out.println("To exit to Main Menu, press \"Q\".");
                System.out.println("Please enter update statement:");

                update = scan.nextLine();

                //check base cases for update
                if (checkString(update)) {
                    break;
                }
                //trim update if necessary to fit prepared statement format.
                if (update.endsWith(";")) {
                    if (debugFlag) debug("Update statement before: " + update);
                    update = update.substring(0, update.length() - 1);
                    if (debugFlag) debug("Update statement after: " + update);
                }

                System.out.println("Executing user update statement: " + update + ";\n");
                //prepare the statement and execute
                pStmt = conn.prepareStatement(update);
                pStmt.executeUpdate(update);

                //commit the update to the database
                conn.commit();
                System.out.println("Update to table SUCCESSFUL.  Press Enter to continue.");
            }
        } catch (SQLException se) {
            System.out.println("Improper update statement.  Please check syntax and/or database tables."
                    + "  Returning to Main Menu.");
            //close the prepared statement
        } finally {
            try {
                if (pStmt != null) {
                    pStmt.close();
                    if (debugFlag) debug("Prepared Statement closed!");
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
     * Method to exit program and close the scanner.
     *
     * @param scan : scanner to close
     * @return void.
     * @author Adam Clifton (akclifto@asu.edu)
     */
    private static void exitProgram(Scanner scan) {

        System.out.print("Exiting Program. ");
        scan.close();
        if (debugFlag) debug("Scanner Closed!");
    }

    /**
     * Method to get input for application via CLI.  Method show main menu and passes arguments based on user input.
     *
     * @return void.
     * @author Adam Clifton (acklifto@asu.edu)
     */
    private static void getUserInput() {

        //show menu options
        System.out.println("Main Menu: ");
        System.out.println("1 - Write your own selection query");
        System.out.println("2 - Preset queries provided with application");
        System.out.println("3 - Insert data into database");
        System.out.println("4 - Remove data from database");
        System.out.println("5 - Update existing data in database");
        System.out.println("Q - Exit Program\n");
        System.out.print("Please select an option (1, 2, 3, 4, 5, Q): ");

        //set scanner and get input
        try (Scanner scan = new Scanner(System.in)) {

            //user inputs, then send off to appropriate methods.
            while (scan.hasNext()) {

                String in = scan.next();

                if (in.equals("1") || in.equalsIgnoreCase("Write")) {

                    writeQuery(scan);
                } else if (in.equals("2") || in.equalsIgnoreCase("Preset")) {

                    selectPreset(scan);
                } else if (in.equals("3") || in.equalsIgnoreCase("Insert")) {

                    insertData(scan);
                } else if (in.equals("4") || in.equalsIgnoreCase("Remove")) {

                    removeData(scan);
                } else if (in.equals("5") || in.equalsIgnoreCase("Update")) {

                    updateTable(scan);
                } else if (in.equalsIgnoreCase("Q") || in.equalsIgnoreCase("Exit")) {

                    exitProgram(scan);
                    break;
                    //access to debug messages for testing
                } else if (in.equalsIgnoreCase("debug")) {

                    if (debugFlag) {
                        debugFlag = false;
                        System.out.println("Debug messages toggled off.  Proceed with Main Menu selection.");
                    } else {
                        debugFlag = true;
                        System.out.println("Debug message toggled on.  Proceed with Main Menu selection.");
                    }
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
     * @author Adam Clifton (akclifto@asu.edu)
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
            //set autocommit to false
            conn.setAutoCommit(false);

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
                    if (debugFlag) debug("ResultSet closed!");
                }
                if (stmt != null) {
                    stmt.close();
                    if (debugFlag) debug("Query Statement closed!");
                }
            } catch (SQLException se) {
                System.out.println("There is a problem closing database resources!");
                se.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Connection closed.");
                }
            } catch (Throwable se) {
                System.out.println("There is a leak!");
                se.printStackTrace();
            }
        }
    }


    /**
     * Helper method for debugging.  This method is toggled with debugFlag in the main menu.
     * At the main menu, enter "debug" to toggle on/off message debugger
     *
     * @param message :  message to pass to debugger
     * @return void.
     * @author Adam Clifton (akclifto@asu.edu)
     */
    private static void debug(String message) {

        System.out.println("Debug: " + message);
    }

}