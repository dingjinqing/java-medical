ALTER TABLE `b2c_anchor_points` MODIFY COLUMN `platform` varchar(20) NOT NULL DEFAULT '' COMMENT '平台  wxapp admin' AFTER `module`,
ALTER TABLE `b2c_anchor_points` MODIFY COLUMN `device` varchar(20) NOT NULL DEFAULT '' COMMENT '设备  android  ios   pc' AFTER `platform`;
