-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: a2_web
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
-- Table structure for table `bankaccount`
--

DROP TABLE IF EXISTS `bankaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bankaccount` (
  `BankAccountID` int NOT NULL,
  `BACreationDate` date DEFAULT NULL,
  `BACurrentBalance` varchar(45) DEFAULT NULL,
  `BACustomerID` int DEFAULT NULL,
  PRIMARY KEY (`BankAccountID`),
  KEY `BACustomerID` (`BACustomerID`),
  CONSTRAINT `bankaccount_ibfk_1` FOREIGN KEY (`BACustomerID`) REFERENCES `customer` (`CustomerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bankaccount`
--

LOCK TABLES `bankaccount` WRITE;
/*!40000 ALTER TABLE `bankaccount` DISABLE KEYS */;
INSERT INTO `bankaccount` VALUES (290,'2020-12-24','500',8),(429,'2020-12-22','1000',7),(440,'2020-12-01','4200',1),(506,'2020-12-22','316',6),(801,'2020-12-02','628',2),(829,'2020-12-01','1',3),(993,'2020-12-14','355',5);
/*!40000 ALTER TABLE `bankaccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banktransaction`
--

DROP TABLE IF EXISTS `banktransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banktransaction` (
  `BankTransactionID` int NOT NULL,
  `BTCreationDate` date DEFAULT NULL,
  `BTAmount` varchar(45) DEFAULT NULL,
  `BTFromAccountID` int DEFAULT NULL,
  `BTToAccountID` int DEFAULT NULL,
  PRIMARY KEY (`BankTransactionID`),
  KEY `BTFromAccountID_idx` (`BTFromAccountID`,`BTToAccountID`),
  KEY `BTToAccountID` (`BTToAccountID`),
  CONSTRAINT `banktransaction_ibfk_1` FOREIGN KEY (`BTFromAccountID`) REFERENCES `bankaccount` (`BankAccountID`),
  CONSTRAINT `banktransaction_ibfk_2` FOREIGN KEY (`BTToAccountID`) REFERENCES `bankaccount` (`BankAccountID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banktransaction`
--

LOCK TABLES `banktransaction` WRITE;
/*!40000 ALTER TABLE `banktransaction` DISABLE KEYS */;
INSERT INTO `banktransaction` VALUES (123,'2020-12-25','3000',801,440),(146,'2020-12-18','2634',440,801),(291,'2020-12-14','645',993,440),(573,'2020-12-24','500',290,440),(658,'2020-12-12','50',440,993),(791,'2020-12-27','6',801,440),(797,'2020-12-22','684',506,440),(915,'2020-12-18','999',829,440);
/*!40000 ALTER TABLE `banktransaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `CustomerID` int NOT NULL,
  `CustomerName` varchar(45) DEFAULT NULL,
  `CustomerAddress` varchar(45) DEFAULT NULL,
  `CustomerMobile` varchar(45) DEFAULT NULL,
  `CustomerPassword` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CustomerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Tawfik','Cairo','011','123456'),(2,'Ahmed','Giza','012','1234'),(3,'Mohamed','Alex','015','123456789'),(4,'Kareem','Asuan','010','123'),(5,'Amir','Cairo','012','123'),(6,'Moaz','Cairo','010','12'),(7,'Karem','Gza','010','7'),(8,'mohamed','cairo','010','8'),(9,'Mazen','Cairo','011','9');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-27 11:06:10
