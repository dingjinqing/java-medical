/*********************3.3***********************BEGIN*/
-- 店铺短信配置表
create table if not exists `b2c_shop_sms_config` (
    `id` int(11) not null auto_increment,
    `shop_id` int(11) not null comment '店铺id',
    `user_check_code_num` int(11) not null default 15 comment '每个用户可发送验证码数量',
    `patient_check_code_num` int(11) not null default 15 comment '每个患者可发送验证码数量',
    `marketing_num` int(11) not null default 2000 comment '每天可发送营销短信数量',
    `industry_num` int(11) not null default 2000 comment '每天可发送行业短信数量',
    `is_delete`     tinyint(1)   not null default '0',
    `create_time`   timestamp    not null default current_timestamp,
    `update_time`   timestamp    not null default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`id`)
)comment = '店铺短信配置表';


-- 订单商品备份
CREATE TABLE if not exists `b2c_order_goods_bak` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rec_id` mediumint(8) NOT NULL ,
  `main_rec_id` mediumint(8) NOT NULL DEFAULT '0' COMMENT '主订单rec_id',
  `shop_id` int(11) NOT NULL DEFAULT '0' COMMENT '店铺ID',
  `order_id` mediumint(8) NOT NULL DEFAULT '0',
  `order_sn` varchar(20) NOT NULL DEFAULT '',
  `goods_id` mediumint(8) NOT NULL DEFAULT '0',
  `goods_name` varchar(120) NOT NULL DEFAULT '',
  `goods_sn` varchar(60) NOT NULL DEFAULT '',
  `product_id` mediumint(8) NOT NULL DEFAULT '0',
  `product_sn` varchar(65) NOT NULL DEFAULT '',
  `goods_number` INT(11)     NOT NULL DEFAULT '1',
  `market_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `goods_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `goods_attr` text NOT NULL,
  `send_number` INT(11)      NOT NULL DEFAULT '0',
  `return_number` INT(11)      NOT NULL DEFAULT '0' COMMENT '退货/退款成功数量',
  `is_real` tinyint(1) NOT NULL DEFAULT '0',
  `goods_attr_id` varchar(191) NOT NULL DEFAULT '',
  `goods_img` varchar(191) NOT NULL DEFAULT '',
  `refund_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货',
  `comment_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:未评论，1:已评论，2：已晒单',
  `stra_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品参与的促销活动id',
  `per_discount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '促销折扣均摊到每件商品的折扣',
  `is_gift` int(1) NOT NULL DEFAULT '0' COMMENT '是否是赠品',
  `r_goods` varchar(191) NOT NULL DEFAULT '' COMMENT '赠品的关联商品',
  `goods_score` int(11) NOT NULL DEFAULT '0' COMMENT '商品积分',
  `goods_growth` int(11) NOT NULL DEFAULT '0' COMMENT '商品成长值',
  `discounted_goods_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '折后单价',
  `discount_detail` text COMMENT '打折详情，json存储',
  `fanli_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:不返利商品，1：返利商品',
  `can_calculate_money` decimal(10,2) DEFAULT '0.00' COMMENT '单品可计算返利金额，刨除优惠券和其他折扣后的金额',
  `fanli_money` decimal(10,2) DEFAULT '0.00' COMMENT '单品返利金额',
  `discounted_total_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '折后总价',
  `total_fanli_money` decimal(10,2) DEFAULT '0.00' COMMENT '商品返利总金额',
  `fanli_strategy` VARCHAR (2999) DEFAULT '' COMMENT '返利配置详情',
  `fanli_percent` decimal(10,2) DEFAULT '0.00' COMMENT '返利比例',
  `cost_price` decimal(10,2) DEFAULT NULL COMMENT '成本价',
  `is_card_exclusive` tinyint(1) DEFAULT '0' COMMENT '是否会员卡专属',
  `promote_info` varchar(500) DEFAULT NULL COMMENT '推广信息',
  `gift_id` int(11) DEFAULT '0' COMMENT '赠品ID',
  `is_can_return` tinyint(1) DEFAULT '1' COMMENT '是否可退款',
  `reduce_price_num` smallint(5) NOT NULL DEFAULT '0',
  `activity_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '营销活动种类',
  `activity_id` int(11) NOT NULL DEFAULT '0' COMMENT '营销活动id',
  `activity_rule` int(11) NOT NULL DEFAULT '0' COMMENT '营销活动id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `purchase_id` int(11) NOT NULL DEFAULT 0 COMMENT '加价购活动id',
  `prescription_old_code` varchar(64)   NOT NULL DEFAULT '' COMMENT '老处方项目明细号码（可根据此字段反查批次号）',
  `prescription_code` varchar(64)   NOT NULL DEFAULT '' COMMENT '处方项目明细号码（可根据此字段反查批次号）',
  `medical_audit_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '药品审核类型, 0不审核,1审核,2开方,3根据处方下单',
  `medical_audit_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '药品审核状态 0未审核 1审核通过 2审核不通过',
  `audit_time`timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '药品审核时间',
  PRIMARY KEY (`id`),
  KEY `rec_id` (`rec_id`),
  KEY `order_id` (`order_id`),
  KEY `order_sn` (`order_sn`),
  KEY `goods_id` (`goods_id`),
  KEY `shop_id` (`shop_id`)
)COMMENT='订单商品表  b2c_order_goods';

-- 订单详情备份
CREATE TABLE  if not exists  `b2c_order_info_bak` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` mediumint(8) NOT NULL COMMENT '订单ID',
  `shop_id` int(11) NOT NULL DEFAULT '0' COMMENT '店铺ID',
  `order_sn` varchar(20) NOT NULL DEFAULT '' COMMENT '订单编号',
  `main_order_sn` varchar(20) NOT NULL DEFAULT '' COMMENT '主订单编号(拆单时用)',
  `user_id` mediumint(8) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `user_openid` varchar(191) NOT NULL DEFAULT '' COMMENT '用户openid',
  `order_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单状态',
  `order_status_name` varchar(32) NOT NULL DEFAULT '' COMMENT '订单状态名称',
  `consignee` varchar(60) NOT NULL DEFAULT '' COMMENT '收件人姓名',
  `address_id` int(11) NOT NULL DEFAULT '0' COMMENT '地址ID',
  `country_code` mediumint(10) DEFAULT '0' COMMENT '国家编号',
  `country_name` varchar(50) NOT NULL DEFAULT '' COMMENT '国家名称',
  `province_code` mediumint(10) DEFAULT '0' COMMENT '省份编号',
  `province_name` varchar(50) NOT NULL DEFAULT '' COMMENT '省份名称',
  `city_code` mediumint(10) DEFAULT '0' COMMENT '城市编号',
  `city_name` varchar(120) NOT NULL DEFAULT '' COMMENT '城市名称',
  `district_code` mediumint(10) DEFAULT '0' COMMENT '区县编号',
  `district_name` varchar(120) NOT NULL DEFAULT '' COMMENT '区县名称',
  `address` varchar(191) NOT NULL DEFAULT '' COMMENT '更多详细地址',
  `complete_address` varchar(512) NOT NULL DEFAULT '' COMMENT '完整收件地址',
  `zipcode` varchar(60) NOT NULL DEFAULT '' COMMENT '邮编',
  `mobile` varchar(60) NOT NULL DEFAULT '' COMMENT '手机号',
  `add_message` varchar(191) DEFAULT '' COMMENT '客户留言',
  `shipping_id` tinyint(3) NOT NULL DEFAULT '0' COMMENT '快递ID',
  `shipping_name` varchar(120) NOT NULL DEFAULT '' COMMENT '快递名称',
  `pay_code` varchar(30) NOT NULL DEFAULT '' COMMENT '支付代号',
  `pay_name` varchar(120) NOT NULL DEFAULT '' COMMENT '支付名称',
  `pay_sn` varchar(32) NOT NULL DEFAULT '' COMMENT '支付流水号',
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
  `package_discount` decimal(10,2) DEFAULT '0.00' COMMENT '一口价抵扣金额',
  `dapei_id` int(8) NOT NULL DEFAULT '0' COMMENT '搭配ID来源',
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
  `shipping_no` varchar(191) NOT NULL DEFAULT '' COMMENT '快递单号',
  `shipping_type` varchar(60) NOT NULL DEFAULT '' COMMENT '快递类型',
  `is_cod` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否货到付款',
  `return_type_cfg` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否支持退换货：1支持 2不支持',
  `return_days_cfg` tinyint(1) NOT NULL DEFAULT '7' COMMENT '发货后自动确认收货时间,单位天',
  `order_timeout_days` smallint(3) NOT NULL DEFAULT '3' COMMENT '确认收货后自动订单完成时间,单位天',
  `seller_remark` varchar(512) NOT NULL DEFAULT '' COMMENT '卖家备注',
  `erpordercode` varchar(32) NOT NULL DEFAULT '' COMMENT 'ERP中订单代码',
  `comment_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:未评论，1:已评论，2：已晒单',
  `fanli_user_id` int(11) NOT NULL DEFAULT '0' COMMENT '返利会员id',
  `fanli_grade` varchar(64) NOT NULL DEFAULT '' COMMENT '返利等级',
  `fanli_percent` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '返利百分比',
  `settlement_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '结算标志：0：未结算，1：已结算',
  `invoice_id` int(11) NOT NULL DEFAULT '0' COMMENT '发票Id',
  `invoice_content` int(11) DEFAULT '0' COMMENT '发票类型：0普通发票；1增值税专票',
  `invoice_title` text COMMENT '发票内容：json存储',
  `refund_status` tinyint(1) DEFAULT '0' COMMENT '1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货',
  `pay_order_sn` varchar(30) DEFAULT '' COMMENT '对账单号',
  `goods_type` varchar(50) DEFAULT '0' COMMENT '商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品 6限时降价 7加价购',
  `order_source` tinyint(2) DEFAULT NULL COMMENT '订单来源，0pc，1wap，2app',
  `fanli_type` tinyint(2) DEFAULT NULL COMMENT '返利类型，0普通订单，1三级分销返利订单，2返利会员返利订单',
  `manual_refund` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1代表手动退款，0代表非手动',
  `order_pay_way` tinyint(2) DEFAULT '0' COMMENT '订单付款方式，0全款 1定金 2补款',
  `bk_order_sn` varchar(20) DEFAULT '' COMMENT '补款订单号 order_pay_way=1时有效',
  `bk_order_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '补款金额 order_pay_way=1时有效',
  `bk_order_paid` tinyint(1) NOT NULL DEFAULT '0' COMMENT '补款金额是否支付 order_pay_way=1时有效，0未支付，1已支付',
  `pin_goods_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '当前拼团商品金额，阶梯团根据人数时会变化，补款也随之变化',
  `pin_yj_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '拼团支付佣金金额',
  `activity_id` int(11) NOT NULL DEFAULT '0' COMMENT '营销活动id',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:未删除，1:已删除',
  `source` varchar(30) DEFAULT '' COMMENT '订单来源，记录app，wap，pc来源',
  `part_ship_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:，1:部分发货',
  `promotion_id` int(11) NOT NULL DEFAULT '0' COMMENT '促销活动Id',
  `promotion_reduce` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '促销活动折扣金额,满折满减',
  `push_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'yadu推送状态：0-暂无推送，1-推送失败，2-推送成功',
  `push_desc` varchar(100) NOT NULL DEFAULT '' COMMENT 'yadu推送失败原因',
  `pos_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '门店订单标志：0：商城，1：门店同步订单',
  `pos_shop_name` varchar(191) NOT NULL DEFAULT '' COMMENT 'pos店铺名称',
  `store_id` int(11) DEFAULT '0' COMMENT '门店ID',
  `store_name` varchar(191) DEFAULT '' COMMENT '门店名称',
  `member_card_id` int(11) DEFAULT '0' COMMENT '会员卡ID',
  `member_card_reduce` decimal(10,2) DEFAULT '0.00' COMMENT '会员卡优惠金额',
  `member_card_balance` decimal(10,2) DEFAULT '0.00' COMMENT '会员卡消费金额',
  `expire_time` timestamp NULL DEFAULT NULL COMMENT '订单支付过期时间',
  `del_time` timestamp NULL DEFAULT NULL COMMENT '订单删除时间',
  `prepay_id` varchar(191) DEFAULT NULL COMMENT '微信支付Id，用于发送模板消息',
  `deliver_type` tinyint(1) DEFAULT '0' COMMENT '配送类型：0 快递 1 自提',
  `deliver_type_name` varchar(191) DEFAULT NULL COMMENT '配送类型名称',
  `pickup_time` varchar(30) DEFAULT NULL COMMENT '自提时间',
  `star_flag` tinyint(1) DEFAULT '0' COMMENT '标星订单：0 未标星 1 标星',
  `verify_code` varchar(191) DEFAULT '' COMMENT '核销自提码',
  `split` int(11) DEFAULT '0' COMMENT '分裂优惠券id',
  `card_no` varchar(32) NOT NULL DEFAULT '' COMMENT '会员卡号',
  `fanli_money` decimal(10,2) DEFAULT '0.00' COMMENT '返利金额',
  `true_name` varchar(32) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `id_card` varchar(32) NOT NULL DEFAULT '' COMMENT '身份证号18位',
  `ali_trade_no` varchar(60) DEFAULT '' COMMENT '支付宝交易单号',
  `grouper_cheap_reduce` decimal(10,2) DEFAULT '0.00' COMMENT '团长优惠金额',
  `bk_shipping_time` timestamp NULL DEFAULT NULL COMMENT '定金预计发货时间',
  `bk_return_type` tinyint(2) DEFAULT NULL COMMENT '定金退款状态',
  `bk_prepay_id` varchar(191) DEFAULT NULL COMMENT '微信支付Id，用于发送模板消息',
  `pre_sale_discount` decimal(10,2) DEFAULT '0.00' COMMENT '定金膨胀优惠金额',
  `instead_pay_money` decimal(10,2) DEFAULT '0.00' COMMENT '代付金额',
  `order_user_message` varchar(50) DEFAULT NULL COMMENT '发起人留言',
  `instead_pay` text COMMENT '好友代付规则',
  `instead_pay_num` smallint(6) DEFAULT '0' COMMENT '代付人数',
  `is_promote` tinyint(1) DEFAULT '0' COMMENT '是否是推广单',
  `verifier_id` int(9) DEFAULT '0' COMMENT '核销员',
  `exchang` tinyint(2) DEFAULT '0' COMMENT '1 兑换 0否',
  `currency` varchar(10) NOT NULL DEFAULT 'CNY' COMMENT '币种',
  `free_ship` decimal(10,2) DEFAULT '0.00' COMMENT '运费抵扣',
  `free_detail` text COMMENT '运费抵扣规则',
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
  `order_medical_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单药品类型 0普通 1处方药',
  `order_audit_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单审核类型, 0不审核,1审核,2开方,3根据处方下单',
  `order_audit_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '订单审核状态 0未审核 1审核通过 2审核不通过 ',
  `prescription_code_list` varchar(350)  NOT NULL DEFAULT '' COMMENT '处方号外键',
  `audit_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '药品审核时间',
  `patient_id` int(11) NOT NULL DEFAULT '0' COMMENT '患者id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_sn` (`order_sn`),
  KEY `main_order_sn` (`main_order_sn`),
  KEY `user_id` (`user_id`),
  KEY `user_openid` (`user_openid`),
  KEY `order_status` (`order_status`),
  KEY `shipping_id` (`shipping_id`),
  KEY `shop_id` (`shop_id`)
)COMMENT='订单';


