-- -- 李晓冰 b2c_goods_brand 将记录创建时间添加默认值为当前时间戳，记录修改时间设置为当前记录变化时间戳
-- ALTER TABLE b2c_goods_brand MODIFY COLUMN add_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间';
-- ALTER TABLE b2c_goods_brand MODIFY COLUMN update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间';

-- -- b2c_sort 添加update_time 字段
-- ALTER TABLE b2c_sort ADD COLUMN update_time TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间' AFTER create_time;
-- -- b2c_sort 删除字段keywords
-- ALTER TABLE b2c_sort DROP COLUMN keywords;
-- -- b2c_sort 修改parent_id字段类型，使其和sort_id类型一致
-- ALTER TABLE b2c_sort MODIFY COLUMN parent_id INT NOT NULL;

-- -- 7月3号 李晓冰 设置goods_brand goods_sort 部分字段默认值和非空约束
-- ALTER TABLE b2c_goods_brand MODIFY COLUMN is_recommend TINYINT NOT NULL DEFAULT 0 COMMENT '是否为推荐品牌 0否 1是';
-- ALTER TABLE b2c_goods_brand MODIFY COLUMN classify_id TINYINT NOT NULL DEFAULT 0 COMMENT '品牌所属分类 0未分类 否则是分类id';
-- ALTER TABLE b2c_goods_brand MODIFY COLUMN update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间';

-- ALTER TABLE b2c_sort MODIFY COLUMN sort_name VARCHAR(90) NOT NULL;
-- ALTER TABLE b2c_sort MODIFY COLUMN parent_id INT(11) NOT NULL DEFAULT 0 COMMENT '分类父节点，0表示一级';
-- ALTER TABLE b2c_sort MODIFY COLUMN sort_name VARCHAR(90) NOT NULL;

-- -- 7月4号 修改b2c_group表名为b2c_store_group,字段in_time改为create_time
-- ALTER  TABLE b2c_group RENAME TO b2c_store_group;
-- ALTER TABLE b2c_store_group CHANGE column in_time create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- -- 7月5日 李晓冰  修改b2c_spec和 b2c_spec_val表，添加非空约束，和唯一索引
-- ALTER TABLE b2c_spec
-- MODIFY COLUMN create_time timestamp   not null 	default current_timestamp,
-- MODIFY COLUMN update_time  timestamp   not null  	default current_timestamp on update current_timestamp comment '最后修改时间',
-- ADD UNIQUE INDEX unique_spec_name_goods_id (spec_name,goods_id);

-- ALTER TABLE b2c_spec_vals
-- MODIFY COLUMN create_time timestamp   not null 	default current_timestamp,
-- MODIFY COLUMN update_time  timestamp   not null  	default current_timestamp on update current_timestamp comment '最后修改时间',
-- CHANGE COLUMN specvalid spec_val_id int(11) 	not null auto_increment ,
-- CHANGE COLUMN specvalname spec_val_name VARCHAR(60) not null DEFAULT '',
-- ADD UNIQUE INDEX unique_spec_id_spec_val_name (spec_id,spec_val_name);


-- -- 7月10日 梁晨 b2c_comment_goods 添加is_shop_add  bogus_username  bogus_user_avatar字段
-- ALTER TABLE b2c_comment_goods ADD COLUMN is_shop_add tinyint(1)  not null DEFAULT '0' comment '是否商家增加：0不是，1是';
-- ALTER TABLE b2c_comment_goods ADD COLUMN bogus_username  varchar(32)  not null default '' comment '用户名称：商家添加时使用';
-- ALTER TABLE b2c_comment_goods ADD COLUMN bogus_user_avatar varchar(100)  not null default '' comment '用户头像：商家添加时使用';




-- -- 7月9日 黄壮壮 修改b2c_tag表名in_time为create_time 并且添加字段update_time
-- alter table b2c_tag CHANGE COLUMN in_time create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
-- ALTER TABLE b2c_tag ADD COLUMN update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间' AFTER create_time;



-- -- 7月9日添加
-- -- 修改标签组关系表
-- ALTER TABLE b2c_goods_label_couple MODIFY COLUMN label_id INT(11) NOT NULL;

-- -- 7月11日 常乐 创建测评活动相关表结构
-- -- 测评活动表
-- -- drop table if exists `b2c_assess_activity`;
-- create table `b2c_assess_activity` (
--   `id`                int(11)               not null auto_increment,
--   `shop_id`           int(11)               null     default 0 comment '店铺ID',
--   `act_code`          varchar(32)           null     default '' comment '活动编码',
--   `act_name`          varchar(120)          not null default '' comment '活动名称',
--   `start_time`        datetime              not null comment '活动起始时间',
--   `end_time`          datetime              not null comment '活动截止时间',
--   `due_time_type`     tinyint(1)            null     default 0 comment '是否永久有效：0否，1是',
--   `part_times_type`   tinyint(1)            null     default 0 comment '单用户参与次数类型：0不限制，1限制次数',
--   `part_times_day`    int(4)                null     default 1 comment '每天最多参与次数：默认为1,0表示不限制',
--   `part_times_total`  int(4)                null     default 1 comment '累计最多参与次数：默认为1,0表示不限制',
--   `feedback_total`    int(4)                null     default 0 comment '活动总反馈数量：默认为0,0表示不限制',
--   `assess_judge_type` tinyint(1)            null     default 0 comment '测评结果判断条件：0根据选项判断，1根据得分判断',
--   `cover_style_type`  tinyint(1)            null     default 0 comment '封面样式类型：0默认样式，1自定义',
--   `cover_style`       text comment '封面样式内容，json串',
--   `assess_desc`       varchar(500)          not null default '' comment '测评介绍',
--   `create_time`       timestamp       default current_timestamp,
--   `update_time`       timestamp       default current_timestamp on update current_timestamp comment '最后修改时间',
--   `is_block`          tinyint(1)            null     default 0 comment '活动状态：0未停用，1已停用',
--   `del_flag`          tinyint(1)            null     default 0 comment '删除标识：0未删除，1已删除',
--   `pub_flag`          tinyint(1)            null     default 0 comment '发布标识：0未发布，1已发布',
--   primary key (`id`),
--   key `act_name` (`act_name`),
--   key (`shop_id`)
-- );

-- -- 测评活动题目表
-- -- drop table if exists `b2c_assess_topic`;
-- create table `b2c_assess_topic` (
--   `id`                int(8)                not null auto_increment,
--   `shop_id`           int(11)               null     default 0 comment '店铺ID',
--   `assess_id`         int(11)               null     default 0 comment '测评活动ID',
--   `topic_type`        tinyint(1)            null     default 0 comment '题目格式：0文本，1图片，2视频',
--   `topic_type_path`   varchar(200)          null     default '' comment '题目图片、视频路径',
--   `topic_title`       varchar(200)          null     default '' comment '题目标题',
--   `topic_level`       tinyint(2)            null     default 1 comment '题目优先级，越小优先级越大，从1开始',
--   `bg_img_type`       tinyint(1)            null     default 0 comment '题目背景类型：0默认，1自定义',
--   `bg_img_path`       varchar(200)          null     default '' comment '题目背景图片路径',
--   `option_type`       tinyint(1)            null     default 0 comment '选项类型：0单选，1多选',
--   `option_skip_type`  tinyint(1)            null     default 0 comment '多选时跳转类型：1跳转到指定题目，2跳转导致指定结果',
--   `option_skip_value` int(8)                null     default 0 comment '多选时跳转到指定题目ID或者结果ID',
--   `option_content`    text comment '选项内容，json串，包括选项描述、图片和跳转逻辑或者分值',
--   `result_id`         int(11)               null     default 0 comment '对应ID',
--   `create_time`       timestamp       default current_timestamp,
--   `update_time`       timestamp       default current_timestamp on update current_timestamp comment '最后修改时间',
--   `del_flag`          tinyint(1)            null     default 0 comment '删除标识：0未删除，1已删除',
--   primary key (`id`),
--   key `assess_id` (`assess_id`),
--   key (`shop_id`)
-- );

-- -- 测评活动结果表
-- -- drop table if exists `b2c_assess_result`;
-- create table `b2c_assess_result` (
--   `id`                     int(11)                not null auto_increment,
--   `shop_id`                int(11)               null     default 0 comment '店铺ID',
--   `assess_id`              int(11)               null     default 0 comment '测评活动ID',
--   `result`                 varchar(200)          null     default '' comment '测试结果',
--   `min_point`              int(11)               null     default 0 comment '结果分值区间起始值',
--   `max_point`              int(11)               null     default 0 comment '结果分值区间终值',
--   `result_img_path`        varchar(200)          null     default '' comment '测试结果图片路径',
--   `result_desc`            varchar(500)          null     default '' comment '测试结果描述',
--   `reward_type`            tinyint(1)            null     default 0 comment '奖励类型：0不设置，1优惠券，2奖品，3积分，4余额，5自定义',
--   `reward_info`            varchar(300)          null     default '' comment '奖励内容',
--   `reward_limit_type`      tinyint(1)            null     default 0 comment '领奖限制类型：0无限制，1分享好友领奖品，2填写信息领奖品',
--   `reward_limit_condition` varchar(32)           null     default '' comment '领奖限制条件,多选字符串逗号隔开：1真实姓名，2手机号，3身份证号码，4性别，5生日，6婚姻状况，7教育程度，8所在行业，9所在地',
--   `bg_type`                tinyint(1)            null     default 0 comment '结果页背景类型：0默认，1自定义',
--   `bg_img_path`            varchar(132)          null     default '' comment '结果页背景图片路径',
--   `create_time`            timestamp       default current_timestamp,
--   `update_time`            timestamp       default current_timestamp on update current_timestamp comment '最后修改时间',
--   `del_flag`               tinyint(1)            null     default 0 comment '删除标识：0未删除，1已删除',
--   primary key (`id`),
--   key (`assess_id`),
--   key (`shop_id`)
-- );

-- -- 测评活动记录表
-- -- drop table if exists `b2c_assess_record`;
-- create table `b2c_assess_record` (
--   `id`           int(11)               not null  auto_increment,
--   `shop_id`      int(11)               not null  default 0 comment '店铺ID',
--   `user_id`      int(11)               not null  default 0 comment '会员ID',
--   `assess_id`    int(11)               not null  default 0 comment '测评活动ID',
--   `topic_id`     int(11)               not null  default 0 comment '测评得出结果的题目的ID',
--   `result_id`    int(11)               not null  default 0 comment '测评结果ID',
--   `assess_point` varchar(11)           null      default '' comment '测评总得分或者题目的选项编号',
--   `create_time`   timestamp            default current_timestamp,
--   `update_time`   timestamp            default current_timestamp on update current_timestamp comment '最后修改时间',
--   `finish_time`  datetime              not null comment '测评完成时间',
--   `del_flag`     tinyint(1)            not null  default 0 comment '删除标识：0未删除，1已删除',
--   `state_status` tinyint(1)            not null  default 0 comment '测评状态：0测评中，1测评完成待领取奖励，2测评完成已领取，3领取失效，4测评失败',
--   primary key (`id`),
--   key (`assess_id`),
--   key (`shop_id`),
--   key (`user_id`)
-- );

