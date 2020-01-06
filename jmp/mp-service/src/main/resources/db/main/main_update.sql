-- b2c_article_category表结构修改
ALTER TABLE `b2c_article_category`
DROP COLUMN `use_footer_nav`,
ADD COLUMN `del_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0未删除,1删除',
ADD COLUMN `add_time`  timestamp NOT NULL DEFAULT current_timestamp COMMENT '添加时间',
ADD COLUMN `update_time`  timestamp NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';
-- b2c_article表结构修改
ALTER TABLE `b2c_article`
DROP COLUMN `show_footer`,
MODIFY COLUMN `create_time`  timestamp NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
MODIFY COLUMN `update_time`  timestamp NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
MODIFY COLUMN `pub_time`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发布时间';



-- 表中店铺续费金额字段变为decimal
alter table b2c_shop_renew modify column renew_money decimal(12,2);

-- 07-05王兵兵 删除店铺表中share_config、shop_style字段，该项配置转移到店铺库shop_cfg表中
alter table b2c_shop drop column share_config;
alter table b2c_shop drop column shop_style;

-- 07-05王兵兵 shop表缺少的字段
alter table b2c_shop ADD COLUMN   `expire_time` date DEFAULT NULL COMMENT '到期时间';

--  07-16 孔德成 b2c_shop_uploaded_image_category表结构修改

ALTER TABLE `b2c_shop_uploaded_image_category`
ADD COLUMN `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

--  07-16 孔德成 b2c_uploaded_image表结构修改
ALTER TABLE `b2c_shop_uploaded_image`
    MODIFY COLUMN `upload_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    ADD COLUMN `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';


-- 把b2c_user_login_record字段更改
ALTER TABLE `b2c_user_login_record` MODIFY COLUMN user_id INT( 11 );
ALTER TABLE `b2c_user_login_record` CHANGE COLUMN `add_time` `add_time` TIMESTAMP  NULL DEFAULT CURRENT_TIMESTAMP COMMENT '每日登陆时间' ;


-- b2c_shop_renew新增字段
ALTER TABLE `b2c_shop_renew` ADD COLUMN `renew_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '续费类型：1续费，2试用，3赠送，4退款';
ALTER TABLE `b2c_shop_renew` ADD COLUMN `renew_duration` varchar(32) NOT NULL DEFAULT '0' COMMENT '时长：字符串逗号隔开';
ALTER TABLE `b2c_shop_renew` ADD COLUMN `send_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '赠送类型：0无，1时间，2功能';
ALTER TABLE `b2c_shop_renew` ADD COLUMN `send_content` varchar(32)  NOT NULL DEFAULT '0' COMMENT '赠送内容：字符串逗号隔开';

-- b2c_mp_operate_log新增字段
ALTER TABLE  b2c_mp_operate_log  ADD COLUMN `operate_state` tinyint      not null  default 1 comment '操作状态:1成功 2失败';

-- b2c_mp_auth_shop新增字段
ALTER TABLE `b2c_mp_auth_shop` ADD COLUMN `fee_type` varchar(191) default 'CNY' comment '标价币种，国际支付字段';
ALTER TABLE `b2c_mp_auth_shop` ADD COLUMN `merchant_category_code` varchar(191) not null default '' comment 'MCC码，国际支付字段';


-- audit_id改为Long类型
ALTER TABLE `b2c_mp_auth_shop` CHANGE COLUMN `audit_id` `audit_id` BIGINT(64) NULL DEFAULT '0' COMMENT '最新的审核ID' ;
ALTER TABLE `b2c_mp_deploy_history` CHANGE COLUMN `audit_id` `audit_id` BIGINT(64) NULL DEFAULT '0' COMMENT '最新的审核ID' ;



ALTER TABLE `b2c_mp_operate_log` ADD COLUMN `memo_id` VARCHAR(100) NOT NULL AFTER `create_time`, ADD COLUMN `memo_list` VARCHAR(100) NOT NULL AFTER `memo_id`;

