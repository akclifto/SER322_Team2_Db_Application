-- *** PROJECT TEAM 2 DELIVERABLE 3 INSERT STATEMENTS *** 
-- Adam Clifton (akclifto@asu.edu)
-- Anne Landrum (aelandru@asu.edu)
-- Ivan Fernandez (iafernan@asu.edu)
-- Robert Ibarra (rnibarra@asu.edu)


SELECT PLATFORM.Platform_Name
FROM PLATFORM, PLATFORM_TYPE
WHERE PLATFORM.Platform_Name = PLATFORM_TYPE.Platform_Name;

SELECT TITLE.Title_Name, TITLE.Description, TITLE.ESRB_Rating, PLATFORM.Platform_Name
FROM TITLE, PLATFORM;

SELECT ACCOUNT.Member_ID, CUSTOMER.First_Name, CUSTOMER.Last_Name, CUSTOMER.Birthdate, ACCOUNT.Credit_Card_Number, ACCOUNT.Subscription, ACCOUNT.Email, PLATFORM.Platform_Name
FROM ACCOUNT, PLATFORM, CUSTOMER
WHERE ACCOUNT.Member_ID = CUSTOMER.Member_ID;

SELECT USER_LIBRARY.UPC, USER_LIBRARY.Member_ID, USER_LIBRARY.Date_Added, USER_LIBRARY.Date_Removed, TITLE.Title_Name, TITLE.Description, TITLE.Year_Of_Release, TITLE.ESRB_Rating, TITLE.Metacritic_Rating, TITLE.Genre_Name, TITLE.Genre_Description, PLATFORM.Platform_Name 
FROM USER_LIBRARY, PLATFORM, TITLE 
WHERE USER_LIBRARY.UPC = TITLE.UPC AND PLATFORM.UPC = TITLE.UPC;