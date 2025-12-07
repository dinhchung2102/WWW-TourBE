-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.5.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for springcloud
CREATE DATABASE IF NOT EXISTS `tour_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci */;
USE `tour_db`;

-- Dumping structure for table springcloud.booking
CREATE TABLE IF NOT EXISTS `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_date` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `number_of_people` int(11) DEFAULT NULL,
  `status` enum('PENDING','CONFIRMED','CANCELLED') NOT NULL,
  `total_price` double DEFAULT NULL,
  `tour_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table springcloud.booking: ~3 rows (approximately)
INSERT INTO `booking` (`id`, `booking_date`, `created_at`, `number_of_people`, `status`, `total_price`, `tour_id`, `user_id`) VALUES
	(65, '2025-05-17 07:00:00.000000', NULL, 1, 'PENDING', 35000000, 4, 2),
	(66, '2025-05-17 07:00:00.000000', NULL, 1, 'PENDING', 50000, 3, 2),
	(67, '2025-05-23 07:00:00.000000', NULL, 1, 'PENDING', 50000, 3, 3);

-- Dumping structure for table springcloud.customer
CREATE TABLE IF NOT EXISTS `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_provider` enum('LOCAL','GOOGLE','FACEBOOK') DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT current_timestamp(),
  `email` varchar(255) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `refresh_token` varchar(512) DEFAULT NULL,
  `role` enum('CUSTOMER','ADMIN','GUIDE') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dwk6cx0afu8bs9o4t536v1j5v` (`email`),
  UNIQUE KEY `UK_o3uty20c6csmx5y4uk2tc5r4m` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table springcloud.customer: ~4 rows (approximately)
INSERT INTO `customer` (`id`, `auth_provider`, `created_at`, `email`, `name`, `password`, `phone`, `refresh_token`, `role`) VALUES
(1, 'LOCAL', '2025-04-25 15:07:50', 'admin@gmail.com', 'Admin', '$2a$10$d83MqR46GE1UYBp5T8Kf1eYsayhMRV6C8CNAC1pRPYtj884eGE6wS', '0123456789', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaGFudHJ1b25ndHVhbjA0QGdtYWlsLmNvbSIsImlhdCI6MTc0NTYyNzU2MywiZXhwIjoxNzQ4MjE5NTYzfQ.iFsXQCFWAECdx2FLZMX8R0F3diswItUFtdtTwhPvY_I', 'ADMIN'),
	(2, 'LOCAL', '2025-04-25 15:07:50', 'phantruongtuan04@gmail.com', 'Phan Tuaans', '$2a$10$d83MqR46GE1UYBp5T8Kf1eYsayhMRV6C8CNAC1pRPYtj884eGE6wS', '0123456789', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaGFudHJ1b25ndHVhbjA0QGdtYWlsLmNvbSIsImlhdCI6MTc0NTYyNzU2MywiZXhwIjoxNzQ4MjE5NTYzfQ.iFsXQCFWAECdx2FLZMX8R0F3diswItUFtdtTwhPvY_I', 'CUSTOMER'),
	(3, 'LOCAL', '2025-04-25 18:31:41', 'truonghoanganh059@gmail.com', 'Hoang Anh', '$2a$10$NZboiMvqxlqSkF6Q5c4kr.Sth/9o0MJKgobAYGk9MNAW1DPh2k9pC', '0356782738', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0cnVvbmdob2FuZ2FuaDA1OUBnbWFpbC5jb20iLCJpYXQiOjE3NDU2Mjg4MTcsImV4cCI6MTc0ODIyMDgxN30.ey3vNOwZzRkDUg_zckvTOEFrMxsoXSVjYMzkn8OXtFI', 'CUSTOMER'),
	(4, 'LOCAL', '2025-04-25 19:04:52', 'phantruongtuan03@gmail.com', 'Phan Tuaans', '$2a$10$VxpxKryEAHiBv00HmO4ZYup9zYQdotp0e8JTRsp6DXu6kRJxxnW4W', '0365784748', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaGFudHJ1b25ndHVhbjAzQGdtYWlsLmNvbSIsImlhdCI6MTc0NTYyOTAwOSwiZXhwIjoxNzQ4MjIxMDA5fQ.Fg1WmpEWbR62EeI9DDmfZ9gBcStYzS--XE6N5lU1VfM', 'CUSTOMER'),
	(5, 'GOOGLE', '2025-04-26 00:59:44', 'boyproga47@gmail.com', 'dũng tiến', NULL, NULL, 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib3lwcm9nYTQ3QGdtYWlsLmNvbSIsImlhdCI6MTc0NTYyOTI2NCwiZXhwIjoxNzQ4MjIxMjY0fQ._Ly9ybc2Y8OcJh4EV-yvEl6mdsqm9XG8_QMrFdw1W1w', 'ADMIN');

-- Dumping structure for table springcloud.payments
CREATE TABLE IF NOT EXISTS `payments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `customer_email` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `payment_method` enum('MOMO','VNPAY') DEFAULT NULL,
  `payment_url` varchar(255) DEFAULT NULL,
  `response_code` varchar(255) DEFAULT NULL,
  `response_message` varchar(255) DEFAULT NULL,
  `status` enum('PENDING','COMPLETED','FAILED','CANCELLED') DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table springcloud.payments: ~0 rows (approximately)

