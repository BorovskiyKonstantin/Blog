DROP DATABASE IF EXISTS blog;
CREATE DATABASE blog;
use blog;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_moderator` tinyint NOT NULL,
  `reg_time` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `code` varchar(255),
  `photo` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `posts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `is_active` tinyint NOT NULL,
  `moderation_status` varchar(45) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NEW',
  `moderator_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `time` datetime NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `text` text COLLATE utf8mb4_general_ci NOT NULL,
  `view_count` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `post_votes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `post_id` int NOT NULL,
  `time` datetime NOT NULL,
  `value` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`),
  KEY `posts_idx` (`post_id`),
  CONSTRAINT `posts` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tags` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  UNIQUE KEY `name_UNIQUE` (`name`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tag2post` (
  `id` int NOT NULL AUTO_INCREMENT,
  `post_id` int NOT NULL,
  `tag_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `post_idx` (`post_id`),
  KEY `tag_idx` (`tag_id`),
  CONSTRAINT `post` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `tag` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `post_comments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `parent_id` int,
  `user_id` int NOT NULL,
  `post_id` int NOT NULL,
  `time` datetime NOT NULL,
  `text` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_idx` (`user_id`),
  KEY `post_id fk_idx` (`post_id`),
  KEY `parent_idfk_idx` (`parent_id`),
  CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `post_id fk` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
  CONSTRAINT `parent_idfk` FOREIGN KEY (`parent_id`) REFERENCES `post_comments` (`id`)
) ENGINE=InnoDB ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `captcha_codes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` tinytext NOT NULL,
  `secret_code` tinytext NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `global_settings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO global_settings(code,name,value) VALUES
('MULTIUSER_MODE','Многопользовательский режим','YES'),
('POST_PREMODERATION','Премодерация постов','YES'),
('STATISTICS_IN_PUBLIC','Показывать всем статистику блога','YES');