-- 退款订单
CREATE TABLE if not exists  `b2c_return_order_bak` (
  `ret_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL DEFAULT '0',
  `order_sn` varchar(30) NOT NULL DEFAULT '',
  `return_order_sn` varchar(30) DEFAULT '' COMMENT '退款单号',
  `shop_id` int(11) NOT NULL DEFAULT '0' COMMENT '店铺id',
  `user_id` int(11) NOT NULL DEFAULT '0',
  `goods_id` int(11) NOT NULL DEFAULT '0',
  `refund_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1是审核中，2是通过审核，3退货没通过审核，4买家提交物流 或 仅退款申请，5：退款退货成功，6是拒绝退款退货,7 撤销退款、退货',
  `money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '退款商品金额',
  `shipping_fee` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '退运费金额',
  `return_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '退款类型,0:只退款，1:退货又退款',
  `reason_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '退款/退货原因类型，0：协商一致退款，1：未按约定时间发货，2：缺货，3：拍错/多拍/不想要，4：其他',
  `reason_desc` text COMMENT '退款/退货描述',
  `shipping_type` varchar(191) NOT NULL DEFAULT '' COMMENT '快递类型',
  `shipping_no` varchar(50) NOT NULL DEFAULT '' COMMENT '快递单号',
  `goods_images` text COMMENT '商品图片',
  `voucher_images` text COMMENT '凭证图片',
  `phone` varchar(12) NOT NULL DEFAULT '' COMMENT '电话号码',
  `apply_time` timestamp NULL DEFAULT NULL COMMENT '退货且退货提交审核时间，对应refund_status=1',
  `apply_pass_time` timestamp NULL DEFAULT NULL COMMENT '审核通过时间，对应refund_status=2',
  `apply_not_pass_time` timestamp NULL DEFAULT NULL COMMENT '审核未通过时间，对应refund_status=3',
  `shipping_or_refund_time` timestamp NULL DEFAULT NULL COMMENT '只退款时为退款申请时间，退货又退款时为提交物流信息时间，对应refund_status=4',
  `refund_success_time` timestamp NULL DEFAULT NULL COMMENT '退款成功时间，对应refund_status=5',
  `refund_refuse_time` timestamp NULL DEFAULT NULL COMMENT '退款拒绝时间，对应refund_status=6',
  `refund_cancel_time` timestamp NULL DEFAULT NULL COMMENT '退款撤销时间，对应refund_status=7',
  `apply_not_pass_reason` varchar(1000) DEFAULT NULL COMMENT '审核未通过原因',
  `refund_refuse_reason` varchar(1000) DEFAULT NULL COMMENT '退款拒绝原因',
  `return_address` varchar(1000) NOT NULL DEFAULT '' COMMENT '退货地址',
  `merchant_telephone` varchar(12) NOT NULL DEFAULT '' COMMENT '商家电话',
  `consignee` varchar(32) NOT NULL DEFAULT '' COMMENT '收货人',
  `zip_code` varchar(10) NOT NULL DEFAULT '' COMMENT '邮编',
  `currency` varchar(10) NOT NULL DEFAULT 'CNY' COMMENT '币种',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `is_auto_return` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0否；1是',
  `return_source` tinyint(1) DEFAULT '1' COMMENT '售后发起来源：0商家手动发起，1用户主动申请，2订单异常系统自动发起',
  `return_source_type` tinyint(1) DEFAULT '0' COMMENT '售后发起来源类型：0改价失败自动售后，1微信支付失败，2活动自动售后',
  PRIMARY KEY (`ret_id`),
  KEY `order_sn` (`order_sn`)
)COMMENT='退回订单表';


-- 退款订单商品
CREATE TABLE if not exists  `b2c_return_order_goods_bak` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` int(11) NOT NULL DEFAULT '0' COMMENT '店铺id',
  `rec_id` int(11) DEFAULT NULL COMMENT '订单商品表的id',
  `ret_id` int(11) DEFAULT NULL COMMENT '退货记录表的id',
  `order_sn` varchar(20) NOT NULL DEFAULT '',
  `goods_id` mediumint(8) NOT NULL DEFAULT '0',
  `goods_name` varchar(120) NOT NULL DEFAULT '',
  `product_id` mediumint(8) NOT NULL DEFAULT '0',
  `goods_number` smallint(5) NOT NULL DEFAULT '1' COMMENT '退货商品数量',
  `market_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `goods_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `goods_attr` text,
  `send_number` smallint(5) NOT NULL DEFAULT '0' COMMENT '发货商品数量',
  `return_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '实际退款金额',
  `discounted_goods_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '实际退款金额',
  `goods_img` varchar(191) NOT NULL DEFAULT '',
  `success` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0代表退货申请被拒绝，1代表正在退货中，2代表退货成功',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `rec_id` (`rec_id`),
  KEY `ret_id` (`ret_id`),
  KEY `order_sn` (`order_sn`),
  KEY `goods_id` (`goods_id`),
  KEY `shop_id` (`shop_id`)
)COMMENT='退货商品表';

