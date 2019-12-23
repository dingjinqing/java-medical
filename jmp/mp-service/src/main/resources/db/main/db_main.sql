/*
drop database if exists jmini_main;
create database jmini_main default charset utf8mb4 ;
use jmini_main;
*/
-- ddd
--
##商家账户系统
#drop table if exists `b2c_shop_account`;
create table `b2c_shop_account`
(
    `sys_id`             int(11)      not null auto_increment comment '店铺ID',
    `user_name`          varchar(64)  not null default '-- dd ##' comment '用户名',
    `password`           varchar(32)  not null default '' comment '密码',
    `state`              tinyint(1)            default '0' comment '1 入驻申请，2审核通过，3审核不通过,4已禁用',
    `business_state`     tinyint(1)            default '0' comment '营业状态 0未营业 1营业',
    `shop_grade`         tinyint(1)            default '0' comment '店铺等级：4旗舰店、3精品店、2专营店、1普通店',
    `max_sku_num`        int(11)               default '0' comment '最大上传sku数量，不同等级不同数量，管理员可修改',
    `max_shop_num`       int(11)               default '0' comment '最多小程序数量，不同等级不同数量，管理员可修改',
    `add_time`           timestamp    not null default CURRENT_TIMESTAMP comment '创建时间',
    `buy_time`           timestamp    null     default null comment '首次续费时间',
    `end_time`           timestamp    null     default null comment '到期时间',
    `last_login_shop_id` int(11)               default '0' comment '上次登录店铺ID',
    `mobile`             varchar(32)           default '' comment '店铺账户的手机号',
    `account_name`       varchar(32)           default '' comment '账户昵称',
    `shop_avatar`        varchar(255)          default '/image/admin/head_icon.png' comment '账户头像',
    `company`            varchar(255)          default '' comment '公司名称',
    `salesperson`        varchar(32)           default '' comment '销售员',
    `province_code`      varchar(10)           default '' comment '省',
    `city_code`          varchar(10)           default '' comment '市',
    `district_code`      varchar(10)           default '' comment '区',
    `address`            varchar(200)          default '' comment '详细地址',
    `base_sale`          tinyint(1)            default '0' comment '初始销量配置开关：0关闭，1开启',
    `backlog`            text         null comment '待办事项列表',
    `add_comment_switch` tinyint(1)            default '0' comment '商户自己添加评论开关：0关闭，1开启',
    `official_open_id`   varchar(128) null comment '公众号openid',
    `is_bind`            tinyint(1)            default 0 null comment '是否已绑定',
    primary key (`sys_id`),
    unique key (`user_name`)
);

##店铺角色表
#drop table if exists `b2c_shop_role`;
create table `b2c_shop_role`
(
    `role_id`        int(11)     not null auto_increment,
    `sys_id`         int(11)     not null default 0 comment '主账户ID',
    `shop_id`        int(11)     not null default 0 comment '店铺ID',
    `role_name`      varchar(50) not null default '' comment '角色名称',
    `privilege_list` text comment '权限列表，json数组存储',
    `create_time`    timestamp            default now(),
    `privilege_pass` text comment '权限密码，json数组存储',
    `role_pass`      varchar(50) comment '权限密码',
    primary key (`role_id`),
    key (`shop_id`),
    key (`sys_id`)
);

##商家子帐号
#drop table if exists `b2c_shop_child_account`;
create table `b2c_shop_child_account`
(
    `account_id`       int(11)      not null auto_increment,
    `sys_id`           int(11)      not null default 0 comment '主账户ID',
    `account_name`     varchar(191) not null default '' comment '子账号用户名',
    `account_pwd`      varchar(40)  not null default '' comment '子账号密码',
    `create_time`      timestamp             default now(),
    `mobile`           varchar(32)  not null default '0' comment '手机号',
    `backlog`          text         null comment '待办事项列表',
    `official_open_id` varchar(128) null comment '公众号openid',
    `is_bind`          tinyint(1)            default 0 null comment '是否已绑定',
    primary key (`account_id`),
    unique key (`sys_id`, `account_name`),
    key (`sys_id`)
);

##商家子帐号与店铺权限关联表
#drop table if exists `b2c_shop_child_role`;
create table `b2c_shop_child_role`
(
    `rec_id`     int(11) not null auto_increment,
    `sys_id`     int(11) not null default 0 comment '主账户ID',
    `account_id` int(11) not null default 0 comment '子账户ID',
    `shop_id`    int(11) not null default 0 comment '店铺ID',
    `role_id`    int(11) not null default 0 comment '角色ID',
    primary key (`rec_id`),
    unique key (`account_id`, `shop_id`),
    key (`sys_id`)
);

##店铺
#drop table if exists `b2c_shop`;
create table `b2c_shop`
(
    `shop_id`          int(11)        not null auto_increment comment '店铺ID',
    `sys_id`           int(11)        not null,
    `mobile`           varchar(32)    not null default '',
    `receive_mobile`   varchar(32)             default '' comment ' 接收通知手机号码',
    `shop_name`        varchar(50)             default '' comment '店铺名称',
    `shop_avatar`      varchar(191)            default '' comment '店铺头像',
    `shop_bg_path`     varchar(191)            default '' comment '店铺背景图片',
    `shop_phone`       varchar(50)             default '' comment '店铺客服电话',
    `shop_notice`      varchar(191)            default '' comment '店铺公告',
    `shop_wx`          varchar(191)            default '' comment '店铺客服微信',
    `shop_email`       varchar(191)            default '',
    `created`          timestamp      not null default now() comment '创建时间',
    `is_enabled`       tinyint(1)              default '1' comment '是否可用',
    `province_code`    mediumint(10)  not null default '0' comment '所在省',
    `province_name`    varchar(50)    not null default '',
    `city_code`        mediumint(10)  not null default '0' comment '所在城市',
    `city_name`        varchar(120)   not null default '',
    `district_code`    mediumint(10)  not null default '0' comment '所在区县',
    `district_name`    varchar(120)   not null default '',
    `address`          varchar(191)   not null default '' comment '所在地址',
    `complete_address` varchar(512)   not null default '' comment '所在完整地址',
    `shop_sell_type`   int            not null default 254 comment '经营品类,254：其他',
    `shop_qq`          varchar(20)             default '' comment '店铺客服QQ',
    `last_login_ip`    varchar(40)             default '' comment '上次登录IP',
    `state`            tinyint(1)              default 0 comment '0 入驻申请，1审核通过，2审核不通过',
    `business_state`   tinyint(1)              default 0 comment '营业状态 0未营业 1营业',
    `manage_fee`       decimal(10, 2)          default '0.00' not null comment '平台管理费',
    `surplus`          decimal(10, 2) not null default 0.0 comment '余额',
    `db_config`        text comment 'db config,json format',
    `shop_type`        varchar(20)    not null default 'v3' comment '店铺类型',
    `version_config`   text comment '店铺功能',
    `shop_flag`        tinyint(2)              default 0 null comment '店铺标志：0店家，1欧派，2嗨购',
    `member_key`       varchar(64)             default null comment '欧派店铺标识',
    `tenancy_name`     varchar(64)             default null comment '欧派创思大屏租户名称',
    `user_name`        varchar(64)             default null comment '欧派创思大屏用户名',
    `password`         varchar(64)             default null comment '欧派创思大屏密码',
    `sms_account`      varchar(50)    null comment '短信账号',
    `kuajinggou`       tinyint(1)              default '0' comment '跨境购',
    `order_real_name`  tinyint(1)              default '0' comment '下单实名制',
    `hid_bottom`       tinyint(1)              default 0 null comment '是否隐藏底部 1是 ',
    `logo`             varchar(200)   null comment '小程序logo',
    `currency`         varchar(45)  NOT NULL DEFAULT 'CNY' COMMENT '币种',
  	`shop_language`    varchar(45) NOT NULL DEFAULT 'zh_CN' COMMENT '语言',
    primary key (`shop_id`),
    key `mobile` (`mobile`)
);

##key value缓存表
#drop table if exists `b2c_cache`;
create table `b2c_cache`
(
    `id`      int(11) not null auto_increment,
    `k`       varchar(100) default '',
    `v`       text,
    `created` timestamp    default CURRENT_TIMESTAMP,
    primary key (`id`),
    unique key (`k`)
);

##店铺等级
#drop table if exists `b2c_shop_grade`;
create table `b2c_shop_grade`
(
    `id`           int(11)     not null auto_increment,
    `shop_grade`   varchar(64) not null comment '店铺等级',
    `max_sku_num`  int(11)     not null comment 'SKU数量',
    `max_shop_num` int(11)     not null comment '店铺数量',
    `manage_fee`   decimal(10, 2)       default '0.00' not null comment '平台管理费百分比',
    `flag`         tinyint(1)  not null default '0' comment '0:正常，1:删除',
    `in_time`      datetime,
    `up_time`      datetime,
    primary key (`id`)
);

##店铺平台管理费修改记录
#drop table if exists `b2c_shop_grade_log`;
create table `b2c_shop_grade_log`
(
    `id`           int(11)        not null auto_increment,
    `shop_id`      int(11)        not null,
    `shop_grade`   varchar(64)    not null comment '店铺等级',
    `max_sku_num`  int(11)        not null comment 'SKU数量',
    `max_shop_num` int(11)        not null comment '店铺数量',
    `manage_fee`   decimal(10, 2) not null default '0.00' comment '平台管理费百分比',
    `flag`         tinyint(1)     not null default '0' comment '0:正常，1:删除',
    `in_time`      datetime,
    `up_time`      datetime,
    primary key (`id`)
);

##平台账号
#drop table if exists `b2c_system_user`;
create table `b2c_system_user`
(
    `system_user_id` int         not null auto_increment,
    `user_name`      varchar(60) not null default '',
    `password`       varchar(32) not null default '',
    `mobile`         varchar(32) not null default '',
    `receive_mobile` varchar(32)          default '' comment ' 接收通知手机号码',
    `created`        timestamp   not null default now() comment '创建时间',
    `is_enabled`     tinyint(1)           default '1' comment '是否可用',
    `last_login_ip`  varchar(40)          default '' comment '上次登录IP',
    primary key (`system_user_id`)
);

##平台角色表
#drop table if exists `b2c_system_role`;
create table `b2c_system_role`
(
    `role_id`        int(11)     not null auto_increment,
    `system_user_id` int(11)     not null default 0 comment '平台账号ID',
    `role_name`      varchar(50) not null default '' comment '角色名称',
    `privilege_list` text comment '权限列表，json数组存储',
    `create_time`    timestamp            default now(),
    primary key (`role_id`),
    key (`system_user_id`)
);

##平台子帐号
#drop table if exists `b2c_system_child_account`;
create table `b2c_system_child_account`
(
    `account_id`     int(11)      not null auto_increment,
    `system_user_id` int(11)      not null default 0 comment '店铺ID',
    `account_name`   varchar(191) not null default '' comment '子账号用户名',
    `account_pwd`    varchar(40)  not null default '' comment '子账号密码',
    `role_id`        int(11)      not null default 0 comment '角色ID',
    `create_time`    timestamp             default now(),
    `mobile`         varchar(32)  not null default '0' comment '手机号',
    primary key (`account_id`),
    unique key (`account_name`),
    key (`system_user_id`)
);

##uploaded_image 上传图片表
#drop table if exists `b2c_uploaded_image`;
create table `b2c_uploaded_image`
(
    `img_id`         int(10)      not null auto_increment,
    `img_type`       varchar(60)  not null default '',
    `img_size`       int(10)      not null default '0',
    `img_name`       varchar(500) not null default '' comment '图片名称，用于前端显示',
    `img_orig_fname` varchar(500) not null default '',
    `img_path`       varchar(500) not null default '',
    `img_url`        varchar(500) not null default '',
    `img_cat_id`     int(10)               default 0 comment '图片分类',
    `img_width`      int(10)      not null default 0,
    `img_height`     int(10)      not null default 0,
    `is_refer`       tinyint(4)            default 0 comment '是否引用',
    `upload_time`    timestamp             default now(),
    `shop_id`        int(11)      not null default 0 comment '店铺ID',
    `del_flag`       tinyint(1)   not null default 0,
    `sys_id`         int(11)      not null default 0 comment '账户ID',
    `create_time`    timestamp             default current_timestamp,
    `update_time`    timestamp             default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`img_id`),
    key (`shop_id`),
    key (img_orig_fname)
);