-- -- 测评活动答题记录表
-- -- drop table if exists `b2c_assess_topic_record`;
-- create table `b2c_assess_topic_record` (
--   `id`           int(11)               not null auto_increment,
--   `record_id`    int(11)               not null default 0 comment '测评活动记录表关联ID',
--   `topic_id`     int(11)               not null default 0 comment '测评题目的ID',
--   `topic_option` varchar(11)           not null default '' comment '测评题目选项编号,多选的逗号隔开',
--   `topic_point`  int(8)                not null default 0 comment '测评得分',
--   `result_id`    int(11)               not null default 0 comment '测评结果ID',
--   `create_time`  timestamp       default current_timestamp,
--   `update_time`  timestamp       default current_timestamp on update current_timestamp comment '最后修改时间',
--   primary key (`id`),
--   key (`record_id`),
--   key (`result_id`)
-- );

-- -- 7月12日 常乐 添加优惠礼包表结构
-- ##优惠券礼包
-- #DROP TABLE IF EXISTS `b2c_coupon_pack`;
-- create table `b2c_coupon_pack` (
--   `id`              int(11)                  not null auto_increment,
--   `act_name`        varchar(100)             not null comment '活动名称',
--   `start_time`      datetime                 not null comment '开始时间',
--   `end_time`        datetime                 not null comment '结束时间',
--   `pack_name`       varchar(20)              not null comment '礼包名称',
--   `limit_get_times` tinyint(5)       		 not null default 0 comment '单用户领取限制次数，0不限制',
--   `total_amount`    int(11)                  not null default '0' comment '总数量',
--   `issued_amount`   int(11)                  not null default '0' comment '已发放数量',
--   `access_mode`     tinyint(1)               not null default '0' comment '获取方式，0：现金购买，1：积分购买，2直接领取',
--   `access_cost`     decimal(10, 2)           null     default 0.00 comment '价格（现金或积分，直接领取时该值为0）',
--   `act_rule`        text collate utf8mb4_bin null comment '活动规则',
--   `state`           tinyint(1)               not null default '1' comment '开启状态1:开启，0:停用',
--   `create_time`     timestamp       default current_timestamp,
--   `update_time`     timestamp       default current_timestamp on update current_timestamp comment '最后修改时间',
--   `del_flag`        tinyint(1)                        default 0,
--   `del_time`        timestamp       default current_timestamp,
--   primary key (`id`)
-- );

-- -- 李晓冰 7月12日添加
-- -- 修改表 b2c_goods_spec_product_bak
-- drop table if exists `b2c_goods_spec_product_bak`;
-- create table `b2c_goods_spec_product_bak` (
--   `prd_bak_id`           int(10)                         	not null auto_increment,
-- 	`del_time`				timestamp													not null DEFAULT current_timestamp,
-- 	`prd_id`					 int(10)													not null default '0',
--   `shop_id`          int(11)                        	not null default '0',
--   `goods_id`         int(10)                        	not null default '0',
--   `prd_price`        decimal(10, 2)                   	not null default '0.00',
--   `prd_market_price` decimal(10, 2)                   	not null default '0.00' 	comment '市场价',
--   `prd_cost_price`   decimal(10, 2) 					not null default '0.00' 	comment '成本价',
--   `prd_number`       int(11)                        	not null default '0' 	comment '当前规格组合产品库存',
--   `prd_sn`           varchar(65)                       	not null default '' 		comment '商家编码',
--   `prd_codes`        varchar(500)                     	not null default '' 		comment '商品条码',
--   `prd_specs`        varchar(1024)                     	not null default '',
--   `prd_desc`         varchar(1024)                     	not null default '' 		comment '规格描述，格式例子：颜色:红色 尺码:s',
--   `del_flag`         tinyint(1)                       	not null default '0',
--   `self_flag`        tinyint(1) 						not null default '0'   	comment '1:商家自己添加商品，其他没用',
--   `low_shop_price`   varchar(1024)                     	not null default '0.00' 	comment '最低售出价格',
--   `prd_img`          varchar(1024)                     	not null default '' 		comment '图片地址',
--   `price_flag`       tinyint(1)                       	not null default '0' 	comment '0:商家未改价，1：商家改价，2：批量改价，3：毛利改价',
--   `create_time`      timestamp    	not null comment '规格记录在原表内的添加时间',
--   `update_time`      timestamp     	not null comment '规格记录在原表内的最后修改时间',
--   primary key (`prd_bak_id`),
--   key `gsp_goods_id` (`goods_id`),
--   key `gsp_goods_codes` (`prd_codes`),
--   key `gsp_prd_sn` (`prd_sn`)
-- );
-- -- 李晓冰 7月12日添加
-- -- b2c_goods 添加主键约束
-- ALTER TABLE b2c_goods  add PRIMARY KEY(goods_id);

-- -- 王兵兵 7月15日
-- -- b2c_service_category 修改表名
-- alter table `b2c_service_category` rename to `b2c_store_service_category`;
-- -- b2c_service_category 统一主键字段类型
-- alter table `b2c_store_service_category` modify  column `cat_id` int(11) NOT NULL AUTO_INCREMENT;

-- --常乐  7月16日 重新设计优惠券表结构
-- -- drop table if exists `b2c_mrking_voucher`;
-- create table `b2c_mrking_voucher` (
--   `id`                   int(11)                not null auto_increment,
--   `shop_id`              int(11)                not null default 0 comment '店铺id',
--   `act_code`             varchar(50)            not null default 'voucher',
--   `act_name`             varchar(120)           not null default '',
--   `start_time`           timestamp              default null,
--   `end_time`             timestamp              default null,
--   `denomination`         decimal(10, 2)         not null default '0' comment '面额',
--   `total_amount`         int(11)                not null default '0' comment '发行量',
--   `type`                 tinyint(1)             default 0 comment '优惠券类型，0普通优惠券；1分裂优惠券',
--   `surplus`              int(11)                not null default '0',
--   `remain_amount`        int(11)                not null default '0',
--   `use_consume_restrict` tinyint(1)             not null default '0' comment '使用限制',
--   `least_consume`        mediumint(5)           not null default '0' comment '满多少可用',
--   `use_explain`          varchar(256)           not null default '',
--   `enabled`              tinyint(1)             not null default '1',
--   `is_random`            tinyint(1)             not null default '0' comment '是否需要积分兑换',
--   `receive_per_person`   smallint(3)            not null default '0' comment '每人限领张数',
--   `suit_goods`           tinyint(1)             not null default '0' comment '0:全店通用,1:指定店铺',
--   `together_used`        tinyint(1)             not null default '0' comment '是否与其他优惠券同时使用',
--   `permit_share`         tinyint(1)             not null default '0' comment '是否允许分享优惠券链接',
--   `remind_owner`         tinyint(1)             not null default '0' comment '是否到期前提醒用户',
--   `giveout_amount`       smallint(4)            not null default '0' comment '发放优惠券数量',
--   `giveout_person`       smallint(4)            not null default '0' comment '发放优惠券人数',
--   `receive_amount`       smallint(4)            not null default '0' comment '领取优惠券数量',
--   `receive_person`       smallint(4)            not null default '0' comment '领取优惠券人数',
--   `used_amount`          smallint(4)            not null default '0' comment '已使用优惠券数量',
--   `alias_code`           varchar(16)            not null default '' comment '唯一活动代码',
--   `validation_code`      varchar(10)            not null default '' comment '领取码',
--   `recommend_goods_id`   text                   comment '指定商品可用',
--   `recommend_cat_id`     text                   comment '指定平台可用',
--   `recommend_sort_id`    text                   comment '指定商家分类可用',
--   `validity`             mediumint(11)      not null default 0    comment '优惠券有效天数',
--   `del_flag`             tinyint(1)             not null default '0' comment '1为删除状态',
--   `action`               tinyint(1)       not null default 1   comment '1:系統创建 2：来自crm',
--   `identity_id`          varchar(50)            default null comment '关联外部优惠券规则唯一码',
--   `recommend_product_id` text                   comment '关联商品规格',
--   `use_score`            tinyint(2)       not null default 0    comment '是否可以积分兑换',
--   `score_number`         int(6)         not null default 0    comment '需要积分数',
--   `card_id`              text                 comment '专属会员卡',
--   `create_time`          timestamp      default current_timestamp,
--   `update_time`          timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
--   primary key (`id`),
--   unique key `alias_code` (`alias_code`),
--   key `act_name` (`act_name`),
--   key (`shop_id`)
-- );

-- -- 黄荣刚 7月15日
-- -- 修改
-- ALTER TABLE b2c_service_technician MODIFY COLUMN group_id int(11) DEFAULT 0  COMMENT '技师分组';
-- ALTER TABLE b2c_service_technician_group MODIFY COLUMN group_id int(11) NOT NULL AUTO_INCREMENT COMMENT '技师分组';



-- 黄壮壮 7月16
-- 修改字段in_time与up_time
-- alter table b2c_user_score_set CHANGE COLUMN in_time create_time TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP
-- alter table b2c_user_score_set CHANGE COLUMN up_time update_time TIMESTAMP not null DEFAULT CURRENT_TIMESTAMP



-- 2019-07-17 重新建表 --

-- 王兵兵 201-07-18
alter table `b2c_service_order` add index order_sn(order_sn);

/* liufei 2019-07-19 */
alter table b2c_trades drop index ref_date;

-- 2019-07-23 统计相关表添加 int 主键 --
-- 郑保乐 2019-07-23
alter table b2c_mp_daily_visit
    add id int auto_increment primary key first;

alter table b2c_mp_daily_retain
    add id int auto_increment primary key first ;

alter table b2c_mp_weekly_visit
    add id int auto_increment primary key first;

alter table b2c_mp_weekly_retain
    add id int auto_increment primary key first ;

alter table b2c_mp_monthly_visit
    add id int auto_increment primary key first;

alter table b2c_mp_monthly_retain
    add id int auto_increment primary key first;

alter table b2c_mp_user_portrait
    add id int auto_increment primary key first;

alter table b2c_mp_visit_page
    add id int auto_increment primary key first;

alter table b2c_mp_distribution_visit
    add id int auto_increment primary key first;

-- 王兵兵 201-07-24 补全砍价活动表字段