-- 店铺物流信息表
CREATE TABLE if not exists `b2c_shop_logistics` (
    `id` int(8) NOT NULL AUTO_INCREMENT,
    `shop_id` int(8) NOT NULL DEFAULT '0' COMMENT '店铺Id',
    `logistic_name` varchar(30) NOT NULL DEFAULT '' COMMENT '快递公司名称',
    `logistic_type` varchar(20) NOT NULL DEFAULT '' COMMENT '快递100type字段',
    `shipping_code` varchar(20) NOT NULL DEFAULT '',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:未删除，1:已删除',
    PRIMARY KEY (`id`)
) COMMENT='店铺物流信息表';

-- 门店账户表
CREATE TABLE if not exists `b2c_store_account` (
    `account_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '门店账号ID',
    `shop_id` int(11) NOT NULL DEFAULT '0' COMMENT '所属店铺id',
    `sys_id` int(10) NOT NULL DEFAULT '0' COMMENT '所属账户id',
    `mobile` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号',
    `account_name` varchar(50) DEFAULT '' COMMENT '账户名称',
    `wx_nick_name` varchar(50) default '' comment '门店账户绑定微信昵称',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `account_type` tinyint(1) DEFAULT '1' COMMENT '账户类型1:店员，2：店长',
    `status` tinyint(1) DEFAULT '0' COMMENT '账户状态0:禁用，1：启用',
    `del_flag` tinyint(1) DEFAULT '0' COMMENT '是否已删除0:否，1：是',
    `account_passwd` varchar(64)  DEFAULT NULL COMMENT '账号密码',
    `store_list` varchar(191)  DEFAULT NULL COMMENT '可用门店id,逗号隔开',
    `update_time`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`account_id`),
    KEY `mobile` (`mobile`),
    KEY `account_name` (`account_name`)
) comment '门店账户表';

/*********************3.3*************************END*/
/*********************3.4*************************BEGIN*/

-- 店铺增加 宣传图和文案
ALTER TABLE `b2c_shop` ADD COLUMN `publicity_img` varchar(512) NULL DEFAULT '' COMMENT '店铺宣传图' AFTER `store_clerk_privilege_list`;
ALTER TABLE `b2c_shop` ADD COLUMN `copywriting` text NULL COMMENT '店铺详情文案' AFTER `publicity_img`;

/*********************3.4*************************END*/
