CREATE DATABASE `knowthyself` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE `knowthyself`;

CREATE TABLE `tb_area`(
	`area_id` INT(2) NOT NULL AUTO_INCREMENT,
	`area_name` VARCHAR(200) NOT NULL,
	`priority` INT(2) NOT NULL DEFAULT '0',
	`create_time` DATETIME DEFAULT NOW(),
	`last_edit_time` DATETIME DEFAULT NULL,
	PRIMARY KEY(`area_id`),
	UNIQUE KEY `UK_AREA`(`area_name`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

SELECT area_id, area_name,
priority, create_time, last_edit_time
FROM tb_area
ORDER BY priority
DESC


