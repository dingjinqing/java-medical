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

--2020-03-30 退款订单生成时保存是否自动退款的快照
ALTER TABLE `b2c_return_order` ADD COLUMN `is_auto_return` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0否；1是';
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

-- 2020-04-17 拼团抽奖表添加活动说明字段
ALTER TABLE `b2c_group_draw` ADD COLUMN `activity_copywriting` text COMMENT '活动说明';

-- 20200423订单商品表增加加价购id
ALTER TABLE `b2c_order_goods` ADD COLUMN `purchase_id` int(11) NOT NULL DEFAULT 0 COMMENT '加价购活动id';
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

-- 2020-03-30 门店表添加支持同城配送字段
ALTER TABLE `b2c_store` ADD COLUMN `city_service` tinyint(1) DEFAULT '0' COMMENT '支持同城配送 1:支持';

-- 2020-03-30 新增b2c_article表
CREATE TABLE IF NOT EXISTS `b2c_article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL DEFAULT '1' COMMENT '文章分类',
  `title` varchar(256) DEFAULT NULL,
  `author` varchar(50)  DEFAULT NULL,
  `keyword` varchar(256) DEFAULT NULL COMMENT '标签',
  `desc` varchar(1024)  DEFAULT NULL COMMENT '文章描述',
  `content` text ,
  `is_recommend` tinyint(1) DEFAULT '0' COMMENT '1:推荐',
  `is_top` tinyint(1) DEFAULT '0' COMMENT '1:置顶',
  `status` tinyint(1) DEFAULT '0' COMMENT '0未发布,1已发布',
  `pub_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_visit_time` datetime DEFAULT NULL,
  `pv` int(11) DEFAULT NULL,
  `show_footer` tinyint(1) DEFAULT '0' COMMENT '0:不在footer显示，1：显示',
  `part_type` tinyint(1) DEFAULT '0' COMMENT '文章所属类型：0普通，1门店公告类文章',
  `cover_img` varchar(50) DEFAULT NULL COMMENT '封面图片路径',
  `is_del` tinyint(1) DEFAULT '0' COMMENT '0未删除,1已删除',
  PRIMARY KEY (`article_id`),
  KEY `is_recommend` (`is_recommend`),
  KEY `is_top` (`is_top`)
);

-- 2020年3月30日 kdc 预售改为多商品 增加优先级 和预告时间
ALTER TABLE `b2c_presale` MODIFY COLUMN `goods_id` varchar(1000) NOT NULL DEFAULT 0 COMMENT '商品id 1,2,4' ;
ALTER TABLE `b2c_presale` ADD COLUMN `pre_time` int(8) NOT NULL DEFAULT 0 COMMENT '预告时间：-1：立刻预告；0：不预告；大于0：开始前预告小时数' ;
ALTER TABLE `b2c_presale` ADD COLUMN `first` int(8) NULL DEFAULT 1 COMMENT '优先级' ;

CREATE TABLE IF NOT EXISTS `b2c_live_goods` (
  `id`  int NOT NULL AUTO_INCREMENT,
  `live_id`  int NOT NULL COMMENT '直播表关联ID',
  `room_id`  int NOT NULL COMMENT '直播间ID',
  `goods_id`  int NULL DEFAULT 0,
  `cover_img`  varchar(255) NULL COMMENT '商品图',
  `url`  varchar(255) NULL COMMENT '小程序路径',
  `price`  decimal(10,2) NULL,
  `name`  varchar(255) NULL COMMENT '商品名称',
  `add_cart_num`  int NULL DEFAULT 0 COMMENT '加购数',
  `price_end`  decimal(10,2) NULL DEFAULT 0 COMMENT '另一个价格',
  `price_type`  tinyint(1) NULL DEFAULT 1 COMMENT '价格形式：1一口价 2价格区间 3显示折扣价',
  `del_flag`  tinyint(1) NULL DEFAULT 0,
  `del_time`  datetime NULL,
  PRIMARY KEY (`id`),
  INDEX `live_id` (`live_id`),
  INDEX `room_id` (`room_id`),
  INDEX `goods_id` (`goods_id`)
);

CREATE TABLE IF NOT EXISTS `b2c_live_broadcast` (
  `id`  int NOT NULL AUTO_INCREMENT ,
  `room_id`  int NOT NULL COMMENT '直播间ID' ,
  `name`  varchar(255) NOT NULL COMMENT '直播间名称' ,
  `live_status`  smallint NULL DEFAULT 0 COMMENT '直播状态 101: 直播中, 102: 未开始, 103: 已结束, 104: 禁播, 105: 暂停中, 106: 异常, 107: 已过期' ,
  `start_time`  datetime NULL COMMENT '计划开始时间' ,
  `end_time`  datetime NULL COMMENT '计划结束时间' ,
  `anchor_name`  varchar(100) NULL DEFAULT NULL COMMENT '主播名' ,
  `cover_img`  varchar(255) NULL DEFAULT NULL COMMENT '封面图片 url' ,
  `anchor_img`  varchar(255) NULL DEFAULT NULL COMMENT '直播间图片url' ,
  `add_time`  datetime NULL DEFAULT CURRENT_TIMESTAMP ,
  `update_time`  datetime NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP ,
  `del_flag`  tinyint(1) NULL DEFAULT 0,
  `del_time`  datetime NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `room_id` (`room_id`)
);
-- 2020年04月03日 商品规格表添加规格重量属性
ALTER TABLE b2c_goods MODIFY goods_weight DECIMAL(10,3) DEFAULT NULL COMMENT '商品重量，默认规格重量或自定义规格中的最小重量';
ALTER TABLE b2c_goods_spec_product add COLUMN prd_weight DECIMAL(10,3) DEFAULT NULL COMMENT '规格重量';
ALTER TABLE b2c_goods_spec_product_bak add COLUMN prd_weight DECIMAL(10,3) DEFAULT NULL COMMENT '规格重量';

-- 2020年04月06日  新增会员卡续费表
CREATE TABLE IF NOT EXISTS `b2c_card_renew` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `card_id` int(20) NOT NULL DEFAULT '0' COMMENT '续费会员卡id',
  `card_no` varchar(32) NOT NULL DEFAULT '' COMMENT '会员卡号',
  `add_time` timestamp NULL DEFAULT NULL COMMENT '续费时间',
  `renew_money` decimal(10,2) DEFAULT '0.00' COMMENT '续费金额',
  `renew_time` int(11) DEFAULT NULL COMMENT '续费时间',
  `renew_date_type` tinyint(1) DEFAULT NULL COMMENT '0:日，1:周 2: 月',
  `renew_type` tinyint(1) DEFAULT '0' COMMENT '0:现金 1：积分',
  `payment` varchar(90) NOT NULL COMMENT '支付方式',
  `pay_code` varchar(30) NOT NULL DEFAULT '' COMMENT '支付代号',
  `prepay_id` varchar(191) DEFAULT NULL COMMENT '微信支付Id，用于发送模板消息',
  `message` varchar(191) DEFAULT '' COMMENT '备注',
  `renew_order_sn` varchar(20) NOT NULL DEFAULT '' COMMENT '续费单号',
  `money_paid` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单应付金额',
  `order_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单状态 0：待支付，1：已完成',
  `member_card_no` varchar(32) DEFAULT '0' COMMENT '会员卡NO',
  `member_card_redunce` decimal(10,2) DEFAULT '0.00' COMMENT '会员卡抵扣金额',
  `use_score` decimal(10,2) DEFAULT '0.00' COMMENT '积分抵扣金额',
  `use_account` decimal(10,2) DEFAULT '0.00' COMMENT '用户消费余额',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `ali_trade_no` varchar(60) DEFAULT '' COMMENT '支付宝交易单号',
  `renew_expire_time` timestamp NULL DEFAULT NULL COMMENT '续费后过期时间',
  PRIMARY KEY (`id`)
);

