-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: defenselogix
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `allocations`
--

DROP TABLE IF EXISTS `allocations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `allocations` (
  `allocation_id` int NOT NULL AUTO_INCREMENT,
  `request_id` int NOT NULL,
  `resource_type` enum('PERSONNEL','VEHICLE','EQUIPMENT','SUPPLY') NOT NULL,
  `resource_id` int NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  `assigned_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('ACTIVE','RELEASED') NOT NULL DEFAULT 'ACTIVE',
  PRIMARY KEY (`allocation_id`),
  KEY `request_id` (`request_id`),
  CONSTRAINT `allocations_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `logistics_requests` (`request_id`),
  CONSTRAINT `allocations_chk_1` CHECK ((`quantity` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `allocations`
--

LOCK TABLES `allocations` WRITE;
/*!40000 ALTER TABLE `allocations` DISABLE KEYS */;
/*!40000 ALTER TABLE `allocations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipment` (
  `equipment_id` int NOT NULL AUTO_INCREMENT,
  `equipment_name` varchar(100) NOT NULL,
  `location` varchar(100) NOT NULL,
  `condition_status` enum('GOOD','FAIR','DAMAGED') NOT NULL DEFAULT 'GOOD',
  `status` enum('AVAILABLE','ASSIGNED','MAINTENANCE') NOT NULL DEFAULT 'AVAILABLE',
  PRIMARY KEY (`equipment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment`
--

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment` DISABLE KEYS */;
INSERT INTO `equipment` VALUES (1,'Generator','Location A','GOOD','AVAILABLE'),(2,'Portable Light','Location A','FAIR','AVAILABLE');
/*!40000 ALTER TABLE `equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `item_name` varchar(100) NOT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `minimum_stock` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_id`),
  UNIQUE KEY `item_name` (`item_name`),
  CONSTRAINT `inventory_chk_1` CHECK ((`quantity` >= 0)),
  CONSTRAINT `inventory_chk_2` CHECK ((`minimum_stock` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,'First Aid Kit',50,10),(2,'Water Container',100,20),(3,'Safety Gloves',8,15);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logistics_requests`
--

DROP TABLE IF EXISTS `logistics_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `logistics_requests` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `unit_id` int NOT NULL,
  `requested_by` int NOT NULL,
  `purpose` varchar(255) NOT NULL,
  `request_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('PENDING','APPROVED','REJECTED','ASSIGNED','IN_PROGRESS','COMPLETED') NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`request_id`),
  KEY `unit_id` (`unit_id`),
  KEY `requested_by` (`requested_by`),
  CONSTRAINT `logistics_requests_ibfk_1` FOREIGN KEY (`unit_id`) REFERENCES `units` (`unit_id`),
  CONSTRAINT `logistics_requests_ibfk_2` FOREIGN KEY (`requested_by`) REFERENCES `personnel` (`personnel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logistics_requests`
--

LOCK TABLES `logistics_requests` WRITE;
/*!40000 ALTER TABLE `logistics_requests` DISABLE KEYS */;
INSERT INTO `logistics_requests` VALUES (1,1,3,'Request for transport and equipment','2026-10-10 07:27:21','PENDING');
/*!40000 ALTER TABLE `logistics_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `maintenance`
--

DROP TABLE IF EXISTS `maintenance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maintenance` (
  `maintenance_id` int NOT NULL AUTO_INCREMENT,
  `vehicle_id` int DEFAULT NULL,
  `equipment_id` int DEFAULT NULL,
  `maintenance_type` enum('PREVENTIVE','CORRECTIVE') NOT NULL,
  `scheduled_date` date NOT NULL,
  `completed_date` date DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `status` enum('SCHEDULED','IN_PROGRESS','COMPLETED') NOT NULL DEFAULT 'SCHEDULED',
  PRIMARY KEY (`maintenance_id`),
  KEY `vehicle_id` (`vehicle_id`),
  KEY `equipment_id` (`equipment_id`),
  CONSTRAINT `maintenance_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`),
  CONSTRAINT `maintenance_ibfk_2` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`equipment_id`),
  CONSTRAINT `maintenance_chk_1` CHECK ((((`vehicle_id` is not null) and (`equipment_id` is null)) or ((`vehicle_id` is null) and (`equipment_id` is not null))))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintenance`
--

LOCK TABLES `maintenance` WRITE;
/*!40000 ALTER TABLE `maintenance` DISABLE KEYS */;
INSERT INTO `maintenance` VALUES (1,3,NULL,'PREVENTIVE','2026-10-15',NULL,'Scheduled vehicle service','SCHEDULED');
/*!40000 ALTER TABLE `maintenance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personnel`
--

DROP TABLE IF EXISTS `personnel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personnel` (
  `personnel_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) NOT NULL,
  `role` varchar(50) NOT NULL,
  `unit_id` int NOT NULL,
  `posting` varchar(100) DEFAULT NULL,
  `availability` enum('AVAILABLE','ASSIGNED','ON_LEAVE') NOT NULL DEFAULT 'AVAILABLE',
  PRIMARY KEY (`personnel_id`),
  KEY `unit_id` (`unit_id`),
  CONSTRAINT `personnel_ibfk_1` FOREIGN KEY (`unit_id`) REFERENCES `units` (`unit_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personnel`
--

LOCK TABLES `personnel` WRITE;
/*!40000 ALTER TABLE `personnel` DISABLE KEYS */;
INSERT INTO `personnel` VALUES (1,'Person A','Driver',1,'Location A','AVAILABLE'),(2,'Person B','Technician',1,'Location A','AVAILABLE'),(3,'Person C','Coordinator',2,'Location B','AVAILABLE');
/*!40000 ALTER TABLE `personnel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request_items`
--

DROP TABLE IF EXISTS `request_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request_items` (
  `request_item_id` int NOT NULL AUTO_INCREMENT,
  `request_id` int NOT NULL,
  `resource_type` enum('PERSONNEL','VEHICLE','EQUIPMENT','SUPPLY') NOT NULL,
  `description` varchar(150) NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  `item_id` int DEFAULT NULL,
  PRIMARY KEY (`request_item_id`),
  KEY `request_id` (`request_id`),
  KEY `item_id` (`item_id`),
  CONSTRAINT `request_items_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `logistics_requests` (`request_id`),
  CONSTRAINT `request_items_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `inventory` (`item_id`),
  CONSTRAINT `request_items_chk_1` CHECK ((`quantity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request_items`
--

LOCK TABLES `request_items` WRITE;
/*!40000 ALTER TABLE `request_items` DISABLE KEYS */;
INSERT INTO `request_items` VALUES (1,1,'VEHICLE','Transport vehicle',1,NULL),(2,1,'EQUIPMENT','Generator',1,NULL),(3,1,'SUPPLY','First Aid Kit',5,NULL);
/*!40000 ALTER TABLE `request_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `units` (
  `unit_id` int NOT NULL AUTO_INCREMENT,
  `unit_name` varchar(100) NOT NULL,
  `location` varchar(100) NOT NULL,
  PRIMARY KEY (`unit_id`),
  UNIQUE KEY `unit_name` (`unit_name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units`
--

LOCK TABLES `units` WRITE;
/*!40000 ALTER TABLE `units` DISABLE KEYS */;
INSERT INTO `units` VALUES (1,'Unit Alpha','Location A'),(2,'Unit Bravo','Location B');
/*!40000 ALTER TABLE `units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicles` (
  `vehicle_id` int NOT NULL AUTO_INCREMENT,
  `vehicle_type` varchar(50) NOT NULL,
  `location` varchar(100) NOT NULL,
  `status` enum('AVAILABLE','ASSIGNED','MAINTENANCE') NOT NULL DEFAULT 'AVAILABLE',
  PRIMARY KEY (`vehicle_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
/*!40000 ALTER TABLE `vehicles` DISABLE KEYS */;
INSERT INTO `vehicles` VALUES (1,'Transport Van','Location A','AVAILABLE'),(2,'Utility Truck','Location A','ASSIGNED'),(3,'Service Van','Location B','MAINTENANCE');
/*!40000 ALTER TABLE `vehicles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-10 18:37:54
