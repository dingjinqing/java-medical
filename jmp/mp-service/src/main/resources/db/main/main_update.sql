/***********************2.8********************BEGIN*/




/***********************2.8*********************END*/

/***********************2.10********************BEGIN*/
-- 20200527 李晓冰 扩充b2c_task_job_content content字段长度
ALTER TABLE b2c_task_job_content MODIFY COLUMN content MEDIUMTEXT COMMENT '消息内容';

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


CREATE TABLE IF NOT EXISTS `b2c_order_info_new` (
  `order_id` mediumint(8) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `shop_id` int(11) NOT NULL DEFAULT '0' COMMENT '店铺id',
  `order_sn` varchar(20)   NOT NULL DEFAULT '' COMMENT '订单编号',
  `main_order_sn` varchar(20)   NOT NULL DEFAULT '' COMMENT '主订单编号(拆单时用)',
  `user_id` mediumint(8) NOT NULL DEFAULT '0' COMMENT '用户id',
  `user_openid` varchar(191)   NOT NULL DEFAULT '' COMMENT '用户openid',
  `order_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单状态',
  `order_status_name` varchar(32)   NOT NULL DEFAULT '' COMMENT '订单状态名称',
  `consignee` varchar(60)   NOT NULL DEFAULT '' COMMENT '收件人姓名',
  `address_id` int(11) NOT NULL DEFAULT '0' COMMENT '地址id',
  `country_code` mediumint(10) NOT NULL DEFAULT '0' COMMENT '国家编号',
  `country_name` varchar(50)   NOT NULL DEFAULT '' COMMENT '国家名称',
  `province_code` mediumint(10) NOT NULL DEFAULT '0' COMMENT '省份编号',
  `province_name` varchar(50)   NOT NULL DEFAULT '' COMMENT '省份名称',
  `city_code` mediumint(10) NOT NULL DEFAULT '0' COMMENT '城市编号',
  `city_name` varchar(120)   NOT NULL DEFAULT '' COMMENT '城市名称',
  `district_code` mediumint(10) NOT NULL DEFAULT '0' COMMENT '区县编号',
  `district_name` varchar(120)   NOT NULL DEFAULT '' COMMENT '区县名称',
  `address` varchar(191)   NOT NULL DEFAULT '' COMMENT '更多详细地址',
  `complete_address` varchar(512)   NOT NULL DEFAULT '' COMMENT '完整收件地址',
  `zipcode` varchar(60)   NOT NULL DEFAULT '' COMMENT '邮编',
  `mobile` varchar(60)   NOT NULL DEFAULT '' COMMENT '手机号',
  `add_message` varchar(191)   NOT NULL DEFAULT '' COMMENT '客户留言',
  `shipping_id` tinyint(3) NOT NULL DEFAULT '0' COMMENT '快递id',
  `shipping_name` varchar(120)   NOT NULL DEFAULT '' COMMENT '快递名称',
  `pay_code` varchar(30)   NOT NULL DEFAULT '' COMMENT '支付代号',
  `pay_name` varchar(120)   NOT NULL DEFAULT '' COMMENT '支付名称',
  `pay_sn` varchar(32)   NOT NULL DEFAULT '' COMMENT '支付流水号',
  `goods_amount` smallint(6) NOT NULL DEFAULT '0' COMMENT '订单商品数量',
  `shipping_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '快递费金额',
  `money_paid` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单应付金额',
  `shoper_reduce_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商家减价金额',
  `sub_order_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '子订单总金额',
  `discount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '券折扣金额',
  `score_discount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '积分抵扣金额',
  `use_account` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '用户消费余额',
  `order_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额',
  `grade_percent` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '购买会员等级的折扣%',
  `discount_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '购买会员等级的折扣金额',
  `dapei_reduce_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '搭配减价',
  `package_discount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '一口价抵扣金额',
  `dapei_id` int(8) NOT NULL DEFAULT '0' COMMENT '搭配id来源',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `confirm_time` timestamp NULL DEFAULT NULL COMMENT '订单确收收货时间',
  `pay_time` timestamp NULL DEFAULT NULL COMMENT '支付时间',
  `shipping_time` timestamp NULL DEFAULT NULL COMMENT '发货时间',
  `closed_time` timestamp NULL DEFAULT NULL COMMENT '关闭时间',
  `cancelled_time` timestamp NULL DEFAULT NULL COMMENT '取消时间',
  `finished_time` timestamp NULL DEFAULT NULL COMMENT '订单完成时间',
  `return_time` timestamp NULL DEFAULT NULL COMMENT '订单申请退货时间',
  `return_finish_time` timestamp NULL DEFAULT NULL COMMENT '订单退货完成时间',
  `refund_time` timestamp NULL DEFAULT NULL COMMENT '订单申请退款时间',
  `refund_finish_time` timestamp NULL DEFAULT NULL COMMENT '订单退款完成时间',
  `shipping_no` varchar(191)   NOT NULL DEFAULT '' COMMENT '快递单号',
  `shipping_type` varchar(60)   NOT NULL DEFAULT '' COMMENT '快递类型',
  `is_cod` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否货到付款',
  `return_type_cfg` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否支持退换货：1支持 2不支持',
  `return_days_cfg` tinyint(1) NOT NULL DEFAULT '7' COMMENT '发货后自动确认收货时间,单位天',
  `order_timeout_days` smallint(3) NOT NULL DEFAULT '3' COMMENT '确认收货后自动订单完成时间,单位天',
  `seller_remark` varchar(512)   NOT NULL DEFAULT '' COMMENT '卖家备注',
  `erpordercode` varchar(32)   NOT NULL DEFAULT '' COMMENT 'erp中订单代码',
  `comment_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:未评论，1:已评论，2：已晒单',
  `fanli_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '返利会员id',
  `fanli_grade` varchar(64)   NOT NULL DEFAULT '' COMMENT '返利等级',
  `fanli_percent` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '返利百分比',
  `settlement_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '结算标志：0：未结算，1：已结算',
  `invoice_id` int(11) NOT NULL DEFAULT '0' COMMENT '发票id',
  `invoice_content` int(11) NOT NULL DEFAULT '0' COMMENT '发票类型：0普通发票；1增值税专票',
  `invoice_title` text   COMMENT '发票内容：json存储',
  `refund_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货',
  `pay_order_sn` varchar(30)   NOT NULL DEFAULT '' COMMENT '对账单号',
  `goods_type` varchar(50)   NOT NULL DEFAULT '0' COMMENT '商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品 6限时降价 7加价购',
  `order_source` tinyint(2) NOT NULL DEFAULT '0' COMMENT '订单来源，0：小程序，1wap，2app',
  `fanli_type` tinyint(2) NOT NULL DEFAULT '0' COMMENT '返利类型，0：普通订单，1：分销返利订单，2：返利会员返利订单',
  `manual_refund` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1代表手动退款，0代表非手动',
  `order_pay_way` tinyint(2) NOT NULL DEFAULT '0' COMMENT '订单付款方式，0全款 1定金 2好友代付',
  `bk_order_sn` varchar(32)   NOT NULL DEFAULT '' COMMENT '补款订单号 order_pay_way=1时有效',
  `bk_order_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '补款金额 order_pay_way=1时有效',
  `bk_order_paid` tinyint(1) NOT NULL DEFAULT '0' COMMENT '补款金额是否支付 order_pay_way=1时有效，0未支付，1已支付',
  `pin_goods_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '当前拼团商品金额，阶梯团根据人数时会变化，补款也随之变化',
  `pin_yj_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '拼团支付佣金金额',
  `activity_id` int(11) NOT NULL DEFAULT '0' COMMENT '营销活动id',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:未删除，1:已删除',
  `source` varchar(30)   NOT NULL DEFAULT '' COMMENT '订单来源，记录app，wap，pc来源',
  `part_ship_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:，1:部分发货',
  `promotion_id` int(11) NOT NULL DEFAULT '0' COMMENT '促销活动id',
  `promotion_reduce` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '促销活动折扣金额,满折满减',
  `push_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'yadu推送状态：0-暂无推送，1-推送失败，2-推送成功',
  `push_desc` varchar(100)   NOT NULL DEFAULT '' COMMENT 'yadu推送失败原因',
  `pos_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '门店订单标志：0：商城，1：门店同步订单',
  `pos_shop_name` varchar(191)   NOT NULL DEFAULT '' COMMENT 'pos店铺名称',
  `store_id` int(11) DEFAULT '0' COMMENT '门店id',
  `store_name` varchar(191)   NOT NULL DEFAULT '' COMMENT '门店名称',
  `member_card_id` int(11) NOT NULL DEFAULT '0' COMMENT '会员卡id',
  `member_card_reduce` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '会员卡优惠金额',
  `member_card_balance` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '会员卡消费金额',
  `expire_time` timestamp NULL DEFAULT NULL COMMENT '订单支付过期时间',
  `del_time` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  `prepay_id` varchar(191)   DEFAULT NULL COMMENT '微信支付id，用于发送模板消息',
  `deliver_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '配送类型：0 快递 1 自提',
  `deliver_type_name` varchar(191)   DEFAULT NULL COMMENT '配送类型名称',
  `pickupdate_time` varchar(30)   DEFAULT NULL COMMENT '自提时间',
  `star_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标星订单：0 未标星 1 标星',
  `verify_code` varchar(191)   NOT NULL DEFAULT '' COMMENT '核销自提码',
  `split` int(11) NOT NULL DEFAULT '0' COMMENT '分裂优惠券id',
  `card_no` varchar(32)   NOT NULL DEFAULT '' COMMENT '会员卡号',
  `fanli_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '单品返利金额',
  `true_name` varchar(32)   NOT NULL DEFAULT '' COMMENT '真实姓名',
  `id_card` varchar(32)   NOT NULL DEFAULT '' COMMENT '身份证号',
  `ali_trade_no` varchar(60)   NOT NULL DEFAULT '' COMMENT '支付宝交易单号',
  `grouper_cheap_reduce` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '团长优惠金额',
  `bk_shipping_time` timestamp NULL DEFAULT NULL COMMENT '定金预计发货时间',
  `bk_return_type` tinyint(2) DEFAULT NULL COMMENT '定金退款状态',
  `bk_prepay_id` varchar(191)   DEFAULT NULL COMMENT '微信支付id，用于发送模板消息',
  `pre_sale_discount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '定金膨胀优惠金额',
  `instead_pay_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '代付金额',
  `order_user_message` varchar(50)   DEFAULT NULL COMMENT '发起人留言',
  `instead_pay` text   COMMENT '好友代付规则',
  `instead_pay_num` smallint(6) NOT NULL DEFAULT '0' COMMENT '代付人数',
  `is_promote` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否是推广单',
  `verifier_id` int(11) NOT NULL DEFAULT '0' COMMENT '核销员id',
  `exchang` tinyint(2) NOT NULL DEFAULT '0' COMMENT '1 兑换 0否',
  `currency` varchar(10)   NOT NULL DEFAULT 'CNY' COMMENT '币种',
  `free_ship` decimal(10,2) DEFAULT '0.00' COMMENT '运费抵扣',
  `free_detail` text   COMMENT '运费抵扣规则',
  `sub_goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '子单金额',
  `is_refund_coupon` tinyint(1) DEFAULT '0' COMMENT '是否退优惠券',
  `is_finish_refund` tinyint(1) DEFAULT '0' COMMENT '子订单是否已处理退款',
  `is_view_comment` tinyint(1) DEFAULT '1' COMMENT '是否已查看评价',
  `pos_order_action` tinyint(1) DEFAULT '1' COMMENT '1:扫码购 2：仅自提',
  `order_remind` tinyint(1) DEFAULT '0' COMMENT '发货提醒次数',
  `order_remind_time` timestamp NULL DEFAULT NULL COMMENT '发货提醒时间',
  `extend_receive_action` tinyint(1) DEFAULT '0' COMMENT '延长收货操作人：1:商家 2:用户',
  `extend_receive_time` timestamp NULL DEFAULT NULL COMMENT '收货延长时间',
  `tk_order_type` tinyint(2) DEFAULT '0' COMMENT '淘客订单类型：0：普通订单，1：京东订单，2：淘宝订单',
  `pay_award_id` int(9) DEFAULT NULL COMMENT '支付有礼id',
  `is_lock` tinyint(1) DEFAULT '0' COMMENT '是否锁库存，0否，1是',
  `score_proportion` int(9) DEFAULT '100' COMMENT '积分比例',
  `is_freeship_card` tinyint(1) DEFAULT '0' COMMENT '0否，1是',
  `room_id` int(11) DEFAULT '0' COMMENT '直播间ID',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_sn` (`order_sn`),
  KEY `boin_main_order_sn` (`main_order_sn`),
  KEY `boin_user_id` (`user_id`),
  KEY `boin_user_openid` (`user_openid`),
  KEY `boin_order_status` (`order_status`),
  KEY `boin_shipping_id` (`shipping_id`),
  KEY `boin_shop_id` (`shop_id`)
);


/*********************2.12*************************END*/
/*********************3.1*************************BEGIN*/
ALTER TABLE `b2c_mp_auth_shop` CHANGE COLUMN `link_official_app_id` `link_official_app_id` VARCHAR(191) DEFAULT NULL COMMENT '关联公众号appId，用于发送模板消息' ;
/*********************3.1*************************END*/

/*********************3.2***********************BEGIN*/
-- 2020.7.21 用户表新增字段判断当前用户角色
ALTER TABLE `b2c_user` ADD `user_type`TINYINT NOT NULL DEFAULT 0 COMMENT '用户角色 0、患者 1、医师 2、药师';
/*********************3.2*************************END*/