-- Dumping structure for table springcloud.tour
CREATE TABLE IF NOT EXISTS `tour` (
  `id_tour` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `max_participants` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_tour`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Dumping data for table springcloud.tour: ~15 rows (approximately)
INSERT INTO `tour` (`id_tour`, `created_at`, `description`, `duration`, `end_date`, `location`, `max_participants`, `price`, `start_date`, `title`, `image`) VALUES
	(3, '2025-03-01 15:42:15.000000', 'dcea', 10, '2025-03-28 15:42:54.000000', 'Phu Yen', 20, 50000, '2025-03-18 15:43:19.000000', 'Du lich Phu Yen', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609065/Cac-Tour-Dulich-Tron_cgiu7d.jpg'),
	(4, '2025-03-02 17:15:30.000000', 'Khám phá biển và núi', 7, '2025-04-09 00:00:00.000000', 'Da Nang', 15, 35000000, '2025-04-01 16:00:00.000000', 'Du lich Da Nang', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609265/tour-du-lich-mien-trung-nen-di-vao-thoi-gian-nao-la-dep-nhat-min_elvbpv.jpg'),
	(5, '2025-03-03 21:20:10.000000', 'Vịnh Hạ Long tuyệt đẹp', 5, '2025-05-15 23:45:00.000000', 'Quang Ninh', 25, 40000000, '2025-05-10 14:30:00.000000', 'Du lich Ha Long', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609265/Koh-Phi-Phi_xja5ko.jpg'),
	(6, '2025-03-04 16:10:25.000000', 'Tham quan ruộng bậc thang', 4, '2025-06-20 01:00:00.000000', 'Lao Cai', 10, 28000000, '2025-06-15 13:00:00.000000', 'Du lich Sapa', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609265/tour-la-gi_rxlbzi.webp'),
	(7, '2025-03-05 18:35:40.000000', 'Biển xanh cát trắng', 6, '2025-07-27 00:30:00.000000', 'Khanh Hoa', 30, 45000000, '2025-07-20 15:15:00.000000', 'Du lich Nha Trang', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609265/Koh-Phi-Phi_xja5ko.jpg'),
	(8, '2025-03-06 20:50:15.000000', 'Khám phá cố đô', 3, '2025-08-08 23:00:00.000000', 'Thua Thien Hue', 12, 20000000, '2025-08-05 14:45:00.000000', 'Du lich Hue', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609065/Cac-Tour-Dulich-Tron_cgiu7d.jpg'),
	(9, '2025-03-07 16:25:45.000000', 'Thành phố ngàn hoa', 5, '2025-09-07 00:00:00.000000', 'Lam Dong', 18, 30000000, '2025-09-01 15:00:00.000000', 'Du lich Da Lat', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609265/tour-la-gi_rxlbzi.webp'),
	(10, '2025-03-08 18:40:20.000000', 'Chợ nổi Cái Răng', 3, '2025-10-13 23:30:00.000000', 'Can Tho', 20, 18000000, '2025-10-10 14:00:00.000000', 'Du lich Can Tho', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609265/tour-la-gi_rxlbzi.webp'),
	(11, '2025-03-09 21:15:10.000000', 'Đảo ngọc thiên đường', 4, '2025-11-20 01:00:00.000000', 'Kien Giang', 25, 45000000, '2025-11-15 16:30:00.000000', 'Du lich Phu Quoc', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609265/tour-du-lich-nha-trang-1_hptuyc.jpg'),
	(12, '2025-03-10 17:30:55.000000', 'Phố cổ đèn lồng', 3, '2025-12-09 00:45:00.000000', 'Quang Nam', 15, 25000000, '2025-12-05 15:15:00.000000', 'Du lich Hoi An', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609265/tour-la-gi_rxlbzi.webp'),
	(13, '2025-03-11 20:20:35.000000', 'Thủ đô ngàn năm văn hiến', 6, '2025-12-26 23:00:00.000000', 'Ha Noi', 22, 35000000, '2025-12-20 14:45:00.000000', 'Du lich Hanoi', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609265/Koh-Phi-Phi_xja5ko.jpg'),
	(14, '2025-03-07 16:25:45.000000', 'Thành phố ngàn hoa', 5, '2025-09-07 00:00:00.000000', 'Lam Dong', 18, 30000000, '2025-09-01 15:00:00.000000', 'Du lich Da Lat 2', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609265/tour-du-lich-mien-trung-nen-di-vao-thoi-gian-nao-la-dep-nhat-min_elvbpv.jpg'),
	(15, '2025-04-11 16:30:00.000000', 'Tham quan lăng tẩm và thành cổ', 5, '2025-05-07 00:00:00.000000', 'Hue', 20, 25000000, '2025-05-01 15:00:00.000000', 'Du lịch Huế', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609265/Koh-Phi-Phi_xja5ko.jpg'),
	(16, '2025-04-11 16:30:00.000000', 'Tham quan lăng tẩm và thành cổ', 5, '2025-05-07 00:00:00.000000', 'Hue', 20, 25000000, '2025-05-01 15:00:00.000000', 'Du lịch', 'https://res.cloudinary.com/dgioc0umy/image/upload/v1745609265/tour-du-lich-nha-trang-1_hptuyc.jpg'),
	(17, '2025-04-11 16:00:00.000000', 'Thăm cố đô', 3, '2025-06-04 00:00:00.000000', 'Hue', 15, 20000000, '2025-06-01 16:00:00.000000', 'Tour Hue', 'https://res.cloudinary.com/dj7l7khdy/image/upload/v1745418597/cafmiondsaw9ptyzlxxs.jpg');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
