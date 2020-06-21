-- *** PROJECT TEAM 2 DELIVERABLE 3 DDL STATEMENTS AND 
-- *** LOAD TABLES WITH SAMPLE DATA *** 
-- Adam Clifton (akclifto@asu.edu)
-- Anne Landrum (aelandru@asu.edu)
-- Ivan Fernandez (iafernan@asu.edu)
-- Robert Ibarra (rnibarra@asu.edu)


-- Genre (Genre_Name, Genre_Description)
CREATE TABLE GENRE
(
    Genre_Name VARCHAR(20) NOT NULL,
    Genre_Description VARCHAR(100),
    CONSTRAINT GENREPK
    PRIMARY KEY (Genre_Name)
);


-- Title (UPC, Title_Name, Description, Year_of_Realease, 
--       ESRB_Rating, Metacritic_Rating, Genre_Name, Genre_Description)
CREATE TABLE TITLE 
( 
    UPC INT  NOT NULL,
    Title_Name  VARCHAR(30)  NOT NULL,
    Description  VARCHAR(100),
    Year_Of_Release  INT,
    ESRB_Rating  VARCHAR (30),
    Metacritic_Rating  FLOAT,
    Genre_Name VARCHAR(20) NOT NULL,
    Genre_Description VARCHAR(100),
    CONSTRAINT TITLEPK
    PRIMARY KEY (UPC),
    CONSTRAINT TITLEFK
    FOREIGN KEY (Genre) REFERENCES GENRE (Genre_Name)
    ON DELETE SET NULL ON UPDATE CASCADE
);

-- Account (Account_PW, Member_ID, Credit_Card_Number, Subscription, 
--         Email, Serial_Number)
CREATE TABLE ACCOUNT
( 
    Account_PW VARCHAR(20)  NOT NULL,
    Member_ID  VARCHAR(20)  NOT NULL,
    Credit_Card_Number  INT  NOT NULL,
    Subscription BIT,
    Email VARCHAR(50) NOT NULL,
    Serial_Number INT NOT NULL,
    CONSTRAINT ACCPK
    PRIMARY KEY (Account_PW, Member_ID),
    CONSTRAINT MIDPK
    PRIMARY KEY (Member_ID),
    CONSTRAINT EMAILFK
    FOREIGN KEY (Email) REFERENCES CUSTOMER (Email)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT SNFK
    FOREIGN KEY (Serial_Number) REFERENCES PLATFORM (Serial_Number)
    ON DELETE CASCADE ON UPDATE CASCADE,
);

-- Customer (Email, First_Name, Middle_Name, Last_Name, Birthdate, 
--          Account_PW, Member_ID)
CREATE TABLE CUSTOMER
( 
    Email  VARCHAR(80)  NOT NULL,
    First_Name  VARCHAR(25)  NOT NULL,
    Middle_Name VARCHAR(25),
    Last_Name  VARCHAR(25) NOT NULL,
    Birthdate  DATE  NOT NULL,
    Account_PW VARCHAR(20)  NOT NULL,
    Member_ID  VARCHAR(20)  NOT NULL,
    CONSTRAINT CUSTPK
    PRIMARY KEY (Email),
    CONSTRAINT ACCTPWFK
    FOREIGN KEY (Account_PW) REFERENCES ACCOUNT (Account_PW)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT MIDFK
    FOREIGN KEY (Member_ID) REFERENCES ACCOUNT (Member_ID)
    ON DELETE CASCADE ON UPDATE CASCADE,
);

-- User_Library (UPC, Account_PW, Member_ID, Date_Added, Date_Removed)
CREATE TABLE USER_LIBRARY
(
    UPC INT  NOT NULL,
    Account_PW VARCHAR(20)  NOT NULL,
    Member_ID  VARCHAR(20)  NOT NULL,
    Date_Added DATE, 
    Date_Removed DATE,
    CONSTRAINT UPCFK
    FOREIGN KEY (UPC) REFERENCES TITLE (UPC)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT ACCTPWFK
    FOREIGN KEY (Account_PW) REFERENCES ACCOUNT (Account_PW)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT MIDFK
    FOREIGN KEY (Member_ID) REFERENCES ACCOUNT (Member_ID)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- Saved_Game_File(UPC, Account_PW, Member_ID, Start_Time, End_Time, Serial_Number)
CREATE TABLE SAVE_GAME_FILE
(
    UPC INT  NOT NULL,
    Account_PW VARCHAR(20)  NOT NULL,
    Member_ID  VARCHAR(20)  NOT NULL,
    Start_Time DATE,
    End_Time DATE,
    CONSTRAINT UPCFK
    FOREIGN KEY (UPC) REFERENCES TITLE (UPC)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT ACCTPWFK
    FOREIGN KEY (Account_PW) REFERENCES ACCOUNT (Account_PW)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT MIDFK
    FOREIGN KEY (Member_ID) REFERENCES ACCOUNT (Member_ID)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- Logs_Into (Account-PW, Member_ID, Customer_Email)
CREATE TABLE LOGS_INTO
(
    Account_PW VARCHAR(20)  NOT NULL,
    Member_ID  VARCHAR(20)  NOT NULL,
    Customer_Email  VARCHAR(80)  NOT NULL,
    CONSTRAINT ACCTPWFK
    FOREIGN KEY (Account_PW) REFERENCES ACCOUNT (Account_PW)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT MIDFK
    FOREIGN KEY (Member_ID) REFERENCES ACCOUNT (Member_ID)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT CUSTEMFK
    FOREIGN KEY (Customer_Email) REFERENCES CUSTOMER (Email)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- Platform (Serial_Number, UPC, Platform_Name)
CREATE TABLE PLATFORM
(
    Serial_Number INT NOT NULL,
    UPC INT  NOT NULL,
    Platform_Name VARCHAR(30) NOT NULL,
    CONSTRAINT SNPK
    PRIMARY KEY (Serial_Number),
    CONSTRAINT UPCFK
    FOREIGN KEY (UPC) REFERENCES TITLE (UPC)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT PLATNFK
    FOREIGN KEY (Platform_Name) REFERENCES PLATFORM_TYPE (Platform_Name)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- Platform_Type (Platform_Name, Serial_Number)
CREATE TABLE PLATFORM_TYPE
(
    Platform_Name VARCHAR(30) NOT NULL,
    Serial_Number INT NOT NULL,
    CONSTRAINT PLATNPK
    PRIMARY KEY (Platform_Name),
    CONSTRAINT SNFK
    FOREIGN KEY (Serial_Number) REFERENCES PLATFORM (Serial_Number)
    ON DELETE CASCADE ON UPDATE CASCADE,
);

-- need to test to for errors
-- need inserts for data




