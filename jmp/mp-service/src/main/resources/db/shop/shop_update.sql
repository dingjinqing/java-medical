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

--2020-03-11 订单增加库存记录
ALTER TABLE `b2c_order_info` ADD  COLUMN `is_lock` tinyint(1) DEFAULT '0' COMMENT '是否锁库存，0否，1是';
ALTER TABLE `b2c_order_info` ADD  COLUMN `score_proportion` int(9) DEFAULT '100' COMMENT '积分比例';

--2020-03-30 用户优惠券使用时间允许为null
ALTER TABLE `b2c_customer_avail_coupons` MODIFY COLUMN `used_time` timestamp NULL DEFAULT '0000-00-00 00:00:00';

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

-- 2020年2月28日 加价购活动添加换购商品运费策略字段
ALTER TABLE `b2c_purchase_price_define` ADD COLUMN `redemption_freight` tinyint(1)  NOT NULL DEFAULT '0' COMMENT '换购商品运费策略，0免运费，1使用原商品运费模板';

-- 2020年03月03日 添加包邮,自定义权益字段
ALTER TABLE `b2c_member_card` ADD COLUMN `custom_rights` text COMMENT '自定义权益';
ALTER TABLE `b2c_member_card` ADD COLUMN `freeship_limit` tinyint(3) DEFAULT -1 COMMENT '-1：不包邮，0:不限制，1：持卡有效期内，2：年，3：季，4：月，5：周，6：日';
ALTER TABLE `b2c_member_card` ADD COLUMN `freeship_num` int(11) DEFAULT 0 COMMENT '周期内包邮次数';

-- 2020年03月04日优惠券礼包活动修改用户优惠券表
ALTER TABLE `b2c_customer_avail_coupons` ADD COLUMN `access_order_sn` varchar(20) NOT NULL DEFAULT '' COMMENT '发放活动的订单的订单编号';
ALTER TABLE `b2c_customer_avail_coupons` MODIFY COLUMN `access_mode` tinyint(1) NOT NULL DEFAULT '0' COMMENT '获取方式，0：发放，1：领取，2：优惠券礼包自动发放';