## 上传图片分类
#drop table if exists `b2c_uploaded_image_category`;
create table `b2c_uploaded_image_category`
(
    `img_cat_id`        int(10)      not null auto_increment,
    `shop_id`           int(11)      not null default 0 comment '店铺ID',
    `img_cat_name`      varchar(60)  not null default '',
    `img_cat_parent_id` int(10)      not null default 0,
    `cat_ids`           varchar(191) not null default '0' comment '层级ID串,逗号分隔',
    `level`             tinyint(4)            default 0 comment '层级，0开始',
    `sort`              int(11)               default 1 comment '排序优先级',
    `create_time`       timestamp             default current_timestamp,
    `update_time`       timestamp             default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`img_cat_id`),
    key (`shop_id`)
);

##帮助中心-文章
#drop table if exists `b2c_article`;
create table `b2c_article`
(
    `article_id`      int(11)   NOT NULL AUTO_INCREMENT,
    `category_id`     int(11)   NOT NULL DEFAULT 1 COMMENT '文章分类',
    `title`           varchar(256)       DEFAULT NULL,
    `author`          varchar(50)        DEFAULT NULL,
    `keyword`         varchar(256)       DEFAULT NULL COMMENT '标签',
    `desc`            varchar(1024)      DEFAULT NULL COMMENT '文章描述',
    `content`         text               DEFAULT NULL,
    `is_recommend`    tinyint(1)         DEFAULT 0 COMMENT '1:推荐',
    `is_top`          tinyint(1)         DEFAULT 0 COMMENT '1:置顶',
    `status`          tinyint(1)         DEFAULT 0 COMMENT '0未发布,1已发布',
    `pub_time`        timestamp NULL     DEFAULT NULL COMMENT '发布时间',
    `update_time`     timestamp NULL     DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
    `create_time`     timestamp NULL     DEFAULT current_timestamp() COMMENT '创建时间',
    `last_visit_time` datetime           DEFAULT NULL,
    `pv`              int(11)            DEFAULT NULL,
    `head_pic`        varchar(191)       DEFAULT NULL COMMENT '头图',
    primary key (`article_id`),
    key (`is_recommend`),
    key (`is_top`)
);
##文章分类表
#drop table if exists `b2c_article_category`;
create table `b2c_article_category`
(
    `category_id`   int(11)      not null auto_increment,
    `category_name` varchar(191) not null default '',
    `use_footer_nav`     tinyint(1)   NOT NULL DEFAULT 0 COMMENT '是否用于底部导航',
    `add_time`      timestamp    NULL     DEFAULT current_timestamp() COMMENT '添加时间',
    `update_time`   timestamp    NULL     DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '更新时间',
    primary key (`category_id`)
);

## 小程序授权信息
#drop table if exists `b2c_mp_auth_shop`;
create table `b2c_mp_auth_shop`
(
    `app_id`                 varchar(191) not null comment '授权小程序appId',
    `shop_id`                int          not null,
    `nick_name`              varchar(191)          default '' comment '小程序昵称',
    `user_name`              varchar(191)          default '' comment '授权方小程序的原始ID',
    `alias`                  varchar(191)          default '' comment '授权方小程序所设置的微信号，可为空',
    `verify_type_info`       varchar(191)          default '' comment '授权方认证类型，-1代表未认证，0代表微信认证',
    `head_img`               varchar(191)          default '' comment '头像URL',
    `func_info`              text comment '权限集',
    `is_auth_ok`             tinyint(1)            default 1 comment '是否授权成功,如果小程序后台取消授权，则为0',
    `last_auth_time`         timestamp    null     default null comment '最后成功授权的时间',
    `last_cancel_auth_time`  timestamp    null     default null comment '最后取消授权的时间',
    `create_time`            timestamp             default now(),
    `qrcode_url`             varchar(191) comment '二维码图片的URL',
    `open_pay`               tinyint(1)            default 0 comment '是否开头微信支付',
    `open_card`              tinyint(1)            default 0 comment '是否开通微信卡券功能',
    `authorizer_info`        text comment '授权者信息,json存储',
    `authorization_info`     text comment '授权详情,json存储',
    `pay_mch_id`             varchar(191) comment '支付商户号',
    `pay_key`                varchar(191) comment '支付密钥',
    `pay_cert_content`       text comment '支付证书内容',
    `pay_key_content`        text comment '支付私钥内容',
    `is_modify_domain`       tinyint(1)            default 0 comment '是否修改开发和业务域名，0未修改，1已修改',
    `bind_template_id`       int                   default 0 comment '绑定小程序的模板ID',
    `upload_state`           tinyint(1)            default 0 comment '上传状态，0未上传，1已上传',
    `last_upload_time`       timestamp    null     default null comment '上传代码时间',
    `audit_id`               bigint(64)            default 0 comment '最新的审核ID',
    `audit_state`            tinyint(1)            default 0 comment '审核状态，0未提交，1审核中，2审核成功 3审核失败',
    `submit_audit_time`      timestamp    null     default null comment '提交代码审核时间',
    `audit_ok_time`          timestamp    null     default null comment '审核通过时间',
    `audit_fail_reason`      varchar(191)          default '' comment '未通过审核原因',
    `publish_state`          tinyint(1)            default 0 comment '通过审核的小程序发布状态，0未发布，1已发布',
    `publish_time`           timestamp    null     default null comment '程序发布时间',
    `tester`                 text comment '小程序体验者列表,json存储',
    `test_qr_path`           varchar(191) comment '小程序体验二维码图片路径',
    `category`               text comment '小程序可选类目,json存储',
    `page_cfg`               text comment '小程序页面配置,json存储',
    `principal_name`         varchar(191) not null default '' comment '小程序的主体名称',
    `bind_open_app_id`       varchar(191) not null comment '' comment '绑定开放平台appId',
    `link_official_app_id`   varchar(191) not null comment '' comment '关联公众号appId，用于发送模板消息',
    `is_sub_merchant`        tinyint(1)   not null default 0 comment '子商户模式,0：非子商户 1：微铺宝子商户 2：通联支付子商户',
    `union_pay_app_id`       varchar(191) not null default '' comment '通联支付子商户appId',
    `union_pay_cus_id`       varchar(191) not null default '' comment '通联支付子商户商户号',
    `union_pay_app_key`      varchar(191) not null default '' comment '通联支付子商户密钥',
    `fee_type`               varchar(191)          DEFAULT 'CNY' COMMENT '标价币种，国际支付字段',
    `merchant_category_code` varchar(191) NOT NULL DEFAULT '' COMMENT 'MCC码，国际支付字段',
    primary key (`app_id`),
    unique key (`shop_id`),
    key (`principal_name`),
    key (`bind_template_id`),
    key (`audit_state`),
    key (`is_auth_ok`),
    key (`link_official_app_id`)
);

## 公众号列表
#drop table if exists `b2c_mp_official_account`;
create table `b2c_mp_official_account`
(
    `app_id`                varchar(191) not null comment '授权公众号appId',
    `nick_name`             varchar(191)          default '' comment '小程序昵称',
    `user_name`             varchar(191)          default '' comment '授权方小程序的原始ID',
    `alias`                 varchar(191)          default '' comment '授权方小程序所设置的微信号，可为空',
    `verify_type_info`      varchar(191)          default '' comment '授权方认证类型，-1代表未认证，0代表微信认证',
    `head_img`              varchar(191)          default '' comment '头像URL',
    `func_info`             text comment '权限集',
    `is_auth_ok`            tinyint(1)            default 1 comment '是否授权成功,如果公众号后台取消授权，则为0',
    `last_auth_time`        timestamp    null     default null comment '最后成功授权的时间',
    `last_cancel_auth_time` timestamp    null     default null comment '最后取消授权的时间',
    `create_time`           timestamp             default now(),
    `open_pay`              tinyint(1)            default 0 comment '是否开头微信支付',
    `open_card`             tinyint(1)            default 0 comment '是否开通微信卡券功能',
    `authorizer_info`       text comment '授权者信息,json存储',
    `authorization_info`    text comment '授权详情,json存储',
    `pay_mch_id`            varchar(191) comment '支付商户号',
    `pay_key`               varchar(191) comment '支付密钥',
    `pay_cert_content`      text comment '支付证书内容',
    `pay_key_content`       text comment '支付私钥内容',
    `principal_name`        varchar(191)          default '' comment '公众号的主体名称',
    `account_type`          tinyint(1)   not null default '0' comment '公众号类型：0 订阅号, 1微信认证订阅号 2 服务号, 3微信认证服务号',
    `bind_open_app_id`      varchar(191) comment '' comment '绑定开放平台appId',
    `sys_id`                int(11)      not null default '0' comment '系统账户ID',
    `qrcode_url`            varchar(500) null     default null comment '二维码图片的URL',
    primary key (`app_id`),
    key (`principal_name`),
    key (`create_time`)
);

