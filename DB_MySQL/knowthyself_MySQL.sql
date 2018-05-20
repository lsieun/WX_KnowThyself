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

INSERT INTO `tb_area`(`area_name`,`priority`) VALUES('西双版纳','3');
INSERT INTO `tb_area`(`area_name`,`priority`) VALUES('呼伦贝尔','2');

SELECT * FROM `tb_area` ORDER BY `priority` ASC;

SELECT area_id, area_name,
priority, create_time, last_edit_time
FROM tb_area
ORDER BY priority
DESC

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user`(
	`uid` VARCHAR(128) NOT NULL COMMENT '用户ID',
	`wxopenid` VARCHAR(128) NOT NULL COMMENT '微信openid',
	`uname` VARCHAR(20) NOT NULL COMMENT '用户姓名',
	`ugender` INT(1) NOT NULL DEFAULT '0' COMMENT '用户性别（0-保密，1-男，2-女）',
	`uavatar` VARCHAR(128) DEFAULT NULL COMMENT '用户头像',
	`create_time` DATETIME DEFAULT NOW() NOT NULL COMMENT '创建时间',
	`last_edit_time` DATETIME DEFAULT NULL COMMENT '更新时间',
	`is_valid` INT(1) DEFAULT '1' NOT NULL COMMENT '是否有效（0-无效，1-有效）',
	PRIMARY KEY(`uid`),
	UNIQUE KEY `UK_OPENID`(`wxopenid`)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='用户表';

SELECT * FROM tb_user WHERE uid = '441517664024657920';

SELECT * FROM tb_user;

SELECT NOW();

CREATE TABLE `tb_task`(
	`uid` VARCHAR(128) NOT NULL COMMENT '任务ID',
	`userid` VARCHAR(128) NOT NULL COMMENT '用户ID',
	`name` VARCHAR(20) NOT NULL COMMENT '任务名称',
	`priority` INT(1) NOT NULL DEFAULT '0' COMMENT '优先等级（0-重要且紧急，1-重要不紧急，2-紧急不重要，3-不紧急不重要）',
	`start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
	`end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
	`status` INT(1) NOT NULL DEFAULT '0' COMMENT '是否完成（0-未完成，1-完成）',
	`create_time` DATETIME DEFAULT NOW() NOT NULL COMMENT '创建时间',
	`last_edit_time` DATETIME DEFAULT NULL COMMENT '更新时间',
	`is_valid` INT(1) DEFAULT '1' NOT NULL COMMENT '是否有效（0-无效，1-有效）',
	PRIMARY KEY(`uid`)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='任务表';

SELECT * FROM `tb_task`;


SELECT * FROM `tb_task` WHERE DATE_FORMAT(start_time,'%Y-%m-%d') <= '2018-05-03' AND DATE_FORMAT(end_time,'%Y-%m-%d') >= '2018-05-03'

SELECT DATE_FORMAT(start_time,'%Y-%m-%d'), DATE_FORMAT(end_time,'%Y-%m-%d') FROM `tb_task`

SELECT * FROM `tb_task` WHERE userid = '441517664024657920';

UPDATE `tb_task` SET is_valid = 1 WHERE userid = '441517664024657920';

DROP TABLE `tb_timeline`;

CREATE TABLE `tb_timeline`(
	`uid` VARCHAR(128) NOT NULL COMMENT '时间线ID',
	`userid` VARCHAR(128) NOT NULL COMMENT '用户ID',
	`name` VARCHAR(20) NOT NULL COMMENT '时间线名称',
	`timeline_type` VARCHAR(128) NOT NULL DEFAULT '0' COMMENT '时间线类型（0-未分类，其他-用户自建类型）',
	`start_time` DATETIME DEFAULT NULL COMMENT '开始时间',
	`end_time` DATETIME DEFAULT NULL COMMENT '结束时间',
	`create_time` DATETIME DEFAULT NOW() NOT NULL COMMENT '创建时间',
	`last_edit_time` DATETIME DEFAULT NULL COMMENT '更新时间',
	`is_valid` INT(1) DEFAULT '1' NOT NULL COMMENT '是否有效（0-无效，1-有效）',
	PRIMARY KEY(`uid`)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='时间线表';

CREATE TABLE `tb_timeline_type`(
	`uid` VARCHAR(128) NOT NULL COMMENT '时间线类型ID',
	`userid` VARCHAR(128) NOT NULL COMMENT '用户ID',
	`name` VARCHAR(20) NOT NULL COMMENT '时间线类型名称',
	`create_time` DATETIME DEFAULT NOW() NOT NULL COMMENT '创建时间',
	`last_edit_time` DATETIME DEFAULT NULL COMMENT '更新时间',
	`is_valid` INT(1) DEFAULT '1' NOT NULL COMMENT '是否有效（0-无效，1-有效）',
	PRIMARY KEY(`uid`)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='时间线类型表';

SELECT * FROM `tb_timeline_type`

SELECT * FROM `tb_timeline`

CREATE TABLE `tb_drucker`(
	`uid` VARCHAR(128) NOT NULL COMMENT 'ID',
	`userid` VARCHAR(128) NOT NULL COMMENT '用户ID',
	`title` VARCHAR(50) NOT NULL COMMENT '标题',
	`author` VARCHAR(20)  NULL DEFAULT '' COMMENT '作者',
	`url` VARCHAR(256) NOT NULL COMMENT 'URL',
	`desc` VARCHAR(500)  NULL DEFAULT '' COMMENT '描述',
	`pub_time` DATETIME DEFAULT NULL COMMENT '发布时间',
	`create_time` DATETIME DEFAULT NOW() NOT NULL COMMENT '创建时间',
	`last_edit_time` DATETIME DEFAULT NULL COMMENT '更新时间',
	`is_valid` INT(1) DEFAULT '1' NOT NULL COMMENT '是否有效（0-无效，1-有效）',
	PRIMARY KEY(`uid`)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='德鲁克表';

SELECT * FROM `tb_drucker`;














