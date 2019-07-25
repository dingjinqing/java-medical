set foreign_key_checks = false;

-- -- 用户
-- drop table if exists `b2c_user`;
create table `b2c_user` (
  `user_id`             int(8)			not null  auto_increment,
  `shop_id`             int(8)               	not null default 0,
  `username`            varchar(100)			not null 				comment '用户名',
  `user_pwd`            varchar(60)          	not null default '' 		comment '密码',
  `user_cid`            varchar(64)          	not null default '',
  `mobile`              varchar(100)            default null 	comment '电话',
  `user_code`           varchar(100)            default null 	comment '会员卡号',
  `wx_openid`           varchar(128)         	not null default '',
  `email`               varchar(100)            default null 	comment '邮箱',
  `create_time`         timestamp            	default current_timestamp comment '创建时间',
  `update_time`         timestamp            	default current_timestamp on update current_timestamp comment '最后修改时间',
  `wechat`              varchar(100)         	not null default '' 		comment '微信',
  `fanli_grade`         int(11)              	not null default '0' 	comment '返利会员级别',
  `user_grade`          int(11)              	not null default '1' 	comment '会员级别',
  `invite`              int(10) 				not null default 0,
  `invite_source`       varchar(32)          	default null 	comment '邀请来源:groupbuy.拼团,bargain.砍价,integral.积分,seckill.秒杀,lottery.抽奖',
  `invitation_code`     int(8)               	not null default 0 		comment '邀请码',
  `account`             decimal(10, 2)       	not null default '0.00' 	comment '用户余额',
  `discount`            int(11)              	not null default '0' 	comment '折扣',
  `discount_grade`      int(11)              	not null default '0' 	comment '会员折扣等级',
  `del_flag`            tinyint(1)   		 	not null default '0',
  `del_time`         timestamp            	null default null 	comment '删除时间',
  `growth`              int(11)              	not null default '0' 	comment '成长值',
  `score`               int(11)              	not null default '0' 	comment '积分',
  `source`              int(11) 			 	not null default -1   	comment '门店来源-1未录入0后台>0为门店',
  `invite_id`           int(11) 			 	not null default '0'  	comment '邀请人id',
  `invite_expiry_date`  date            		default null 	comment '邀请失效时间',
  `wx_union_id`         varchar(191)          	not null default '' 		comment '小程序unionid',
  `is_distributor`      tinyint(2) 				not null default 0  		comment '是否是分销员',
  `invite_act_id`       int(10) 				not null default 0     	comment '邀请来源活动id',
  `distributor_level`   tinyint(2)              not null default '1' 	comment '用户等级',
  `ali_user_id`         varchar(191)         	not null default '' 		comment '支付宝用户id',
  `device`              varchar(50)          	default null 	comment '登录设备',
  `invite_protect_date` date                 	default null 	comment '邀请保护时间',
  `look_collect_time`   timestamp             null  default null 	comment '最近看见收藏有礼图标时间',
  `get_collect_gift`    tinyint(1)   			not null default 0 		comment '是否获得收藏好礼：0未获得，1已获得',
  `invite_group` 		int(6) 					not null default 0   	comment '分销员分组',
  `unit_price`  		decimal(10, 2)       	not null default '0.00' 	comment '客单价',
  primary key (`user_id`),
  unique key `mobile` (`mobile`, `shop_id`),
  key (`user_code`),
  key (`wx_union_id`)
);

-- -- 用户地址
-- drop table if exists `b2c_user_address`;
create table `b2c_user_address` (
  `address_id`       mediumint(8)  not null auto_increment,
  `address_name`     varchar(50)			not null default '',
  `user_id`          mediumint(8)			not null default '0',
  `user_cid`         varchar(40)           	not null default '',
  `wx_openid`        varchar(128)          	not null default '',
  `consignee`        varchar(60)           	not null default '',
  `email`            varchar(60)           	not null default '',
  `country_code`     mediumint(10)         	not null default '0',
  `province_name`    varchar(191)          	not null default '',
  `province_code`    mediumint(10)         	not null default '0',
  `city_code`        mediumint(10)         	not null default '0',
  `city_name`        varchar(191)           not null default '',
  `district_code`    mediumint(10)        	not null default '0',
  `district_name`    varchar(191)           not null default '',
  `address`          varchar(120)          	not null default '',
  `complete_address` varchar(191)          	not null default '',
  `zipcode`          varchar(60)           	not null default '',
  `tel`              varchar(60)           	not null default '',
  `mobile`           varchar(60)           	not null default '',
  `best_time`        varchar(120)          	not null default '',
  `is_default`       tinyint(1)    			not null default '0',
  `last_used_time`   timestamp	 		    null 	default null,
  `create_time`      timestamp            	default current_timestamp,
  `update_time`      timestamp            	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`address_id`),
  key `user_id` (`user_id`)
);

-- -- 用户积分表
-- drop table if exists `b2c_user_score`;
create table `b2c_user_score` (
  `id`           int(11)               	not null auto_increment,
  `user_id`      int(11)               	not null,
  `score`        int(11)               	not null,
  `status`       tinyint(1)				not null default '0' 	comment '0:未使用 1:已使用 2：已过期 3：已退款',
  `flow_no`      varchar(20)           	null comment '积分流水号',
  `usable_score` int(11)				not null default 0 comment '可用积分',
  `identity_id`  varchar(500)          	comment '关联其他属性：例如order_sn',
  `goods_id`     int(11)               	not null default '0',
  `order_sn`     varchar(20)           	not null default '',
  `shop_id`      int(11)               	not null default '0' comment '店铺id',
  `desc`         varchar(191)          	not null default '',
  `remark`       varchar(1024) 						comment '备注',
  `create_time`  timestamp            	default current_timestamp,
  `expire_time`  timestamp null default null,
  `admin_user`   varchar(191)         	not null default '0' comment '操作员',
  primary key (`id`),
  key (`shop_id`)
);

-- -- 用户详情表
-- drop table if exists `b2c_user_detail`;
create table `b2c_user_detail` (
  `id`              int(11)     	not null auto_increment,
  `user_id`         int(8)      	not null,
  `shop_id`         int(11)     		not null default 0 comment '店铺id',
  `username`        varchar(100) 		default null comment '昵称',
  `sex`             char(5)       		default null comment '性别：女f,男m',
  `birthday_year`   int(4)       		default null comment '生日年份',
  `birthday_month`  int(2)        		default null,
  `birthday_day`    int(2)          	default null,
  `email`           varchar(100)   		default null comment '邮箱',
  `real_name`       varchar(50) 		default null comment '真实姓名',
  `province_code`   mediumint(10)       default null comment '所在地省编号',
  `city_code`       mediumint(10)       default null comment '所在地市编号',
  `district_code`   mediumint(10)       default null comment '所在地区编号',
  `address`         varchar(120)        default null comment '所在地',
  `marital_status`  tinyint(1)          default null comment '婚姻状况：1未婚，2已婚，3保密',
  `monthly_income`  tinyint(1)          default null comment '月收入',
  `cid`             varchar(18)         default null comment '身份证号码',
  `education`       tinyint(1)          default null comment '教育程度',
  `industry_info`   tinyint(1)          default null comment '所在行业',
  `big_image`       varchar(191)        default null comment '头像',
  `bank_user_name`  varchar(100)        default null comment '开户行姓名',
  `shop_bank`       varchar(100)        default null comment '开户行',
  `bank_no`         varchar(32)         default null comment '开户行卡号',
  `withdraw_passwd` varchar(64)         default null comment '提现密码验证',
  `user_avatar`     varchar(191)        not null default '/image/admin/head_icon.png' comment '用户头像',
  `create_time`      timestamp            	default current_timestamp,
  `update_time`      timestamp            	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  unique key (`user_id`)
);

-- -- 积分设置
-- drop table if exists `b2c_user_score_set`;
create table `b2c_user_score_set` (
  `id`          int(11)      		not null    auto_increment,
  `shop_id`     int(11)      		not null default 0 	comment '店铺id',
  `score_name`  varchar(20)  		not null 			comment '购买:buy,评价:comment,兑换:convert',
  `status`      tinyint(1)   		not null default '0' comment '0:未启用,1:启用',
  `two_status`  tinyint(1)   		not null default '0',
  `set_val`     varchar(10)  		not null default '',
  `set_val2`    varchar(10)  		not null default '',
  `set_val3`    text,
  `sign_val`    int(11)      		not null default '0' comment '签到积分',
  `sign_date`   tinyint(1)   		not null default '0' comment '签到天数',
  `desc`        varchar(191) 		not null default '',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  `growth_flag` tinyint(1)   		not null default '0' comment '0:不送成长值，1：送成长值',
  primary key (`id`)
);

-- --  运费模板表
-- drop table if exists `b2c_deliver_fee_template`;
create table `b2c_deliver_fee_template` (
  `deliver_template_id` int(11)  			not null auto_increment,
  `template_name`       varchar(191)		not null default '' 	comment '模板名称',
  `template_content`    text 							comment '模板内容，json存储',
  `shop_id`             int(11)         	not null default 0 	comment '店铺id',
  `flag`                tinyint(1)       	not null default 0 	comment '0运费模板,1重量运费模板',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`deliver_template_id`),
  key (`shop_id`)
);

-- -- --  商品表 `b2c_goods`
-- drop table if exists `b2c_goods`;
create table `b2c_goods` (
  `goods_id`            int(8)                  not null auto_increment,
  `shop_id`             int(11)                	not null default '0' 	comment '店铺id',
  `cat_id`              int(5)                 	not null default '0',
  `goods_sn`            varchar(60)            	not null default '',
  `goods_name`          varchar(120)          	not null default '',
  `brand_id`            int(11)                	not null default 0 		comment '品牌id',
  `goods_ad`            varchar(1024)          	not null default '' 		comment '广告词',
  `goods_number`        int(11)              	not null default '0' 	comment '库存',
  `goods_weight`        decimal(10, 3)        	not null default '0.000',
  `market_price`        decimal(10, 2)       	not null default '0.00',
  `shop_price`          decimal(10, 2)        	not null default '0.00',
  `cost_price`          decimal(10, 2) 			not null default '0.00' 	comment '成本价',
  `goods_desc`          text,
  `goods_img`           varchar(500)          	not null default '' ,
  `is_on_sale`          tinyint(1)            	not null default '1' 	comment '是否在售，1在售，0下架',
  `del_flag`           tinyint(1)         	 	not null default '0',
  `goods_type`          tinyint(2)  			not null default 0 		comment '商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品',
  `deliver_template_id` int(5)               	not null default '0' 	comment '运费模板id',
  `goods_sale_num`      int(8)                	not null default '0' 	comment '销售数量',
  `goods_collect_num`   int(8)                 	not null default '0' 	comment '收藏数量',
  `create_time`         timestamp              	default current_timestamp,
  `update_time`         timestamp              	default current_timestamp on update current_timestamp comment '最后修改时间',
  `state`               tinyint(1)             	not null default '0' 	comment '审核状态,0待审核 1 审核通过 2 违规下架',
  `reason`              text                    comment '违规下架原因' ,
  `sub_account_id`      int(11)               	not null default '0' 	comment '子帐号id，主要用于官方店铺',
  `sale_time`           timestamp             	default current_timestamp comment '上架时间',
  `limit_buy_num`       int(11)                	not null default '0' 	comment '最少起购数量，0不限购',
  `unit`                varchar(60)             not null default '' 		comment '商品单位' ,
  `add_sale_num`        int(11)                	not null default '0' 	comment '虚假销量',
  `limit_max_num`       int(11) 				not null default 0     	comment '最多起购数量，0不限购',
  `sale_type`           tinyint(1)            	not null default '0' 	comment '上架状态,0立即上架， 1审核通过 2 加入仓库',
  `sort_id`             int(11)                	not null default '0',
  `goods_video`         varchar(191)          	not null default '' 		comment '视频',
  `goods_video_img`     varchar(191)          	not null default '' 		comment '视频首图',
  `goods_video_size`    int(11)               	not null default 0 		comment '视频尺寸',
  `goods_video_id`      int(11)               	not null default 0 		comment '视频id',
  `goods_page_id`       int(11)                	not null default '0' 	comment '详情页装修模板id',
  `is_page_up`          tinyint(1)             	not null default '1' 	comment '是否在文本区域上方',
  `is_card_exclusive`   tinyint(1)          	not null default 0 		comment '是否会员卡专属',
  `base_sale`           int(8)             		not null default '0' 	comment '初始销量',
  `source`              tinyint(1)           	not null default '0' 	comment '商品来源,0：店铺自带；1、2..等：不同类型店铺第三方抓取自带商品来源',
  `is_control_price`    tinyint(1)             	not null default '0' 	comment '是否控价：0不控价，1控价（不可修改价格）',
  `can_rebate`          tinyint(1)         		not null default 0  		comment '是否分销改价',
  primary key (`goods_id`),
  unique index `goods_id` (`goods_id`, `shop_id`),
  unique index `goods_sn` (`goods_sn`, `shop_id`),
  index `goods_id_2` (`goods_id`),
  index `shop_id` (`shop_id`),
  index `cat_id` (`cat_id`)
);


-- --  商品图片表 `b2c_goods_img`
-- drop table if exists `b2c_goods_img`;
create table `b2c_goods_img` (
  `img_id`   int(11)  not null auto_increment,
  `goods_id` int(11)		not null default '0',
  `img_url`  varchar(500)	not null default '',
  `img_desc` varchar(500)  	not null default '',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`img_id`),
  key `goods_id` (`goods_id`)
);

-- -- --  规格表 `b2c_spec`
-- drop table if exists `b2c_spec`;
create table `b2c_spec` (
  `spec_id`   int(11) 		not null auto_increment,
  `spec_name` varchar(60)	not null default '',
  `del_flag`  tinyint(1)   	not null default '0',
  `goods_id`   int(11)     	not null default 0 comment '店铺id',
  `create_time`      timestamp   not null   	default current_timestamp,
  `update_time`      timestamp    not null   	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`spec_id`),
  UNIQUE INDEX unique_spec_name_goods_id (spec_name,goods_id)
);

-- drop table if exists `b2c_spec_vals`;
create table `b2c_spec_vals` (
  `spec_val_id`   int(11)  	not null auto_increment,
  `spec_id`     int(11)			not null default '0',
  `spec_val_name` varchar(60)  	not null default '',
  `del_flag`    tinyint(1)    	not null default '0',
  `goods_id`    int(11)        	not null default 0 comment '店铺id',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`spec_val_id`),
  key `spec_id` (`spec_id`),
  UNIQUE INDEX unique_spec_id_spec_val_name (spec_id,spec_val_name)
);
-- --  商品规格组合的产品表 `b2c_goods_spec_product`
-- drop table if exists `b2c_goods_spec_product`;
create table `b2c_goods_spec_product` (
  `prd_id`           int(10)                         	not null auto_increment,
  `shop_id`          int(11)                        	not null default '0',
  `goods_id`         int(10)                        	not null default '0',
  `prd_price`        decimal(10, 2)                   	not null default '0.00',
  `prd_market_price` decimal(10, 2)                   	not null default '0.00' 	comment '市场价',
  `prd_cost_price`   decimal(10, 2) 					not null default '0.00' 	comment '成本价',
  `prd_number`       int(11)                        	not null default '0' 	comment '当前规格组合产品库存',
  `prd_sn`           varchar(65)                       	not null default '' 		comment '商家编码',
  `prd_codes`        varchar(500)                     	not null default '' 		comment '商品条码',
  `prd_specs`        varchar(1024)                     	not null default '',
  `prd_desc`         varchar(1024)                     	not null default '' 		comment '规格描述，格式例子：颜色:红色 尺码:s',
  `del_flag`         tinyint(1)                       	not null default '0',
  `self_flag`        tinyint(1) 						not null default '0'   	comment '1:商家自己添加商品，其他没用',
  `low_shop_price`   varchar(1024)                     	not null default '0.00' 	comment '最低售出价格',
  `prd_img`          varchar(1024)                     	not null default '' 		comment '图片地址',
  `price_flag`       tinyint(1)                       	not null default '0' 	comment '0:商家未改价，1：商家改价，2：批量改价，3：毛利改价',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`prd_id`),
  key `gsp_goods_id` (`goods_id`),
  key `gsp_goods_codes` (`prd_codes`),
  key `gsp_prd_sn` (`prd_sn`)
);

-- -- --  购物车 `b2c_cart`
-- drop table if exists `b2c_cart`;
create table `b2c_cart` (
  `rec_id`       bigint(20)        	not null auto_increment,
  `shop_id`      int(11)          	not null default '0' 	comment '店铺id',
  `store_id`     int(11) 			not null default '0' 	comment '门店id',
  `user_id`      int(11)          	not null default '0',
  `goods_id`     int(11)         	not null default '0',
  `goods_sn`     varchar(60)     	not null default '',
  `product_id`   int(11)         	not null default '0' 	comment '规格产品id',
  `prd_sn`       varchar(60)      	not null default '',
  `goods_name`   varchar(120)      	not null default '',
  `market_price` decimal(10, 2)     not null default '0.00',
  `goods_price`  decimal(10, 2)    	not null default '0.00',
  `goods_number` smallint(5)    	not null default '0',
  `goods_specs`  text 							comment '例如,颜色:黑色',
  `parent_id`    int(11)        	not null default 0 	comment '该商品的父商品id，没有该值为0 ，有的话那该商品就是该id的配件',
  `rec_type`     tinyint(1)     	not null default '0' comment '购物车商品类型，0，普通 1，团够 2，拍卖 3，夺宝奇兵',
  `is_gift`      smallint        	not null default '0',
  `is_buynow`    tinyint(1)     	not null default 0 	comment '',
  `strategy`     varchar(191)      	not null default '' 	comment '参与的优惠活动,json格式',
  `action`       tinyint(1)        	not null default '0' comment '商品活动类型：1：加价购主商品， 2： 满折满减',
  `identity_id`  int(11)           	not null default '0' comment '关联id: 如：加价购id, 满折满减id',
  `extend_id`    int(11)           	not null default '0' comment '扩展字段: 如：换购挡位id',
  `is_checked`   tinyint(1) 		not null default 0   comment '是否选中',
  `platform`     varchar(20)       	default null comment '平台：如 crm',
  `extend_info`  varchar(200)    	default null comment '扩展内容：如crm的promote_info',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`rec_id`)
);

-- -- --  b2c_uploaded_image 上传图片表
-- drop table if exists `b2c_uploaded_image`;
create table `b2c_uploaded_image` (
  `img_id`         int(11)  		not null auto_increment,
  `img_type`       varchar(60)	 	not null default '',
  `img_size`       int(11)  		not null default '0',
  `img_name`       varchar(500)    	not null default '' comment '图片名称，用于前端显示',
  `img_orig_fname` varchar(500)    	not null default '',
  `img_path`       varchar(500)    	not null default '',
  `img_url`        varchar(500)    	not null default '',
  `img_cat_id`     int(11)        	not null default 0 comment '图片分类',
  `img_width`      int(11)        	not null default 0,
  `img_height`     int(11)          not null default 0,
  `is_refer`       tinyint         	not null default 0 comment '是否引用',
  `shop_id`        int(11)         	not null default 0 comment '店铺id',
  `del_flag`       tinyint(1)      	not null default 0,
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`img_id`),
  key (`shop_id`),
  key (`img_orig_fname`)
);

-- --  上传图片分类
-- drop table if exists `b2c_uploaded_image_category`;
create table `b2c_uploaded_image_category` (
  `img_cat_id`        int(10)  		not null auto_increment,
  `shop_id`           int(11)   	not null default 0 comment '店铺id',
  `img_cat_name`      varchar(60)  	not null default '',
  `img_cat_parent_id` int(10)      	not null default 0,
  `cat_ids`           varchar(191) 	not null default '0' comment '层级id串,逗号分隔',
  `level`             tinyint       not null default 0 comment '层级，0开始',
  `sort`              int(11)      	not null default 0 comment '排序优先级',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`img_cat_id`),
  key (`shop_id`)
);

-- --  小视频
-- drop table if exists `b2c_uploaded_video`;
create table `b2c_uploaded_video` (
  `video_id`         int(11)  		not null auto_increment,
  `video_type`       varchar(60)   	not null default '',
  `video_size`       int(11)  		not null default '0',
  `video_name`       varchar(191)  	not null default '' comment '视频名称，用于前端显示',
  `video_orig_fname` varchar(191)  	not null default '',
  `video_path`       varchar(500)  	not null default '',
  `video_snap_path`  varchar(500)  	not null default '' comment '视频截图',
  `video_url`        varchar(500)  	not null default '',
  `video_cat_id`     int(11)       	not null default 0 comment '视频分类',
  `video_width`      int(11)       	not null default 0,
  `video_height`     int(11)       	not null default 0,
  `video_meta`       varchar(500)  	not null default '' comment '视频信息,json',
  `is_refer`         tinyint(1)   	not null default 0 comment '是否引用',
  `shop_id`          int(11)       	not null default 0 comment '店铺id',
  `del_flag`         tinyint(1)    	not null default 0,
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`video_id`),
  key (`shop_id`),
  key (`video_orig_fname`)
);

-- --  微信小程序自定义页面表
-- drop table if exists `b2c_xcx_customer_page`;
create table `b2c_xcx_customer_page` (
  `page_id`              int(11)  		not null auto_increment,
  `shop_id`              int(11)      	not null default 0 	comment '店铺id',
  `page_name`            varchar(60)   	not null default '',
  `page_type`            tinyint(1)    	not null default 0 	comment '是否为首页1为首页，0非首页',
  `page_enabled`         tinyint(1)    	not null default 1 	comment '是否可用',
  `page_tpl_type`        tinyint(1)    	not null default 0 	comment '模板类型:0自定义模板，1默认模板，2美女模板，3自定义首页',
  `page_content`         longtext 					comment '页面内容，json格式存储',
  `page_publish_content` longtext 					comment '正式页面内容，json格式存储',
  `page_state`           tinyint(1)  	not null default '0' comment '状态：0未发布，1已发布',
  `cat_id`               int(11)      	not null default 0 	comment '页面分类id',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`page_id`)
);

