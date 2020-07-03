/** 
	PROJECT TEAM 2 DELIVERABLE 4 *** 

	Adam Clifton (akclifto@asu.edu)
	Anne Landrum (aelandru@asu.edu)
	Ivan Fernandez (iafernan@asu.edu)
	Robert Ibarra (rnibarra@asu.edu)

Provided below is a script to run in a mySql server of your choice to set up the VGS database and dummy data.

Run this before starting the application to set up the server database.

This script will:
	- Set up a new database called VGS
	- Create all tables, inserts, and altertable
    - Show tables and describe each for easier navigation when testing queries in the VGS application.
	
@author Project Team 2
@version 2020.06.30

*/
CREATE DATABASE VGS;
SHOW DATABASES;
USE VGS;

-- Genre (Genre_Name, Genre_Description)
CREATE TABLE GENRE
(
    Genre_Name        VARCHAR(20) NOT NULL,
    Genre_Description VARCHAR(100),
    CONSTRAINT GENREPK
        PRIMARY KEY (Genre_Name)
);


-- Title (UPC, Title_Name, Description, Year_of_Realease,
--       ESRB_Rating, Metacritic_Rating, Genre_Name, Genre_Description)
CREATE TABLE TITLE
(
    UPC               BIGINT      NOT NULL,
    Title_Name        VARCHAR(30) NOT NULL,
    Description       VARCHAR(100),
    Year_Of_Release   INT,
    ESRB_Rating       VARCHAR(30),
    Metacritic_Rating FLOAT,
    Genre_Name        VARCHAR(20) NOT NULL,
    Genre_Description VARCHAR(100),
    CONSTRAINT TITLEPK
        PRIMARY KEY (UPC),
    CONSTRAINT TITLEFK
        FOREIGN KEY (Genre_Name) REFERENCES GENRE (Genre_Name)
            ON DELETE CASCADE ON UPDATE CASCADE
);


-- Platform (Serial_Number, UPC, Platform_Name)
CREATE TABLE PLATFORM
(
    Serial_Number BIGINT      NOT NULL,
    UPC           BIGINT      NOT NULL,
    Platform_Name VARCHAR(30) NOT NULL,
    CONSTRAINT SNPK
        PRIMARY KEY (Serial_Number)
);

-- Platform_Type (Platform_Name, Serial_Number)
CREATE TABLE PLATFORM_TYPE
(
    Platform_Name VARCHAR(30) NOT NULL,
    Serial_Number BIGINT      NOT NULL,
    CONSTRAINT PLATNPK
        PRIMARY KEY (Platform_Name),
    CONSTRAINT SNFK
        FOREIGN KEY (Serial_Number) REFERENCES PLATFORM (Serial_Number)
            ON DELETE CASCADE ON UPDATE CASCADE
);


-- Account (Account_PW, Member_ID, Credit_Card_Number, Subscription,
--         Email, Serial_Number)
CREATE TABLE ACCOUNT
(
    Account_PW         VARCHAR(30) NOT NULL,
    Member_ID          VARCHAR(20) NOT NULL,
    Credit_Card_Number BIGINT      NOT NULL,
    Subscription       BIT,
    Email              VARCHAR(50) NOT NULL,
    Serial_Number      BIGINT      NOT NULL,
    CONSTRAINT ACCPK
        PRIMARY KEY (Account_PW, Member_ID),
    CONSTRAINT ACCSNFK
        FOREIGN KEY (Serial_Number) REFERENCES PLATFORM (Serial_Number)
            ON DELETE CASCADE ON UPDATE CASCADE
);

-- Customer (Email, First_Name, Middle_Name, Last_Name, Birthdate,
--          Account_PW, Member_ID)
CREATE TABLE CUSTOMER
(
    Email       VARCHAR(80) NOT NULL,
    First_Name  VARCHAR(25) NOT NULL,
    Middle_Name VARCHAR(25),
    Last_Name   VARCHAR(25) NOT NULL,
    Birthdate   DATE        NOT NULL,
    Account_PW  VARCHAR(30) NOT NULL,
    Member_ID   VARCHAR(20) NOT NULL,
    CONSTRAINT CUSTPK
        PRIMARY KEY (Email)
);


