-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Dec 21, 2021 at 07:49 AM
-- Server version: 8.0.18
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel_inventory`
--

-- --------------------------------------------------------

--
-- Table structure for table `floor`
--

DROP TABLE IF EXISTS `floor`;
CREATE TABLE IF NOT EXISTS `floor` (
  `floor_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `modified_date` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `floor_name` varchar(50) NOT NULL,
  `hotel_id` bigint(20) NOT NULL,
  PRIMARY KEY (`floor_id`),
  KEY `FKd26afg150j839r0c71k8aogng` (`hotel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `floor`
--

INSERT INTO `floor` (`floor_id`, `created_by`, `created_date`, `modified_by`, `modified_date`, `status`, `floor_name`, `hotel_id`) VALUES
(1, 1, '21-12-2021 13:13:35', NULL, NULL, 1, 'First Floor', 1),
(2, 1, '21-12-2021 13:13:35', NULL, NULL, 1, 'Second Floor', 1),
(3, 1, '21-12-2021 13:13:35', NULL, NULL, 1, 'First Floor', 2),
(4, 1, '21-12-2021 13:13:35', NULL, NULL, 1, 'Second Floor', 2);

-- --------------------------------------------------------

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
CREATE TABLE IF NOT EXISTS `hotel` (
  `hotel_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `modified_date` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `hotel_name` varchar(100) NOT NULL,
  PRIMARY KEY (`hotel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `hotel`
--

INSERT INTO `hotel` (`hotel_id`, `created_by`, `created_date`, `modified_by`, `modified_date`, `status`, `hotel_name`) VALUES
(1, 1, '21-12-2021 13:13:35', NULL, NULL, 1, 'Marriott'),
(2, 1, '21-12-2021 13:13:35', NULL, NULL, 1, 'ITC Royal Bengal');

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE IF NOT EXISTS `room` (
  `room_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `modified_date` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `room_number` varchar(10) NOT NULL,
  `floor_id` bigint(20) NOT NULL,
  `hotel_id` bigint(20) NOT NULL,
  `roomtype_id` bigint(20) NOT NULL,
  PRIMARY KEY (`room_id`),
  KEY `FKstlo96g0nkwp4urd4e0ki5b3h` (`floor_id`),
  KEY `FKdosq3ww4h9m2osim6o0lugng8` (`hotel_id`),
  KEY `FK92wwx13iwxnrx4g08dv6dpd2e` (`roomtype_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `room_type`
--

DROP TABLE IF EXISTS `room_type`;
CREATE TABLE IF NOT EXISTS `room_type` (
  `roomtype_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` bigint(20) DEFAULT NULL,
  `created_date` varchar(255) DEFAULT NULL,
  `modified_by` bigint(20) DEFAULT NULL,
  `modified_date` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `roomtype_name` varchar(50) NOT NULL,
  `hotel_id` bigint(20) NOT NULL,
  PRIMARY KEY (`roomtype_id`),
  KEY `FK8sgnny12n0v74j6u7u94w7mxp` (`hotel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `room_type`
--

INSERT INTO `room_type` (`roomtype_id`, `created_by`, `created_date`, `modified_by`, `modified_date`, `status`, `description`, `roomtype_name`, `hotel_id`) VALUES
(1, 1, '21-12-2021 13:13:35', NULL, NULL, 1, 'AC Room', 'Delux', 1),
(2, 1, '21-12-2021 13:13:35', NULL, NULL, 1, 'Non AC Room', 'Semi-Delux', 1),
(3, 1, '21-12-2021 13:13:35', NULL, NULL, 1, 'AC Room', 'Delux', 2),
(4, 1, '21-12-2021 13:13:35', NULL, NULL, 1, 'Non AC Room', 'Semi-Delux', 2);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `floor`
--
ALTER TABLE `floor`
  ADD CONSTRAINT `FKd26afg150j839r0c71k8aogng` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`hotel_id`);

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FK92wwx13iwxnrx4g08dv6dpd2e` FOREIGN KEY (`roomtype_id`) REFERENCES `room_type` (`roomtype_id`),
  ADD CONSTRAINT `FKdosq3ww4h9m2osim6o0lugng8` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`hotel_id`),
  ADD CONSTRAINT `FKstlo96g0nkwp4urd4e0ki5b3h` FOREIGN KEY (`floor_id`) REFERENCES `floor` (`floor_id`);

--
-- Constraints for table `room_type`
--
ALTER TABLE `room_type`
  ADD CONSTRAINT `FK8sgnny12n0v74j6u7u94w7mxp` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`hotel_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