-- 2020年03月05日 积分兑换记录表增加删除标识
ALTER TABLE `b2c_integral_mall_record` ADD COLUMN `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '1删除';

-- 2020年03月11日 会员卡表添加续费相关字段
ALTER TABLE `b2c_member_card` ADD COLUMN `renew_member_card` tinyint(1) DEFAULT 0 COMMENT '0:不可续费，1:可续费';
ALTER TABLE `b2c_member_card` ADD COLUMN `renew_type` tinyint(1) DEFAULT 0 COMMENT '0:现金 1：积分';
ALTER TABLE `b2c_member_card` ADD COLUMN `renew_num` decimal(10, 2) DEFAULT 0.00 COMMENT '现金或积分数量';
ALTER TABLE `b2c_member_card` ADD COLUMN `renew_time` int(11) DEFAULT NULL COMMENT '续费时间' ;
ALTER TABLE `b2c_member_card` ADD COLUMN `renew_date_type` tinyint(1) DEFAULT NULL COMMENT '0:日，1:周 2: 月';



-- 商品表和规格表设置市场价字段可以为null
ALTER TABLE b2c_goods MODIFY market_price DECIMAL(10,2) COMMENT '市场价格，多规格时取最高';
ALTER TABLE b2c_goods_spec_product MODIFY prd_market_price DECIMAL(10,2) COMMENT '市场价格';
ALTER TABLE b2c_goods_spec_product_bak MODIFY prd_market_price DECIMAL(10,2) COMMENT '市场价格';
-- 恢复之前被他人误删的商品主键
ALTER TABLE `b2c_goods` ADD PRIMARY KEY ( `goods_id` );
-- 2020年03月12日 会员卡表添加不可与优惠券共用字段
ALTER TABLE `b2c_member_card` ADD COLUMN `cannot_use_coupon` tinyint(1) DEFAULT 0 COMMENT '是否和会员卡一起使用0:可以1：不可以' ;
-- 2020年03月12日 会员卡表添加自定义权益开关字段
ALTER TABLE `b2c_member_card` ADD COLUMN `custom_rights_flag` tinyint(1) DEFAULT 0 COMMENT '自定义权益开关';
--2020-03-17 订单增加会员卡包邮标识
ALTER TABLE `b2c_order_info` ADD  COLUMN `is_freeship_card` tinyint(1) DEFAULT '0' COMMENT '0否，1是';

-- 瓜分积分 添加活动规则说明
ALTER TABLE `b2c_group_integration_define` ADD COLUMN `activity_copywriting` TEXT COMMENT '活动规则说明';

-- 2020-03-17 用户卡表添加包邮快照信息字段
ALTER TABLE `b2c_user_card` ADD COLUMN `free_limit` tinyint(3) DEFAULT -1 COMMENT '-1：不包邮，0:不限制，1：持卡有效期内，2：年，3：季，4：月，5：周，6：日';
ALTER TABLE `b2c_user_card` ADD COLUMN `free_num` int(11) DEFAULT 0 COMMENT '周期内包邮次数';

-- 2020-03-19 商品表添加统计字段
ALTER TABLE `b2c_goods` ADD COLUMN `pv` int(11) DEFAULT '0' COMMENT '7天访问量';
ALTER TABLE `b2c_goods` ADD COLUMN `comment_num` int(11) DEFAULT '0' COMMENT '评论数';

-- 2020-03-20 商品导入信息结果详情表修改字段
-- ALTER TABLE b2c_goods_import_detail CHANGE error_msg error_code TINYINT(3) NOT NULL DEFAULT 0 COMMENT '导入数据错误码，0表示正确 非0对应错误码';

-- 2020-03-24 评价表添加置顶字段
ALTER TABLE `b2c_comment_goods` ADD COLUMN `is_top` TINYINT ( 2 ) DEFAULT '0' COMMENT '是否置顶';
ALTER TABLE `b2c_comment_goods` ADD COLUMN `top_time` TIMESTAMP NULL DEFAULT NULL COMMENT '置顶时间';

--2020年3月26日 打包一口价 商品数可以为空
ALTER TABLE `b2c_package_sale` MODIFY COLUMN `goods_number_1` mediumint(11) NULL DEFAULT 0 COMMENT '分组商品数' ;
ALTER TABLE `b2c_package_sale` MODIFY COLUMN `goods_number_2` mediumint(11) NULL DEFAULT 0 COMMENT '分组商品数' ;
ALTER TABLE `b2c_package_sale` MODIFY COLUMN `goods_number_3` mediumint(11) NULL DEFAULT 0 COMMENT '分组商品数' ;

/***********************2.10*********************END*/

/***********************2.11*********************BEGIN*/
-- 2020-03-26 砍价支持选择多商品
ALTER TABLE `b2c_bargain` MODIFY COLUMN `goods_id` varchar(9999)  COMMENT '商品ID';
ALTER TABLE `b2c_bargain` ADD COLUMN `first` int(9) NOT NULL DEFAULT 0 COMMENT '优先级';
CREATE TABLE IF NOT EXISTS `b2c_bargain_goods` (
  `id` int(9) NOT NULL AUTO_INCREMENT,
  `bargain_id` int(9) DEFAULT '0' COMMENT '砍价活动主键',
  `goods_id` int(9) DEFAULT '0',
  `expectation_price` decimal(10,2) DEFAULT '0.00' COMMENT '指定金额结算模式的砍价底价 或 砍到任意金额结算模式的结算金额上限',
  `floor_price` decimal(10,2) DEFAULT '0.00' COMMENT '任意金额结算模式的结算金额底价',
  `stock` int(9) DEFAULT '0' COMMENT '活动库存',
  `sale_num` int(9) DEFAULT '0' COMMENT '销量',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)COMMENT='砍价活动商品表';
/***********************2.11*********************END*/