-- User_Library (UPC, Account_PW, Member_ID, Date_Added, Date_Removed)
CREATE TABLE USER_LIBRARY
(
    UPC          BIGINT      NOT NULL,
    Account_PW   VARCHAR(30) NOT NULL,
    Member_ID    VARCHAR(20) NOT NULL,
    Date_Added   DATE,
    Date_Removed DATE,
    CONSTRAINT ULUPCFK
        FOREIGN KEY (UPC) REFERENCES TITLE (UPC)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT ULACCTPWFK
        FOREIGN KEY (Account_PW, Member_ID) REFERENCES ACCOUNT (Account_PW, Member_ID)
            ON DELETE CASCADE ON UPDATE CASCADE
);

-- Saved_Game_File(UPC, Account_PW, Member_ID, Start_Time, End_Time, Serial_Number)
CREATE TABLE SAVE_GAME_FILE
(
    UPC           BIGINT      NOT NULL,
    Account_PW    VARCHAR(30) NOT NULL,
    Member_ID     VARCHAR(20) NOT NULL,
    Start_Time    TIME,
    End_Time      TIME,
    Serial_Number bigint,
    CONSTRAINT SGFUPCFK
        FOREIGN KEY (UPC) REFERENCES TITLE (UPC)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT SGFACCTPWFK
        FOREIGN KEY (Account_PW, Member_ID) REFERENCES ACCOUNT (Account_PW, Member_ID)
            ON DELETE CASCADE ON UPDATE CASCADE
);

-- Logs_Into (Account-PW, Member_ID, Customer_Email)
CREATE TABLE LOGS_INTO
(
    Account_PW     VARCHAR(30) NOT NULL,
    Member_ID      VARCHAR(20) NOT NULL,
    Customer_Email VARCHAR(80) NOT NULL,
    CONSTRAINT LIACCTPWFK
        FOREIGN KEY (Account_PW, Member_ID) REFERENCES ACCOUNT (Account_PW, Member_ID)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT LICUSTEMFK
        FOREIGN KEY (Customer_Email) REFERENCES CUSTOMER (Email)
            ON DELETE CASCADE ON UPDATE CASCADE
);

-- inserts
INSERT INTO GENRE
    (Genre_Name, Genre_Description)
VALUES ('Adventure', 'Solve puzzles by interacting with characters and the environment'),
       ('Action-Adventure', 'Combat, explore, gather items, and solve puzzles'),
       ('Sports', 'Simulation of sports with AI and multiplayer'),
       ('Simulation', 'Closely simulates real life or fictional reality'),
       ('Survival Horror', 'Survive within a supernatural setting');

INSERT INTO TITLE
(UPC, Title_Name, Description, Year_Of_Release, ESRB_Rating, Metacritic_Rating, Genre_Name, Genre_Description)
VALUES (012345678985, 'The Last Of Us', 'Venture through a post apocalyptic United States as Joel to save Ellie.', 2013,
        'Mature', 95, 'Survival Horror', 'Survive within a supernatural setting'),
       (854123697852, 'God Of War', 'Norse Mythology game set in Norway.  Join Kratos and Atreus in their adventure.',
        2018, 'Mature', 94, 'Action-Adventure', 'Combat, explore, gather items, and solve puzzles'),
       (125479648532, 'Myst',
        'First-person journey in which players interact with the environment to solve puzzles and advance.', 1993,
        'Everyone', 69, 'Adventure', 'Solve puzzles by interacting with characters and the environment');

INSERT INTO PLATFORM
    (Serial_Number, UPC, Platform_Name)
VALUES (5482, 012345678985, 'Playstation 3'),
       (6495, 854123697852, 'Playstation 4'),
       (8245, 125479648532, 'PC');

INSERT INTO PLATFORM_TYPE
    (Platform_Name, Serial_Number)
VALUES ('Playstation 3', 5482),
       ('Playstation 4', 6495),
       ('PC', 8245);

INSERT INTO ACCOUNT
(Account_PW, Member_ID, Credit_Card_Number, Subscription,
 Email, Serial_Number)
VALUES ('IloveGaming45!', 'TheDestroyer85', 187245087634512, 1, 'TheDestroyer85@gmail.com', 5482),
       ('Pneuma8927&', 'ToolBand46&2', 132140987890345, 1, 'ToolBand46&2@gmail.com', 6495),
       ('HeroesofThestormlover86$', 'TheCrow', 264738290198435, 1, 'BrandonLee@yahoo.com', 8245);