## 微信公众号用户表
#drop table if exists `b2c_mp_official_account_user`;
create table `b2c_mp_official_account_user`
(
    `rec_id`         int          not null auto_increment,
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


/*
 k='alipay_pay_fee_rate'  v=0.006
 k='bank_pay_fee_rate' v=0.006
 k='alipay_wd_fee_rate' v=0.0015
 k='alipay_pay_least' v=2
 k='alipay_pay_max' v=25
 k='alipay_can_wd_least' v=1
 k='alipay_wd_frequency_per_day' v=10000
 k='bank_can_wd_least' v=1
 k='bank_wd_frequency_per_day' v=10000
 k='wxpay_ds_pay_fee_rate' v=0.006
*/
#drop table if exists `b2c_system_cfg`;
create table `b2c_system_cfg`
(
    `rec_id` smallint(5) not null auto_increment,
    `sys_id` int(11)     not null,
    `k`      varchar(191) default '',
    `v`      text,
    primary key (`rec_id`),
    key (`sys_id`)
);

##用户
#drop table if exists `b2c_user`;
create table `b2c_user`
(
    `id`                 bigint(20)     not null auto_increment,
    `shop_id`            int(11)         not null default '0',
    `user_id`            int(11)     not null,
    `username`           varchar(100)   not null comment '用户名',
    `user_pwd`           varchar(60)    not null default '' comment '密码',
    `user_cid`           varchar(64)    not null default '',
    `mobile`             varchar(100)            default null comment '电话',
    `user_code`          varchar(100)            default null comment '会员卡号',
    `wx_openid`          varchar(128)   not null default '',
    `email`              varchar(100)            default null comment '邮箱',
    `create_time`        timestamp      not null default CURRENT_TIMESTAMP comment '创建时间',
    `wechat`             varchar(100)   not null default '' comment '微信',
    `fanli_grade`        int(11)        not null default '0' comment '返利会员级别',
    `user_grade`         int(11)        not null default '1' comment '会员级别',
    `invite`             int(11)        not null default '0' comment '',
    `invite_source`       varchar(32)            default null comment '邀请来源:groupbuy.拼团,bargain.砍价,integral.积分,seckill.秒杀,lottery.抽奖',
    `invitation_code`    int(8)         not null default '0' comment '邀请码',
    `account`            decimal(10, 2) not null default '0.00' comment '用户余额',
    `discount`           int(11)        not null default '0' comment '折扣',
    `discount_grade`     int(11)        not null default '0' comment '会员折扣等级',
    `del_flag`            tinyint(1)     not null default '0',
    `del_time`            timestamp      null     default null comment '删除时间',
    `growth`             int(11)                 default '0' comment '成长值',
    `score`              int(11)                 default '0' comment '积分',
    `source`             int(11)                 default '-1' comment '门店来源-1未录入0后台>0为门店',
    `invite_id`          int(11)                 default '0' comment '邀请人ID',
    `invite_expiry_date`  date                   default null comment '邀请失效时间',
    `wx_union_id`        varchar(191)   not null default '' comment '小程序union_id',
    `update_time`        timestamp      null     default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '最后修改时间',
    `is_distributor`     tinyint(2)              default 0 null comment '是否是分销员',
    `invite_act_id`      int(10)                 default 0 null comment '邀请来源活动ID',
    `distributor_level`  tinyint(2)              default '1' comment '用户等级',
    `ali_user_id`        varchar(191)   not null default '' comment '支付宝用户ID',
    primary key (`id`),
    unique key `un_user_id` (`user_id`, `shop_id`),
    key `user_id` (`user_id`),
    key `wx_openid` (`wx_openid`)
);
##用户详情
#drop table if exists `b2c_user_detail`;
create table `b2c_user_detail`
(
    `id`              int(11)     not null auto_increment,
    `user_id`         int(11)      not null,
    `shop_id`         int(11)     not null default 0 comment '店铺ID',
    `username`        varchar(100)         default null comment '昵称',
    `sex`             char(5)              default null comment '性别：女f,男m',
    `birthday_year`   int(4)               default null comment '生日年份',
    `birthday_month`  int(2)               default null,
    `birthday_day`    int(2)               default null,
    `email`           varchar(100)         default null comment '邮箱',
    `real_name`       varchar(50) null comment '真实姓名',
    `province_code`   mediumint(10)        default null comment '所在地省编号',
    `city_code`       mediumint(10)        default null comment '所在地市编号',
    `district_code`   mediumint(10)        default null comment '所在地区编号',
    `address`         varchar(120)         default null comment '所在地',
    `marital_status`  tinyint(1)           default null comment '婚姻状况：1未婚，2已婚，3保密',
    `monthly_income`  tinyint(1)           default null comment '月收入',
    `cid`             varchar(18)          default null comment '身份证号码',
    `education`       tinyint(1)           default null comment '教育程度',
    `industry_info`   tinyint(1)           default null comment '所在行业',
    `big_image`       varchar(191)         default null comment '头像',
    `bank_user_name`  varchar(100)         default null comment '开户行姓名',
    `shop_bank`       varchar(100)         default null comment '开户行',
    `bank_no`         varchar(32)          default null comment '开户行卡号',
    `withdraw_passwd` varchar(64)          default null comment '提现密码验证',
    `user_avatar`     varchar(191)         default '/image/admin/head_icon.png' comment '用户头像',
    primary key (`id`)
);

## 移动端只支持分类一级
#drop table if exists `b2c_category`;
create table `b2c_category`
(
    `cat_id`      smallint(5)  not null auto_increment,
    `cat_name`    varchar(90)           default '' comment '分类名称',
    `keywords`    varchar(191)          default '' comment '关键词',
    `cat_desc`    varchar(191)          default '' comment '分类描述',
    `parent_id`   smallint(5)  not null default '0' comment '父ID',
    `level`       smallint(5)  not null default 0 comment '层级',
    `has_child`   tinyint(1)   not null default 0 comment '是否是子节点',
    `create_time` timestamp             default now() comment '添加时间',
    `cat_img`     varchar(191) not null default '' comment '分类图标',
    `first`       smallint(2)  not null default '0' comment '优先级',
    primary key (`cat_id`),
    key `parent_id` (`parent_id`)
);

##店铺续费表
#drop table if exists `b2c_shop_renew`;
create table `b2c_shop_renew`
(
    `id`             int(11)     not null auto_increment,
    `shop_id`        int(11)     not null comment '店铺ID',
    `sys_id`         int(11)     not null,
    `mobile`         varchar(32)          default '',
    `renew_money`    decimal(12, 2)       default 0 comment '店铺续费金额',
    `renew_date`     timestamp   null     default null comment '店铺续费日期',
    `expire_time`    timestamp   null     default null comment '到期时间',
    `operator`       int(11)              default 0 comment '操作员ID,主账号是0，子账号ID',
    `renew_desc`     varchar(255)         default '' null comment '说明',
    `renew_type`     tinyint(1)  not null default '0' comment '说明',
    `renew_duration` varchar(32) not null default '0' comment '时长：字符串逗号隔开',
    `send_type`      tinyint(1)  not null default '0' comment '赠送类型：0无，1时间，2功能',
    `send_content`   varchar(32) not null default '0' comment '赠送内容：字符串逗号隔开',
    primary key (`id`)
);

## 小程序模板版本信息
#drop table if exists `b2c_mp_version`;
create table `b2c_mp_version`
(
    `template_id`              int(11)      not null comment '小程序模板Id',
    `user_version`             varchar(191) not null default '' comment '小程序模板版本号',
    `user_desc`                varchar(191)          default '' comment '小程序模板版本描述',
    `create_time`              timestamp    not null default now() comment '小程序模板添加时间',
    `del_flag`                 tinyint(1)   not null default 0 comment '删除标记',
    `current_in_use`           tinyint(1)   not null default 0 comment '当前使用的版本',
    `source_miniprogram_appid` varchar(191)          default '' comment '小程序开发appid',
    `source_miniprogram`       varchar(191)          default '' comment '小程序开发名称',
    `developer`                varchar(191)          default '' comment '开发者',
    `package_version`          tinyint(1)            default 1 null comment '包版本 1：正常 2：包含好物推荐',
    primary key (`template_id`)
);

## 小程序部署历史
#drop table if exists `b2c_mp_deploy_history`;
create table `b2c_mp_deploy_history`
(
    `deploy_id`         int(11)      not null auto_increment comment '自增ID',
    `bind_template_id`  int(11)      not null comment '小程序模板Id',
    `app_id`            varchar(191) not null default '' comment '小程序app_id',
    `deploy_log`        text comment '小程序模板部署日志',
    `deploy_time`       timestamp    not null default now() comment '小程序模板添加时间',
    `is_modify_domain`  tinyint(1)            default 0 comment '是否修改开发和业务域名，0未修改，1已修改',
    `upload_state`      tinyint(1)            default 0 comment '上传状态，0未上传，1已上传',
    `last_upload_time`  timestamp    null     default null comment '上传代码时间',
    `audit_id`          bigint(64)            default 0 comment '最新的审核ID',
    `audit_state`       tinyint(1)            default 0 comment '审核状态，0未提交，1审核中，2审核成功 3审核失败',
    `submit_audit_time` timestamp    null     default null comment '提交代码审核时间',
    `audit_ok_time`     timestamp    null     default null comment '审核通过时间',
    `audit_fail_reason` varchar(191) comment '未通过审核原因',
    `publish_state`     tinyint(1)            default 0 comment '通过审核的小程序发布状态，0未发布，1已发布',
    `publish_time`      timestamp    null     default null comment '程序发布时间',
    `tester`            text comment '小程序体验者列表,json存储',
    `test_qr_path`      varchar(191) comment '小程序体验二维码图片路径',
    `category`          text comment '小程序可选类目,json存储',
    `page_cfg`          text comment '小程序页面配置,json存储',
    primary key (`deploy_id`),
    unique key (`bind_template_id`, `app_id`)
);

## 小程序操作日志
#drop table if exists `b2c_mp_operate_log`;
create table `b2c_mp_operate_log`
(
  `operate_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `app_id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '小程序app_id',
  `template_id` int(11) NOT NULL COMMENT '小程序模板Id',
  `operate_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '操作类型',
  `memo` text COLLATE utf8mb4_unicode_ci COMMENT '操作日志',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  `memo_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `memo_list` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `operate_state` tinyint(4) NOT NULL DEFAULT '1' COMMENT '操作状态:1成功 2失败',
  PRIMARY KEY (`operate_id`),
  KEY `app_id` (`app_id`),
  KEY `operate_type` (`operate_type`),
  KEY `template_id` (`template_id`)
);



## 后台进程执行信息表
#drop table if exists `b2c_back_process`;
create table `b2c_back_process`
(
    `rec_id`        int          not null auto_increment,
    `shop_id`       int(11)      not null default 0 comment '店铺ID',
    `process_id`    int(11)      not null default 0 comment '进程ID',
    `job_name`      varchar(255) not null default '' comment '任务名称',
    `class_name`    varchar(255) not null default '' comment '类名称',
    `parameters`    text comment '任务执行时，所需参数数组,serialize存储',
    `state`         tinyint(1)            default 0 comment '进程状态，0初始，1执行中，2完成，3失败',
    `fail_reason`   varchar(255) not null default '' comment '失败原因',
    `only_run_one`  tinyint(1)            default 0 comment '是否只允许一个进程存在，按照class_name，static_method联合查',
    `progress`      smallint(4)           default 0 comment '执行进度',
    `progress_info` varchar(255) not null default '' comment '当前进度信息',
    `end_time`      timestamp    not null comment '进程结束时间',
    `job_code`      int          not null default 0 comment '执行结果码，成功0，其他非0',
    `job_message`   varchar(255) not null default '' comment '错误信息',
    `job_result`    text comment '执行结果,serialize存储',
    `memo`          text comment '备注',
    `created`       timestamp    not null default now(),
    primary key (`rec_id`),
    key `created` (`created`),
    key `shop_class` (`shop_id`, `class_name`)
);
## 发送短信记录表
#drop table if exists `b2c_sms`;
create table `b2c_sms`
(
    `id`       int(11)     not null auto_increment,
    `mobile`   varchar(32) not null default '',
    `sms_code` varchar(10) not null,
    `add_time` timestamp   null     default CURRENT_TIMESTAMP,
    `ip`       varchar(20)          default '',
    `status`   tinyint(1)  not null default '1' comment '0:发送失败 1：发送成功',
    `type`     varchar(10)          default null comment '',
    `content`  text        not null comment '发送短信内容',
    primary key (`id`),
    key `mobile` (`mobile`)
);

## 店铺主账号图片记录表
#drop table if exists `b2c_shop_uploaded_image`;
create table `b2c_shop_uploaded_image`
(
    `img_id`         int(10)      not null auto_increment,
    `img_type`       varchar(60)  not null default '',
    `img_size`       int(10)      not null default '0',
    `img_name`       varchar(191) not null default '' comment '图片名称，用于前端显示',
    `img_orig_fname` varchar(191) not null default '',
    `img_path`       varchar(191) not null default '',
    `img_url`        varchar(191) not null default '',
    `img_cat_id`     int(10)               default '0' comment '图片分类',
    `img_width`      int(10)      not null default '0',
    `img_height`     int(10)      not null default '0',
    `is_refer`       tinyint(4)            default '0' comment '是否引用',
    `upload_time`    timestamp    NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `sys_id`         int(11)      not null default '0' comment '账户ID',
    `shop_id`        int(11)      not null default '0' comment '店铺ID',
    `del_flag`       tinyint(1)   not null default '0',
    `create_time`    timestamp    NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    primary key (`img_id`),
    key `shop_id` (`shop_id`),
    key `img_orig_fname` (`img_orig_fname`)
);

## 店铺主账号图片分类
#drop table if exists `b2c_shop_uploaded_image_category`;
create table `b2c_shop_uploaded_image_category`
(
    `img_cat_id`        int(10)      not null auto_increment,
    `shop_id`           int(11)      not null default '0' comment '店铺ID',
    `img_cat_name`      varchar(60)  not null default '',
    `img_cat_parent_id` int(10)      not null default '0',
    `create_time`       timestamp    null     default CURRENT_TIMESTAMP,
    `cat_ids`           varchar(191) not null default '0' comment '层级ID串,逗号分隔',
    `level`             tinyint(4)            default '0' comment '层级，0开始',
    `sort`              int(11)               default '1' comment '排序优先级',
    `sys_id`            int(11)      not null default '0' comment '账户ID',
    primary key (`img_cat_id`),
    key `shop_id` (`shop_id`)
);

##装修模板表
#drop table if exists `b2c_decoration_template`;
create table `b2c_decoration_template`
(
    `page_id`      int(10)       not null auto_increment,
    `page_name`    varchar(60)   not null default '',
    `page_enabled` tinyint(1)    not null default 1 comment '是否可用',
    `page_content` longtext comment '页面内容，json格式存储',
    `create_time`  timestamp              default now(),
    `page_img`     varchar(1000) null     default '' comment '装修页面封图',
    primary key (`page_id`)
);

## 应用表
#drop table if exists `b2c_app`;
create table `b2c_app`
(
    `app_id`     varchar(20)  not null,
    `app_name`   varchar(60)  not null default '' comment '应用名称',
    `app_secret` varchar(255) not null default '',
    `add_time`   datetime     not null,
    unique index `app_id` (`app_id`) using btree,
    unique index `app_name` (`app_name`) using btree
);

## 应用授权表
#drop table if exists `b2c_app_auth`;
create table `b2c_app_auth`
(
    `id`              smallint(5)  not null auto_increment,
    `action`          tinyint(1)            default 1 null comment '1：erp 2：pos',
    `sys_id`          int(11)      not null default 0,
    `shop_id`         int(11)      not null default 0 comment '店铺ID',
    `session_key`     varchar(191) not null default '' comment '授权key',
    `pos_session_key` varchar(100) null comment 'pos授权key',
    `app_key`         varchar(200) null comment 'crm分配的appKey',
    `app_secret`      varchar(200) null comment 'crm分配的appSecret',
    `product`         tinyint(1)            default 1 null comment '产品：1 ERP企业版 2：ERP旗舰版',
    `is_sync`         tinyint(1)            default 0 null comment '是否已同步',
    `status`          tinyint(1)   null     default 0 comment '1：启用 0：禁用',
    `add_time`        datetime     not null,
    `update_time`     datetime     not null,
    primary key (`id`),
    unique index `session_key` (`session_key`) using btree,
    index `shop_id` (`shop_id`) using btree
);

##访问趋势
#drop table if exists `b2c_mp_daily_visit`;
create table `b2c_mp_daily_visit`
(
    `ref_date`                 char(8)   not null comment '时间： 如： "20180313"',
    `session_cnt`              int(11)   not null default '0' comment '打开次数',
    `visit_pv`                 int(11)   not null default '0' comment '访问次数',
    `visit_uv`                 int(11)   not null default '0' comment '访问人数',
    `visit_uv_new`             int(11)   not null default '0' comment '新用户数',
    `stay_time_uv`             float     not null default '0' comment '人均停留时长 (浮点型，单位：秒)',
    `stay_time_session`        float     not null default '0' comment '次均停留时长 (浮点型，单位：秒)',
    `visit_depth`              float     not null default '0' comment '平均访问深度 (浮点型)',
    `add_time`                 timestamp not null default CURRENT_TIMESTAMP comment '添加时间',
    `session_cnt_middle`       int(11)   not null default '0' comment '打开次数',
    `visit_pv_middle`          int(11)   not null default '0' comment '访问次数',
    `visit_uv_middle`          int(11)   not null default '0' comment '访问人数',
    `visit_uv_new_middle`      int(11)   not null default '0' comment '新用户数',
    `stay_time_uv_middle`      float     not null default '0' comment '人均停留时长 (浮点型，单位：秒)',
    `stay_time_session_middle` float     not null default '0' comment '次均停留时长 (浮点型，单位：秒)',
    `visit_depth_middle`       float     not null default '0' comment '平均访问深度 (浮点型)',
    `stay_time_uv_total`       float     not null default '0' comment '停留时长 总和(浮点型)',
    `stay_time_session_total`  float     not null default '0' comment '次均停留时长 总和(浮点型)',
    `visit_depth_total`        float     not null default '0' comment '平均访问深度 总和(浮点型)'
);

##分享统计
#drop table if exists `b2c_mp_summary_trend`;
create table `b2c_mp_summary_trend`
(
    `ref_date`           char(8)   not null comment '日期',
    `visit_total`        int(11)   not null default '0' comment '总访问量',
    `share_pv`           int(11)   not null default '0' comment '转发次数',
    `share_uv`           int(11)   not null default '0' comment '转发人数',
    `add_time`           timestamp null     default CURRENT_TIMESTAMP comment '添加时间',
    `visit_total_middle` int(11)   not null default '0' comment '总访问量 中位数',
    `share_pv_middle`    int(11)   not null default '0' comment '转发次数 中位数',
    `share_uv_middle`    int(11)   not null default '0' comment '转发人数 中位数'
);

##按店铺统计访问量
#drop table if exists `b2c_mp_summary_trend_shop`;
create table `b2c_mp_summary_trend_shop`
(
    `ref_date`          char(8)   not null comment '日期 如： "20180313"',
    `shop_id`           int(11)   not null comment '店铺ID',
    `visit_total`       int(11)   not null default '0' comment '总访问量',
    `share_pv`          int(11)   not null default '0' comment '转发次数',
    `share_uv`          int(11)   not null default '0' comment '转发人数',
    `session_cnt`       int(11)   not null default '0' comment '打开次数',
    `visit_pv`          int(11)   not null default '0' comment '访问次数',
    `visit_uv`          int(11)   not null default '0' comment '访问人数',
    `visit_uv_new`      int(11)   not null default '0' comment '新用户数',
    `stay_time_uv`      float     not null default '0' comment '人均停留时长 (浮点型，单位：秒)',
    `stay_time_session` float     not null default '0' comment '次均停留时长 (浮点型，单位：秒)',
    `visit_depth`       float     not null default '0' comment '平均访问深度 (浮点型)',
    `add_time`          timestamp null     default CURRENT_TIMESTAMP comment '添加时间',
    key `ref_date` (`ref_date`)
);

##订单
#drop table if exists `b2c_order_info`;
create table `b2c_order_info`
(
    `id`                   bigint(20)     not null auto_increment,
    `order_id`             mediumint(8)   not null comment '订单ID',
    `shop_id`              int(11)        not null default '0' comment '店铺ID',
    `order_sn`             varchar(20)    not null default '' comment '订单编号',
    `main_order_sn`        varchar(20)    not null default '' comment '主订单编号(拆单时用)',
    `user_id`              mediumint(8)   not null default '0' comment '用户ID',
    `user_openid`          varchar(191)   not null default '' comment '用户openid',
    `order_status`         tinyint(1)     not null default '0' comment '订单状态',
    `order_status_name`    varchar(32)    not null default '' comment '订单状态名称',
    `consignee`            varchar(60)    not null default '' comment '收件人姓名',
    `address_id`           int(11)        not null default '0' comment '地址ID',
    `country_code`         mediumint(10)  null     default '0' comment '国家编号',
    `country_name`         varchar(50)    not null default '' comment '国家名称',
    `province_code`        mediumint(10)  null     default '0' comment '省份编号',
    `province_name`        varchar(50)    not null default '' comment '省份名称',
    `city_code`            mediumint(10)  null     default '0' comment '城市编号',
    `city_name`            varchar(120)   not null default '' comment '城市名称',
    `district_code`        mediumint(10)  null     default '0' comment '区县编号',
    `district_name`        varchar(120)   not null default '' comment '区县名称',
    `address`              varchar(191)   not null default '' comment '更多详细地址',
    `complete_address`     varchar(512)   not null default '' comment '完整收件地址',
    `zipcode`              varchar(60)    not null default '' comment '邮编',
    `mobile`               varchar(60)    not null default '' comment '手机号',
    `add_message`          varchar(191)            default '' comment '客户留言',
    `shipping_id`          tinyint(3)     not null default '0' comment '快递ID',
    `shipping_name`        varchar(120)   not null default '' comment '快递名称',
    `pay_code`             varchar(30)    not null default '' comment '支付代号',
    `pay_name`             varchar(120)   not null default '' comment '支付名称',
    `pay_sn`               varchar(32)    not null default '' comment '支付流水号',
    `goods_amount`         smallint(6)    not null default '0' comment '订单商品数量',
    `shipping_fee`         decimal(10, 2) not null default '0.00' comment '快递费金额',
    `money_paid`           decimal(10, 2) not null default '0.00' comment '订单应付金额',
    `shoper_reduce_amount` decimal(10, 2) not null default '0.00' comment '商家减价金额',
    `sub_order_amount`     decimal(10, 2) not null default '0.00' comment '子订单总金额',
    `discount`             decimal(10, 2) not null default '0.00' comment '券折扣金额',
    `score_discount`       decimal(10, 2) not null default '0.00' comment '积分抵扣金额',
    `use_account`          decimal(10, 2) not null default '0.00' comment '用户消费余额',
    `order_amount`         decimal(10, 2) not null default '0.00' comment '订单总金额',
    `grade_percent`        decimal(10, 2) not null default '0.00' comment '购买会员等级的折扣%',
    `discount_price`       decimal(10, 2) not null default '0.00' comment '购买会员等级的折扣金额',
    `dapei_reduce_amount`  decimal(10, 2) not null default '0.00' comment '搭配减价',
    `package_discount`     decimal(10, 2)          default 0.00 null comment '一口价抵扣金额',
    `dapei_id`             int(8)         not null default '0' comment '搭配ID来源',
    `add_time`             timestamp      null     default null comment '订单提交时间',
    `confirm_time`         timestamp      null     default null comment '订单确收收货时间',
    `pay_time`             timestamp      null     default null comment '支付时间',
    `shipping_time`        timestamp      null     default null comment '发货时间',
    `closed_time`          timestamp      null     default null comment '关闭时间',
    `cancelled_time`       timestamp      null     default null comment '取消时间',
    `finished_time`        timestamp      null     default null comment '订单完成时间',
    `return_time`          timestamp      null     default null comment '订单申请退货时间',
    `return_finish_time`   timestamp      null     default null comment '订单退货完成时间',
    `refund_time`          timestamp      null     default null comment '订单申请退款时间',
    `refund_finish_time`   timestamp      null     default null comment '订单退款完成时间',
    `update_time`          timestamp      not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '订单更新时间',
    `shipping_no`          varchar(191)   not null default '' comment '快递单号',
    `shipping_type`        varchar(60)    not null default '' comment '快递类型',
    `is_cod`               tinyint(1)     not null default '0' comment '是否货到付款',
    `return_type_cfg`      tinyint(1)     not null default '1' comment '是否支持退换货：1支持 2不支持',
    `return_days_cfg`      tinyint(1)     not null default '7' comment '发货后自动确认收货时间,单位天',
    `order_timeout_days`   smallint(3)    not null default '3' comment '确认收货后自动订单完成时间,单位天',
    `seller_remark`        varchar(512)   not null default '' comment '卖家备注',
    `erpordercode`         varchar(32)    not null default '' comment 'ERP中订单代码',
    `comment_flag`         tinyint(1)     not null default '0' comment '0:未评论，1:已评论，2：已晒单',
    `fanli_user_id`        int(11)        not null default '0' comment '返利会员id',
    `fanli_grade`          varchar(64)    not null default '' comment '返利等级',
    `fanli_percent`        decimal(10, 2) not null default '0.00' comment '返利百分比',
    `settlement_flag`      tinyint(1)     not null default '0' comment '结算标志：0：未结算，1：已结算',
    `invoice_id`           int(11)        not null default '0' comment '发票Id',
    `invoice_content`      int(11)                 default '0' comment '发票类型：0普通发票；1增值税专票',
    `invoice_title`        text comment '发票内容：json存储',
    `refund_status`        tinyint(1)              default '0' comment '1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货',
    `pay_order_sn`         varchar(30)             default '' comment '对账单号',
    `goods_type`           varchar(50)             default '0' null comment '商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品 6限时降价 7加价购',
    `order_source`         tinyint(2)              default null comment '订单来源，0pc，1wap，2app',
    `fanli_type`           tinyint(2)              default null comment '返利类型，0普通订单，1三级分销返利订单，2返利会员返利订单',
    `manual_refund`        tinyint(1)     not null default '0' comment '1代表手动退款，0代表非手动',
    `order_pay_way`        tinyint(2)              default '0' comment '订单付款方式，0全款 1定金 2补款',
    `bk_order_sn`          varchar(20)             default '' comment '补款订单号 order_pay_way=1时有效',
    `bk_order_money`       decimal(10, 2) not null default '0.00' comment '补款金额 order_pay_way=1时有效',
    `bk_order_paid`        tinyint(1)     not null default '0' comment '补款金额是否支付 order_pay_way=1时有效，0未支付，1已支付',
    `pin_goods_money`      decimal(10, 2) not null default '0.00' comment '当前拼团商品金额，阶梯团根据人数时会变化，补款也随之变化',
    `pin_yj_money`         decimal(10, 2) not null default '0.00' comment '拼团支付佣金金额',
    `pin_group_id`         int(11)        not null default '0' comment '拼团ID',
    `del_flag`             tinyint(1)     not null default '0' comment '0:未删除，1:已删除',
    `source`               varchar(30)             default '' comment '订单来源，记录app，wap，pc来源',
    `part_ship_flag`       tinyint(1)     not null default '0' comment '0:，1:部分发货',
    `promotion_id`         int(11)        not null default '0' comment '促销活动Id',
    `promotion_reduce`     decimal(10, 2) not null default '0.00' comment '促销活动折扣金额,满折满减',
    `push_type`            tinyint(1)     not null default '0' comment 'yadu推送状态：0-暂无推送，1-推送失败，2-推送成功',
    `push_desc`            varchar(100)   not null default '' comment 'yadu推送失败原因',
    `pos_flag`             tinyint(1)     not null default '0' comment '门店订单标志：0：商城，1：门店同步订单',
    `pos_shop_name`        varchar(191)   not null default '' comment 'pos店铺名称',
    `store_id`             int(11)                 default '0' comment '门店ID',
    `store_name`           varchar(191)            default '' comment '门店名称',
    `member_card_id`       int(11)                 default '0' comment '会员卡ID',
    `member_card_reduce`   decimal(10, 2)          default '0.00' comment '会员卡优惠金额',
    `member_card_balance`  decimal(10, 2)          default '0.00' comment '会员卡消费金额',
    `expire_time`          timestamp      null     default null comment '订单支付过期时间',
    `del_time`             timestamp      null     default null comment '订单删除时间',
    `prepay_id`            varchar(191)            default null comment '微信支付Id，用于发送模板消息',
    `deliver_type`         tinyint(1)              default '0' comment '配送类型：0 快递 1 自提',
    `deliver_type_name`    varchar(191)            default null comment '配送类型名称',
    `pickup_time`          varchar(30)             default null comment '自提时间',
    `star_flag`            tinyint(1)              default '0' comment '标星订单：0 未标星 1 标星',
    `verify_code`          varchar(191)            default '' comment '核销自提码',
    `split`                int(11)                 default '0' comment '分裂优惠券id',
    `card_no`              varchar(32)    not null default '' comment '会员卡号',
    `fanli_money`          decimal(10, 2)          default '0.00' comment '返利金额',
    `true_name`            varchar(32)             default '' not null comment '真实姓名',
    `id_card`              varchar(32)             default '' not null comment '身份证号18位',
    `ali_trade_no`         varchar(60)    null     default '' comment '支付宝交易单号',
    `grouper_cheap_reduce` decimal(10, 2)          default 0 comment '团长优惠金额',
    `bk_shipping_time`     timestamp      null comment '定金预计发货时间',
    `bk_return_type`       tinyint(2)     null comment '定金退款状态',
    `bk_prepay_id`         varchar(191)   null comment '微信支付Id，用于发送模板消息',
    `pre_sale_discount`    decimal(10, 2)          default 0.00 null comment '定金膨胀优惠金额',
    `instead_pay_money`    decimal(10, 2)          default 0.00 null comment '代付金额',
    `order_user_message`   varchar(50)    null comment '发起人留言',
    `instead_pay`          text           null comment '好友代付规则',
    `instead_pay_num`      smallint                default 0 null comment '代付人数',
    `is_promote`           tinyint(1)              default 0 null comment '是否是推广单',
    `verifier_id`          int(9)                  default 0 null comment '核销员',
    `exchang`              tinyint(2)              default 0 null comment '1 兑换 0否',
    `free_ship`            decimal(10, 2)          default '0.00' null comment '运费抵扣',
    `free_detail`          text           null comment '运费抵扣规则',
    primary key (`id`),
    unique key `order_sn` (`order_sn`),
    key `main_order_sn` (`main_order_sn`),
    key `user_id` (`user_id`),
    key `user_openid` (`user_openid`),
    key `order_status` (`order_status`),
    key `shipping_id` (`shipping_id`),
    key `shop_id` (`shop_id`)
);

## 文章阅读记录
#drop table if exists `b2c_article_record`;
create table `b2c_article_record`
(
    `id`         int(11) not null auto_increment,
    `article_id` int(11) not null comment '阅读文章id',
    `add_time`   timestamp default now(),
    `sys_id`     int(11),
    primary key (`id`)
);

## 商品主库
#drop table if exists `b2c_goods`;
create table `b2c_goods`
(
    `id`                  bigint(20)     not null auto_increment,
    `goods_id`            int(8)         not null,
    `shop_id`             int(11)                 default '0' comment '店铺ID',
    `cat_id`              int(5)         not null default '0',
    `goods_sn`            varchar(60)    not null default '',
    `goods_name`          varchar(120)   not null default '',
    `brand_id`            int(11)        not null default 0 comment '品牌ID',
    `goods_ad`            varchar(1024)           default '' comment '广告词',
    `goods_number`        int(11)        not null default '0' comment '库存',
    `goods_weight`        decimal(10, 3)          default '0.000',
    `market_price`        decimal(10, 2)          default '0.00',
    `shop_price`          decimal(10, 2) not null default '0.00',
    `cost_price`          decimal(10, 2)          default 0.00 null comment '成本价',
    `goods_desc`          text,
    `goods_img`           varchar(191)            default '',
    `is_on_sale`          tinyint(1)              default '1' comment '是否在售，1在售，0下架',
    `is_delete`           tinyint(1)              default '0',
    `goods_type`          tinyint(2)              default '0' comment '商品类型，0普通商品，1团购商品，2秒杀商品,3积分商品',
    `deliver_template_id` int(5)                  default '0' comment '运费模板ID',
    `goods_sale_num`      int(8)                  default '0' comment '销售数量',
    `goods_collect_num`   int(8)                  default '0' comment '收藏数量',
    `add_time`            timestamp      null     default CURRENT_TIMESTAMP,
    `update_time`         timestamp      null     default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '最后修改时间',
    `state`               tinyint(1)              default '0' comment '审核状态,0待审核 1 审核通过 2 违规下架',
    `reason`              text comment '违规下架原因',
    `sub_account_id`      int(11)                 default '0' comment '子帐号ID，主要用于官方店铺',
    `sale_time`           timestamp      null     default CURRENT_TIMESTAMP comment '上架时间',
    `limit_buy_num`       int(11)                 default '0' comment '最少起购数量，0不限购',
    `limit_max_num`       int(11)                 default 0 null comment '最多起购数量，0不限购',
    `unit`                varchar(60)             default '' comment '商品单位',
    `add_sale_num`        int(11)                 default '0' comment '虚假销量',
    `sale_type`           tinyint(1)              default '0' comment '上架状态,0立即上架， 1审核通过 2 加入仓库',
    `sort_id`             int(5)         not null default '0',
    `goods_video`         varchar(191)   null     default '' comment '视频',
    `goods_video_img`     varchar(191)   null     default '' comment '视频首图',
    `goods_video_size`    int            null comment '视频尺寸',
    `goods_video_id`      int            null comment '视频ID',
    `goods_page_id`       int(11)        not null default '0' comment '详情页装修模板ID',
    `is_page_up`          tinyint(1)     not null default '1' comment '是否在文本区域上方',
    `is_card_exclusive`   tinyint(1)              default 0 comment '是否会员卡专属',
    `base_sale`           int(8)         null     default '0' comment '初始销量',
    `source`              tinyint(1)              default '0' comment '商品来源,0：店铺自带；1、2..等：不同类型店铺第三方抓取自带商品来源',
    `is_control_price`    tinyint(1)              default '0' comment '是否控价：0不控价，1控价（不可修改价格）',
    `can_rebate`          tinyint(1)              default 0 null comment '是否分销改价',
    `deliver_flag`        tinyint(1)              default 0 null comment '混批标记，0：默认不支持，1：支持',
    primary key (`id`),
    unique key `goods_id` (`goods_id`, `shop_id`),
    unique key `goods_sn` (`goods_sn`, `shop_id`),
    key `goods_id_2` (`goods_id`),
    key `shop_id` (`shop_id`),
    key `cat_id` (`cat_id`)
);

##升级续费记录
#drop table if exists `b2c_charge_renew`;
create table `b2c_charge_renew`
(
    `id`         int(11)    not null auto_increment comment '申请ID',
    `shop_id`    int(11)    not null comment '店铺ID',
    `apply_id`   int(11)    not null comment '申请用户的ID',
    `apply_name` varchar(50)         default '' comment '申请用户名称',
    `sys_id`     int(11)    not null comment '所属账号的ID',
    `shop_name`  varchar(50)         default '' comment '店铺名称',
    `created`    timestamp  not null default now() comment '创建时间',
    `apply_mod`  varchar(50)         default '' comment '点击模块',
    `apply_type` tinyint(1) not null default '0' comment '申请类型 0升级 1续费',
    `contact`    tinyint(1) not null default '0' comment '申请类型 0未联系 1已联系',
    `desc`       text       null comment '备注',
    primary key (`id`)
);

##操作日志记录
#drop table if exists `b2c_shop_operation`;
create table `b2c_shop_operation`
(
    `id`          int(11)     not null auto_increment comment '操作id',
    `shop_id`     int(11)     not null comment '店铺ID',
    `operator_id` int(11)     not null comment '操作员id',
    `operator`    varchar(50) not null comment '操作员',
    `created`     timestamp   not null default now() comment '操作时间',
    `desc`        text comment '操作描述',
    `ip`          varchar(50)          default '' comment '操作ip',
    `type`        tinyint(1)  not null default 0 comment '记录类型 0 店铺 1 账号',
    primary key (`id`)
);

##店铺分版本
#drop table if exists `b2c_shop_version`;
create table `b2c_shop_version`
(
    `id`           int(11)     not null auto_increment comment '',
    `version_name` varchar(50) not null comment '版本名称',
    `level`        varchar(50) not null comment '版本级别',
    `content`      text comment '包含功能',
    `created`      timestamp   not null default now() comment '操作时间',
    `update_time`  timestamp   null     default now() comment '更新时间',
    `desc`         text comment '版本介绍',
    `flag`         tinyint(1)  not null default '0' comment '0正常 1删除',
    primary key (`id`),
    unique key (`level`)
);

##  订单商品表  b2c_order_goods
#drop table if exists `b2c_order_goods`;
create table `b2c_order_goods`
(
    `id`                     bigint(20)     not null auto_increment,
    `rec_id`                 mediumint(8)   not null,
    `shop_id`                int(11)        not null default 0 comment '店铺ID',
    `order_id`               mediumint(8)   not null default '0',
    `order_sn`               varchar(20)    not null default '',
    `goods_id`               mediumint(8)   not null default '0',
    `goods_name`             varchar(120)   not null default '',
    `goods_sn`               varchar(60)    not null default '',
    `product_id`             mediumint(8)   not null default '0',
    `product_sn`             varchar(65)    not null default '',
    `goods_number`           smallint(5)    not null default '1',
    `market_price`           decimal(10, 2) not null default '0.00',
    `goods_price`            decimal(10, 2) not null default '0.00',
    `goods_attr`             text           not null,
    `send_number`            smallint(5)    not null default '0',
    `return_number`          smallint(5)    not null default '0' comment '退货/退款成功数量',
    `is_real`                tinyint(1)     not null default '0',
    `goods_attr_id`          varchar(191)   not null default '',
    `goods_img`              varchar(191)   not null default '',
    `refund_status`          tinyint(1)     not null default '0' comment '1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货',
    `comment_flag`           tinyint(1)     not null default '0' comment '0:未评论，1:已评论，2：已晒单',
    `stra_id`                int(11)        not null default '0' comment '商品参与的促销活动id',
    `per_discount`           decimal(10, 2) not null default '0' comment '促销折扣均摊到每件商品的折扣',
    `is_gift`                int(1)         not null default '0' comment '是否是赠品',
    `r_goods`                varchar(191)   not null default '' comment '赠品的关联商品',
    `goods_score`            int(11)        not null default '0' comment '商品积分',
    `goods_growth`           int(11)        not null default '0' comment '商品成长值',
    `discounted_goods_price` decimal(10, 2) not null default '0' comment '折后单价',
    `discount_detail`        text comment '打折详情，json存储',
    `fanli_type`             tinyint(1)              default '0' not null comment '0:不返利商品，1：返利商品',
    `can_calculate_money`    decimal(10, 2)          default '0.00' null comment '单品可计算返利金额，刨除优惠券和其他折扣后的金额',
    `fanli_money`            decimal(10, 2)          default '0.00' null comment '单品返利金额',
    `discounted_total_price` decimal(10, 2) not null default '0' comment '折后总价',
    `total_fanli_money`      decimal(10, 2)          default 0.00 null comment '商品返利总金额',
    `purchase_price_id`      int                     default 0 null comment '加价购ID',
    `purchase_price_rule_id` int                     default 0 null comment '换购挡位ID',
    `reduce_price_id`        int                     default 0 null comment '限时降价ID',
    `fanli_strategy`         varchar(299)            default '' comment '返利比例',
    `fanli_percent`          decimal(10, 2)          default 0.00 null comment '返利比例',
    `cost_price`             decimal(10, 2)          default 0.00 null comment '成本价',
    `is_card_exclusive`      tinyint(1)              default 0 null comment '是否会员卡专属',
    `promote_info`           varchar(500)   null comment '推广信息',
    `gift_id`                int                     default 0 null comment '赠品ID',
    `is_can_return`          tinyint(1)              default 1 null comment '是否可退款',
    primary key (`id`),
    key `order_id` (`order_id`),
    key `order_sn` (`order_sn`),
    key `goods_id` (`goods_id`),
    key (`shop_id`)
);
##用户登录记录表
#drop table if exists `b2c_user_login_record`;
create table `b2c_user_login_record`
(
    `id`        int(20)      not null auto_increment,
    `shop_id`   int(11)      not null default 0 comment '店铺ID',
    `shop_name` varchar(100) not null default '' comment '店铺名称',
    `sys_id`    int(11)      not null default 0 comment '主账户ID',
    `user_id`   int(11)      not null default '0' comment '登陆用户id',
    `user_name` varchar(64)  not null default '' comment '登陆用户名',
    `add_time`  timestamp    null     default CURRENT_TIMESTAMP comment '每日登陆时间',
    `user_ip`   varchar(64)  null     default null comment '用户登录ip',
    `count`     smallint(3)  null     default '0' comment '每日登陆次数',
    `account_type` tinyint(1) DEFAULT NULL COMMENT '登录日志账户类型：0店铺登录日志，1系统账号登录日志',
    primary key (`id`)
);

#drop table if exists `b2c_jobs`;
create table `b2c_jobs`
(
    `id`           bigint(20)   not null auto_increment,
    `queue`        varchar(191) not null,
    `payload`      longtext     not null,
    `attempts`     tinyint(3)   not null,
    `reserved_at`  int(10) default null,
    `available_at` int(10)      not null,
    `created_at`   int(10)      not null,
    primary key (`id`),
    key `jobs_queue_index` (`queue`)
);

create table `b2c_failed_jobs`
(
    `id`         bigint(20) not null auto_increment,
    `connection` text       not null,
    `queue`      text       not null,
    `payload`    longtext   not null,
    `exception`  longtext   not null,
    `failed_at`  timestamp  not null default CURRENT_TIMESTAMP,
    primary key (`id`)
);

##admin用户问题反馈记录
#drop table if exists `b2c_shop_question_feedback`;
create table `b2c_shop_question_feedback`
(
    `question_feedback_id` int(11)      not null auto_increment,
    `shop_id`              int(11)      not null comment '反馈店铺ID',
    `category_id`          int(11)      not null default '1' comment '反馈问题分类',
    `mobile`               varchar(32)           default '' comment '填写的手机号',
    `content`              text,
    `is_look`              tinyint(1)            default '0' comment '1:已查看',
    `create_time`          timestamp    null     default CURRENT_TIMESTAMP comment '反馈时间',
    `qf_img`               varchar(191) not null default '' comment '图片',
    primary key (`question_feedback_id`),
    key `is_look` (`is_look`)
);

##admin用户问题反馈记录图片
#drop table if exists `b2c_qf_img`;
create table `b2c_qf_img`
(
    `img_id`               int(8)       not null auto_increment,
    `question_feedback_id` int(8)       not null default '0',
    `img_url`              varchar(191) not null default '',
    `img_desc`             varchar(191) not null default '',
    primary key (`img_id`),
    key `question_feedback_id` (`question_feedback_id`)
);

##官网免费申请
#drop table if exists `b2c_shop_free_experience`;
create table `b2c_shop_free_experience`
(
    `fe_id`       int(11)      not null auto_increment,
    `company`     varchar(191) not null default '' comment '公司',
    `contact`     varchar(191) not null default '' comment '联系人',
    `mobile`      varchar(32)           default '' comment '电话',
    `province_id` int(11)      not null,
    `content`     text comment '留言',
    `ask_time`    timestamp    null     default current_timestamp comment '申请时间',
    `is_deal`     tinyint(1)            default '0' comment '1:已查看',
    `shop_id`     int(11)               default null comment '开通店铺ID',
    `desc`        text comment '备注',
    `source`      varchar(191) not null default '' comment '来源shop_id',
    `user_id`     int(11)               default null comment '用户ID',
    primary key (`fe_id`)
);

##活动记录
#drop table if exists `b2c_activity_statistics`;
create table `b2c_activity_statistics`
(
    `id`                 int(11)        not null auto_increment,
    `ref_date`           date           not null comment '2018-09-18',
    `type`               tinyint(2)     not null comment '1，7，30',
    `activity_type`      tinyint(2)     not null comment '活动类型',
    `shop_id`            int(11)        not null comment '店铺ID',
    `shop_version`       varchar(11)    not null comment '店铺版本',
    `currently`          int(11)        null comment '进行中的活动',
    `expired`            int(11)        null comment '过期中的活动',
    `closed`             int(11)        null comment '关闭中的活动（截止到统计时间）',
    `order_num`          int(11)        null comment '活动订单',
    `order_money`        decimal(10, 2) null comment '活动订单金额',
    `visited`            int(11)        null comment '活动访问用户数',
    `visited_user`       int(11)        null comment '访问用户数',
    `join_user`          int(11)        null comment '参与用户数',
    `success_user`       int(11)        null comment '成功用户数',
    `share_user`         int(11)        null comment '分享用户数',
    `used_user`          int(11)        null comment '使用用户数',
    `bargain_user_count` int(11)        null comment '砍价人次',
    `new_user`           int(11)        null comment '拉新数',
    primary key (`id`)
);

##店铺活动记录
#drop table if exists `b2c_shop_activity`;
create table `b2c_shop_activity`
(
    `id`            int(11)    not null auto_increment,
    `ref_date`      date       not null comment '2018-09-18',
    `type`          tinyint(2) not null comment '1，7，30',
    `activity_type` tinyint(2) not null comment '活动类型',
    `shop_num`      int(11)    not null comment '有权限的店铺数',
    `use`           int(11)    not null comment '有权限已使用的店铺数',
    `nouse`         int(11)    not null comment '有权限未使用的店铺数',
    `ingoing`       int(11)    not null comment '有权限正在使用的店铺数',
    `num`           int(11)    not null comment '进行中的活动',
    primary key (`id`)
);

##访问分布
#drop table if exists `b2c_mp_distribution_visit`;
create table `b2c_mp_distribution_visit`
(
    `ref_date` char(8)   not null comment '时间，如："20180313"',
    `list`     text comment '存入所有类型的指标情况',
    `add_time` timestamp not null default CURRENT_TIMESTAMP comment '添加时间',
    key `ref_date` (`ref_date`) using btree
);

##访问页面
#drop table if exists `b2c_mp_visit_page`;
create table `b2c_mp_visit_page`
(
    `ref_date`         char(8)      not null comment '时间，如："20170313"',
    `page_path`        varchar(200) not null,
    `page_visit_pv`    int(11)               default null comment '访问次数',
    `page_visit_uv`    int(11)               default null comment '访问人数',
    `page_staytime_pv` float                 default null comment '人均停留时长 (浮点型，单位：秒)',
    `entrypage_pv`     int(11)               default null comment '进入页次数',
    `exitpage_pv`      int(11)               default null comment '退出页次数',
    `page_share_pv`    int(11)               default null comment '转发次数',
    `page_share_uv`    int(11)               default null comment '转发人数',
    `add_time`         timestamp    not null default CURRENT_TIMESTAMP comment '添加时间',
    key `ref_date` (`ref_date`) using btree,
    key `page_path` (`page_path`) using btree,
    key `page_visit_pv` (`page_visit_pv`) using btree
);

##日留存
#drop table if exists `b2c_mp_daily_retain`;
create table `b2c_mp_daily_retain`
(
    `ref_date`     char(8)   not null comment '时间，如："20180313"',
    `visit_uv_new` text comment '新增用户留存',
    `visit_uv`     text comment '活跃用户留存',
    `add_time`     timestamp not null default CURRENT_TIMESTAMP comment '添加时间',
    key `ref_date` (`ref_date`) using btree
);

##周留存
#drop table if exists `b2c_mp_weekly_retain`;
create table `b2c_mp_weekly_retain`
(
    `ref_date`     char(20)  not null comment '时间，如："20180306-20180312"',
    `visit_uv_new` text comment '新增用户留存',
    `visit_uv`     text comment '活跃用户留存',
    `add_time`     timestamp not null default CURRENT_TIMESTAMP comment '添加时间'
);

##月留存
#drop table if exists `b2c_mp_monthly_retain`;
create table `b2c_mp_monthly_retain`
(
    `ref_date`     char(6)   not null comment '时间，如："201803"',
    `visit_uv_new` text comment '新增用户留存',
    `visit_uv`     text comment '活跃用户留存',
    `add_time`     timestamp not null default CURRENT_TIMESTAMP comment '添加时间'
);

##周趋势
#drop table if exists `b2c_mp_weekly_visit`;
create table `b2c_mp_weekly_visit`
(
    `ref_date`          char(20)  not null comment '时间，如："20180306-20180312"',
    `session_cnt`       int(11)   not null default '0' comment '打开次数',
    `visit_pv`          int(11)   not null default '0' comment '访问次数',
    `visit_uv`          int(11)   not null default '0' comment '访问人数',
    `visit_uv_new`      int(11)   not null default '0' comment '新用户数',
    `stay_time_uv`      float     not null default '0' comment '人均停留时长 (浮点型，单位：秒)',
    `stay_time_session` float     not null default '0' comment '次均停留时长 (浮点型，单位：秒)',
    `visit_depth`       float     not null default '0' comment '平均访问深度 (浮点型)',
    `add_time`          timestamp not null default CURRENT_TIMESTAMP comment '添加时间'
);

##月趋势
#drop table if exists `b2c_mp_monthly_visit`;
create table `b2c_mp_monthly_visit`
(
    `ref_date`          char(6)   not null comment '时间，如："201803"',
    `session_cnt`       int(11)   not null default '0' comment '打开次数',
    `visit_pv`          int(11)   not null default '0' comment '访问次数',
    `visit_uv`          int(11)   not null default '0' comment '访问人数',
    `visit_uv_new`      int(11)   not null default '0' comment '新用户数',
    `stay_time_uv`      float     not null default '0' comment '人均停留时长 (浮点型，单位：秒)',
    `stay_time_session` float     not null default '0' comment '次均停留时长 (浮点型，单位：秒)',
    `visit_depth`       float     not null default '0' comment '平均访问深度 (浮点型)',
    `add_time`          timestamp not null default CURRENT_TIMESTAMP comment '添加时间'
);

## 阿里小程序模板订购信息
#drop table if exists `b2c_ali_my_order`;
create table `b2c_ali_my_order`
(
    `rec_id`             int            not null auto_increment,
    `commodity_order_id` varchar(191)        default '' comment '订单编号',
    `order_time`         timestamp      null default null comment '订购时间',
    `title`              varchar(191)        default '' comment '服务名称',
    `name`               varchar(191)        default '' comment '商户名称',
    `merchant_pid`       varchar(191)        default '' comment '商户ID	',
    `contactor`          varchar(191)        default '' comment '联系人	',
    `phone`              varchar(191)        default 1 comment '联系电话',
    `order_item_num`     int                 default 0 comment '门店数量	',
    `total_price`        decimal(10, 2) null default 0.0 comment '总价格	',
    `create_time`        timestamp           default now(),
    `order_ticket`       varchar(191) comment '订单授权码，用于事务创建接口中的入参',
    `service_code`       varchar(191) comment '服务码，用于商户和ISV匹配订单',
    `link_shop_id`       int                 default null comment '关联店铺Id',
    primary key (`rec_id`),
    unique key (`commodity_order_id`),
    unique key (`link_shop_id`)
);

## 阿里小程序授权信息
#drop table if exists `b2c_ali_mini_agent`;
create table `b2c_ali_mini_agent`
(
    `rec_id`               int          not null auto_increment,
    `shop_id`              int          not null comment '店铺Id',
    `account`              varchar(191)      default '' comment 'isv代操作的商户账号，可以是支付宝账号，也可以是pid（2088开头）',
    `contact_name`         varchar(191)      default '' comment '联系人名称',
    `contact_mobile`       varchar(191)      default '' comment '联系人手机号码',
    `contact_email`        varchar(191)      default '' comment '联系人邮箱',
    `order_ticket`         varchar(191)      default '' comment '订单授权凭证，填写都则对应事务提交进入预授权模式',
    `batch_no`             varchar(191)      default '' comment '本次代商户操作的全局唯一事务编号',
    `create_agent_result`  text comment '提交事务接口结果,json',
    `batch_status`         varchar(191)      default '' comment 'ISV 代商户操作事务状态，事务状态包括：init=初始状态，submit=提交状态，cancel=取消状态，timeout=超时状态，init状态的事务，在【1个小时】后会自动超时',
    `create_agent_time`    timestamp    null comment '事务初始时间',

    `app_name`             varchar(191) null default null comment '代商家创建的小程序应用名称',
    `app_english_name`     varchar(191) null default null comment '小程序英文名称，长度3~20个字符	',
    `app_category_ids`     varchar(191)      default null comment '小程序应用类目，参数格式：一级类目_二级类目。',
    `app_slogan`           varchar(191) comment '代商家创建的小程序的简介，请用一句话简要描述小程序提供的服务；应用上架后一个自然月可修改5次（10~32个字符）	',
    `service_phone`        varchar(191)      default 0 comment '商家小程序的客服电话，推荐填写商家小程序客服电话和邮箱，可以二选一填写，但不能同时为空',
    `service_email`        varchar(191)      default 0 comment '商家小程序客服邮箱 ',
    `app_logo`             varchar(191) comment '商家小程序应用图标，最大256KB,png、jpeg、jpg格式，建议上传像素为180*180',
    `app_desc`             varchar(191) comment '商家小程序描述信息，简要描述小程序主要功能（20-200个字）',
    `create_mini_result`   text comment '创建小程序接口结果,json',
    `create_mini_time`     timestamp    null comment '创建小程序时间',

    `user_id`              varchar(191) comment '授权商户的user_id	',
    `auth_app_id`          varchar(191) comment '授权商户的appid	',
    `confirm_agent_result` text comment '提交事务接口结果,json',
    `confirm_agent_time`   timestamp    null comment '提交事务时间',
    primary key (`rec_id`),
    unique key (`shop_id`),
    unique key (`auth_app_id`)
);

## 阿里授权信息
#drop table if exists `b2c_ali_mini_auth_shop`;
create table `b2c_ali_mini_auth_shop`
(
    `auth_app_id`           varchar(191) comment '授权商户的appid	',
    `user_id`               varchar(191) comment '授权商户的user_id	',
    `app_auth_token`        varchar(191) comment '应用授权令牌	',
    `app_refresh_token`     varchar(191) comment '刷新令牌',
    `expires_in_time`       timestamp    null default null comment '应用授权令牌的过期时间',
    `re_expires_in_time`    timestamp    null default null comment '刷新令牌的过期时间',
    `shop_id`               int          not null comment '店铺Id',
    `app_name`              varchar(191) null default null comment '代商家创建的小程序应用名称',
    `app_english_name`      varchar(191) null default null comment '小程序英文名称，长度3~20个字符	',
    `app_category_ids`      varchar(191)      default null comment '小程序应用类目，参数格式：一级类目_二级类目。',
    `app_slogan`            varchar(191) comment '代商家创建的小程序的简介，请用一句话简要描述小程序提供的服务；应用上架后一个自然月可修改5次（10~32个字符）	',
    `service_phone`         varchar(191)      default '' comment '商家小程序的客服电话，推荐填写商家小程序客服电话和邮箱，可以二选一填写，但不能同时为空',
    `service_email`         varchar(191)      default '' comment '商家小程序客服邮箱 ',
    `app_logo`              varchar(191) comment '商家小程序应用图标，最大256KB,png、jpeg、jpg格式，建议上传像素为180*180',
    `app_desc`              varchar(191) comment '商家小程序描述信息，简要描述小程序主要功能（20-200个字）',
    `is_auth_ok`            tinyint(1)        default 1 comment '是否授权成功,如果公众号后台取消授权，则为0',
    `last_auth_time`        timestamp    null default null comment '最后成功授权的时间',
    `last_cancel_auth_time` timestamp    null default null comment '最后取消授权的时间',
    `app_version`           varchar(191) comment '小程序版本',
    `status`                varchar(191) comment '小程序版本状态，INIT-开发中，AUDITING-审核中，WAIT_RELEASE-审核通过，AUDIT_REJECT-审核驳回，RELEASE-已上架，GRAY-灰度中，OFFLINE-下架	',
    `detail_info`           text comment '小程序详细信息，参考https://docs.open.alipay.com/api_49/alipay.open.mini.version.detail.query/',
    `experience_status`     varchar(191)      default 'notExpVersion' comment '体验版打包状态，expVersionPackged-体验版打包成功，expVersionPackaging-体验版打包中，notExpVersion-非体验版',
    `tester`                text comment '小程序体验者列表,json存储',
    `exp_qr_code_url`       varchar(191) comment '小程序体验版二维码地址	',
    `create_time`           timestamp         default now(),
    primary key (`auth_app_id`),
    unique key (`shop_id`)
);

##小程序版本列表
#drop table if exists `b2c_ali_mini_app_version`;
create table `b2c_ali_mini_app_version`
(
    `rec_id`           int not null auto_increment,
    `app_version`      varchar(191) comment '小程序版本号',
    `app_version_desc` varchar(191) comment '小程序版本描述',
    `create_time`      timestamp default now(),
    primary key (`rec_id`),
    unique key (`app_version`)
);

#drop table if exists `b2c_ali_user_token`;
create table `b2c_ali_user_token`
(
    `rec_id`             int          not null auto_increment,
    `app_id`             varchar(191) not null default '' comment '应用Id',
    `user_id`            varchar(20)  not null comment '支付宝用户Id',
    `access_token`       varchar(191) not null default '' comment '支付宝用户访问令牌',
    `expires_in_time`    timestamp    null     default now() comment '支付宝用户访问令牌过期时间',
    `refresh_token`      varchar(191) not null default '' comment '支付宝用户刷新令牌',
    `re_expires_in_time` timestamp    null     default now() comment '支付宝用户刷新令牌过期时间',
    primary key (`rec_id`),
    unique key (`app_id`, `user_id`)
);

## 小程序跳转链接，申请发布新版本列表
#DROP TABLE IF EXISTS `b2c_mp_jump_version`;
create table `b2c_mp_jump_version`
(
    `id`          int(11)     not null auto_increment,
    `shop_id`     varchar(64) not null,
    `flag`        tinyint(1)  not null default '0' comment '0:申请中，1:已提交',
    `add_time`    timestamp   null     default null,
    `update_time` timestamp   null     default null,
    primary key (`id`)
);

### 第三方抓取商品表 `b2c_grasp_goods`
#drop table if exists `b2c_grasp_goods`;
create table `b2c_grasp_goods`
(
    `goods_id`            int(8)         not null auto_increment,
    `shop_id`             int(11)        null     default '0' comment '店铺ID',
    `cat_id`              int(5)         not null default '0',
    `goods_sn`            varchar(60)    not null default '',
    `goods_name`          varchar(120)   not null default '',
    `brand_id`            int(11)        not null default 0 comment '品牌ID',
    `goods_ad`            varchar(1024)  null     default '' comment '广告词',
    `goods_number`        int(11)        not null default '0' comment '库存',
    `goods_weight`        decimal(10, 3) null     default '0.000',
    `market_price`        decimal(10, 2) null     default '0.00',
    `shop_price`          decimal(10, 2) not null default '0.00',
    `cost_price`          decimal(10, 2)          default 0.00 null comment '成本价',
    `goods_desc`          text           null,
    `goods_img`           varchar(191)   null     default '',
    `is_on_sale`          tinyint(1)     null     default '1' comment '是否在售，1在售，0下架',
    `is_delete`           tinyint(1)     null     default '0',
    `goods_type`          tinyint(2)              default 0 null comment '商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品',
    `deliver_template_id` int(5)         null     default '0' comment '运费模板ID',
    `goods_sale_num`      int(8)         null     default '0' comment '销售数量',
    `goods_collect_num`   int(8)         null     default '0' comment '收藏数量',
    `add_time`            timestamp      null     default CURRENT_TIMESTAMP,
    `update_time`         timestamp      null     default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '最后修改时间',
    `state`               tinyint(1)     null     default '0' comment '审核状态,0待审核 1 审核通过 2 违规下架',
    `reason`              text           null comment '违规下架原因',
    `sub_account_id`      int(11)        null     default '0' comment '子帐号ID，主要用于官方店铺',
    `sale_time`           timestamp      null     default CURRENT_TIMESTAMP comment '上架时间',
    `limit_buy_num`       int(11)        null     default '0' comment '最少起购数量，0不限购',
    `unit`                varchar(60)    null     default '' comment '商品单位',
    `add_sale_num`        int(11)        null     default '0' comment '虚假销量',
    `limit_max_num`       int(11)                 default 0 null comment '最多起购数量，0不限购',
    `sale_type`           tinyint(1)     null     default '0' comment '上架状态,0立即上架， 1审核通过 2 加入仓库',
    `sort_id`             int(11)        null     default '0',
    `goods_video`         varchar(191)   null     default '' comment '视频',
    `goods_video_img`     varchar(191)   null     default '' comment '视频首图',
    `goods_video_size`    int            null comment '视频尺寸',
    `goods_video_id`      int            null comment '视频ID',
    `goods_page_id`       int(11)        not null default '0' comment '详情页装修模板ID',
    `is_page_up`          tinyint(1)     not null default '1' comment '是否在文本区域上方',
    `is_card_exclusive`   tinyint(1)              default 0 comment '是否会员卡专属',
    `base_sale`           int(8)         null     default '0' comment '初始销量',
    `on_sale_shop_id`     text           null comment '发布店铺',
    `source`              tinyint(1)              default '0' comment '商品来源,0：店铺自带；1、2..等：不同类型店铺第三方抓取自带商品来源',
    `is_control_price`    tinyint(1)              default '0' comment '是否控价,0：不控价；1：控价',
    `goods_flag`          tinyint(1)              default '1' comment '商品来源平台1：欧派；2：寺库',
    unique index `goods_id` (`goods_id`, `shop_id`),
    unique index `goods_sn` (`goods_sn`, `shop_id`),
    index `goods_id_2` (`goods_id`),
    index `shop_id` (`shop_id`),
    index `cat_id` (`cat_id`)
);

## 店铺自定义分类
#drop table if exists `b2c_sort`;
create table `b2c_sort`
(
    `sort_id`     int(11)      not null auto_increment,
    `sort_name`   varchar(90)           default '',
    `parent_id`   smallint(5)  not null default '0',
    `level`       smallint(5)  not null default 0,
    `has_child`   tinyint(1)   not null default 0,
    `create_time` timestamp             default now(),
    `sort_img`    varchar(191) not null default '' comment '一级分类是头图 其他为分类图标',
    `img_link`    varchar(191) null     default '' comment '图标或者头图链接',
    `first`       smallint(2)  not null default '0' comment '优先级',
    `type`        tinyint(1)   not null default 0 comment '0普通商家分类 1推荐分类',
    `sort_desc`   varchar(191)          default '',
    `keywords`    varchar(191)          default '',
    primary key (`sort_id`),
    key `parent_id` (`parent_id`)
);

## 商品规格组合的产品表 `b2c_goods_spec_product`
#drop table if exists `b2c_goods_spec_product`;
create table `b2c_goods_spec_product`
(
    `prd_id`           int(10)        not null auto_increment,
    `shop_id`          int(11)        not null default '0',
    `goods_id`         int(10)        not null default '0',
    `prd_price`        decimal(10, 2) not null default '0.00',
    `prd_market_price` decimal(10, 2) not null default '0.00' comment '市场价',
    `prd_cost_price`   decimal(10, 2)          default 0.00 null comment '成本价',
    `prd_number`       int(11)        not null default '1' comment '当前规格组合产品库存',
    `prd_sn`           varchar(65)    not null default '' comment '商家编码',
    `prd_codes`        varchar(500)   not null default '' comment '商品条码',
    `prd_specs`        varchar(1024)  not null default '',
    `prd_desc`         varchar(1024)  not null default '' comment '规格描述，格式例子：颜色:红色 尺码:S',
    `del_flag`         tinyint(1)     not null default '0',
    `self_flag`        tinyint(1)              default '0' not null comment '1:商家自己添加商品，其他没用',
    `low_shop_price`   varchar(1024)  not null default '0.00' comment '最低售出价格',
    `prd_img`          varchar(1024)  not null default '' comment '图片地址',
    primary key (`prd_id`),
    key `gsp_goods_id` (`goods_id`),
    key `gsp_goods_codes` (`prd_codes`),
    key `gsp_prd_sn` (`prd_sn`)
);

### 规格表 `b2c_spec`
#drop table if exists `b2c_spec`;
create table `b2c_spec`
(
    `spec_id`   int(10)     not null auto_increment,
    `spec_name` varchar(60) not null default '',
    `del_flag`  tinyint(1)  not null default '0',
    `shop_id`   int(11)     not null default 0 comment '店铺ID',
    primary key (`spec_id`)
);

#drop table if exists `b2c_spec_vals`;
create table `b2c_spec_vals`
(
    `specvalid`   int(10)     not null auto_increment,
    `spec_id`     int(10)     not null default '0',
    `specvalname` varchar(60) not null default '',
    `del_flag`    tinyint(1)  not null default '0',
    `shop_id`     int(11)     not null default 0 comment '店铺ID',
    primary key (`specvalid`),
    key `spec_id` (`spec_id`)
);

## 商品图片表 `b2c_goods_img`
#drop table if exists `b2c_goods_img`;
create table `b2c_goods_img`
(
    `img_id`   int(8)       not null auto_increment,
    `goods_id` int(8)       not null default '0',
    `img_url`  varchar(191) not null default '',
    `img_desc` varchar(191) not null default '',
    primary key (`img_id`),
    key `goods_id` (`goods_id`)
);

##商品品牌
#drop table if exists `b2c_goods_brand`;
create table `b2c_goods_brand`
(
    `id`          int(11)      not null auto_increment,
    `brand_name`  varchar(500) not null comment '品牌名称',
    `e_name`      varchar(500) not null default '' comment '品牌英文名称',
    `logo`        varchar(255)          default null comment '品牌Logo',
    `first`       tinyint(3)   not null default '0' comment '优先级',
    `add_time`    datetime     not null,
    `update_time` datetime              default null,
    `is_delete`   tinyint(1)   not null default '0' comment '0为未删除 1为删除',
    `desc`        text                  default null comment '品牌介绍',
    primary key (`id`)
);

## 店铺数据统计
#drop table if exists `b2c_statistics_shop`;
create table `b2c_statistics_shop`
(
    `id`                    mediumint(10)  not null auto_increment,
    `shop_id`               int(11)        not null default 0 comment '店铺ID',
    `ref_date`              datetime       null     default now() comment '统计数据时间',
    `type`                  tinyint(1)     not null default 0 comment '统计类型 1天 7周 30月 3季度',
    `week`                  tinyint(2)     not null default 0 comment '第几周',
    `months`                tinyint(2)     not null default 0 comment '第几周',
    `quarter`               tinyint(2)     not null default 0 comment '第几季度',
    `new_user`              mediumint(8)   not null default 0 comment '注册用户',
    `order_num`             mediumint(8)   not null default 0 comment '订单数',
    `wx_order_num`          mediumint(8)   not null default 0 comment '微信支付订单数',
    `cod_order_num`         mediumint(8)   not null default 0 comment '货到付款订单数',
    `balance_order_num`     mediumint(8)   not null default 0 comment '余额订单',
    `score_order_num`       mediumint(8)   not null default 0 comment '积分订单',
    `wx_money`              decimal(10, 2) not null default 0 comment '微信支付总额',
    `card_money`            decimal(10, 2) not null default 0 comment '卡余额总额',
    `balance_money`         decimal(10, 2) not null default 0 comment '余额总额',
    `score_money`           decimal(10, 2) not null default 0 comment '积分总额',
    `order_user_num`        mediumint(8)   not null default 0 comment '下单用户',
    `wx_user_num`           mediumint(8)   not null default 0 comment '微信下单用户',
    `cod_user_num`          mediumint(8)   not null default 0 comment '货到付款用户',
    `balance_user_num`      mediumint(8)   not null default 0 comment '余额购买用户',
    `score_user_num`        mediumint(8)   not null default 0 comment '积分购买用户',
    `order_goods_num`       mediumint(8)   not null default 0 comment '订单商品数量',
    `wx_goods_num`          mediumint(8)   not null default 0 comment '微信支付商品',
    `cod_goods_num`         mediumint(8)   not null default 0 comment '货到付款商品',
    `balance_goods_num`     mediumint(8)   not null default 0 comment '余额购买商品',
    `score_goods_num`       mediumint(8)   not null default 0 comment '积分购买商品',
    `wx_order_pay`          decimal(10, 2) not null default 0 comment '微信金额',
    `cod_order_pay`         decimal(10, 2) not null default 0 comment '货到付款金额',
    `balance_order_pay`     decimal(10, 2) not null default 0 comment '余额金额',
    `score_order_pay`       decimal(10, 2) not null default 0 comment '积分金额',
    `wx_order_balance`      decimal(10, 2) not null default 0 comment '微信支付余额',
    `cod_order_balance`     decimal(10, 2) not null default 0 comment '货到付款支付余额',
    `balance_order_balance` decimal(10, 2) not null default 0 comment '余额支付余额',
    `score_order_balance`   decimal(10, 2) not null default 0 comment '积分支付余额',
    `wx_order_card`         decimal(10, 2) not null default 0 comment '微信支付卡余额',
    `cod_order_card`        decimal(10, 2) not null default 0 comment '货到付款支付卡余额',
    `balance_order_card`    decimal(10, 2) not null default 0 comment '余额支付卡余额',
    `score_order_card`      decimal(10, 2) not null default 0 comment '积分支付卡余额',
    `wx_order_score`        mediumint(8)   not null default 0 comment '微信支付积分',
    `cod_order_score`       mediumint(8)   not null default 0 comment '货到付款支付积分',
    `balance_order_score`   mediumint(8)   not null default 0 comment '余额支付积分',
    `score_order_score`     mediumint(8)   not null default 0 comment '积分支付卡积分',
    primary key (`id`),
    key (`ref_date`),
    key (`shop_id`)
);
## 平台店铺数据统计
#drop table if exists `b2c_user_summary_trend`;
create table `b2c_user_summary_trend`
(
    `id`                   int(11)    not null auto_increment,
    `shop_id`              int(11)    not null default '0' comment '店铺ID',
    `ref_date`             date       not null comment '2018-09-04',
    `type`                 tinyint(2) not null comment '1，7，30',
    `login_data`           int(11)    not null comment '访问会员数据',
    `user_data`            int(11)    not null comment '累积会员数',
    `coupon_data`          int(11)    not null comment '领券会员数',
    `cart_data`            int(11)    not null comment '加购会员数',
    `reg_user_data`        int(11)    not null comment '注册数',
    `upgrade_user_data`    int(11)    not null comment '升级会员数',
    `charge_user_data`     int(11)    not null comment '储值会员数',
    `order_user_data`      int(11)    not null comment '成交客户数',
    `new_order_user_data`  int(11)    not null comment '成交新客户数',
    `old_order_user_data`  int(11)    not null comment '成交老客户数',
    `total_paid_money`     int(11)    not null comment '总成交金额',
    `new_paid_money`       decimal(10, 4)      default null comment '成交新客户支付金额',
    `old_paid_money`       decimal(10, 0)      default null comment '成交老客户支付金额',
    `pay_goods_number`     int(11)             default null comment '付款件数',
    `new_pay_goods_number` int(11)             default null comment '付款件数',
    `old_pay_goods_number` int(11)             default null comment '付款件数',
    `pay_order_num`        int(11)             default '0' comment '成交订单数',
    `login_pv`             int(11)             default '0' comment '登录pv',
    `order_num`            int(11)    not null default '0' comment '下单笔数',
    `order_user_num`       int(11)             default '0' comment '下单人数(生成订单就算)',
    `add_time`             timestamp  not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '统计时间',
    primary key (`id`),
    key `ref_type` (`ref_date`, `type`) using btree
);

#drop table if exists `b2c_mp_user_portrait`;
create table `b2c_mp_user_portrait`
(
    `shop_id`      int(11)    null     default '0' comment '店铺ID',
    `ref_date`     char(30)   not null comment '时间： 如： "20180313"',
    `visit_uv_new` longtext comment '新用户',
    `visit_uv`     longtext comment '活跃用户',
    `type`         tinyint(4) not null default '0' comment '0:昨天，1：最近7天，2:30天',
    `add_time`     timestamp  not null default CURRENT_TIMESTAMP comment '添加时间',
    key `type` (`type`) using btree,
    key `ref_date` (`ref_date`) using btree
);

#日志管理表
#drop table if exists `b2c_log_manage`;
create table `b2c_log_manage`
(
    `log_id`           bigint(20)   not null auto_increment,
    `shop_id`          int(11)      not null comment '店铺ID',
    `error_level`      tinyint(2)   not null default '0' comment '错误等级：0正常，1debug，2info，3error',
    `error_code`       varchar(8)   not null default '0' comment '错误编码',
    `error_msg`        varchar(128) not null default '' comment '错误说明',
    `error_content`    text comment '错误内容',
    `error_source`     tinyint(2)   not null default '0' comment '错误来源：0无，1erp，2crm，3pos，4寺库，5欧派，...',
    `deal_status`      tinyint(1)   not null default '0' comment '处理结果：0待处理，1已处理',
    `action`           varchar(64)  not null default '' comment '动作方法',
    `action_name`      varchar(64)  not null default '' comment '动作方法名称',
    `request_content`  text comment '请求内容 json串',
    `response_content` text comment '响应内容 json串',
    `ip`               varchar(100)          default null comment '请求ip',
    `deal_time`        datetime comment '处理时间',
    `deal_person`      varchar(32) comment '处理人员',
    `in_time`          datetime comment '添加时间',
    `up_time`          datetime comment '更新时间',
    `remark`           varchar(600) comment '备注',
    `identity_id`      int                   default 0 null comment '关联外部表',
    primary key (`log_id`),
    key `shop_id` (`shop_id`)
);

#数据库更新日志表
#drop table if exists `b2c_db_option_record`;
create table `b2c_db_option_record`
(
    `record_id`   int(9)    not null auto_increment,
    `version`     varchar(10)        default '' comment '版本号',
    `option_rst`  longtext comment '执行结果',
    `db_type`     varchar(32)        default '' comment '更新类型：mian主库，shop从库',
    `shop_id`     int(11)   not null default '0' comment '店铺ID，单个店铺更新时使用',
    `in_time`     timestamp null comment '添加时间',
    `up_time`     timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    `option_type` varchar(32)        default '' comment '操作类型：command命令行操作，SQL语句操作',
    `total_num`   int(6)             default '0' comment '总更新条数',
    `success_num` int(6)             default '0' comment '成功条数',
    primary key (`record_id`)
);

#上传uyun记录
#drop table if exists `b2c_upload_uyun_record`;
create table `b2c_upload_uyun_record`
(
    `record_id`        bigint(20)   not null auto_increment,
    `shop_id`          int(11)      not null comment '店铺ID',
    `file_size`        int(64)      not null comment '文件大小',
    `file_url`         varchar(500) not null default '' comment '文件url',
    `update_timestamp` varchar(500) not null default '' comment '文件更新时间戳',
    `update_date`      timestamp    not null comment '文件更新日期',
    `upload_status`    tinyint(2)            default 0 comment '文件上传状态 0：上传成功；1：上传失败（或开关关闭后未上传的文件）',
    `upload_time`      timestamp             default now() comment '文件上传时间',
    `fail_reason`      text         not null comment '失败原因',
    `fail_date`        timestamp    null default null comment '失败时间',
    primary key (`record_id`),
    key `file_url` (`file_url`),
    key `shop_id` (`shop_id`)
);
# 第三方服务提醒
#drop table if exists `b2c_third_party_services`;
create table `b2c_third_party_services`
(
    `shop_id`        int        not null comment '店铺ID',
    `account_action` tinyint(1) not null default 1 comment '账号类型 1:主账号 2：子账号',
    `account_id`     int        not null comment '账号ID',
    `service_action` tinyint             default 1 comment '服务类型 1:订单提醒',
    `service_detail` text comment '服务明细',
    `add_time`       datetime comment '操作时间',
    index `shop_id` (`shop_id`),
    index `account_type` (`account_action`, `account_id`, `service_action`),
    index `account_id` (`account_id`)
);

#task jobs
CREATE TABLE `b2c_task_job_main`
(
    `id`                INT(11)   NOT NULL AUTO_INCREMENT,
    `shop_id`           INT(11)        DEFAULT '0' COMMENT '店铺ID',
    `content_id`        INT(11)        DEFAULT '0' COMMENT 'MQ消息内容ID',
    `status`            TINYINT(3)     DEFAULT '0' COMMENT '任务状态：0待执行,1执行中,2已完成',
    `progress`          TINYINT(3)     DEFAULT '0' COMMENT '任务进度：0-100',
    `class_name`        varchar(100)   DEFAULT '' COMMENT '反序列化类名（全称）',
    `execution_type`    INT(8)         DEFAULT '0' COMMENT '执行类型:任务类型标识',
    `cycle`             INT(11)        DEFAULT '0' COMMENT '轮循间隔(单位:秒)',
    `type`              TINYINT(3)     DEFAULT '0' COMMENT 'task任务类型(立刻执行；定时执行；循环执行)',
    `next_execute_time` TIMESTAMP NULL DEFAULT null COMMENT '下次执行开始日期',
    `start_time`        TIMESTAMP NULL DEFAULT null COMMENT '周期开始时间',
    `end_time`          TIMESTAMP NULL DEFAULT null COMMENT '周期结束时间',
    `create_time`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`       TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    `del_flag`          TINYINT(1)     DEFAULT '0' COMMENT '删除标识：0未删除，1已删除',
    PRIMARY KEY (`id`)
);

CREATE TABLE `b2c_task_job_content`
(
    `id`          INT(11)   NOT NULL AUTO_INCREMENT,
    `content`     TEXT COMMENT '消息内容',
    `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    `del_flag`    TINYINT(1)     DEFAULT '0' COMMENT '删除标识：0未删除，1已删除',
    PRIMARY KEY (`id`)
);

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

