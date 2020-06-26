-- *** PROJECT TEAM 2 DELIVERABLE 3 ALTER TABLE STATEMENTS ***
-- Adam Clifton (akclifto@asu.edu)
-- Anne Landrum (aelandru@asu.edu)
-- Ivan Fernandez (iafernan@asu.edu)
-- Robert Ibarra (rnibarra@asu.edu)

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