--添加币种
ALTER TABLE `b2c_shop`  ADD COLUMN `currency` VARCHAR(45) NOT NULL DEFAULT 'CNY' COMMENT '币种';
--添加语言
ALTER TABLE `b2c_shop`  ADD COLUMN `shop_language` VARCHAR(45) NOT NULL DEFAULT 'zh_CN' COMMENT '语言';
--语言字段更改，如果数据库字段是shopLanguage
ALTER TABLE `b2c_shop` CHANGE COLUMN `shopLanguage` `shop_language` VARCHAR(45) NOT NULL DEFAULT 'zh_CN' COMMENT '语言' ;

-- 修改b2c_category主键字段
ALTER TABLE b2c_category CHANGE COLUMN `cat_id` `cat_id` int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE b2c_category CHANGE COLUMN `parent_id` `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父ID';

--修改b2c_user字段与店铺库一致
ALTER TABLE b2c_user
CHANGE COLUMN `del_flag` `del_flag` TINYINT(1) NOT NULL DEFAULT '0' ,
CHANGE COLUMN `del_time` `del_time` TIMESTAMP NULL DEFAULT NULL COMMENT '删除时间' ;


--更新字段长度，使主库和店铺库保持一致
ALTER TABLE `b2c_user_detail`
CHANGE COLUMN `user_id` `user_id` INT(11) NOT NULL ;

--更新字段长度，使主库和店铺库保持一致
ALTER TABLE `b2c_user`
CHANGE COLUMN `shop_id` `shop_id` INT(11) NOT NULL DEFAULT '0' ,
CHANGE COLUMN `user_id` `user_id` INT(11) NOT NULL ,
CHANGE COLUMN `user_pwd` `user_pwd` varchar(60) not null default '' comment '密码',
CHANGE COLUMN `wechat` `wechat`   varchar(100) not null default '' comment '微信',
CHANGE COLUMN `invite_expiry_date` `invite_expiry_date` date  default null comment '邀请失效时间',
CHANGE COLUMN `invite_source` `invite_source`  varchar(32)   default null comment '邀请来源:groupbuy.拼团,bargain.砍价,integral.积分,seckill.秒杀,lottery.抽奖';

--记录表更新
ALTER TABLE `b2c_user_login_record`
ADD COLUMN `account_type` TINYINT(1) NULL COMMENT '登录日志账户类型：0店铺登录日志，1系统账号登录日志' ;

--文章分类
ALTER TABLE `b2c_article_category`
CHANGE COLUMN `del_state` `use_footer_nav` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否用于底部导航' ;

-- 定时任务定义表
drop table if exists `b2c_cron_define`;
CREATE TABLE `b2c_cron_define` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `class_name` varchar(128) NOT NULL COMMENT '定时任务完整类名',
  `expression` varchar(32) NOT NULL COMMENT 'cron表达式',
  `description` varchar(64) NOT NULL DEFAULT '' COMMENT '任务描述',
    `result` tinyint(1) NOT NULL DEFAULT 0 COMMENT '执行结果,0:待执行;1:执行中；2已完成；3:执行失败',
    `retries_num` tinyint(1) NOT NULL DEFAULT 0 COMMENT '失败重试次数,默认0不重试',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态,1:启用;0:停用',
      `create_time`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cron_key` (`class_name`)
) COMMENT='定时任务定义表';

-- 定时任务执行结果记录表（只记录执行失败的记录）
drop table if exists `b2c_cron_record`;
CREATE TABLE `b2c_cron_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cron_id` int(11) NOT NULL COMMENT '定时任务id',
  `execute_num` tinyint(1) NOT NULL DEFAULT 0 COMMENT '执行次数（小于等于失败重试次数）',
  `failed_reason` varchar(512) NOT NULL DEFAULT '' COMMENT '最后一次执行失败原因',
        `create_time`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_id` (`cron_id`)
) COMMENT='定时任务执行结果记录表';


alter table b2c_mp_user_portrait
    add id int auto_increment primary key first;
ALTER TABLE `b2c_mp_user_portrait`
ADD COLUMN `start_time` TIMESTAMP NOT NULL COMMENT '开始时间，ref_date前8个字符' ;