-- -- 订单表
-- drop table if exists `b2c_order_info`;
create table `b2c_order_info` (
  `order_id`             mediumint(8)          	not null  auto_increment comment '订单id',
  `shop_id`              int(11)              	not null default 0 comment '店铺id',
  `order_sn`             varchar(20)           	not null default '' comment '订单编号',
  `main_order_sn`        varchar(20)           	not null default '' comment '主订单编号(拆单时用)',
  `user_id`              mediumint(8)          	not null default '0' comment '用户id',
  `user_openid`          varchar(191)          	not null default '' comment '用户openid',
  `order_status`         tinyint(1)           	not null default '0' comment '订单状态',
  `order_status_name`    varchar(32)           	not null default '' comment '订单状态名称',
  `consignee`            varchar(60)           	not null default '' comment '收件人姓名',
  `address_id`           int(11)               	not null default 0 comment '地址id',
  `country_code`         mediumint(10)        	not null default '0' comment '国家编号',
  `country_name`         varchar(50)           	not null default '' comment '国家名称',
  `province_code`        mediumint(10)       	not null default '0' comment '省份编号',
  `province_name`        varchar(50)          	not null default '' comment '省份名称',
  `city_code`            mediumint(10)         	not null default '0' comment '城市编号',
  `city_name`            varchar(120)          	not null default '' comment '城市名称',
  `district_code`        mediumint(10)         	not null default '0' comment '区县编号',
  `district_name`        varchar(120)          	not null default '' comment '区县名称',
  `address`              varchar(191)          	not null default '' comment '更多详细地址',
  `complete_address`     varchar(512)          	not null default '' comment '完整收件地址',
  `zipcode`              varchar(60)           	not null default '' comment '邮编',
  `mobile`               varchar(60)            not null default '' comment '手机号',
  `add_message`          varchar(191)          	not null default '' comment '客户留言',
  `shipping_id`          tinyint(3)           	not null default '0' comment '快递id',
  `shipping_name`        varchar(120)          	not null default '' comment '快递名称',
  `pay_code`             varchar(30)           	not null default '' comment '支付代号',
  `pay_name`             varchar(120)          	not null default '' comment '支付名称',
  `pay_sn`               varchar(32)           	not null default '' comment '支付流水号',
  `goods_amount`         smallint(6)           	not null default '0' comment '订单商品数量',
  `shipping_fee`         decimal(10, 2)        	not null default '0.00' comment '快递费金额',
  `money_paid`           decimal(10, 2)        	not null default '0.00' comment '订单应付金额',
  `shoper_reduce_amount` decimal(10, 2)        	not null default '0.00' comment '商家减价金额',
  `sub_order_amount`     decimal(10, 2)        	not null default '0.00' comment '子订单总金额',
  `discount`             decimal(10, 2)        	not null default '0.00' comment '券折扣金额',
  `score_discount`       decimal(10, 2)        	not null default '0.00' comment '积分抵扣金额',
  `use_account`          decimal(10, 2)        	not null default '0.00' comment '用户消费余额',
  `order_amount`         decimal(10, 2)        	not null default '0.00' comment '订单总金额',
  `grade_percent`        decimal(10, 2)        	not null default '0.00' comment '购买会员等级的折扣%',
  `discount_price`       decimal(10, 2)        	not null default '0.00' comment '购买会员等级的折扣金额',
  `dapei_reduce_amount`  decimal(10, 2)        	not null default '0.00' comment '搭配减价',
  `package_discount`     decimal(10, 2) 		not null default 0.00   comment '一口价抵扣金额',
  `dapei_id`             int(8)                 not null default '0' comment '搭配id来源',
  `create_time`      	 timestamp    	default current_timestamp,
  `update_time`      	 timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  `confirm_time`         timestamp          null	default null comment '订单确收收货时间',
  `pay_time`             timestamp          null  	default null comment '支付时间',
  `shipping_time`        timestamp           null  	default null comment '发货时间',
  `closed_time`          timestamp           null  	default null comment '关闭时间',
  `cancelled_time`       timestamp           null 	default null comment '取消时间',
  `finished_time`        timestamp           null 	default null comment '订单完成时间',
  `return_time`          timestamp            null 	default null comment '订单申请退货时间',
  `return_finish_time`   timestamp            null 	default null comment '订单退货完成时间',
  `refund_time`          timestamp            null 	default null comment '订单申请退款时间',
  `refund_finish_time`   timestamp            null	default null comment '订单退款完成时间',
  `shipping_no`          varchar(191)          	not null default '' 		comment '快递单号',
  `shipping_type`        varchar(60)           	not null default '' 		comment '快递类型',
  `is_cod`               tinyint(1)           	not null default '0' 	comment '是否货到付款',
  `return_type_cfg`      tinyint(1)          	not null default '1' 	comment '是否支持退换货：1支持 2不支持',
  `return_days_cfg`      tinyint(1)            	not null default '7' 	comment '发货后自动确认收货时间,单位天',
  `order_timeout_days`   smallint(3)           	not null default '3' 	comment '确认收货后自动订单完成时间,单位天',
  `seller_remark`        varchar(512)         	not null default '' 		comment '卖家备注',
  `erpordercode`         varchar(32)           	not null default '' 		comment 'erp中订单代码',
  `comment_flag`         tinyint(1)            	not null default '0' 	comment '0:未评论，1:已评论，2：已晒单',
  `fanli_user_id`        int(11)              	not null default '0' 	comment '返利会员id',
  `fanli_grade`          varchar(64)           	not null default '' 		comment '返利等级',
  `fanli_percent`        decimal(10, 2)        	not null default '0.00' 	comment '返利百分比',
  `settlement_flag`      tinyint(1)            	not null default '0' 	comment '结算标志：0：未结算，1：已结算',
  `invoice_id`           int(11)               	not null default '0' 	comment '发票id',
  `invoice_content`      int(11)               	not null default '0' 	comment '发票类型：0普通发票；1增值税专票',
  `invoice_title`        text               	comment '发票内容：json存储',
  `refund_status`        tinyint(1)            	not null default '0' 	comment '1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货',
  `pay_order_sn`         varchar(30)           	not null default "" 		comment '对账单号',
  `goods_type`           varchar(50) 			not null default '0'		comment '商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品 6限时降价 7加价购',
  `order_source`         tinyint(2) 			not null default '0'		comment '订单来源，0：小程序，1wap，2app',
  `fanli_type`           tinyint(2) 			not null default '0'		comment '返利类型，0：普通订单，1：分销返利订单，2：返利会员返利订单',
  `manual_refund`        tinyint(1)            	not null default 0 comment '1代表手动退款，0代表非手动',
  `order_pay_way`        tinyint(2)            	not null default 0 comment '订单付款方式，0全款 1定金 2补款',
  `bk_order_sn`          varchar(32)           	not null default '' comment '补款订单号 order_pay_way=1时有效',
  `bk_order_money`       decimal(10, 2)        	not null default '0.00' comment '补款金额 order_pay_way=1时有效',
  `bk_order_paid`        tinyint(1)            	not null default 0 comment '补款金额是否支付 order_pay_way=1时有效，0未支付，1已支付',
  `pin_goods_money`      decimal(10, 2)        	not null default '0.00' comment '当前拼团商品金额，阶梯团根据人数时会变化，补款也随之变化',
  `pin_yj_money`         decimal(10, 2)        	not null default '0.00' comment '拼团支付佣金金额',
  `pin_group_id`         int(11)                not null default 0 comment '拼团id',
  `del_flag`             tinyint(1)            	not null default '0' comment '0:未删除，1:已删除',
  `source`               varchar(30)            not null default "" comment '订单来源，记录app，wap，pc来源',
  `part_ship_flag`       tinyint(1)            	not null default '0' comment '0:，1:部分发货',
  `promotion_id`         int(11)                not null default 0 comment '促销活动id',
  `promotion_reduce`     decimal(10, 2)        	not null default '0' comment '促销活动折扣金额,满折满减',
  `push_type`            tinyint(1)            	not null default '0' comment 'yadu推送状态：0-暂无推送，1-推送失败，2-推送成功',
  `push_desc`            varchar(100)          	not null default '' comment 'yadu推送失败原因',
  `pos_flag`             tinyint(1)            	not null default '0' comment '门店订单标志：0：商城，1：门店同步订单',
  `pos_shop_name`        varchar(191)          	not null default '' comment 'pos店铺名称',
  `store_id`             int                   	default 0 comment '门店id',
  `store_name`           varchar(191)         	not null default '' comment '门店名称',
  `member_card_id`       int                  	not null default 0 comment '会员卡id',
  `member_card_reduce`   decimal(10, 2)        	not null default 0 comment '会员卡优惠金额',
  `member_card_balance`  decimal(10, 2)        	not null default 0 comment '会员卡消费金额',
  `expire_time`          timestamp            null 	default null comment '订单支付过期时间',
  `del_time`		     timestamp      	          null	default null comment '删除时间',
  `prepay_id`            varchar(191)          	default null comment '微信支付id，用于发送模板消息',
  `deliver_type`         tinyint(1)            	not null default 0 comment '配送类型：0 快递 1 自提',
  `deliver_type_name`    varchar(191)          	default null comment '配送类型名称',
  `pickupdate_time`      varchar(30)          	default null comment '自提时间',
  `star_flag`            tinyint(1)            	not null default 0 comment '标星订单：0 未标星 1 标星',
  `verify_code`          varchar(191)          	not null default '' comment '核销自提码',
  `split`                int                  	not null default 0 comment '分裂优惠券id',
  `card_no`              varchar(32) 			not null default '' comment '会员卡号',
  `fanli_money`          decimal(10, 2) 		not null default '0.00' comment '单品返利金额',
  `true_name`            varchar(32) 			not null default '' comment '真实姓名',
  `id_card`              varchar(32) 			not null default '' comment '身份证号',
  `ali_trade_no`         varchar(60)           	not null default '' comment '支付宝交易单号',
  `grouper_cheap_reduce` decimal(10, 2)       null 	not null default 0 comment '团长优惠金额',
  `bk_shipping_time`     timestamp            null	default null comment '定金预计发货时间',
  `bk_return_type`       tinyint(2)            	default null comment '定金退款状态',
  `bk_prepay_id`         varchar(191)         	default null comment '微信支付id，用于发送模板消息',
  `pre_sale_discount`    decimal(10, 2) 		not null default 0.00 comment '定金膨胀优惠金额',
  `instead_pay_money`    decimal(10, 2) 		not null default 0.00 comment '代付金额',
  `order_user_message`   varchar(50)           	default null comment '发起人留言',
  `instead_pay`          text              		comment '好友代付规则',
  `instead_pay_num`      smallint 				not null default 0 	comment '代付人数',
  `is_promote`           tinyint(1) 			not null default 0   comment '是否是推广单',
  `verifier_id`          int(11) 				not null default 0 	comment '核销员id',
  `exchang`              tinyint(2) 			not null default 0 	comment '1 兑换 0否',
  primary key (`order_id`),
  unique key `order_sn` (`order_sn`),
  key `main_order_sn` (`main_order_sn`),
  key `user_id` (`user_id`),
  key `user_openid` (`user_openid`),
  key `order_status` (`order_status`),
  key `shipping_id` (`shipping_id`),
  key (`shop_id`)
);

-- --   订单操作表 b2c_order_action
-- drop table if exists `b2c_order_action`;
create table `b2c_order_action` (
  `action_id`    mediumint(8)  	not null auto_increment,
  `shop_id`      int(11)       	not null default 0 comment '店铺id',
  `order_id`     mediumint(8)  	not null default '0',
  `order_sn`     varchar(20)  	not null default '',
  `action_user`  varchar(128)  	not null default '',
  `user_id`      mediumint(8)  	not null default '0',
  `user_cid`     varchar(40)   	not null default '',
  `user_openid`  varchar(128)  	not null default '',
  `order_status` tinyint(1)    	not null default '0',
  `action_note`  varchar(191)  	not null default '',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`action_id`),
  key (`order_id`, `order_sn`)
);

-- --   订单商品表  b2c_order_goods
-- drop table if exists `b2c_order_goods`;
create table `b2c_order_goods` (
  `rec_id`                 int(11)         not null  auto_increment,
  `shop_id`                int(11)              not null default 0 comment '店铺id',
  `order_id`               int(11)        		not null default '0',
  `order_sn`               varchar(20)         	not null default '',
  `goods_id`               int(11)       		not null default '0',
  `goods_name`             varchar(120)        	not null default '',
  `goods_sn`               varchar(60)          not null default '',
  `product_id`             int(11)        		not null default '0',
  `product_sn`             varchar(65)         	not null default '',
  `goods_number`           smallint(5)         	not null default '1',
  `market_price`           decimal(10, 2)       not null default '0.00',
  `goods_price`            decimal(10, 2)      	not null default '0.00',
  `goods_attr`             text,
  `send_number`            smallint(5)         	not null default '0',
  `return_number`          smallint(5)         	not null default '0' comment '退货/退款成功数量',
  `is_real`                tinyint(1)          	not null default '0',
  `goods_attr_id`          varchar(191)        	not null default '',
  `goods_img`              varchar(191)        	not null default '',
  `refund_status`          tinyint(1)          	not null default '0' comment '1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货',
  `comment_flag`           tinyint(1)          	not null default '0' comment '0:未评论，1:已评论，2：已晒单',
  `stra_id`                int(11)             	not null default '0' comment '商品参与的促销活动id',
  `per_discount`           decimal(10, 2)      	not null default '0' comment '促销折扣均摊到每件商品的折扣',
  `is_gift`                int(1)              	not null default '0' comment '是否是赠品',
  `r_goods`                varchar(191)        	not null default '' comment '赠品的关联商品',
  `goods_score`            int(11)             	not null default '0' comment '商品积分',
  `goods_growth`           int(11)             	not null default '0' comment '商品成长值',
  `discounted_goods_price` decimal(10, 2)      	not null default '0' comment '折后单价',
  `discount_detail`        text 							comment '打折详情，json存储',
  `fanli_type`             tinyint(1) 			not null default '0'	comment '0:不返利商品，1：返利商品',
  `can_calculate_money`    decimal(10, 2) 		not null default '0.00'	comment '单品可计算返利金额，刨除优惠券和其他折扣后的金额',
  `fanli_money`            decimal(10, 2) 		not null default '0.00'  comment '单品返利金额',
  `discounted_total_price` decimal(10, 2) 		not null default 0.00  	comment '折后总价',
  `total_fanli_money`      decimal(10, 2) 		not null default 0.00 	comment '商品返利总金额',
  `purchase_price_id`      int 					not null default 0 		comment '加价购id',
  `purchase_price_rule_id` int 					not null default 0      	comment '换购挡位id',
  `reduce_price_id`        int 					not null default 0      	comment '限时降价id',
  `fanli_strategy`         varchar(500)      	default null comment '返利比例',
  `fanli_percent`          decimal(10, 2) 		not null default 0.00   comment '返利比例',
  `cost_price`             decimal(10, 2) 		not null default 0.00   comment '成本价',
  `is_card_exclusive`      tinyint(1) 			not null default 0          comment '是否会员卡专属',
  `promote_info` 		   varchar(500) 		default null comment '推广信息',
  `gift_id` 			   int 					not null default 0    comment '赠品id',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`rec_id`),
  key `order_id` (`order_id`),
  key `order_sn` (`order_sn`),
  key `goods_id` (`goods_id`),
  key (`shop_id`)
);


-- -- 发放优惠券
-- drop table if exists `b2c_customer_avail_coupons`;
create table `b2c_customer_avail_coupons` (
  `id`                 mediumint(8)  	not null auto_increment,
  `coupon_sn`          varchar(30)  	not null default '',
  `user_id`            mediumint(8) 	not null default '0',
  `act_type`           mediumint(5)  	not null default '0' comment 'user_id不为空时1:经销商等级打折,为空时1:首次下单优惠，2减价，3打折',
  `act_id`             mediumint(8)  	not null default '0',
  `start_time`         timestamp       null	default null,
  `end_time`           timestamp       null	default null,
  `type`               tinyint(2)    	not null default '0' comment '1为减价，2为打折',
  `amount`             decimal(10, 2)   not null default '0.00' comment '打折或减价量',
  `act_desc`           varchar(128)    	not null default '',
  `limit_order_amount` mediumint(8)  	not null default '0',
  `is_used`            tinyint(1)      	not null default '0',
  `used_time`          timestamp       null	default null,
  `access_mode`        tinyint(1)      	not null default '0' comment '获取方式，0：发放，1：领取',
  `access_id`          mediumint(8)    	not null default '0' comment '发放活动id',
  `notify_time`        timestamp       null	default null comment '通知时间',
  `order_sn`           varchar(20)     	not null default '' comment '优惠订单编号',
  `del_flag`           tinyint(1)      	not null default '0' comment '是否删除,1：删除',
  `get_source`         tinyint(2) 		not null default 0   comment '//1表单送券2支付送券3活动送券4积分兑换5直接领取6分裂优惠券7crm领券8幸运大抽奖9定向发券',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  unique key (`coupon_sn`),
  key `user_id` (`user_id`)
);

-- -- 优惠券列表
-- 常乐  7月16日 重新设计优惠券表结构
-- drop table if exists `b2c_mrking_voucher`;
create table `b2c_mrking_voucher` (
  `id`                   int(11)                not null auto_increment,
  `shop_id`              int(11)                not null default 0 comment '店铺id',
  `act_code`             varchar(50)            not null default 'voucher',
  `act_name`             varchar(120)           not null default '',
  `start_time`           timestamp null default null,
  `end_time`             timestamp null default null,
  `denomination`         decimal(10, 2)         not null default '0' comment '面额',
  `total_amount`         int(11)                not null default '0' comment '发行量',
  `type`                 tinyint(1)             default 0 comment '优惠券类型，0普通优惠券；1分裂优惠券',
  `surplus`              int(11)                not null default '0',
  `remain_amount`        int(11)                not null default '0',
  `use_consume_restrict` tinyint(1)             not null default '0' comment '使用限制',
  `least_consume`        mediumint(5)           not null default '0' comment '满多少可用',
  `use_explain`          varchar(256)           not null default '',
  `enabled`              tinyint(1)             not null default '1',
  `is_random`            tinyint(1)             not null default '0' comment '是否需要积分兑换',
  `receive_per_person`   smallint(3)            not null default '0' comment '每人限领张数',
  `suit_goods`           tinyint(1)             not null default '0' comment '0:全店通用,1:指定店铺',
  `together_used`        tinyint(1)             not null default '0' comment '是否与其他优惠券同时使用',
  `permit_share`         tinyint(1)             not null default '0' comment '是否允许分享优惠券链接',
  `remind_owner`         tinyint(1)             not null default '0' comment '是否到期前提醒用户',
  `giveout_amount`       smallint(4)            not null default '0' comment '发放优惠券数量',
  `giveout_person`       smallint(4)            not null default '0' comment '发放优惠券人数',
  `receive_amount`       smallint(4)            not null default '0' comment '领取优惠券数量',
  `receive_person`       smallint(4)            not null default '0' comment '领取优惠券人数',
  `used_amount`          smallint(4)            not null default '0' comment '已使用优惠券数量',
  `alias_code`           varchar(16)            not null default '' comment '唯一活动代码',
  `validation_code`      varchar(10)            not null default '' comment '领取码',
  `recommend_goods_id`   text                   comment '指定商品可用',
  `recommend_cat_id`     text                   comment '指定平台可用',
  `recommend_sort_id`    text                   comment '指定商家分类可用',
  `validity`             mediumint(11)      not null default 0    comment '优惠券有效天数',
  `del_flag`             tinyint(1)             not null default '0' comment '1为删除状态',
  `action`               tinyint(1)       not null default 1   comment '1:系統创建 2：来自crm',
  `identity_id`          varchar(50)            default null comment '关联外部优惠券规则唯一码',
  `recommend_product_id` text                   comment '关联商品规格',
  `use_score`            tinyint(2)       not null default 0    comment '是否可以积分兑换',
  `score_number`         int(6)         not null default 0    comment '需要积分数',
  `card_id`              text                 comment '专属会员卡',
  `create_time`          timestamp      default current_timestamp,
  `update_time`          timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  unique key `alias_code` (`alias_code`),
  key `act_name` (`act_name`),
  key (`shop_id`)
);

-- -- 快递配置表 `b2c_shipping`
-- drop table if exists `b2c_shipping`;
create table `b2c_shipping` (
  `shipping_id`    tinyint(3)  			not null auto_increment,
  `shipping_code`  varchar(20)         	not null default '',
  `shipping_name`  varchar(120)        	not null default '',
  `shipping_desc`  varchar(191)        	not null default '',
  `insure`         varchar(10)         	not null default '0',
  `support_cod`    tinyint(1)  			not null default '0',
  `enabled`        tinyint(1)  			not null default '0',
  `shipping_print` text,
  `print_model`    tinyint(1)          	default '0',
  `shipping_order` tinyint(3)  			not null default '0',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`shipping_id`),
  key `shipping_code` (`shipping_code`, `enabled`)
);

-- -- 用户余额
-- drop table if exists `b2c_user_account`;
create table `b2c_user_account` (
  `id`         mediumint(8)   not null auto_increment,
  `user_id`    mediumint(8)   not null default '0',
  `admin_user` varchar(191)           not null default '0' comment '操作员',
  `order_sn`   varchar(20)            not null default '' comment '分销订单结算产生返利',
  `amount`     decimal(10, 2)         not null comment '金额',
  `admin_note` varchar(191)           not null comment '操作员备注',
  `payment`    varchar(90)            not null comment '支付方式',
  `is_paid`    tinyint(1)             not null default '0' comment '支付类型，0：充值，1：消费',
  `remark`     varchar(1024) 			comment '备注',
  `source`     tinyint(1) 				default '0' comment '1:分销来源，0:充值',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `user_id` (`user_id`)
);

-- -- 支付配置表
-- drop table if exists `b2c_payment`;
create table `b2c_payment` (
  `id`            tinyint(3)  not null auto_increment,
  `shop_id`       int(11)             not null default 0 comment '店铺id',
  `pay_name`      varchar(80)         not null default '',
  `pay_code`      varchar(20)         not null default '',
  `pay_fee`       varchar(10)         not null default '0',
  `pay_desc`      text,
  `enabled`       tinyint(1)  not null default '0',
  `is_cod`        tinyint(1)  not null default '0',
  `is_online_pay` tinyint(1)  not null default '0',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  unique key `pay_code` (`pay_code`)
) auto_increment = 4;

-- --  支付记录表
-- drop table if exists `b2c_payment_record`;
create table `b2c_payment_record` (
  `id`                  mediumint(20)  not null auto_increment,
  `shop_id`             int(11)                not null default 0 comment '店铺id',
  `pay_sn`              varchar(32)            not null default '' comment '支付流水号',
  `pay_code`            varchar(20)            not null default '' comment '支付宝:alipay,微信：？，...',
  `pay_code_alias`      varchar(20)            not null default '' comment '支付宝:alipay,微信：？，...',
  `trade_no`            varchar(32)            not null default '' comment '各平台交易号',
  `trdae_status`        tinyint(3)             not null default '-1' comment '交易状态0:成功，其它失败',
  `trdae_origin_status` varchar(20)            not null default '' comment '原始交易状态',
  `subject`             varchar(256)           not null default '' comment '商品名称',
  `quantity`            mediumint(10)          not null default '1' comment '购买数量',
  `order_sn`            varchar(32)            not null default '' comment '网站订单号',
  `main_order_sn`       varchar(20)            not null default '' comment '网站主订单号',
  `total_fee`           varchar(20)            not null default '0' comment '交易金额',
  `buyer_id`            varchar(256)           not null default '' comment '买家支付用户号',
  `buyer_account`       varchar(256)           not null default '' comment '各平台买家支付账号',
  `seller_id`           varchar(32)            not null default '' comment '收款方用户号',
  `seller_account`      varchar(100)           not null default '' comment '各平台收款方支付账号',
  `gmt_create`          timestamp             null 	default null comment '支付交易创建时间',
  `notify_time`         timestamp            null	default null comment '通知时间',
  `gmt_pay_time`        timestamp              null	default null comment '交易付款时间',
  `gmt_close_time`      timestamp              null	default null comment '交易关闭时间',
  `created`             timestamp             null	default null comment '插入时间',
  `remark1`             text 					comment '自定义备注  建议用于存储原始数据',
  `remark2`             text 					comment '自定义备注',
  primary key (`id`),
  unique key `pay_sn` (`pay_sn`),
  key `pay_code` (`pay_code`),
  key `trade_no` (`trade_no`),
  key `order_sn` (`order_sn`),
  key `trdae_status` (`trdae_status`),
  key `seller_account` (`seller_account`)
);

-- --  收支明细表
-- drop table if exists `b2c_income_outcome_detail`;
create table `b2c_income_outcome_detail` (
  `id`                       mediumint(20)  not null     auto_increment,
  `req_id`                   mediumint(12)          not null     default 0,
  `shop_id`                  int(11)                not null     default 0 comment '店铺id',
  `pay_sn`                   varchar(32)            not null     default '' comment '支付流水号',
  `pay_code`                 varchar(20)            not null     default '' comment '支付宝:alipay,微信：？，...',
  `pay_code_alias`           varchar(20)            not null     default '' comment '支付宝:alipay,微信：？，...',
  `pay_act_name`             varchar(120)           not null     default '' comment '支付说明',
  `pay_act_time`             timestamp             null	default null comment '交易付款时间',
  `income_amount`            decimal(15, 2)         not null     default '0.00' comment '收入金额',
  `outcome_amount`           decimal(15, 2)         not null     default '0.00' comment '支出金额',
  `pay_fee`                  decimal(15, 2)         not null     default '0.00' comment '手续费',
  `total_surplus_amount`     decimal(20, 2)         not null     default '0.00' comment '余额',
  `trade_no`                 varchar(32)            not null     default '' comment '各平台交易号',
  `order_sn`                 varchar(32)            not null     default '' comment '网站订单号',
  `order_comp_status`        tinyint(1)     		not null     default '0' comment '订单完成状态1：已完成，2:未完成',
  `update_order_status_time` timestamp            null 	default null comment '更新订单状态时间',
  `pay_type`                 tinyint(2)             not null     default '0' comment '支付类型 1:收入，2：支出',
  `pay_type_name`            varchar(32)            not null     default '' comment '支付类型名称',
  `remark`                   text comment '自定义备注',
  `no_settle_flag`           tinyint(2)             not null     default '0' comment '是否参与结算 0:参入，1：不参与',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `pay_sn` (`pay_sn`),
  key `pay_code` (`pay_code`),
  key `trade_no` (`trade_no`),
  key `order_sn` (`order_sn`),
  key `pay_type` (`pay_type`),
  key (`shop_id`)
);

-- -- 用户收藏
-- drop table if exists `b2c_user_collection`;
create table `b2c_user_collection` (
  `id`         int(11)      not null  auto_increment,
  `user_id`    int(11)      not null,
  `goods_id`   int(11)      not null,
  `shop_id`    int(11)      not null  default 0 comment '店铺id',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key (`shop_id`)
);

