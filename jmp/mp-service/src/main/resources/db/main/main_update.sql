/***********************2.8********************BEGIN*/




/***********************2.8*********************END*/

/***********************2.10********************BEGIN*/
ALTER TABLE `b2c_shop` ADD COLUMN `expire_time` timestamp NULL COMMENT '到期时间';
/***********************2.10*********************END*/

/***********************2.11********************BEGIN*/
ALTER TABLE `b2c_shop` ADD COLUMN `store_clerk_privilege_list` TEXT NULL DEFAULT NULL COMMENT '门店店员权限列表';


CREATE TABLE IF NOT EXISTS `b2c_store_account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '门店账号ID',
  `shop_id` int(11) NOT NULL DEFAULT '0' COMMENT '所属店铺id',
  `sys_id` int(10) NOT NULL DEFAULT '0' COMMENT '所属账户id',
  `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号',
  `account_name` varchar(50) DEFAULT '' COMMENT '账户名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `account_type` tinyint(1) DEFAULT '1' COMMENT '账户类型1:店员，2：店长',
  `status` tinyint(1) DEFAULT '0' COMMENT '账户状态0:禁用，1：启用',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '是否已删除0:否，1：是',
  `account_passwd` varchar(64)  DEFAULT NULL COMMENT '账号密码',
  `store_list` varchar(191)  DEFAULT NULL COMMENT '可用门店id,逗号隔开',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP  COMMENT '修改时间',
  PRIMARY KEY (`account_id`),
  KEY `mobile` (`mobile`),
  KEY `account_name` (`account_name`)
);

ALTER TABLE `b2c_store_account` CHANGE COLUMN `update_time` `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间' ;

ALTER TABLE `b2c_mp_auth_shop` ADD COLUMN `live_pack_status` TINYINT(1) NULL DEFAULT 0 COMMENT '直播包状态 1：通过 2：打包审核中' ;

/***********************2.11*********************END*/
