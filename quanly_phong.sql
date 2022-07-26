-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 26, 2022 at 12:26 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quanly_phong`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_role`
--

CREATE TABLE `tbl_role` (
  `id` bigint(20) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_role`
--

INSERT INTO `tbl_role` (`id`, `role_name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_room`
--

CREATE TABLE `tbl_room` (
  `id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `room_description` varchar(255) DEFAULT NULL,
  `room_image` varchar(255) DEFAULT NULL,
  `room_name` varchar(255) DEFAULT NULL,
  `room_price` double DEFAULT NULL,
  `room_status` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_room`
--

INSERT INTO `tbl_room` (`id`, `created_date`, `room_description`, `room_image`, `room_name`, `room_price`, `room_status`, `updated_date`, `user_id`) VALUES
(1, NULL, 'akm', 'http://localhost:8080/cy-market-place.png', '7021124âaa', 1284, 'RENTED', '2022-07-26 17:11:43', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_room_user`
--

CREATE TABLE `tbl_room_user` (
  `id` bigint(20) NOT NULL,
  `checkout_date` date DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `rented_price` double DEFAULT NULL,
  `room_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_room_user`
--

INSERT INTO `tbl_room_user` (`id`, `checkout_date`, `created_date`, `rented_price`, `room_id`, `user_id`) VALUES
(3, NULL, '2022-07-26 17:11:43', 1284, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE `tbl_user` (
  `id` bigint(20) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`id`, `created_date`, `email`, `password`, `updated_date`, `username`, `full_name`) VALUES
(1, NULL, 'admin@admin.com', '$2a$10$NMV6HHq5lgECYXK9WmQZHufB2d0mnR5pWcSr2slvNeYcCGX1p.by6', '2022-07-26 17:22:50', 'admin', NULL),
(2, NULL, 'user@admin.com', '$2a$10$kqixTxK7duH1fs9rW1HWwelwU9zNuxXdUD9y3oThfphgw7pGmtL2G', '2022-07-26 17:22:50', 'user', NULL),
(3, '2022-07-26 16:18:10', 'dfa@af.c', '$2a$10$yv3P1Lt8M1L35DQ01NfeE.Ci9S3RlprG9KOqXqKrEy/7X5ssBf2S.', '2022-07-26 16:18:10', 'asdaws', 'admin'),
(4, '2022-07-26 16:19:49', 'ad@af.co', '$2a$10$n7W9PJAxD7SBBOblo9Ihb.pu3qSCMtfz1fYqDwLc6BNpiZZaz.Rky', '2022-07-26 16:19:49', 'admina', 'wrf');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user_role_set`
--

CREATE TABLE `tbl_user_role_set` (
  `user_entity_set_id` bigint(20) NOT NULL,
  `role_set_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_user_role_set`
--

INSERT INTO `tbl_user_role_set` (`user_entity_set_id`, `role_set_id`) VALUES
(1, 1),
(1, 2),
(2, 2),
(3, 2),
(4, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_role`
--
ALTER TABLE `tbl_role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_room`
--
ALTER TABLE `tbl_room`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKeanbnp4dphty5bsmg8954hix5` (`user_id`);

--
-- Indexes for table `tbl_room_user`
--
ALTER TABLE `tbl_room_user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_user_role_set`
--
ALTER TABLE `tbl_user_role_set`
  ADD PRIMARY KEY (`user_entity_set_id`,`role_set_id`),
  ADD KEY `FKhxgmuwu1mu58g5r8fxnxgsniy` (`role_set_id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FK3vkrkdb0pqx66m62fn1ftjvfo` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_role`
--
ALTER TABLE `tbl_role`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_room`
--
ALTER TABLE `tbl_room`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_room_user`
--
ALTER TABLE `tbl_room_user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