ALTER TABLE `b2c_bargain` ADD COLUMN `bargain_type` TINYINT(1) DEFAULT 0  NULL   COMMENT '砍价类型0定人1任意价';
ALTER TABLE `b2c_bargain` ADD COLUMN `floor_price` DECIMAL(10,2) DEFAULT 0.00  NULL   COMMENT '任意低价';
ALTER TABLE `b2c_bargain` ADD COLUMN `bargain_money_type` TINYINT(1) DEFAULT 0  NULL   COMMENT '砍价计算模式';
ALTER TABLE `b2c_bargain` ADD COLUMN `bargain_fixed_money` DECIMAL(10,2) DEFAULT 0.00  NULL   COMMENT '固定金额';
ALTER TABLE `b2c_bargain` ADD COLUMN `bargain_min_money` DECIMAL(10,2) DEFAULT 0.00  NULL   COMMENT '最低价';
ALTER TABLE `b2c_bargain` ADD COLUMN `bargain_max_money` DECIMAL(10,2) DEFAULT 0.00  NULL   COMMENT '最高价';
ALTER TABLE `b2c_bargain` ADD COLUMN `free_freight` TINYINT(1) DEFAULT '0' COMMENT '0不免运费，使用原商品运费模板   1免运费';

-- 王兵兵 201-07-25 砍价活动默认状态可用

alter table `b2c_bargain` modify column `status` tinyint(1) not null default '1' comment '状态：1可用，0停用';

-- liufei 2019-07-26 商品备份表添加品牌id字段

alter table b2c_goods_bak add column `brand_id` int(11) not null default 0 comment '品牌id';

-- 孔德成 2019-7-29  修改多人拼团表
-- --  拼团活动定义表
-- drop table if exists `b2c_group_buy__define`;
create table `b2c_group_buy__define`
(
    `id`               int(11)      not null auto_increment,
    `shop_id`          int(11)      not null comment '店铺id',
    `goods_id`         int(11)      not null comment '商品id',
    `name`             varchar(100) not null comment '活动名称',
    `limit_amount`     smallint(6)  not null comment '成团人数',
    `join_limit`       smallint(6)  not null comment '参团限制',
    `open_limit`       smallint(6)  not null comment '开团限制',
    `is_default`       tinyint(1)   not null default 0 comment '默认成团',
    `start_time`       timestamp    null     default null comment '开始时间',
    `end_time`         timestamp    null     default null comment '结束时间',
    `stock`            smallint(6)  not null default 0 comment '总库存',
    `sale_num`         smallint(6)  not null default 0 comment '销量',
    `del_flag`         tinyint(1)   not null default 0,
    `status`           tinyint(1)   not null default 1 comment '状态： 1：启用  0： 禁用 2 代表已无库存',
    `del_time`         int(11)      not null default 0,
    `activity_type`    tinyint(1)   not null default '1' comment '活动类型：1：普通拼团，2：老带新团',
    `is_grouper_cheap` tinyint(1)   not null default '0' comment '是否开启团长优惠：0：不开启，1：开启',
    `reward_coupon_id` varchar(200) null comment '拼团失败发放优惠券',
    `share_config`     text comment '分享设置',
    `create_time`      timestamp             default current_timestamp,
    `update_time`      timestamp             default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`id`)
);

-- --  拼团活动产品规格定义表
-- drop table if exists `b2c_group_buy_product_define`;
create table `b2c_group_buy_product_define`
(
    `id`              int(11)        not null auto_increment,
    `activity_id` int(11)        not null comment '拼团定义id',
    `product_id`      int(11)        not null comment '商品规格id',
    `group_price` decimal(10, 2) not null default 0.00 comment '拼团价',
    `stock`           smallint(6)    not null default 0 comment '库存',
    `sale_num`        smallint(6)    not null default 0 comment '销量',
    `grouper_price`   decimal(10, 2) not null default '0.00' comment '团长优惠价',
    `create_time`     timestamp               default current_timestamp,
    `update_time`     timestamp               default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`id`)
);