-- 商品标签修改del_flag字段类型，添加is_none字段
ALTER TABLE b2c_goods_label MODIFY del_flag TINYINT not NULL default 0 COMMENT '是否删除 0否 1是';
ALTER TABLE b2c_goods_label add COLUMN is_none TINYINT(1) DEFAULT 0 COMMENT '是否不选择商品： 1：是  0： 否';

ALTER TABLE `b2c_order_info` ADD COLUMN `room_id` INT(11) NULL DEFAULT '0' COMMENT '直播间ID';
-- 商品表添加直播间id字段
ALTER TABLE b2c_goods add COLUMN room_id int(4) COMMENT '直播间id';
-- 2020年04月10日 添加自定义激活配置
ALTER TABLE `b2c_member_card` ADD COLUMN `custom_options` text COMMENT '自定义激活信息配置';


-- 2020年04月10日 添加自定义激活配置
ALTER TABLE `b2c_member_card` ADD COLUMN `custom_options` text COMMENT '自定义激活信息配置';

-- 2020-04-10 分裂优惠券分享领取记录
CREATE TABLE IF NOT EXISTS `b2c_division_receive_record` (
  `id` mediumint(8) NOT NULL AUTO_INCREMENT,
  `user` mediumint(8)  NOT NULL DEFAULT '0' COMMENT '分享的user_id',
  `user_id` mediumint(8)  NOT NULL DEFAULT '0' COMMENT '分享后领取的user_id',
  `coupon_id` mediumint(8)  NOT NULL DEFAULT '0' COMMENT '分裂优惠券id 对应优惠券表中id',
  `coupon_sn` varchar(60) NOT NULL DEFAULT '' COMMENT '分裂优惠券的sn唯一标识',
  `receive_num` int(11) NOT NULL DEFAULT '0' COMMENT '可领取个数',
  `receive_per_num` smallint(3) NOT NULL DEFAULT '0' COMMENT '分裂优惠券领券人数是否限制 0不限制 1限制',
  `source` tinyint(2) DEFAULT NULL COMMENT '送券活动来源',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '打折或减价量',
  `receive_coupon_sn` varchar(60) NOT NULL DEFAULT '' COMMENT '领取的sn唯一标识',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0主分裂优惠券 1 点击链接领取的',
  `is_share` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未分享 1分享',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
  PRIMARY KEY (`id`),
  KEY `user` (`user`)
);
-- 2020年4月13日 增加分裂优惠券是否可用
ALTER TABLE `b2c_customer_avail_coupons` ADD COLUMN `division_enabled` tinyint(1) NOT NULL  DEFAULT 0 COMMENT '是否可用,0可用 1不可用(只适用分裂优惠券)';

