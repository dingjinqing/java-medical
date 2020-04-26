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

-- 更新旗舰版权限
-- UPDATE `b2c_shop_version` SET `content` = '{"main_config":{"sub_0":["basic_yesterday","portrait_user","second_view","visit_source","analysis_visit_source"],"sub_1":["sort"],"sub_2":["m_member_card","m_voucher","m_bargain","m_video","m_integral_goods","m_seckill_goods","authorization","m_group_draw","m_pin_integration","m_nav"],"sub_3":["charge_card","count_card","grade_card","tag","sign_score","pay_score"],"sub_4":["full_cut","pin_group","bargain","form_decoration","distribution","message_template","pay_reward","coupon_grant","activity_reward","integral_goods","seckill_goods","lottery","purchase_price","reduce_price","group_draw","pin_integration","package_sale","pre_sale","insteadpay","gift","promote","assess","free_ship","reduce_price","coupon_package","comment_gift","first_special","give_gift","share_award",,"wx_mcode",,"live_broadcast"],"sub_5":["store_pay","technician","service","service_city"]},"num_config":{"picture_num":"10240","video_num":"10240","goods_num":"-1","store_num":"200","decorate_num":"-1","form_num":"-1"}}', `level` = 'v4' WHERE `id` = 4;
/***********************2.11*********************END*/
/***********************2.12********************BEGIN*/
ALTER TABLE `b2c_shop_question_feedback` ADD COLUMN `version` varchar(50) not NULL default '' COMMENT '使用系统版本';
ALTER TABLE `b2c_shop_question_feedback` ADD COLUMN `submit_user` varchar(128) not NULL default '' COMMENT '提交账号';
ALTER TABLE `b2c_shop_question_feedback` ADD COLUMN `submit_user_phone` varchar(32) not NULL default '' COMMENT '提交账号绑定的手机号';


-- 营销日历表
CREATE TABLE IF NOT EXISTS `b2c_market_calendar` (
`id` INT ( 8 ) NOT NULL AUTO_INCREMENT,
`event_name` VARCHAR ( 64 ) NOT NULL DEFAULT '' COMMENT '事件名称',
`event_time` date DEFAULT NULL COMMENT '事件时间',
`event_desc` text COMMENT '事件说明',
`pub_flag` TINYINT ( 1 ) NOT NULL DEFAULT '0' COMMENT '发布状态：0未发布，1已发布',
`del_flag` TINYINT ( 1 ) NOT NULL DEFAULT '0' COMMENT '是否已删除：0否，1是',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY ( `id` )
);

-- 营销日历表对应活动
CREATE TABLE IF NOT EXISTS `b2c_market_calendar_activity` (
`id` INT ( 8 ) NOT NULL AUTO_INCREMENT,
`calendar_id` INT ( 8 ) NOT NULL DEFAULT '0' COMMENT '营销日历Id',
`activity_type` VARCHAR ( 16 ) NOT NULL DEFAULT '0' COMMENT '具体营销活动类型',
`shop_use_num` INT ( 8 ) NOT NULL DEFAULT '0' COMMENT '店铺使用累计数量',
`recommend_type` TINYINT ( 1 ) NOT NULL DEFAULT '0' COMMENT '推荐类型：0站内文本，1外部链接',
`recommend_link` VARCHAR ( 100 ) NOT NULL DEFAULT '' COMMENT '推荐链接',
`del_flag` TINYINT ( 1 ) NOT NULL DEFAULT '0' COMMENT '是否已删除：0否，1是',
`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
`shop_ids` VARCHAR ( 500 ) NOT NULL DEFAULT '' COMMENT '使用该推荐活动的店铺id，逗号隔开',
`recommend_title` VARCHAR ( 32 ) NOT NULL DEFAULT '' COMMENT '推荐标题',
PRIMARY KEY ( `id` ),
KEY `calendar_id` ( `calendar_id` )
);
/*********************2.12*************************END*/