-- --  拼团活动参团明细表
-- drop table if exists `b2c_group_buy_list`;
create table `b2c_group_buy_list`
(
    `id`              int(11)     not null auto_increment,
    `activity_id` int(11)     not null comment '拼团活动定义id',
    `goods_id`        int(11)     not null default 0,
    `group_id`        int(11)     not null default 0 comment '拼团id',
    `user_id`         int(11)     not null,
    `is_grouper`      tinyint(1)  not null default 0 comment '是否为团长 1：是 0：否',
    `order_sn`        varchar(20) not null comment '订单编号',
    `status`          tinyint(1)  not null default 0 comment '0: 拼团中 1:拼团成功 2:拼团失败',
    `start_time`      timestamp   null     default null comment '开团时间',
    `end_time`        timestamp   null     default null comment '成团时间',
    `create_time`     timestamp            default current_timestamp,
    `update_time`     timestamp            default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`id`)
);
--  瓜分积分活动配置
-- drop table if exists `b2c_group_integration_define`;
create table `b2c_group_integration_define` (
  `id`            int(11)                                 not null auto_increment,
  `shop_id`       int(11)                                 not null comment '店铺id',
  `name`          varchar(100)  						  not null comment '活动名称',
  `inte_total`    int(11)                                 not null default '0' comment '总抽奖积分',
  `inte_group`    int(11)                                 not null default '0' comment '每个团总积分',
  `limit_amount`  smallint(6)                             not null comment '成团人数',
  `join_limit`    smallint(6)                             not null comment '参团限制',
  `divide_type`   tinyint(1)                              not null default '0' comment '瓜分方式：0：按邀请好友数量瓜分，1：好友均分，2：随机瓜分',
  `start_time`    timestamp null default null comment '开始时间',
  `end_time`      timestamp null default null comment '结束时间',
  `status`        tinyint(1)                               not null default '1' comment '状态： 1：启用  0： 禁用',
  `del_flag`      tinyint(1)                               not null default '0',
  `del_time`	  timestamp      null	default null comment '删除时间',
  `inte_remain`   int(11)                                 not null default '0' comment '剩余积分',
  `is_day_divide` tinyint(1)                              not null comment '是否开团24小时自动开奖',
  `param_n`       float                                   not null default '0' comment '常数n',
  `is_continue`   tinyint(1)                              not null default '1' comment '继续： 1：继续  0： 结束',
  `advertise`     varchar(100)  						  not null comment '活动宣传语',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

--  参团列表
-- drop table if exists `b2c_group_integration_list`;
create table `b2c_group_integration_list` (
  `id`               int(11)                                not null auto_increment,
  `inte_activity_id` int(11)                                not null comment '瓜分积分活动定义id',
  `group_id`         varchar(60)  							not null default '' comment '拼团id',
  `user_id`          int(11)                                not null,
  `is_grouper`       tinyint(1)                             not null default '0' comment '是否为团长 1：是 0：否',
  `status`           tinyint(1)                             not null default '0' comment '0: 拼团中 1:拼团成功 2:拼团失败',
  `start_time`       timestamp null default null comment '参团时间',
  `end_time`         timestamp null default null comment '成团时间',
  `integration`      int(11)                                not null default '0' comment '瓜分到的积分',
  `invite_num`       smallint(6)                            not null default '0' comment '邀请人的数量',
  `invite_user`      int(11)                                not null default '0' comment '邀请人（被谁邀请）',
  `is_new`           tinyint(1)                             not null default '0' comment '是否是新用户：0：不是，1：是',
  `is_look`          tinyint(1)                             not null default '0' comment '是否看过开奖结果',
  `can_integration`  int(11)                                not null default '0' comment '该团可瓜分积分池',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);
--  退款商品行增加列
alter table b2c_return_order_goods
add COLUMN return_money decimal(10, 2)	not null default '0.00' comment '实际退款金额',
add COLUMN discounted_goods_price decimal(10, 2)	not null default '0.00' comment '实际退款金额';

-- 修改虚拟商品订单表
alter table b2c_card_order
    add goods_type tinyint(2) not null comment '虚拟商品类别：0：会员卡，1：优惠券';
alter table b2c_card_order
    add voucher_id int(11) null comment '优惠券 ID' after card_no;

-- 会员卡订单
-- drop table if exists `b2c_virtual_card_order`;
create table b2c_virtual_card_order
(
    card_order_id mediumint unsigned auto_increment comment '会员卡订单id' primary key,
    order_id      mediumint unsigned comment '主虚拟商品订单id',
    card_id       int         default 0   not null comment '会员卡id',
    card_no       varchar(32) default '0' null comment '会员卡 No'
);

--分销员数据汇总 常乐 20190801
ALTER TABLE `b2c_user_fanli_statistics` DROP PRIMARY KEY;
ALTER TABLE `b2c_user_fanli_statistics` ADD COLUMN `rebate_level` TINYINT(2) DEFAULT 1  NULL   COMMENT '返利等级0自购1直返2间返' AFTER `fanli_user_id`;

--  孔德成 2019-7-29 16:05:51  增加表 满包邮
--  满包邮详情
-- DROP TABLE IF EXISTS `b2c_free_shipping`;
CREATE TABLE `b2c_free_shipping` (
`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
`name` VARCHAR ( 100 )  NOT NULL COMMENT '活动名称',
`expire_type` TINYINT ( 1 ) DEFAULT '0' COMMENT '0:固定日期 1：永久有效',
`start_time` datetime DEFAULT NULL COMMENT '开始时间',
`end_time` datetime DEFAULT NULL COMMENT '结束时间',
`type` INT ( 11 ) NOT NULL COMMENT '条件 0全部 1部分',
`recommend_goods_id` text  COMMENT '指定商品可用',
`recommend_cat_id` text  COMMENT '指定分类可用',
`recommend_sort_id` text  COMMENT '指定商家分类可用',
`status` TINYINT ( 1 ) DEFAULT '0' COMMENT '1停用',
`create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
`update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
`del_flag` TINYINT ( 1 ) DEFAULT '0' COMMENT '1删除',
`del_time` datetime DEFAULT NULL,
`level` TINYINT ( 2 ) DEFAULT '0' COMMENT '优先级 默认0',
PRIMARY KEY ( `id` )
);



-- 满包邮规则
-- DROP TABLE IF EXISTS `b2c_free_shipping_rule`;
CREATE TABLE `b2c_free_shipping_rule` (
`id` INT ( 11 ) NOT NULL AUTO_INCREMENT,
`shipping_id` INT ( 11 ) NOT NULL COMMENT '包邮活动ID',
`con_type` INT ( 11 ) NOT NULL COMMENT '包邮条件 0满金额 1满件数',
`money` DECIMAL ( 10, 2 ) NOT NULL COMMENT '满金额',
`num` INT ( 11 ) NOT NULL COMMENT '满件数',
`area` text  COMMENT '包邮地区',
`area_list` text  COMMENT '包邮地区',
`area_text` text  COMMENT '包邮地区',
`create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
`update_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
PRIMARY KEY ( `id` ),
KEY `shipping_id` ( `shipping_id` )
);



--优惠券礼包_礼包内容(优惠券)
--DROP TABLE IF EXISTS `b2c_coupon_pack_voucher`;
create table `b2c_coupon_pack_voucher` (
  `id`                       int(11)               not null auto_increment,
  `voucher_id`               int(11)               not null default 0 comment '优惠券id',
  `act_id`                   int(11)               not null default 0 comment '所属优惠券礼包id',
  `total_amount`             int(11)  unsigned not null default '0' comment '总数量',
  `immediately_grant_amount` int(11)  unsigned not null default '0' comment '立即发放数量',
  `timing_every`             int(11)  unsigned null     default '0' comment '每个时间单位间隔（1为无间隔）',
  `timing_unit`              tinyint(1)        null     default '0' comment '定时发放的时间单位，0：自然天，1：自然周，2自然月',
  `timing_time`              int(11)           null     default '0' comment '定时发放的时间,周1-7，月1-31，自然天不填',
  `timing_amount`            int(11)  unsigned null     default '0' comment '定时发放的数量',
  `del_flag`                 tinyint(1)                 default 0,
  primary key (`id`),
  index `voucher_id` (`voucher_id`),
  index `act_id` (`act_id`)
);

-- 修改虚拟订单表，整合不同商品id字段
alter table b2c_card_order drop column card_id;

alter table b2c_card_order drop column card_no;

alter table b2c_card_order drop column voucher_id;

alter table b2c_card_order
    add virtual_goods_id int not null comment '虚拟商品id';

-- 添加会员卡相关字段
alter table b2c_card_order
    add card_no varchar(32) null comment '会员卡号';

alter table b2c_card_order
    add member_card_balance decimal(10, 2)  not null default '0.00' comment '会员卡消费' AFTER use_score;

alter table b2c_card_order
    add return_card_balance decimal(10, 2) not null default '0.00' comment '会员卡退款' after return_money;

alter table b2c_card_order
	add still_send_flag tinyint(2) not null default '1' comment '退款后是否继续发放优惠劵，1：继续发放，0：停止发放' ;

--增加虚拟商品 优惠劵订单 会员卡余额支付方式的退款金额字段
ALTER TABLE `b2c_refund_card_record`
	ADD COLUMN `member_card_balance`  decimal(10,2) NOT NULL default '0.00' COMMENT '会员卡余额退款金额' AFTER `money_paid`;

-- 王兵兵 201-08-05 秒杀修改
alter table `b2c_sec_kill_define` drop column `shop_id`;

alter table `b2c_group_integration_list` modify column `group_id` int(11) not null comment '拼团ID';

--订单返利表---常乐--2019-08-07
ALTER TABLE `b2c_order_goods_rebate` ADD COLUMN `real_rebate_money` DECIMAL(10,2) DEFAULT 0.00  NULL   COMMENT '实际返利金额';

-- 王兵兵 201-08-09 满折满减
alter table `b2c_mrking_strategy` drop column `shop_id`;
alter table `b2c_mrking_strategy` add column `status` tinyint(1)  not null default '1' comment '状态：1可用，0停用';
alter table `b2c_mrking_strategy` modify column `del_flag`  tinyint(1)  not null default 0;
alter table `b2c_mrking_strategy` modify column `type`  tinyint(1)  not null default 0 comment '类型,1每满减 2满件 3满折 4仅第X件打折';
alter table `b2c_mrking_strategy_condition` drop column `shop_id`;
alter table `b2c_mrking_strategy_condition` drop column `gift`;
alter table `b2c_mrking_strategy_condition` drop column `gift_left`;
alter table `b2c_mrking_strategy_condition` modify column `amount`  int(11)  not null default '0' comment '满几件或第几件（第X件打折）';

-- 李晓冰 8月9号修改
ALTER table b2c_code MODIFY COLUMN param_id varchar(64) NOT NULL DEFAULT '' COMMENT '记录唯一值，由url和对应参数产生';
ALTER table b2c_code CHANGE COLUMN flag del_flag tinyint  NOT NULL DEFAULT 0;

-- 王兵兵 201-08-09
alter table `b2c_mrking_strategy` add column `recommend_sort_id` text comment '指定商家分类可用';
alter table `b2c_mrking_strategy` modify column `recommend_cat_id` text comment '指定平台分类可用';
alter table `b2c_mrking_strategy` modify column `act_type` tinyint(1) not null default '0' comment '活动类型，0-全部商品参与活动；1-选中商品参与活动（由商品、平台分类、商家分类、品牌等ID字符串指定）';

-- 活动有礼 - 郑保乐 - 2019-08-12
ALTER TABLE `b2c_coupon_activity` ADD COLUMN `customize_img_path` varchar(191)  not null default '' comment '活动有礼跳转活动图片路径';
ALTER TABLE `b2c_coupon_activity` ADD COLUMN `customize_url` varchar(191)  not null default '' comment '活动有礼跳转活动链接';
ALTER TABLE `b2c_coupon_activity` CHANGE `activity_action` `activity_action` tinyint(1) DEFAULT '1' COMMENT '活动类型：1：活动送券 2：大转盘抽奖 3：跳转自定义链接';

-- 8月12日 常乐 分销推广语相关表
-- 分销推广语
-- DROP TABLE IF EXISTS `b2c_promotion_language`;
create table `b2c_promotion_language` (
  `id`                   int(8)       not null  auto_increment,
  `title`                varchar(32)  not null comment '推广语标题',
  `promotion_language`   varchar(400) not null comment '推广语',
  `create_time`          timestamp      default current_timestamp,
  `update_time`          timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  `is_block`             tinyint(1)             default 0 comment '是否停用：0否，1是',
  `del_flag`             tinyint(1)             default 0 comment '是否停用：0否，1是',
  primary key (`id`)
);

-- 用户默认分销推广语
-- DROP TABLE IF EXISTS `b2c_user_promotion_language`;
create table `b2c_user_promotion_language` (
  `id`             int(8)   not null  auto_increment,
  `lan_id`         int(8)   not null  default 0 comment '推广语关联ID',
  `user_id`        int(11)  not null  default 0 comment '会员ID',
  `create_time`    timestamp      default current_timestamp,
  `update_time`    timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key (`user_id`)
);
-- 王帅orderGoods删除营销字段，增加营销分类和营销id
alter table `b2c_order_goods`
drop column `first_special_id`,
drop column `free_ship`,
drop column `purchase_price_id`,
drop column `purchase_price_rule_id`,
drop column `reduce_price_id`,
add column `activity_type`  tinyint(1) NOT NULL DEFAULT '0' COMMENT '营销活动种类',
add column `activity_id` int(11) NOT NULL DEFAULT '0' COMMENT '营销活动id',
add column `activity_rule` int(11) NOT NULL DEFAULT '0' COMMENT '营销活动规则（目前加价购用到）';

-- 王兵兵 201-08-14
alter table `b2c_reduce_price` add column `limit_flag`  tinyint(1)   default '0' comment '超限购买设置标记，1禁止超限购买，0超限全部恢复原价';
alter table `b2c_reduce_price_goods` drop column `is_checked`;

-- 王帅retuen_order删除退款原因，修改退款原因描述，增加退款原因类型
alter table `b2c_return_order`
drop column `reason`,
drop column `return_desc`,
add column `reason_type`  tinyint(1) NOT NULL DEFAULT '0' comment '退款/退货原因类型，0：协商一致退款，1：未按约定时间发货，2：缺货，3：拍错/多拍/不想要，4：其他',
add column `reason_desc` text comment '退款/退货描述';
-- orderinfo修改营销活动id名称
alter table `b2c_order_info`
drop column `pin_group_id`,
add column `activity_id` INT ( 11 ) NOT NULL DEFAULT '0' COMMENT '营销活动id';

-- 首单特惠定义表
-- DROP TABLE IF EXISTS `b2c_first_special`;
CREATE TABLE `b2c_first_special` (
   `id`                 int(11) NOT NULL AUTO_INCREMENT,
   `name`               varchar(50)  NOT NULL COMMENT '活动名称',
   `start_time`         timestamp NULL DEFAULT NULL COMMENT '开始时间',
   `end_time`           timestamp NULL DEFAULT NULL COMMENT '结束日期',
   `batch_discount`     tinyint(1) DEFAULT '0' COMMENT '批量打几折',
   `batch_reduce`       decimal(10,2) DEFAULT NULL COMMENT '批量减多少',
   `batch_final_price`  decimal(10,2) DEFAULT NULL COMMENT '批量折后价',
   `is_batch_integer`   tinyint(1) DEFAULT '0' COMMENT '是否批量取整',
   `status`             tinyint(1) DEFAULT '1' COMMENT '状态：1：启用 0：禁用',
   `del_flag`           tinyint(1) DEFAULT '0',
   `del_time`           timestamp NULL DEFAULT NULL,
   `limit_amount`       int(11) DEFAULT '0',
   `first`              tinyint(1) DEFAULT '1' COMMENT '优先级',
   `share_config`       text  COMMENT '分享设置',
   `is_forever`         tinyint(1) DEFAULT '0' COMMENT '是否永久',
   `limit_flag`         tinyint(1) DEFAULT '0' COMMENT '超限购购买标记',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
   PRIMARY KEY (`id`)
);

-- 首单特惠商品
-- DROP TABLE IF EXISTS `b2c_first_special_goods`;
CREATE TABLE `b2c_first_special_goods` (
   `id`               int(11) NOT NULL AUTO_INCREMENT,
   `first_special_id` int(11) NOT NULL COMMENT '限时减价活动ID',
   `goods_id`         int(11) NOT NULL COMMENT '商品ID',
   `discount`         decimal(10,2) DEFAULT NULL COMMENT '打几折',
   `reduce_price`     decimal(10,2) DEFAULT NULL COMMENT '减多少钱',
   `goods_price`      decimal(10,2) DEFAULT NULL COMMENT '折后价格',
   PRIMARY KEY (`id`)
);

-- 首单特惠商品规格
-- ROP TABLE IF EXISTS `b2c_first_special_product`;
CREATE TABLE `b2c_first_special_product` (
   `id`               int(11) NOT NULL AUTO_INCREMENT,
   `first_special_id` int(11) NOT NULL COMMENT '限时减价活动ID',
   `goods_id`         int(11) NOT NULL COMMENT '商品ID',
   `product_id`       int(11) NOT NULL COMMENT '规格ID',
   `prd_price`        decimal(10,2) DEFAULT NULL COMMENT '折后价格',
   PRIMARY KEY (`id`)
);
-- 修改支付记录表创建时间
ALTER table b2c_payment_record
CHANGE  created create_time timestamp   DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间';
--修改支付记录表金额类型
ALTER table b2c_payment_record
MODIFY total_fee  DECIMAL ( 10, 2 ) NOT NULL DEFAULT '0.00' COMMENT '交易金额';

alter table `b2c_coupon_pack` change column `state` `status` tinyint(1) not null default '1' comment '开启状态1:开启，0:停用';
alter table `b2c_coupon_pack` modify column `limit_get_times` int(11) not null default 0 comment '单用户领取限制次数，0不限制';

DROP TABLE IF EXISTS `b2c_card_order`;
DROP TABLE IF EXISTS `b2c_virtual_order`;
CREATE TABLE `b2c_virtual_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_sn` varchar(20) NOT NULL DEFAULT '' COMMENT '订单编号',
  `user_id` mediumint(8) NOT NULL DEFAULT '0' COMMENT '用户id',
  `order_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单状态',
  `order_status_name` varchar(32) NOT NULL DEFAULT '' COMMENT '订单状态名称',
  `invoice_id` int(11) NOT NULL DEFAULT '0' COMMENT '发票id',
  `invoice_detail` text COMMENT '发票内容：json存储',
  `add_message` varchar(191) NOT NULL DEFAULT '' COMMENT '客户留言',
  `pay_code` varchar(30) DEFAULT NULL COMMENT '支付代号',
  `pay_name` varchar(120) DEFAULT NULL COMMENT '支付名称',
  `prepay_id` varchar(191) DEFAULT NULL COMMENT '微信支付id，用于发送模板消息',
  `pay_sn` varchar(32) DEFAULT NULL COMMENT '支付流水号',
  `money_paid` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '用户消费现金',
  `use_account` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '用户消费余额',
  `use_score` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '用户消费积分',
  `member_card_balance` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '会员卡消费金额',
  `order_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额',
  `pay_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '支付时间',
  `seller_remark` varchar(512) NOT NULL DEFAULT '' COMMENT '卖家备注',
  `star_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标星订单：0 未标星 1 标星',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除',
  `ali_trade_no` varchar(60) NOT NULL DEFAULT '' COMMENT '支付宝交易单号',
  `return_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:未申请退款，1：退款失败，2：退款成功',
  `return_score` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '退款积分',
  `return_account` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '退款余额',
  `return_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '退款现金',
  `return_card_balance` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '会员卡退款余额',
  `return_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '退款时间',
  `del_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '退款时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `goods_type` tinyint(2) NOT NULL COMMENT '虚拟商品类别：0：会员卡，1：优惠券',
  `virtual_goods_id` int(11) NOT NULL COMMENT '虚拟商品id',
  `card_no` varchar(32) DEFAULT NULL COMMENT '下单使用的会员卡号',
  `still_send_flag` tinyint(2) NOT NULL DEFAULT '1' COMMENT '退款后是否继续发放优惠劵，1：继续发放，0：停止发放',
  PRIMARY KEY (`order_id`) USING BTREE
);

-- 王兵兵 2019-08-21
alter table `b2c_mrking_voucher` modify column `least_consume` decimal(10,2) not null default '0.00' comment '满多少可用';
alter table `b2c_mrking_voucher` drop column `shop_id`;
alter table `b2c_mrking_voucher` drop column `remain_amount`;

alter table `b2c_mrking_voucher` drop column `is_random`;
alter table `b2c_mrking_voucher` add column `validity_type`  tinyint(1)   default '0' comment '优惠券有效期类型标记，1领取后开始指定时间段内有效，0固定时间段有效';
alter table `b2c_mrking_voucher` add column `validity_hour`  mediumint(11) default 0  null comment '优惠券有效小时数';
alter table `b2c_mrking_voucher` add column `validity_minute`  mediumint(11) default 0  null comment '优惠券有效分钟数';


-- 李晓冰2019-8-21修改商品表
ALTER TABLE b2c_goods ADD COLUMN `promotion_language_switch` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否使用分销推广语0关闭，1使用';
ALTER TABLE b2c_goods ADD COLUMN `promotion_language` VARCHAR (400)  NOT NULL DEFAULT '' COMMENT '推广语';
ALTER TABLE b2c_goods ADD COLUMN `deliver_place` VARCHAR ( 191 )  DEFAULT NULL COMMENT '发货地址';
ALTER TABLE b2c_goods ADD COLUMN `share_config` VARCHAR ( 500 )  DEFAULT NULL COMMENT '分享配置';
ALTER TABLE b2c_grade_prd ADD COLUMN `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除0否，1是';
ALTER TABLE b2c_goods_rebate_price ADD COLUMN `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除0否，1是';

-- 王兵兵 2019-08-22
alter table `b2c_virtual_order` modify column `still_send_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '优惠券礼包订单-退款后是否继续发放优惠劵，1：继续发放，0：停止发放';
alter table `b2c_virtual_order` add column `access_mode`  tinyint(1)   default '0' comment '优惠券礼包订单-下单时的领取方式，0：现金购买，1：积分购买，2直接领取';
alter table `b2c_virtual_order` add index `order_sn` (`order_sn`);
alter table `b2c_virtual_order` add index `user_id` (`user_id`);
alter table `b2c_virtual_order` modify column `use_score` int(11) NULL DEFAULT '0' COMMENT '用户消费积分';
alter table `b2c_virtual_order` modify column `return_score` int(11) NULL DEFAULT '0' COMMENT '退款积分';

alter table `b2c_virtual_order` modify column `return_score` int(11) NULL DEFAULT '0' COMMENT '已退款积分';
alter table `b2c_virtual_order` modify column `return_account` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已退款余额';
alter table `b2c_virtual_order` modify column `return_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已退款现金';
alter table `b2c_virtual_order` modify column `return_card_balance` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '已退款会员卡余额';
alter table `b2c_virtual_order` modify column `del_time` timestamp null COMMENT '删除时间';
alter table `b2c_virtual_order` modify column `return_time` timestamp null COMMENT '退款时间';


DROP TABLE IF EXISTS `b2c_refund_card_record`;
DROP TABLE IF EXISTS `b2c_virtual_order_refund_record`;
create table `b2c_virtual_order_refund_record` (
  `id`              int(11)                        not null auto_increment,
  `order_sn`            varchar(30)  not null default '',
  `user_id`             int(11)                        not null default '0',
  `use_score`           int(11)                 not null default '0' comment '退款积分',
  `use_account`         decimal(10, 2)                 not null default '0.00' comment '退款余额',
  `money_paid`          decimal(10, 2)                 not null default '0.00' comment '退款现金',
  `member_card_balance` decimal(10, 2)                 not null default '0.00' comment '退款会员卡余额',
  `refund_time`         timestamp                              not null default CURRENT_TIMESTAMP comment '订单退款时间',
  `is_success`          tinyint(1)                     not null default '0' comment '处理状态，1：退款失败，2：退款成功',
  primary key (`id`),
  key `order_sn` (`order_sn`),
  key `user_id` (`user_id`)
);



drop table if exists `b2c_mp_official_account_user`;
create table `b2c_mp_official_account_user` (
  `rec_id`         int unsigned not null auto_increment,
  `openid`         varchar(128) not null comment '用户的标识，对当前公众号唯一',
  `app_id`         varchar(191) not null comment '授权公众号appId',
  `sys_id`         int(11)      not null default '0' comment '系统账户ID',
  `subscribe`      tinyint(1)            default 0 comment '是否订阅，为0代表此用户没有关注该公众号，拉取不到其余信息。',
  `nickname`       varchar(191)          default '',
  `sex`            tinyint(1)   not null default 0 comment '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `language`       varchar(20) comment '用户的语言，简体中文为zh_CN',
  `city`           varchar(50) comment '用户所在城市',
  `province`       varchar(50) comment '用户所在省份',
  `country`        varchar(50) comment '用户所在国家',
  `headimgurl`     varchar(191) comment '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空',
  `subscribe_time` timestamp    null     default null comment '用户关注时间，如果用户曾多次关注，则取最后关注时间',
  `unionid`        varchar(191) comment '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段',
  primary key (`rec_id`),
  unique key (`openid`, `app_id`),
  key (`sys_id`),
  key (`unionid`)
);

-- 黄壮壮： 为表b2c_member_card 添加两个字段
ALTER TABLE b2c_member_card ADD COLUMN `stock` int(11) DEFAULT 0 COMMENT '发放总量';
ALTER TABLE b2c_member_card ADD COLUMN `limit` int(11) DEFAULT 1 COMMENT '领取限制';

-- 孔德成 2019年8月29日 16:19:57
-- --  拼团活动定义表
--  drop table if exists `b2c_group_buy_define`;
create table `b2c_group_buy_define`
(
    `id`               int(11)      not null auto_increment,
    `goods_id`         int(11)      not null comment '商品id',
    `name`             varchar(100) not null comment '活动名称',
    `limit_amount`     smallint(6)  not null comment '成团人数 不小于2人',
    `join_limit`       smallint(6)  not null default 0 comment '参团限制 0不限制',
    `open_limit`       smallint(6)  not null default 0 comment '开团限制 0不限制',
    `limit_buy_num` 	 int(6)   		not null default 0 comment '最少购买数 0不限制',
    `limit_max_num` 	 int(6) 		  not null default 0 comment '最多购买数 0不限制',
    `start_time`       timestamp    null     default null comment '开始时间',
    `end_time`         timestamp    null     default null comment '结束时间',
    `stock`            smallint(6)  not null default 0 comment '总库存',
    `sale_num`         smallint(6)  not null default 0 comment '销量',
    `is_default`       tinyint(1)   not null default 0 comment '默认成团 ',
    `activity_type`    tinyint(1)   not null default '1' comment '活动类型：1：普通拼团，2：老带新团',
    `is_grouper_cheap` tinyint(1)   not null default '0' comment '是否开启团长优惠：0：不开启，1：开启',
    `shipping_type`     tinyint(2)   not null  default '0' comment '运费类型 1免运费 2自定义',
    `reward_coupon_id` varchar(200) null comment '拼团失败发放优惠券',
    `share_config`     text comment '分享设置',
    `status`           tinyint(1)   not null default 1 comment '状态： 1：启用  0： 禁用 2 代表已无库存',
    `del_flag`         tinyint(1)   not null default 0,
    `del_time`         int(11)      not null default 0,
    `create_time`      timestamp             default current_timestamp,
    `update_time`      timestamp             default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`id`)
);

-- 分享有礼b2c_share_award表添加删除时间字段 liufei
alter table b2c_share_award add column `del_time` timestamp null default null comment '删除时间';

-- 黄壮壮： 修改b2c_user_detail中月收入monthly_income 类型tinyint为int(8)
alter table b2c_user_detail MODIFY COLUMN monthly_income int(8)
-- 王帅订单增加币种
ALTER TABLE b2c_order_info add column `currency` varchar(10) NOT NULL DEFAULT 'CNY' COMMENT '币种';
-- 王帅退款订单增加币种
ALTER TABLE b2c_return_order add column `currency` varchar(10) NOT NULL DEFAULT 'CNY' COMMENT '币种';
-- 王帅买单订单增加'会员卡号'
ALTER TABLE `b2c_store_order`
ADD COLUMN `card_no` varchar(32) NOT NULL DEFAULT '' COMMENT '会员卡号';

-- 王帅买单订单增加币种
ALTER TABLE b2c_store_order add column `currency` varchar(10) NOT NULL DEFAULT 'CNY' COMMENT '币种';

-- 2019-09-24虚拟商品订单增加币种
alter table b2c_virtual_order add column `currency` varchar(10) NOT NULL DEFAULT 'CNY' COMMENT '币种';
-- 王帅买单订单修改英文
ALTER TABLE `b2c_store_order`
CHANGE COLUMN `member_card_redunce` `member_card_reduce` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '会员卡抵扣金额' ;

-- 梁晨 用户-优惠券表修改type字段
ALTER TABLE `b2c_customer_avail_coupons` MODIFY COLUMN `type` TINYINT NOT NULL DEFAULT '0' COMMENT '0为减价，1为打折';

--user添加字段
ALTER TABLE b2c_user
ADD COLUMN `scene` INT(11) NULL DEFAULT -1 COMMENT '用户微信来源 -1搜索、公众号等入口（主动）进入，-2分享（被动）进入，-3扫码进入 -4未获取' AFTER `invite_time`;

-- 添加视频字段

alter table b2c_uploaded_video add column `video_duration` INT(6) DEFAULT 0  NULL   COMMENT '视频时长';
alter table b2c_uploaded_video add column    `user_id`          INT(11) DEFAULT 0  NULL   COMMENT '用户ID';
alter table b2c_uploaded_video add column    `del_time` timestamp NULL DEFAULT  null COMMENT '删除时间';
alter table b2c_uploaded_video add column   `upyun_del` TINYINT(1) DEFAULT 0  NULL   COMMENT '又拍云是否删除';

-- 添加视频分类表
create table `b2c_uploaded_video_category` (
  `video_cat_id`        int(10) not null auto_increment,
  `shop_id`             int(11)          not null default 0 comment '店铺ID',
  `video_cat_name`      varchar(60)      not null default '',
  `video_cat_parent_id` int(10)          not null default 0,
  `cat_ids`             varchar(191)     not null default '0' comment '层级ID串,逗号分隔',
  `level`               tinyint                   default 0 comment '层级，0开始',
  `sort`                int(11)                   default 1 comment '排序优先级',
  `create_time`          timestamp            default current_timestamp,
  `update_time`          timestamp            default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`video_cat_id`),
  key (`shop_id`)
);

ALTER TABLE `b2c_reduce_price` CHANGE `extend_time` `extend_time` VARCHAR(50) NULL   COMMENT '每月第几日（单选）；每周第几天（多选，@符隔开）；';

--常乐 商品收藏表
ALTER TABLE b2c_user_collection add column `username` varchar(32) NOT NULL DEFAULT '' COMMENT '用户名';
ALTER TABLE b2c_user_collection add column `collect_price` decimal(10, 2) NOT NULL DEFAULT '0.00' COMMENT '收藏时商品价格';

alter table `b2c_part_order_goods_ship` add index `product_id` (`product_id`);

梁晨 2019-10-18 添加优惠券字段
alter table `b2c_mrking_voucher` add column `limit_surplus_flag`  tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否限制库存：0限制，1不限制';

--王帅增加订单字段
ALTER TABLE `b2c_order_info`
ADD COLUMN `free_ship` decimal(10, 2) DEFAULT 0.00 COMMENT '运费抵扣' ,
ADD COLUMN `free_detail` text  COMMENT '运费抵扣规则',
ADD COLUMN `sub_goods_price` decimal(10, 2) DEFAULT 0.00 COMMENT '子单金额' ,
ADD COLUMN `is_refund_coupon` tinyint(1) DEFAULT 0 COMMENT '是否退优惠券',
ADD COLUMN `is_finish_refund` tinyint(1) DEFAULT 0 COMMENT '子订单是否已处理退款',
ADD COLUMN `is_view_comment` tinyint(1) DEFAULT 1 COMMENT '是否已查看评价',
ADD COLUMN `pos_order_action` tinyint(1) DEFAULT 1 COMMENT '1:扫码购 2：仅自提' ,
ADD COLUMN `order_remind` tinyint(1) DEFAULT 0 COMMENT '发货提醒次数',
ADD COLUMN `order_remind_time` timestamp(0) NULL DEFAULT NULL COMMENT '发货提醒时间',
ADD COLUMN `extend_receive_action` tinyint(1) DEFAULT 0 COMMENT '延长收货操作人：1:商家 2:用户' ,
ADD COLUMN `extend_receive_time` timestamp(0) NULL DEFAULT NULL COMMENT '收货延长时间' ,
ADD COLUMN `tk_order_type` tinyint(2) DEFAULT 0 COMMENT '淘客订单类型：0：普通订单，1：京东订单，2：淘宝订单' ,
ADD COLUMN `pay_award_id` int(9) DEFAULT NULL COMMENT '支付有礼id' ;

-- 黄壮壮  member_card 会员卡表添加折扣品牌字段
ALTER TABLE b2c_member_card ADD COLUMN discount_brand_id varchar(299) DEFAULT NULL COMMENT '折扣品牌ID';


--更新字段长度，使主库和店铺库保持一致
ALTER TABLE `b2c_user_detail`
CHANGE COLUMN `user_id` `user_id` INT(11) NOT NULL ;

--2019-10-28 限时降价、首单特惠相关表的规格ID与规格表保持一致
ALTER TABLE `b2c_reduce_price_product` CHANGE COLUMN `product_id` `prd_id` int(11) NOT NULL COMMENT '规格id';
ALTER TABLE `b2c_first_special_product` CHANGE COLUMN `product_id` `prd_id` int(11) NOT NULL COMMENT '规格id';

-- 图片上传增加 userid
ALTER TABLE `jmini_shop_245547`.`b2c_uploaded_image`
MODIFY COLUMN `img_cat_id` int(11) NOT NULL DEFAULT 0 COMMENT '图片分类 默认0 用户上传为-1' AFTER `img_url`,
ADD COLUMN `user_id` int(11) NOT NULL DEFAULT 0 AFTER `shop_id`;


-- 支付有礼活动
-- drop table if 	exists `b2c_pay_reward`;
create table `b2c_pay_reward` (
	`id` int ( 11 ) not null auto_increment,
	`act_name` varchar ( 120 ) not null default '' comment '活动名称',
	`start_time` timestamp null default null,
	`end_time` timestamp null default null,
	`type` tinyint ( 1 ) default 0 comment '类型，1为分裂 2抽奖 3送券 4跳转自定义链接',
	`everytime_amount` smallint ( 4 ) null default '0' comment '每一单可以发放优惠券数量',
	`denomination` decimal ( 10, 2 ) not null default '0' comment '触发条件：支付金额满',
	`coupon_ids` varchar ( 50 ) null comment '支付送券的优惠券id',
	`lottery_id` int ( 10 ) not null comment '幸运大抽奖',
	`recommend_type` tinyint ( 1 ) not null default '1' comment '支付有礼跳转链接 1:全部商品可用 2：指定商品可用',
	`recommend_goods_id` text comment '指定商品可用',
	`recommend_cat_id` text comment '指定平台可用',
	`recommend_sort_id` text comment '指定商家分类可用',
	`img_url` varchar ( 191 ) not null default '' comment '支付有礼跳转活动图片路径',
	`link_path` varchar ( 191 ) not null default '' comment '支付有礼跳转活动链接',
	`status` tinyint ( 2 ) not null default '0' comment '状态：1停用',
	`is_delete` tinyint ( 1 ) null default '0' comment '1为删除状态',
	`create_time` timestamp default current_timestamp,
	`update_time` timestamp default current_timestamp on update current_timestamp comment '最后修改时间',
primary key ( `id` ));


-- 支付有礼记录
-- drop table  if	exists `b2c_pay_reward_record`;
create table `b2c_pay_reward_record` (
	`id` int ( 11 ) not null auto_increment,
	`pay_reward_id` int ( 11 ) not null comment '支付有礼活动id',
	`order_sn` varchar ( 20 ) collate utf8mb4_unicode_ci not null default '' comment '订单编号',
	`user_id` int ( 11 ) not null comment '下单用户id',
	`type` tinyint ( 1 ) default 0 comment '类型，1为分裂  2抽奖 3送券 4跳转自定义链接',
	`mrking_voucher_id` varchar ( 500 ) not null comment '发送的活动优惠券，逗号分隔',
	`is_delete` tinyint ( 1 ) null default '0' comment '1为删除状态',
	`create_time` timestamp default current_timestamp,
	`update_time` timestamp default current_timestamp on update current_timestamp comment '最后修改时间',
primary key ( `id` ));

--ws 地址增加经纬度
ALTER TABLE `b2c_user_address`
ADD COLUMN `lat` varchar(20)  DEFAULT NULL COMMENT '纬度' ,
ADD COLUMN `lng` varchar(20)  DEFAULT NULL COMMENT '经度' ;
--ws 修改类型
ALTER TABLE b2c_order_goods
MODIFY COLUMN goods_number int(11) NOT NULL DEFAULT 1 ,
MODIFY COLUMN send_number int(11) NOT NULL DEFAULT 0 ,
MODIFY COLUMN return_number int(11) NOT NULL DEFAULT 0 ;

-- 更新字段默认值
alter table b2c_member_card
MODIFY COLUMN `store_list` varchar(191)   not null default '[]' comment '可用门店列表';


-- 更新字段类型
ALTER TABLE `b2c_goods_card_couple`
MODIFY COLUMN `card_id` int(11) NOT NULL COMMENT '会员卡ID' AFTER `id`;

-- 统计相关表添加 int 主键
alter table b2c_distribution_tag
    add id int auto_increment primary key first;

--  支付有礼 2019年10月28日 17:04:30
drop table if exists `b2c_pay_reward`;
create table `b2c_pay_award`
(
    `id`              int(9)    not null auto_increment,
    `activity_names`  varchar(50)    default null comment '活动名称',
    `time_type`       tinyint(1)     default '0' comment '时间类型0固时1永久',
    `start_time`      timestamp null default null comment '开始',
    `end_time`        timestamp null default null comment '结束',
    `act_first`       mediumint(5)   default '0' comment '优先级',
    `goods_area_type` mediumint(5)   default '0' comment '商品范围类型 0全部商品 1 部分商品',
    `goods_ids`       text comment '商品id',
    `goods_cat_ids`   text comment '商品平台分类',
    `goods_sort_ids`  text comment '商品商家分类',
    `min_pay_money`   decimal(10, 2) default '0.00' comment '最小金额',
    `limit_times`     int(9)         default '0' comment '每个用户可参加次数',
    `award_list`      text comment '奖品列表json',
    `status`          tinyint(1)     default '0' comment '状态1停用',
    `del_flag`        tinyint(1)     default '0' comment '1删除',
    `create_time`     timestamp      default current_timestamp comment '创建时间',
    `update_time`     timestamp      default current_timestamp on update current_timestamp comment '跟新时间',
    primary key (`id`)
);


-- 支付有礼记录
drop table if exists `b2c_pay_reward_record`;
create table `b2c_pay_award_record`
(
    `id`          int(9) not null auto_increment,
    `user_id`     int(9)       default null comment '用户id',
    `award_id`    int(9)       default null comment '支付有礼活动id',
    `order_sn`    varchar(50)  default null comment '订单号',
    `gift_type`   tinyint(4)   default null comment '礼物类型 0 无奖品 1普通优惠卷  2分裂优惠卷 3幸运大抽奖 4 余额 5 商品 6积分 7 自定义',
    `award_times` int(11)      default null comment '',
    `award_data`  varchar(599) default null comment '',
    `send_data`   varchar(599) default null comment '',
    `status`      tinyint(1)   default null comment '',
    `keep_days`   mediumint(5) default null,
    `create_time` timestamp    default current_timestamp,
    `update_time` timestamp    default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`id`)
);

-- 梁晨 商品推荐表添加字段
ALTER TABLE b2c_recommend_goods
ADD COLUMN `choose_type` TINYINT(2) DEFAULT '0' COMMENT '0普通推荐1智能推荐',
ADD COLUMN `recommend_number` INT(4) DEFAULT '0' COMMENT '智能推荐商品数';

-- 梁晨 修改商品评价表字段允许为空
ALTER TABLE b2c_comment_goods MODIFY COLUMN `comm_note` VARCHAR(255) COMMENT '评论内容';

-- 修改会员卡表，可用门店store_list默认值
ALTER TABLE `b2c_member_card`
MODIFY COLUMN `store_list` varchar(191)  NOT NULL DEFAULT '[]' COMMENT '可用门店' AFTER `use_time`;
-- 添加会员卡表添加三个与优惠券相关的字段
ALTER TABLE `b2c_member_card`
ADD COLUMN `send_coupon_switch` tinyint(1) DEFAULT 0 COMMENT '是否开卡送券：0不是，1是' AFTER `discount_brand_id`,
ADD COLUMN `send_coupon_type` tinyint(1) DEFAULT 0 COMMENT '送惠类型：0优惠券，1优惠券礼包' AFTER `send_coupon_switch`,
ADD COLUMN `send_coupon_ids` varchar(20) DEFAULT NULL COMMENT '赠送优惠券或礼包id，字符串逗号隔开' AFTER `send_coupon_type`;

--记录表添加账户类型
ALTER TABLE `b2c_record_admin_action`
ADD COLUMN `account_type` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '账户类型，默认1商家账户';

-- 更新字段默认值,注释
alter table b2c_service_technician
MODIFY COLUMN `service_list` varchar(191)   not null default '[]' comment '当type=1是服务项目ID数组';
-- 更新字段默认值
alter table b2c_store_service
MODIFY COLUMN      `tech_services_number` int(11)  default  0  comment '技师单时段服务数量',
MODIFY COLUMN     `services_number`      int(11)                 default 0 comment '服务数量',


-- 梁晨 修改用户统计表字段类型
ALTER TABLE b2c_user_summary_trend
MODIFY COLUMN `total_paid_money` DECIMAL(10,2) DEFAULT NULL COMMENT '总成交金额',
MODIFY COLUMN `new_paid_money` DECIMAL(10,2) DEFAULT NULL COMMENT '成交新客户支付金额',
MODIFY COLUMN `old_paid_money` DECIMAL(10,2) DEFAULT NULL COMMENT '成交新老客户支付金额';

-- 修改门店服务评价
ALTER TABLE b2c_comment_service
drop KEY `shop_id`,
add KEY `service_id` (`service_id`) USING BTREE ;

-- 门店服务订单添加字段
ALTER TABLE b2c_service_order
ADD  COLUMN  `member_card_no` varchar(32)  DEFAULT '0' COMMENT '会员卡NO',
ADD  COLUMN  `member_card_balance` decimal(10,2) DEFAULT '0.00' COMMENT '会员卡消费金额',
ADD  COLUMN   `use_account` decimal(10,2) DEFAULT '0.00' COMMENT '用户消费余额';

-- 修改秒杀表库存类型 2019-11-11
ALTER TABLE b2c_sec_kill_define
MODIFY COLUMN `stock` int(11) NOT NULL DEFAULT '0' COMMENT '总库存',
MODIFY COLUMN `sale_num` int(11) NOT NULL DEFAULT '0' COMMENT '销量';

-- 修改秒杀商品规格表库存类型 2019-11-11
ALTER TABLE b2c_sec_kill_product_define
MODIFY COLUMN `stock` int(11) NOT NULL DEFAULT '0' COMMENT '总库存',
MODIFY COLUMN `sale_num` int(11) NOT NULL DEFAULT '0' COMMENT '销量',
MODIFY COLUMN `total_stock` int(11) NOT NULL DEFAULT '0' COMMENT '总库存';

ALTER TABLE b2c_sec_kill_product_define add index sk_id(`sk_id`);
ALTER TABLE b2c_sec_kill_define add index goods_id(`goods_id`);

-- 新增 用户统计-rfm统计表
drop table if exists `b2c_user_rfm_summary`;
CREATE TABLE `b2c_user_rfm_summary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ref_date` date NOT NULL COMMENT '统计日期，如2018-09-04，按照下单时间',
  `recency_type` tinyint(2) NOT NULL COMMENT '最近一次消费时间类型（小达顺序，左闭右开）：1最近5天内，2最近5到10天，3最近10到30天，4最近30到90天，5最近90到180天，6最近180到365天，7最近365天以上',
  `frequency_type` tinyint(2) NOT NULL COMMENT '最近时间范围内用户消费频次类型：1，2，3，4，5大于等于5次',
  `total_paid_money` decimal(10,2) DEFAULT NULL COMMENT '总成交金额',
  `pay_user_num` int(11) DEFAULT '0' COMMENT '下单人数（已付款订单人数）',
  `create_time` timestamp    default current_timestamp,
  `update_time` timestamp    default current_timestamp on update current_timestamp comment '最后修改时间',
  `order_num` int(11) DEFAULT '0' COMMENT '订单数量（已付款订单数）',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ref_date` (`ref_date`) USING BTREE
);

-- 11月13日 常乐-返利策略添加首单返利
ALTER TABLE `b2c_distribution_strategy` ADD COLUMN `first_rebate` TINYINT(1) DEFAULT 0  NOT NULL   COMMENT '邀请新用户下首单返利';
ALTER TABLE `b2c_distribution_strategy` ADD COLUMN `first_ratio` FLOAT DEFAULT 0  NULL   COMMENT '首单返利金额';
ALTER TABLE `b2c_distribution_strategy` ADD COLUMN `first_ratio_2` FLOAT DEFAULT 0  NULL   COMMENT '首单返利金额';
ALTER TABLE `b2c_distribution_strategy` ADD COLUMN `first_ratio_3` FLOAT DEFAULT 0  NULL   COMMENT '首单返利金额';
ALTER TABLE `b2c_distribution_strategy` ADD COLUMN `first_ratio_4` FLOAT DEFAULT 0  NULL   COMMENT '首单返利金额';
ALTER TABLE `b2c_distribution_strategy` ADD COLUMN `first_ratio_5` FLOAT DEFAULT 0  NULL   COMMENT '首单返利金额';

-- 11月13 李晓冰-删除商品规格del_flag无效字段
ALTER TABLE b2c_goods_spec_product DROP COLUMN del_flag;
ALTER TABLE b2c_goods_spec_product_bak DROP COLUMN del_flag;
-- 重构商品概览统计表
drop table if exists `b2c_goods_user_summary`;
-- drop table if exists `b2c_goods_overview_summary`;
create table `b2c_goods_overview_summary`
(
    `id`                int(11) not null auto_increment,
    `ref_date`          date       default null comment '2018-09-04',
    `type`              tinyint(1) default null comment '1,7,30',
    `on_shelf_goods_num`   int(11)    default 0 comment '在架商品数',
    `sold_goods_num`   int(11)    default 0 comment '动销商品数(统计时间内，销量不为0的商品数量)',
    `visited_goods_num`    int(11)    default 0 comment '被访问商品数',
    `goods_user_visit`  int(11)    default 0 comment 'uv(商品访客数)',
    `goods_pageviews`       int(11)    default 0 comment 'pv(商品浏览量)',
    `purchase_num`  int(11)    default 0 comment '加购人数',
    `purchase_quantity` int(11)    default 0 comment '加购件数',
    `paid_goods_num` int(11)    default 0 comment '付款商品数',
    `order_goods_num`  int(11)    default 0 comment '下单商品数',
    `create_time`       timestamp  default current_timestamp comment '创建时间',
    `update_time`       timestamp  default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`id`),
    key `ref_type` (`ref_date`, `type`) using btree
) comment '商品概览统计表' ;