-- 2020年04月13日 添加自定义激活信息
ALTER TABLE `b2c_card_examine` ADD COLUMN `custom_options` text COMMENT '自定义激活信息';

-- 2020年04月13日 修改会员卡续费表字段
ALTER TABLE `b2c_card_renew` MODIFY COLUMN `id` int(20) NOT NULL AUTO_INCREMENT;

-- 2020年04月14日 添加会员卡同步标签
ALTER TABLE `b2c_member_card` ADD COLUMN `card_tag` tinyint(1) DEFAULT 0 COMMENT '是否开启给领卡用户打标签0否，1是';
ALTER TABLE `b2c_member_card` ADD COLUMN `card_tag_id` varchar(20) COMMENT '领卡打标签id';

-- 2020年04月14日 添加会员卡转赠字段
ALTER TABLE `b2c_member_card` ADD COLUMN `card_give_away` tinyint(1) DEFAULT 0 COMMENT '0:不可转赠，1:可以转赠';
ALTER TABLE `b2c_member_card` ADD COLUMN `card_give_continue` tinyint(1) DEFAULT 0 COMMENT '0:不可继续转赠，1:可以继续转赠';
ALTER TABLE `b2c_member_card` ADD COLUMN `most_give_away` int(10) DEFAULT 0 COMMENT '最多可转赠多少次 0不限制';