-- -- 退回订单表
-- drop table if exists `b2c_return_order`;
create table `b2c_return_order` (
  `ret_id`                  int(11)         not null  auto_increment,
  `order_id`                int(11)         not null  default '0',
  `order_sn`                varchar(30)    	not null  default '',
  `return_order_sn`         varchar(30) 	default ''  comment '退款单号',
  `shop_id`                 int(11)        	not null  default 0 comment '店铺id',
  `user_id`                 int(11)         not null  default '0',
  `goods_id`                int(11)         not null  default '0',
  `refund_status`           tinyint(1)     	not null  default '1' comment '1是审核中，2是通过审核，3退货没通过审核，4买家提交物流 或 仅退款申请，5：退款退货成功，6是拒绝退款退货',
  `money`                   decimal(10, 2)  not null  default '0.00' comment '退款商品金额',
  `shipping_fee`            decimal(10, 2)  not null  default '0.00' comment '退运费金额',
  `return_type`             tinyint(1)              not null  default '0' comment '退款类型,0:只退款，1:退货又退款',
  `reason`                  varchar(191)            not null  default '' comment '退款理由',
  `return_desc`             text                    comment '退款说明',
  `shipping_type`           varchar(191)            not null  default '' comment '快递类型',
  `shipping_no`             varchar(50)             not null  default '' comment '快递单号',
  `goods_images`            text comment '商品图片',
  `voucher_images`          text comment '凭证图片',
  `phone`                   varchar(12)             not null  default '' comment '电话号码',
  `apply_time`              timestamp            null   default null comment '退货且退货提交审核时间，对应refund_status=1',
  `apply_pass_time`         timestamp             null 	default null comment '审核通过时间，对应refund_status=2',
  `apply_not_pass_time`     timestamp             null 	default null comment '审核未通过时间，对应refund_status=3',
  `shipping_or_refund_time` timestamp             null 	default null comment '只退款时为退款申请时间，退货又退款时为提交物流信息时间，对应refund_status=4',
  `refund_success_time`     timestamp             null 	default null comment '退款成功时间，对应refund_status=5',
  `refund_refuse_time`      timestamp             null 	default null comment '退款拒绝时间，对应refund_status=6',
  `refund_cancel_time`      timestamp             null 	default null comment '退款撤销时间，对应refund_status=7',
  `apply_not_pass_reason`   varchar(1000)           default null comment '审核未通过原因',
  `refund_refuse_reason`    varchar(1000)           default null comment '退款拒绝原因',
  `return_address`          varchar(1000)           not null  default '' comment '退货地址',
  `merchant_telephone`      varchar(12)             not null  default '' comment '商家电话',
  `consignee`               varchar(32)             not null  default '' comment '收货人',
  `zip_code`                varchar(10)             not null  default '' comment '邮编',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`ret_id`),
  key `order_sn` (`order_sn`)
);


-- -- --  退款日志记录，优先级卡余额、用户余额、积分抵扣、支付额
-- drop table if exists `b2c_refund_amount_record`;
create table `b2c_refund_amount_record` (
  `rec_id`       int(11)         	not null      auto_increment,
  `order_sn`     varchar(30)    		not null default '',
  `user_id`      int(11)        		not null default '0',
  `refund_field` varchar(20) comment '订单退款字段：member_card_balance,score_discount,money_paid,use_account',
  `refund_money` decimal(10, 2)  		not null default '0.00' comment '退款金额',
  `refund_time`  timestamp        		default current_timestamp comment '订单退款时间',
  `ret_id`       int(11)         	not null comment 'b2c_return_order的ret_id',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`rec_id`),
  key `order_sn` (`order_sn`),
  key `ret_id` (`ret_id`)
);

-- -- 部分商品发货表
-- drop table if exists `b2c_part_order_goods_ship`;
create table `b2c_part_order_goods_ship` (
  `rec_id`         int(11)        	not null  auto_increment,
  `shop_id`        int(11)       		not null default 0 comment '店铺id',
  `order_goods_id` int(11)        		not null default '0' comment '订单商品表的id',
  `order_sn`       varchar(20)     		not null default '',
  `batch_no`       varchar(120)    		not null default '',
  `goods_id`       mediumint(8)   		not null default '0',
  `goods_name`     varchar(120)    		not null default '',
  `product_id`     mediumint(8)   		not null default '0',
  `send_number`    smallint(5)    		not null default '1',
  `goods_attr`     text,
  `shipping_no`    varchar(50)     		not null default '' comment '快递单号',
  `shipping_name`  varchar(120)    		not null default '' comment '快递名称',
  `shipping_id`    tinyint(3)      		not null default '0' comment '快递id',
  `shipping_time`  timestamp       null	default current_timestamp comment '部分发货时间',
  `confirm_time`   timestamp       null	default null comment '订单确收收货时间',
  `create_time`    timestamp    	default current_timestamp,
  `update_time`    timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`rec_id`),
  key `order_goods_id` (`order_goods_id`),
  key `order_sn` (`order_sn`),
  key `batch_no` (`batch_no`),
  key `goods_id` (`goods_id`)
);
-- -- 退货商品表
-- drop table if exists `b2c_return_order_goods`;
create table `b2c_return_order_goods` (
  `id`           int(11)       	not null  auto_increment,
  `shop_id`      int(11)      		not null default 0 comment '店铺id',
  `rec_id`       int(11)  							comment '订单商品表的id',
  `ret_id`       int(11)  							comment '退货记录表的id',
  `order_sn`     varchar(20)   		not null default '',
  `goods_id`     mediumint(8)  		not null default '0',
  `goods_name`   varchar(120)  		not null default '',
  `product_id`   mediumint(8)  		not null default '0',
  `goods_number` smallint(5)   		not null default '1' comment '退货商品数量',
  `market_price` decimal(10, 2)		not null default '0.00',
  `goods_price`  decimal(10, 2)		not null default '0.00',
  `goods_attr`   text,
  `send_number`  smallint(5)   		not null default '0' comment '发货商品数量',
  `goods_img`    varchar(191)  		not null default '',
  `success`      tinyint(1)   		not null default 1 comment '0代表退货申请被拒绝，1代表正在退货中，2代表退货成功',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `rec_id` (`rec_id`),
  key `ret_id` (`ret_id`),
  key `order_sn` (`order_sn`),
  key `goods_id` (`goods_id`),
  key (`shop_id`)
);

-- -- 满折满减活动列表
-- drop table if exists `b2c_mrking_strategy`;
create table `b2c_mrking_strategy` (
  `id`                 mediumint(8)  not null auto_increment,
  `shop_id`            int(11)      		not null default 0 comment '店铺id',
  `act_name`           varchar(120)       	not null default '',
  `type`               tinyint(1)           not null default 0 comment '类型,1每满减 2满件 3满折 4满赠',
  `start_time`         timestamp           null	default null,
  `end_time`           timestamp           null	default null,
  `recommend_goods_id` text                 comment '指定商品可用',
  `recommend_cat_id`   text                	comment '指定分类可用',
  `recommend_brand_id` text                	comment '指定品牌可用',
  `act_type`           tinyint(1)           not null default 0 comment '活动类型，0-选中项参与活动；1-选中项不参与活动',
  `del_flag`           int(1)              	not null default 0,
  `strategy_priority`  int(11)            	not null default 0 comment '促销活动优先级',
  `card_id`            text 					comment '专属会员卡',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key (`shop_id`),
  key mrking_strategy_delflag(`del_flag`)
);


-- -- 瞒折满减优惠规则表
-- drop table if exists `b2c_mrking_strategy_condition`;
create table `b2c_mrking_strategy_condition` (
  `id`           mediumint(8)  not null  auto_increment,
  `shop_id`      int(11)           	not null default 0 comment '店铺id',
  `strategy_id`  int(11)           	not null default 0,
  `full_money`   decimal(10, 2)    	not null default '0.00' comment '满多少金额',
  `reduce_money` decimal(10, 2)    	not null default '0.00' comment '减多少钱',
  `amount`       int(11)       		not null default '0' comment '满几件',
  `discount`     decimal(10, 2)    	not null default '0.00' comment '打几折',
  `gift`         text                   comment '赠品',
  `gift_left`    text                   comment '赠品剩余数量',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 个人中心说明管理
-- drop table if exists `b2c_user_explain`;
create table `b2c_user_explain` (
  `id`          smallint(5)  		not null  auto_increment,
  `shop_id`     int(11) 			default 0 comment '店铺id',
  `text`        text,
  `type`        tinyint(1)        	default '0' comment '1-余额，2-级别，3-成长值，4-积分',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  unique key `type` (`type`)
);

-- --  配置信息表 店铺或平台的配置
-- drop table if exists `b2c_shop_cfg`;
create table `b2c_shop_cfg` (
  `rec_id`  smallint(5)  not null auto_increment,
  `k`       varchar(191)	default '',
  `v`       text,
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`rec_id`)
);

-- -- 商品评价表
-- drop table if exists `b2c_comment_goods`;
create table `b2c_comment_goods` (
  `id`            int(11)       not null auto_increment,
  `shop_id`       int(11)       not null comment '店铺id',
  `user_id`       int(11)       not null comment '用户id',
  `commstar`      tinyint(1)    not null comment '评价星级',
  `user_score`    int(11)       not null comment '评价可得积分',
  `anonymousflag` tinyint(1)    not null comment '匿名状态 0.未匿名；1.匿名',
  `commtag`       varchar(100)  	default '' comment '评价标签' ,
  `goods_id`      int(11)       not null comment '商品id',
  `order_sn`      varchar(20)   not null comment '订单编号' ,
  `comm_note`     varchar(255)  not null comment '评论内容' ,
  `comm_img`      varchar(1000) 	default null comment '评论图片' ,
  `create_time`   timestamp    		default current_timestamp,
  `update_time`   timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  `flag`          tinyint(1)    	default '0' comment '0:未审批,1:审批通过,2:审批未通过',
  `del_flag`      tinyint(1)    	default '0' comment '1:删除',
  `is_shop_add`       tinyint(1)    not null default '0' comment '是否商家增加：0不是，1是',
  `bogus_username`    varchar(32)   not null default '' comment '用户名称：商家添加时使用',
  `bogus_user_avatar` varchar(100)  not null default '' comment '用户头像：商家添加时使用',
  primary key (`id`),
  index `shop_id` (`shop_id`)
);

-- -- 商品评价回复表
-- drop table if exists `b2c_comment_goods_answer`;
create table `b2c_comment_goods_answer` (
  `answer_id`   int(11)    not null auto_increment,
  `comment_id`  int(11)    not null comment '回复的商品评论id',
  `content`     text  comment '回复内容',
  `del_flag`    tinyint(1) 			not null default '0' comment '1:删除',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`answer_id`),
  key `comment_id` (`comment_id`)
);

-- 会员标签
-- drop table if exists `b2c_tag`;
create table `b2c_tag` (
  `tag_id`   		 int(11)   		not null auto_increment,
  `tag_name` 		 varchar(20)   	default null,
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  `is_delete`		tinyint(1)      DEFAULT 0 NOT NULL  COMMENT "0未删除，1已删除",
  primary key (`tag_id`),
  unique key `tag_name` (`tag_name`)
);
-- 会员关联标签表
-- drop table if exists `b2c_user_tag`;
create table `b2c_user_tag` (
  `user_id`  int(11)   not null,
  `tag_id`   int(11)   not null,
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  `is_delete`		 tinyint(1)     DEFAULT 0 NOT NULL  COMMENT "0未删除，1已删除",
  primary key (`user_id`, `tag_id`),
  unique key `user_tag` (`user_id`, `tag_id`)
);
-- -- 会员绑定的会员卡信息
-- drop table if exists `b2c_user_card`;
create table `b2c_user_card` (
  `user_id`         int(11)                	not null comment '会员id',
  `card_id`         int(11)                	not null comment '会员卡id',
  `flag`            tinyint(1)             	not null	default '0' comment '0:正常，1:删除',
  `card_no`         varchar(32) 			not null default '' not null comment '会员卡号',
  `expire_time`     timestamp              null	default null,
  `is_default`      tinyint(1)             	not null default '0' comment '1:默认会员卡',
  `money`           decimal(10, 2)        	not null default '0.00' comment '卡余额',
  `surplus`         int(11)              	not null default '0' comment '卡剩余次数',
  `activation_time` timestamp            		null default null comment '激活时间',
  `exchang_surplus`	int(11)        	not null default '0' comment '卡剩余兑换次数',
  `create_time`      timestamp    	default current_timestamp,
  `update_time`      timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  unique key `card_no` (`card_no`)
);

-- -- 会员卡信息
-- drop table if exists `b2c_member_card`;
create table `b2c_member_card` (
  `id`                int(11)      not null                   auto_increment,
  `card_name`         varchar(20)  not null,
  `card_type`         tinyint(1)   not null                   default '0' comment '0:普通会员卡，1:次卡,2:登记卡',
  `bg_type`           tinyint(1)   not null                   default '0' comment '0:背景色，1:背景图',
  `bg_color`          char(10)                                default null comment '背景色',
  `bg_img`            varchar(100)                            default null comment '背景图',
  `discount`          decimal(10, 2)                          default null comment '折扣',
  `sorce`             int(11)                                 default null comment '开卡送积分',
  `buy_score`         text comment '购物送积分策略json数据',
  `expire_type`       tinyint(1)   not null                   default '0' comment '0:固定日期 1：自领取之日起 2:不过期',
  `start_time`        timestamp    							            null  default null comment '开始日期',
  `end_time`          timestamp null default null comment '结束日期',
  `receive_day`       int(11)                                 default null comment '领取之日起n',
  `date_type`         tinyint(1)                              default null comment '0:日，1:周 2: 月',
  `activation`        tinyint(1)                              default null comment '0：不用激活，1：需要激活',
  `receive_code`      char(10)                                default null comment '领取码',
  `desc`              text comment '使用须知',
  `mobile`            char(11)                                default null comment '联系电话',
  `create_time`       timestamp                            	null  default current_timestamp comment '添加时间',
  `update_time`       timestamp                          	   null default current_timestamp on update current_timestamp comment '最后修改时间',
  `flag`              tinyint(1)   not null                   default '1' comment '1:使用中，2:停止使用',
  `send_money`        int(11)                                 default null comment '开卡送钱',
  `charge_money`      text comment '充值活动策略',
  `use_time`          int(11)                                 default null comment '使用时间 1工作日 2双休 0不限制',
  `store_list`        varchar(191) not null                   default '{}' comment '可用门店',
  `count`             int(11)                                 default null comment '卡总次数',
  `del_flag`          tinyint(1)   not null                        default '0' comment '1为删除状态',
  `grade`             char(10)     not null                        default '' comment '等级卡的等级',
  `grade_condition`   varchar(200) not null                        default '' comment '等级卡的条件',
  `activation_cfg`    varchar(200) default null comment '激活信息配置',
  `examine`           tinyint(1)   	not null                    default '0' comment '是否审核 0不审核 1审核',
  `discount_goods_id` varchar(299)  default null comment '折扣商品id',
  `discount_cat_id`   varchar(299)  default null comment '折扣平台分类id',
  `discount_sort_id`  varchar(299)  default null comment '折扣商家分类id',
  `discount_is_all`   tinyint(1)                      not null default '1' comment '折扣商品范围： 0:部分商品，1:全部商品',
  `is_pay`            tinyint(1)                      not null default '0' comment '0:直接购买 1:需要购买 2: 需要领取码',
  `pay_type`          tinyint(1)                      not null default '0' comment '0:不支持现金购买，1:支持现金购买',
  `pay_fee`           decimal(10,2)                   not null   default '0.00' comment '购买资金',
  `pay_own_good`      tinyint(1)                      not null   default '0' comment '是否专属购买商品 0不是 1是',
  `receive_action` 	  tinyint(1) 	not null default 0     comment '领取方式 1:领取码 2：卡号+密码',
  `is_exchang`        tinyint(1)                  not null 	default '0' comment '0不可 1部分 2全部',
  `store_use_switch`  tinyint(1)                  not null 	default '0' comment '可否在门店使用  0可以 1不可以',
  `exchang_goods`     varchar(299)                          default null comment '可兑换商品id',
  `exchang_freight`   tinyint(1)                            default null comment '运费策略 0免运费 1使用商品运费策略',
  `exchang_count`     int(11)                               default null comment '允许兑换次数',
  primary key (`id`)
);

-- -- 门店分组
-- drop table if exists `b2c_store_group`;
create table `b2c_store_group` (
  `group_id`   int(11)     not null auto_increment,
  `group_name` varchar(20) not null,
  `create_time`		timestamp       default current_timestamp,
  `update_time` 	timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`group_id`),
  unique key `group_name` (`group_name`)
);

-- -- 门店商品管理
-- drop table if exists `b2c_store_goods`;
create table `b2c_store_goods` (
  `store_id`       int(11)                      not null,
  `goods_id`       int(11)                      not null,
  `prd_id`         int(11)                      not null,
  `prd_sn`         varchar(30)                  not null,
  `product_number` int 				not null	default 0 		comment '库存',
  `product_price`  decimal(10, 2) 	not null	default 0.00 	comment '价格',
  `is_sync`        tinyint(1) 		not null	default 0 		comment '是否已同步',
  `is_on_sale`     tinyint(1)                   not null default '0' comment '''是否在售，1在售，0下架''',
  `create_time`		timestamp       default current_timestamp,
  `update_time` 	timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  `flag`           tinyint(1)                   not null default '0' comment '1:初始化数据，0:无效数据',
  primary key (`store_id`, `goods_id`, `prd_id`, `is_on_sale`)
);

-- --  门店信息表
-- drop table if exists `b2c_store`;
create table `b2c_store` (
  `store_id`       int(11)        not null auto_increment,
  `store_name`     varchar(60)    not null comment '门店名称',
  `manager`        varchar(30)    not null comment '负责人',
  `mobile`         varchar(20)    not null comment '联系电话',
  `store_imgs`     text comment '图片',
  `business_state` tinyint(1)     not null default '1' comment '营业状态1:营业，0:关店',
  `business_type`  tinyint(1)              default '1' comment '营业时间1：每天，0：工作日',
  `opening_time`   varchar(5)              default null comment '开门时间',
  `close_time`     varchar(5)              default null comment '打烊时间',
  `province_code`  varchar(6)              default null comment '省',
  `city_code`      varchar(6)              default null comment '市',
  `district_code`  varchar(6)              default null comment '区',
  `latitude`       varchar(16)             default null comment '纬度',
  `longitude`      varchar(16)             default null comment '经度',
  `address`        varchar(100)            default null comment '详细地址',
  `group`          int(11)                 default null comment '分组',
  `service`        text comment '服务',
  `content`        mediumtext comment '介绍',
  `pos_shop_id`    int 				not null	default 0 comment 'pos店铺id',
  `create_time`		timestamp       default current_timestamp,
  `update_time` 	timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  `auto_pick`      smallint(1)    null     default '0' comment '设定自提',
  `del_flag`       tinyint(1)     null     default '0' comment '1为删除状态',
  primary key (`store_id`)
);

-- -- 发券记录
-- drop table if exists `b2c_give_voucher`;
create table `b2c_give_voucher` (
  `id`            int(11)               not null auto_increment,
  `act_name`      varchar(50) 			not null comment '活动名称',
  `number`        int(6)               	default null comment '参与人数',
  `have_pay`      int(4)               	default null comment '有交易记录',
  `no_pay`        int(4)              	default null comment '无交易记录',
  `max_count`     int(20)              	default null comment '购买次数大于',
  `min_count`     int(20)             	default null comment '购买次数小于',
  `card_id`       text comment '会员卡',
  `tag_id`        text comment '标签',
  `act_id`        	int(11)            	not null comment '优惠券',
  `max_ave_price` 	decimal(10, 2)  	default null comment '均价大于',
  `min_ave_price` 	decimal(10, 2)    	default null comment '均价小于',
  `user`          	text 						comment '手动添加会员',
  `send_condition` 	text     					comment '筛选发放人条件',
  `send_status`   	tinyint(1) 		default '0' comment '发放状态:0未发放，1已发放',
  `send_action` 	tinyint(1) 		default '1' comment '发放类型:0立即发放，1定时发放',
  `start_time` 		timestamp 	null	default null comment '发放开始时间',
  `create_time`		timestamp       default current_timestamp,
  `update_time` 	timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);


-- -- 店铺服务分类
-- drop table if exists `b2c_store_service_category`;
create table `b2c_store_service_category` (
  `cat_id`      	int(11)  not null auto_increment,
  `cat_name`    	varchar(90)          not null 	default '',
  `store_id`    	int(11)              not null,
  `create_time`		timestamp       default current_timestamp,
  `update_time` 	timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`cat_id`),
  unique key `cat_name` (`cat_name`, `store_id`)
);

-- -- 门店服务信息
-- drop table if exists `b2c_store_service`;
create table `b2c_store_service` (
  `id`                   int(11)               not null auto_increment,
  `store_id`             int(11)               not null comment '门店id',
  `service_name`         varchar(120)          not null default '' comment '服务名称',
  `service_sn`           varchar(60)                    default '' comment '服务编码',
  `sale_num`             int(11)               not null default '0' comment '销量',
  `service_price`        decimal(10, 2)        not null default '0.00' comment '服务价格',
  `service_subsist`      decimal(10, 2)        not null default '0.00' comment '预约订金',
  `cat_id`               int(11)                        default null comment '服务分类',
  `service_shelf`        tinyint(1)            not null default '1' comment '上下架 1:上架，0:下架',
  `service_img`          text comment '分类主图',
  `start_date`           date                           default null comment '可服务日期开始时间',
  `end_date`             date                           default null comment '可服务日期结束时间',
  `start_period`         varchar(10)                    default null comment '开始服务时段',
  `end_period`           varchar(10)                    default null comment '结束服务时段',
  `service_duration`     int(11)                        default null comment '服务时长',
  `service_type`         tinyint(1) 			not null default 0  comment '服务类型:0无技师1有技师',
  `services_number`      int(11)                        default null comment '服务数量',
  `tech_services_number` int(11)               null comment '技师单时段服务数量',
  `content`              mediumtext comment '服务描述',
  `create_time`			timestamp       default current_timestamp,
  `update_time` 		timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  `charge_resolve`       varchar(255)          null comment '收费说明',
  `del_flag`             tinyint(1)            not null	default '0' comment '1为删除状态',
  primary key (`id`)
);

-- -- --  退款记录表
-- drop table if exists `b2c_order_refund_record`;
create table `b2c_order_refund_record` (
  `id`               mediumint(12)  not null auto_increment,
  `ret_id`           int(11)                not null default 0 comment '订单退货请求id',
  `shop_id`          int(11)                not null default 0 comment '店铺id',
  `refund_sn`        varchar(32)            not null default '' comment '退款流水号',
  `pay_sn`           varchar(32)            not null default '' comment '支付流水号',
  `order_sn`         varchar(20)            not null default '' comment '订单编号',
  `apply_user`       varchar(60)            not null default '',
  `pay_code`         varchar(20)            not null default '' comment '支付宝:alipay,微信：？，...',
  `refund_amount`    decimal(20, 2)         not null default '0.00' comment '退款金额',
  `refund_time`      timestamp null default null comment '退款时间',
  `mobile`           varchar(32)            not null default '',
  `shop_name`        varchar(50)                     default '' comment '店铺名称',
  `deal_status`      tinyint(1)     not null default 0 comment '处理状态，0:退款中，1：退款完成，2：退款失败',
  `op_deal_status`   tinyint(1)     not null default 0 comment '人工处理状态，0:待确认，1：已确认待退款，2：退款完成',
  `deal_status_name` varchar(64)            not null default '',
  `deal_remark`      varchar(512)           not null default '',
  `trans_sn`         varchar(64)            not null default '' comment '转账流水号',
  `finished_time`    timestamp null default null comment '出账操作时间',
  `create_time`		 timestamp      default current_timestamp,
  `update_time` 	 timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  `remark1`          text 					comment '自定义备注  建议用于存储原始数据',
  `is_offline`       tinyint(1) 			not null default 0    comment '是否线下处理',
  primary key (`id`),
  unique key (`refund_sn`),
  key (`apply_user`),
  key (`pay_code`),
  key (`refund_amount`),
  key (`shop_id`),
  key (`refund_time`),
  key (`ret_id`)
) auto_increment = 1000001;

