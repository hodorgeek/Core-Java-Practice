CREATE DATABASE IF NOT EXISTS test;
USE test;
SHOW tables;

CREATE TABLE IF NOT EXISTS Persons
(
P_Id int NOT NULL,
FirstName varchar(255)NOT NULL,
LastName varchar(255) NOT NULL,
Address varchar(255),
City varchar(255),
PRIMARY KEY (P_Id)
);

SELECT * FROM test.Persons;