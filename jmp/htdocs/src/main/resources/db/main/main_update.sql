--b2c_article_category表结构修改
ALTER TABLE `b2c_article_category`
DROP COLUMN `use_footer_nav`,
ADD COLUMN `del_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0未删除,1删除',
ADD COLUMN `add_time`  timestamp NOT NULL DEFAULT current_timestamp() COMMENT '添加时间',
ADD COLUMN `update_time`  timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';
--b2c_article表结构修改
ALTER TABLE `b2c_article`
DROP COLUMN `show_footer`,
MODIFY COLUMN `create_time`  timestamp NOT NULL DEFAULT current_timestamp() COMMENT '创建时间',
MODIFY COLUMN `update_time`  timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
MODIFY COLUMN `pub_time`  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '发布时间';



--表中店铺续费金额字段变为decimal
alter table b2c_shop_renew modify column renew_money decimal(12,2);

--07-05王兵兵 删除店铺表中share_config、shop_style字段，该项配置转移到店铺库shop_cfg表中
alter table b2c_shop drop column share_config;
alter table b2c_shop drop column shop_style;

--07-05王兵兵 shop表缺少的字段
alter table b2c_shop ADD COLUMN   `expire_time` date DEFAULT NULL COMMENT '到期时间';

-- 07-16 孔德成 b2c_shop_uploaded_image_category表结构修改

ALTER TABLE `b2c_shop_uploaded_image_category`
ADD COLUMN `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP() COMMENT '更新时间';

-- 07-16 孔德成 b2c_uploaded_image表结构修改
ALTER TABLE `b2c_shop_uploaded_image`
    MODIFY COLUMN `upload_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
    ADD COLUMN `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间';


--把b2c_user_login_record字段更改
ALTER TABLE `mini_main`.`b2c_user_login_record` MODIFY COLUMN user_id INT( 11 );
ALTER TABLE `mini_main`.`b2c_user_login_record` CHANGE COLUMN `add_time` `add_time` TIMESTAMP  NULL DEFAULT CURRENT_TIMESTAMP COMMENT '每日登陆时间' ;
