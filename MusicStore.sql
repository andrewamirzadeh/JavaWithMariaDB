-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 21, 2021 at 10:43 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `music_store`
--
CREATE DATABASE IF NOT EXISTS `music_store` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `music_store`;

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `CustomerID` int(11) NOT NULL,
  `LastName` varchar(13) NOT NULL,
  `FirstName` varchar(13) NOT NULL,
  `Address` varchar(30) NOT NULL,
  `City` varchar(15) NOT NULL,
  `State` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`CustomerID`, `LastName`, `FirstName`, `Address`, `City`, `State`) VALUES
(29, 'Lipster', 'Grace', '398493 King', 'Dayton', 'Oh'),
(30, 'John', 'Hamiisss', '2939', 'Altamonte', 'FL'),
(31, 'Josh', 'Myers', '4343 State st', 'Apopka', 'FL'),
(32, 'Howard', 'Dwight', '3993 Myrtle Street', 'Daytona Beach', 'FL'),
(33, 'lskdjfld', 'slkjfd', 'slkdfjld', 'slkdjfd', 'ff'),
(34, 'Grossman', 'Rex', '309 wii', 'sdlkjd', 'IL'),
(35, 'Jake', 'Cutler', '9399', 'Denver', 'CO'),
(36, 'Nick', 'Folk', '9388 Philly Express', 'Philadelphia', 'PA'),
(37, 'Trubisky', 'Mitchel', '3999 Duke Way', 'Durham', 'NC'),
(38, 'Fields', 'Justin', '30988 Short North', 'Columbus', 'OH'),
(39, 'Flacco', 'Joe', '93 Blue Hen way', 'Dover', 'DE');

-- --------------------------------------------------------

--
-- Table structure for table `instruments`
--

CREATE TABLE `instruments` (
  `SerialNum` int(11) NOT NULL,
  `Make` varchar(10) NOT NULL,
  `Model` varchar(10) NOT NULL,
  `PurchasePrice` int(10) NOT NULL,
  `SellPrice` int(10) NOT NULL,
  `Sold` int(1) NOT NULL,
  `VendorID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `instruments`
--

INSERT INTO `instruments` (`SerialNum`, `Make`, `Model`, `PurchasePrice`, `SellPrice`, `Sold`, `VendorID`) VALUES
(1, 'Gibson', 'Hollowbody', 300, 4888, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `SaleID` int(11) NOT NULL,
  `CustomerID` int(11) NOT NULL,
  `SerialNum` int(11) NOT NULL,
  `VendorID` int(11) NOT NULL,
  `DateSold` varchar(11) NOT NULL,
  `SaleAmount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`SaleID`, `CustomerID`, `SerialNum`, `VendorID`, `DateSold`, `SaleAmount`) VALUES
(2, 34, 1, 1, '0000-00-00', 10000);

-- --------------------------------------------------------

--
-- Table structure for table `vendor`
--

CREATE TABLE `vendor` (
  `VendorID` int(11) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Address` varchar(30) NOT NULL,
  `City` varchar(15) NOT NULL,
  `State` varchar(2) NOT NULL,
  `LicensceNum` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vendor`
--

INSERT INTO `vendor` (`VendorID`, `Name`, `Address`, `City`, `State`, `LicensceNum`) VALUES
(1, 'Craig\'s Flutes', '7981 Courtesy Lane', 'Maitland', 'FL', '6200-939-7844'),
(4, 'Bass Central', '3998 Parkway Avenue', 'Melbourne', 'FL', '993-9393-9999'),
(5, 'Gloomy Guitars', '8883 Park Avenue', 'Apopka', 'FL', '3881');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`CustomerID`);

--
-- Indexes for table `instruments`
--
ALTER TABLE `instruments`
  ADD PRIMARY KEY (`SerialNum`),
  ADD KEY `VendorID` (`VendorID`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`SaleID`),
  ADD KEY `CustomerID` (`CustomerID`),
  ADD KEY `SerialNum` (`SerialNum`),
  ADD KEY `VendorID` (`VendorID`);

--
-- Indexes for table `vendor`
--
ALTER TABLE `vendor`
  ADD PRIMARY KEY (`VendorID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `CustomerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `SaleID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `vendor`
--
ALTER TABLE `vendor`
  MODIFY `VendorID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `instruments`
--
ALTER TABLE `instruments`
  ADD CONSTRAINT `instruments_ibfk_1` FOREIGN KEY (`VendorID`) REFERENCES `vendor` (`VendorID`);

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `customers` (`CustomerID`),
  ADD CONSTRAINT `sales_ibfk_2` FOREIGN KEY (`SerialNum`) REFERENCES `instruments` (`SerialNum`),
  ADD CONSTRAINT `sales_ibfk_3` FOREIGN KEY (`VendorID`) REFERENCES `vendor` (`VendorID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