-- 技师表
ALTER TABLE b2c_service_technician
MODIFY COLUMN    `bg_img_path`          varchar(191)  default '' comment '技师头像地址',
MODIFY COLUMN    `technician_introduce` varchar(200)  default '' comment '技师简介',
MODIFY COLUMN    `group_id`             int(11)   default 0 comment '技师分组',
MODIFY COLUMN    `service_type`         tinyint(2)  default 0 comment '技师服务项目：0所有，1部分',
MODIFY COLUMN    `service_list`         varchar(191)  default '[]' comment '当type=1是服务项目id数组',
MODIFY COLUMN    `remarks`              varchar(1024) default '' comment '备注',
MODIFY COLUMN    `del_flag`             tinyint(1)  default 0 comment '0正常，1删除';

--添加分裂优惠券相关字端
alter table `b2c_mrking_voucher` add column `random_min` int(11) not null default 0    COMMENT '分裂优惠卷随机金额最低';
alter table `b2c_mrking_voucher` add column `random_max` int(11) not null default 0    COMMENT '分裂优惠卷随机金额最高';
alter table `b2c_mrking_voucher` add column `receive_per_num` smallint(3) not null default 0    COMMENT '分裂优惠券领券人数是否限制 0不限制 1限制';
alter table `b2c_mrking_voucher` add column `receive_num` int(11) not null default 0    COMMENT '分裂优惠券可领券人数';
-- 商品评价表 添加字段
ALTER TABLE `b2c_comment_goods`
ADD COLUMN `rec_id` int(9) DEFAULT '0' COMMENT 'order_goods的rec_id',
ADD COLUMN `prd_id` int(9) DEFAULT '0' COMMENT '商品规格id';

