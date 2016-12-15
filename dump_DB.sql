-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ws_project
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `affiliations`
--

DROP TABLE IF EXISTS `affiliations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `affiliations` (
  `userEmail` varchar(255) DEFAULT NULL,
  `groupName` varchar(255) DEFAULT NULL,
  KEY `fk_affiliations_users` (`userEmail`),
  KEY `fk_affiliations_groups` (`groupName`),
  CONSTRAINT `fk_affiliations_groups` FOREIGN KEY (`groupName`) REFERENCES `groups` (`name`) ON DELETE CASCADE,
  CONSTRAINT `fk_affiliations_users` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `affiliations`
--

LOCK TABLES `affiliations` WRITE;
/*!40000 ALTER TABLE `affiliations` DISABLE KEYS */;
INSERT INTO `affiliations` VALUES ('maxime.makhloufi@telecom-st-etienne.fr','Web Services'),('guillaume.terpend@telecom-st-etienne.fr','Web Services'),('guillaume.terpend@telecom-st-etienne.fr','League of Legends'),('dali.mersel@telecom-st-etienne.fr','Heroes of the Storm'),('dali.mersel@telecom-st-etienne.fr','League of Legends'),('dali.mersel@telecom-st-etienne.fr','Web Services'),('robin.vanet@telecom-st-etienne.fr','I am looking for friends :))'),('maxime.makhloufi@telecom-st-etienne.fr','Come on my new Youtube channel');
/*!40000 ALTER TABLE `affiliations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `boards`
--

DROP TABLE IF EXISTS `boards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `boards` (
  `groupName` varchar(255) DEFAULT NULL,
  `userEmail` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  KEY `fk_boards_groups` (`groupName`),
  KEY `fk_boards_users` (`userEmail`),
  CONSTRAINT `fk_boards_groups` FOREIGN KEY (`groupName`) REFERENCES `groups` (`name`) ON DELETE CASCADE,
  CONSTRAINT `fk_boards_users` FOREIGN KEY (`userEmail`) REFERENCES `users` (`email`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boards`
--

LOCK TABLES `boards` WRITE;
/*!40000 ALTER TABLE `boards` DISABLE KEYS */;
INSERT INTO `boards` VALUES ('Web Services','maxime.makhloufi@telecom-st-etienne.fr','The user Maxime Makhloufi has joined the group Web Services!'),('Web Services','guillaume.terpend@telecom-st-etienne.fr','The user Guillaume Terpend has joined the group Web Services!'),('Web Services','guillaume.terpend@telecom-st-etienne.fr','Hi Maxime, do you want to play League of Legends with me today?'),('Web Services','maxime.makhloufi@telecom-st-etienne.fr','Sorry I can\'t, I have to finish my Web Service project :/'),('League of Legends','guillaume.terpend@telecom-st-etienne.fr','The user Guillaume Terpend has joined the group League of Legends!'),('Heroes of the Storm','dali.mersel@telecom-st-etienne.fr','The user Dali Mersel has joined the group Heroes of the Storm!'),('League of Legends','dali.mersel@telecom-st-etienne.fr','The user Dali Mersel has joined the group League of Legends!'),('League of Legends','dali.mersel@telecom-st-etienne.fr','LoL sucks ;)'),('Web Services','dali.mersel@telecom-st-etienne.fr','The user Dali Mersel has joined the group Web Services!'),('Web Services','dali.mersel@telecom-st-etienne.fr','My project is far better than yours hahahahaha!!!!'),('I am looking for friends :))','robin.vanet@telecom-st-etienne.fr','The user Robin Vanet has joined the group I am looking for friends :))!'),('Come on my new Youtube channel','maxime.makhloufi@telecom-st-etienne.fr','The user Maxime Makhloufi has joined the group Come on my new Youtube channel!');
/*!40000 ALTER TABLE `boards` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `groups`
--

DROP TABLE IF EXISTS `groups`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `groups` (
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `admin` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`),
  KEY `fk_groups_users` (`admin`),
  CONSTRAINT `fk_groups_users` FOREIGN KEY (`admin`) REFERENCES `users` (`email`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--

LOCK TABLES `groups` WRITE;
/*!40000 ALTER TABLE `groups` DISABLE KEYS */;
INSERT INTO `groups` VALUES ('Come on my new Youtube channel','','maxime.makhloufi@telecom-st-etienne.fr'),('Heroes of the Storm','HotS > LoL','dali.mersel@telecom-st-etienne.fr'),('I am looking for friends :))','(girl friends :D)','robin.vanet@telecom-st-etienne.fr'),('League of Legends','Welcome to Summoner\'s Rift!','guillaume.terpend@telecom-st-etienne.fr'),('Web Services','Come here if you like creating web services :)','maxime.makhloufi@telecom-st-etienne.fr');
/*!40000 ALTER TABLE `groups` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `email` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('dali.mersel@telecom-st-etienne.fr','Dali','Mersel','HotS > LoL :D'),('guillaume.terpend@telecom-st-etienne.fr','Guillaume','Terpend','I like playing League of Legends!'),('maxime.makhloufi@telecom-st-etienne.fr','Maxime','Makhloufi','I am a 22 year-old boy :)'),('robin.vanet@telecom-st-etienne.fr','Robin','Vanet','I am a ginger :\'(');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-15 16:44:02