-- 2020年04月15日 添加限次卡转赠记录表
CREATE TABLE IF NOT EXISTS `b2c_give_card_record` (
  `id`int(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(8)  not null default 0 comment '转赠人用户ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '转赠时间',
  `card_no` varchar(32) default '' not null comment '转赠会员卡号',
  `get_user_id` int(8) not null default 0 comment '获赠人用户ID',
  `get_time` timestamp null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '领取时间',
  `get_card_no` varchar(32) default '' not null comment '获赠会员卡号',
  `flag` tinyint(1) default '0' comment '正常 1放弃 2 转赠成功',
  `deadline` timestamp NULL DEFAULT NULL comment '链接截止时间',
  PRIMARY KEY (`id`)
);

-- 2020年04月15日 在用户卡表中添加转赠字段
ALTER TABLE `b2c_user_card` ADD COLUMN `give_away_status` tinyint(1) DEFAULT 0 COMMENT '0:正常，1:转赠中，2转赠成功';
ALTER TABLE `b2c_user_card` ADD COLUMN `give_away_surplus` int(11) DEFAULT NULL COMMENT '卡剩余赠送次数';
ALTER TABLE `b2c_user_card` ADD COLUMN `card_source` tinyint(1) NOT NULL DEFAULT 0 COMMENT '卡来源 0:正常  2 别人转赠 ';

-- 2020年04月16日 修改备注
ALTER TABLE `b2c_user_card` MODIFY COLUMN `flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0:正常，1:删除 2 转赠中 3 已转赠';

-- 2020年04月16日 用户标签添加字段
ALTER TABLE `b2c_user_tag` ADD COLUMN `source` smallint(2) NOT NULL DEFAULT 0 COMMENT '标签来源 0 后台设置 1领券 2领卡';
ALTER TABLE `b2c_user_tag` ADD COLUMN `tool_id` int(11) COMMENT '优惠券或会员卡id';
ALTER TABLE `b2c_user_tag` ADD COLUMN `times` smallint(5) DEFAULT 1 COMMENT '打标签次数，会员卡或优惠券过期停用时次数减一，为0时删除';

--订单返利商品表添加 商品行ID
ALTER TABLE `b2c_order_goods_rebate` ADD COLUMN `rec_id` int(11) NOT NULL DEFAULT 0 COMMENT '商品行ID';


-- 2020年04月21日 ws
ALTER TABLE `b2c_order_goods` MODIFY COLUMN `fanli_strategy` VARCHAR ( 2999 ) DEFAULT '' COMMENT '返利配置详情';
/*********************2.11*************************END*/

/*********************2.12*************************START*/
-- 营销日历表活动
CREATE TABLE IF NOT EXISTS `b2c_market_calendar_activity` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `calendar_id` int(8) NOT NULL DEFAULT '0' COMMENT '营销日历Id',
  `sys_cal_act_id` int(8) NOT NULL DEFAULT '0' COMMENT '来源营销日历Id',
  `activity_type` varchar(16)   NOT NULL DEFAULT '0' COMMENT '具体营销活动类型',
  `activity_id` int(8) NOT NULL DEFAULT '0' COMMENT '具体营销活动Id',
  `recommend_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '推荐类型：0站内文本，1外部链接',
  `recommend_link` varchar(100)   NOT NULL DEFAULT '' COMMENT '推荐链接',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除：0否，1是',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_sync` tinyint(1) NOT NULL DEFAULT '0' COMMENT '数据是否已同步到system：目前只同步是否使用的数量',
  `recommend_title` varchar(32)   NOT NULL DEFAULT '' COMMENT '推荐标题',
  PRIMARY KEY (`id`),
  KEY `calendar_id` (`calendar_id`),
  KEY `sys_cal_act_id` (`sys_cal_act_id`)
);
-- 营销日历表
CREATE TABLE IF NOT EXISTS `b2c_market_calendar` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `event_name` varchar(64)  NOT NULL DEFAULT '' COMMENT '事件名称',
  `event_time` date DEFAULT NULL  COMMENT '事件时间',
  `event_desc` text COMMENT '事件说明',
  `pub_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发布状态：0未发布，1已发布',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已删除：0否，1是',
  `source` tinyint(1) NOT NULL DEFAULT '0' COMMENT '来源：0admin自己添加，1system推荐',
  `source_id` int(8) NOT NULL DEFAULT '0' COMMENT '来源：来源营销事件id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
);


-- 2020年04月20日 会员卡添加折扣不予营销活动公用
ALTER TABLE `b2c_member_card` ADD COLUMN `cannot_use_action` varchar(10) DEFAULT NULL COMMENT '不能与哪些营销活动共用 1会员价 2限时降价 3首单特惠';

-- 2020年04月21日 加价购给参加活动的用户打标签
ALTER TABLE `b2c_purchase_price_define` ADD COLUMN `activity_tag` tinyint(1) DEFAULT '0' COMMENT '是否给参加活动的用户打标签，1是';
ALTER TABLE `b2c_purchase_price_define` ADD COLUMN `activity_tag_id` varchar(20) DEFAULT NULL COMMENT '参加活动打标签id';

-- 2020年04月21日 限时降价给参加活动的用户打标签、预告
ALTER TABLE `b2c_reduce_price` ADD COLUMN `pre_time` int(8) DEFAULT '0' COMMENT '预告时间：-1：立刻预告；0：不预告；大于0：开始前预告小时数';
ALTER TABLE `b2c_reduce_price` ADD COLUMN `activity_tag` tinyint(1) DEFAULT '0' COMMENT '是否给参加活动的用户打标签，1是';
ALTER TABLE `b2c_reduce_price` ADD COLUMN `activity_tag_id` varchar(20) DEFAULT NULL COMMENT '参加活动打标签id';

-- 2020年04月21日 秒杀给参加活动的用户打标签、预告
ALTER TABLE `b2c_sec_kill_define` ADD COLUMN `pre_time` int(8) DEFAULT '0' COMMENT '预告时间：-1：立刻预告；0：不预告；大于0：开始前预告小时数';
ALTER TABLE `b2c_sec_kill_define` ADD COLUMN `activity_tag` tinyint(1) DEFAULT '0' COMMENT '是否给参加活动的用户打标签，1是';
ALTER TABLE `b2c_sec_kill_define` ADD COLUMN `activity_tag_id` varchar(20) DEFAULT NULL COMMENT '参加活动打标签id';

-- 2020年04月21日 砍价给参加活动的用户打标签、自定义活动规则
ALTER TABLE `b2c_bargain` ADD COLUMN `activity_copywriting` text COMMENT '自定义活动说明';
ALTER TABLE `b2c_bargain` ADD COLUMN `launch_tag` tinyint(1) DEFAULT '0' COMMENT '是否给发起砍价用户打标签';
ALTER TABLE `b2c_bargain` ADD COLUMN `launch_tag_id` varchar(20) DEFAULT NULL COMMENT '发起砍价活动用户打标签id';
ALTER TABLE `b2c_bargain` ADD COLUMN `attend_tag` tinyint(1) DEFAULT '0' COMMENT '是否参与砍价用户打标签';
ALTER TABLE `b2c_bargain` ADD COLUMN `attend_tag_id` varchar(20) DEFAULT NULL COMMENT '参与砍价活动用户打标签id';

-- 2020年04月22日-常乐-普通优惠券添加用户打标签，与部分营销活动叠加使用配置
ALTER TABLE `b2c_mrking_voucher` ADD COLUMN `coupon_tag` tinyint(1) DEFAULT '0' COMMENT '是否领取优惠券用户打标签 0:否；1：是';
ALTER TABLE `b2c_mrking_voucher` ADD COLUMN `coupon_tag_id` varchar(20) DEFAULT NULL COMMENT '领取优惠券用户打标签id';
ALTER TABLE `b2c_mrking_voucher` ADD COLUMN `coupon_overlay` tinyint(1) NOT NULL DEFAULT '0'  comment '是否与限时降价、首单特惠、会员价活动共用 0共用 1不共用 ';
-- 2020年4月23日 kdc 拼团增加用户打标签,预告
ALTER TABLE `b2c_group_buy_define`    ADD COLUMN `pre_time` int(8) NOT NULL DEFAULT 0 COMMENT '预告时间：-1：立刻预告；0：不预告；大于0：开始前预告小时数' ;
ALTER TABLE `b2c_group_buy_define`    ADD COLUMN `activity_tag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '参加活动打标签  0:否；1：是' ;
ALTER TABLE `b2c_group_buy_define`    ADD COLUMN `activity_tag_id` varchar(30) NOT NULL COMMENT '参加活动打标签id' ;
-- 2020年4月24日 kdc 拼团增加 活动说明
ALTER TABLE `b2c_group_buy_define`  ADD COLUMN `activity_copywriting` text NOT NULL COMMENT '活动说明';
-- 幸运大抽奖  限制免费,未中奖积分奖励 次数限制
ALTER TABLE `b2c_lottery`   ADD COLUMN `chance_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '次数限制 0每人 1每人每天';
ALTER TABLE `b2c_lottery`   MODIFY COLUMN `free_chances` int(8) NULL DEFAULT NULL COMMENT '免费抽奖次数 0不限制 -1不可免费抽奖 ';
ALTER TABLE `b2c_lottery`   MODIFY COLUMN `no_award_score` int(8) NULL DEFAULT NULL COMMENT '未中奖奖励积分 0不赠送积分 ';
ALTER TABLE `b2c_lottery`   MODIFY COLUMN `share_chances` int(8) NULL DEFAULT NULL COMMENT '分享最多获得次数 0 不限制次数' ;
ALTER TABLE `b2c_lottery`   MODIFY COLUMN `score_chances` int(8) NULL DEFAULT NULL COMMENT '积分最多抽奖次数 0不限制次数' ;
-- 支付有礼 注释修改  商品范围  状态
ALTER TABLE `b2c_pay_award`   MODIFY COLUMN `goods_area_type` mediumint(5) NULL DEFAULT 0 COMMENT '商品范围类型 1全部商品 0 部分商品' ;
ALTER TABLE `b2c_pay_award`   MODIFY COLUMN `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态 1启用 0停用' ;

-- 返利策略 佣金计算方式
ALTER TABLE `b2c_distribution_strategy` ADD COLUMN `strategy_type` tinyint(1) NULL DEFAULT 0 COMMENT '佣金计算方式;0:商品实际支付金额*佣金比例；1：商品实际利润（实际支付金额-成本价）* 佣金比例';
/*********************2.12*************************END*/

