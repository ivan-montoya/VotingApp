-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: votingApp
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `candidate`
--

DROP TABLE IF EXISTS `candidate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidate` (
  `description` varchar(50) NOT NULL,
  `thread_ID` int NOT NULL,
  `username` varchar(30) NOT NULL,
  `votes` int DEFAULT NULL,
  PRIMARY KEY (`thread_ID`,`username`,`description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidate`
--

LOCK TABLES `candidate` WRITE;
/*!40000 ALTER TABLE `candidate` DISABLE KEYS */;
INSERT INTO `candidate` VALUES ('Bobs Burgers',0,'imontoya',1),('Burger King',0,'imontoya',0),('McDonalds',0,'imontoya',0),('Red Robins',0,'imontoya',0),('a',0,'tigery69',0),('b',0,'tigery69',0),('c',0,'tigery69',0),('d',0,'tigery69',0),('e',0,'tigery69',0),('A',1,'imontoya',0),('B',1,'imontoya',0),('C',1,'imontoya',0),('A',2,'imontoya',1),('B',2,'imontoya',0),('C',2,'imontoya',0),('A',3,'imontoya',1),('B',3,'imontoya',0),('C',3,'imontoya',0),('D',3,'imontoya',0),('UW',4,'imontoya',0),('WSU',4,'imontoya',1),('a',5,'imontoya',0),('b',5,'imontoya',0),('c',5,'imontoya',0),('d',5,'imontoya',0),('a',6,'imontoya',0),('b',6,'imontoya',0),('c',6,'imontoya',0),('d',6,'imontoya',0);
/*!40000 ALTER TABLE `candidate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupmember`
--

DROP TABLE IF EXISTS `groupmember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groupmember` (
  `group_name` varchar(20) NOT NULL,
  `group_creator` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  PRIMARY KEY (`group_name`,`group_creator`,`username`),
  KEY `username` (`username`),
  CONSTRAINT `groupmember_ibfk_1` FOREIGN KEY (`username`) REFERENCES `registereduser` (`username`),
  CONSTRAINT `groupmember_ibfk_2` FOREIGN KEY (`group_name`, `group_creator`) REFERENCES `privategroup` (`group_name`, `creator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupmember`
--

LOCK TABLES `groupmember` WRITE;
/*!40000 ALTER TABLE `groupmember` DISABLE KEYS */;
INSERT INTO `groupmember` VALUES ('Raptors','imontoya','AliceAlice'),('WSU Friends','imontoya','AliceAlice'),('Raptors','imontoya','imontoya'),('WSU Friends','imontoya','imontoya'),('Hallelujah','TerryTerry','MarcoMarco'),('Raptors','imontoya','MarcoMarco'),('Hallelujah','TerryTerry','TerryTerry'),('Raptors','imontoya','TerryTerry'),('WSU Friends','imontoya','TerryTerry'),('Raptors','imontoya','tigery7'),('WSU Friends','imontoya','tigery7');
/*!40000 ALTER TABLE `groupmember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groupthread`
--

DROP TABLE IF EXISTS `groupthread`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groupthread` (
  `group_name` varchar(20) NOT NULL,
  `group_creator` varchar(30) NOT NULL,
  `title` varchar(80) DEFAULT NULL,
  `thread_ID` int NOT NULL,
  `thread_creator` varchar(30) NOT NULL,
  PRIMARY KEY (`group_name`,`group_creator`,`thread_ID`,`thread_creator`),
  KEY `thread_creator` (`thread_creator`),
  CONSTRAINT `groupthread_ibfk_1` FOREIGN KEY (`group_name`, `group_creator`) REFERENCES `privategroup` (`group_name`, `creator`),
  CONSTRAINT `groupthread_ibfk_2` FOREIGN KEY (`thread_creator`) REFERENCES `registereduser` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groupthread`
--

LOCK TABLES `groupthread` WRITE;
/*!40000 ALTER TABLE `groupthread` DISABLE KEYS */;
INSERT INTO `groupthread` VALUES ('Raptors','imontoya','I Hope this works',2,'imontoya'),('Raptors','imontoya','Test Thread',3,'imontoya'),('Raptors','imontoya','Which school is better?',4,'imontoya'),('WSU Friends','imontoya','Group Thread',6,'imontoya');
/*!40000 ALTER TABLE `groupthread` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privategroup`
--

DROP TABLE IF EXISTS `privategroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `privategroup` (
  `group_name` varchar(20) NOT NULL,
  `creator` varchar(30) NOT NULL,
  PRIMARY KEY (`group_name`,`creator`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privategroup`
--

LOCK TABLES `privategroup` WRITE;
/*!40000 ALTER TABLE `privategroup` DISABLE KEYS */;
INSERT INTO `privategroup` VALUES ('Hallelujah','TerryTerry'),('Raptors','imontoya'),('WSU Friends','imontoya');
/*!40000 ALTER TABLE `privategroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registereduser`
--

DROP TABLE IF EXISTS `registereduser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registereduser` (
  `name` varchar(25) DEFAULT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `birthday` char(10) DEFAULT NULL,
  `user_since` timestamp NULL DEFAULT NULL,
  `numThread` int DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registereduser`
--

LOCK TABLES `registereduser` WRITE;
/*!40000 ALTER TABLE `registereduser` DISABLE KEYS */;
INSERT INTO `registereduser` VALUES ('Alice','AliceAlice','qwertyuiop','fdsaf','fdsafd','2021-01-06 23:27:46',0),('Ivan Montoya','imontoya','helloworld','example@wsu.edu','11-05-1996','2021-01-06 01:59:32',0),('Jose Zuniga','imontoya420','qwertyuiop','gmail@gmail.com','11-05-1996','2021-01-17 21:28:04',0),('fdsaf','MarcoMarco','qwertyuiop','fdsaf','fdsf','2021-01-06 23:28:01',0),('fdsaf','TerryTerry','qwertyuiop','fdsaf','fdsaf','2021-01-06 23:28:28',0),('Hello Kitty','tigery69','qwertyuiop','hellokitty@gmail.com','11/05/1996','2021-01-15 19:27:03',0),('Ivan Montoya','tigery7','helloworld','imontoya@gmail.com','11/05/1996','2021-01-06 01:39:22',0),('Ivan Montoya','tigery769','qwertyuiop','hello@example.edu','11-05-2000','2021-01-17 21:37:39',0);
/*!40000 ALTER TABLE `registereduser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread`
--

DROP TABLE IF EXISTS `thread`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thread` (
  `title` varchar(80) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `numCandidates` int DEFAULT NULL,
  `thread_ID` int NOT NULL,
  `date_created` timestamp NULL DEFAULT NULL,
  `username` varchar(30) NOT NULL,
  `private_status` varchar(5) DEFAULT NULL,
  `open_status` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`username`,`thread_ID`),
  CONSTRAINT `thread_ibfk_1` FOREIGN KEY (`username`) REFERENCES `registereduser` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread`
--

LOCK TABLES `thread` WRITE;
/*!40000 ALTER TABLE `thread` DISABLE KEYS */;
INSERT INTO `thread` VALUES ('Which restaurant has the best burgers?','I am incredibly bored during quarantine!',4,0,'2021-01-06 02:02:40','imontoya','FALSE','FALSE'),('Test Thread','How in the world does this work?',3,1,'2021-01-09 00:21:25','imontoya','TRUE','FALSE'),('I Hope this works','When does this stop?',3,2,'2021-01-09 00:23:56','imontoya','TRUE','FALSE'),('Test Thread','Test thread to make sure this works',4,3,'2021-01-15 01:17:09','imontoya','TRUE','TRUE'),('Which school is better?','I want to see if people prefer WSU or UW.',2,4,'2021-01-15 01:31:22','imontoya','FALSE','TRUE'),('Test 2','Just a test thread',4,5,'2021-01-15 19:16:31','imontoya','FALSE','TRUE'),('Group Thread','creatng another thread',4,6,'2021-01-15 19:17:24','imontoya','TRUE','TRUE'),('Test Thread 3','Why am I doing this?',5,0,'2021-01-15 19:28:11','tigery69','TRUE','TRUE');
/*!40000 ALTER TABLE `thread` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voted`
--

DROP TABLE IF EXISTS `voted`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voted` (
  `voter` varchar(30) NOT NULL,
  `creator` varchar(30) NOT NULL,
  `thread_ID` int NOT NULL,
  PRIMARY KEY (`voter`,`creator`,`thread_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voted`
--

LOCK TABLES `voted` WRITE;
/*!40000 ALTER TABLE `voted` DISABLE KEYS */;
INSERT INTO `voted` VALUES ('imontoya','imontoya',0),('imontoya','imontoya',2),('imontoya','imontoya',3),('imontoya','imontoya',4);
/*!40000 ALTER TABLE `voted` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-29 16:27:51