-- 商品规格表 添加字段
ALTER TABLE `b2c_goods_spec_product`
ADD COLUMN `del_flag` tinyint(1) NOT NULL DEFAULT '0';

-- 商品统计-效果
ALTER TABLE `b2c_goods_summary`
ADD COLUMN `goods_sales` decimal(10, 2)  NULL DEFAULT 0.0 comment '销售额',
ADD COLUMN `recommend_user_num` int(11)  NULL DEFAULT 0 comment '推荐人数',
ADD COLUMN `collect_use_num` int(11)  NULL DEFAULT 0 comment '收藏人数',
ADD COLUMN `share_pv` int(11)  NULL DEFAULT 0 comment '分享次数',
ADD COLUMN `share_uv` int(11)  NULL DEFAULT 0 comment '分享人数',
MODIFY COLUMN    `new_user_number`   int(11)    default 0 comment '新成交客户数',
MODIFY COLUMN    `old_user_number`   int(11)    default 0 comment '老成交客户数',
MODIFY COLUMN    `pv`                int(11)    default 0 comment '浏览量',
MODIFY COLUMN    `uv`                int(11)    default 0 comment '访客数',
MODIFY COLUMN    `cart_uv`           int(11)    default 0 comment '加购人数',
MODIFY COLUMN    `paid_uv`           int(11)    default 0 comment '付款人数',
MODIFY COLUMN    `paid_goods_number` int(11)    default 0 comment '付款商品件数',
drop KEY `ref_type`,
ADD UNIQUE KEY `uni_key` (`ref_date`,`type`,`goods_id`) using btree ;