-- -- 服务订单表
-- drop table if exists `b2c_service_order`;
create table `b2c_service_order` (
  `order_id`          mediumint(8)    not null auto_increment comment '订单id',
  `store_id`          int(11)                 not null comment '门店id',
  `order_sn`          varchar(20)             not null default '' comment '订单编号',
  `user_id`           mediumint(8)    		  not null default '0' comment '用户id',
  `order_status`      tinyint(1)              not null default '0' comment '订单状态 0：待服务，1：已取消，2：已完成',
  `order_status_name` varchar(32) 			  not null default '' comment '订单状态名称',
  `subscriber`        varchar(60)             not null default '' comment '预约人姓名',
  `mobile`            varchar(60)             not null default '' comment '手机号',
  `service_id`        int(11)                 not null default '0' comment '服务id',
  `technician_id`     mediumint(8)            not null default 0 comment '预约技师id',
  `technician_name`   varchar(20)             not null default '' comment '技师名称',
  `service_date`      varchar(18) 			  not null default ''  comment '预约日期',
  `service_period`    varchar(20)                      default null comment '预约时段',
  `add_message`       varchar(191)                     default null comment '客户留言',
  `admin_message`     varchar(191)                     default null comment '商家备注',
  `verify_code`       varchar(191)            not null default '' comment '核销自提码',
  `verify_admin`      varchar(30)             not null default '' comment '核销人',
  `pay_code`          varchar(30)             not null default '' comment '支付代号',
  `pay_name`          varchar(120)            not null default '' comment '支付名称',
  `pay_sn`            varchar(32)             not null default '' comment '支付流水号',
  `money_paid`        decimal(10, 2)          not null default '0.00' comment '订单应付金额',
  `discount`          decimal(10, 2)          not null default '0.00' comment '券抵扣金额',
  `coupon_id`         mediumint(8)    		  not null default '0' comment '优惠券id',
  `order_amount`      decimal(10, 2)          not null default '0.00' comment '订单总金额',
  `pay_time`          timestamp                   null  default null comment '支付时间',
  `cancelled_time`    timestamp                   null default null comment '取消时间',
  `finished_time`     timestamp                   null default null comment '订单完成时间',
  `prepay_id`         varchar(191)            null comment '微信支付id，用于发送模板消息',
  `del_flag`          tinyint(1)              not null     default '0' comment '删除',
  `verify_type`       tinyint(1)              not null     default '0' comment '核销方式 0是店家核销 1是用户',
  `cancel_reason`     varchar(200)            null comment '取消原因',
  `type`              tinyint(1)              not null default '0' comment '创建类型 0用户创建 1后台',
  `verify_pay`        tinyint(1)              not null     default '0' comment '核销支付方式 0门店买单 1会员卡 2余额',
  `ali_trade_no`      varchar(60)             not null     default '' comment '支付宝交易单号',
  `create_time`		 timestamp      default current_timestamp,
  `update_time` 	 timestamp     	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`order_id`),
  key `order_sn` (`order_sn`)
);

-- -- 二维码存储表
-- drop table if exists `b2c_code`;
create table `b2c_code` (
  `code_id`    mediumint(11)     not null auto_increment comment '二维码id',
  `type`       smallint(2)              not null default '0' comment '分类：1店铺，2商品，3服务，4会员卡，5优惠券',
  `param_id`   varchar(32) default '0'  not null comment '对应的参数id',
  `type_url`   varchar(100)             not null default '' comment 'type对应的app页面地址',
  `qrcode_img` varchar(200)             not null default '' comment '二维码位置',
  `flag`       tinyint(1)               not null default '0' comment '标记位',
  `channel` 		varchar(20) 		not null default '0' comment '渠道分享码',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`code_id`)
);

-- -- 小程序访问日趋势
-- drop table if exists `b2c_mp_daily_visit`;
create table `b2c_mp_daily_visit` (
  `ref_date`          char(8)   not null comment '时间： 如： "20180313"',
  `session_cnt`       int(11)   not null default '0' comment '打开次数',
  `visit_pv`          int(11)   not null default '0' comment '访问次数',
  `visit_uv`          int(11)   not null default '0' comment '访问人数',
  `visit_uv_new`      int(11)   not null default '0' comment '新用户数',
  `stay_time_uv`      float     not null default '0' comment '人均停留时长 (浮点型，单位：秒)',
  `stay_time_session` float     not null default '0' comment '次均停留时长 (浮点型，单位：秒)',
  `visit_depth`       float     not null default '0' comment '平均访问深度 (浮点型)',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  key `ref_date` (`ref_date`) using btree
);

-- -- 小程序概况趋势
-- drop table if exists `b2c_mp_summary_trend`;
create table `b2c_mp_summary_trend` (
  `ref_date`    char(8)   not null comment '日期',
  `visit_total` int(11)   not null default '0' comment '总访问量',
  `share_pv`    int(11)   not null default '0' comment '转发次数',
  `share_uv`    int(11)   not null default '0' comment '转发人数',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  key `ref_date` (`ref_date`) using btree
);

-- -- 周趋势
-- drop table if exists `b2c_mp_weekly_visit`;
create table `b2c_mp_weekly_visit` (
  `ref_date`          char(20)  not null comment '时间，如："20180306-20180312"',
  `session_cnt`       int(11)   not null default '0' comment '打开次数',
  `visit_pv`          int(11)   not null default '0' comment '访问次数',
  `visit_uv`          int(11)   not null default '0' comment '访问人数',
  `visit_uv_new`      int(11)   not null default '0' comment '新用户数',
  `stay_time_uv`      float     not null default '0' comment '人均停留时长 (浮点型，单位：秒)',
  `stay_time_session` float     not null default '0' comment '次均停留时长 (浮点型，单位：秒)',
  `visit_depth`       float     not null default '0' comment '平均访问深度 (浮点型)',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间'
);

-- -- 月趋势
-- drop table if exists `b2c_mp_monthly_visit`;
create table `b2c_mp_monthly_visit` (
  `ref_date`          char(6)   not null comment '时间，如："201803"',
  `session_cnt`       int(11)   not null default '0' comment '打开次数',
  `visit_pv`          int(11)   not null default '0' comment '访问次数',
  `visit_uv`          int(11)   not null default '0' comment '访问人数',
  `visit_uv_new`      int(11)   not null default '0' comment '新用户数',
  `stay_time_uv`      float     not null default '0' comment '人均停留时长 (浮点型，单位：秒)',
  `stay_time_session` float     not null default '0' comment '次均停留时长 (浮点型，单位：秒)',
  `visit_depth`       float     not null default '0' comment '平均访问深度 (浮点型)',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间'
);

-- -- 访问分布
-- drop table if exists `b2c_mp_distribution_visit`;
create table `b2c_mp_distribution_visit` (
  `ref_date` char(8)   not null comment '时间，如："20180313"',
  `list`     text comment '存入所有类型的指标情况',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  key `ref_date` (`ref_date`) using btree
);

-- -- 日留存
-- drop table if exists `b2c_mp_daily_retain`;
create table `b2c_mp_daily_retain` (
  `ref_date`     char(8)   not null comment '时间，如："20180313"',
  `visit_uv_new` text comment '新增用户留存',
  `visit_uv`     text comment '活跃用户留存',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  key `ref_date` (`ref_date`) using btree
);

-- -- 周留存
-- drop table if exists `b2c_mp_weekly_retain`;
create table `b2c_mp_weekly_retain` (
  `ref_date`     char(20)  not null comment '时间，如："20180306-20180312"',
  `visit_uv_new` text comment '新增用户留存',
  `visit_uv`     text comment '活跃用户留存',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间'
);

-- -- 月留存
-- drop table if exists `b2c_mp_monthly_retain`;
create table `b2c_mp_monthly_retain` (
  `ref_date`     char(6)   not null comment '时间，如："201803"',
  `visit_uv_new` text comment '新增用户留存',
  `visit_uv`     text comment '活跃用户留存',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间'
);

-- -- 访问页面
-- drop table if exists `b2c_mp_visit_page`;
create table `b2c_mp_visit_page` (
  `ref_date`         char(8)      not null comment '时间，如："20170313"',
  `page_path`        varchar(200) not null,
  `page_visit_pv`    int(11)               default null comment '访问次数',
  `page_visit_uv`    int(11)               default null comment '访问人数',
  `page_staytime_pv` float                 default null comment '人均停留时长 (浮点型，单位：秒)',
  `entrypage_pv`     int(11)               default null comment '进入页次数',
  `exitpage_pv`      int(11)               default null comment '退出页次数',
  `page_share_pv`    int(11)               default null comment '转发次数',
  `page_share_uv`    int(11)               default null comment '转发人数',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  key `ref_date` (`ref_date`) using btree,
  key `page_path` (`page_path`) using btree,
  key `page_visit_pv` (`page_visit_pv`) using btree
);

-- -- 用户登录记录表,每小时存一条
-- drop table if exists `b2c_user_login_record`;
create table `b2c_user_login_record` (
  `id`            bigint(20)  		not null auto_increment,
  `user_id`       int 				default 0  not null   comment '登陆用户id',
  `user_ip`       varchar(64)     	default null comment '用户登录ip',
  `count`         int 				not null default 0	comment '每日登陆次数',
  `province_code` varchar(20)  default null comment '省',
  `province`      varchar(20)  default null comment '省',
  `city_code`     varchar(20)  default null comment '市',
  `city`          varchar(20)  default null comment '市',
  `district_code` varchar(20)  default null comment '区',
  `district`      varchar(20)  default null comment '区',
  `lat`           varchar(64)      default null comment '经度',
  `lng`           varchar(64)      default null comment '纬度',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `create_time` (`create_time`) using btree,
  key `district_create_time` (`create_time`, `district_code`) using btree
);

-- -- 发票表
-- drop table if exists `b2c_invoice`;
create table `b2c_invoice` (
  `id`             int(11)      not null  auto_increment,
  `user_id`        int(11)      not null comment '用户id',
  `type`           tinyint(1)   not null  default 0 comment '发票类型',
  `title`          varchar(191) not null comment '公司名称',
  `telephone`      varchar(191)           default null comment '公司电话',
  `taxnumber`      varchar(191)           default null comment '税号',
  `companyaddress` varchar(191)           default null comment '公司地址',
  `bankname`       varchar(191)           default null comment '银行名称',
  `bankaccount`    varchar(191)           default null comment '银行账号',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 服务技师表
-- drop table if exists `b2c_service_technician`;
create table `b2c_service_technician` (
  `id`                   mediumint(8)  not null auto_increment comment '技师id',
  `store_id`             int(11)        not null default '0',
  `technician_name`      varchar(100)          not null default '' comment '技师名称',
  `technician_mobile`    varchar(32)           not null default '' comment '技师电话',
  `bg_img_path`          varchar(191)          not null default '' comment '技师头像地址',
  `technician_introduce` varchar(200)          not null default '' comment '技师简介',
  `group_id`             int(11)          not null default '0' comment '技师分组',
  `service_type`         tinyint(2)            not null default '0' comment '技师服务项目：0所有，1部分',
  `service_list`         varchar(191)          not null default '{}' comment '当type=0是服务项目详情：array()',
  `remarks`              varchar(1024)         not null default '' comment '备注',
  `del_flag`             tinyint(1)            not null default '0' comment '0正常，1删除',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 服务技师分组表
-- drop table if exists `b2c_service_technician_group`;
create table `b2c_service_technician_group` (
  `group_id`   int(11)  not null auto_increment comment '分组id',
  `group_name` varchar(90)          not null default '' comment '分组名称',
  `store_id`   int(11)         not null comment '门店id',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  `del_flag`   smallint(1)          not null default '0' comment '0使用，1删除',
  primary key (`group_id`)
);

-- 页面装修 > 网页/小程序跳转表
-- drop table if exists `b2c_decorate_link`;
create table `b2c_decorate_link` (
  `id`          int(11)      not null auto_increment,
  `shop_id`     int(11)      not null,
  `link_action` tinyint(4)   not null default 0 comment '1: 网页跳转  2： 小程序跳转',
  `title`       varchar(50)  null comment '小程序名称',
  `path_name`   varchar(255) null comment '链接名称',
  `link_path`   varchar(255) not null comment '跳转链接',
  `appid`       varchar(100) 	default null comment '小程序appid',
  `del_flag`    tinyint(1)   not null default 0 comment '0:未删除，1:已删除',
  `del_time`    int(11)      not null default 0,
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  index `shop_decorate_link` (`shop_id`, `link_action`, `del_flag`)
);

-- -- 服务技师班次表
-- drop table if exists `b2c_service_schedule`;
create table `b2c_service_schedule` (
  `schedule_id`   tinyint(6)  not null auto_increment comment '排班id',
  `store_id`      int(11)      not null default '0',
  `schedule_name` varchar(32)         not null default '' comment '排班名称',
  `begcreate_time`    varchar(10)     not null default '' comment '开始时间',
  `end_time`      varchar(10)         not null default '' comment '结束时间',
  `del_flag`      tinyint(1)          not null default '0' comment '0正常，1删除',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`schedule_id`)
);

-- -- 服务技师排班表
-- drop table if exists `b2c_service_technician_schedule`;
create table `b2c_service_technician_schedule` (
  `id`            int(12)       not null auto_increment comment 'id',
  `store_id`      int(11)        not null default '0',
  `technician_id` mediumint(8)  not null comment '技师id',
  `work_date`     varchar(18)           not null default '' comment '工作日期',
  `schedule_id`   tinyint(6)            not null default '0' comment '排班id',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 分裂优惠券分享领取记录
-- drop table if exists `b2c_share_split`;
create table `b2c_share_split` (
  `id`          mediumint(8)  not null auto_increment,
  `user`        mediumint(8)  not null default '0' comment '分享的user_id',
  `user_id`     mediumint(8)  not null default '0' comment '分享领取的user_id',
  `act_id`      mediumint(8)  not null default '0' comment '分裂优惠券id',
  `order_sn`    varchar(20)           not null default '' comment '',
  `share_limit` int(11)               not null default 0 comment '可分享个数',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `user` (`user`)
);

-- -- 服务评价表
-- drop table if exists `b2c_comment_service`;
create table `b2c_comment_service` (
  `id`            int(11)               not null auto_increment,
  `shop_id`       int(11)               not null comment '店铺id',
  `store_id`      int(11)               not null comment '门店id',
  `technician_id` mediumint(8)  not null comment '技师id',
  `user_id`       int(11)               not null comment '用户id',
  `commstar`      tinyint(1)            not null comment '评价星级',
  `user_score`    int(11)               not null comment '评价可得积分',
  `anonymousflag` tinyint(1)            not null comment '匿名状态 0.未匿名；1.匿名',
  `commtag`       varchar(100)          not null default '' comment '评价标签' ,
  `service_id`    int(11)               not null comment '服务id',
  `order_sn`      varchar(20)           not null comment '订单编号' ,
  `comm_note`     varchar(255)          not null comment '评论内容' ,
  `comm_img`      varchar(1000)        	default null comment '评论图片' ,
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  `flag`          tinyint(1)            not null default '0' comment '0:未审批,1:审批通过,2:审批未通过',
  `del_flag`      tinyint(1)            not null default '0' comment '1:删除',
  primary key (`id`),
  index `shop_id` (`shop_id`)
);

-- -- 门店买单订单表
-- drop table if exists `b2c_store_order`;
create table `b2c_store_order` (
  `order_id`            mediumint(8)         not null auto_increment comment '订单id',
  `store_id`            int(11)                      not null default '0' comment '门店id',
  `order_sn`            varchar(20)                  not null default '' comment '订单编号',
  `user_id`             mediumint(8)         not null default '0' comment '用户id',
  `order_status`        tinyint(1)                   not null default '0' comment '订单状态',
  `order_status_name`   varchar(32)                           default null comment '订单状态名称',
  `invoice_id`          int(11)                      not null default '0' comment '发票id',
  `invoice_detail`      text comment '发票内容：json存储',
  `add_message`         varchar(191)                 not null default '' comment '客户留言',
  `pay_code`            varchar(30)                           default null comment '支付代号',
  `pay_name`            varchar(120)                          default null comment '支付名称',
  `prepay_id`           varchar(191)                          default null comment '微信支付id，用于发送模板消息',
  `pay_sn`              varchar(32)                           default null comment '支付流水号',
  `money_paid`          decimal(10, 2)               not null default '0.00' comment '订单应付金额',
  `member_card_no`      varchar(32) 				 not null default '0' comment '会员卡no',
  `member_card_redunce` decimal(10, 2)               not null default '0.00' comment '会员卡抵扣金额',
  `member_card_balance` decimal(10, 2) 				 not null default '0.00' comment '会员卡消费金额',
  `score_discount`      decimal(10, 2)               not null          default '0.00' comment '积分抵扣金额',
  `use_account`         decimal(10, 2)               not null          default '0.00' comment '用户消费余额',
  `order_amount`        decimal(10, 2)               not null          default '0.00' comment '订单总金额',
  `pay_time`            timestamp                   null default null comment '支付时间',
  `seller_remark`       varchar(512)                 not null     	default '' comment '卖家备注',
  `star_flag`           tinyint(1)                   not null    	default '0' comment '标星订单：0 未标星 1 标星',
  `del_flag`            tinyint(1)                   not null	  	default '0' comment '删除',
  `ali_trade_no`        varchar(60)                  not null     	default '' comment '支付宝交易单号',
  `create_time`			timestamp      default current_timestamp,
  `update_time` 		timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`order_id`)
);

-- -- 模板消息form_id表
-- drop table if exists `b2c_mp_template_form_id`;
create table `b2c_mp_template_form_id` (
  `rec_id`              mediumint(8)  not null auto_increment comment '记录id',
  `form_id`             varchar(255)  not null default '' comment '小程序提交form_id',
  `user_id`             mediumint(8)  not null default '0' comment '用户id',
  `open_id`             varchar(255)          not null default '' comment '用户openid',
  `use_state`           tinyint(1)            not null default '0' comment '使用状态，0未使用，1冻结 2 使用',
  `status`              tinyint(1)            not null default 0 comment '1: 发送成功  0：未知',
  `is_visit`            tinyint(1)            not null default 0 comment '是否已点击访问 1：是 0： 否',
  `template_type`       tinyint(1)            not null default '0' comment '使用类型，0 初始 1 订单 2 预约 3 优惠券 4 拼团 5卡券 ',
  `mp_link_identity`    varchar(255)          not null default '' comment '发送消息关联id，如果order_sn等',
  `mp_template_no`      varchar(255)          not null default '' comment '发送模板编号',
  `mp_template_content` text comment '发送模板消息内容',
  `user_visit_time`     timestamp null default null comment '用户点击访问时间',
  `use_time`            timestamp null default null comment '使用时间',
  `create_time`			timestamp      default current_timestamp,
  `update_time` 		timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`rec_id`),
  key (`form_id`),
  key (`user_id`),
  key (`open_id`)
);

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
    `status`           tinyint(1)   not null default 1 comment '状态： 1：启用  0： 禁用',
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

-- -- 用户充值记录表
-- drop table if exists `b2c_charge_money`;
create table `b2c_charge_money` (
  `id`           int(20)        not null auto_increment,
  `user_id`      int(20)                not null default '0' comment '用户id',
  `card_id`      int(20)                not null default '0' comment '会员卡id',
  `charge`       decimal(10, 2)         not null default '0.00' comment '充值的钱',
  `count`        smallint(3)            not null default '0' comment '充值次数',
  `payment`      varchar(90)            not null comment '支付方式',
  `type`         tinyint(1)             not null default '0' comment '消费类型 0是普通卡 1限次卡',
  `reason`       varchar(191)           default null comment '充值原因',
  `prepay_id`    varchar(191)           default null comment '微信支付id，用于发送模板消息',
  `message`      varchar(191)           not null default '' comment '备注',
  `order_sn`     varchar(20)            not null default '',
  `order_status` tinyint(1)             not null default '0' comment '订单状态 0：待支付，1：已取消，2：已完成',
  `money_paid`   decimal(10, 2)         not null default '0.00' comment '订单应付金额',
  `charge_type`  tinyint(1)             not null default '0' comment '0按规则 1自定义',
  `card_no`      varchar(32) 			default '' not null comment '会员卡号',
  `ali_trade_no` varchar(60)            not null   default '' comment '支付宝交易单号',
  `exchang_count`        smallint(3)    not null   default '0' comment '兑换充值次数',
  `create_time`			timestamp      default current_timestamp,
  `update_time` 		timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 用户会员卡消费记录表
-- drop table if exists `b2c_card_consumer`;
create table `b2c_card_consumer` (
  `id`       int(20)        not null  auto_increment,
  `user_id`  int(20)                not null  default '0' comment '用户id',
  `card_id`  int(20)                not null  default '0' comment '会员卡id',
  `money`    decimal(10, 2)         not null  default '0.00' comment '消费的卡余额',
  `count`    smallint(3)            not null  default '0' comment '消费次数',
  `type`     tinyint(1)             not null  default 0 comment '消费类型 0是普通卡 1限次卡',
  `reason`   varchar(191)           default null comment '消费原因',
  `message`  varchar(191)           not null  default '' comment '备注',
  `card_no`  varchar(32) 			default '' not null comment '会员卡号',
  `exchang_count`    smallint(3)    not null  default '0' comment '兑换次数',
  `order_sn` 			varchar(20) not null  default '' comment '订单号',
  `create_time`			timestamp      default current_timestamp,
  `update_time` 		timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- --  表单页面
-- drop table if exists `b2c_form_page`;
create table `b2c_form_page` (
  `page_id`          int(10)  not null auto_increment comment '表单页id',
  `shop_id`          int(11)          not null default 0 comment '店铺id',
  `page_name`        varchar(60)      not null default '',
  `state`            tinyint(1)       not null default 0 comment '状态：0未发布，1已发布 2已关闭 3 已删除',
  `page_content`     longtext comment '页面内容，json格式存储',
  `form_cfg`         longtext comment '表单配置，json格式存储',
  `start_time`       timestamp null default null comment '开始时间',
  `end_time`         timestamp null default null comment '结束时间',
  `is_forever_valid` tinyint          not null default 1 comment '1永久有效，0期限内有效',
  `submit_num`       int              not null default 0 comment '反馈数量',
  `create_time`			timestamp      default current_timestamp,
  `update_time` 		timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`page_id`)
);

-- --  表单提交列表
-- drop table if exists `b2c_form_submit_list`;
create table `b2c_form_submit_list` (
  `submit_id`    int(10)  not null auto_increment,
  `page_id`      int(10)  not null,
  `shop_id`      int(11)          not null default 0 comment '店铺id',
  `user_id`      int(11)          not null default 0 comment '用户id',
  `open_id`      varchar(255) comment '微信openid',
  `nick_name`    varchar(255) comment '微信昵称',
  `send_score`   int(6)           null comment '送积分',
  `send_coupons` varchar(200)     null comment '送优惠券',
  `create_time`			timestamp      default current_timestamp,
  `update_time` 		timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`submit_id`),
  key (`page_id`),
  key (`user_id`)
);

-- --  表单提交详情
-- drop table if exists `b2c_form_submit_details`;
create table `b2c_form_submit_details` (
  `rec_id`       int(10)  not null auto_increment,
  `page_id`      int(10)  not null,
  `submit_id`    int(10)  not null comment '表单提交id，对应b2c_form_submit_list的submit_id',
  `user_id`      int(10)  not null,
  `module_name`  varchar(255) comment '模块名称',
  `module_type`  varchar(255) comment '模块类型',
  `module_value` text comment '模块的值',
  `cur_idx`      varchar(32)      not null comment '装修模块保存id',
  `create_time`			timestamp      default current_timestamp,
  `update_time` 		timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`rec_id`),
  key (`submit_id`),
  key (`page_id`),
  key (`user_id`),
  key (`module_type`),
  key (`module_name`)
);

-- -- 用户画像
-- drop table if exists `b2c_mp_user_portrait`;
create table `b2c_mp_user_portrait` (
  `ref_date`     char(30)   not null comment '时间： 如： "20180313"',
  `visit_uv_new` longtext comment '新用户',
  `visit_uv`     longtext comment '活跃用户',
  `type`         tinyint(4) not null default '0' comment '0:昨天，1：最近7天，2:30天',
  `create_time`			timestamp      default current_timestamp,
  `update_time` 		timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  key `type` (`type`) using btree,
  key `ref_date` (`ref_date`) using btree
);

