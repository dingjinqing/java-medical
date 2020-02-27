/***********************2.7********************BEGIN*/
--  修改用户持有优惠券表结构
ALTER TABLE `b2c_customer_avail_coupons` MODIFY `limit_order_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '满多少可用';


-- 更正错误备注
ALTER TABLE `b2c_member_card` MODIFY COLUMN `store_use_switch` tinyint(1) NOT NULL DEFAULT 0 COMMENT '可否在门店使用  0不可以 1可以';
/***********************2.7*********************END*/


/***********************2.8********************BEGIN*/

-- 2020年1月10日 16:06:24 购物车表增加商品规格
ALTER TABLE `b2c_cart` CHANGE COLUMN `goods_specs` `prd_desc` text NULL COMMENT '例如,颜色:黑色';
ALTER TABLE `b2c_cart` MODIFY COLUMN `prd_desc` varchar(1024) NOT NULL DEFAULT '' COMMENT '规格描述，格式例子：颜色:红色 尺码:s';

--订单必填增加默认
ALTER TABLE `b2c_order_must` MODIFY COLUMN `must_content` varchar(100) NOT NULL DEFAULT '' COMMENT '必填信息';
-- 2020年2月6日 秒杀表添加初始销量字段
ALTER TABLE `b2c_sec_kill_define` ADD COLUMN `base_sale` int(8) DEFAULT '0' COMMENT '初始销量';
--20200207 申请分销员审核字段类型优化
ALTER TABLE `b2c_distributor_apply` MODIFY COLUMN `activation_fields` text DEFAULT NULL COMMENT '审核校验';

-- 2020年2月18日14:50:58 孔德成 抽奖奖品增加账户余额
ALTER TABLE `b2c_lottery_prize` ADD COLUMN `award_account` decimal(10,2) DEFAULT '0.00' COMMENT '用户余额';


-- 2020年2月20日17:11:57  字段默认存储从{}改为[]
ALTER TABLE `b2c_member_card` MODIFY COLUMN `store_list` varchar(191) NOT NULL DEFAULT '[]' COMMENT '可用门店';

-- 2020年2月20日 liufei  修改字段注释，新增字段
ALTER TABLE `b2c_user_summary_trend` MODIFY COLUMN `order_user_data` int(11) NOT NULL COMMENT '成交客户数（付款用户数:distinct(userId)）';
ALTER TABLE `b2c_user_summary_trend` MODIFY COLUMN `order_user_num` int(11) DEFAULT '0' COMMENT '下单用户数(distinct(userId)生成订单就算)';
ALTER TABLE `b2c_user_summary_trend` ADD COLUMN `pay_people_num` int(11) DEFAULT '0' COMMENT '付款人数';
ALTER TABLE `b2c_user_summary_trend` ADD COLUMN `order_people_num` int(11) DEFAULT '0' COMMENT '下单人数';


-- 修复table


/***********************2.8*********************END*/

/***********************2.9********************BEGIN*/

-- 2020年2月11日 砍价杀表添加初始销量、绑定手机号字段
ALTER TABLE `b2c_bargain` ADD COLUMN `need_bind_mobile` tinyint(1) DEFAULT '0' COMMENT '是否需要绑定手机号，1是';
ALTER TABLE `b2c_bargain` ADD COLUMN `initial_sales` int(9) DEFAULT '0' COMMENT '初始销量';

-- 2020年2月11日 pictorial 分享图片缓存表添加活动id字段
ALTER TABLE `b2c_pictorial` ADD COLUMN `activity_id` int(10) DEFAULT NULL COMMENT '活动id';
-- 2020年2月10日15:46:57  拼团表增加字段
ALTER TABLE `b2c_group_buy_define` ADD  COLUMN `level` int(11) NOT NULL DEFAULT 0 COMMENT '优先级' ;
ALTER TABLE `b2c_group_buy_define` ADD COLUMN `begin_num` int(11) NOT NULL DEFAULT 0 COMMENT '初始成团数' ;
-- 2020-02-11 新加服务承诺关联表和服务承诺表新加类型和优先级字段
CREATE TABLE IF NOT EXISTS  `b2c_pledge_related` (
  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pledge_id` int(9) NOT NULL DEFAULT '0' COMMENT '承诺id',
  `type` tinyint(1) DEFAULT NULL COMMENT '指定商品范围:1 商品id,2 商家分类id,3 商品品牌id',
  `related_id` int(9) NOT NULL DEFAULT '0' COMMENT '相关的id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  key `bpr_pledge_id`(`pledge_id`)
)COMMENT='服务承诺关联表';
ALTER TABLE `b2c_pledge` ADD COLUMN `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '商品范围:1全部商品,2指定商品' ;
ALTER TABLE `b2c_pledge` ADD COLUMN `level` int(6) NOT NULL DEFAULT 0 COMMENT '商品优先级' ;
INSERT IGNORE INTO `b2c_message_template_config` (`id`, `open_ma`, `open_mp`) VALUES (2013, 1, 1);
INSERT IGNORE INTO `b2c_message_template_config` (`id`, `open_ma`, `open_mp`) VALUES (2014, 1, 1);
INSERT IGNORE INTO `b2c_message_template_config` (`id`, `open_ma`, `open_mp`) VALUES (2015, 1, 1);
CREATE TABLE IF NOT EXISTS  `b2c_user_remark` (
  `id`          mediumint(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id`     mediumint(8) unsigned NOT NULL DEFAULT '0',
  `remark`      TEXT COMMENT '会员备注',
  `add_time`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_delete`   tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT'0:未删除；1删除',
  PRIMARY KEY (`id`),
  key `user_id` (`user_id`)
)COMMENT='会员备注';
--
ALTER TABLE `b2c_user_remark` MODIFY COLUMN `id` mediumint(10)  NOT NULL AUTO_INCREMENT;
ALTER TABLE `b2c_user_remark` MODIFY COLUMN `user_id` mediumint(8)  NOT NULL DEFAULT '0';
ALTER TABLE `b2c_user_remark` MODIFY COLUMN `is_delete` tinyint(1)  NOT NULL DEFAULT '0' COMMENT'0:未删除；1删除';

--2020-02-20 常乐 分销分组表添加 用户是否可选择
ALTER TABLE `b2c_distributor_group` ADD  COLUMN `can_select` tinyint(1) DEFAULT 1 NULL   COMMENT '支持用户选择 1：支持；0：不支持';

--2020-02-20 一口价 支持打包一口价折扣和减金额两种方式
ALTER TABLE `b2c_package_sale` ADD  COLUMN `package_type` tinyint(1) DEFAULT '0' COMMENT '活动类型0金额1折扣';
ALTER TABLE `b2c_package_sale` ADD  COLUMN `total_ratio` decimal(4,2) DEFAULT '0.00' COMMENT '结算比例';

/***********************2.9*********************END*/

/***********************2.10*********************BEGIN*/
-- 2020年2月26日10:46:57  拼团活动商品信息记录表添加商品id字段
ALTER TABLE `b2c_group_buy_product_define` ADD COLUMN `goods_id` int(8) NOT NULL DEFAULT 0 COMMENT '商品id';
-- 2020年2月26日10:46:57  秒杀活动商品信息记录表添加商品id字段
ALTER TABLE `b2c_sec_kill_product_define` ADD COLUMN `goods_id` int(8) NOT NULL DEFAULT 0 COMMENT '商品id';
-- 2020年2月26日10:46:57  秒杀活动表添加优先级字段
ALTER TABLE `b2c_sec_kill_define` ADD COLUMN `first` tinyint(3) NOT NULL DEFAULT 0 COMMENT '优先级';
-- 2020年2月26日10:46:57  秒杀活动表修改goods_id类型为字符串
ALTER TABLE `b2c_sec_kill_define` DROP  INDEX IF exists `goods_id`;
ALTER TABLE `b2c_sec_kill_define` MODIFY COLUMN `goods_id` text  COMMENT '商品ID';
-- 2020年2月26日20:13:50 拼团活动表goods_id 字段有int转换为string
ALTER TABLE `b2c_group_buy_define` MODIFY COLUMN `goods_id` text NOT NULL COMMENT '商品id';
-- 2020年2月27日16:35:50 好友助力新增单天助力限制字段
ALTER TABLE `b2c_friend_promote_activity` ADD COLUMN `promote_times_per_day` int(8) null default 0 comment '单个用户每天最多可帮忙助力次数';
/***********************2.10*********************END*/