-- kdc 2019年11月22日 16:44:46

-- 开屏有礼活动
-- drop table if exists `b2c_Coopen_activity`;
CREATE TABLE `b2c_coopen_activity`
(
    `id`                 int(11)                                 NOT NULL AUTO_INCREMENT,
    `action`             tinyint(1)                              NOT NULL DEFAULT '1' COMMENT '针对用户群体： 1: 初次访问新用户 2: 全部用户 3:未支付的用户',
    `name`               varchar(50) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '活动名称',
    `title`              varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '宣传语',
    `bg_action`          tinyint(4)                              NOT NULL DEFAULT '1' COMMENT '背景图',
    `is_forever`         int(11)                                 NOT NULL DEFAULT '0' COMMENT '是否永久有效 0:无效 1:有效',
    `start_date`         datetime                                NOT NULL COMMENT '有效期-起始',
    `end_date`           datetime                                NOT NULL COMMENT '有效期-结束',
    `first`              int(11)                                 NOT NULL DEFAULT '1' COMMENT '优先级',
    `activity_action`    tinyint(1)                                       DEFAULT '1' COMMENT '活动类型：1：活动送券 2：大转盘抽奖 3：跳转自定义链接 4: 积分 5:余额  6:分裂',
    `mrking_voucher_id`  varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '活动优惠券，逗号分隔',
    `lottery_id`         int(11)                                 not null default 0 comment '抽奖活动id',
    `customize_img_path` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '活动有礼跳转活动图片路径',
    `customize_url`      varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '活动有礼跳转活动链接',
    `give_score`         decimal(10, 2)                                   DEFAULT '0.00' COMMENT '积分',
    `give_account`       decimal(10, 2)                                   DEFAULT '0.00' COMMENT '余额',
    `award_num`          int(11)                                          DEFAULT '-1' COMMENT '发放数量',
    `status`             tinyint(1)                              NOT NULL DEFAULT '1' COMMENT '状态： 1: 正常 0: 关闭',
    `del_flag`           tinyint(4)                              NOT NULL DEFAULT '0',
    `del_time`           int(11)                                 NOT NULL DEFAULT '0',
    `create_time`        timestamp                               NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`        timestamp                               NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (`id`)
);
-- 开屏有礼活动记录
-- drop table if exists `b2c_coopen_activity_records`;
CREATE TABLE `b2c_coopen_activity_records`
(
    `id`                int(11)      NOT NULL AUTO_INCREMENT,
    `activity_id`       int(11)      NOT NULL COMMENT '活动id',
    `user_id`           int(11)      NOT NULL,
    `activity_action`   tinyint(1)                                                    DEFAULT '1' COMMENT '活动类型：1：活动送券 2：大转盘抽奖 3：跳转自定义链接 4: 积分 5:余额  6:分裂',
    `comment`           varchar(200) not null                                         DEFAULT '' comment '说明',
    `receive_time`      timestamp    NOT NULL COMMENT '领取时间',
    `mrking_voucher_id` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '已领取的优惠券',
    `lottery_id`        int(11) unsigned                                              DEFAULT NULL COMMENT '抽奖id',
    `give_num`          decimal(10, 2)                                                DEFAULT '0.00' COMMENT '积分或者余额数量',
    `create_time`       timestamp    NOT NULL                                         DEFAULT CURRENT_TIMESTAMP,
    `update_time`       timestamp    NOT NULL                                         DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    PRIMARY KEY (`id`) USING BTREE
);

-- 添加字段
ALTER TABLE `b2c_user_account`
ADD COLUMN `settle_account` decimal(10, 2) DEFAULT 0.00 COMMENT '更新后的余额' AFTER `source`;

-- 修改默认值
ALTER TABLE `b2c_user_account`
MODIFY COLUMN `admin_note` varchar(191) not null default '' COMMENT '操作员备注' AFTER `amount`;

-- 服务评价表结构修改
alter table `b2c_comment_service`
drop column `shop_id`,
modify column  `technician_id` int(11) not null default 0 comment '技师id',
modify column  `user_score`    int(11)   null default  0 comment '评价可得积分',
modify column  `anonymousflag` tinyint(1)  not null default  0  comment '匿名状态 0.未匿名；1.匿名',
modify column `comm_img`      varchar(1000)    default '[]' comment '评论图片',
add unique index `order_sn` (`order_sn`) using BTREE;


-- 用户会员卡表添加字段
ALTER TABLE `b2c_user_card`
ADD COLUMN `qrcode_img` varchar(200) NOT NULL DEFAULT '' COMMENT '二维码条形码位置' AFTER `exchang_surplus`;
-- 删除字段
ALTER TABLE `b2c_user_card` DROP COLUMN `qrcode_img`;
add unique index `order_sn` (`order_sn`) using BTREE;

-- 李晓冰 修改商品标签表结构，删除无用字段，修改错误类型
ALTER TABLE b2c_goods_label DROP del_time;
ALTER TABLE b2c_goods_label MODIFY del_flag TINYINT(1);

ALTER TABLE `b2c_mrking_voucher` CHANGE `random_min` `random_min` decimal(10, 2) DEFAULT '0' COMMENT '分裂优惠卷随机金额最低';
ALTER TABLE `b2c_mrking_voucher` CHANGE `random_max` `random_max` decimal(10, 2) DEFAULT '0' COMMENT '分裂优惠卷随机金额最高';
ALTER TABLE `b2c_mrking_voucher` CHANGE `receive_per_num` `receive_per_num` tinyint(1) DEFAULT '0' COMMENT '分裂优惠券领券人数是否限制 0不限制 1限制';

-- 修改默认值
ALTER TABLE `b2c_distribution_strategy`
MODIFY COLUMN `status` tinyint(1) not null default '1' COMMENT '是否启用，1启用，0停用';

-- 修改默认值
ALTER TABLE `b2c_reduce_price`
MODIFY COLUMN `period_action` tinyint(1) NOT NULL DEFAULT '0' COMMENT '周期类型：0:不进行周期重复  1:每天 2:每月 3:每周';

--修改默认值
ALTER TABLE `b2c_subscribe_message`
CHANGE COLUMN `can_use_num` `can_use_num` INT(11) NULL DEFAULT 0 COMMENT '可使用数' ;

--  孔德成 修改字段名称
ALTER TABLE `b2c_comment_award`
CHANGE COLUMN `leve` `level` int(9) NULL DEFAULT 1 COMMENT '优先级' AFTER `is_forever`;

-- 添加字段 黄壮壮
ALTER TABLE `b2c_mrking_voucher`
ADD COLUMN `recommend_type` tinyint(1) NOT NULL DEFAULT 1 COMMENT '支付有礼跳转链接 1:全部商品可用 2：指定商品可用' AFTER `validity_type`;

-- 添加字段
ALTER TABLE `b2c_reduce_price`
ADD COLUMN `first` tinyint(1) NOT NULL DEFAULT 1 COMMENT '优先级';

-- 添加字段
ALTER TABLE `b2c_card_receive_code`
ADD COLUMN `status` tinyint(1) DEFAULT 0 COMMENT '1: 可用 0：禁用' AFTER `error_msg`;

-- 购物车 `b2c_cart` 孔德成
drop table if exists `b2c_cart`;
create table `b2c_cart`
(
    `cart_id`        int(11)        not null auto_increment,
    `store_id`       int(11)        not null default '0' comment '门店id',
    `user_id`        int(11)        not null default '0' comment '用户id',
    `goods_id`       int(11)        not null default '0' comment '商品id',
    `goods_sn`       varchar(60)    not null default '' comment '商品sn',
    `goods_name`     varchar(120)   not null default '' comment '商品名称',
    `goods_specs`    text comment '例如,颜色:黑色',
    `product_id`     int(11)        not null default '0' comment '规格产品id',
    `prd_sn`         varchar(60)    not null default '' comment '规格sn',
    `goods_price`    decimal(10, 2) not null default '0.00' comment '商品价格',
    `is_checked`     tinyint(1)     not null default 0 comment '是否选中',
    `cart_number`    smallint(5)    not null default '0' comment '数量',
    `original_price` decimal(10, 2) not null default '0.00' comment '加入购物车时的价格',
    `type`           tinyint(1)     not null default '0' comment '类型 0 普通 ',
    `extend_id`      int(11)        not null default '0' comment '扩展字段:对应type的类型 ',
    `create_time`    timestamp               default current_timestamp,
    `update_time`    timestamp               default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`cart_id`),
    KEY `user_id` (`cart_id`,`store_id`) USING BTREE
);

-- 标签用户下单统计
drop table if exists `b2c_distribution_tag`;
create table `b2c_distribution_tag`
(
    `id`                   int(11)         not null auto_increment,
    `ref_date`         date     not null comment '日期',
    `type`             tinyint(1)     not null comment '1,7,30',
    `tag_id` int(11) not null COMMENT '标签id',
    `tag_name` varchar(50) not null COMMENT '标签内容',
    `pay_order_num`    int(11)        default 0 comment '付款订单数',
    `pay_order_money`  decimal(10, 2) default 0.0 comment '付款金额',
    `pay_user_num`     int(11)        default 0 comment '付款人数',
    `pay_goods_number` int(11)        default 0 comment '付款商品件数',
    `has_mobile_num`   int(11)        default 0 comment '下单有手机号的用户',
    `has_user_num`     int(11)       default 0 comment '用户数',
    `create_time`      timestamp      default current_timestamp,
    `update_time`      timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`id`),
    unique index `date_type_tag` (`ref_date`, `type`, `tag_id`) using btree
);