-- --  消息模板配置表
-- drop table if exists `b2c_template_config`;
create table `b2c_template_config` (
  `id`             int(11)          not null auto_increment,
  `name`           varchar(50)    	not null comment '消息名称',
  `action`         tinyint(1)    	not null default 1 comment '消息类型： 1： 业务处理通知 2： 商家活动通知 3： 活动加入成功提醒',
  `title`          varchar(50)    	not null comment '业务标题',
  `template_id`    int(11)         	not null default 0 comment '选择的模板id',
  `content`        text           	comment '业务内容',
  `page_link`      varchar(255)   	default null comment '页面链接',
  `send_condition` text            	comment '筛选发送人条件',
  `to_user`        text            	comment '发送人，逗号分隔，* 代表全部',
  `send_action`    tinyint(1)     	not null default 1 comment '发送方式： 1：立即发送  2： 持续发送  3：定时发送',
  `send_status`    tinyint(1)       not null default 0 comment '发送完成 1： 完成',
  `start_time`     timestamp null default null comment '发送起始时间',
  `end_time`       timestamp null default null comment '发送终止时间',
  `del_flag`       tinyint(1)       not null default 0 comment '删除标识',
  `del_time`       timestamp null default null,
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- --  消息模板表
-- drop table if exists `b2c_message_template`;
create table `b2c_message_template` (
  `id`      int(11)    not null auto_increment,
  `action`  tinyint(1) not null default 1 comment '消息类型： 1： 业务处理通知 2： 商家活动通知 3： 活动加入成功提醒',
  `content` text       ,
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 每个分销员统计信息
-- drop table if exists `b2c_user_total_fanli`;
create table `b2c_user_total_fanli` (
  `user_id`         int not null comment '会员id',
  `mobile`          varchar(16)    default '' comment '会员手机号',
  `sublayer_number` int(11)        default '0' comment '子层分销员数量',
  `total_money`     decimal(10, 2) default '0.00' comment '累计获得佣金数',
  `can_money`       decimal(10, 2) default '0.00' comment '可用佣金余额',
  `blocked`         decimal(10, 2) default '0.00' comment '冻结佣金余额',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`user_id`)
);

-- -- 每个子分销员数据汇总
-- drop table if exists `b2c_user_fanli_statistics`;
create table `b2c_user_fanli_statistics` (
  `user_id`               int(11) not null,
  `fanli_user_id`         int(11) comment '邀请人id',
  `order_number`          int(11) comment '累积订单数量',
  `total_can_fanli_money` decimal(10, 2) default '0.00' comment '累计返利订单可计算返利总金额',
  `total_fanli_money`     decimal(10, 2) default '0.00' comment '用户累计返利佣金',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间'
);

-- -- 商品返利统计
-- drop table if exists `b2c_fanli_goods_statistics`;
create table `b2c_fanli_goods_statistics` (
  `prd_id`          int(11) not null,
  `prd_sn`          varchar(30) comment '规格编码',
  `goods_id`        int(11),
  `cat_id`          int(11) comment '分类id',
  `sale_number`     int(11) comment '销量',
  `prd_total_fanli` decimal(10, 2) default '0.00' comment '商品返利总金额',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间'
);

-- --  店铺自定义分类
-- drop table if exists `b2c_sort`;
create table `b2c_sort` (
  `sort_id`     int(11)  not null auto_increment,
  `sort_name`   varchar(90)      not null default '',
  `parent_id`   int(11)      not null default '0' COMMENT '分类父节点，0表示一级',
  `level`       smallint(5)      not null default 0,
  `has_child`   tinyint(1)       not null default 0,
  `sort_img`    varchar(191)     not null default '' comment '一级分类是头图 其他为分类图标',
  `img_link`    varchar(191)     not null default '' comment '图标或者头图链接',
  `first`       smallint(2)      not null default '0' comment '优先级',
  `type`        tinyint(1)       not null default 0 comment '0普通商家分类 1推荐分类',
  `sort_desc`   varchar(191)     not null default '',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`sort_id`),
  key `parent_id` (`parent_id`)
);

-- --  砍价活动表
-- drop table if exists `b2c_bargain`;
create table `b2c_bargain` (
  `id`                 int(11)               not null auto_increment,
  `bargain_name`       varchar(120)                 not null comment '活动名称',
  `goods_id`           int(11)                      not null default '0' comment '商品id',
  `start_time`         timestamp                 null   default null comment '开始时间',
  `end_time`           timestamp                  null  default null comment '结束时间',
  `expectation_number` int(6)                       not null default '0' comment '砍价预期人数',
  `expectation_price`  decimal(10, 2) 				not null default 0.00 comment '预期砍价最低金额',
  `bargain_min`        float                                 default null comment '首次返利比例小',
  `bargain_max`        float                                 default null comment '首次返利比例大',
  `stock`              int(6)                       not null default '0' comment '库存',
  `sale_num`           int(6)                       not null default '0' comment '销售量',
  `mrking_voucher_id`  varchar(200)                 null,
  `status`             tinyint(1)                   not null default '1' comment '状态：1可用，0停用',
  `del_flag`           tinyint(1)                   not null default '0' comment '1删除',
  `create_time`			timestamp      default current_timestamp,
  `update_time` 		timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  `del_time`        	timestamp null default null,
  `reward_coupon_id` 	varchar(200) null   comment '砍价失败发放优惠券',
  `share_config`      	text    comment '分享设置',
  `bargain_type`        tinyint(1) default 0         null comment '砍价类型0定人1任意价',
  `floor_price`         decimal(10, 2) default 0.00  null comment '任意低价',
  `bargain_money_type`  tinyint(1) default 0         null comment '砍价计算模式',
  `bargain_fixed_money` decimal(10, 2) default 0.00  null comment '固定金额',
  `bargain_min_money`   decimal(10, 2) default 0.00  null comment '最低价',
  `bargain_max_money`   decimal(10, 2) default 0.00  null comment '最高价',
  `free_freight` tinyint(1) DEFAULT '0' COMMENT '0不免运费，使用原商品运费模板   1免运费',
  primary key (`id`),
  key `goods_id` (`goods_id`)
);

-- --  砍价发起表
-- drop table if exists `b2c_bargain_record`;
create table `b2c_bargain_record` (
  `id`            int(11)  not null auto_increment,
  `user_id`       int(11)           not null default '0' comment '用户id',
  `bargain_id`    int(11)           not null default '0' comment '活动id',
  `goods_id`      int(11)           not null default '0' comment '商品id',
  `prd_id`        int(11)           not null default '0' comment '产品id',
  `goods_price`   decimal(10, 2)   not null default '0.00' comment '商品价格',
  `bargain_money` decimal(10, 2)   not null default '0.00' comment '已砍价格',
  `user_number`   int(6)           not null default '0' comment '砍价人数',
  `status`        tinyint(1)       not null default '0' comment '0砍价中1成功2失败（成功即扣库存）',
  `is_ordered`    int(1)           not null default '0' comment '是否下单',
  `order_sn`      varchar(20)      not null default '0' comment '订单号',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  `del_flag`      tinyint(1)       not null default '0',
  `del_time`      timestamp null default null,
  primary key (`id`),
  key `user_bargain` (`user_id`, `bargain_id`),
  key `bargain_id` (`bargain_id`)
);

-- --  砍价用户表
-- drop table if exists `b2c_bargain_user_list`;
create table `b2c_bargain_user_list` (
  `id`            int(11)  not null auto_increment,
  `record_id`     int(11)          not null default '0' comment '对应b2c_bargain_record表id',
  `user_id`       int(11)           not null default '0',
  `bargain_money` decimal(10, 2)   not null default '0.00',
  `create_time`		timestamp      default current_timestamp,
  `update_time` 	timestamp      default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `bargain_user` (`record_id`, `user_id`)
);

