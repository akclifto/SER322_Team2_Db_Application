/* *** PROJECT TEAM 2 DELIVERABLE 3 SELECT QUERY STATEMENTS *** 
	Adam Clifton (akclifto@asu.edu)
	Anne Landrum (aelandru@asu.edu)
	Ivan Fernandez (iafernan@asu.edu)
	Robert Ibarra (rnibarra@asu.edu)
*/


-- Query to select all platforms available on VGS by name.
SELECT PLATFORM.Platform_Name
FROM PLATFORM, PLATFORM_TYPE
WHERE PLATFORM.Platform_Name = PLATFORM_TYPE.Platform_Name;


-- Query to select all available titles, their description, ESRB Rating, and platform available on VGS
SELECT TITLE.Title_Name, TITLE.Description, TITLE.ESRB_Rating, PLATFORM.Platform_Name
FROM TITLE, PLATFORM
ORDER BY TITLE.Title_Name ASC;


-- Query to select all current account members by member_id, full name, DOB that displays credit card number
-- subscription activity, account email and platofrms used.
SELECT ACCOUNT.Member_ID, CUSTOMER.First_Name, CUSTOMER.Last_Name, CUSTOMER.Birthdate, 
	   ACCOUNT.Credit_Card_Number, ACCOUNT.Subscription, ACCOUNT.Email, PLATFORM.Platform_Name
FROM ACCOUNT, PLATFORM, CUSTOMER
WHERE ACCOUNT.Member_ID = CUSTOMER.Member_ID
ORDER BY ACCOUNT.Member_ID ASC;


-- Query to select games played by users in their user library.  Retrieved using UPC, Member_ID, Date Added, 
-- and Date Removed from library. Further, selection provides information about the title including name, description,
-- year of release, ESRB Rating, Metacritic Score, Genre, Genre Description, and Platform.  
SELECT USER_LIBRARY.UPC, USER_LIBRARY.Member_ID, USER_LIBRARY.Date_Added, USER_LIBRARY.Date_Removed, 
	   TITLE.Title_Name, TITLE.Description, TITLE.Year_Of_Release, TITLE.ESRB_Rating, TITLE.Metacritic_Rating, 
	   TITLE.Genre_Name, TITLE.Genre_Description, PLATFORM.Platform_Name 
FROM USER_LIBRARY, PLATFORM, TITLE 
WHERE USER_LIBRARY.UPC = TITLE.UPC AND PLATFORM.UPC = TITLE.UPC;


--  Query to select all titles added to user-library for user "TheCrow".
SELECT acct.Member_ID, t.Title_Name
FROM TITLE t
	JOIN USER_LIBRARY ul ON ul.UPC = t.UPC
	JOIN ACCOUNT acct ON acct.Member_ID = ul.Member_ID
WHERE acct.Member_ID = 'TheCrow';


-- Query to Select all saved game files for user "TheDestroyer85" that are actively in ther user's library and show game platform(s)
SELECT acct.Member_ID, t.Title_Name, p.Platform_Name
FROM  TITLE t
	JOIN USER_LIBRARY ul ON ul.UPC = t.UPC 
	JOIN SAVE_GAME_FILE sgf ON sgf.UPC = ul.UPC
	JOIN PLATFORM p ON p.Serial_Number = sgf.Serial_Number 
	JOIN ACCOUNT acct ON acct.Member_ID = ul.Member_ID
WHERE acct.Member_ID = "TheDestroyer85" 
	AND t.UPC = sgf.UPC
GROUP BY t.Title_Name, p.Platform_Name;