INSERT INTO CUSTOMER
(Email, First_Name, Middle_Name, Last_Name, Birthdate, Account_PW, Member_ID)
VALUES ('TheDestroyer85@gmail.com', 'David', 'Thomas', 'Draiman', '1980/03/09', 'IloveGaming45!', 'TheDestroyer85'),
       ('ToolBand46&2@gmail.com', 'Maynard', 'James', 'Keenan', '1964/04/17', 'Pneuma8927&', 'ToolBand46&2'),
       ('BrandonLee@yahoo.com', 'Brandon', 'Bruce', 'Lee', '1965/02/01', 'HeroesofThestormlover86$', 'TheCrow');

INSERT INTO USER_LIBRARY
    (UPC, Account_PW, Member_ID, Date_Added, Date_Removed)
VALUES (012345678985, 'IloveGaming45!', 'TheDestroyer85', '2013/07/10', '2018/08/20'),
       (012345678985, 'Pneuma8927&', 'ToolBand46&2', '2013/09/20', '2020/01/30'),
       (854123697852, 'HeroesofThestormlover86$', 'TheCrow', '2018/04/20', '2020/05/10'),
       (125479648532, 'HeroesofThestormlover86$', 'TheCrow', '2000/09/21', '2015/10/18');

INSERT INTO SAVE_GAME_FILE
    (UPC, Account_PW, Member_ID, Start_Time, End_Time, Serial_Number)
VALUES (125479648532, 'HeroesofThestormlover86$', 'TheCrow', '11:21:58', '23:18:41', 8245),
       (854123697852, 'HeroesofThestormlover86$', 'TheCrow', '08:24:15', '19:18:11', 6495),
       (012345678985, 'Pneuma8927&', 'ToolBand46&2', '15:08:04', '11:13:55', 5482),
       (012345678985, 'IloveGaming45!', 'TheDestroyer85', '21:05:17', '00:18:35', 5482);


INSERT INTO LOGS_INTO
    (Account_PW, Member_ID, Customer_Email)
VALUES ('IloveGaming45!', 'TheDestroyer85', 'TheDestroyer85@gmail.com'),
       ('Pneuma8927&', 'ToolBand46&2', 'ToolBand46&2@gmail.com'),
       ('HeroesofThestormlover86$', 'TheCrow', 'BrandonLee@yahoo.com');

-- alter table customer to add FK Account_PW, Member_ID
ALTER TABLE CUSTOMER
    ADD CONSTRAINT CUSTACCTPWFK
        FOREIGN KEY (Account_PW, Member_ID) REFERENCES ACCOUNT (Account_PW, Member_ID)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- alter table account to add FK Email
ALTER TABLE ACCOUNT
    ADD CONSTRAINT EMAILFK
        FOREIGN KEY (Email) REFERENCES CUSTOMER (Email)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- alter table platform to add FKs for platform_name and upc
ALTER TABLE PLATFORM
    ADD CONSTRAINT PLATNFK
        FOREIGN KEY (Platform_Name) REFERENCES PLATFORM_TYPE (Platform_Name)
            ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE PLATFORM
    ADD CONSTRAINT UPCFK
        FOREIGN KEY (UPC) REFERENCES TITLE (UPC)
            ON DELETE CASCADE ON UPDATE CASCADE;

-- show tables for check
SHOW TABLES;
-- describe each table
\! echo "ACCOUNT TABLE:";
DESCRIBE ACCOUNT;
\! echo "CUSTOMER TABLE:";
DESCRIBE CUSTOMER;
\! echo "GENRE TABLE:";
DESCRIBE GENRE;
\! echo "LOGS_INTO TABLE:";
DESCRIBE LOGS_INTO;
\! echo "PLATFORM TABLE:";
DESCRIBE PLATFORM;
\! echo "PLATFORM_TYPE TABLE:";
DESCRIBE PLATFORM_TYPE;
\! echo "SAVE_GAME_FILE TABLE:";
DESCRIBE SAVE_GAME_FILE;
\! echo "TITLE TABLE:";
DESCRIBE TITLE;
\! echo "USER_LIBRARY TABLE:";
DESCRIBE USER_LIBRARY;



