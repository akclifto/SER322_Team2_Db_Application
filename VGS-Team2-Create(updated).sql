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
    UPC BIGINT  NOT NULL,
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
	FOREIGN KEY (Genre_Name) REFERENCES GENRE (Genre_Name)
    ON DELETE CASCADE ON UPDATE CASCADE
);








-- Platform (Serial_Number, UPC, Platform_Name)
CREATE TABLE PLATFORM
(
    Serial_Number BIGINT NOT NULL,
    UPC BIGINT  NOT NULL,
    Platform_Name VARCHAR(30) NOT NULL,
    CONSTRAINT SNPK
    PRIMARY KEY (Serial_Number)
);

-- Platform_Type (Platform_Name, Serial_Number)
CREATE TABLE PLATFORM_TYPE
(
    Platform_Name VARCHAR(30) NOT NULL,
    Serial_Number BIGINT NOT NULL,
    CONSTRAINT PLATNPK
    PRIMARY KEY (Platform_Name),
    CONSTRAINT SNFK
    FOREIGN KEY (Serial_Number) REFERENCES PLATFORM (Serial_Number)
    ON DELETE CASCADE ON UPDATE CASCADE
);

-- alter table platform to add FKs for platform_name and upc
    ALTER TABLE PLATFORM 
    ADD CONSTRAINT PLATNFK
    FOREIGN KEY (Platform_Name) REFERENCES PLATFORM_TYPE (Platform_Name)
    ON DELETE CASCADE ON UPDATE CASCADE;
    
    ALTER TABLE PLATFORM 
    ADD CONSTRAINT UPCFK
    FOREIGN KEY (UPC) REFERENCES TITLE (UPC)
    ON DELETE CASCADE ON UPDATE CASCADE;
    





    
-- Account (Account_PW, Member_ID, Credit_Card_Number, Subscription, 
--         Email, Serial_Number)
CREATE TABLE ACCOUNT
( 
    Account_PW VARCHAR(30)  NOT NULL,
    Member_ID  VARCHAR(20)  NOT NULL,
    Credit_Card_Number  BIGINT  NOT NULL,
    Subscription BIT,
    Email VARCHAR(50) NOT NULL,
    Serial_Number BIGINT NOT NULL,
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
    Email  VARCHAR(80)  NOT NULL,
    First_Name  VARCHAR(25)  NOT NULL,
    Middle_Name VARCHAR(25),
    Last_Name  VARCHAR(25) NOT NULL,
    Birthdate  DATE  NOT NULL,
    Account_PW VARCHAR(30)  NOT NULL,
    Member_ID  VARCHAR(20)  NOT NULL,
    CONSTRAINT CUSTPK
    PRIMARY KEY (Email)
);


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
    
    -- User_Library (UPC, Account_PW, Member_ID, Date_Added, Date_Removed)
CREATE TABLE USER_LIBRARY
(
    UPC BIGINT  NOT NULL,
    Account_PW VARCHAR(30)  NOT NULL,
    Member_ID  VARCHAR(20)  NOT NULL,
    Date_Added DATE, 
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
    UPC BIGINT  NOT NULL,
    Account_PW VARCHAR(30)  NOT NULL,
    Member_ID  VARCHAR(20)  NOT NULL,
    Start_Time TIME,
    End_Time TIME,
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
    Account_PW VARCHAR(30)  NOT NULL,
    Member_ID  VARCHAR(20)  NOT NULL,
    Customer_Email  VARCHAR(80)  NOT NULL,
    CONSTRAINT LIACCTPWFK
    FOREIGN KEY (Account_PW, Member_ID) REFERENCES ACCOUNT (Account_PW,      Member_ID)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT LICUSTEMFK
    FOREIGN KEY (Customer_Email) REFERENCES CUSTOMER (Email)
    ON DELETE CASCADE ON UPDATE CASCADE
);

