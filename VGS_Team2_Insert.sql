-- *** PROJECT TEAM 2 DELIVERABLE 3 INSERT DATA *** 
-- Adam Clifton (akclifto@asu.edu)
-- Anne Landrum (aelandru@asu.edu)
-- Ivan Fernandez (iafernan@asu.edu)
-- Robert Ibarra (rnibarra@asu.edu)

INSERT INTO GENRE 
(Genre_Name, Genre_Description)
VALUES
('Adventure','Solve puzzles by interacting with characters and the environment'), 
('Action-Adventure', 'Combat, explore, gather items, and solve puzzles'),
('Sports', 'Simulation of sports with AI and multiplayer'),
('Simulation', 'Closely simulates real life or fictional reality'),
('Survival Horror', 'Survive within a supernatural setting');

INSERT INTO TITLE
(UPC, Title_Name, Description, Year_Of_Release, ESRB_Rating, Metacritic_Rating, Genre_Name, Genre_Description)
VALUES
( 012345678985, 'The Last Of Us', 'Venture through a post apocalyptic United States as Joel to save Ellie.', 2013, 'Mature', 95, 'Survival Horror', 'Survive within a supernatural setting'),
(854123697852, 'God Of War', 'Norse Mythology game set in Norway.  Join Kratos and Atreus in their adventure.', 2018, 'Mature', 94, 'Action-Adventure',  'Combat, explore, gather items, and solve puzzles'),
(125479648532, 'Myst', 'First-person journey in which players interact with the environment to solve puzzles and advance.', 1993, 'Everyone', 69, 'Adventure', 'Solve puzzles by interacting with characters and the environment');

INSERT INTO PLATFORM
(Serial_Number, UPC, Platform_Name)
VALUES
(5482, 012345678985, 'Playstation 3'),
(6495, 854123697852, 'Playstation 4'),
(8245, 125479648532, 'PC');

INSERT INTO PLATFORM_TYPE
(Platform_Name, Serial_Number)
VALUES
('Playstation 3', 5482),
('Playstation 4', 6495),
('PC', 8245);

INSERT INTO ACCOUNT
(Account_PW, Member_ID, Credit_Card_Number, Subscription, 
       Email, Serial_Number)
VALUES
('IloveGaming45!', 'TheDestroyer85', 187245087634512, 1, 'TheDestroyer85@gmail.com', 5482),
('Pneuma8927&', 'ToolBand46&2', 132140987890345, 1, 'ToolBand46&2@gmail.com', 6495),
('HeroesofThestormlover86$', 'TheCrow', 264738290198435, 1, 'BrandonLee@yahoo.com', 8245);


INSERT INTO CUSTOMER
(Email, First_Name, Middle_Name, Last_Name, Birthdate, Account_PW, Member_ID)
VALUES
('TheDestroyer85@gmail.com', 'David', 'Thomas', 'Draiman', '1980/03/09', 'IloveGaming45!', 'TheDestroyer85'),
('ToolBand46&2@gmail.com', 'Maynard', 'James', 'Keenan', '1964/04/17', 'Pneuma8927&', 'ToolBand46&2'),
('BrandonLee@yahoo.com', 'Brandon', 'Bruce', 'Lee', '1965/02/01', 'HeroesofThestormlover86$', 'TheCrow');

INSERT INTO USER_LIBRARY
(UPC, Account_PW, Member_ID, Date_Added, Date_Removed)
VALUES
(012345678985, 'IloveGaming45!', 'TheDestroyer85', '2013/07/10', '2018/08/20'),
(012345678985, 'Pneuma8927&', 'ToolBand46&2', '2013/09/20', '2020/01/30'),
(854123697852, 'HeroesofThestormlover86$', 'TheCrow', '2018/04/20', '2020/05/10'),
(125479648532, 'HeroesofThestormlover86$', 'TheCrow', '2000/09/21', '2015/10/18');

INSERT INTO SAVE_GAME_FILE
(UPC, Account_PW, Member_ID, Start_Time, End_Time, Serial_Number)
VALUES
(125479648532, 'HeroesofThestormlover86$', 'TheCrow', '11:21:58', '23:18:41', 8245),
(854123697852, 'HeroesofThestormlover86$', 'TheCrow', '08:24:15', '19:18:11', 6495),
(012345678985, 'Pneuma8927&', 'ToolBand46&2', '15:08:04', '11:13:55', 5482),
(012345678985, 'IloveGaming45!', 'TheDestroyer85', '21:05:17', '00:18:35', 5482);


INSERT INTO LOGS_INTO
(Account_PW, Member_ID, Customer_Email)
VALUES
('IloveGaming45!', 'TheDestroyer85', 'TheDestroyer85@gmail.com'),
('Pneuma8927&', 'ToolBand46&2','ToolBand46&2@gmail.com'),
('HeroesofThestormlover86$', 'TheCrow', 'BrandonLee@yahoo.com');