-- --  海报表
-- drop table if exists `b2c_pictorial`;
create table `b2c_pictorial` (
  `id`          int(11)            	not null auto_increment,
  `action`      tinyint(1)          not null comment '海报类型： 1：普通 2 ：拼团 3：砍价 4：表单 5:拼团分享',
  `path`        varchar(500)  		not null comment '海报路径',
  `rule`        text           		comment '生成触发规则',
  `user_id`     int(11)            	not null default '0',
  `identity_id` int(11)             not null comment '关联id： 例如：goods_id, page_id',
  `del_flag`    tinyint(4)          not null default '0' comment '删除标记： 1：删除',
  `del_time`      	timestamp null default null,
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

--  优惠券活动表
-- drop table if exists `b2c_coupon_activity`;
create table `b2c_coupon_activity` (
  `id`                int(11)               not null auto_increment,
  `activity_action`   tinyint(1) 			default 1  null comment '活动类型：1：活动送券 2：大转盘抽奖',
  `action`            tinyint(1)            not null default '1' comment '针对用户群体： 1: 新用户 2: 全部用户',
  `name`              varchar(50)           not null comment '活动名称',
  `title`             varchar(100)          not null comment '宣传语',
  `bg_action`         tinyint(4)            not null default '1' comment '背景图',
  `start_date`        timestamp null default null comment '有效期-起始',
  `end_date`          timestamp null default null comment '有效期-结束',
  `mrking_voucher_id` varchar(500)          not null comment '活动优惠券，逗号分隔',
  `status`            tinyint(1)            not null default '1' comment '状态： 1: 正常 0: 关闭',
  `del_flag`          tinyint(4)            not null default '0',
  `del_time`      	timestamp null default null,
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

--  活动送券记录表
-- drop table if exists `b2c_coupon_activity_record`;
create table `b2c_coupon_activity_record` (
  `id`                int(11)  not null auto_increment,
  `activity_id`       int(11)  not null comment '活动id',
  `user_id`           int(11)  not null,
  `receive_time`      timestamp null default null comment '领取时间',
  `mrking_voucher_id` varchar(500)      default null comment '已领取的优惠券',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- 积分商城活动定义表
-- drop table if exists `b2c_integral_mall_define`;
create table `b2c_integral_mall_define` (
  `id`               int(11)        not null auto_increment,
  `goods_id`         int(11)       	not null comment '商品id',
  `max_exchange_num` smallint(6)    not null default '0' comment '每个用户最大可兑换数量',
  `start_time`       timestamp null default null comment '活动起始时间',
  `end_time`         timestamp null default null comment '活动终止时间',
  `status`           tinyint(1)     not null default '1' comment '1: 正常 0：禁用',
  `del_flag`         tinyint(1)     not null default '0',
  `del_time`      	timestamp null default null,
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  `name`            varchar(100)   	not null comment '活动名称',
  `share_config`    text    		comment '分享设置',
  primary key (`id`)
);

-- 积分商城产品定义表
-- drop table if exists `b2c_integral_mall_product`;
create table `b2c_integral_mall_product` (
  `id`                      int(11)  not null auto_increment,
  `integral_mall_define_id` int(11)          not null comment '积分商城活动定义表id',
  `product_id`              int(11)          not null comment '规格产品id',
  `score`                   int(11)          not null comment '积分数',
  `stock`                   smallint(6)      not null comment '库存数',
  `money`                   decimal(10, 2)   not null comment '兑换现金',
  `create_time`				timestamp      	default current_timestamp,
  `update_time` 			timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- 积分商城用户兑换记录表
-- drop table if exists `b2c_integral_mall_record`;
create table `b2c_integral_mall_record` (
  `id`                      int(11) 		not null              auto_increment,
  `integral_mall_define_id` int(11)         not null comment '积分商城活动定义表id',
  `order_sn`                varchar(20)  	default null comment '订单编号',
  `user_id`                 int(11)          not null comment '用户id',
  `goods_id`                int(11)          not null comment '商品id',
  `product_id`              int(11)          not null comment '产品规格id',
  `score`                   int(11)          not null comment '消费积分',
  `number`                  smallint(6)      not null comment '兑换数量',
  `money`                   decimal(10, 2)   not null comment '消耗现金',
  `create_time`				timestamp      	default current_timestamp,
  `update_time` 			timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

--  等级卡升级记录
-- drop table if exists `b2c_card_upgrade`;
create table `b2c_card_upgrade` (
  `id`              int(11)      not null auto_increment,
  `user_id`         int(11)      not null comment '用户id',
  `old_card_id`     int(11)      not null comment '升级前卡id',
  `new_card_id`     int(11)      not null comment '升级后卡id',
  `old_grade`       varchar(20)  not null comment '升级前卡等级',
  `new_grade`       varchar(20)  not null comment '升级后卡等级',
  `old_card_name`   varchar(20)  not null,
  `new_card_name`   varchar(20)  not null,
  `grade_condition` varchar(200) not null default '' comment '条件',
  `operate`         varchar(200) not null default '' comment '操作备注',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- --  分销原申请记录
-- drop table if exists `b2c_distributor_apply`;
create table `b2c_distributor_apply` (
  `id`                int(11)  		not null auto_increment,
  `user_id`           int(11)  		not null default '0',
  `status`            tinyint(2) 	not null default '0',
  `msg`               text              	comment '审核内容',
  `del_flag`          tinyint(2)    not null default '0',
  `activation_fields` varchar(1000)    null comment '审核校验',
  `config_fields`     varchar(500)     null comment '审核字段',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `user_id` (`user_id`)
);

-- --  规格对应会员价
-- drop table if exists `b2c_grade_prd`;
create table `b2c_grade_prd` (
  `id`          int(11)         not null auto_increment,
  `prd_id`      int(10)         not null,
  `goods_id`    int(10)         not null,
  `shop_id`     int(11)         not null default '0',
  `grade_price` decimal(10, 2)  not null default '0.00',
  `grade`       varchar(65)     not null default '' comment '会员卡等级',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `prd_id` (`prd_id`)
);

-- --  模板发送记录
-- drop table if exists `b2c_service_message_record`;
create table `b2c_service_message_record` (
  `user_id`           int(11)      default null comment '用户id',
  `mobile`            varchar(20)  default null comment '用户手机号',
  `request_action`    tinyint(4)   not null default '0' comment '请求类型：100:短信平台',
  `identity_id`       varchar(50)  default null comment '关联其他表：如：外部请求requestid',
  `template_platform` tinyint(1)   not null default '1' comment '模板平台：1： 小程序 2：公众号',
  `template_content`  text         	 comment '模板内容',
  `response_code`     varchar(20)  default null comment '响应code 0:成功 >0 其他',
  `response_msg`      varchar(500) default null comment '响应结果',
  `path`              varchar(200)         null comment '小程序路径',
  `path_query`        varchar(500)         null comment '小程序路径参数',
  `send_status`       tinyint(1) 	not null default 0   comment '1: 发送成功  0：未知',
  `is_visit`          tinyint(1) 	not null default 0   comment '是否已点击访问 1：是 0： 否',
  `visit_time`        timestamp null default null comment '访问时间',
  `template_type`     tinyint(2)            null comment '模板类型 7：商家自定义',
  `link_identity`     varchar(50)           null comment '模板消息关联id',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  index `user_request` (`user_id`, `request_action`, `template_platform`)
);

-- --  接口请求记录
-- drop table if exists `b2c_service_request`;
create table `b2c_service_request` (
  `id`               bigint(20)       	not null auto_increment,
  `request_id`       varchar(50)       	default null comment '请求requestid',
  `service_name`     varchar(50)      	not null comment '服务名',
  `request_content`  text 				comment '请求内容',
  `request_time`     timestamp null default null comment '请求时间',
  `response_time`    timestamp null default null comment '响应时间',
  `response_content` text 				comment '响应内容',
  `ip`               varchar(100)      	default null comment '请求ip',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  index `request_id` (`request_id`)
);

-- --  抽奖活动表
-- drop table if exists `b2c_lottery`;
create table `b2c_lottery` (
  `id`                 int(11)                   not null auto_increment,
  `lottery_name`       varchar(120)  			not null comment '抽奖名称',
  `start_time`         timestamp              null  default null comment '开始时间',
  `end_time`           timestamp              null  default null comment '结束时间',
  `lottery_explain`    varchar(299)  			not null comment '抽奖说明',
  `free_chances`       int(8)                   default null comment '免费抽奖次数',
  `can_share`          tinyint(2)               default null comment '是否分享获得次数',
  `share_chances`      int(8)                   default null comment '分享最多获得次数',
  `can_use_score`      tinyint(2)               default null comment '是否可以积分抽奖',
  `score_per_chance`   int(8)                   default null comment '抽奖一次使用积分',
  `score_chances`      int(8)                   default null comment '积分最多抽奖次数',
  `no_award_score`     int(8)                   default null comment '未中奖奖励积分',
  `no_award_image`     varchar(199)           	default null comment '未中奖图片',
  `no_award_icon`      varchar(20)            	default null comment '未中奖提示',
  `first_award`        varchar(500)           	default null comment '一等奖设置（json）',
  `first_award_times`  int(8)                 	default null comment '中奖数',
  `second_award`       varchar(500)           	default null comment '二等奖设置（json）',
  `second_award_times` int(8)                 	default null comment '中奖数',
  `third_award`        varchar(500)           	default null comment '三等奖设置（json）',
  `third_award_times`  int(8)                 	default null comment '中奖数',
  `fourth_award`       varchar(500)           	default null comment '四等奖设置（json）',
  `fourth_award_times` int(8)                 	default null comment '中奖数',
  `status`             tinyint(2)             not null default '0' comment '状态：1停用',
  `del_flag`           tinyint(2)             not null default '0' comment '1删除',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- --  抽奖活动表
-- drop table if exists `b2c_lottery_share`;
create table `b2c_lottery_share` (
  `id`              int(10)  not null auto_increment,
  `user_id`         int(10)  not null comment '用户编号',
  `lottery_id`      int(10)  not null comment '抽奖编号',
  `share_times`     int(8)   not null default '0' comment '分享次数',
  `use_share_times` int(8)   not null default '0' comment '抽奖次数',
  `use_score_times` int(8)   not null default '0',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `user_lottery` (`user_id`, `lottery_id`)
);

-- --  抽奖活动表
-- drop table if exists `b2c_lottery_record`;
create table `b2c_lottery_record` (
  `id`             int(11)      not null           auto_increment,
  `user_id`        int(10)      not null comment '用户编号',
  `lottery_id`     int(10)      not null comment '抽奖编号',
  `lottery_source` tinyint(1) 	not null default 0 comment '抽奖来源:1.登录2.支付',
  `lottery_act_id` int(10) 		not null default 0 comment '抽奖来源id',
  `chance_source`  tinyint(2)   default null comment '抽奖机会来源',
  `lottery_cost`   varchar(32)  default null comment '抽奖花费积分',
  `lottery_grade`  tinyint(2)   default null comment '中奖等级：0未中奖，1一等奖，2二等奖，3三等奖，4四等奖',
  `lottery_type`   tinyint(2)   default null comment '奖励类型',
  `lottery_award`  varchar(60)   default null comment '获得奖励',
  `award_info`     varchar(500)  default null comment '中奖信息',
  `prd_id`         int(10)       not null comment '商品（规格）编号',
  `present_status` tinyint(1) 	 not null default '0' comment '赠品状态:0.待领取，1：已领取，2.已过期',
  `order_sn` 		varchar(60)  default null comment '关联订单',
  `lottery_expired_time` timestamp null default null comment '赠品过期时间',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `user_lottery` (`user_id`, `lottery_id`),
  key `lottery_id` (`lottery_id`)
);

--  用户足迹
-- drop table if exists `b2c_footprint_record`;
create table `b2c_footprint_record` (
  `id`          int(11)  not null auto_increment,
  `goods_id`    int(11)  not null comment '商品id',
  `user_id`     int(11)  not null,
  `count` int(11) default 1 null comment '浏览次数',
  `type` tinyint(2) default 0  null   comment '0 老用户 1新用户',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 返利策略
-- drop table if exists `b2c_distribution_strategy`;
create table `b2c_distribution_strategy` (
  `id`                 int(10)                         	not null auto_increment,
  `strategy_name`      varchar(120)  					not null comment '策略名称',
  `strategy_level`     tinyint(3)                              not null comment '策略等级',
  `start_time`         timestamp null default null comment '开始时间',
  `end_time`           timestamp null default null comment '结束时间',
  `self_purchase`      tinyint(1)                              not null default '0' comment '自购返利',
  `cost_protection`    tinyint(1)                              not null default '0' comment '成本保护',
  `fanli_ratio`        float                                   not null default '0' comment '返利比例（%的系数）',
  `rebate_ratio`       float 									default 0 comment '间接',
  `fanli_ratio_2`      float                                   not null default '0' comment '二级返利比例（%的系数）',
  `rebate_ratio_2`     float 									default 0 comment '间接',
  `fanli_ratio_3`      float                                   not null default '0' comment '三级返利比例（%的系数）',
  `rebate_ratio_3`     float 									default 0 comment '间接',
  `fanli_ratio_4`      float                                   not null default '0' comment '四级返利比例（%的系数）',
  `rebate_ratio_4`     float 									default 0 comment '间接',
  `fanli_ratio_5`      float                                   not null default '0' comment '五级返利比例（%的系数）',
  `rebate_ratio_5`     float 									default 0  comment '间接',
  `recommend_type`     tinyint(4)                              default null comment '0:全部商品1:部分商品',
  `recommend_goods_id` text    						comment '返利商品ids',
  `recommend_cat_id`   text    						comment '返利分类ids',
  `status`             tinyint(2)                                       default '0' comment '1停用',
  `del_flag`           tinyint(2)                                       default '0' comment '1删除',
  `del_time`			timestamp     null 	default null comment '删除时间',
  `recommend_sort_id`  	varchar(300)           					default null comment '返利商家分类ids',
  `send_coupon`       tinyint(1) 								default 0  null   comment '赠送优惠券',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 推荐商品
-- drop table if exists `b2c_recommend_goods`;
create table `b2c_recommend_goods` (
  `id`                 int(10)                         not null auto_increment comment '编号',
  `recommend_name`     varchar(120)  					not null comment '推荐名称',
  `recommend_type`     tinyint(2)                              not null default '0' comment '0.全部商品1.部分商品',
  `recommend_goods_id` varchar(299)                            null comment '推荐商品id',
  `recommend_cat_id`   varchar(299)                            null comment '推荐分类id',
  `recommend_use_page` varchar(299)                            not null default '' comment '推荐使用页面',
  `status`             tinyint(2) default 0                    null comment '状态1停用',
  `del_flag`           tinyint(2) default 0                    null comment '1删除',
  `del_time`			timestamp      null	default null comment '删除时间',
  `recommend_sort_id`  	varchar(299)                            null comment '推荐商家分类id',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);


-- --  加价购
-- drop table if exists `b2c_purchase_price_define`;
create table `b2c_purchase_price_define` (
  `id`                  int(11)                                 not null auto_increment,
  `name`                varchar(255)  not null comment '活动名称',
  `level`               smallint(6)                             not null default '0' comment '优先级',
  `max_change_purchase` smallint(6)                                      default null comment '最大换购数',
  `goods_id`            text  comment '主商品',
  `start_time`          timestamp null default null comment '开始时间',
  `end_time`            timestamp null default null comment '结束时间',
  `status`              tinyint(1)                                       default '1' comment '状态 1: 启用 0:禁用',
  `del_flag`            tinyint(4)                                       default '0',
  `del_time`			timestamp     null 	default null comment '删除时间',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);
-- -- 加价购
-- drop table if exists `b2c_purchase_price_rule`;
create table `b2c_purchase_price_rule` (
  `id`                int(11) not null auto_increment,
  `purchase_price_id` int(11) not null comment '加价购活动id',
  `full_price`        decimal(10, 2)   default null comment '满多少钱',
  `purchase_price`    decimal(10, 2)   default null comment '换购多少钱的商品',
  `product_id`        text  comment '换购商品',
  `del_flag`          tinyint(1)       default '0',
  `del_time`			timestamp      null	default null comment '删除时间',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

--  秒杀定义
-- drop table if exists `b2c_sec_kill_define`;
create table `b2c_sec_kill_define` (
  `sk_id`         int(11)                                 not null auto_increment comment '秒杀活动id',
  `shop_id`       int(11)                                 not null comment '店铺id',
  `goods_id`      int(11)                                 not null comment '商品id',
  `name`          varchar(100)  not null comment '活动名称',
  `limit_amount`  smallint(6)                             not null comment '每人限购数量',
  `limit_paytime` smallint(6)                             not null comment '规定的有效支付时间',
  `start_time`    timestamp null default null comment '开始时间',
  `end_time`      timestamp null default null comment '结束时间',
  `stock`         smallint(6)                             not null default '0' comment '总库存',
  `sale_num`      smallint(6)                             not null default '0' comment '销量',
  `del_flag`      tinyint(1)                                       default '0',
  `status`        tinyint(1)                                       default '1' comment '状态： 1：启用  0： 禁用',
  `free_freight`  tinyint(1)                                       default '1' comment '是否免运费： 1：免运费  0： 原先商品的运费',
  `del_time`			timestamp      null	default null comment '删除时间',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  `card_id`       text   comment '专属会员卡',
  `share_config` text   comment '分享配置',
  primary key (`sk_id`)
);

--  参与秒杀活动记录
-- drop table if exists `b2c_sec_kill_list`;
create table `b2c_sec_kill_list` (
  `sklog_id` int(11)                                not null auto_increment comment '秒杀活动商品购买记录id',
  `sk_id`    int(11)                                not null comment '秒杀活动定义id',
  `goods_id` int(11)                                not null default '0',
  `user_id`  int(11)                                not null comment '参与秒杀活动用户id',
  `order_sn` varchar(20)  not null comment '订单编号',
  `del_flag` tinyint(1)                                      default '0',
  `del_time`			timestamp      null	default null comment '删除时间',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`sklog_id`)
);

--  参与秒杀规格商品价格
-- drop table if exists `b2c_sec_kill_product_define`;
create table `b2c_sec_kill_product_define` (
  `skpro_id`       int(11)        not null auto_increment comment '秒杀商品规格id',
  `sk_id`          int(11)        not null comment '秒杀活动定义id',
  `product_id`     int(11)        not null comment '商品规格id',
  `sec_kill_price` decimal(10, 2) not null default '0.00' comment '秒杀价',
  `stock`          smallint(6)    not null default '0' comment '库存',
  `sale_num`       smallint(6)    not null default '0' comment '销量',
  `total_stock`    smallint(6)    not null default '0' comment '总库存',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`skpro_id`)
);

-- -- 用户访问商品记录表
-- drop table if exists `b2c_user_goods_record`;
create table `b2c_user_goods_record` (
  `id`            bigint(20)  not null              auto_increment,
  `user_id`       int(11)      not null                 default '0' comment '用户id',
  `goods_id`      int(11)     not null                  default '0' comment '商品id',
  `active_id`     int(11)      null                     default '0' comment '活动id',
  `active_type`   smallint(3)      null                  default '0' comment '活动类型',
  `user_ip`       varchar(64)      null                  default null comment '用户ip',
  `province_code` varchar(20)  default null comment '省',
  `province`      varchar(20)  default null comment '省',
  `city_code`     varchar(20)  default null comment '市',
  `city`          varchar(20)  default null comment '市',
  `district_code` varchar(20)  default null comment '区',
  `district`      varchar(20)  default null comment '区',
  `lat`           varchar(64)      null                  default null comment '经度',
  `lng`           varchar(64)      null                  default null comment '纬度',
  `count`         smallint(3)      null                  default '0' comment '次数',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `create_time` (`create_time`) using btree
);

-- -- 用户添加购物车商品记录表
-- drop table if exists `b2c_user_cart_record`;
create table `b2c_user_cart_record` (
  `id`            int(20)  not null              auto_increment,
  `user_id`       int(11)      not null              default '0' comment '用户id',
  `goods_id`      int(11)      not null              default '0' comment '商品id',
  `prd_id`        int(11)      not null              default '0' comment '规格id',
  `num`           smallint(3)      not null              default '1' comment '件数',
  `del_flag`      smallint(3)      null                  default '0' comment '0：添加，1：删除标记',
  `user_ip`       varchar(64)      null                  default null comment '用户ip',
  `province_code` varchar(20)  default null comment '省',
  `province`      varchar(20)  default null comment '省',
  `city_code`     varchar(20)  default null comment '市',
  `city`          varchar(20)  default null comment '市',
  `district_code` varchar(20)  default null comment '区',
  `district`      varchar(20)  default null comment '区',
  `lat`           varchar(64)      null                  default null comment '经度',
  `lng`           varchar(64)      null                  default null comment '纬度',
  `count`         smallint(3)      null                  default '0' comment '次数',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `create_time` (`create_time`) using btree,
  key `goods_create_time` (`goods_id`, `create_time`) using btree,
  key `user_create_time` (`user_id`, `create_time`) using btree
);

-- 商品概览
-- drop table if exists `b2c_goods_user_summary`;
create table `b2c_goods_user_summary` (
  `id`                int(11)   not null auto_increment,
  `ref_date`          date               default null comment '2018-09-04',
  `type`              tinyint(1)         default null comment '1,7,30',
  `goods_id_number`   int(11)            default null comment '在售商品数',
  `prd_id_number`     int(11)            default null comment '在售规格数',
  `goods_id_visit`    int(11)            default null comment '访问商品数',
  `goods_user_visit`  int(11)            default null comment 'uv',
  `goods_visit`       int(11)            default null comment 'goods pv',
  `cart_user_number`  int(11)            default null comment '加购人数',
  `cart_goods_number` int(11)            default null comment '加购件数',
  `paid_goods_number` int(11)            default null comment '付款人数',
  `paid_user_number`  int(11)            default null comment '付费用户数',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `ref_type` (`ref_date`, `type`) using btree
);

-- 商品概览-效果
-- drop table if exists `b2c_goods_summary`;
create table `b2c_goods_summary` (
  `id`                int(11)   not null auto_increment,
  `ref_date`          date               default null comment '统计日期',
  `type`              tinyint(2)         default null comment '1,7,30',
  `goods_id`          int(11)            default null,
  `new_user_number`   int(11)            default null comment '新成交客户数',
  `old_user_number`   int(11)            default null,
  `pv`                int(11)            default null comment '浏览量',
  `uv`                int(11)            default null comment '访客数',
  `cart_uv`           int(11)            default null comment '加购人数',
  `paid_uv`           int(11)            default null comment '付款人数',
  `paid_goods_number` int(11)            default null comment '付款商品件数',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `ref_type` (`ref_date`, `type`) using btree
);

-- 用户概览
-- drop table if exists `b2c_user_summary_trend`;
create table `b2c_user_summary_trend` (
  `id`                   int(11)        not null auto_increment,
  `ref_date`             date           not null comment '2018-09-04',
  `type`                 tinyint(2)     not null comment '1,7,30',
  `login_data`           int(11)        not null comment '访问会员数据',
  `user_data`            int(11)        not null comment '累积会员数',
  `coupon_data`          int(11)        not null comment '领券会员数',
  `cart_data`            int(11)        not null comment '加购会员数',
  `reg_user_data`        int(11)        not null comment '注册数',
  `upgrade_user_data`    int(11)        not null comment '升级会员数',
  `charge_user_data`     int(11)        not null comment '储值会员数',
  `order_user_data`      int(11)        not null comment '成交客户数',
  `new_order_user_data`  int(11)        not null comment '成交新客户数',
  `old_order_user_data`  int(11)        not null comment '成交老客户数',
  `total_paid_money`     int(11)        not null comment '总成交金额',
  `new_paid_money`       decimal(10, 4)          default null comment '成交新客户支付金额',
  `old_paid_money`       decimal(10, 0)          default null comment '成交老客户支付金额',
  `pay_goods_number`     int(11)                 default null comment '付款件数',
  `new_pay_goods_number` int(11)                 default null comment '付款件数',
  `old_pay_goods_number` int(11)                 default null comment '付款件数',
  `pay_order_num`        int default 0  null comment '成交订单数',
  `login_pv`             int default 0  null comment '登录pv',
  `order_num`            int default 0  not null comment '下单笔数',
  `order_user_num`       int default 0  null comment '下单人数(生成订单就算)',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `ref_type` (`ref_date`, `type`) using btree
);

-- 标签用户下单统计
-- drop table if exists `b2c_distribution_tag`;
create table `b2c_distribution_tag` (
  `ref_date`         date                                   default null comment '日期',
  `type`             tinyint(2)                             default null comment '1,7,30',
  `tag`              varchar(50)  default null comment '标签',
  `pay_order_num`    int(11)                                default null comment '付款订单数',
  `pay_order_money`  decimal(10, 2)                         default null comment '付款金额',
  `pay_user_num`     int(11)                                default null comment '付款人数',
  `pay_goods_number` int(11)                                default null comment '付款商品件数',
  `has_mobile_num`   int(11)                                default null comment '下单有手机号的用户',
  `has_user_num`     int null                               default 0 comment '用户数',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  key `date_type` (`ref_date`, `type`) using btree
);

-- 交易订单地区分布
-- drop table if exists `b2c_distribution_order`;
create table `b2c_distribution_order` (
  `ref_date`        char(7)      default null comment '2018-07',
  `province`        varchar(20)  default null comment '省',
  `city`            varchar(20)  default null comment '市',
  `district`        varchar(20)  default null comment '区',
  `province_code`   mediumint(10)  not null        default '0' comment '省份编号',
  `city_code`       mediumint(10)  not null        default '0' comment '城市编号',
  `district_code`   mediumint(10)  not null        default '0' comment '区县编号',
  `pay_order_money` decimal(10, 2)                         default null comment '付款金额',
  `pay_user_num`    int(11)                                default null comment '付款人数',
  `uv`              int(11)                                default null comment '访客数',
  `uv_pay_ratio`    decimal(4, 2)                          default null comment '转化率',
  `order_num`       int(11)                                default null comment '订单数',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  key `ref_date` (`ref_date`) using btree,
  key `pay_order_money` (`pay_order_money`) using btree
);

-- 交易统计 每小时统计数据
-- drop table if exists `b2c_trades`;
create table `b2c_trades` (
  `ref_date`         date           default null comment '日期：例2018-09-05',
  `hour`             tinyint(2)     default null comment '小时：10时',
  `uv`               int(11)        default null comment '访客数',
  `pv`               int(11)        default null comment '访问量',
  `pay_user_num`     int(11)        default null comment '付款人数（包括货到付款发货后的状态）',
  `pay_order_num`    int(11)        default null comment '付款订单数',
  `pay_order_money`  decimal(10, 2) default null comment '付款金额',
  `pay_goods_number` int(11)        default null comment '付款件数',
  `pct`              decimal(10, 2) default null comment '客单价',
  `uv_pay_ratio`     decimal(4, 2)  default null comment '转化率',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  key `ref_date` (`ref_date`) using btree,
  key `ref_hour` (`ref_date`, `hour`) using btree
);

-- goods备份
-- drop table if exists `b2c_goods_bak`;
create table `b2c_goods_bak` (
  `id`           int(10)                not null auto_increment,
  `bak_date`     date                   default null comment '备份日期：例2018-09-05',
  `goods_id`     int(8)                 not null,
  `shop_id`      int(11)                not null default '0' comment '店铺id',
  `cat_id`       int(5)                 not null default '0',
  `sort_id`      int(11)                not null default '0',
  `market_price` decimal(10, 2)         not null default '0.00',
  `shop_price`   decimal(10, 2)         not null default '0.00',
  `goods_number` int(11)                not null default '0' comment '库存',
  `is_on_sale`   tinyint(1)             not null default '1' comment '是否在售，1在售，0下架',
  `goods_type`   tinyint(2)  			not null default 0 comment '商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- --  商品规格组合的产品表备份 `b2c_goods_spec_product_bak`
-- 李晓冰 7月12日添加
-- 修改表 b2c_goods_spec_product_bak
-- drop table if exists `b2c_goods_spec_product_bak`;
create table `b2c_goods_spec_product_bak` (
  `prd_bak_id`           int(10)                          not null auto_increment,
  `del_time`        timestamp                       null  not null DEFAULT current_timestamp,
  `prd_id`           int(10)                          not null default '0',
  `shop_id`          int(11)                          not null default '0',
  `goods_id`         int(10)                          not null default '0',
  `prd_price`        decimal(10, 2)                     not null default '0.00',
  `prd_market_price` decimal(10, 2)                     not null default '0.00'   comment '市场价',
  `prd_cost_price`   decimal(10, 2)           not null default '0.00'   comment '成本价',
  `prd_number`       int(11)                          not null default '0'  comment '当前规格组合产品库存',
  `prd_sn`           varchar(65)                        not null default ''     comment '商家编码',
  `prd_codes`        varchar(500)                       not null default ''     comment '商品条码',
  `prd_specs`        varchar(1024)                      not null default '',
  `prd_desc`         varchar(1024)                      not null default ''     comment '规格描述，格式例子：颜色:红色 尺码:s',
  `del_flag`         tinyint(1)                         not null default '0',
  `self_flag`        tinyint(1)             not null default '0'    comment '1:商家自己添加商品，其他没用',
  `low_shop_price`   varchar(1024)                      not null default '0.00'   comment '最低售出价格',
  `prd_img`          varchar(1024)                      not null default ''     comment '图片地址',
  `price_flag`       tinyint(1)                         not null default '0'  comment '0:商家未改价，1：商家改价，2：批量改价，3：毛利改价',
  `create_time`      timestamp      not null comment '规格记录在原表内的添加时间',
  `update_time`      timestamp      not null comment '规格记录在原表内的最后修改时间',
  primary key (`prd_bak_id`),
  key `gsp_goods_id` (`goods_id`),
  key `gsp_goods_codes` (`prd_codes`),
  key `gsp_prd_sn` (`prd_sn`)
);

-- 小程序初始化场景值
-- drop table if exists `b2c_mp_scene_record`;
create table `b2c_mp_scene_record` (
  `user_id`       int                                  null comment '用户id',
  `path`          varchar(50) 		comment '打开小程序的路径',
  `path_query`    varchar(500) 		comment '打开小程序的query',
  `scene`         smallint 			comment '场景值',
  `share_ticket`  varchar(500) 		comment '转发的ticket',
  `referrer_info` text 				comment 'referrer信息',
  `count`         int               	null comment '记录次数',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  index `user_path` (`user_id`, `path`, `scene`),
  index `scene_create_time` (`scene`, `create_time`),
  index `path_create_time` (`path`, `create_time`)
);

--  分享记录
-- drop table if exists `b2c_share_record`;
create table `b2c_share_record` (
  `id`            int(11)  not null auto_increment,
  `activity_id`   int(11)  null comment '活动id或商品id',
  `user_id`       int(11)  null,
  `activity_type` int(11)  null comment '活动类型',
  `count`         int(11)  null comment '次数',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

--  商品标签
-- drop table if exists `b2c_goods_label`;
create table `b2c_goods_label` (
  `id`           int(11)                                 not null auto_increment comment '标签id',
  `name`         varchar(100)  							 not null comment '标签名称',
  `goods_detail` tinyint(1)                              not null default '0' comment '是否应用于商品详情页： 1：是  0： 否',
  `goods_list`   tinyint(1)                              not null default '0' comment '是否应用于商品列表页： 1：是  0： 否',
  `is_all`       tinyint(1)                              not null default '0' comment '是否适用于全部商品： 1：是  0： 否',
  `level`        smallint(5)                             not null default '0',
  `del_time`			timestamp      null	default null comment '删除时间',
  `del_flag`     int(1)                                  not null default '0',
  `list_pattern` smallint(5)                             not null default '0' comment '列表样式',
  `goods_select` tinyint(1)                              not null default '0' comment '是否应用于商品筛选页： 1：是  0： 否',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

--  商品标签对关联表
-- drop table if exists `b2c_goods_label_couple`;
create table `b2c_goods_label_couple` (
  `id`       int(11)                                 not null auto_increment comment '标签id',
  `label_id` int(11)  							 not null comment '标签id',
  `gta_id`   int(11)                                 not null default '0' comment '商品或类型id',
  `type`     tinyint(1)                              not null default '0' comment '标签关联类型： 1：关联商品 2：平台分类 3店鋪分類 4：全部商品',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);
-- 限时减价活动
-- drop table if exists `b2c_reduce_price`;
create table `b2c_reduce_price` (
  `id`                int(11)                                not null auto_increment,
  `name`              varchar(50)  							 not null comment '活动名称',
  `start_time`        timestamp null default null comment '开始时间',
  `end_time`          timestamp null default null comment '结束日期',
  `period_action`     tinyint(1)                             not null default '1' comment '周期类型：1:每天 2:每月 3:每周',
  `point_time`        varchar(20)  							 null comment '时间段',
  `extend_time`       tinyint(20)                                     default null comment '每月第几日；每周第几天',
  `batch_discount`    tinyint(1)                             not null default '0' comment '批量打几折',
  `batch_reduce`      decimal(10, 2)                                  default null comment '批量减多少',
  `batch_final_price` decimal(10, 2)                                  default null comment '批量折后价',
  `is_batch_integer`  tinyint(1)                             not null default '0' comment '是否批量取整',
  `status`            tinyint(1)                             not null default '1' comment '状态：1：启用 0：禁用',
  `del_flag`          tinyint(1)                             not null default '0',
  `del_time`			timestamp      null	default null comment '删除时间',
  `limit_amount`      int(11)                                not null default 0,
  `add_type`          tinyint(1)                             not null default '0' comment '新建方式：0正常，1批量改价，2批量加价率',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  `share_config`      text   comment '分享设置',
  primary key (`id`)
);
--  限时减价活动商品
-- drop table if exists `b2c_reduce_price_goods`;
create table `b2c_reduce_price_goods` (
  `id`              int(11) not null auto_increment,
  `reduce_price_id` int(11) not null comment '限时减价活动id',
  `goods_id`        int(11) not null comment '商品id',
  `discount`        decimal(10, 2)   default null comment '打几折',
  `reduce_price`    decimal(10, 2)   default null comment '减多少钱',
  `goods_price`     decimal(10, 2)   default null comment '折后价格',
  `is_checked`      tinyint(1)       default null comment '是否选中',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  unique key `reduce_goods` (`reduce_price_id`, `goods_id`)
);

--  限时减价活动商品规格
-- drop table if exists `b2c_reduce_price_product`;
create table `b2c_reduce_price_product` (
  `id`              int(11) not null auto_increment,
  `reduce_price_id` int(11) not null comment '限时减价活动id',
  `goods_id`        int(11) not null comment '商品id',
  `product_id`      int(11) not null comment '规格id',
  `prd_price`       decimal(10, 2)   default null comment '折后价格',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  unique key `reduce_product` (`reduce_price_id`,`goods_id`,`product_id`)
);

--  分销员等级表
-- drop table if exists `b2c_distributor_level`;
create table `b2c_distributor_level` (
  `id`                       int(6)  not null               auto_increment,
  `level_id`                 tinyint(2)      not null               default '0' comment '等级',
  `level_name`               varchar(32)  default null comment '等级名称',
  `level_up_route`           tinyint(1)      not null               default '0' comment '升级类型：0自动，1手动',
  `invite_number`            int(10)                                default null comment '邀请人数量（uo_route=0有效）',
  `total_distribution_money` decimal(10, 2)                         default null comment '推广金额（uo_route=0有效）',
  `total_buy_money`          decimal(10, 2)                         default null comment '推广和消费总额（uo_route=0有效）',
  `level_user_ids`           text  comment '等级用户id（uo_route=1有效）',
  `level_status`             tinyint(2)                             default '0' comment '状态:0停用，1启用',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  unique key `level_id` (`level_id`)
);

--  分销员等级变更表
-- drop table if exists `b2c_distributor_level_record`;
create table `b2c_distributor_level_record` (
  `id`             int(11)  not null               auto_increment,
  `user_id`        int(10)  not null               default '0' comment '用户id',
  `is_go_up`       tinyint(1)       not null               default '0' comment '升降：0降，1升',
  `old_level`      tinyint(2)       not null               default '1' comment '旧等级',
  `old_level_name` varchar(32)      null comment '旧等级名字',
  `new_level`      tinyint(2)       not null               default '1' comment '新等级',
  `new_level_name` varchar(32)   default null comment '新等级名字',
  `update_note`    varchar(120)  default null comment '更新备注',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 欧派记录价格变化表
-- drop table if exists `b2c_goods_opai_spec`;
create table `b2c_goods_opai_spec` (
  `id`         bigint(20) not null                    auto_increment,
  `goods_id`   int(11)                                default null,
  `prd_sn`     varchar(64)  default null,
  `prd_price`  decimal(10, 2)                         default null,
  `is_on_sale` tinyint(1)                             default '0' comment '1:上架，0:下架',
  `del_flag`   tinyint(1)                             default '0' comment '1:删除',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 订单商品返利表
-- drop table if exists `b2c_order_goods_rebate`;
create table `b2c_order_goods_rebate` (
  `rebate_id`          int(11)                  not null auto_increment,
  `order_sn`           varchar(20)  			not null default '0' comment '订单uuid',
  `goods_id`           mediumint(8) 			not null default 0 comment '商品id',
  `product_id`         mediumint(8)             not null default '0' comment '产品id',
  `rebate_level`       tinyint(2)               not null default '0' comment '返利级别：0自购，2二级',
  `rebate_user_id`     int(11)                  not null default '0' comment '返利用户id',
  `rebate_percent`     decimal(6, 4)            not null default '0.0000' comment '返利比例',
  `rebate_money`       decimal(10, 4) 			not null default 0.0000 comment '单商品返利金额',
  `total_rebate_money` decimal(10, 4) 			not null default 0.0000 comment '总返利金额',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`rebate_id`),
  key `order_sn` (`order_sn`, `product_id`),
  key `rebate_user_id` (`rebate_user_id`)
);

-- 拼团抽奖配置页
-- drop table if exists `b2c_group_draw`;
create table `b2c_group_draw` (
  `id`           int(11)                                not null auto_increment,
  `name`         varchar(50)  							not null comment '活动名称',
  `start_time`   timestamp null default null comment '开始时间',
  `end_time`     timestamp null default null comment '结束时间',
  `goods_id`     text        							comment '参与抽奖的商品id',
  `min_join_num` smallint(6)                            not null comment '开奖最小参与人数',
  `pay_money`    decimal(10, 2)                         not null comment '下单支付金额',
  `join_limit`   smallint(6)                            not null comment '参团限制',
  `open_limit`   smallint(6)                            not null comment '开团限制',
  `limit_amount` smallint(6)                            not null comment '最小成团人数',
  `to_num_show`  smallint(6)                                     default null comment '参与用户达到多少前端展示',
  `status`       tinyint(1)                             not null default '1' comment '1：启用 0：禁用',
  `is_draw`      tinyint(1) 							not null default 0 comment '是否已开奖',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  `del_flag`     tinyint(1)                                      default '0',
  `del_time`			timestamp      null	default null comment '删除时间',
  `reward_coupon_id` varchar(200) null   comment '拼团失败发放优惠券',
  primary key (`id`)
);

--  拼团抽奖邀请表
-- drop table if exists `b2c_group_draw_invite`;
create table `b2c_group_draw_invite` (
  `id`             int(11)                                 not null auto_increment,
  `action`         int(11)                                 not null default '1' comment '活动类型：1 拼团抽奖',
  `identity_id`    int(11)                                 not null comment '活动id',
  `path`           varchar(100)  						   not null comment '页面路径',
  `goods_id`       int(11)                                 not null comment '商品id',
  `group_id`       int(11)                                          default null comment '团id',
  `invite_user_id` int(11)                                 not null comment '邀请用户id',
  `user_id`        int(11)                                 not null comment '用户id',
  `is_new`         tinyint(4)                              not null default '0' comment '是否是新用户',
  `is_used`        tinyint(4)                              not null default '0' comment '记录是否已处理(已记录拼团信息)',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

--  拼团抽奖码记录
-- drop table if exists `b2c_join_draw_list`;
create table `b2c_join_draw_list` (
  `id`            int(11)    not null auto_increment,
  `group_draw_id` int(11)    not null comment '拼团抽奖id',
  `goods_id`      int(11)    not null comment '商品id',
  `group_id`      int(11)    not null comment '拼团id',
  `user_id`       int(11)    not null comment '抽奖用户id',
  `draw_id`       int(11)    not null comment '抽奖序列id',
  `is_win_draw`   tinyint(1) not null default '0' comment '是否已中奖',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

--  参团列表
-- drop table if exists `b2c_join_group_list`;
create table `b2c_join_group_list` (
  `id`              int(11)        not null                       auto_increment,
  `group_draw_id`   int(11)        not null comment '拼团抽奖id',
  `goods_id`        int(11)        not null comment '商品id',
  `group_id`        int(11)                 default null comment '拼团id',
  `user_id`         int(11)                 default null comment '用户id',
  `is_grouper`      tinyint(1)              default '0' comment '是否是团长 1是 0不是',
  `invite_user_id`  int(11)                 default null comment '邀请人',
  `order_sn`        varchar(20)         	default null comment '订单编号',
  `status`          tinyint(1)     not null default '0' comment '0:拼团中 1：已成团 2：未成团',
  `draw_status`     tinyint(1)     not null default '0' comment '0:未开奖 1：已开奖',
  `is_win_draw`     tinyint(1)     not null default '0' comment '是否已中奖 1：已中奖',
  `open_time`       timestamp          null     default null comment '开团时间',
  `end_time`        timestamp          null    default null comment '成团时间(达到最小成团数量就记录)',
  `draw_time`       timestamp          null    default null comment '开奖时间',
  `invite_user_num` int 			not null default '0' comment '邀请用户数',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
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

--  小程序链接列表
-- drop table if exists `b2c_mp_jump`;
create table `b2c_mp_jump` (
  `id`          int(11)                                 not null auto_increment,
  `app_id`      varchar(64)                             not null comment '小程序AppID',
  `app_name`    varchar(200)                            not null comment '小程序名称',
  `flag`        tinyint(1)                              not null default '0' comment '0:可用，1:停用',
  `del_flag`    tinyint(1)                              not null default '0' comment '0:未删除，1:已删除',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- 小程序可用appid记录
-- drop table if exists `b2c_mp_jump_usable`;
create table `b2c_mp_jump_usable` (
  `id`          int(11)                                not null auto_increment,
  `template_id` int(11)                                not null comment '小程序模板id',
  `app_id`      varchar(64)  not null comment '跳转小程序appid',
  `usable`      tinyint(1)                             not null default '0' comment '0:不可用，1：可用',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- 资产变动记录
-- drop table if exists `b2c_trades_record`;
create table `b2c_trades_record` (
  `id`            mediumint(8)                   not null auto_increment comment '交易记录id',
  `trade_time`    timestamp null default null comment '交易时间',
  `trade_num`     decimal(10, 2)                 not null default 0.00 comment '交易额',
  `user_id`       mediumint(8)                   not null default '0' comment '交易用户id',
  `trade_content` tinyint(1)                     not null default '0' comment '交易内容：0：现金，1：积分',
  `trade_type`    tinyint(2)                     not null default '0' comment '交易类型说明',
  `trade_flow`    tinyint(1)                     not null default '0' comment '资金流向：0：收入，1：支出，2：待确认收入',
  `trade_status`  tinyint(1)                     not null default '0' comment '交易状态：0：已入账，1：已到账',
  `trade_sn`      varchar(20)  					 not null default '' comment '交易单号',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- 资产变动记录统计
-- drop table if exists `b2c_trades_record_summary`;
create table `b2c_trades_record_summary` (
  `id`                 mediumint(8)  not null auto_increment,
  `type`               tinyint(2)            not null default '1' comment '统计类型：1,7,30',
  `income_total_money` decimal(10, 2)        not null default 0.00 comment '总现金收入',
  `outgo_money`        decimal(10, 2)        not null default 0.00 comment '现金支出',
  `income_real_money`  decimal(10, 2)        not null default 0.00 comment '净现金收入',
  `income_total_score` decimal(10, 2)        not null default 0.00 comment '总积分收入',
  `outgo_score`        decimal(10, 2)        not null default 0.00 comment '积分支出',
  `income_real_score`  decimal(10, 2)        not null default 0.00 comment '净积分收入',
  `ref_date`           date                  not null comment '2018-09-04',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- 分销提现记录
-- drop table if exists `b2c_distribution_withdraw`;
create table `b2c_distribution_withdraw` (
  `id`                int(11)                                not null auto_increment,
  `user_id`           int(11)                                not null comment '用户id',
  `type`              tinyint(1)                             null comment '提现类型  1微信公众号钱包提现 2小程序',
  `status`            tinyint(1)                             not null default 1 comment '处理状态 1待审核 2拒绝 3已审核待出账 4出账成功 5失败',
  `order_sn`          varchar(20)                            not null comment '提现单号',
  `withdraw_user_num` varchar(20)                            not null comment '用户提现序号',
  `withdraw_num`      varchar(20)                            not null comment '流水号',
  `withdraw_cash`     decimal(10, 2)                         not null comment '提现金额',
  `withdraw`          decimal(10, 2)                         not null comment '可提现金额',
  `desc`              text                                    comment '备注',
  `refuse_desc`       text                                    comment '驳回原因',
  `check_time`        timestamp null default null  comment '审核时间',
  `refuse_time`       timestamp null default null  comment '驳回时间',
  `billing_time`      timestamp null default null  comment '出账时间',
  `fail_time`         timestamp null default null  comment '失败时间',
  `desc_time`         timestamp null default null  comment '备注时间',
  `del_flag`          tinyint(1)                             not null default '0',
  `del_time`		  timestamp      null	default null comment '删除时间',
  `withdraw_source`   varchar(20)  not null default '' comment '申请时提现配置',
  `real_name`         varchar(32)                            null comment '真实姓名',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  unique key `order_sn` (`order_sn`)
) engine = innodb default charset = utf8mb4 collate = utf8mb4_unicode_ci;

-- -- 商品品牌
-- drop table if exists `b2c_goods_brand`;
create table `b2c_goods_brand` (
  `id`          int(11)     	not null auto_increment,
  `brand_name`  varchar(500) 	not null comment '品牌名称',
  `e_name`      varchar(500) 	not null default '' comment '品牌英文名称',
  `logo`        varchar(255)         	default null comment '品牌logo',
  `first`       tinyint(3)  	not null default '0' comment '优先级',
  `del_flag`    tinyint(1)  	not null default '0' comment '0为未删除 1为删除',
  `desc`        text							comment '品牌介绍',
  `is_recommend` tinyint(1) 	default 0 not null comment '是否为推荐品牌 0否 1是',
  `classify_id`  int(11) 		default 0 not null comment '品牌所属分类 0未分类 否则是分类id',
  `create_time`		timestamp   not null   	default current_timestamp,
  `update_time` 	timestamp   not null    	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 退货退款/退款状态记录
-- drop table if exists `b2c_return_status_change`;
create table `b2c_return_status_change` (
  `id`       int(11)     not null auto_increment,
  `ret_id`   int(11)     not null comment '退货申请id',
  `user_id`  int(11)     not null comment '用户id',
  `type`     tinyint(1)  		default null comment '0 商家触发   1是用户触发  2系统自动处理',
  `status`   tinyint(1)  not null default '1' comment '更改状态',
  `order_sn` varchar(20) not null comment '订单号',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  `desc`     text        comment '备注' ,
  primary key (`id`)
) collate = 'utf8mb4_unicode_ci' engine = innodb;

-- unlimit小程序码
-- drop table if exists `b2c_wxp_unlimit_code`;
create table `b2c_wxp_unlimit_code` (
  `code_id`   int(10)      not null auto_increment comment '小程序码id',
  `scene_id`  varchar(32)  not null comment '对应b2c_wxp_unlimit_scene的scene_id',
  `code_page` varchar(191)          default null comment '必须是已经发布的小程序存在的页面（否则报错），例如 pages/index/index, 根路径前不要填加 /,不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面',
  `code_url`  varchar(191)          default null comment '小程序码url',
  `code_path` varchar(191)          default null comment '小程序码本地地址',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  `del_flag` 		tinyint(1) 		not null default 0  null,
  `del_time`		timestamp      null	default null comment '删除时间',
  primary key (`code_id`),
  key `scene_id` (`scene_id`, `code_page`)
);

-- unlimit小程序码scene值保存表
-- drop table if exists `b2c_wxp_unlimit_scene`;
create table `b2c_wxp_unlimit_scene` (
  `scene_id`    int(10) 		not null  auto_increment,
  `scene_value` varchar(1200)  	default null,
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`scene_id`)
);

-- 一口价活动
-- drop table if exists `b2c_package_sale`;
create table `b2c_package_sale` (
  `id`             int(10)   		not null auto_increment,
  `package_name`   varchar(120)    	default null comment '名称',
  `start_time`     timestamp 	null	default null comment '开始时间',
  `end_time`       timestamp 	null	default null comment '结束时间',
  `total_money`    decimal(10, 2)   not null default '0.00' comment '结算总价格',
  `goods_group_1`  tinyint(2)       not null default '0' comment '分组一，1启用',
  `group_name_1`   varchar(20)      default null comment '分组名称',
  `goods_number_1` mediumint(11)     not null default '0' comment '分组商品数',
  `goods_ids_1`    text  comment '分组goodsids',
  `cat_ids_1`      text  comment '分组平台分类id',
  `sort_ids_1`     text  comment '分组商家分类id',
  `goods_group_2`  tinyint(2)       not null default '0' comment '分组二，1启用',
  `group_name_2`   varchar(20)      default null comment '分组名称',
  `goods_number_2` mediumint(11)     not null default '0' comment '分组商品数',
  `goods_ids_2`    text  comment '分组goodsids',
  `cat_ids_2`      text  comment '分组平台分类id',
  `sort_ids_2`     text  comment '分组商家分类id',
  `goods_group_3`  tinyint(2)       not null default '0' comment '分组三，1启用',
  `group_name_3`   varchar(20)      default null comment '分组名称',
  `goods_number_3` mediumint(11)     not null default '0' comment '分组商品数',
  `goods_ids_3`    text  comment '分组goodsids',
  `cat_ids_3`      text  comment '分组平台分类id',
  `sort_ids_3`     text  comment '分组商家分类id',
  `status`         tinyint(2)       not null default '1' comment '活动状态1启用',
  `del_flag`       tinyint(2)       not null default '0' comment '删除状态',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- 一口价已选商品
-- drop table if exists `b2c_package_goods_cart`;
create table `b2c_package_goods_cart` (
  `id`           int(11)  	not null auto_increment,
  `user_id`      int(11)   	not null default '0' comment '用户id',
  `package_id`   int(11)   	not null default '0' comment '一口价活动id',
  `group_id`     tinyint(2)	not null default '1' comment '商品组id',
  `goods_id`     int(11)	not null default '0' comment '商品id',
  `product_id`   int(11)    not null default '0' comment '产品id',
  `goods_number` int(11)    not null default '1' comment '商品数量',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `user_id` (`user_id`, `package_id`, `group_id`)
);

-- -- 会员卡订单表
-- drop table if exists `b2c_card_order`;
create table `b2c_card_order` (
  `order_id`          int(11)          	not null auto_increment comment '订单id',
  `card_id`           int(11)           not null default '0' comment '会云卡id',
  `card_no`           varchar(32) 		not null default '0'      null comment '会员卡no',
  `order_sn`          varchar(20)       not null default '' comment '订单编号',
  `user_id`           mediumint(8)      not null default '0' comment '用户id',
  `order_status`      tinyint(1)        not null default '0' comment '订单状态',
  `order_status_name` varchar(32) 		not null default '' comment '订单状态名称',
  `invoice_id`        int(11)           not null default '0' comment '发票id',
  `invoice_detail`    text comment '发票内容：json存储',
  `add_message`       varchar(191)      not null default '' comment '客户留言',
  `pay_code`          varchar(30)                default null comment '支付代号',
  `pay_name`          varchar(120)               default null comment '支付名称',
  `prepay_id`         varchar(191)               default null comment '微信支付id，用于发送模板消息',
  `pay_sn`            varchar(32)                default null comment '支付流水号',
  `money_paid`        decimal(10, 2)    not null default '0.00' comment '订单应付金额',
  `use_account`       decimal(10, 2)    not null default '0.00' comment '用户消费余额',
  `use_score`         decimal(10, 2)    not null default '0.00' comment '用户消费余额',
  `order_amount`      decimal(10, 2)    not null default '0.00' comment '订单总金额',
  `pay_time`          timestamp             null     default null comment '支付时间',
  `seller_remark`     varchar(512)      not null default '' comment '卖家备注',
  `star_flag`         tinyint(1)        not null default 0 comment '标星订单：0 未标星 1 标星',
  `del_flag`          tinyint(1)        not null default '0' comment '删除',
  `ali_trade_no`      varchar(60)       not null default '' comment '支付宝交易单号',
  `return_flag`       tinyint(1)        not null default '0' comment '0:未申请退款，1：退款失败，2：退款成功',
  `return_score`      decimal(10, 2)    not null default '0.00' comment '退款积分',
  `return_account`    decimal(10, 2)    not null default '0.00' comment '退款余额',
  `return_money`      decimal(10, 2)    not null default '0.00' comment '退款余额',
  `return_time`       timestamp              null    default null comment '退款时间',
  `del_time`       	timestamp                 null   default null comment '退款时间',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`order_id`)
);

-- -- 会员卡激活审核表
-- drop table if exists `b2c_card_examine`;
create table `b2c_card_examine` (
  `id`             int(11)         not null auto_increment comment '订单id',
  `card_id`        int(11)                      not null default '0' comment '会云卡id',
  `card_no`        varchar(32) 					not null default '0' comment '会员卡no',
  `user_id`        mediumint(8)         		not null default '0' comment '用户id',
  `status`         tinyint(1)                   not null default '1' comment '审核状态 1审核中 2通过 3拒绝',
  `desc`           varchar(512)                 null comment '备注',
  `real_name`      varchar(50)                  null comment '真是姓名',
  `cid`            varchar(18)                  null comment '身份证号',
  `province_code`  mediumint(10)                null comment '所在地',
  `city_code`      mediumint(10)                null comment '所在地',
  `district_code`  mediumint(10)                null comment '所在地',
  `sex`            char(5)                      null comment '性别',
  `birthday_year`  int(4)                       null comment '生日',
  `birthday_month` int(2)                       null comment '生日',
  `birthday_day`   int(2)                       null comment '生日',
  `marital_status` tinyint(1)                   null comment '婚姻状况',
  `education`      tinyint(1)                   null comment '教育程度',
  `industry_info`  tinyint(1)                   null comment '所在行业',
  `pass_time`      timestamp                    null default null comment '通过时间',
  `refuse_time`    timestamp                    null default null comment '拒绝时间',
  `refuse_desc`    varchar(512)                 null comment '拒绝理由',
  `del_flag`       tinyint(1)                   not null default '0' comment '删除',
  `def_time`       timestamp                    null default null comment '删除时间',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 会员卡专享商品关联表
-- drop table if exists `b2c_goods_card_couple`;
create table `b2c_goods_card_couple` (
  `id`      int(11)        	not null auto_increment comment '会员卡专属商品关联id',
  `card_id` varchar(100)  	not null comment '会员卡id',
  `gcta_id` int(11)        	default '0' comment '商品或类型id',
  `type`    tinyint(1)     	default '0' comment '标签关联类型： 1：关联商品 2：关联商家分类 3：关联平台分类',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 会员卡专享商品关联表
-- drop table if exists `b2c_presale`;
create table `b2c_presale` (
  `id`               int(11)                         not null auto_increment comment '活动id',
  `presale_type`     tinyint(2)                             not null default '0' comment '预售类型1：全款',
  `presale_name`     varchar(32)  							not null comment '预售活动名称',
  `pre_pay_step`     tinyint(2)                             not null default '1' comment '定金期数1|2',
  `pre_start_time`   timestamp null default null comment '定金一期支付开始时间',
  `pre_end_time`     timestamp null default null comment '定金一期支付结束时间',
  `pre_start_time_2` timestamp null default null comment '定金二期支付开始时间',
  `pre_end_time_2`   timestamp null default null comment '定金二期支付结束时间',
  `start_time`       timestamp null default null comment '尾款支付开始时间',
  `end_time`         timestamp null default null comment '尾款支付结束时间',
  `goods_id`         int(11)                                 not null default '0' comment '商品id',
  `deliver_type`     tinyint(2)                             not null default '0' comment '发货时间模式1:deliver_time;2:deliver_days',
  `deliver_time`     timestamp null default null comment '发货日期',
  `deliver_days`     int(6)                                 not null default '0' comment '下单后几日发货',
  `discount_type`    tinyint(2)                             not null default '0' comment '优惠策略1:可叠加0:不可叠加',
  `return_type`      tinyint(2)                             not null default '0' comment '退定金模式1:自动退定金0:不退定金',
  `show_sale_number` int(6)                                 not null default '0' comment '是否显示销量1:显示',
  `buy_type`         tinyint(2)                             not null default '0' comment '是否支持原价1:支持',
  `buy_number`       int(6)                                 not null default '0' comment '单用户最多可购买数量',
  `status`           tinyint(2)                             not null default '1' comment '状态1:启用0:停用',
  `del_flag`         tinyint(2)                             not null default '0' comment '删除状态1:删除',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  `share_config`      text                                  comment '分享设置',
  primary key (`id`)
);

-- -- 会员卡专享商品关联表
-- drop table if exists `b2c_presale_product`;
create table `b2c_presale_product` (
  `id`                   int(11)  not null auto_increment,
  `presale_id`           int(11)           not null default '0' comment '预售id',
  `goods_id`             int(11)           not null default '0' comment '商品id',
  `product_id`           int(11)           not null default '0' comment '产品id',
  `presale_price`        decimal(10, 2)   not null default '0.00' comment '预售价格',
  `presale_number`       int(11)           not null default '0' comment '预售商品数量',
  `sale_number`          int(6)           not null default '0',
  `presale_money`        decimal(10, 2)   not null default '0.00' comment '预售定金金额',
  `pre_discount_money_1` decimal(10, 2)   not null default '0.00' comment '预售一阶段定金抵扣金额',
  `pre_discount_money_2` decimal(10, 2)   not null default '0.00' comment '预售二阶段定金抵扣金额',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `presale_id` (`presale_id`, `goods_id`, `product_id`),
  key `presale_id_2` (`presale_id`, `product_id`)
);

-- -- 会员卡订单退款记录
-- drop table if exists `b2c_refund_card_record`;
create table `b2c_refund_card_record` (
  `rec_id`      int(11)                        not null auto_increment,
  `order_sn`    varchar(30)  				   not null default '',
  `user_id`     int(11)                        not null default '0',
  `use_score`   decimal(10, 2)                 not null default '0.00' comment '积分抵扣金额',
  `use_account` decimal(10, 2)                 not null default '0.00' comment '退款余额',
  `money_paid`  decimal(10, 2)                 not null default '0.00' comment '退款余额',
  `refund_time` timestamp                      not null default current_timestamp comment '订单退款时间',
  `is_success`  tinyint(1)                     not null default '0' comment '人工处理状态，1：退款失败，2：退款成功',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`rec_id`),
  key `order_sn` (`order_sn`)
);

-- --  页面分类
-- drop table if exists `b2c_page_classification`;
create table `b2c_page_classification` (
  `id`          int(10)  not null auto_increment comment 'id',
  `shop_id`     int(11)          not null default 0 comment '店铺id',
  `name`        varchar(60)      not null default '' comment '分类名称',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key (`shop_id`)
);

--  支付送券记录表
-- drop table if exists `b2c_coupon_payreward_record`;
create table `b2c_coupon_payreward_record` (
  `id`                int(11)     not null auto_increment,
  `activity_id`       int(11)     not null comment '活动id',
  `user_id`           int(11)     not null,
  `receive_time`      timestamp null default null comment '领取时间',
  `mrking_voucher_id` varchar(500)         default null comment '已领取的优惠券',
  `order_sn`          varchar(20) not null default '' comment '订单编号',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- --  短信发送记录
-- drop table if exists `b2c_sms_send_record`;
create table `b2c_sms_send_record` (
  `id`            int         not null auto_increment,
  `account_name`  varchar(50) not null comment '账号',
  `user_id`       int         not null,
  `mobile`        varchar(20) not null,
  `request_msg`   text			comment '请求内容',
  `response_code` varchar(50) comment '响应码',
  `response_msg`  text comment '响应内容',
  `ext`           varchar(20) comment '行业账号 默认:行业 market:营销,checkcode:验证码',
  `sms`           varchar(20) comment '短信通道 默认短信策略:mxt',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- --  代付表
-- drop table if exists `b2c_sub_order_info`;
create table `b2c_sub_order_info` (
  `id`            int            not null auto_increment,
  `sub_order_sn`  varchar(20)    not null comment '代付订单号',
  `main_order_sn` varchar(20)    not null comment '主订单号',
  `user_id`       int            not null comment '用户id',
  `username`      varchar(50)    null comment '用户昵称',
  `order_status`  tinyint(1)     not null default 0 comment '代付订单状态',
  `money_paid`    decimal(10, 2) not null default 0.00 comment '付款金额',
  `refund_money`  decimal(10, 2) not null default 0.00 comment '退款金额',
  `pay_code`      varchar(20)    null comment '支付代号',
  `pay_name`      varchar(100)   null comment '支付名称',
  `pay_sn`        varchar(32) comment '支付流水号',
  `prepay_id`     varchar(200) comment '微信支付id',
  `message`       varchar(200) comment '留言',
  `pay_time`      timestamp null default null comment '支付时间',
  `refund_time`   timestamp null default null comment '最近一次退款时间',
  `del_flag`      tinyint(1)              default 0,
  `del_time`	  timestamp      null	default null comment '删除时间',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  unique index `sub_order_sn` (`sub_order_sn`),
  index `main_order_sn` (`main_order_sn`),
  index `user_id` (`user_id`),
  index `order_status` (`order_status`),
  primary key (`id`)
);

-- --  用户导入主表
-- drop table if exists `b2c_user_import`;
create table `b2c_user_import` (
  `id`          int not null auto_increment,
  `card_id`     varchar(100) comment '会员卡id',
  `total_num`   int          default 0 comment '总数',
  `success_num` int          default 0 comment '成功数',
  `del_flag`    	tinyint(1)      default 0,
  `del_time`		timestamp      null	default null comment '删除时间',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- --  用户导入明细表
-- drop table if exists `b2c_user_import_detail`;
create table `b2c_user_import_detail` (
  `id`                 int(11) not null auto_increment,
  `batch_id`           int(11) not null comment '主表id',
  `mobile`             varchar(15)      default null comment '手机号',
  `name`               varchar(50)      default null comment '姓名',
  `invite_user_mobile` varchar(15)      default null comment '邀请人手机号',
  `score`              int(11)          default null comment '积分',
  `sex`                char(5)          default null comment '性别： 女f 男m',
  `birthday`           varchar(15)      default null comment '生日',
  `province`           varchar(10)      default null comment '省',
  `city`               varchar(10)      default null comment '市',
  `district`           varchar(10)      default null comment '区',
  `address`            varchar(100)     default null comment '地址',
  `id_number`          varchar(18)      default null comment '身份证号',
  `education`          varchar(50)      default null comment '教育程度',
  `industry`           varchar(50)      default null comment '所在行业',
  `marriage`           varchar(50)      default null comment '婚姻状况',
  `income`             decimal(10, 2)   default null comment '月收入',
  `error_msg`          varchar(100)     default null comment '错误内容',
  `card_id`            varchar(100)     default null comment '会员卡id',
  `is_activate`        tinyint(1)       not null default '0' comment '是否已激活',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `mobile` (`mobile`),
  key `batch_id` (`batch_id`)
);

-- --  下单必填信息
-- drop table if exists b2c_order_must;
create table `b2c_order_must` (
  `id`                  int          not null auto_increment,
  `order_sn`            varchar(20)  not null comment '订单号',
  `must_content`        varchar(100) not null comment '必填信息',
  `order_real_name`     varchar(50)  null comment '下单人真实姓名',
  `order_cid`           varchar(50)  null comment '下单人身份证号码',
  `consignee_real_name` varchar(50)  null comment '收货人真实姓名',
  `consignee_cid`       varchar(50)  null comment '收货人身份证号码',
  `custom_title`        varchar(50)  null comment '自定义信息标题',
  `custom`              varchar(50)  null comment '自定义信息内容',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  unique index `order_sn` (`order_sn`),
  primary key (`id`)
);

-- --  会员卡批次表
-- drop table if exists b2c_card_batch;
create table `b2c_card_batch`(
  `id` 		int(11) 	not null auto_increment,
  `card_id` int(11) 	default 0 comment '卡号id',
  `action` 	tinyint(1) 	default 1 comment '领取码获得方式 1：自动生成 2：导入',
  `name` 	varchar(200) not null comment '批次名称',
  `code_size` 		tinyint(1) 	default 0 comment '领取码位数',
  `card_size` 		tinyint(1) 	comment '卡号位数',
  `card_pwd_size` 	tinyint(1) 	comment '卡密码位数',
  `number` 		int(11) 	default 0 comment '发放数量',
  `code_prefix` varchar(10) null   comment '领取码前缀',
  `card_prefix` varchar(10) null   comment '卡前缀',
  `del_flag` tinyint(1) 	default 0,
  `del_time`			timestamp      null	default null comment '删除时间',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  index `card_id` (`card_id`),
  index `action` (`action`)
);

-- --  会员卡领取码表
-- drop table if exists b2c_card_receive_code;
create table `b2c_card_receive_code`(
  `id` 			int(11) 	not null auto_increment,
  `card_id` 	int(11) 	not null default 0 comment '卡号id',
  `batch_id` 	int(11) 	not null comment '批次id',
  `group_id` 	int(11) 	not null default 1 comment '分组id',
  `code` 		varchar(15) comment '领取码',
  `card_no` 	varchar(30) comment '卡号',
  `card_pwd` 	varchar(20) comment '卡密码',
  `user_id` 		int(11) 	not null default 0   comment '领取人',
  `receive_time` 	timestamp 	null	default null   comment '领取时间',
  `error_msg` 		varchar(200) 	null   comment '错误说明',
  `del_flag` 		tinyint(1) 	not null default 0,
  `del_time`		timestamp     null 	default null comment '删除时间',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  index `card_id` (`card_id`),
  index `batch_id` (`batch_id`)
);

-- --  分销员分组表
-- drop table if exists b2c_distributor_group;
create table `b2c_distributor_group` (
  `id` 			int(11)  		not null auto_increment comment '分组id',
  `group_name` 	varchar(32)  	not null comment '分组名字',
  `is_default` 	tinyint(1) 		default '0' comment '是否为默认',
  `del_flag` 	tinyint(1) 		default '0' comment '是否删除',
  `del_time`		timestamp      null	default null comment '删除时间',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- --  赠品活动
-- drop table if exists b2c_gift;
create table `b2c_gift`(
  `id` 		int(11) 			not null auto_increment,
  `name` 	varchar(100) 		not null comment '活动名称',
  `level` 	smallint(4) 		not null default 0 comment '优先级',
  `start_time` 	timestamp null default null comment '开始时间',
  `end_time` 	timestamp null default null comment '结束时间',
  `goods_id` 	text comment '活动商品',
  `rule` 		text comment '赠品策略',
  `explain` 	text comment '说明',
  `status` 		tinyint(1) default 1,
  `del_flag` 	tinyint(1) default 0,
  `del_time`	timestamp      null	default null comment '删除时间',
  `create_time`	timestamp      	default current_timestamp,
  `update_time` timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  index `level` (`level`)
);

-- --  赠品规格商品
-- drop table if exists b2c_gift_product;
create table `b2c_gift_product`(
  `id` 			int(11) 		not null auto_increment,
  `gift_id` 	int(11) 		not null comment '赠品活动id',
  `product_id` 	int(11) 		not null comment '规格id',
  `product_number` int(11) 		not null comment '库存',
  `create_time`	timestamp      	default current_timestamp,
  `update_time` timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  index `gift_id` (`gift_id`),
  index `product_id` (`product_id`)
);

-- --  商品导入主表
-- drop table if exists `b2c_goods_import`;
create table `b2c_goods_import` (
  `id`          int(11)		 not null auto_increment,
  `total_num`   int(11)          default 0 comment '导入总数',
  `success_num` int(11)          default 0 comment '导入成功数',
  `del_flag`    tinyint(1)      	default 0,
  `del_time`	timestamp      	null	default null comment '删除时间',
  `import_file_path` varchar(120) 	not null comment '导入源文件地址',
  `is_update`    tinyint(1) 		not null default 0 comment '是否更新：0新增，1更新',
  `create_time`	timestamp      	default current_timestamp,
  `update_time` timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- --  商品导入明细表
-- drop table if exists `b2c_goods_import_detail`;
create table `b2c_goods_import_detail` (
  `id`                 int(11) not null auto_increment,
  `batch_id`           int(11) not null comment '主表id',
  `goods_sn`           varchar(32)      default null comment '商品sn',
  `prd_sn`             varchar(32)      default null comment '规格sn',
  `goods_name`         varchar(120)     default null comment '商品名称',
  `prd_desc`           varchar(120)     default null comment '规格描述',
  `error_msg`          varchar(100)     default null comment '错误内容',
  `is_success`         tinyint(1)          not null default 0 comment '导入成功标识：0不成功，1成功',
  `create_time`	timestamp      	default current_timestamp,
  `update_time` timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `goods_sn` (`goods_sn`),
  key `prd_sn` (`prd_sn`)
);

-- -- 渠道页面
-- drop table if exists `b2c_channel`;
create table `b2c_channel` (
  `id`                  int(11)  not null auto_increment comment '渠道页id',
  `page_id`             int(11)  not null  comment 'page_id',
  `goods_id`            int(11)  not null  comment 'goods_id',
  `channel_name`      	varchar(100)        not null default '' comment '渠道页名称',
  `source_type`         tinyint(2)          not null default 0 comment '来源类型 0自定义 1商品',
  `share`          		varchar(191)        not null default '' comment '分享码',
  `del_flag`            tinyint(1)          not null default '0' comment '0正常，1废除',
  `del_time`			timestamp      null	default null comment '删除时间',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 渠道统计
-- drop table if exists `b2c_channel_record`;
create table `b2c_channel_record` (
  `id`                   int(11)  not null auto_increment comment 'id',
  `channel_id`           int(11)  not null  comment '渠道页id',
  `user_id`              int(11)  not null  comment 'userid',
  `type`          		 tinyint(2)		not null default 0 comment '类型 1新用 0老用户',
  `count`                int(11)  		not null  comment '访问次数',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key (`user_id`)
);

-- -- 核销员表
-- drop table if exists `b2c_order_verifier`;
create table `b2c_order_verifier` (
  `id` 			int(11)  	not null auto_increment,
  `store_id` 	int(8)  	default null comment '门店id',
  `user_id` 	int(8)  	default null comment '用户id',
  `verify_orders` 	int(6) 		default null comment '核销订单数',
  `del_flag` 		tinyint(1) 	not null default '0' comment '删除',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `store_id` (`store_id`,`user_id`)
);

-- --  好物推荐
-- drop table if exists `b2c_wx_shopping_recommend`;
create table `b2c_wx_shopping_recommend` (
  `id` 			int(11) not null auto_increment,
  `user_id` 	int(11) not null,
  `goods_id` 	int(11) not null comment '商品id',
  `order_sn` 	int(11) default null comment '订单ordersn',
  `click_num` 	int(11) not null default 1 comment '点击次数',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `user_id` (`user_id`),
  key `goods_id` (`goods_id`)
);

-- --  自定义足迹
-- drop table if exists `b2c_index_foot_record`;
create table `b2c_index_foot_record` (
  `id`          	int(11)  not null auto_increment,
  `page_id`    		int(11)  not null comment '自定义页面id',
  `user_id`     	int(11)  not null,
  `count` 			int(11) 	not null default 1 null comment '浏览次数',
  `type` 			tinyint(2) 	not null default 0  null   comment '0 老用户 1新用户',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);
-- --渠道表
-- drop table if exists `b2c_channel_statistical`;
create table `b2c_channel_statistical` (
  `id`                          int(11)  not null auto_increment,
  `page_id`                     int(11)  not null default 0 comment '',
  `goods_id`                    int(11)  not null default 0 comment '',
  `channel_id`                  text comment '渠道id',
  `channel_all_pv`              text ,
  `channel_all_uv`              text ,
  `channel_new_pv`              text,
  `channel_new_uv`              text,
  `channel_old_pv`              text,
  `channel_old_uv`              text,
  `all_pv`                    	text,
  `all_uv`                    	text ,
  `new_pv`                     	text ,
  `new_uv`                     	text,
  `old_pv`                    	text ,
  `old_uv`                     	text,
  `ref_date`           			date		not null comment '2019-03-04',
  `ref_type`             		tinyint(1) 	not null default '0' comment '1昨天 7天 30天',
  `create_time`					timestamp      	default current_timestamp,
  `update_time` 				timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key `ref_date` (`ref_date`)
);

-- --  分销改价价格表
-- drop table if exists `b2c_goods_rebate_price`;
create table `b2c_goods_rebate_price` (
  `id` 				int(11)  not null auto_increment,
  `goods_id` 		int(11) default null comment '商品id',
  `product_id` 		int(11) default null,
  `advise_price` 	decimal(10,2) not null default 0.00,
  `min_price` 		decimal(10,2) default null,
  `max_price` 		decimal(10,2) default null,
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- --  分销改价价格表
-- drop table if exists `b2c_user_rebate_price`;
create table `b2c_user_rebate_price` (
  `id` 			int(11)  	not null auto_increment,
  `user_id` 	int(11) 	not null default '0' comment '用户id',
  `goods_id` 	int(11) 	not null default '0' comment '商品id',
  `product_id` 	int(11) 	not null default '0' comment '产品id',
  `advice_price` 	decimal(10,2) 	not null default 0.00 comment '分销价格',
  `expire_time` 	timestamp 		null default null comment '过期时间',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- --  搜索热词表
-- drop table if exists `b2c_search_history`;
create table `b2c_search_history`(
  `user_id` 		int(11) 		not null,
  `hot_words` 		varchar(100) 	not null comment '热词',
  `search_count` 	int(11) 		not null default 1   comment '搜索次数',
  `is_hot_words` 	tinyint(1) 		not null default 0   comment '是否是热词搜索',
  `del_flag` 		tinyint(1) 		not null default 0,
  `del_time` 		timestamp 	null	default null comment '过期时间',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  index `user_id` (`user_id`),
  index `hot_words` (`hot_words`)
);

-- --  请求外部记录表
-- drop table if exists `b2c_rebate_price_record`;
create table `b2c_rebate_price_record` (
  `id` 				int(11)  	not null auto_increment,
  `user_id` 		int(11) 	not null default '0' comment '用户id',
  `data_sign` 		varchar(50) not null default '' comment 'md5(rebate_data)',
  `rebate_data` 	text 		comment '分享内容',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  unique key `user_id` (`user_id`,`data_sign`)
);

 -- --  店铺自定义品牌分类
-- drop table if exists `b2c_brand_classify`;
create table `b2c_brand_classify` (
  `classify_id`     int(11)  		 not null auto_increment,
  `classify_name`   varchar(90)      not null default '',
  `first`       	smallint(2)      not null default '0' comment '优先级',
  `del_flag`        tinyint(1)       not null default '0',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
   primary key (`classify_id`)
);

 -- --  批量改价规格价格对,寺库专用
-- drop table if exists `b2c_batch_price`;
create table `b2c_batch_price` (
   `id` 		int(11)  		not null auto_increment,
   `prd_sn` 	varchar(65)  	not null default '' comment '商家编码',
   `price` 		decimal(10,2) 	not null default '0.00' comment '规格价格',
   `act_id` 	int(11) 		not null default '1' comment '导入批次',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
   primary key (`id`)
);

 -- --  品牌分类毛利对 寺库专用
-- drop table if exists `b2c_batch_profit`;
create table `b2c_batch_profit` (
  `id` 			int(11)  not null auto_increment,
  `brand_id` 	int(11) default '0' comment '品牌id',
  `sort_id` 	int(11) default '0' comment '分类id',
  `act_id` 		int(11) default '1' comment '导入批次',
  `profit_per` 	decimal(10,2) not null default '0.00',
  `file_name` 	varchar(200)  not null default '' comment '文件名称',
  `create_time`		timestamp      	default current_timestamp,
  `update_time` 	timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`)
);

-- -- 好友助力活动表
-- drop table if exists `b2c_friend_promote_activity`;
create table `b2c_friend_promote_activity` (
  `id`                   int(11)  not null auto_increment,
  `shop_id`              int(11)               not null default 0 comment '店铺id',
  `act_code`             varchar(32)           not null default '' comment '活动编码',
  `act_name`             varchar(120)          not null default '' comment '活动名称',
  `start_time`           timestamp null default null    comment '活动起始时间',
  `end_time`             timestamp null default null   comment '活动截止时间',
  `reward_type`          tinyint(1)            not null default 0 comment '奖励类型：0赠送商品，1折扣商品，2赠送优惠券',
  `reward_content`       text                  comment '奖励内容',
  `reward_duration`      int(8)                not null default 0 comment '奖励有效期',
  `reward_duration_unit` tinyint(1)            not null default 0 comment '奖励有效期单位：0小时，1天，2周，3月，4年',
  `promote_amount`        decimal(10,2)        not null default '0.00' comment '所需助力值',
  `promote_times`         int(5)        	   not null default 1 comment '所需助力次数',
  `launch_limit_duration` int(5)        		not null default 0 comment '发起次数限制时长，0不限制',
  `launch_limit_unit`    tinyint(1)             not null default 0 comment '发起次数限制时长单位：0天，1周，2月，3年',
  `launch_limit_times`   tinyint(3)    			not null default 0 comment '发起限制次数，0不限制',
  `share_create_times`   tinyint(3)    			not null default 1 comment '好友分享可获得助力次数',
  `promote_type`         tinyint(1)             not null default 0 comment '单次助力值类型：0平均，1随机',
  `promote_condition`     tinyint(1)    		not null default 0 comment '好友助力条件：0可不授权个人信息，1必须授权',
  `failed_send_type`     tinyint(1)    			not null default 0 comment '助力失败赠送类型：0不赠送，1优惠券，2积分',
  `failed_send_content`  int(8)                	not null default 0 comment '助力失败赠送内容',
  `activity_share_type`  tinyint(1)    			not null default 0 comment '助力活动分享样式类型：0默认样式，1自定义样式',
  `custom_share_word`    varchar(400)          not null default '' comment '自定义分享样式文案',
  `share_img_type`    	 tinyint(1)            not null default 0 comment '自定义分享图片类型：0首页截图，1自定义图片',
  `custom_img_path`      varchar(100)          not null default '' comment '自定义分享样式图片路径',
  `is_block`             tinyint(1)            not null default 0 comment '活动状态：0未停用，1已停用',
  `del_flag`             tinyint(1)            not null default 0 comment '删除标识：0未删除，1已删除',
  `use_discount`         tinyint(1)            not null default 0 comment '是否可与会员卡折扣、优惠券叠加使用：0不可叠加，1可叠加',
  `use_score`            tinyint(1)            not null default 1 comment '是否可使用积分抵扣部分金额：0不可抵扣，1可抵扣',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  unique key `act_code` (`act_code`),
  key `act_name` (`act_name`),
  key (`shop_id`)
);

-- -- 好友助力发起表
-- drop table if exists `b2c_friend_promote_launch`;
create table `b2c_friend_promote_launch` (
  `id`                   int(11)  			   not null auto_increment,
  `user_id`              int(11)               not null default 0  comment '发起会员id',
  `promote_id`           int(11)               not null default 0  comment '助力活动id',
  `promote_status`       tinyint(1)            not null default 0  comment '助力状态：0助力中，1助力完成待领取，2助力完成已领取，3助力失效，4助力未完成失败',
  `order_sn`             varchar(32)           not null default '' comment '助力完成生产的订单编码',
  `launch_time`          timestamp                      default current_timestamp comment '发起时间',
  `success_time`         timestamp null default null comment '助力成功时间',
  `del_flag`             tinyint(1)            not null default 0 comment '删除标识：0未删除，1已删除',
  `del_time` 			timestamp 	null	default null comment '过期时间',
  `create_time`			timestamp      	default current_timestamp,
  `update_time` 		timestamp      	default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key (`order_sn`),
  key (`user_id`),
  key (`promote_id`)
);

-- -- 好友助力明细表
-- drop table if exists `b2c_friend_promote_detail`;
create table `b2c_friend_promote_detail` (
  `id`                   int(11)  not null auto_increment,
  `launch_id`            int(11)               null     default 0 comment '助力活动发起id',
  `user_id`              int(11)               null     default 0 comment '助力会员id',
  `promote_id`           int(11)               null     default 0 comment '助力活动id',
  `promote_value`        int(11)                null     default 0 comment '助力值',
  `create_time`         	timestamp          default current_timestamp comment '助力时间',
  `update_time`          	timestamp          default current_timestamp on update current_timestamp comment '更新时间',
  primary key (`id`),
  key (`launch_id`),
  key (`user_id`),
  key (`promote_id`)
);

-- -- 可助力次数表
-- drop table if exists `b2c_friend_promote_times`;
create table `b2c_friend_promote_times` (
  `id`                   int(11)  				not null auto_increment,
  `launch_id`            int(11)               	not null default 0 comment '助力活动发起id',
  `user_id`              int(11)               	not null default 0 comment '助力会员id',
  `share_times`          int(8)                	not null default 0 comment '分享的次数',
  `own_promote_times`    int(8)                	not null default 0 comment '总的所有助力次数',
  `is_auth`              tinyint(1)             not null default 0 comment '是否有授权增加次数：0没有，1有',
  `create_time`          timestamp                      default current_timestamp comment '助力时间',
  `update_time`          timestamp                      default current_timestamp on update current_timestamp comment '更新时间',
  primary key (`id`),
  key (`launch_id`),
  key (`user_id`)
);

-- -- 操作记录表
-- drop table if exists `b2c_record_admin_action`;
create table `b2c_record_admin_action` (
  `id`        int(11)   not null auto_increment comment 'id',
  `sys_id`      int(11)   not null default '0' comment '操作员id',
  `account_id`    int(11)   not null default '0' comment 'sub操作员id',
  `action_type`   tinyint(2)  not null default '0' comment '操作类型',
  `template_id`     varchar(100)    not null  comment '模版id',
  `template_data`   varchar(200)    not null  comment '模版数据',
  `user_name`       varchar(60)   not null  comment '操作员名称',
  `mobile`          varchar(32)   not null  comment '操作员手机号',
  `create_time`     timestamp   default current_timestamp comment '助力时间',
  `update_time`     timestamp   default current_timestamp on update current_timestamp comment '更新时间',
  primary key (`id`)
);
-- --服务承诺表
-- drop table if exists `b2c_pledge`;
CREATE TABLE `b2c_pledge` (
  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pledge_name` varchar(100) NOT NULL DEFAULT '' COMMENT '承诺名称',
  `pledge_logo` varchar(255) DEFAULT NULL COMMENT '服务承诺的图标',
  `pledge_content` varchar(500) NOT NULL DEFAULT '' COMMENT '服务承诺的说明',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '开启状态1:开启，0:关闭',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识：0未删除，1已删除',
  PRIMARY KEY (`id`)
);
commit;

##测评活动表
##drop table if exists `b2c_assess_activity`;
create table `b2c_assess_activity` (
  `id`                int(11)               not null auto_increment,
  `shop_id`           int(11)               null     default 0 comment '店铺ID',
  `act_code`          varchar(32)           null     default '' comment '活动编码',
  `act_name`          varchar(120)          not null default '' comment '活动名称',
  `start_time`        datetime              not null comment '活动起始时间',
  `end_time`          datetime              not null comment '活动截止时间',
  `due_time_type`     tinyint(1)            null     default 0 comment '是否永久有效：0否，1是',
  `part_times_type`   tinyint(1)            null     default 0 comment '单用户参与次数类型：0不限制，1限制次数',
  `part_times_day`    int(4)                null     default 1 comment '每天最多参与次数：默认为1,0表示不限制',
  `part_times_total`  int(4)                null     default 1 comment '累计最多参与次数：默认为1,0表示不限制',
  `feedback_total`    int(4)                null     default 0 comment '活动总反馈数量：默认为0,0表示不限制',
  `assess_judge_type` tinyint(1)            null     default 0 comment '测评结果判断条件：0根据选项判断，1根据得分判断',
  `cover_style_type`  tinyint(1)            null     default 0 comment '封面样式类型：0默认样式，1自定义',
  `cover_style`       text comment '封面样式内容，json串',
  `assess_desc`       varchar(500)          not null default '' comment '测评介绍',
  `create_time`       timestamp       default current_timestamp,
  `update_time`       timestamp       default current_timestamp on update current_timestamp comment '最后修改时间',
  `is_block`          tinyint(1)            null     default 0 comment '活动状态：0未停用，1已停用',
  `del_flag`          tinyint(1)            null     default 0 comment '删除标识：0未删除，1已删除',
  `pub_flag`          tinyint(1)            null     default 0 comment '发布标识：0未发布，1已发布',
  primary key (`id`),
  key `act_name` (`act_name`),
  key (`shop_id`)
);

-- 测评活动题目表
-- drop table if exists `b2c_assess_topic`;
create table `b2c_assess_topic` (
  `id`                int(8)                not null auto_increment,
  `shop_id`           int(11)               null     default 0 comment '店铺ID',
  `assess_id`         int(11)               null     default 0 comment '测评活动ID',
  `topic_type`        tinyint(1)            null     default 0 comment '题目格式：0文本，1图片，2视频',
  `topic_type_path`   varchar(200)          null     default '' comment '题目图片、视频路径',
  `topic_title`       varchar(200)          null     default '' comment '题目标题',
  `topic_level`       tinyint(2)            null     default 1 comment '题目优先级，越小优先级越大，从1开始',
  `bg_img_type`       tinyint(1)            null     default 0 comment '题目背景类型：0默认，1自定义',
  `bg_img_path`       varchar(200)          null     default '' comment '题目背景图片路径',
  `option_type`       tinyint(1)            null     default 0 comment '选项类型：0单选，1多选',
  `option_skip_type`  tinyint(1)            null     default 0 comment '多选时跳转类型：1跳转到指定题目，2跳转导致指定结果',
  `option_skip_value` int(8)                null     default 0 comment '多选时跳转到指定题目ID或者结果ID',
  `option_content`    text comment '选项内容，json串，包括选项描述、图片和跳转逻辑或者分值',
  `result_id`         int(11)               null     default 0 comment '对应ID',
  `create_time`       timestamp       default current_timestamp,
  `update_time`       timestamp       default current_timestamp on update current_timestamp comment '最后修改时间',
  `del_flag`          tinyint(1)            null     default 0 comment '删除标识：0未删除，1已删除',
  primary key (`id`),
  key `assess_id` (`assess_id`),
  key (`shop_id`)
);

-- 测评活动结果表
-- drop table if exists `b2c_assess_result`;
create table `b2c_assess_result` (
  `id`                     int(11)                not null auto_increment,
  `shop_id`                int(11)               null     default 0 comment '店铺ID',
  `assess_id`              int(11)               null     default 0 comment '测评活动ID',
  `result`                 varchar(200)          null     default '' comment '测试结果',
  `min_point`              int(11)               null     default 0 comment '结果分值区间起始值',
  `max_point`              int(11)               null     default 0 comment '结果分值区间终值',
  `result_img_path`        varchar(200)          null     default '' comment '测试结果图片路径',
  `result_desc`            varchar(500)          null     default '' comment '测试结果描述',
  `reward_type`            tinyint(1)            null     default 0 comment '奖励类型：0不设置，1优惠券，2奖品，3积分，4余额，5自定义',
  `reward_info`            varchar(300)          null     default '' comment '奖励内容',
  `reward_limit_type`      tinyint(1)            null     default 0 comment '领奖限制类型：0无限制，1分享好友领奖品，2填写信息领奖品',
  `reward_limit_condition` varchar(32)           null     default '' comment '领奖限制条件,多选字符串逗号隔开：1真实姓名，2手机号，3身份证号码，4性别，5生日，6婚姻状况，7教育程度，8所在行业，9所在地',
  `bg_type`                tinyint(1)            null     default 0 comment '结果页背景类型：0默认，1自定义',
  `bg_img_path`            varchar(132)          null     default '' comment '结果页背景图片路径',
  `create_time`            timestamp       default current_timestamp,
  `update_time`            timestamp       default current_timestamp on update current_timestamp comment '最后修改时间',
  `del_flag`               tinyint(1)            null     default 0 comment '删除标识：0未删除，1已删除',
  primary key (`id`),
  key (`assess_id`),
  key (`shop_id`)
);

-- 测评活动记录表
-- drop table if exists `b2c_assess_record`;
create table `b2c_assess_record` (
  `id`           int(11)               not null  auto_increment,
  `shop_id`      int(11)               not null  default 0 comment '店铺ID',
  `user_id`      int(11)               not null  default 0 comment '会员ID',
  `assess_id`    int(11)               not null  default 0 comment '测评活动ID',
  `topic_id`     int(11)               not null  default 0 comment '测评得出结果的题目的ID',
  `result_id`    int(11)               not null  default 0 comment '测评结果ID',
  `assess_point` varchar(11)           null      default '' comment '测评总得分或者题目的选项编号',
  `create_time`   timestamp            default current_timestamp,
  `update_time`   timestamp            default current_timestamp on update current_timestamp comment '最后修改时间',
  `finish_time`  datetime              not null comment '测评完成时间',
  `del_flag`     tinyint(1)            not null  default 0 comment '删除标识：0未删除，1已删除',
  `state_status` tinyint(1)            not null  default 0 comment '测评状态：0测评中，1测评完成待领取奖励，2测评完成已领取，3领取失效，4测评失败',
  primary key (`id`),
  key (`assess_id`),
  key (`shop_id`),
  key (`user_id`)
);

-- 测评活动答题记录表
-- drop table if exists `b2c_assess_topic_record`;
create table `b2c_assess_topic_record` (
  `id`           int(11)               not null auto_increment,
  `record_id`    int(11)               not null default 0 comment '测评活动记录表关联ID',
  `topic_id`     int(11)               not null default 0 comment '测评题目的ID',
  `topic_option` varchar(11)           not null default '' comment '测评题目选项编号,多选的逗号隔开',
  `topic_point`  int(8)                not null default 0 comment '测评得分',
  `result_id`    int(11)               not null default 0 comment '测评结果ID',
  `create_time`  timestamp       default current_timestamp,
  `update_time`  timestamp       default current_timestamp on update current_timestamp comment '最后修改时间',
  primary key (`id`),
  key (`record_id`),
  key (`result_id`)
);

-- 7月12日 常乐 添加优惠礼包表结构
##优惠券礼包
#DROP TABLE IF EXISTS `b2c_coupon_pack`;
create table `b2c_coupon_pack` (
  `id`              int(11)                  not null auto_increment,
  `act_name`        varchar(100)             not null comment '活动名称',
  `start_time`      datetime                 not null comment '开始时间',
  `end_time`        datetime                 not null comment '结束时间',
  `pack_name`       varchar(20)              not null comment '礼包名称',
  `limit_get_times` tinyint(5)           not null default 0 comment '单用户领取限制次数，0不限制',
  `total_amount`    int(11)                  not null default '0' comment '总数量',
  `issued_amount`   int(11)                  not null default '0' comment '已发放数量',
  `access_mode`     tinyint(1)               not null default '0' comment '获取方式，0：现金购买，1：积分购买，2直接领取',
  `access_cost`     decimal(10, 2)           null     default 0.00 comment '价格（现金或积分，直接领取时该值为0）',
  `act_rule`        text collate utf8mb4_bin null comment '活动规则',
  `state`           tinyint(1)               not null default '1' comment '开启状态1:开启，0:停用',
  `create_time`     timestamp       default current_timestamp,
  `update_time`     timestamp       default current_timestamp on update current_timestamp comment '最后修改时间',
  `del_flag`        tinyint(1)                        default 0,
  `del_time`        timestamp      null default null,
  primary key (`id`)
);

