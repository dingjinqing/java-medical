set foreign_key_checks = false;

-- -- 店铺
-- drop table if exists `b2c_shop`;
create table `b2c_shop` (
  `shop_id`                         int(11)                       not null  auto_increment comment '店铺ID',
  `sys_id`                          int(11)                       not null,
  `mobile`                          varchar(32)                   not null  default '',
  `receive_mobile`                  varchar(32)                             default '' comment ' 接收通知手机号码',
  `shop_name`                       varchar(50)                             default '' comment '店铺名称',
  `shop_avatar`                     varchar(191)                            default '/image/admin/shop_default.png' comment '店铺头像',
  `shop_bg_path`                    varchar(191)                            default 'upload/images/default/0picstor.jpg' comment '店铺背景图片',
  `shop_phone`                      varchar(50)                             default '' comment '店铺客服电话',
  `shop_notice`                     varchar(191)                            default '' comment '店铺公告',
  `shop_wx`                         varchar(191)                            default '' comment '店铺客服微信',
  `shop_email`                      varchar(191)                            default '',
  `created`                         timestamp                     not null  default now() comment '创建时间',
  `is_enabled`                      tinyint(1)                              default '0' comment '0:正常，1：禁用',
  `province_code`                   mediumint(10)         not null  default '0' comment '所在省',
  `province_name`                   varchar(50)                   not null  default '',
  `city_code`                       mediumint(10)         not null  default '0' comment '所在城市',
  `city_name`                       varchar(120)                  not null  default '',
  `district_code`                   mediumint(10)         not null  default '0' comment '所在区县',
  `district_name`                   varchar(120)                  not null  default '',
  `address`                         varchar(191)                  not null  default '' comment '所在地址',
  `complete_address`                varchar(512)                  not null  default '' comment '所在完整地址',
  `shop_sell_type`                  int               not null  default 254 comment '经营品类,254：其他',
  `shop_qq`                         varchar(20)                             default '' comment '店铺客服QQ',
  `last_login_ip`                   varchar(40)                             default '' comment '上次登录IP',
  `state`                           tinyint(1)                              default 0 comment '0 入驻申请，1审核通过，2审核不通过',
  `business_state`                  tinyint(1)                              default 0 comment '营业状态 0未营业 1营业',
  `manage_fee`                      decimal(10, 2) default '0.00' not null comment '平台管理费',
  `shop_license`                    varchar(191) comment '营业执照',
  `shop_tax_credential`             varchar(191) comment '税务登记证',
  `organization_code_certificate`   varchar(191)                            default null comment '组织机构代码证',
  `permit_for_opening_bank_account` varchar(191)                            default null comment '银行开户许可证',
  `financial_registration`          varchar(191)                            default null comment '财政登记证',
  `shop_bank_name`                  varchar(191)                            default '' comment '开户行姓名',
  `shop_bank`                       varchar(191)                            default '' comment '开户行',
  `shop_bank_branch`                varchar(191)                            default '' comment '开户行分行',
  `shop_bank_no`                    varchar(191)                            default '' comment '开户行卡号',
  `surplus`                         decimal(10, 2)                not null  default 0.0 comment '余额',
  `reject_reason`                   varchar(191)                            default null comment '驳回理由',
  `shop_stat_code`                  varchar(191)                            default '' comment '第三方统计代码',
  `shop_icp`                        varchar(191)                            default '' comment 'IP信息',
  `shop_copyright`                  varchar(191)                            default '' comment '版权信息',
  `db_config`                       varchar(191)                            default '' comment 'db config,json format',
  `share_config` TEXT NULL   COMMENT '分享设置',
  primary key (`shop_id`),
  unique key (`mobile`)
);

-- -- 帮助中心-文章
-- drop table if exists `b2c_article`;
create table `b2c_article` (
  `article_id`      int(11) not null auto_increment,
  `category_id`     int(11) not null default 1 comment '文章分类',
  `title`           varchar(256)     default null,
  `author`          varchar(50)      default null,
  `keyword`         varchar(256)     default null comment '标签',
  `desc`            varchar(1024)    default null comment '文章描述',
  `content`         text,
  `is_recommend`    tinyint(1)       default '0' comment '1:推荐',
  `is_top`          tinyint(1)       default '0' comment '1:置顶',
  `status`          tinyint(1)       default '0' comment '0未发布,1已发布',
  `pub_time`        timestamp        default now() comment '发布时间',
  `update_time`     timestamp        default now() comment '更新时间',
  `create_time`     datetime         default now(),
  `last_visit_time` datetime         default null,
  `pv`              int(11)          default null,
  `show_footer`     tinyint(1)       default '0' comment '0:不在footer显示，1：显示',
  primary key (`article_id`),
  key (`is_recommend`),
  key (`is_top`)
);

-- drop table if exists `b2c_article_category`;
create table `b2c_article_category` (
  `category_id`    int(11)      not null auto_increment,
  `category_name`  varchar(191) not null default '',
  `use_footer_nav` tinyint(1)            default '0' comment '是否用于底部导航',
  primary key (`category_id`)
);

-- -- 用户
-- drop table if exists `b2c_user`;
create table `b2c_user` (
  `user_id`             int(8)               not null  auto_increment,
  `shop_id`             int(8)               not null  default 0,
  `username`            varchar(100)         not null comment '用户名',
  `user_pwd`            varchar(60)          not null comment '密码',
  `user_cid`            varchar(64)          not null  default '',
  `mobile`              varchar(100)                   default null comment '电话',
  `user_code`           varchar(100)                   default null comment '会员卡号',
  `wx_openid`           varchar(128)         not null  default '',
  `email`               varchar(100)                   default null comment '邮箱',
  `create_time`         timestamp            not null  default CURRENT_TIMESTAMP comment '创建时间',
  `wechat`              varchar(100)         not null comment '微信',
  `fanli_grade`         int(11)              not null  default '0' comment '返利会员级别',
  `user_grade`          int(11)              not null  default '1' comment '会员级别',
  `invite`              int(10) default 0    null,
  `invite_source`       varchar(32)          null comment '邀请来源:groupbuy.拼团,bargain.砍价,integral.积分,seckill.秒杀,lottery.抽奖',
  `invitation_code`     int(8)               not null  default 0 comment '邀请码',
  `account`             decimal(10, 2)       not null  default '0.00' comment '用户余额',
  `discount`            int(11)              not null  default '0' comment '折扣',
  `discount_grade`      int(11)              not null  default '0' comment '会员折扣等级',
  `is_delete`           tinyint(1)   not null  default '0',
  `delete_time`         timestamp            null      default null comment '删除时间',
  `growth`              int(11)                        default '0' comment '成长值',
  `score`               int(11)                        default '0' comment '积分',
  `source`              int(11) default -1   null comment '门店来源-1未录入0后台>0为门店',
  `invite_id`           int(11) default '0'  null comment '邀请人ID',
  `invite_expiry_date`  date                           default null comment '邀请失效时间',
  `wx_union_id`         varchar(191)                   default '' comment '小程序unionid',
  `update_time`         timestamp            null      default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '最后修改时间',
  `is_distributor`      tinyint(2) default 0 null comment '是否是分销员',
  `invite_act_id`       int(10) default 0    null comment '邀请来源活动ID',
  `distributor_level`   tinyint(2)                     default '1' comment '用户等级',
  `ali_user_id`         varchar(191)         not null  default '' comment '支付宝用户ID',
  `device`              varchar(50)          null comment '登录设备',
  `invite_protect_date` date                 null comment '邀请保护时间',
  `look_collect_time`   timestamp            null      default null comment '最近看见收藏有礼图标时间',
  `get_collect_gift`    tinyint(1)   not null  default '0' comment '是否获得收藏好礼：0未获得，1已获得',
  `invite_group` INT(6) DEFAULT 0  NULL   COMMENT '分销员分组',
  `unit_price`  decimal(10, 2)       not null  default '0.00' comment '客单价',
  primary key (`user_id`),
  unique key `mobile` (`mobile`, `shop_id`),
  key (`user_code`),
  key (`wx_union_id`)
);

-- -- 用户地址

-- drop table if exists `b2c_user_address`;
create table `b2c_user_address` (
  `address_id`       mediumint(8)  not null auto_increment,
  `address_name`     varchar(50)           not null default '',
  `user_id`          mediumint(8)  not null default '0',
  `user_cid`         varchar(40)           not null default '',
  `wx_openid`        varchar(128)          not null default '',
  `consignee`        varchar(60)           not null default '',
  `email`            varchar(60)           not null default '',
  `country_code`     mediumint(10)         not null default '0',
  `province_name`    varchar(191)                   default '',
  `province_code`    mediumint(10)         not null default '0',
  `city_code`        mediumint(10)         not null default '0',
  `city_name`        varchar(191)                   default '',
  `district_code`    mediumint(10)         not null default '0',
  `district_name`    varchar(191)                   default '',
  `address`          varchar(120)          not null default '',
  `complete_address` varchar(191)          not null default '',
  `zipcode`          varchar(60)           not null default '',
  `tel`              varchar(60)           not null default '',
  `mobile`           varchar(60)           not null default '',
  `best_time`        varchar(120)          not null default '',
  `is_default`       tinyint(1)    not null default '0',
  `last_used_time`   timestamp             null     default null,
  primary key (`address_id`),
  key `user_id` (`user_id`)
);

-- -- 用户积分表
-- drop table if exists `b2c_user_score`;
create table `b2c_user_score` (
  `id`           int(11)               not null auto_increment,
  `user_id`      int(11)               not null,
  `score`        int(11)               not null,
  `status`       tinyint(1) default 0  not null comment '0:未使用 1:已使用 2：已过期 3：已退款',
  `flow_no`      varchar(20)           null comment '积分流水号',
  `usable_score` int default 0         null comment '可用积分',
  `identity_id`  varchar(500)          null comment '关联其他属性：例如order_sn',
  `goods_id`     int(11)               not null default '0',
  `order_sn`     varchar(20)           not null default '',
  `shop_id`      int(11)               not null default 0 comment '店铺ID',
  `desc`         varchar(191)          not null default '',
  `remark`       varchar(1024) comment '备注',
  `in_time`      datetime                       default null,
  `expire_time`  datetime                       default null,
  `admin_user`   varchar(191)          not null default '0' comment '操作员',
  primary key (`id`),
  key (`shop_id`)
);

-- -- 用户详情表
-- drop table if exists `b2c_user_detail`;
create table `b2c_user_detail` (
  `id`              int(11)     not null auto_increment,
  `user_id`         int(8)      not null,
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
  primary key (`id`),
  unique key (`user_id`)
);

-- -- 积分设置
-- drop table if exists `b2c_user_score_set`;
create table `b2c_user_score_set` (
  `id`          int(11)      not null    auto_increment,
  `shop_id`     int(11)      not null    default 0 comment '店铺ID',
  `score_name`  varchar(20)  not null comment '购买:buy,评价:comment,兑换:convert',
  `status`      tinyint(1)   not null    default '0' comment '0:未启用,1:启用',
  `two_status`  tinyint(1)   not null    default '0',
  `set_val`     varchar(10)  not null    default '',
  `set_val2`    varchar(10)  not null    default '',
  `set_val3`    text,
  `sign_val`    int(11)      not null    default '0' comment '签到积分',
  `sign_date`   tinyint(1)   not null    default '0' comment '签到天数',
  `desc`        varchar(191) not null    default '',
  `in_time`     datetime                 default null,
  `up_time`     datetime                 default null,
  `growth_flag` tinyint(1)   not null    default '0' comment '0:不送成长值，1：送成长值',
  primary key (`id`)
);

-- -- 用户等级设置
-- drop table if exists `b2c_user_grade`;
create table `b2c_user_grade` (
  `id`          int(11)     not null auto_increment,
  `shop_id`     int(11)     not null default 0 comment '店铺ID',
  `grade`       varchar(10) not null comment '会员等级：reg:注册,bronze:铜,silver:银,gold:金,diamond:钻石',
  `grade_name`  varchar(20) not null comment '等级名称',
  `min_val`     int(11)     not null default '0' comment '最小成长值',
  `max_val`     int(11)     not null default '0' comment '最大成长史',
  `reduce`      int(11)     not null default '0' comment '一年后扣除的成长值',
  `in_time`     datetime             default null,
  `up_time`     datetime             default null,
  `settle_time` datetime             default null comment '结算时间',
  `divide`      decimal(10, 2)       default '0.00' comment '分成',
  `discount`    decimal(10, 2)       default '0.00' comment '折扣',
  primary key (`id`)
);

-- -- 用户成长值设置
-- drop table if exists `b2c_user_growth_set`;
create table `b2c_user_growth_set` (
  `id`          int(11)      not null auto_increment,
  `shop_id`     int(11)      not null default 0 comment '店铺ID',
  `growth_name` varchar(20)  not null comment 'login,buy,comment,shai,month',
  `score`       int(11)      not null default '0',
  `score_flag`  tinyint(1)   not null default '1' comment '0:不送积分，1：送积分',
  `min_val`     int(11)      not null default '0',
  `max_val`     int(11)      not null default '0',
  `flag`        tinyint(1)   not null default '0' comment '购物送成长值方式，0:订单金额,1:实际支付金额',
  `desc`        varchar(191) not null default '',
  `in_time`     datetime              default null,
  `up_time`     datetime              default null,
  `share_flag`  tinyint(1)   not null default '0' comment '0:分享一次赠送,1:每天分享赠送',
  primary key (`id`),
  key (`shop_id`)
);

-- -- 用户成长值表
-- drop table if exists `b2c_user_growth`;
create table `b2c_user_growth` (
  `id`          int(11)      not null auto_increment,
  `user_id`     int(11)      not null,
  `shop_id`     int(11)      not null default 0 comment '店铺ID',
  `score`       int(11)      not null,
  `status`      tinyint(1)   not null default '0' comment '0:未过期，1:已过期',
  `goods_id`    int(11)      not null default '0',
  `order_sn`    varchar(20)  not null default '',
  `desc`        varchar(191) not null comment '登录，购物，评价，晒单',
  `in_time`     datetime              default null,
  `expire_time` datetime              default null,
  `growth_flag` tinyint(2)   not null default '0' comment '0:评价商品送给成长值,1:分享商品赠送成长值',
  `deal_remark` varchar(100) comment '备注',
  primary key (`id`),
  key (`shop_id`)
);
-- -- 店铺积分，成长值开关设置表
-- drop table if exists `b2c_shop_score_cfg`;
create table `b2c_shop_score_cfg` (
  `id`      int(11) not null auto_increment,
  `growth`  int(11) not null default '0' comment '0:关闭，1:开启',
  `score`   int(11) not null default '0' comment '0:关闭，1:开启',
  `comment` int(11)          default '0' comment 'comment:0:关闭评论，1：不用审批，2：先发后审，3：先审后发',
  `in_time` datetime         default null,
  `up_time` datetime         default null,
  `sign`    int(11) not null default '0' comment '0:关闭，1:开启',
  `sys_id`  int(10) not null default '0' comment '商家ID',
  primary key (`id`)
);

-- -- 用户升级记录
-- drop table if exists `b2c_user_upgrade`;
create table `b2c_user_upgrade` (
  `id`         int(11)     not null auto_increment,
  `user_id`    int(11)     not null,
  `shop_id`    int         not null default 0 comment '店铺ID',
  `grade_id`   int(11)     not null,
  `grade`      varchar(20) not null,
  `grade_name` varchar(20) not null,
  `in_time`    datetime             default null,
  primary key (`id`)
);
-- -- 消息中心
-- drop table if exists `b2c_user_msg`;
create table `b2c_user_msg` (
  `id`       int(11)      not null auto_increment,
  `user_id`  int(11)      not null,
  `shop_id`  int(11)      not null default 0 comment '店铺ID',
  `msg_type` varchar(20)  not null comment 'login,comment,order_finish,discount,upgrade,return_order,deliver_goods,article',
  `msg_desc` varchar(191) not null,
  `msg_date` date         not null,
  `msg_id`   varchar(20)           default null comment '评论时是商品id，订单是订单id',
  `status`   tinyint(1)   not null default '0' comment '0:未读，1:已读',
  `url`      varchar(191)          default null,
  `in_time`  datetime              default null,
  primary key (`id`),
  key (`shop_id`)
);

-- --  运费模板表
-- drop table if exists `b2c_deliver_fee_template`;
create table `b2c_deliver_fee_template` (
  `deliver_template_id` int(11)  not null auto_increment,
  `template_name`       varchar(191)              default '' comment '模板名称',
  `template_content`    text comment '模板内容，json存储',
  `update_time`         timestamp        null     default null,
  `created`             timestamp                 default now(),
  `shop_id`             int(11)          not null default 0 comment '店铺ID',
  `flag`                tinyint(1)       not null default 0 comment '0运费模板,1重量运费模板',
  primary key (`deliver_template_id`),
  key (`shop_id`)
);

-- --  商品运费模关联表
-- drop table if exists `b2c_goods_deliver_template_link`;
create table `b2c_goods_deliver_template_link` (
  `rec_id`              int(11)  not null auto_increment,
  `goods_id`            int(11)  not null default 0,
  `deliver_template_id` int(11)  not null default 0,
  `created`             timestamp                 default now(),
  primary key (`rec_id`),
  key (`goods_id`)
);
-- --  移动端只支持分类一级
-- drop table if exists `b2c_category`;
create table `b2c_category` (
  `cat_id`      smallint(5)  not null auto_increment,
  `cat_name`    varchar(90)                   default '',
  `keywords`    varchar(191)                  default '',
  `cat_desc`    varchar(191)                  default '',
  `parent_id`   smallint(5)  not null default '0',
  `level`       smallint(5)          not null default 0,
  `has_child`   tinyint(1)           not null default 0,
  `create_time` timestamp                     default now(),
  `cat_img`     varchar(191)         not null default '' comment '分类图标',
  primary key (`cat_id`),
  key `parent_id` (`parent_id`)
);
-- -- --  商品表 `b2c_goods`
-- drop table if exists `b2c_goods`;
create table `b2c_goods` (
  `goods_id`            int(8)                         not null auto_increment,
  `shop_id`             int(11)                        null     default '0' comment '店铺ID',
  `cat_id`              int(5)                 not null default '0',
  `goods_sn`            varchar(60)                    not null default '' collate 'utf8mb4_unicode_ci',
  `goods_name`          varchar(120)                   not null default '' collate 'utf8mb4_unicode_ci',
  `brand_id`            int(11)                        not null default 0 comment '品牌ID',
  `goods_ad`            varchar(1024)                  null     default '' comment '广告词' collate 'utf8mb4_unicode_ci',
  `goods_number`        int(11)                        not null default '0' comment '库存',
  `goods_weight`        decimal(10, 3)         null     default '0.000',
  `market_price`        decimal(10, 2)         null     default '0.00',
  `shop_price`          decimal(10, 2)         not null default '0.00',
  `cost_price`          decimal(10, 2) default 0.00    null comment '成本价',
  `goods_desc`          text                           null collate 'utf8mb4_unicode_ci',
  `goods_img`           varchar(500)                   null     default '' collate 'utf8mb4_unicode_ci',
  `is_on_sale`          tinyint(1)             null     default '1' comment '是否在售，1在售，0下架',
  `is_delete`           tinyint(1)             null     default '0',
  `goods_type`          tinyint(2)  default 0  null comment '商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品',
  `deliver_template_id` int(5)                 null     default '0' comment '运费模板ID',
  `goods_sale_num`      int(8)                 null     default '0' comment '销售数量',
  `goods_collect_num`   int(8)                 null     default '0' comment '收藏数量',
  `add_time`            timestamp                      null     default CURRENT_TIMESTAMP,
  `update_time`         timestamp                      null     default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '最后修改时间',
  `state`               tinyint(1)                     null     default '0' comment '审核状态,0待审核 1 审核通过 2 违规下架',
  `reason`              text                           null comment '违规下架原因' collate 'utf8mb4_unicode_ci',
  `sub_account_id`      int(11)                        null     default '0' comment '子帐号ID，主要用于官方店铺',
  `sale_time`           timestamp                      null     default CURRENT_TIMESTAMP comment '上架时间',
  `limit_buy_num`       int(11)                        null     default '0' comment '最少起购数量，0不限购',
  `unit`                varchar(60)                    null     default '' comment '商品单位' collate 'utf8mb4_unicode_ci',
  `add_sale_num`        int(11)                        null     default '0' comment '虚假销量',
  `limit_max_num`       int(11) default 0              null comment '最多起购数量，0不限购',
  `sale_type`           tinyint(1)                     null     default '0' comment '上架状态,0立即上架， 1审核通过 2 加入仓库',
  `sort_id`             int(11)                        null     default '0',
  `goods_video`         varchar(191)                   null     default '' comment '视频',
  `goods_video_img`     varchar(191)                   null     default '' comment '视频首图',
  `goods_video_size`    int                            null comment '视频尺寸',
  `goods_video_id`      int                            null comment '视频ID',
  `goods_page_id`       int(11)                        not null default '0' comment '详情页装修模板ID',
  `is_page_up`          tinyint(1)                     not null default '1' comment '是否在文本区域上方',
  `is_card_exclusive`   tinyint(1)                              default 0 comment '是否会员卡专属',
  `base_sale`           int(8)                 null     default '0' comment '初始销量',
  `source`              tinyint(1)                               default '0' comment '商品来源,0：店铺自带；1、2..等：不同类型店铺第三方抓取自带商品来源',
  `is_control_price`    tinyint(1)                               default '0' comment '是否控价：0不控价，1控价（不可修改价格）',
  `can_rebate`           TINYINT(1)                   DEFAULT 0  NULL   COMMENT '是否分销改价',
  unique index `goods_id` (`goods_id`, `shop_id`),
  unique index `goods_sn` (`goods_sn`, `shop_id`),
  index `goods_id_2` (`goods_id`),
  index `shop_id` (`shop_id`),
  index `cat_id` (`cat_id`)
);


-- --  商品图片表 `b2c_goods_img`
-- drop table if exists `b2c_goods_img`;
create table `b2c_goods_img` (
  `img_id`   int(8)  not null auto_increment,
  `goods_id` int(8)  not null default '0',
  `img_url`  varchar(500)    not null default '',
  `img_desc` varchar(500)    not null default '',
  primary key (`img_id`),
  key `goods_id` (`goods_id`)
);

-- -- --  规格表 `b2c_spec`
-- drop table if exists `b2c_spec`;
create table `b2c_spec` (
  `spec_id`   int(10)  not null auto_increment,
  `spec_name` varchar(60)      not null default '',
  `del_flag`  tinyint(1)       not null default '0',
  `shop_id`   int(11)          not null default 0 comment '店铺ID',
  primary key (`spec_id`)
);

-- drop table if exists `b2c_spec_vals`;
create table `b2c_spec_vals` (
  `specvalid`   int(10)  not null auto_increment,
  `spec_id`     int(10)          not null default '0',
  `specvalname` varchar(60)      not null default '',
  `del_flag`    tinyint(1)       not null default '0',
  `shop_id`     int(11)          not null default 0 comment '店铺ID',
  primary key (`specvalid`),
  key `spec_id` (`spec_id`)
);
-- --  商品规格组合的产品表 `b2c_goods_spec_product`
-- drop table if exists `b2c_goods_spec_product`;
create table `b2c_goods_spec_product` (
  `prd_id`           int(10)                                 not null auto_increment,
  `shop_id`          int(11)                                         not null default '0',
  `goods_id`         int(10)                                 not null default '0',
  `prd_price`        decimal(10, 2)                          not null default '0.00',
  `prd_market_price` decimal(10, 2)                          not null default '0.00' comment '市场价',
  `prd_cost_price`   decimal(10, 2) default 0.00                     null comment '成本价',
  `prd_number`       int(11)                                         not null default '0' comment '当前规格组合产品库存',
  `prd_sn`           varchar(65)                                     not null default '' comment '商家编码',
  `prd_codes`        varchar(500)                                    not null default '' comment '商品条码',
  `prd_specs`        varchar(1024)                                   not null default '',
  `prd_desc`         varchar(1024)                                   not null default '' comment '规格描述，格式例子：颜色:红色 尺码:S',
  `del_flag`         tinyint(1)                                      not null default '0',
  `self_flag`        tinyint(1) default '0'                          not null comment '1:商家自己添加商品，其他没用',
  `low_shop_price`   varchar(1024)                                   not null default '0.00' comment '最低售出价格',
  `prd_img`          varchar(1024)                                   not null default '' comment '图片地址',
  `price_flag`       tinyint(1)                                       not null default '0' comment '0:商家未改价，1：商家改价，2：批量改价，3：毛利改价',
  primary key (`prd_id`),
  key `gsp_goods_id` (`goods_id`),
  key `gsp_goods_codes` (`prd_codes`),
  key `gsp_prd_sn` (`prd_sn`)
);

-- -- --  购物车 `b2c_cart`
-- drop table if exists `b2c_cart`;
create table `b2c_cart` (
  `rec_id`       BIGINT(20)          not null auto_increment,
  `shop_id`      int(11)                 null     default '0' comment '店铺ID',
  `store_id`     int(11) default 0       null comment '门店ID',
  `user_id`      int(8)          not null default '0',
  `goods_id`     int(8)          not null default '0',
  `goods_sn`     varchar(60)             not null default '',
  `product_id`   int(8)          not null default '0' comment '规格产品id',
  `prd_sn`       varchar(60)             not null default '',
  `goods_name`   varchar(120)            not null default '',
  `market_price` decimal(10, 2)  null     default '0.00',
  `goods_price`  decimal(10, 2)          not null default '0.00',
  `goods_number` smallint(5)     not null default '0',
  `goods_specs`  text comment '例如,颜色:黑色',
  `parent_id`    int(10)         null     default 0 comment '该商品的父商品id，没有该值为0 ，有的话那该商品就是该id的配件',
  `rec_type`     tinyint(1)      null     default '0' comment '购物车商品类型，0，普通 1，团够 2，拍卖 3，夺宝奇兵',
  `is_gift`      smallint        null     default '0',
  `is_buynow`    tinyint(1)                       default 0 comment '',
  `create_time`  timestamp                        default now(),
  `strategy`     varchar(191)            null     default '' comment '参与的优惠活动,json格式',
  `action`       tinyint(1)                       default '0' comment '商品活动类型：1：加价购主商品， 2： 满折满减',
  `identity_id`  int(11)                          default '0' comment '关联ID: 如：加价购ID, 满折满减ID',
  `extend_id`    int(11)                          default '0' comment '扩展字段: 如：换购挡位ID',
  `is_checked`   tinyint(1) default 0    null comment '是否选中',
  `platform`     varchar(20)             null comment '平台：如 crm',
  `extend_info`  varchar(200)            null comment '扩展内容：如crm的promote_info',
  primary key (`rec_id`)
);

/**
 微信公众号列表
 */

-- drop table if exists `b2c_wxp_list`;
create table `b2c_wxp_list` (
  `micro_id`                  int(11)  not null auto_increment,
  `shop_id`                   int(11)          not null default '0' comment '店铺ID',
  `wxp_name`                  varchar(191)     not null default '' comment '微信公众平台名称',
  `wxp_en_name`               varchar(191)     not null default '' comment '微信公众平台微信号码',
  `token`                     varchar(40)      not null default '' comment '微信公众平台的token',
  `appid`                     varchar(191)     not null default '' comment '用于高级功能的appid',
  `appsecret`                 varchar(191)     not null default '' comment '用于高级功能的appsecret',
  `partnerid`                 varchar(191)     not null default '' comment '用于微信支付的partnerid',
  `partnerkey`                varchar(191)     not null default '' comment '用于微信支付的partnerkey',
  `paysignkey`                varchar(191)     not null default '' comment '用于微信支付的paysignkey',
  `wxp_type`                  tinyint(1)       not null default '0' comment '0 订阅号, 1微信认证订阅号 2 服务号, 3微信认证服务号',
  `link_shop`                 tinyint(1)       not null default '0' comment '是否关联店铺 0,否，1是',
  `micro_id_used`             tinyint(1)       not null default '0' comment '添加新公众号会先添加一条空记录，micro_id_used为0，当设置好公众号时，变为1',
  `del_flag`                  tinyint(1)       not null default '0' comment '是否删除',
  `access_token`              varchar(513)              default '' comment 'access_token是公众号的全局唯一票据',
  `expires_timestamp`         int(11) comment 'access_token过期时间戳',
  `auto_customer_svc`         tinyint(1)                default 0 comment '是否自动转接客服，0 or 1',
  `wx_pay_ver`                tinyint(1)                default 2 comment '微信支付版本号，2为2.x，3为3.x版本',
  `wx_v3_mhcid`               varchar(20)               default '' comment '微信支付v3支付商户号',
  `wx_v3_key`                 varchar(50)               default '' comment '微信支付v3商户支付密钥',
  `wx_v3_apiclient_cert_path` varchar(191)              default '' comment '微信支付v3商户证书路径',
  `wx_v3_apiclient_key_path`  varchar(191)              default '' comment '微信支付v3商户证书密钥路径',
  `create_time`               timestamp                 default now(),
  `update_time`               timestamp        null     default null,
  primary key (`micro_id`),
  key (`appid`),
  key (`appsecret`),
  key (`micro_id_used`)
) auto_increment = 100000;

/**
    微信公众账号回复配置
**/
-- drop table if exists `b2c_wxp_response_cfg`;
create table `b2c_wxp_response_cfg` (
  `rec_id`      int(8)  not null auto_increment,
  `shop_id`     int(11)         not null default '0' comment '店铺ID',
  `type`        tinyint(1)      not null default '0' comment '0 关注时回复，1关键词回复，2默认回复,3菜单回复',
  `menu_key`    varchar(128)             default '' comment '当type=3有效，菜单key，与菜单表中的key对应',
  `key_words`   varchar(40)              default '' comment '当type=1时有效，触发关键词',
  `match_type`  tinyint(1)               default '0' comment '当type=1时有效，匹配类型，0精确，1模糊',
  `res_type`    tinyint(1)               default '0' comment '回复类型：0文本 1 单图文 2 多图文 3音乐，4 link',
  `res_content` text comment '回复内容',
  `res_rec_id`  int(8)                   default 0 comment '素材wxp_material的rec_id',
  `create_time` timestamp                default now(),
  `update_time` timestamp       null     default null,
  `micro_id`    int(8)          not null comment '所属微信公众号ID,关联b2c_wxp_list的micro_id',
  primary key (`rec_id`),
  key (`micro_id`)
);


/**
    微信公众账号菜单配置：限制2级菜单，第一级最多3个，二级菜单最多5个
**/
-- drop table if exists `b2c_wxp_menu_cfg`;
create table `b2c_wxp_menu_cfg` (
  `menu_id`        int(8)  not null auto_increment,
  `shop_id`        int(11)         not null default '0' comment '店铺ID',
  `menu_name`      varchar(100)    not null,
  `menu_key`       varchar(100)    not null,
  `menu_parent_id` int(8)          not null default 0,
  `create_time`    timestamp                default now(),
  `micro_id`       int(8)          not null comment '所属微信公众号ID,关联b2c_wxp_list的micro_id',
  primary key (`menu_id`),
  key (`micro_id`)
);


/**
    微信公众账号素材表
**/
-- drop table if exists `b2c_wxp_material`;
create table `b2c_wxp_material` (
  `rec_id`           int(8)  not null auto_increment,
  `shop_id`          int(11)         not null default '0' comment '店铺ID',
  `material_type`    tinyint(1)               default 0 comment '1单图文，2多图文，2音乐',
  `material_content` text comment '为json内容',
  `create_time`      timestamp                default now(),
  `micro_id`         int(8)          not null comment '所属微信公众号ID,关联b2c_wxp_list的micro_id',
  primary key (`rec_id`),
  key (`micro_id`)
);


/**
    微信公众账号用户表
**/
-- drop table if exists `b2c_wxp_user`;
create table `b2c_wxp_user` (
  `user_id`          int(8)  not null auto_increment,
  `shop_id`          int(11)         not null default '0' comment '店铺ID',
  `openid`           varchar(128)    not null comment '用户的标识，对当前公众号唯一',
  `subscribe`        tinyint(1)               default 0 comment '是否订阅，为0代表此用户没有关注该公众号，拉取不到其余信息。',
  `nickname`         varchar(64)              default '',
  `sex`              tinyint(1)      not null default 0 comment '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `language`         varchar(20) comment '用户的语言，简体中文为zh_CN',
  `city`             varchar(50) comment '用户所在城市',
  `province`         varchar(50) comment '用户所在省份',
  `country`          varchar(50) comment '用户所在国家',
  `headimgurl`       varchar(191) comment '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空',
  `subscribe_time`   timestamp       null     default null comment '用户关注时间，如果用户曾多次关注，则取最后关注时间',
  `unionid`          varchar(191) comment '只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段',
  `group_id`         int                      default 0 comment '分组ID,0为默认分组',
  `user_memo`        varchar(500)             default '' comment '备注',
  `last_msg_time`    timestamp                default now() comment '最后互动时间戳',
  `micro_id`         int(8)          not null comment '所属微信公众号ID,关联b2c_wxp_list的micro_id',
  `update_flag`      tinyint(1)      not null default 1 comment '批量更新标志，为1不更新为取消关注',
  `latitude`         double                   default 0 comment '地理位置纬度',
  `longitude`        double                   default 0 comment '地理位置经度',
  `precision`        double                   default 0 comment '地理位置精度',
  `last_up_loc_time` timestamp       null     default null comment '最后记录位置时间',
  primary key (`user_id`),
  key (`micro_id`),
  unique key (`openid`, `micro_id`),
  key (`group_id`)
);

/**
    微信公众账号用户分组表
**/
-- drop table if exists `b2c_wxp_user_group`;
create table `b2c_wxp_user_group` (
  `group_id`   int(8)  not null auto_increment,
  `shop_id`    int(11)         not null default '0' comment '店铺ID',
  `group_name` varchar(128)    not null comment '用分组名称',
  `micro_id`   int(8)          not null comment '所属微信公众号ID,关联b2c_wxp_list的micro_id',
  primary key (`group_id`),
  key (`micro_id`)
);


/**
    微信公众账号用户消息表
**/
-- drop table if exists `b2c_wxp_message`;
create table `b2c_wxp_message` (
  `rec_id`            int(8)  not null auto_increment,
  `shop_id`           int(11)         not null default '0' comment '店铺ID',
  `type`              tinyint(1)               default 0 comment '消息收发类型 0：接收，1自动回复 2 客服回复',
  `customer_user`     varchar(128),
  `msgtype`           varchar(50) comment 'type=0时，取值text,image,voice,video,location,link,subscribe,unsubscribe,SCAN,LOCATION,CLICK,VIEW',
  `content`           text comment '原始发送或接收的内容，type=0 1时，是xmldata，2时是json数据',
  `menu_name`         varchar(191)             default '' comment '菜单名称,type=0 && msgtype=CLICK,VIEW有效',
  `res_cfg_type`      tinyint(1)               default '0' comment '0 关注时回复，1关键词回复，2默认回复,3菜单回复',
  `res_key_words`     varchar(191)             default '' comment '关键词,关键词回复有效',
  `res_content`       text comment '回复时的回复内容',
  `res_rec_id`        int(8)                   default 0 comment '素材wxp_material的rec_id',
  `res_parent_rec_id` int(8)                   default 0 comment '对某个用户某条消息进行回复，这个是消息对的rec_id',
  `is_responsed`      tinyint(1)               default 0 comment '对type=0有意义，当有客服消息回复了，代表已回复',
  `star_flag`         tinyint(1)               default 0 comment '是否星标收藏',
  `micro_id`          int(8)          not null comment '所属微信公众号ID,关联b2c_wxp_list的micro_id',
  `create_time`       timestamp                default now(),
  primary key (`rec_id`),
  key (`type`),
  key (`micro_id`),
  key (`star_flag`)
);


/**
    微信公众账号群发
**/
-- drop table if exists `b2c_wxp_mass_send_msg`;
create table `b2c_wxp_mass_send_msg` (
  `rec_id`      int(8)  not null auto_increment,
  `shop_id`     int(11)         not null default '0' comment '店铺ID',
  `group_id`    int                      default -1 comment '群发组，-1全部 0未分组，>0其他分组',
  `sex`         tinyint(1)               default 0 comment '性别：0全部 1 男，2女',
  `touser`      text comment '群发对象，用,号隔开的openid',
  `content`     text comment '群发数据',
  `is_ok`       tinyint(1)               default 0 comment '是否发送成功',
  `micro_id`    int(8)          not null comment '所属微信公众号ID,关联b2c_wxp_list的micro_id',
  `create_time` timestamp                default now(),
  primary key (`rec_id`),
  key (`micro_id`)
);


/**
    微信公众账号用户订阅统计
**/
-- drop table if exists `b2c_wxp_sub_stat`;
create table `b2c_wxp_sub_stat` (
  `rec_id`      int(8)  not null auto_increment,
  `shop_id`     int(11)         not null default '0' comment '店铺ID',
  `rq`          date,
  `sub_num`     int,
  `unsub_num`   int,
  `sum_sub_num` int,
  `micro_id`    int(8)          not null comment '所属微信公众号ID,关联b2c_wxp_list的micro_id',
  primary key (`rec_id`),
  key (`micro_id`)
);


/**
    微信公众账号用户消息统计
**/
-- drop table if exists `b2c_wxp_msg_stat`;
create table `b2c_wxp_msg_stat` (
  `rec_id`   int(8)  not null auto_increment,
  `shop_id`  int(11)         not null default '0' comment '店铺ID',
  `rq`       date,
  `user_num` int,
  `msg_num`  int,
  `micro_id` int(8)          not null comment '所属微信公众号ID,关联b2c_wxp_list的micro_id',
  primary key (`rec_id`),
  key (`micro_id`)
);


/**
    微信公众账号用户关键词统计
**/
-- drop table if exists `b2c_wxp_keywords_stat`;
create table `b2c_wxp_keywords_stat` (
  `rec_id`    int(8)  not null auto_increment,
  `shop_id`   int(11)         not null default '0' comment '店铺ID',
  `rq`        date,
  `key_words` varchar(191),
  `user_num`  int,
  `msg_num`   int,
  `micro_id`  int(8)          not null comment '所属微信公众号ID,关联b2c_wxp_list的micro_id',
  primary key (`rec_id`),
  key (`micro_id`)
);


/**
    微信公众账号用户自定义菜单统计
**/
-- drop table if exists `b2c_wxp_menu_stat`;
create table `b2c_wxp_menu_stat` (
  `rec_id`    int(8)  not null auto_increment,
  `shop_id`   int(11)         not null default '0' comment '店铺ID',
  `rq`        date,
  `menu_name` varchar(191),
  `user_num`  int,
  `msg_num`   int,
  `micro_id`  int(8)          not null comment '所属微信公众号ID,关联b2c_wxp_list的micro_id',
  primary key (`rec_id`),
  key (`micro_id`)
);


/**
  小程序用户表,wx.getUserInfo获取
**/
-- drop table if exists `b2c_mp_user`;
create table `b2c_mp_user` (
  `mp_user_id`       int(8)  not null auto_increment,
  `app_id`           varchar(128)    not null comment '小程序appId',
  `openid`           varchar(128)    not null comment '用户的标识，对当前公众号唯一',
  `nickname`         varchar(64)              default '',
  `gender`           tinyint(1)      not null default 0 comment '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `language`         varchar(20) comment '用户的语言，简体中文为zh_CN',
  `city`             varchar(50) comment '用户所在城市',
  `province`         varchar(50) comment '用户所在省份',
  `country`          varchar(50) comment '用户所在国家',
  `avatarUrl`        varchar(191) comment '用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。',
  `unionid`          varchar(191) comment '只有在用户绑定到微信开放平台帐号后，才会出现该字段',
  `user_memo`        varchar(500)             default '' comment '备注',
  `latitude`         double                   default 0 comment '地理位置纬度',
  `longitude`        double                   default 0 comment '地理位置经度',
  `precision`        double                   default 0 comment '地理位置精度',
  `last_up_loc_time` timestamp       null     default null comment '最后记录位置时间',
  `create_time`      timestamp       not null default now() comment '创建时间',
  `user_id`          int             not null default 0 comment '关联用户ID',
  primary key (`mp_user_id`),
  unique key (`app_id`, `openid`),
  key (`user_id`)
);


/**
 * b2c_uploaded_image 上传图片表
 *
 **/
-- drop table if exists `b2c_uploaded_image`;
create table `b2c_uploaded_image` (
  `img_id`         int(10)  not null auto_increment,
  `img_type`       varchar(60)      not null default '',
  `img_size`       int(10)  not null default '0',
  `img_name`       varchar(500)     not null default '' comment '图片名称，用于前端显示',
  `img_orig_fname` varchar(500)     not null default '',
  `img_path`       varchar(500)     not null default '',
  `img_url`        varchar(500)     not null default '',
  `img_cat_id`     int(10)                   default 0 comment '图片分类',
  `img_width`      int(10)          not null default 0,
  `img_height`     int(10)          not null default 0,
  `is_refer`       tinyint                   default 0 comment '是否引用',
  `upload_time`    timestamp                 default now(),
  `shop_id`        int(11)          not null default 0 comment '店铺ID',
  `del_flag`       tinyint(1)       not null default 0,
  primary key (`img_id`),
  key (`shop_id`),
  key (`img_orig_fname`)
);

-- --  上传图片分类
-- drop table if exists `b2c_uploaded_image_category`;
create table `b2c_uploaded_image_category` (
  `img_cat_id`        int(10)  not null auto_increment,
  `shop_id`           int(11)          not null default 0 comment '店铺ID',
  `img_cat_name`      varchar(60)      not null default '',
  `img_cat_parent_id` int(10)          not null default 0,
  `create_time`       timestamp                 default now(),
  `cat_ids`           varchar(191)     not null default '0' comment '层级ID串,逗号分隔',
  `level`             tinyint                   default 0 comment '层级，0开始',
  `sort`              int(11)                   default 1 comment '排序优先级',
  primary key (`img_cat_id`),
  key (`shop_id`)
);

-- --  小视频
-- drop table if exists `b2c_uploaded_video`;
create table `b2c_uploaded_video` (
  `video_id`         int(10)  not null auto_increment,
  `video_type`       varchar(60)      not null default '',
  `video_size`       int(10)  not null default '0',
  `video_name`       varchar(191)     not null default '' comment '视频名称，用于前端显示',
  `video_orig_fname` varchar(191)     not null default '',
  `video_path`       varchar(191)     not null default '',
  `video_snap_path`  varchar(191)     not null default '' comment '视频截图',
  `video_url`        varchar(191)     not null default '',
  `video_cat_id`     int(10)          not null default 0 comment '视频分类',
  `video_width`      int(10)          not null default 0,
  `video_height`     int(10)          not null default 0,
  `video_meta`       varchar(191)     not null default '' comment '视频信息,json',
  `is_refer`         tinyint                   default 0 comment '是否引用',
  `upload_time`      timestamp                 default now(),
  `shop_id`          int(11)          not null default 0 comment '店铺ID',
  `del_flag`         tinyint(1)       not null default 0,
  primary key (`video_id`),
  key (`shop_id`),
  key (`video_orig_fname`)
);

-- --  小视频分类
-- drop table if exists `b2c_uploaded_video_category`;
create table `b2c_uploaded_video_category` (
  `video_cat_id`        int(10)  not null auto_increment,
  `shop_id`             int(11)          not null default 0 comment '店铺ID',
  `video_cat_name`      varchar(60)      not null default '',
  `video_cat_parent_id` int(10)          not null default 0,
  `create_time`         timestamp                 default now(),
  `cat_ids`             varchar(191)     not null default '0' comment '层级ID串,逗号分隔',
  `level`               tinyint                   default 0 comment '层级，0开始',
  `sort`                int(11)                   default 1 comment '排序优先级',
  primary key (`video_cat_id`),
  key (`shop_id`)
);

-- --  微信小程序自定义页面表
-- drop table if exists `b2c_xcx_customer_page`;
create table `b2c_xcx_customer_page` (
  `page_id`              int(10)  not null auto_increment,
  `shop_id`              int(11)          not null default 0 comment '店铺ID',
  `page_name`            varchar(60)      not null default '',
  `page_type`            tinyint(1)       not null default 0 comment '是否为首页1为首页，0非首页',
  `page_enabled`         tinyint(1)       not null default 1 comment '是否可用',
  `page_tpl_type`        tinyint(1)       not null default 0 comment '模板类型:0自定义模板，1默认模板，2美女模板，3自定义首页',
  `page_content`         longtext comment '页面内容，json格式存储',
  `page_publish_content` longtext comment '正式页面内容，json格式存储',
  `page_state`           tinyint(1)                default '0' comment '状态：0未发布，1已发布',
  `create_time`          timestamp                 default now(),
  `cat_id`               int(10)          null     default 0 comment '页面分类id',
  primary key (`page_id`)
);
/*
 */

-- --  自定义页面模板
-- drop table if exists `b2c_customer_page_template`;
create table `b2c_customer_page_template` (
  `page_id`       int(10)  not null auto_increment,
  `shop_id`       int(11)          not null default 0 comment '店铺ID',
  `page_name`     varchar(60)      not null default '',
  `page_type`     tinyint(1)       not null default 0 comment '是否为首页1为首页，0非首页',
  `page_enabled`  tinyint(1)       not null default 1 comment '是否可用',
  `page_tpl_type` tinyint(1)       not null default 0 comment '模板类型:0自定义模板，1默认模板，2美女模板，3自定义首页',
  `page_content`  longtext comment '页面内容，json格式存储',
  `create_time`   timestamp                 default now(),
  primary key (`page_id`)
);

-- -- 微信二维码
-- drop table if exists `b2c_wxp_qr_code`;
create table `b2c_wxp_qr_code` (
  `scene_id`    int(11)       not null auto_increment comment '二维码追踪ID',
  `shop_id`     int(11)       not null default 0 comment '店铺ID',
  `qr_type`     tinyint(1)    not null default 0 comment '二维码类型 0：临时二维码，1永久二维码',
  `expire_time` int(11)       not null default 0 comment '过期时间，永久二维码无效',
  `ticket`      varchar(191)  not null default '' comment '获取的二维码ticket',
  `qr_url`      varchar(191)  not null default '' comment '二维码图片URL',
  `qr_path`     varchar(191)  not null default '' comment '二维码图片本地路径',
  `res_name`    varchar(100)  not null default '' comment '二维码标识名称',
  `res_type`    tinyint(1)    not null default 0 comment '回复类型：0文本 1 单图文 2 多图文 3音乐，4 link',
  `res_content` varchar(1024) not null default 0 comment '回复内容',
  `create_time` timestamp     not null default now() comment '记录时间',
  `micro_id`    int(11)                default 0,
  primary key (`scene_id`),
  key (`expire_time`)
);

-- -- 订单表
-- drop table if exists `b2c_order_info`;
create table `b2c_order_info` (
  `order_id`             mediumint(8)          not null  auto_increment comment '订单ID',
  `shop_id`              int(11)                       not null  default 0 comment '店铺ID',
  `order_sn`             varchar(20)                   not null  default '' comment '订单编号',
  `main_order_sn`        varchar(20)                   not null  default '' comment '主订单编号(拆单时用)',
  `user_id`              mediumint(8)          not null  default '0' comment '用户ID',
  `user_openid`          varchar(191)                  null      default '' comment '用户openid',
  `order_status`         tinyint(1)            not null  default '0' comment '订单状态',
  `order_status_name`    varchar(32)                   not null  default '' comment '订单状态名称',
  `consignee`            varchar(60)                   not null  default '' comment '收件人姓名',
  `address_id`           int(11)                       null      default 0 comment '地址ID',
  `country_code`         mediumint(10)         null      default '0' comment '国家编号',
  `country_name`         varchar(50)                   null      default '' comment '国家名称',
  `province_code`        mediumint(10)         null      default '0' comment '省份编号',
  `province_name`        varchar(50)                   null      default '' comment '省份名称',
  `city_code`            mediumint(10)         null      default '0' comment '城市编号',
  `city_name`            varchar(120)                  null      default '' comment '城市名称',
  `district_code`        mediumint(10)         null      default '0' comment '区县编号',
  `district_name`        varchar(120)                  null      default '' comment '区县名称',
  `address`              varchar(191)                  null      default '' comment '更多详细地址',
  `complete_address`     varchar(512)                  null      default '' comment '完整收件地址',
  `zipcode`              varchar(60)                   null      default '' comment '邮编',
  `mobile`               varchar(60)                   not null  default '' comment '手机号',
  `add_message`          varchar(191)                  null      default '' comment '客户留言',
  `shipping_id`          tinyint(3)                    null      default '0' comment '快递ID',
  `shipping_name`        varchar(120)                  null      default '' comment '快递名称',
  `pay_code`             varchar(30)                   not null  default '' comment '支付代号',
  `pay_name`             varchar(120)                  not null  default '' comment '支付名称',
  `pay_sn`               varchar(32)                   null      default '' comment '支付流水号',
  `goods_amount`         smallint(6)                   not null  default '0' comment '订单商品数量',
  `shipping_fee`         decimal(10, 2)                not null  default '0.00' comment '快递费金额',
  `money_paid`           decimal(10, 2)                not null  default '0.00' comment '订单应付金额',
  `shoper_reduce_amount` decimal(10, 2)                not null  default '0.00' comment '商家减价金额',
  `sub_order_amount`     decimal(10, 2)                not null  default '0.00' comment '子订单总金额',
  `discount`             decimal(10, 2)                not null  default '0.00' comment '券折扣金额',
  `score_discount`       decimal(10, 2)                not null  default '0.00' comment '积分抵扣金额',
  `use_account`          decimal(10, 2)                not null  default '0.00' comment '用户消费余额',
  `order_amount`         decimal(10, 2)                not null  default '0.00' comment '订单总金额',
  `grade_percent`        decimal(10, 2)                not null  default '0.00' comment '购买会员等级的折扣%',
  `discount_price`       decimal(10, 2)                not null  default '0.00' comment '购买会员等级的折扣金额',
  `dapei_reduce_amount`  decimal(10, 2)                not null  default '0.00' comment '搭配减价',
  `package_discount`     decimal(10, 2) default 0.00   null comment '一口价抵扣金额',
  `dapei_id`             int(8)                        not null  default '0' comment '搭配ID来源',
  `add_time`             timestamp                     null      default null comment '订单提交时间',
  `confirm_time`         timestamp                     null      default null comment '订单确收收货时间',
  `pay_time`             timestamp                     null      default null comment '支付时间',
  `shipping_time`        timestamp                     null      default null comment '发货时间',
  `closed_time`          timestamp                     null      default null comment '关闭时间',
  `cancelled_time`       timestamp                     null      default null comment '取消时间',
  `finished_time`        timestamp                     null      default null comment '订单完成时间',
  `return_time`          timestamp                     null      default null comment '订单申请退货时间',
  `return_finish_time`   timestamp                     null      default null comment '订单退货完成时间',
  `refund_time`          timestamp                     null      default null comment '订单申请退款时间',
  `refund_finish_time`   timestamp                     null      default null comment '订单退款完成时间',
  `update_time`          timestamp                     not null  default CURRENT_TIMESTAMP on update current_timestamp comment '订单更新时间',
  `shipping_no`          varchar(191)                  not null  default '' comment '快递单号',
  `shipping_type`        varchar(60)                   not null  default '' comment '快递类型',
  `is_cod`               tinyint(1)            not null  default '0' comment '是否货到付款',
  `return_type_cfg`      tinyint(1)            not null  default '1' comment '是否支持退换货：1支持 2不支持',
  `return_days_cfg`      tinyint(1)            not null  default '7' comment '发货后自动确认收货时间,单位天',
  `order_timeout_days`   smallint(3)           not null  default '3' comment '确认收货后自动订单完成时间,单位天',
  `seller_remark`        varchar(512)                  not null  default '' comment '卖家备注',
  `erpordercode`         varchar(32)                   not null  default '' comment 'ERP中订单代码',
  `comment_flag`         tinyint(1)                    not null  default '0' comment '0:未评论，1:已评论，2：已晒单',
  `fanli_user_id`        int(11)                       not null  default '0' comment '返利会员id',
  `fanli_grade`          varchar(64)                   not null  default '' comment '返利等级',
  `fanli_percent`        decimal(10, 2)                not null  default '0.00' comment '返利百分比',
  `settlement_flag`      tinyint(1)                    not null  default '0' comment '结算标志：0：未结算，1：已结算',
  `invoice_id`           int(11)                       not null  default '0' comment '发票Id',
  `invoice_content`      int(11)                       null      default '0' comment '发票类型：0普通发票；1增值税专票',
  `invoice_title`        text                          null comment '发票内容：json存储',
  `refund_status`        tinyint(1)                    null      default '0' comment '1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货',
  `pay_order_sn`         varchar(30)                             default "" comment '对账单号',
  `goods_type`           varchar(50) default '0'       null comment '商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品 6限时降价 7加价购',
  `order_source`         tinyint(2) comment '订单来源，0pc，1wap，2app',
  `fanli_type`           tinyint(2) comment '返利类型，0普通订单，1三级分销返利订单，2返利会员返利订单',
  `manual_refund`        tinyint(1)                    not null  default 0 comment '1代表手动退款，0代表非手动',
  `order_pay_way`        tinyint(2)                              default 0 comment '订单付款方式，0全款 1定金 2补款',
  `bk_order_sn`          varchar(32)                             default '' comment '补款订单号 order_pay_way=1时有效',
  `bk_order_money`       decimal(10, 2)                not null  default '0.00' comment '补款金额 order_pay_way=1时有效',
  `bk_order_paid`        tinyint(1)                    not null  default 0 comment '补款金额是否支付 order_pay_way=1时有效，0未支付，1已支付',
  `pin_goods_money`      decimal(10, 2)                not null  default '0.00' comment '当前拼团商品金额，阶梯团根据人数时会变化，补款也随之变化',
  `pin_yj_money`         decimal(10, 2)                not null  default '0.00' comment '拼团支付佣金金额',
  `pin_group_id`         int(11)                       not null  default 0 comment '拼团ID',
  `del_flag`             tinyint(1)                    not null  default '0' comment '0:未删除，1:已删除',
  `source`               varchar(30)                             default "" comment '订单来源，记录app，wap，pc来源',
  `part_ship_flag`       tinyint(1)                    not null  default '0' comment '0:，1:部分发货',
  `promotion_id`         int(11)                       not null  default 0 comment '促销活动Id',
  `promotion_reduce`     decimal(10, 2)                not null  default '0' comment '促销活动折扣金额,满折满减',
  `push_type`            tinyint(1)                    not null  default '0' comment 'yadu推送状态：0-暂无推送，1-推送失败，2-推送成功',
  `push_desc`            varchar(100)                  not null  default '' comment 'yadu推送失败原因',
  `pos_flag`             tinyint(1)                    not null  default '0' comment '门店订单标志：0：商城，1：门店同步订单',
  `pos_shop_name`        varchar(191)                  not null  default '' comment 'pos店铺名称',
  `store_id`             int                                     default 0 comment '门店ID',
  `store_name`           varchar(191)                  null      default '' comment '门店名称',
  `member_card_id`       int                                     default 0 comment '会员卡ID',
  `member_card_reduce`   decimal(10, 2)                          default 0 comment '会员卡优惠金额',
  `member_card_balance`  decimal(10, 2)                          default 0 comment '会员卡消费金额',
  `expire_time`          timestamp                     null      default null comment '订单支付过期时间',
  `del_time`             timestamp                     null      default null comment '订单删除时间',
  `prepay_id`            varchar(191)                  null      default null comment '微信支付Id，用于发送模板消息',
  `deliver_type`         tinyint(1)                              default 0 comment '配送类型：0 快递 1 自提',
  `deliver_type_name`    varchar(191)                  null      default null comment '配送类型名称',
  `pickup_time`          varchar(30)                   null comment '自提时间',
  `star_flag`            tinyint(1)                              default 0 comment '标星订单：0 未标星 1 标星',
  `verify_code`          varchar(191)                            default '' comment '核销自提码',
  `split`                int                                     default 0 comment '分裂优惠券id',
  `card_no`              varchar(32) default ''        not null comment '会员卡号',
  `fanli_money`          decimal(10, 2) default '0.00' null comment '单品返利金额',
  `true_name`            varchar(32) default ''        not null comment '真实姓名',
  `id_card`              varchar(32) default ''        not null comment '身份证号',
  `ali_trade_no`         varchar(60)                   null      default '' comment '支付宝交易单号',
  `grouper_cheap_reduce` decimal(10, 2)                          default 0 comment '团长优惠金额',
  `bk_shipping_time`     timestamp                     null comment '定金预计发货时间',
  `bk_return_type`       tinyint(2)                    null comment '定金退款状态',
  `bk_prepay_id`         varchar(191)                  null comment '微信支付Id，用于发送模板消息',
  `pre_sale_discount`    decimal(10, 2) default 0.00   null comment '定金膨胀优惠金额',
  `instead_pay_money`    decimal(10, 2) default 0.00   null comment '代付金额',
  `order_user_message`   varchar(50)                   null comment '发起人留言',
  `instead_pay`          text                          null comment '好友代付规则',
  `instead_pay_num`      smallint default 0            null comment '代付人数',
  `is_promote`           TINYINT(1) DEFAULT 0          NULL COMMENT '是否是推广单',
  `verifier_id`          INT(8) DEFAULT 0              NULL COMMENT '核销员ID',
  `exchang`              TINYINT(2) DEFAULT 0  NULL   COMMENT '1 兑换 0否',
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
  `action_id`    mediumint(8)  not null      auto_increment,
  `shop_id`      int(11)               not null      default 0 comment '店铺ID',
  `order_id`     mediumint(8)  not null      default '0',
  `order_sn`     varchar(20)           not null      default '',
  `action_user`  varchar(128)          not null      default '',
  `user_id`      mediumint(8)  not null      default '0',
  `user_cid`     varchar(40)           not null      default '',
  `user_openid`  varchar(128)          not null      default '',
  `order_status` tinyint(1)    not null      default '0',
  `action_note`  varchar(191)          not null      default '',
  `log_time`     timestamp             not null      default now(),
  primary key (`action_id`),
  key (`order_id`, `order_sn`)
);

-- --   订单商品表  b2c_order_goods
-- drop table if exists `b2c_order_goods`;
create table `b2c_order_goods` (
  `rec_id`                 mediumint(8)          not null  auto_increment,
  `shop_id`                int(11)                       not null  default 0 comment '店铺ID',
  `order_id`               mediumint(8)          not null  default '0',
  `order_sn`               varchar(20)                   not null  default '',
  `goods_id`               mediumint(8)          not null  default '0',
  `goods_name`             varchar(120)                  not null  default '',
  `goods_sn`               varchar(60)                   not null  default '',
  `product_id`             mediumint(8)          not null  default '0',
  `product_sn`             varchar(65)                   not null  default '',
  `goods_number`           smallint(5)           not null  default '1',
  `market_price`           decimal(10, 2)                not null  default '0.00',
  `goods_price`            decimal(10, 2)                not null  default '0.00',
  `goods_attr`             text                          not null,
  `send_number`            smallint(5)           not null  default '0',
  `return_number`          smallint(5)           not null  default '0' comment '退货/退款成功数量',
  `is_real`                tinyint(1)            not null  default '0',
  `goods_attr_id`          varchar(191)                  not null  default '',
  `goods_img`              varchar(191)                  not null  default '',
  `refund_status`          tinyint(1)                    not null  default '0' comment '1是审核中，2是通过审核，3退货没通过审核，4买家再次提交申请，5：退款退货成功，6是拒绝退款退货',
  `comment_flag`           tinyint(1)                    not null  default '0' comment '0:未评论，1:已评论，2：已晒单',
  `stra_id`                int(11)                       not null  default '0' comment '商品参与的促销活动id',
  `per_discount`           decimal(10, 2)                not null  default '0' comment '促销折扣均摊到每件商品的折扣',
  `is_gift`                int(1)                        not null  default '0' comment '是否是赠品',
  `r_goods`                varchar(191)                  not null  default '' comment '赠品的关联商品',
  `goods_score`            int(11)                       not null  default '0' comment '商品积分',
  `goods_growth`           int(11)                       not null  default '0' comment '商品成长值',
  `discounted_goods_price` decimal(10, 2)                not null  default '0' comment '折后单价',
  `discount_detail`        text comment '打折详情，json存储',
  `fanli_type`             tinyint(1) default '0'        not null comment '0:不返利商品，1：返利商品',
  `can_calculate_money`    decimal(10, 2) default '0.00' null comment '单品可计算返利金额，刨除优惠券和其他折扣后的金额',
  `fanli_money`            decimal(10, 2) default '0.00' null comment '单品返利金额',
  `discounted_total_price` decimal(10, 2) default 0.00   not null  default '0' comment '折后总价',
  `total_fanli_money`      decimal(10, 2) default 0.00   null comment '商品返利总金额',
  `purchase_price_id`      int default 0                 null comment '加价购ID',
  `purchase_price_rule_id` int default 0                 null comment '换购挡位ID',
  `reduce_price_id`        int default 0                 null comment '限时降价ID',
  `fanli_strategy`         varchar(666)                  null comment '返利比例',
  `fanli_percent`          decimal(10, 2) default 0.00   null comment '返利比例',
  `cost_price`             decimal(10, 2) default 0.00   null comment '成本价',
  `is_card_exclusive`      tinyint(1) default 0          null comment '是否会员卡专属',
  `promote_info` VARCHAR(500) NULL   COMMENT '推广信息',
  `gift_id` INT DEFAULT 0  NULL  COMMENT '赠品ID',
  primary key (`rec_id`),
  key `order_id` (`order_id`),
  key `order_sn` (`order_sn`),
  key `goods_id` (`goods_id`),
  key (`shop_id`)
);


-- -- 发放优惠券
-- drop table if exists `b2c_customer_avail_coupons`;
create table `b2c_customer_avail_coupons` (
  `id`                 mediumint(8)  not null auto_increment,
  `coupon_sn`          varchar(30)           not null default '',
  `user_id`            mediumint(8)  not null default '0',
  `act_type`           mediumint(5)  null     default '0' comment 'user_id不为空时1:经销商等级打折,为空时1:首次下单优惠，2减价，3打折',
  `act_id`             mediumint(8)  not null default '0',
  `start_time`         timestamp             null     default null,
  `end_time`           timestamp             null     default null,
  `type`               tinyint(2)    null     default '0' comment '1为减价，2为打折',
  `amount`             decimal(10, 2)        null     default '0.00' comment '打折或减价量',
  `act_desc`           varchar(128)          null     default '',
  `limit_order_amount` mediumint(8)  null     default '0',
  `created`            timestamp             not null default now(),
  `is_used`            tinyint(1)             default '0',
  `used_time`          timestamp             null     default null,
  `access_mode`        tinyint(1)            not null default '0' comment '获取方式，0：发放，1：领取',
  `access_id`          mediumint(8)                   default '0' comment '发放活动id',
  `notify_time`        timestamp             null     default null comment '通知时间',
  `order_sn`           varchar(20)           not null default '' comment '优惠订单编号',
  `del_flag`           tinyint(1)            not null default '0' comment '是否删除,1：删除',
  `get_source`         tinyint(2) default 0  null comment '//1表单送券2支付送券3活动送券4积分兑换5直接领取6分裂优惠券7crm领券8幸运大抽奖9定向发券',
  primary key (`id`),
  unique key (`coupon_sn`),
  key `user_id` (`user_id`)
);


-- -- 优惠记录
-- drop table if exists `b2c_mrking_activity_record`;
create table `b2c_mrking_activity_record` (
  `id`                 int(12)       not null auto_increment,
  `shop_id`            int(11)               not null default 0 comment '店铺ID',
  `coupon_id`          mediumint(8)  not null default '0',
  `act_type`           mediumint(5)  not null default '0' comment 'user_id不为空时1:经销营等级打折,为空时1:首次下单优惠，2减价，3打折',
  `act_id`             mediumint(8)  not null default '0',
  `user_id`            mediumint(8)  not null default '0',
  `user_cid`           varchar(40)           not null default '',
  `user_openid`        varchar(128)          not null default '',
  `order_sn`           varchar(20)           not null default '',
  `type`               tinyint(2)    not null default '0' comment '1为减价，2为打折',
  `amount`             decimal(10, 2)        not null default '0.00' comment '打折或减价量',
  `act_desc`           varchar(128)          not null default '',
  `limit_order_amount` mediumint(8)  not null default '0',
  `created`            timestamp             not null default now(),
  primary key (`id`),
  key `act_type` (`act_type`),
  key `user_id` (`user_id`),
  key `user_cid` (`user_cid`),
  key `user_openid` (`user_openid`),
  key `order_sn` (`order_sn`),
  key (`shop_id`)
);
-- -- 优惠券列表
-- drop table if exists `b2c_mrking_voucher`;
create table `b2c_mrking_voucher` (
  `id`                   mediumint(8)  not null auto_increment,
  `shop_id`              int(11)               null     default 0 comment '店铺ID',
  `act_code`             varchar(50)           null     default 'voucher',
  `act_name`             varchar(120)          not null default '',
  `start_time`           timestamp             null     default null,
  `end_time`             timestamp             null     default null,
  `denomination`         decimal(10, 2)        not null default '0',
  `total_amount`         int(11)               not null default '0',
  `surplus`              int(11)               not null default '0',
  `remain_amount`        int(11)               null     default '0',
  `use_consume_restrict` tinyint(1)            null     default '0',
  `least_consume`        mediumint(5)  null     default '0',
  `use_explain`          varchar(256)          null     default '',
  `enabled`              tinyint(1)    null     default '1',
  `created`              timestamp             null     default null,
  `is_random`            tinyint(1)            null     default '0',
  `most_denomination`    mediumint(5)  null     default '0',
  `receive_per_person`   smallint(3)           null     default '0',
  `suit_goods`           tinyint(1)            null     default '0' comment '0:全店通用,1:指定店铺',
  `together_used`        tinyint(1)            null     default '0' comment '是否与其他优惠券同时使用',
  `permit_share`         tinyint(1)            null     default '0' comment '是否允许分享优惠券链接',
  `remind_owner`         tinyint(1)            null     default '0' comment '是否到期前提醒用户',
  `giveout_amount`       smallint(4)           null     default '0' comment '发放优惠券数量',
  `giveout_person`       smallint(4)           null     default '0' comment '发放优惠券人数',
  `receive_amount`       smallint(4)           null     default '0' comment '领取优惠券数量',
  `receive_person`       smallint(4)           null     default '0' comment '领取优惠券人数',
  `used_amount`          smallint(4)           null     default '0' comment '已使用优惠券数量',
  `alias_code`           varchar(16)           null     default '' comment '唯一活动代码',
  `validation_code`      varchar(10)           null     default '' comment '领取码',
  `recommend_goods_id`   text                  null comment '指定商品可用',
  `recommend_cat_id`     text                  null comment '指定品牌可用',
  `validity`             MEDIUMINT(9) DEFAULT 0  NULL   COMMENT '优惠券有效天数',
  `activity_names`       varchar(50)                    default '' comment '分裂优惠卷活动名称',
  `act_start_time`       timestamp             null     default null comment '分裂优惠卷活动起始时间',
  `act_end_time`         timestamp             null     default null comment '分裂优惠卷活动结束时间',
  `cou_limit`            int                            default 0 comment '分裂优惠卷数量',
  `least_money`          int                            default 0 comment '分裂优惠卷触发条件',
  `type`                 tinyint(1)                     default 0 comment '优惠卷类型，1为分裂 0为正常 2抽奖 3送券 4跳转自定义链接',
  `is_delete`            tinyint(1)            null     default '0' comment '1为删除状态',
  `recommend_sort_id`    text                  null comment '指定商家分类可用',
  `action`               tinyint(1) default 1  null comment '1:系統创建 2：来自crm',
  `identity_id`          varchar(50)           null comment '关联外部优惠券规则唯一码',
  `recommend_product_id` text                  null comment '关联商品规格',
  `coupon_ids`           varchar(50)           null comment '支付送券的优惠券ID',
  `use_score`            tinyint(2) default 0  null comment '是否可以积分兑换',
  `score_number`         int(6) default 0      null comment '需要积分数',
  `lottery`              int(11) comment '抽奖活动ID',
  `card_id`              text COLLATE utf8mb4_unicode_ci COMMENT '专属会员卡',
  `pay_reward_img_path`  varchar(191)  not null default '' comment '支付有礼跳转活动图片路径',
  `pay_reward_url`       varchar(191)  not null default '' comment '支付有礼跳转活动链接',
  `recommend_type` tinyint(1) not null  DEFAULT '1' COMMENT '支付有礼跳转链接 1:全部商品可用 2：指定商品可用',
  primary key (`id`),
  unique key `alias_code` (`alias_code`),
  key `act_name` (`act_name`),
  key (`shop_id`)
);


-- -- 快递配置表 `b2c_shipping`

-- drop table if exists `b2c_shipping`;
create table `b2c_shipping` (
  `shipping_id`    tinyint(3)  not null auto_increment,
  `shipping_code`  varchar(20)         not null default '',
  `shipping_name`  varchar(120)        not null default '',
  `shipping_desc`  varchar(191)        not null default '',
  `insure`         varchar(10)         not null default '0',
  `support_cod`    tinyint(1)  not null default '0',
  `enabled`        tinyint(1)  not null default '0',
  `shipping_print` text                not null,
  `print_model`    tinyint(1)                   default '0',
  `shipping_order` tinyint(3)  not null default '0',
  primary key (`shipping_id`),
  key `shipping_code` (`shipping_code`, `enabled`)
);

-- --   配送类型表 `b2c_delivery_type`
-- drop table if exists `b2c_delivery_type`;
create table `b2c_delivery_type` (
  `id`            tinyint(3)  not null auto_increment,
  `shipping_type` varchar(30)         not null default '',
  `delivery_name` varchar(120)        not null default '',
  `enabled`       tinyint(1)  not null default '0',
  primary key (`id`),
  unique key (`shipping_type`)
);
-- -- 用户余额
-- drop table if exists `b2c_user_account`;
create table `b2c_user_account` (
  `id`         mediumint(8)   not null auto_increment,
  `user_id`    mediumint(8)   not null default '0',
  `admin_user` varchar(191)           not null default '0' comment '操作员',
  `order_sn`   varchar(20)            not null default '' comment '分销订单结算产生返利',
  `amount`     decimal(10, 2)         not null comment '金额',
  `add_time`   timestamp              null     default null comment '创建时间',
  `admin_note` varchar(191)           not null comment '操作员备注',
  `payment`    varchar(90)            not null comment '支付方式',
  `is_paid`    tinyint(1)             not null default '0' comment '支付类型，0：充值，1：消费',
  `remark`     varchar(1024) comment '备注',
  `source`     tinyint(1) default '0' null comment '1:分销来源，0:充值',
  primary key (`id`),
  key `user_id` (`user_id`)
);

-- -- 支付配置表
-- drop table if exists `b2c_payment`;
create table `b2c_payment` (
  `id`            tinyint(3)  not null auto_increment,
  `shop_id`       int(11)             not null default 0 comment '店铺ID',
  `pay_name`      varchar(80)         not null default '',
  `pay_code`      varchar(20)         not null default '',
  `pay_fee`       varchar(10)         not null default '0',
  `pay_desc`      text                not null,
  `enabled`       tinyint(1)  not null default '0',
  `is_cod`        tinyint(1)  not null default '0',
  `is_online_pay` tinyint(1)  not null default '0',
  primary key (`id`),
  unique key `pay_code` (`pay_code`)
) auto_increment = 4;

-- --  支付记录表
-- drop table if exists `b2c_payment_record`;
create table `b2c_payment_record` (
  `id`                  mediumint(20)  not null auto_increment,
  `shop_id`             int(11)                not null default 0 comment '店铺ID',
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
  `gmt_create`          timestamp              null     default null comment '支付交易创建时间',
  `notify_time`         timestamp              null     default null comment '通知时间',
  `gmt_pay_time`        timestamp              null     default null comment '交易付款时间',
  `gmt_close_time`      timestamp              null     default null comment '交易关闭时间',
  `created`             timestamp              null     default null comment '插入时间',
  `remark1`             text comment '自定义备注  建议用于存储原始数据',
  `remark2`             text comment '自定义备注',
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
  `shop_id`                  int(11)                not null     default 0 comment '店铺ID',
  `pay_sn`                   varchar(32)            not null     default '' comment '支付流水号',
  `pay_code`                 varchar(20)            not null     default '' comment '支付宝:alipay,微信：？，...',
  `pay_code_alias`           varchar(20)            not null     default '' comment '支付宝:alipay,微信：？，...',
  `pay_act_name`             varchar(120)           not null     default '' comment '支付说明',
  `pay_act_time`             timestamp              null         default null comment '交易付款时间',
  `income_amount`            decimal(15, 2)         not null     default '0.00' comment '收入金额',
  `outcome_amount`           decimal(15, 2)         not null     default '0.00' comment '支出金额',
  `pay_fee`                  decimal(15, 2)         not null     default '0.00' comment '手续费',
  `total_surplus_amount`     decimal(20, 2)         not null     default '0.00' comment '余额',
  `trade_no`                 varchar(32)            not null     default '' comment '各平台交易号',
  `order_sn`                 varchar(32)            not null     default '' comment '网站订单号',
  `order_comp_status`        tinyint(1)     not null     default '0' comment '订单完成状态1：已完成，2:未完成',
  `update_order_status_time` timestamp              null         default null comment '更新订单状态时间',
  `pay_type`                 tinyint(2)             not null     default '0' comment '支付类型 1:收入，2：支出',
  `pay_type_name`            varchar(32)            not null     default '' comment '支付类型名称',
  `remark`                   text comment '自定义备注',
  `created`                  timestamp              not null     default now() comment '创建时间',
  `no_settle_flag`           tinyint(2)             not null     default '0' comment '是否参与结算 0:参入，1：不参与',
  primary key (`id`),
  key `pay_sn` (`pay_sn`),
  key `pay_code` (`pay_code`),
  key `trade_no` (`trade_no`),
  key `order_sn` (`order_sn`),
  key `pay_type` (`pay_type`),
  key (`shop_id`)
);

-- --  店铺数据统计
-- drop table if exists `b2c_statistics_summary`;
create table `b2c_statistics_summary` (
  `id`                            mediumint(10)  not null auto_increment,
  `shop_id`                       int(11)                not null default 0 comment '店铺ID',
  `statis_date`                   timestamp              null     default null comment '统计数据时间',
  `browse_pv`                     mediumint(8)   not null default 0,
  `browse_uv`                     mediumint(8)   not null default 0,
  `total_order`                   mediumint(8)   not null default 0,
  `total_goods`                   mediumint(8)   not null default 0,
  `total_amount`                  decimal(12, 2)         not null default 0.00,
  `conversion_rate`               decimal(5, 2)          not null default 0.00,
  `per_cus_transaction`           decimal(7, 2)          not null default 0.00,
  `new_order_num`                 mediumint(8)   not null default 0,
  `new_cod_order_num`             mediumint(8)   not null default 0,
  `new_waitpay_order_num`         mediumint(8)   not null default 0,
  `new_cancel_order_num`          mediumint(8)   not null default 0,
  `new_close_order_num`           mediumint(8)   not null default 0,
  `new_returnning_order_num`      mediumint(8)   not null default 0,
  `new_return_finish_order_num`   mediumint(8)   not null default 0,
  `total_paid_order_num`          mediumint(8)   not null default 0,
  `total_dlv_order_num`           mediumint(8)   not null default 0,
  `total_cancelled_order_num`     mediumint(8)   not null default 0,
  `total_closed_order_num`        mediumint(8)   not null default 0,
  `total_finished_order_num`      mediumint(8)   not null default 0,
  `total_returning_order_num`     mediumint(8)   not null default 0,
  `total_return_finish_order_num` mediumint(8)   not null default 0,
  `total_goods_num`               smallint(6)    not null default 0,
  `on_sale_goods_num`             smallint(6)    not null default 0,
  `new_add_goods`                 smallint(6)    not null default 0,
  `created`                       timestamp              not null default now() comment '创建时间',
  primary key (`id`),
  unique key `statis_date_unique` (`statis_date`),
  key `created` (`created`),
  key (`shop_id`)
);
-- -- 管理后台登录记录表
-- drop table if exists `b2c_admin_login_record`;
create table `b2c_admin_login_record` (
  `id`          int(20)     not null auto_increment,
  `shop_id`     int(11)             not null default 0 comment '店铺ID',
  `user_name`   varchar(32)         not null default '' comment '登录用户名',
  `login_type`  smallint(3)         not null default 0 comment '登录类型：0普通登录，1改密码',
  `login_time`  timestamp           null     default null comment '操作时间',
  `unlock_time` timestamp           null     default null comment '解锁时间',
  `user_ip`     varchar(64)         not null default '' comment 'ip地址',
  `user_agent`  varchar(256)        not null default '' comment '浏览器ua',
  `retry_count` smallint(3)         not null default 0 comment '重试次数',
  `is_tmp_lock` tinyint(1)  not null default 0 comment '是否暂时锁定',
  primary key (`id`),
  key (`user_name`),
  key (`user_ip`),
  key (`shop_id`)
);

-- -- 店铺角色表

-- drop table if exists `b2c_shop_role`;
create table `b2c_shop_role` (
  `role_id`             int(11)     not null  auto_increment,
  `shop_id`             int(11)     not null  default 0 comment '店铺ID',
  `role_name`           varchar(50) not null  default '' comment '角色名称',
  `privilege_list`      text        not null  default '' comment '权限列表，json数组存储',
  `official_proxy_role` tinyint(1)            default 1 comment '是否为官方代理子帐号角色，是则不可删除',
  `create_time`         timestamp             default now(),
  primary key (`role_id`),
  key (`shop_id`)
);

-- -- 店铺子帐号

-- drop table if exists `b2c_shop_child_account`;
create table `b2c_shop_child_account` (
  `account_id`   int(11)      not null  auto_increment,
  `shop_id`      int(11)      not null  default 0 comment '店铺ID',
  `account_name` varchar(191) not null  default '' comment '子账号用户名',
  `account_pwd`  varchar(40)  not null  default '' comment '子账号密码',
  `role_id`      int(11)      not null  default 0 comment '角色ID',
  `create_time`  timestamp              default now(),
  `mobile`       varchar(32)  not null  default '0' comment '手机号',
  primary key (`account_id`),
  unique key (`account_name`),
  key (`shop_id`)
);

-- -- 商品品牌
-- drop table if exists `b2c_product_brand`;
create table `b2c_product_brand` (
  `id`         int(11)     not null auto_increment,
  `brand_name` varchar(32) not null comment '品牌名称',
  `e_name`     varchar(32) not null comment '品牌英文名称',
  `logo`       varchar(191)         default null comment '品牌Logo',
  `first`      tinyint(1)  not null default '0' comment '优先级',
  `in_time`    datetime    not null,
  `up_time`    datetime             default null,
  `is_del`     tinyint(1)  not null default '0' comment '0为未删除 1为删除',
  `desc`       text                 default null comment '品牌介绍',
  primary key (`id`)
);

-- 属性组表
-- drop table if exists `b2c_attribute`;
create table `b2c_attribute` (
  `attr_id`        int(11)     not null auto_increment,
  `name`           varchar(30) not null,
  `attribute_info` text comment 'json存储',
  `create_time`    timestamp            default now(),
  `is_del`         tinyint(1)  not null default 0,
  primary key (`attr_id`)
);

-- --  属性名称表
-- drop table if exists `b2c_attribute_name`;
create table `b2c_attribute_name` (
  `attr_name_id` int(11)      not null auto_increment,
  `attr_type`    tinyint(1)   not null default 0 comment '0下拉选择，1文本输入',
  `for_search`   tinyint(1)   not null default 0 comment '1用于搜索',
  `attr_name`    varchar(191) not null comment '属性名',
  `attr_id`      int(11)      not null default 0,
  `is_del`       tinyint(1)   not null default 0,
  primary key (`attr_name_id`),
  key (`attr_id`)
);

-- --  属性值表
-- drop table if exists `b2c_attribute_values`;
create table `b2c_attribute_values` (
  `attr_val_id`  int(11)      not null auto_increment,
  `attr_value`   varchar(191) not null comment '属性值',
  `attr_id`      int(11)      not null default 0,
  `attr_name_id` int(11)      not null default 0,
  `is_del`       tinyint(1)   not null default 0,
  primary key (`attr_val_id`),
  key (`attr_id`),
  key (`attr_name_id`)
);

-- --  商品属性值，筛选检索用
-- drop table if exists `b2c_attribute_values_index`;
create table `b2c_attribute_values_index` (
  `id`           int(11)      not null auto_increment,
  `goods_id`     int(11)      not null default 0,
  `attr_id`      int(11)      not null default 0,
  `attr_name_id` int(11)      not null default 0,
  `attr_type`    tinyint(1)   not null default 0,
  `attr_val_id`  int(11)      not null default 0,
  `attr_name`    varchar(191) not null comment '属性名',
  `attr_value`   varchar(191) not null comment '属性值',
  `create_time`  timestamp             default now(),
  primary key (`id`),
  key (`goods_id`),
  key (`attr_id`),
  key (`attr_name_id`),
  key (`attr_val_id`)
);

-- -- 用户收藏
-- drop table if exists `b2c_user_collection`;
create table `b2c_user_collection` (
  `id`         int(11)      not null  auto_increment,
  `user_id`    int(11)      not null,
  `username`   varchar(50)            default null,
  `goods_id`   int(11)      not null,
  `goods_name` varchar(191) not null,
  `goods_img`  varchar(191) not null,
  `in_time`    datetime     not null,
  `shop_id`    int(11)      not null  default 0 comment '店铺ID',
  primary key (`id`),
  key (`shop_id`)
);

-- -- 套餐
-- drop table if exists `b2c_goods_collocation`;
create table `b2c_goods_collocation` (
  `id`        int(11)                       not null  auto_increment,
  `shop_id`   int(11)                       not null  default 0 comment '店铺ID',
  `goods_id`  int(11)                       not null,
  `parent_id` int(11)                       not null comment '自增ID',
  `prd_id`    int(11)                       not null comment '从商品规格ID',
  `positon`   tinyint(1)                    not null  default '1' comment '位置 1为主商品 2为从商品',
  `type`      tinyint(1)                    not null  default '1' comment '类型 1为搭配立减 2为套餐',
  `discount`  decimal(10, 2) default '0.00' not null comment '优惠',
  `in_time`   datetime                      null      default null,
  `up_time`   datetime                      null      default null,
  `flag`      tinyint(1)                    not null  default '1' comment '1有效 2无效',
  primary key (`id`)
);

-- -- 最近浏览
-- drop table if exists `b2c_recently_browsed`;
create table `b2c_recently_browsed` (
  `id`            int(11)  not null  auto_increment,
  `shop_id`       int(11)  not null  default 0 comment '店铺ID',
  `user_id`       int(11)  not null comment '用户ID',
  `user_cid`      varchar(64)        default null,
  `goods_id`      int(11)  not null comment '商品ID',
  `browse_number` int(11)  not null  default '1' comment '浏览次数',
  `up_time`       datetime null      default null,
  primary key (`id`)
);

-- -- 退回订单表
-- drop table if exists `b2c_return_order`;
create table `b2c_return_order` (
  `ret_id`                  int(11)         not null  auto_increment,
  `order_id`                int(11)         not null  default '0',
  `order_sn`                varchar(30)             not null  default '',
  `return_order_sn`         varchar(30) default ''  not null comment '退款单号',
  `shop_id`                 int(11)                 not null  default 0 comment '店铺ID',
  `user_id`                 int(11)         not null  default '0',
  `goods_id`                int(11)         not null  default '0',
  `refund_status`           tinyint(1)              not null  default '1' comment '1是审核中，2是通过审核，3退货没通过审核，4买家提交物流 或 仅退款申请，5：退款退货成功，6是拒绝退款退货',
  `money`                   decimal(10, 2)  not null  default '0.00' comment '退款商品金额',
  `shipping_fee`            decimal(10, 2)  not null  default '0.00' comment '退运费金额',
  `return_type`             tinyint(1)              not null  default '0' comment '退款类型,0:只退款，1:退货又退款',
  `reason`                  varchar(191)            not null  default '' comment '退款理由',
  `return_desc`             text                    not null comment '退款说明',
  `shipping_type`           varchar(191)            not null  default '' comment '快递类型',
  `shipping_no`             varchar(50)             not null  default '' comment '快递单号',
  `goods_images`            text comment '商品图片',
  `voucher_images`          text comment '凭证图片',
  `phone`                   varchar(12)             not null  default '' comment '电话号码',
  `apply_time`              timestamp               null      default null comment '退货且退货提交审核时间，对应refund_status=1',
  `apply_pass_time`         timestamp               null      default null comment '审核通过时间，对应refund_status=2',
  `apply_not_pass_time`     timestamp               null      default null comment '审核未通过时间，对应refund_status=3',
  `shipping_or_refund_time` timestamp               null      default null comment '只退款时为退款申请时间，退货又退款时为提交物流信息时间，对应refund_status=4',
  `refund_success_time`     timestamp               null      default null comment '退款成功时间，对应refund_status=5',
  `refund_refuse_time`      timestamp               null      default null comment '退款拒绝时间，对应refund_status=6',
  `refund_cancel_time`      timestamp               null      default null comment '退款撤销时间，对应refund_status=7',
  `apply_not_pass_reason`   varchar(1000)           null comment '审核未通过原因',
  `refund_refuse_reason`    varchar(1000)           null comment '退款拒绝原因',
  `return_address`          varchar(1000)           not null  default '' comment '退货地址',
  `merchant_telephone`      varchar(12)             not null  default '' comment '商家电话',
  `consignee`               varchar(32)             not null  default '' comment '收货人',
  `zip_code`                varchar(10)             not null  default '' comment '邮编',
  `add_time` timestamp not null default CURRENT_TIMESTAMP on update current_timestamp comment '创建退款单时间',
  primary key (`ret_id`),
  key `order_sn` (`order_sn`)
);

/**
  退款日志记录，优先级卡余额、用户余额、积分抵扣、支付额
 */
-- drop table if exists `b2c_refund_amount_record`;
create table `b2c_refund_amount_record` (
  `rec_id`       int(11)         not null      auto_increment,
  `order_sn`     varchar(30)             not null      default '',
  `user_id`      int(11)         not null      default '0',
  `refund_field` varchar(20) comment '订单退款字段：member_card_balance,score_discount,money_paid,use_account',
  `refund_money` decimal(10, 2)  not null      default '0.00' comment '退款金额',
  `refund_time`  timestamp               not null      default now() comment '订单退款时间',
  `ret_id`       int(11)         not null comment 'b2c_return_order的ret_id',
  primary key (`rec_id`),
  key `order_sn` (`order_sn`),
  key `ret_id` (`ret_id`)
);

-- -- 部分商品发货表

-- drop table if exists `b2c_part_order_goods_ship`;
create table `b2c_part_order_goods_ship` (
  `rec_id`         int(11)        not null  auto_increment,
  `shop_id`        int(11)                not null  default 0 comment '店铺ID',
  `order_goods_id` int(11)        not null  default '0' comment '订单商品表的id',
  `order_sn`       varchar(20)            not null  default '',
  `batch_no`       varchar(120)           not null  default '',
  `goods_id`       mediumint(8)   not null  default '0',
  `goods_name`     varchar(120)           not null  default '',
  `product_id`     mediumint(8)   not null  default '0',
  `send_number`    smallint(5)    not null  default '1',
  `goods_attr`     text                   not null,
  `shipping_no`    varchar(50)            not null  default '' comment '快递单号',
  `shipping_name`  varchar(120)           not null  default '' comment '快递名称',
  `shipping_id`    tinyint(3)             not null  default '0' comment '快递ID',
  `shipping_time`  timestamp                        default now() comment '部分发货时间',
  `confirm_time`   timestamp              null      default null comment '订单确收收货时间',
  primary key (`rec_id`),
  key `order_goods_id` (`order_goods_id`),
  key `order_sn` (`order_sn`),
  key `batch_no` (`batch_no`),
  key `goods_id` (`goods_id`)
);
-- -- 退货商品表
-- drop table if exists `b2c_return_order_goods`;
create table `b2c_return_order_goods` (
  `id`           int(11)       not null  auto_increment,
  `shop_id`      int(11)               not null  default 0 comment '店铺ID',
  `rec_id`       int(11)  comment '订单商品表的id',
  `ret_id`       int(11)  comment '退货记录表的id',
  `order_sn`     varchar(20)           not null  default '',
  `goods_id`     mediumint(8)  not null  default '0',
  `goods_name`   varchar(120)          not null  default '',
  `product_id`   mediumint(8)  not null  default '0',
  `goods_number` smallint(5)   not null  default '1' comment '退货商品数量',
  `market_price` decimal(10, 2)        not null  default '0.00',
  `goods_price`  decimal(10, 2)        not null  default '0.00',
  `goods_attr`   text                  not null,
  `send_number`  smallint(5)   not null  default '0' comment '发货商品数量',
  `goods_img`    varchar(191)          not null  default '',
  `success`      tinyint(1)            not null  default 1 comment '0代表退货申请被拒绝，1代表正在退货中，2代表退货成功',
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
  `shop_id`            int(11)               null     default 0 comment '店铺ID',
  `act_name`           varchar(120)          not null default '',
  `type`               tinyint(1)            not null default 0 comment '类型,1每满减 2满件 3满折 4满赠',
  `start_time`         timestamp             null     default null,
  `end_time`           timestamp             null     default null,
  `created`            timestamp             null     default CURRENT_TIMESTAMP,
  `recommend_goods_id` text                  null comment '指定商品可用',
  `recommend_cat_id`   text                  null comment '指定分类可用',
  `recommend_brand_id` text                  null comment '指定品牌可用',
  `act_type`           tinyint(1)            null     default 0 comment '活动类型，0-选中项参与活动；1-选中项不参与活动',
  `del_flag`           int(1)                null     default 0,
  `strategy_priority`  int(11)               null     default 0 comment '促销活动优先级',
  `card_id`            TEXT NULL   COMMENT '专属会员卡',
  primary key (`id`),
  key (`shop_id`),
  key mrking_strategy_delflag(`del_flag`)
);


-- -- 瞒折满减优惠规则表
-- drop table if exists `b2c_mrking_strategy_condition`;
create table `b2c_mrking_strategy_condition` (
  `id`           mediumint(8)  not null  auto_increment,
  `shop_id`      int(11)               null      default 0 comment '店铺ID',
  `strategy_id`  int(11)               not null  default 0,
  `full_money`   decimal(10, 2)        null      default '0.00' comment '满多少金额',
  `reduce_money` decimal(10, 2)        null      default '0.00' comment '减多少钱',
  `amount`       int(10)       null      default '0' comment '满几件',
  `discount`     decimal(10, 2)        null      default '0.00' comment '打几折',
  `gift`         text                  null comment '赠品',
  `gift_left`    text                  null comment '赠品剩余数量',
  primary key (`id`)
);

-- -- 个人中心说明管理
-- drop table if exists `b2c_user_explain`;
create table `b2c_user_explain` (
  `id`          smallint(5)  not null  auto_increment,
  `shop_id`     int(11)              not null  default 0 comment '店铺ID',
  `text`        text                           default '',
  `type`        tinyint(1)                     default '0' comment '1-余额，2-级别，3-成长值，4-积分',
  `create_time` timestamp                      default now(),
  `up_time`     datetime                       default null,
  primary key (`id`),
  unique key `type` (`type`)
);
-- -- 发票
-- drop table if exists `b2c_invoice_new`;
create table `b2c_invoice_new` (
  `id`                  int(11)                not null  auto_increment,
  `shop_id`             int(11)                not null  default 0 comment '店铺ID',
  `user_id`             int(11)                not null,
  `state`               tinyint(1)                       default 0 comment '发票类型 0普通发票 2增值税发票',
  `invoice_title`       char(255)              not null comment '发票抬头',
  `invoice_content`     int(255)               not null comment '发票内容: 明细1,办公用品2,电脑配件3,耗材4',
  `unit_name`           varchar(60)            not null  default '',
  `identification_code` varchar(120)           not null  default '' comment '纳税人识别码',
  `registered_address`  varchar(191)           not null  default '' comment '所在地址',
  `registered_phone`    varchar(32)            not null  default '',
  `bank`                varchar(191)                     default '' comment '开户行',
  `bank_no`             varchar(191)                     default '' comment '开户行卡号',
  `invoice_name`        varchar(60)            not null  default '',
  `invoice_mobile`      varchar(32)            not null  default '',
  `email`               varchar(100)                     default null comment '邮箱',
  `province_code`       mediumint(10)  not null  default '0' comment '所在省',
  `province_name`       varchar(50)            not null  default '',
  `city_code`           mediumint(10)  null      default '0' comment '所在城市',
  `city_name`           varchar(120)           not null  default '',
  `district_code`       mediumint(10)  null      default '0' comment '所在区县',
  `district_name`       varchar(120)           not null  default '',
  `address`             varchar(191)           not null  default '' comment '所在地址',
  `proof`               varchar(100)                     default null comment '一般纳税人证明',
  `in_time`             datetime               not null,
  `up_time`             datetime               null      default null comment '最后使用时间',
  `pos_id`              int(11)                not null  default '0' comment 'pos同步的id',
  primary key (`id`)
);

-- --  配置信息表 店铺或平台的配置
-- drop table if exists `b2c_shop_cfg`;
create table `b2c_shop_cfg` (
  `rec_id`  smallint(5)  not null auto_increment,
  `shop_id` int(11)      not null default 0 comment '店铺id',
  `k`       varchar(191)                  default '',
  `v`       text,
  primary key (`rec_id`),
  unique key `shop_id_k` (`k`, `shop_id`)
);

-- -- 商品评价表
-- drop table if exists `b2c_comment_goods`;
create table `b2c_comment_goods` (
  `id`            int(11)       not null auto_increment,
  `shop_id`       int(11)       not null comment '店铺ID',
  `user_id`       int(11)       not null comment '用户ID',
  `commstar`      tinyint(1)    not null comment '评价星级',
  `user_score`    int(11)       not null comment '评价可得积分',
  `anonymousflag` tinyint(1)    not null comment '匿名状态 0.未匿名；1.匿名',
  `commtag`       varchar(100)  not null default '' comment '评价标签' collate 'utf8mb4_unicode_ci',
  `goods_id`      int(11)       not null comment '商品id',
  `order_sn`      varchar(20)   not null comment '订单编号' collate 'utf8mb4_unicode_ci',
  `comm_note`     varchar(255)  not null comment '评论内容' collate 'utf8mb4_unicode_ci',
  `comm_img`      varchar(1000) null     default null comment '评论图片' collate 'utf8mb4_unicode_ci',
  `in_time`       datetime      null     default null comment '创建时间',
  `up_time`       datetime      null     default null comment '更新时间',
  `flag`          tinyint(1)    not null default '0' comment '0:未审批,1:审批通过,2:审批未通过',
  `del_flag`      tinyint(1)    not null default '0' comment '1:删除',
  primary key (`id`),
  index `shop_id` (`shop_id`)
);

-- -- 商品评价回复表
-- drop table if exists `b2c_comment_goods_answer`;
create table `b2c_comment_goods_answer` (
  `answer_id`   int(11)    not null auto_increment,
  `comment_id`  int(11)    not null comment '回复的商品评论ID',
  `content`     text collate utf8mb4_unicode_ci comment '回复内容',
  `answer_time` timestamp  null     default CURRENT_TIMESTAMP comment '回复时间',
  `del_flag`    tinyint(1) not null default '0' comment '1:删除',
  primary key (`answer_id`),
  key `comment_id` (`comment_id`)
);

-- 评价标签表
-- drop table if exists `b2c_comment_tag`;
create table `b2c_comment_tag` (
  `id`       int(11)     not null auto_increment,
  `commtag`  varchar(20) not null default '',
  `user_id`  int(11)     not null,
  `goods_id` int(11)     not null default '0',
  `order_sn` varchar(20) not null default '',
  `in_time`  timestamp   not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `flag`     tinyint(1)  not null default '0' comment '0:未审批,1:审批通过,2:审批未通过',
  primary key (`id`)
);
-- 会员标签
-- drop table if exists `b2c_tag`;
create table `b2c_tag` (
  `tag_id`   int(11)   not null                       auto_increment,
  `tag_name` varchar(20)                              default null,
  `in_time`  timestamp null                           default CURRENT_TIMESTAMP,
  primary key (`tag_id`),
  unique key `tag_name` (`tag_name`)
);
-- 会员关联标签表
-- drop table if exists `b2c_user_tag`;
create table `b2c_user_tag` (
  `user_id`  int(11)   not null,
  `tag_id`   int(11)   not null,
  `add_time` timestamp null default CURRENT_TIMESTAMP,
  primary key (`user_id`, `tag_id`),
  unique key `user_tag` (`user_id`, `tag_id`)
);
-- -- 会员绑定的会员卡信息
-- drop table if exists `b2c_user_card`;
create table `b2c_user_card` (
  `user_id`         int(11)                not null comment '会员ID',
  `card_id`         int(11)                not null comment '会员卡ID',
  `add_time`        timestamp              null     default null,
  `flag`            tinyint(1)             not null default '0' comment '0:正常，1:删除',
  `card_no`         varchar(32) default '' not null comment '会员卡号',
  `expire_time`     timestamp              null     default null,
  `update_time`     timestamp              null comment '更新时间',
  `is_default`      tinyint(1)             not null default '0' comment '1:默认会员卡',
  `money`           decimal(10, 2)         null     default '0.00' comment '卡余额',
  `surplus`         int(11)                         default null comment '卡剩余次数',
  `activation_time` timestamp              null comment '激活时间',
  `exchang_surplus`         int(11)                         default '0' comment '卡剩余兑换次数',
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
  `expire_type`       tinyint(1)                              default '0' comment '0:固定日期 1：自领取之日起 2:不过期',
  `start_time`        timestamp    null                       default null comment '开始日期',
  `end_time`          timestamp    null                       default null comment '结束日期',
  `receive_day`       int(11)                                 default null comment '领取之日起N',
  `date_type`         tinyint(1)                              default null comment '0:日，1:周 2: 月',
  `activation`        tinyint(1)                              default null comment '0：不用激活，1：需要激活',
  `receive_code`      char(10)                                default null comment '领取码',
  `desc`              text comment '使用须知',
  `mobile`            char(11)                                default null comment '联系电话',
  `add_time`          timestamp    null                       default CURRENT_TIMESTAMP comment '添加时间',
  `up_time`           timestamp    null                       default null comment '更新时间',
  `flag`              tinyint(1)   not null                   default '1' comment '1:使用中，2:停止使用',
  `send_money`        int(11)                                 default null comment '开卡送钱',
  `charge_money`      text comment '充值活动策略',
  `use_time`          int(11)                                 default null comment '使用时间 1工作日 2双休 0不限制',
  `store_list`        varchar(191) not null                   default '{}' comment '可用门店',
  `count`             int(11)                                 default null comment '卡总次数',
  `is_delete`         tinyint(1)   null                       default '0' comment '1为删除状态',
  `grade`             char(10)     null                       default '' comment '等级卡的等级',
  `grade_condition`   varchar(200) null                       default '' comment '等级卡的条件',
  `activation_cfg`    varchar(200) null comment '激活信息配置',
  `examine`           tinyint(1)   null                       default '0' comment '是否审核 0不审核 1审核',
  `discount_goods_id` varchar(299) collate utf8mb4_unicode_ci default null comment '折扣商品ID',
  `discount_cat_id`   varchar(299) collate utf8mb4_unicode_ci default null comment '折扣平台分类ID',
  `discount_sort_id`  varchar(299) collate utf8mb4_unicode_ci default null comment '折扣商家分类ID',
  `discount_is_all`   tinyint(1)                      not null default '1' comment '折扣商品范围： 0:部分商品，1:全部商品',
  `is_pay`            tinyint(1)                      not null default '0' comment '0:直接购买 1:需要购买 2: 需要领取码',
  `pay_type`          tinyint(1)                      not null default '0' comment '0:不支持现金购买，1:支持现金购买',
  `pay_fee`           decimal(10,2)                             default '0.00' comment '购买资金',
  `pay_own_good`      tinyint(1)                                 default '0' comment '是否专属购买商品 0不是 1是',
  `receive_action` TINYINT(1) DEFAULT 0  NULL   COMMENT '领取方式 1:领取码 2：卡号+密码',
  `is_exchang`      tinyint(1)                                 default '0' comment '0不可 1部分 2全部',
  `store_use_switch`      tinyint(1)                                 default '0' comment '可否在门店使用  0可以 1不可以',
  `exchang_goods`      varchar(299)                                 default null comment '可兑换商品id',
  `exchang_freight`      tinyint(1)                                 default null comment '运费策略 0免运费 1使用商品运费策略',
  `exchang_count`      int(11)                                 default null comment '允许兑换次数',
  primary key (`id`)
);
/**  */


-- -- 门店分组
-- drop table if exists `b2c_group`;
create table `b2c_group` (
  `group_id`   int(11)     not null auto_increment,
  `group_name` varchar(20) not null,
  `in_time`    timestamp   null     default CURRENT_TIMESTAMP,
  primary key (`group_id`),
  unique key `group_name` (`group_name`)
);

-- -- 门店所属分组
-- drop table if exists `b2c_store_group`;
create table `b2c_store_group` (
  `store_id` int(11)   not null,
  `group_id` int(11)   not null,
  `add_time` timestamp null default CURRENT_TIMESTAMP,
  primary key (`store_id`, `group_id`),
  unique key `store_group` (`store_id`, `group_id`)
);

-- -- 门店商品管理
-- drop table if exists `b2c_store_goods`;
create table `b2c_store_goods` (
  `store_id`       int(11)                      not null,
  `goods_id`       int(11)                      not null,
  `prd_id`         int(11)                      not null,
  `prd_sn`         varchar(30)                  not null,
  `product_number` int default 0                null comment '库存',
  `product_price`  decimal(10, 2) default 0.00  null comment '价格',
  `is_sync`        tinyint(1) default 0         null comment '是否已同步',
  `is_on_sale`     tinyint(1)                   not null default '0' comment '''是否在售，1在售，0下架''',
  `up_time`        timestamp                    not null default CURRENT_TIMESTAMP,
  `flag`           tinyint(1)                   not null default '0' comment '1:初始化数据，0:无效数据',
  primary key (`store_id`, `goods_id`, `prd_id`, `is_on_sale`)
);

-- --  发送短信记录表
-- drop table if exists `b2c_sms`;
create table `b2c_sms` (
  `id`       int(11)             not null auto_increment,
  `mobile`   varchar(32)         not null default '',
  `sms_code` varchar(10)         not null,
  `add_time` timestamp           null     default CURRENT_TIMESTAMP,
  `ip`       varchar(20)                  default '',
  `status`   tinyint(1)  not null default '1' comment '0:发送失败 1：发送成功',
  `type`     varchar(10)                  default null comment '',
  `content`  text                not null comment '发送短信内容',
  primary key (`id`),
  key `mobile` (`mobile`)
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
  `pos_shop_id`    int default 0  null comment 'pos店铺ID',
  `add_time`       timestamp      not null default CURRENT_TIMESTAMP comment '添加时间',
  `up_time`        timestamp      null     default null comment '更新时间',
  `auto_pick`      smallint(1)    null     default '0' comment '设定自提',
  `is_delete`      tinyint(1)     null     default '0' comment '1为删除状态',
  primary key (`store_id`)
);

-- -- 发券记录
-- drop table if exists `b2c_give_voucher`;
create table `b2c_give_voucher` (
  `id`            int(11)                        not null auto_increment,
  `act_name`      varchar(50) character set utf8 not null comment '活动名称',
  `number`        int(6)                                  default null comment '参与人数',
  `have_pay`      int(4)                                  default null comment '有交易记录',
  `no_pay`        int(4)                                  default null comment '无交易记录',
  `max_count`     int(20)                                 default null comment '购买次数大于',
  `min_count`     int(20)                                 default null comment '购买次数小于',
  `card_id`       text comment '会员卡',
  `tag_id`        text comment '标签',
  `act_id`        int(11)                        not null comment '优惠券',
  `in_time`       timestamp                      null     default CURRENT_TIMESTAMP comment '插入时间',
  `max_ave_price` decimal(10, 2)                          default null comment '均价大于',
  `min_ave_price` decimal(10, 2)                          default null comment '均价小于',
  `update_time`   timestamp                      null comment '更新时间',
  `user`          text comment '手动添加会员',
  `send_condition` text character set utf8mb4 collate utf8mb4_unicode_ci  null comment '筛选发放人条件',
  `send_status`   tinyint(1) DEFAULT '0' COMMENT '发放状态:0未发放，1已发放',
  `send_action` tinyint(1) DEFAULT '1' COMMENT '发放类型:0立即发放，1定时发放',
  `start_time` DATETIME NOT NULL COMMENT '发放开始时间',
  primary key (`id`)
);


-- -- 服务分类
-- drop table if exists `b2c_service_category`;
create table `b2c_service_category` (
  `cat_id`      smallint(5)  not null auto_increment,
  `cat_name`    varchar(90)                   default '',
  `store_id`    int(11)              not null,
  `create_time` timestamp            null     default CURRENT_TIMESTAMP,
  primary key (`cat_id`),
  unique key `cat_name` (`cat_name`, `store_id`)
);

-- -- 门店服务信息
-- drop table if exists `b2c_store_service`;
create table `b2c_store_service` (
  `id`                   int(11)               not null auto_increment,
  `store_id`             int(11)               not null comment '门店ID',
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
  `service_type`         tinyint(1) default 0  not null comment '服务类型:0无技师1有技师',
  `services_number`      int(11)                        default null comment '服务数量',
  `tech_services_number` int(11)               null comment '技师单时段服务数量',
  `content`              mediumtext comment '服务描述',
  `add_time`             timestamp             null     default CURRENT_TIMESTAMP comment '添加时间',
  `up_time`              timestamp             null     default null comment '更新时间',
  `charge_resolve`       varchar(255)          null comment '收费说明',
  `is_delete`            tinyint(1)            null     default '0' comment '1为删除状态',
  primary key (`id`)
);

/**
退款记录表
*/
-- drop table if exists `b2c_order_refund_record`;
create table `b2c_order_refund_record` (
  `id`               mediumint(12)  not null auto_increment,
  `ret_id`           int(11)                not null default 0 comment '订单退货请求id',
  `shop_id`          int(11)                not null default 0 comment '店铺ID',
  `refund_sn`        varchar(32)            not null default '' comment '退款流水号',
  `pay_sn`           varchar(32)            not null default '' comment '支付流水号',
  `order_sn`         varchar(20)            not null default '' comment '订单编号',
  `apply_user`       varchar(60)            not null default '',
  `pay_code`         varchar(20)            not null default '' comment '支付宝:alipay,微信：？，...',
  `refund_amount`    decimal(20, 2)         not null default '0.00' comment '退款金额',
  `refund_time`      timestamp              not null default '0000-00-00 00:00:00' comment '退款时间',
  `mobile`           varchar(32)            not null default '',
  `shop_name`        varchar(50)                     default '' comment '店铺名称',
  `deal_status`      tinyint(1)     not null default 0 comment '处理状态，0:退款中，1：退款完成，2：退款失败',
  `op_deal_status`   tinyint(1)     not null default 0 comment '人工处理状态，0:待确认，1：已确认待退款，2：退款完成',
  `deal_status_name` varchar(64)            not null default '',
  `deal_remark`      varchar(512)           not null default '',
  `trans_sn`         varchar(64)            not null default '' comment '转账流水号',
  `finished_time`    timestamp              not null default '0000-00-00 00:00:00' comment '出账操作时间',
  `created`          timestamp              not null default CURRENT_TIMESTAMP comment '创建时间',
  `remark1`          text comment '自定义备注  建议用于存储原始数据',
  `is_offline`       TINYINT(1) DEFAULT 0  NULL   COMMENT '是否线下处理',
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
  `order_id`          mediumint(8)    not null auto_increment comment '订单ID',
  `store_id`          int(11)                 not null comment '门店ID',
  `order_sn`          varchar(20)             not null default '' comment '订单编号',
  `user_id`           mediumint(8)    not null default '0' comment '用户ID',
  `order_status`      tinyint(1)              not null default '0' comment '订单状态 0：待服务，1：已取消，2：已完成',
  `order_status_name` varchar(32) default ''  not null comment '订单状态名称',
  `subscriber`        varchar(60)             not null default '' comment '预约人姓名',
  `mobile`            varchar(60)             not null default '' comment '手机号',
  `service_id`        int(11)                 not null default '0' comment '服务ID',
  `technician_id`     mediumint(8)                     default 0 comment '预约技师ID',
  `technician_name`   varchar(20)                      default '' comment '技师名称',
  `service_date`      varchar(18) default ''  not null comment '预约日期',
  `service_period`    varchar(20)                      default null comment '预约时段',
  `add_message`       varchar(191)                     default null comment '客户留言',
  `admin_message`     varchar(191)                     default null comment '商家备注',
  `verify_code`       varchar(191)                     default '' comment '核销自提码',
  `verify_admin`      varchar(30)             null     default '' comment '核销人',
  `pay_code`          varchar(30)             not null default '' comment '支付代号',
  `pay_name`          varchar(120)            not null default '' comment '支付名称',
  `pay_sn`            varchar(32)             not null default '' comment '支付流水号',
  `money_paid`        decimal(10, 2)          not null default '0.00' comment '订单应付金额',
  `discount`          decimal(10, 2)          not null default '0.00' comment '券抵扣金额',
  `coupon_id`         mediumint(8)    not null default '0' comment '优惠券ID',
  `order_amount`      decimal(10, 2)          not null default '0.00' comment '订单总金额',
  `add_time`          timestamp               null     default null comment '订单提交时间',
  `pay_time`          timestamp               null     default null comment '支付时间',
  `cancelled_time`    timestamp               null     default null comment '取消时间',
  `finished_time`     timestamp               null     default null comment '订单完成时间',
  `prepay_id`         varchar(191)            null comment '微信支付Id，用于发送模板消息',
  `del_flag`          tinyint(1)              null     default '0' comment '删除',
  `verify_type`       tinyint(1)              null     default '0' comment '核销方式 0是店家核销 1是用户',
  `cancel_reason`     varchar(200)            null comment '取消原因',
  `type`              tinyint(1)              not null default '0' comment '创建类型 0用户创建 1后台',
  `verify_pay`        tinyint(1)              null     default '0' comment '核销支付方式 0门店买单 1会员卡 2余额',
  `ali_trade_no`      varchar(60)             null     default '' comment '支付宝交易单号',
  primary key (`order_id`)
);



-- -- 二维码存储表
-- drop table if exists `b2c_code`;
create table `b2c_code` (
  `code_id`    mediumint(8)     not null auto_increment comment '二维码id',
  `type`       smallint(2)              not null default '0' comment '分类：1店铺，2商品，3服务，4会员卡，5优惠券',
  `param_id`   varchar(32) default '0'  not null comment '对应的参数ID',
  `type_url`   varchar(100)             not null default '' comment 'type对应的APP页面地址',
  `qrcode_img` varchar(200)             not null default '' comment '二维码位置',
  `flag`       tinyint(1)                        default '0' comment '标记位',
  `add_time`   timestamp                null     default null comment '添加时间',
  `channel` varchar(20) DEFAULT '0' COMMENT '渠道分享码',
  primary key (`code_id`)
);
/**
 */

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
  `add_time`          timestamp not null default CURRENT_TIMESTAMP comment '添加时间',
  key `ref_date` (`ref_date`) using btree
);
-- -- 小程序概况趋势
-- drop table if exists `b2c_mp_summary_trend`;
create table `b2c_mp_summary_trend` (
  `ref_date`    char(8)   not null comment '日期',
  `visit_total` int(11)   not null default '0' comment '总访问量',
  `share_pv`    int(11)   not null default '0' comment '转发次数',
  `share_uv`    int(11)   not null default '0' comment '转发人数',
  `add_time`    timestamp null     default CURRENT_TIMESTAMP comment '添加时间',
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
  `add_time`          timestamp not null default CURRENT_TIMESTAMP comment '添加时间'
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
  `add_time`          timestamp not null default CURRENT_TIMESTAMP comment '添加时间'
);
-- -- 访问分布
-- drop table if exists `b2c_mp_distribution_visit`;
create table `b2c_mp_distribution_visit` (
  `ref_date` char(8)   not null comment '时间，如："20180313"',
  `list`     text comment '存入所有类型的指标情况',
  `add_time` timestamp not null default CURRENT_TIMESTAMP comment '添加时间',
  key `ref_date` (`ref_date`) using btree
);
-- -- 日留存
-- drop table if exists `b2c_mp_daily_retain`;
create table `b2c_mp_daily_retain` (
  `ref_date`     char(8)   not null comment '时间，如："20180313"',
  `visit_uv_new` text comment '新增用户留存',
  `visit_uv`     text comment '活跃用户留存',
  `add_time`     timestamp not null default CURRENT_TIMESTAMP comment '添加时间',
  key `ref_date` (`ref_date`) using btree
);
-- -- 周留存
-- drop table if exists `b2c_mp_weekly_retain`;
create table `b2c_mp_weekly_retain` (
  `ref_date`     char(20)  not null comment '时间，如："20180306-20180312"',
  `visit_uv_new` text comment '新增用户留存',
  `visit_uv`     text comment '活跃用户留存',
  `add_time`     timestamp not null default CURRENT_TIMESTAMP comment '添加时间'
);
-- -- 月留存
-- drop table if exists `b2c_mp_monthly_retain`;
create table `b2c_mp_monthly_retain` (
  `ref_date`     char(6)   not null comment '时间，如："201803"',
  `visit_uv_new` text comment '新增用户留存',
  `visit_uv`     text comment '活跃用户留存',
  `add_time`     timestamp not null default CURRENT_TIMESTAMP comment '添加时间'
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
  `add_time`         timestamp    not null default CURRENT_TIMESTAMP comment '添加时间',
  key `ref_date` (`ref_date`) using btree,
  key `page_path` (`page_path`) using btree,
  key `page_visit_pv` (`page_visit_pv`) using btree
);

-- -- 用户登录记录表,每小时存一条
-- drop table if exists `b2c_user_login_record`;
create table `b2c_user_login_record` (
  `id`            BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `user_id`       INT DEFAULT 0  NOT NULL   COMMENT '登陆用户id',
  `add_time`      timestamp        null                  default null comment '每日登陆时间',
  `user_ip`       varchar(64)      null                  default null comment '用户登录ip',
  `count`         INT DEFAULT 0  NULL   COMMENT '每日登陆次数',
  `update_time`   timestamp        null                  default null comment '最后登录时间',
  `province_code` varchar(20) collate utf8mb4_unicode_ci default null comment '省',
  `province`      varchar(20) collate utf8mb4_unicode_ci default null comment '省',
  `city_code`     varchar(20) collate utf8mb4_unicode_ci default null comment '市',
  `city`          varchar(20) collate utf8mb4_unicode_ci default null comment '市',
  `district_code` varchar(20) collate utf8mb4_unicode_ci default null comment '区',
  `district`      varchar(20) collate utf8mb4_unicode_ci default null comment '区',
  `lat`           varchar(64)      null                  default null comment '经度',
  `lng`           varchar(64)      null                  default null comment '纬度',
  primary key (`id`),
  key `add_time` (`add_time`) using btree,
  key `district_add_time` (`add_time`, `district_code`) using btree
);
-- -- 发票表
-- drop table if exists `b2c_invoice`;
create table `b2c_invoice` (
  `id`             int(11)      not null  auto_increment,
  `user_id`        int(11)      not null comment '用户id',
  `type`           tinyint(1)   not null  default 0 comment '发票类型',
  `title`          varchar(191) not null comment '公司名称',
  `telephone`      varchar(191)           default null comment '公司电话',
  `taxNumber`      varchar(191)           default null comment '税号',
  `companyAddress` varchar(191)           default null comment '公司地址',
  `bankName`       varchar(191)           default null comment '银行名称',
  `bankAccount`    varchar(191)           default null comment '银行账号',
  `add_time`       timestamp    null      default null on update CURRENT_TIMESTAMP,
  primary key (`id`)
);
-- -- 服务技师表
-- drop table if exists `b2c_service_technician`;
create table `b2c_service_technician` (
  `id`                   mediumint(8)  not null auto_increment comment '技师id',
  `store_id`             int(9)        not null default '0',
  `technician_name`      varchar(100)          not null default '' comment '技师名称',
  `technician_mobile`    varchar(32)           not null default '' comment '技师电话',
  `bg_img_path`          varchar(191)          not null default '' comment '技师头像地址',
  `technician_introduce` varchar(200)                   default '' comment '技师简介',
  `group_id`             mediumint(6)          not null default '0' comment '技师分组',
  `service_type`         tinyint(2)            not null default '0' comment '技师服务项目：0所有，1部分',
  `service_list`         varchar(191)          not null default '{}' comment '当type=0是服务项目详情：array()',
  `remarks`              varchar(1024)                  default '' comment '备注',
  `del_flag`             tinyint(1)            not null default '0' comment '0正常，1删除',
  `add_time`             timestamp             null     default null,
  `update_time`          timestamp             null     default null,
  primary key (`id`)
);


-- -- 服务技师分组表
-- drop table if exists `b2c_service_technician_group`;
create table `b2c_service_technician_group` (
  `group_id`   smallint(6)  not null auto_increment comment '分组id',
  `group_name` varchar(90)          not null default '' comment '分组名称',
  `store_id`   mediumint(9)         not null comment '门店id',
  `add_time`   timestamp            not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '添加时间',
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
  `appid`       varchar(100) null     default null comment '小程序appid',
  `add_time`    datetime     not null,
  `up_time`     datetime     not null,
  `del_flag`    tinyint(1)   not null default 0 comment '0:未删除，1:已删除',
  `del_time`    int(11)      null     default 0,
  primary key (`id`),
  index `shop_decorate_link` (`shop_id`, `link_action`, `del_flag`)
);
-- -- 服务技师班次表
-- drop table if exists `b2c_service_schedule`;
create table `b2c_service_schedule` (
  `schedule_id`   tinyint(6)  not null auto_increment comment '排班id',
  `store_id`      int(9)      not null default '0',
  `schedule_name` varchar(32)         not null default '""' comment '排班名称',
  `begin_time`    varchar(10)         not null default '""' comment '开始时间',
  `end_time`      varchar(10)         not null default '""' comment '结束时间',
  `del_flag`      tinyint(1)          not null default '0' comment '0正常，1删除',
  `add_time`      timestamp           null     default CURRENT_TIMESTAMP comment '添加时间',
  `update_time`   timestamp           null     default '0000-00-00 00:00:00' comment '更改时间',
  primary key (`schedule_id`)
);
-- -- 服务技师排班表
-- drop table if exists `b2c_service_technician_schedule`;
create table `b2c_service_technician_schedule` (
  `id`            int(12)       not null auto_increment comment 'id',
  `store_id`      int(9)        not null default '0',
  `technician_id` mediumint(8)  not null comment '技师id',
  `work_date`     varchar(18)           not null default '' comment '工作日期',
  `schedule_id`   tinyint(6)            not null default '0' comment '排班id',
  `add_time`      timestamp             null     default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '添加时间',
  `update_time`   timestamp             null     default '0000-00-00 00:00:00' comment '修改时间',
  primary key (`id`)
);

-- -- 分裂优惠券分享领取记录
-- drop table if exists `b2c_share_split`;
create table `b2c_share_split` (
  `id`          mediumint(8)  not null auto_increment,
  `user`        mediumint(8)  not null default '0' comment '分享的user_id',
  `user_id`     mediumint(8)  not null default '0' comment '分享领取的user_id',
  `act_id`      mediumint(8)  not null default '0' comment '分裂优惠券id',
  `create_time` timestamp             null     default null comment '领取时间',
  `order_sn`    varchar(20)           not null default '' comment '',
  `share_limit` int(11)               not null default 0 comment '可分享个数',
  primary key (`id`),
  key `user` (`user`)
);

-- -- 服务评价表
-- drop table if exists `b2c_comment_service`;
create table `b2c_comment_service` (
  `id`            int(11)               not null auto_increment,
  `shop_id`       int(11)               not null comment '店铺ID',
  `store_id`      int(11)               not null comment '门店ID',
  `technician_id` mediumint(8)  not null comment '技师id',
  `user_id`       int(11)               not null comment '用户ID',
  `commstar`      tinyint(1)            not null comment '评价星级',
  `user_score`    int(11)               not null comment '评价可得积分',
  `anonymousflag` tinyint(1)            not null comment '匿名状态 0.未匿名；1.匿名',
  `commtag`       varchar(100)          not null default '' comment '评价标签' collate 'utf8mb4_unicode_ci',
  `service_id`    int(11)               not null comment '服务id',
  `order_sn`      varchar(20)           not null comment '订单编号' collate 'utf8mb4_unicode_ci',
  `comm_note`     varchar(255)          not null comment '评论内容' collate 'utf8mb4_unicode_ci',
  `comm_img`      varchar(1000)         null     default null comment '评论图片' collate 'utf8mb4_unicode_ci',
  `in_time`       datetime              null     default null comment '创建时间',
  `up_time`       datetime              null     default null comment '更新时间',
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
  `invoice_id`          int(11)                               default '0' comment '发票id',
  `invoice_detail`      text comment '发票内容：json存储',
  `add_message`         varchar(191)                          default '' comment '客户留言',
  `pay_code`            varchar(30)                           default null comment '支付代号',
  `pay_name`            varchar(120)                          default null comment '支付名称',
  `prepay_id`           varchar(191)                          default null comment '微信支付Id，用于发送模板消息',
  `pay_sn`              varchar(32)                           default null comment '支付流水号',
  `money_paid`          decimal(10, 2)                        default '0.00' comment '订单应付金额',
  `member_card_no`      varchar(32) default '0'      null comment '会员卡NO',
  `member_card_redunce` decimal(10, 2)                        default '0.00' comment '会员卡抵扣金额',
  `member_card_balance` decimal(10, 2) default 0.00  null comment '会员卡消费金额',
  `score_discount`      decimal(10, 2)                        default '0.00' comment '积分抵扣金额',
  `use_account`         decimal(10, 2)                        default '0.00' comment '用户消费余额',
  `order_amount`        decimal(10, 2)                        default '0.00' comment '订单总金额',
  `add_time`            timestamp                    null     default CURRENT_TIMESTAMP comment '订单提交时间',
  `pay_time`            timestamp                    null comment '支付时间',
  `seller_remark`       varchar(512)                 null     default '' comment '卖家备注',
  `star_flag`           tinyint(1)                   null     default 0 comment '标星订单：0 未标星 1 标星',
  `del_flag`            tinyint(1)                            default '0' comment '删除',
  `ali_trade_no`        varchar(60)                  null     default '' comment '支付宝交易单号',
  primary key (`order_id`)
);


-- -- 模板消息form_id表
-- drop table if exists `b2c_mp_template_form_id`;
create table `b2c_mp_template_form_id` (
  `rec_id`              mediumint(8)  not null auto_increment comment '记录Id',
  `form_id`             varchar(255)          not null default '' comment '小程序提交form_id',
  `user_id`             mediumint(8)  not null default '0' comment '用户id',
  `open_id`             varchar(255)          not null default '' comment '用户openid',
  `use_state`           tinyint(1)                     default '0' comment '使用状态，0未使用，1冻结 2 使用',
  `status`              tinyint(1)            not null default 0 comment '1: 发送成功  0：未知',
  `is_visit`            tinyint(1)            not null default 0 comment '是否已点击访问 1：是 0： 否',
  `template_type`       tinyint(1)                     default '0' comment '使用类型，0 初始 1 订单 2 预约 3 优惠券 4 拼团 5卡券 ',
  `mp_link_identity`    varchar(255)          not null default '' comment '发送消息关联id，如果order_sn等',
  `mp_template_no`      varchar(255)          not null default '' comment '发送模板编号',
  `mp_template_content` text comment '发送模板消息内容',
  `user_visit_time`     datetime              null     default null comment '用户点击访问时间',
  `create_time`         timestamp             null     default CURRENT_TIMESTAMP comment '提交时间',
  `use_time`            timestamp             null     default '0000-00-00 00:00:00' comment '使用时间',
  primary key (`rec_id`),
  key (`form_id`),
  key (`user_id`),
  key (`open_id`)
);
-- --  拼团活动定义表
-- drop table if exists `b2c_pin_group_define`;
create table `b2c_pin_group_define` (
  `id`               int(11)      not null  auto_increment,
  `shop_id`          int(11)      not null comment '店铺ID',
  `goods_id`         int(11)      not null comment '商品ID',
  `name`             varchar(100) not null comment '活动名称',
  `limit_amount`     smallint(6)  not null comment '成团人数',
  `join_limit`       smallint(6)  not null comment '参团限制',
  `open_limit`       smallint(6)  not null comment '开团限制',
  `is_default`       tinyint(1)   not null  default 0 comment '默认成团',
  `start_time`       datetime     not null comment '开始时间',
  `end_time`         datetime     not null comment '结束时间',
  `stock`            smallint(6)  not null  default 0 comment '总库存',
  `sale_num`         smallint(6)  not null  default 0 comment '销量',
  `del_flag`         tinyint(1)   null      default 0,
  `status`           tinyint(1)   null      default 1 comment '状态： 1：启用  0： 禁用',
  `add_time`         datetime     not null,
  `update_time`      datetime     not null,
  `del_time`         int(11)      null      default 0,
  `activity_type`    tinyint(1)   not null  default '1' comment '活动类型：1：普通拼团，2：老带新团',
  `is_grouper_cheap` tinyint(1)   not null  default '0' comment '是否开启团长优惠：0：不开启，1：开启',
  `reward_coupon_id` VARCHAR(200) NULL   COMMENT '拼团失败发放优惠券',
  `share_config`     TEXT NULL   COMMENT '分享设置',
  primary key (`id`)
);

-- --  拼团活动产品规格定义表
drop table if exists `b2c_pin_group_product_define`;
create table `b2c_pin_group_product_define` (
  `id`              int(11)        not null auto_increment,
  `pin_activity_id` int(11)        not null comment '拼团定义ID',
  `product_id`      int(11)        not null comment '商品规格ID',
  `pin_group_price` decimal(10, 2) not null default 0.00 comment '拼团价',
  `stock`           smallint(6)    not null default 0 comment '库存',
  `sale_num`        smallint(6)    not null default 0 comment '销量',
  `grouper_price`   decimal(10, 2) not null default '0.00' comment '团长优惠价',
  primary key (`id`)
);


-- --  拼团活动参团明细表
drop table if exists `b2c_pin_group_list`;
create table `b2c_pin_group_list` (
  `id`              int(11)     not null auto_increment,
  `pin_activity_id` int(11)     not null comment '拼团活动定义ID',
  `goods_id`        int(11)     not null default 0,
  `group_id`        int(11)     not null default 0 comment '拼团ID',
  `user_id`         int(11)     not null,
  `is_grouper`      tinyint(1)  not null default 0 comment '是否为团长 1：是 0：否',
  `order_sn`        varchar(20) not null comment '订单编号',
  `status`          tinyint(1)  not null default 0 comment '0: 拼团中 1:拼团成功 2:拼团失败',
  `start_time`      datetime    not null comment '开团时间',
  `end_time`        datetime    not null comment '成团时间',
  primary key (`id`)
);

-- -- 用户充值记录表
-- drop table if exists `b2c_charge_money`;
create table `b2c_charge_money` (
  `id`           int(20)        not null auto_increment,
  `user_id`      int(20)                not null default '0' comment '用户id',
  `card_id`      int(20)                not null default '0' comment '会员卡id',
  `add_time`     timestamp              null     default null comment '充值时间',
  `charge`       decimal(10, 2)         null     default '0.00' comment '充值的钱',
  `count`        smallint(3)            null     default '0' comment '充值次数',
  `payment`      varchar(90)            not null comment '支付方式',
  `type`         tinyint(1)             not null default 0 comment '消费类型 0是普通卡 1限次卡',
  `reason`       varchar(191)           null     default null comment '充值原因',
  `prepay_id`    varchar(191)           null     default null comment '微信支付Id，用于发送模板消息',
  `message`      varchar(191)                    default '' comment '备注',
  `order_sn`     varchar(20)            not null default '',
  `order_status` tinyint(1)             not null default '0' comment '订单状态 0：待支付，1：已取消，2：已完成',
  `money_paid`   decimal(10, 2)         not null default '0.00' comment '订单应付金额',
  `charge_type`  tinyint(1)             not null default '0' comment '0按规则 1自定义',
  `card_no`      varchar(32) default '' not null comment '会员卡号',
  `ali_trade_no` varchar(60)            null     default '' comment '支付宝交易单号',
  `exchang_count`        smallint(3)            null     default '0' comment '兑换充值次数',
  primary key (`id`)
);


-- -- 用户会员卡消费记录表
-- drop table if exists `b2c_card_consumer`;
create table `b2c_card_consumer` (
  `id`       int(20)        not null  auto_increment,
  `user_id`  int(20)                not null  default '0' comment '用户id',
  `card_id`  int(20)                not null  default '0' comment '会员卡id',
  `add_time` timestamp              null      default null comment '充值时间',
  `money`    decimal(10, 2)         null      default '0.00' comment '消费的卡余额',
  `count`    smallint(3)            null      default '0' comment '消费次数',
  `type`     tinyint(1)             not null  default 0 comment '消费类型 0是普通卡 1限次卡',
  `reason`   varchar(191)           null      default null comment '消费原因',
  `message`  varchar(191)                     default '' comment '备注',
  `card_no`  varchar(32) default '' not null comment '会员卡号',
  `exchang_count`    smallint(3)            null      default '0' comment '兑换次数',
  `order_sn` varchar(20) null default '' comment '订单号',
  primary key (`id`)
);




-- --  表单页面
-- drop table if exists `b2c_form_page`;
create table `b2c_form_page` (
  `page_id`          int(10)  not null auto_increment comment '表单页ID',
  `shop_id`          int(11)          not null default 0 comment '店铺ID',
  `page_name`        varchar(60)      not null default '',
  `state`            tinyint(1)       not null default 0 comment '状态：0未发布，1已发布 2已关闭 3 已删除',
  `page_content`     longtext comment '页面内容，json格式存储',
  `form_cfg`         longtext comment '表单配置，json格式存储',
  `create_time`      timestamp                 default now(),
  `start_time`       timestamp        null comment '开始时间',
  `end_time`         timestamp        null comment '结束时间',
  `is_forever_valid` tinyint          not null default 1 comment '1永久有效，0期限内有效',
  `submit_num`       int              not null default 0 comment '反馈数量',
  primary key (`page_id`)
);

-- --  表单提交列表
-- drop table if exists `b2c_form_submit_list`;
create table `b2c_form_submit_list` (
  `submit_id`    int(10)  not null auto_increment,
  `page_id`      int(10)  not null,
  `shop_id`      int(11)          not null default 0 comment '店铺ID',
  `user_id`      int(11)          not null default 0 comment '用户Id',
  `open_id`      varchar(255) comment '微信OpenId',
  `nick_name`    varchar(255) comment '微信昵称',
  `create_time`  timestamp                 default now() comment '提交时间',
  `send_score`   int(6)           null comment '送积分',
  `send_coupons` varchar(200)     null comment '送优惠券',
  primary key (`submit_id`),
  key (`page_id`),
  key (`user_id`)
);


-- --  表单提交详情
-- drop table if exists `b2c_form_submit_details`;
create table `b2c_form_submit_details` (
  `rec_id`       int(10)  not null auto_increment,
  `page_id`      int(10)  not null,
  `submit_id`    int(10)  not null comment '表单提交ID，对应b2c_form_submit_list的submit_id',
  `user_id`      int(10)  not null,
  `module_name`  varchar(255) comment '模块名称',
  `module_type`  varchar(255) comment '模块类型',
  `module_value` text comment '模块的值',
  `create_time`  timestamp                 default now() comment '提交时间',
  `cur_idx`      varchar(32)      not null comment '装修模块保存id',
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
  `add_time`     timestamp  not null default CURRENT_TIMESTAMP comment '添加时间',
  key `type` (`type`) using btree,
  key `ref_date` (`ref_date`) using btree
);
-- --  消息模板配置表
-- drop table if exists `b2c_template_config`;
create table `b2c_template_config` (
  `id`             int(11)                                                       not null auto_increment,
  `name`           varchar(50) character set utf8mb4 collate utf8mb4_unicode_ci  not null comment '消息名称',
  `action`         tinyint(1)                                                    not null default 1 comment '消息类型： 1： 业务处理通知 2： 商家活动通知 3： 活动加入成功提醒',
  `title`          varchar(50) character set utf8mb4 collate utf8mb4_unicode_ci  not null comment '业务标题',
  `template_id`    int(11)                                                       null     default 0 comment '选择的模板ID',
  `content`        text character set utf8mb4 collate utf8mb4_unicode_ci         not null comment '业务内容',
  `page_link`      varchar(255) character set utf8mb4 collate utf8mb4_unicode_ci null     default null comment '页面链接',
  `send_condition` text character set utf8mb4 collate utf8mb4_unicode_ci         null comment '筛选发送人条件',
  `to_user`        text character set utf8mb4 collate utf8mb4_unicode_ci         null comment '发送人，逗号分隔，* 代表全部',
  `send_action`    tinyint(1)                                                    not null default 1 comment '发送方式： 1：立即发送  2： 持续发送  3：定时发送',
  `send_status`    tinyint(1)                                                    null     default 0 comment '发送完成 1： 完成',
  `start_time`     datetime                                                      null     default null comment '发送起始时间',
  `end_time`       datetime                                                      null     default null comment '发送终止时间',
  `del_flag`       tinyint(1)                                                    not null default 0 comment '删除标识',
  `add_time`       datetime                                                      not null,
  `update_time`    datetime                                                      not null,
  `del_time`       int(11)                                                       null     default 0,
  primary key (`id`)
);

-- --  消息模板表
-- drop table if exists `b2c_message_template`;
create table `b2c_message_template` (
  `id`      int(11)    not null auto_increment,
  `action`  tinyint(1) not null default 1 comment '消息类型： 1： 业务处理通知 2： 商家活动通知 3： 活动加入成功提醒',
  `content` text       not null,
  primary key (`id`)
);
-- -- 每个分销员统计信息
-- drop table if exists `b2c_user_total_fanli`;
create table `b2c_user_total_fanli` (
  `user_id`         int not null comment '会员ID',
  `mobile`          varchar(16)    default '' comment '会员手机号',
  `sublayer_number` int(11)        default '0' comment '子层分销员数量',
  `total_money`     decimal(10, 2) default '0.00' comment '累计获得佣金数',
  `can_money`       decimal(10, 2) default '0.00' comment '可用佣金余额',
  `blocked`         decimal(10, 2) default '0.00' comment '冻结佣金余额',
  `add_time`        timestamp,
  `update_time`     timestamp,
  primary key (`user_id`)
);
-- -- 每个子分销员数据汇总
-- drop table if exists `b2c_user_fanli_statistics`;
create table `b2c_user_fanli_statistics` (
  `user_id`               int(11) not null,
  `fanli_user_id`         int(11) comment '邀请人ID',
  `order_number`          int(11) comment '累积订单数量',
  `total_can_fanli_money` decimal(10, 2) default '0.00' comment '累计返利订单可计算返利总金额',
  `total_fanli_money`     decimal(10, 2) default '0.00' comment '用户累计返利佣金',
  `add_time`              timestamp,
  `update_time`           timestamp
);

-- -- 商品返利统计
-- drop table if exists `b2c_fanli_goods_statistics`;
create table `b2c_fanli_goods_statistics` (
  `prd_id`          int(11) not null,
  `prd_sn`          varchar(30) comment '规格编码',
  `goods_id`        int(11),
  `cat_id`          int(11) comment '分类ID',
  `sale_number`     int(11) comment '销量',
  `prd_total_fanli` decimal(10, 2) default '0.00' comment '商品返利总金额',
  `add_time`        timestamp,
  `update_time`     timestamp
);

-- --  店铺自定义分类
-- drop table if exists `b2c_sort`;
create table `b2c_sort` (
  `sort_id`     int(11)  not null auto_increment,
  `sort_name`   varchar(90)               default '',
  `parent_id`   smallint(5)      not null default '0',
  `level`       smallint(5)      not null default 0,
  `has_child`   tinyint(1)       not null default 0,
  `create_time` timestamp                 default now(),
  `sort_img`    varchar(191)     not null default '' comment '一级分类是头图 其他为分类图标',
  `img_link`    varchar(191)     null     default '' comment '图标或者头图链接',
  `first`       smallint(2)      not null default '0' comment '优先级',
  `type`        tinyint(1)       not null default 0 comment '0普通商家分类 1推荐分类',
  `sort_desc`   varchar(191)              default '',
  `keywords`    varchar(191)              default '',
  primary key (`sort_id`),
  key `parent_id` (`parent_id`)
);

-- --  砍价活动表
-- drop table if exists `b2c_bargain`;
create table `b2c_bargain` (
  `id`                 int(9)               not null auto_increment,
  `bargain_name`       varchar(120)                 not null comment '活动名称',
  `goods_id`           int(11)                      not null default '0' comment '商品ID',
  `start_time`         timestamp                    not null default '0000-00-00 00:00:00' comment '开始时间',
  `end_time`           timestamp                    not null default '0000-00-00 00:00:00' comment '结束时间',
  `expectation_number` int(6)                       not null default '0' comment '砍价预期人数',
  `expectation_price`  decimal(10, 2) default 0.00  null comment '预期砍价最低金额',
  `bargain_min`        float                                 default null comment '首次返利比例小',
  `bargain_max`        float                                 default null comment '首次返利比例大',
  `stock`              int(6)                                default '0' comment '库存',
  `sale_num`           int(6)                                default '0' comment '销售量',
  `mrking_voucher_id` VARCHAR(200)                  NULL,
  `status`             tinyint(1)                            default '0' comment '状态0可用1停用',
  `del_flag`           tinyint(1)                            default '0' comment '1删除',
  `add_time`           timestamp                    null     default '0000-00-00 00:00:00',
  `update_time`        timestamp                    null     default '0000-00-00 00:00:00',
  `del_time`           timestamp                    null     default '0000-00-00 00:00:00',
  `reward_coupon_id` VARCHAR(200) NULL   COMMENT '砍价失败发放优惠券',
  `share_config`      TEXT NULL   COMMENT '分享设置',
  primary key (`id`),
  key `goods_id` (`goods_id`)
);
-- --  砍价发起表
-- drop table if exists `b2c_bargain_record`;
create table `b2c_bargain_record` (
  `id`            int(11)  not null auto_increment,
  `user_id`       int(9)           not null default '0' comment '用户ID',
  `bargain_id`    int(9)           not null default '0' comment '活动ID',
  `goods_id`      int(9)                    default '0' comment '商品ID',
  `prd_id`        int(9)           not null default '0' comment '产品ID',
  `goods_price`   decimal(10, 2)   not null default '0.00' comment '商品价格',
  `bargain_money` decimal(10, 2)            default '0.00' comment '已砍价格',
  `user_number`   int(6)                    default '0' comment '砍价人数',
  `status`        tinyint(1)                default '0' comment '0砍价中1成功2失败（成功即扣库存）',
  `is_ordered`    int(1)                    default '0' comment '是否下单',
  `order_sn`      varchar(20)               default '0' comment '订单号',
  `add_time`      timestamp        null     default '0000-00-00 00:00:00',
  `update_time`   timestamp        null     default '0000-00-00 00:00:00',
  `del_flag`      tinyint(1)                default '0',
  `del_time`      timestamp        null     default '0000-00-00 00:00:00',
  primary key (`id`),
  key `user_bargain` (`user_id`, `bargain_id`),
  key `bargain_id` (`bargain_id`)
);

-- --  砍价用户表
-- drop table if exists `b2c_bargain_user_list`;
create table `b2c_bargain_user_list` (
  `id`            int(11)  not null auto_increment,
  `record_id`     int(11)          not null default '0' comment '对应b2c_bargain_record表id',
  `user_id`       int(9)           not null default '0',
  `bargain_money` decimal(10, 2)            default '0.00',
  `add_time`      timestamp        null     default '0000-00-00 00:00:00' on update CURRENT_TIMESTAMP,
  primary key (`id`),
  key `bargain_user` (`record_id`, `user_id`)
);


-- --  海报表
-- drop table if exists `b2c_pictorial`;
create table `b2c_pictorial` (
  `id`          int(11)                          not null auto_increment,
  `action`      tinyint(1)                       not null comment '海报类型： 1：普通 2 ：拼团 3：砍价 4：表单 5:拼团分享',
  `path`        varchar(500) collate utf8mb4_bin not null comment '海报路径',
  `rule`        text collate utf8mb4_bin         null comment '生成触发规则',
  `user_id`     int(11)                          not null default '0',
  `identity_id` int(11)                          not null comment '关联ID： 例如：goods_id, page_id',
  `add_time`    datetime                         not null comment '生成时间',
  `update_time` datetime                         not null default CURRENT_TIMESTAMP comment '更新时间',
  `del_flag`    tinyint(4)                       not null default '0' comment '删除标记： 1：删除',
  `del_time`    int(11)                          null     default '0',
  primary key (`id`)
);
--  优惠券活动表
-- DROP TABLE IF EXISTS `b2c_coupon_activity`;
create table `b2c_coupon_activity` (
  `id`                int(11)               not null auto_increment,
  `activity_action`   tinyint(1) default 1  null comment '活动类型：1：活动送券 2：大转盘抽奖',
  `action`            tinyint(1)            not null default '1' comment '针对用户群体： 1: 新用户 2: 全部用户',
  `name`              varchar(50)           not null comment '活动名称',
  `title`             varchar(100)          not null comment '宣传语',
  `bg_action`         tinyint(4)            not null default '1' comment '背景图',
  `start_date`        datetime              not null comment '有效期-起始',
  `end_date`          datetime              not null comment '有效期-结束',
  `mrking_voucher_id` varchar(500)          not null comment '活动优惠券，逗号分隔',
  `status`            tinyint(1)            not null default '1' comment '状态： 1: 正常 0: 关闭',
  `add_time`          datetime              not null,
  `update_time`       datetime              not null default CURRENT_TIMESTAMP,
  `del_flag`          tinyint(4)            not null default '0',
  `del_time`          int(11)               not null default '0',
  primary key (`id`)
);
--  活动送券记录表
-- DROP TABLE IF EXISTS `b2c_coupon_activity_record`;
create table `b2c_coupon_activity_record` (
  `id`                int(11)  not null auto_increment,
  `activity_id`       int(11)  not null comment '活动ID',
  `user_id`           int(11)  not null,
  `receive_time`      datetime not null comment '领取时间',
  `mrking_voucher_id` varchar(500)      default null comment '已领取的优惠券',
  primary key (`id`)
);

-- 积分商城活动定义表
-- DROP TABLE IF EXISTS `b2c_integral_mall_define`;
create table `b2c_integral_mall_define` (
  `id`               int(10)                         not null auto_increment,
  `goods_id`         int(11)                                 not null comment '商品ID',
  `max_exchange_num` smallint(6)                             not null default '0' comment '每个用户最大可兑换数量',
  `start_time`       datetime                                not null comment '活动起始时间',
  `end_time`         datetime                                not null comment '活动终止时间',
  `status`           tinyint(1)                                       default '1' comment '1: 正常 0：禁用',
  `add_time`         datetime                                not null,
  `update_time`      datetime                                not null on update CURRENT_TIMESTAMP,
  `del_flag`         tinyint(1)                                       default '0',
  `del_time`         int(11)                                          default null,
  `name`             varchar(100) collate utf8mb4_unicode_ci not null comment '活动名称',
  `share_config`     TEXT NULL   COMMENT '分享设置',
  primary key (`id`)
);

-- 积分商城产品定义表
-- DROP TABLE IF EXISTS `b2c_integral_mall_product`;
create table `b2c_integral_mall_product` (
  `id`                      int(10)  not null auto_increment,
  `integral_mall_define_id` int(11)          not null comment '积分商城活动定义表ID',
  `product_id`              int(11)          not null comment '规格产品ID',
  `score`                   int(11)          not null comment '积分数',
  `stock`                   smallint(6)      not null comment '库存数',
  `money`                   decimal(10, 2)   not null comment '兑换现金',
  primary key (`id`)
);

-- 积分商城用户兑换记录表
-- DROP TABLE IF EXISTS `b2c_integral_mall_record`;
create table `b2c_integral_mall_record` (
  `id`                      int(10)  not null              auto_increment,
  `integral_mall_define_id` int(11)          not null comment '积分商城活动定义表ID',
  `order_sn`                varchar(20) collate utf8mb4_unicode_ci default null comment '订单编号',
  `user_id`                 int(11)          not null comment '用户ID',
  `goods_id`                int(11)          not null comment '商品ID',
  `product_id`              int(11)          not null comment '产品规格ID',
  `score`                   int(11)          not null comment '消费积分',
  `number`                  smallint(6)      not null comment '兑换数量',
  `add_time`                datetime         not null comment '兑换时间',
  `money`                   decimal(10, 2)   not null comment '消耗现金',
  primary key (`id`)
);

--  等级卡升级记录
-- DROP TABLE IF EXISTS `b2c_card_upgrade`;
create table `b2c_card_upgrade` (
  `id`              int(11)      not null auto_increment,
  `user_id`         int(11)      not null comment '用户ID',
  `old_card_id`     int(11)      not null comment '升级前卡ID',
  `new_card_id`     int(11)      not null comment '升级后卡ID',
  `old_grade`       varchar(20)  not null comment '升级前卡等级',
  `new_grade`       varchar(20)  not null comment '升级后卡等级',
  `old_card_name`   varchar(20)  not null,
  `new_card_name`   varchar(20)  not null,
  `grade_condition` varchar(200) null     default '' comment '条件',
  `operate`         varchar(200) null     default '' comment '操作备注',
  `in_time`         datetime     not null comment '升级时间',
  primary key (`id`)
);

-- --  分销原申请记录
-- DROP TABLE IF EXISTS `b2c_distributor_apply`;
create table `b2c_distributor_apply` (
  `id`                int(11)  not null auto_increment,
  `user_id`           int(11)  not null default '0',
  `add_time`          timestamp        null     default null,
  `status`            tinyint(2)        default '0',
  `msg`               text             null comment '审核内容',
  `update_time`       timestamp        not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flag`          tinyint(2)                default '0',
  `activation_fields` varchar(1000)    null comment '审核校验',
  `config_fields`     varchar(500)     null comment '审核字段',
  primary key (`id`),
  key `user_id` (`user_id`)
);

-- --  规格对应会员价
-- DROP TABLE IF EXISTS `b2c_grade_prd`;
create table `b2c_grade_prd` (
  `id`          int(11)         not null auto_increment,
  `prd_id`      int(10)         not null,
  `goods_id`    int(10)         not null,
  `shop_id`     int(11)                 not null default '0',
  `grade_price` decimal(10, 2)  not null default '0.00',
  `grade`       varchar(65)             not null default '' comment '会员卡等级',
  primary key (`id`),
  key `prd_id` (`prd_id`)
);
-- --  模板发送记录
-- DROP TABLE IF EXISTS `b2c_service_message_record`;
create table `b2c_service_message_record` (
  `user_id`           int(11)      default null comment '用户ID',
  `mobile`            varchar(20)  default null comment '用户手机号',
  `request_action`    tinyint(4)   default '0' comment '请求类型：100:短信平台',
  `identity_id`       varchar(50)  default null comment '关联其他表：如：外部请求requestId',
  `template_platform` tinyint(1)   default '1' comment '模板平台：1： 小程序 2：公众号',
  `template_content`  text                  null comment '模板内容',
  `response_code`     varchar(20)  default null comment '响应code 0:成功 >0 其他',
  `response_msg`      varchar(500) default null comment '响应结果',
  `path`              varchar(50)           null comment '小程序路径',
  `path_query`        varchar(500)          null comment '小程序路径参数',
  `send_status`       tinyint(1) default 0  null comment '1: 发送成功  0：未知',
  `is_visit`          tinyint(1) default 0  null comment '是否已点击访问 1：是 0： 否',
  `visit_time`        datetime              null comment '访问时间',
  `template_type`     tinyint(2)            null comment '模板类型 7：商家自定义',
  `link_identity`     varchar(50)           null comment '模板消息关联Id',
  `add_time`          datetime              null,
  index `user_request` (`user_id`, `request_action`, `template_platform`)
);
-- --  接口请求记录
-- DROP TABLE IF EXISTS `b2c_service_request`;
create table `b2c_service_request` (
  `id`               BIGINT(20)                  NOT NULL AUTO_INCREMENT,
  `request_id`       varchar(50)               default null comment '请求requestId',
  `service_name`     varchar(50)      not null comment '服务名',
  `request_content`  text comment '请求内容',
  `request_time`     datetime                  default null comment '请求时间',
  `response_time`    datetime                  default null comment '响应时间',
  `response_content` text comment '响应内容',
  `ip`               varchar(100)              default null comment '请求ip',
  primary key (`id`),
  index `request_id` (`request_id`)
);

-- --  抽奖活动表
-- DROP TABLE IF EXISTS `b2c_lottery`;
create table `b2c_lottery` (
  `id`                 int(9)                          not null auto_increment,
  `lottery_name`       varchar(120) collate utf8mb4_unicode_ci not null comment '抽奖名称',
  `start_time`         timestamp                               not null default '0000-00-00 00:00:00' comment '开始时间',
  `end_time`           timestamp                               not null default '0000-00-00 00:00:00' comment '结束时间',
  `lottery_explain`    varchar(299) collate utf8mb4_unicode_ci not null comment '抽奖说明',
  `free_chances`       int(8)                                           default null comment '免费抽奖次数',
  `can_share`          tinyint(2)                                       default null comment '是否分享获得次数',
  `share_chances`      int(8)                                           default null comment '分享最多获得次数',
  `can_use_score`      tinyint(2)                                       default null comment '是否可以积分抽奖',
  `score_per_chance`   int(8)                                           default null comment '抽奖一次使用积分',
  `score_chances`      int(8)                                           default null comment '积分最多抽奖次数',
  `no_award_score`     int(8)                                           default null comment '未中奖奖励积分',
  `no_award_image`     varchar(199) collate utf8mb4_unicode_ci          default null comment '未中奖图片',
  `no_award_icon`      varchar(20) collate utf8mb4_unicode_ci           default null comment '未中奖提示',
  `first_award`        varchar(500) collate utf8mb4_unicode_ci          default null comment '一等奖设置（json）',
  `first_award_times`  int(8)                                           default null comment '中奖数',
  `second_award`       varchar(500) collate utf8mb4_unicode_ci          default null comment '二等奖设置（json）',
  `second_award_times` int(8)                                           default null comment '中奖数',
  `third_award`        varchar(500) collate utf8mb4_unicode_ci          default null comment '三等奖设置（json）',
  `third_award_times`  int(8)                                           default null comment '中奖数',
  `fourth_award`       varchar(500) collate utf8mb4_unicode_ci          default null comment '四等奖设置（json）',
  `fourth_award_times` int(8)                                           default null comment '中奖数',
  `add_time`           timestamp                               null     default '0000-00-00 00:00:00' comment '添加时间',
  `status`             tinyint(2)                                       default '0' comment '状态：1停用',
  `del_flag`           tinyint(2)                                       default '0' comment '1删除',
  `update_time`        timestamp                               not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
  primary key (`id`)
);

-- --  抽奖活动表
-- DROP TABLE IF EXISTS `b2c_lottery_share`;
create table `b2c_lottery_share` (
  `id`              int(10)  not null auto_increment,
  `user_id`         int(10)  not null comment '用户编号',
  `lottery_id`      int(10)  not null comment '抽奖编号',
  `share_times`     int(8)                    default '0' comment '分享次数',
  `use_share_times` int(8)                    default '0' comment '抽奖次数',
  `use_score_times` int(8)                    default '0',
  `add_time`        timestamp        null     default '0000-00-00 00:00:00' comment '添加时间',
  `update_time`     timestamp        not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
  primary key (`id`),
  key `user_lottery` (`user_id`, `lottery_id`)
);

-- --  抽奖活动表
-- DROP TABLE IF EXISTS `b2c_lottery_record`;
create table `b2c_lottery_record` (
  `id`             int(11)      not null           auto_increment,
  `user_id`        int(10)      not null comment '用户编号',
  `lottery_id`     int(10)      not null comment '抽奖编号',
  `lottery_source` tinyint(1) default 0 null comment '抽奖来源:1.登录2.支付',
  `lottery_act_id` int(10) default 0    null comment '抽奖来源id',
  `chance_source`  tinyint(2)                              default null comment '抽奖机会来源',
  `lottery_cost`   varchar(32) collate utf8mb4_unicode_ci  default null comment '抽奖花费积分',
  `lottery_grade`  tinyint(2)                              default null comment '中奖等级：0未中奖，1一等奖，2二等奖，3三等奖，4四等奖',
  `lottery_type`   tinyint(2)                              default null comment '奖励类型',
  `lottery_award`  varchar(60) collate utf8mb4_unicode_ci  default null comment '获得奖励',
  `award_info`     varchar(500) collate utf8mb4_unicode_ci default null comment '中奖信息',
  `add_time`       timestamp            not null           default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '抽奖时间',
  `prd_id`        int(10)       NOT NULL COMMENT '商品（规格）编号',
  `present_status` tinyint(1) DEFAULT '0' COMMENT '赠品状态:0.待领取，1：已领取，2.已过期',
  `order_sn` varchar(60) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联订单',
  `lottery_expired_time` timestamp  not null  default now() comment '赠品过期时间',
  primary key (`id`),
  key `user_lottery` (`user_id`, `lottery_id`),
  key `lottery_id` (`lottery_id`)
);

--  用户足迹
-- DROP TABLE IF EXISTS `b2c_footprint_record`;
create table `b2c_footprint_record` (
  `id`          int(11)  not null auto_increment,
  `goods_id`    int(11)  not null comment '商品ID',
  `user_id`     int(11)  not null,
  `update_time` datetime not null comment '浏览时间',
  `count` int(11) DEFAULT 1 NULL COMMENT '浏览次数',
  `type` TINYINT(2) DEFAULT 0  NULL   COMMENT '0 老用户 1新用户',
  primary key (`id`)
);


-- -- 返利策略
-- DROP TABLE IF EXISTS `b2c_distribution_strategy`;
create table `b2c_distribution_strategy` (
  `id`                 int(10)                         not null auto_increment,
  `strategy_name`      varchar(120) collate utf8mb4_unicode_ci not null comment '策略名称',
  `strategy_level`     tinyint(3)                              not null comment '策略等级',
  `start_time`         timestamp                               not null default '0000-00-00 00:00:00' comment '开始时间',
  `end_time`           timestamp                               not null default '0000-00-00 00:00:00' comment '结束时间',
  `self_purchase`      tinyint(1)                                       default '0' comment '自购返利',
  `cost_protection`    tinyint(1)                              not null default '0' comment '成本保护',
  `fanli_ratio`        float                                   not null default '0' comment '返利比例（%的系数）',
  `rebate_ratio`       float default 0                         null comment '间接',
  `fanli_ratio_2`      float                                            default '0' comment '二级返利比例（%的系数）',
  `rebate_ratio_2`     float default 0                         null comment '间接',
  `fanli_ratio_3`      float                                            default '0' comment '三级返利比例（%的系数）',
  `rebate_ratio_3`     float default 0                         null comment '间接',
  `fanli_ratio_4`      float                                            default '0' comment '四级返利比例（%的系数）',
  `rebate_ratio_4`     float default 0                         null comment '间接',
  `fanli_ratio_5`      float                                            default '0' comment '五级返利比例（%的系数）',
  `rebate_ratio_5`     float default 0                         null comment '间接',
  `recommend_type`     tinyint(4)                                       default null comment '0:全部商品1:部分商品',
  `recommend_goods_id` TEXT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci NULL   COMMENT '返利商品ids',
  `recommend_cat_id`   TEXT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci NULL   comment '返利分类ids',
  `status`             tinyint(2)                                       default '0' comment '1停用',
  `add_time`           timestamp                               not null default '0000-00-00 00:00:00' comment '添加时间',
  `del_flag`           tinyint(2)                                       default '0' comment '1删除',
  `update_time`        timestamp                               not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '最后修改时间',
  `recommend_sort_id`  varchar(300) collate utf8mb4_unicode_ci          default null comment '返利商家分类ids',
  `send_coupon`       TINYINT(1) DEFAULT 0  NULL   COMMENT '赠送优惠券',
  primary key (`id`)
);

-- -- 推荐商品
-- DROP TABLE IF EXISTS `b2c_recommend_goods`;
create table `b2c_recommend_goods` (
  `id`                 int(10)                         not null auto_increment comment '编号',
  `recommend_name`     varchar(120) collate utf8mb4_unicode_ci not null comment '推荐名称',
  `recommend_type`     tinyint(2)                              not null default '0' comment '0.全部商品1.部分商品',
  `recommend_goods_id` varchar(299)                            null comment '推荐商品ID',
  `recommend_cat_id`   varchar(299)                            null comment '推荐分类ID',
  `recommend_use_page` varchar(299)                            not null default '' comment '推荐使用页面',
  `add_time`           timestamp                               null     default '0000-00-00 00:00:00' comment '添加时间',
  `status`             tinyint(2) default 0                    null comment '状态1停用',
  `update_time`        timestamp                               null     default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
  `del_flag`           tinyint(2) default 0                    null comment '1删除',
  `recommend_sort_id`  varchar(299)                            null comment '推荐商家分类ID',
  primary key (`id`)
);


-- --  加价购
-- DROP TABLE IF EXISTS `b2c_purchase_price_define`;
create table `b2c_purchase_price_define` (
  `id`                  int(11)                                 not null auto_increment,
  `name`                varchar(255) collate utf8mb4_unicode_ci not null comment '活动名称',
  `level`               smallint(6)                             not null default '0' comment '优先级',
  `max_change_purchase` smallint(6)                                      default null comment '最大换购数',
  `goods_id`            text collate utf8mb4_unicode_ci comment '主商品',
  `start_time`          datetime                                         default null comment '开始时间',
  `end_time`            datetime                                         default null comment '结束时间',
  `status`              tinyint(1)                                       default '1' comment '状态 1: 启用 0:禁用',
  `add_time`            datetime                                         default null,
  `update_time`         datetime                                         default null on update CURRENT_TIMESTAMP,
  `del_flag`            tinyint(4)                                       default '0',
  `del_time`            int(11)                                          default '0',
  primary key (`id`)
);
drop table if exists `b2c_purchase_price_rule`;

create table `b2c_purchase_price_rule` (
  `id`                int(11) not null auto_increment,
  `purchase_price_id` int(11) not null comment '加价购活动ID',
  `full_price`        decimal(10, 2)   default null comment '满多少钱',
  `purchase_price`    decimal(10, 2)   default null comment '换购多少钱的商品',
  `product_id`        text collate utf8mb4_unicode_ci comment '换购商品',
  `del_flag`          tinyint(1)       default '0',
  `del_time`          int(11)          default '0',
  primary key (`id`)
);

--  秒杀定义
-- DROP TABLE IF EXISTS `b2c_sec_kill_define`;
create table `b2c_sec_kill_define` (
  `sk_id`         int(11)                                 not null auto_increment comment '秒杀活动ID',
  `shop_id`       int(11)                                 not null comment '店铺ID',
  `goods_id`      int(11)                                 not null comment '商品ID',
  `name`          varchar(100) collate utf8mb4_unicode_ci not null comment '活动名称',
  `limit_amount`  smallint(6)                             not null comment '每人限购数量',
  `limit_paytime` smallint(6)                             not null comment '规定的有效支付时间',
  `start_time`    datetime                                not null comment '开始时间',
  `end_time`      datetime                                not null comment '结束时间',
  `stock`         smallint(6)                             not null default '0' comment '总库存',
  `sale_num`      smallint(6)                             not null default '0' comment '销量',
  `del_flag`      tinyint(1)                                       default '0',
  `status`        tinyint(1)                                       default '1' comment '状态： 1：启用  0： 禁用',
  `free_freight`  tinyint(1)                                       default '1' comment '是否免运费： 1：免运费  0： 原先商品的运费',
  `add_time`      datetime                                not null,
  `update_time`   datetime                                not null,
  `del_time`      int(11)                                          default '0',
  `card_id`       text  COLLATE utf8mb4_unicode_ci COMMENT '专属会员卡',
  `share_config` TEXT NULL   COMMENT '分享配置',
  primary key (`sk_id`)
);


--  参与秒杀活动记录
-- DROP TABLE IF EXISTS `b2c_sec_kill_list`;
create table `b2c_sec_kill_list` (
  `sklog_id` int(11)                                not null auto_increment comment '秒杀活动商品购买记录ID',
  `sk_id`    int(11)                                not null comment '秒杀活动定义ID',
  `goods_id` int(11)                                not null default '0',
  `user_id`  int(11)                                not null comment '参与秒杀活动用户ID',
  `order_sn` varchar(20) collate utf8mb4_unicode_ci not null comment '订单编号',
  `del_flag` tinyint(1)                                      default '0',
  `add_time` timestamp                              not null default CURRENT_TIMESTAMP comment '参与时间',
  primary key (`sklog_id`)
);

--  参与秒杀规格商品价格
-- DROP TABLE IF EXISTS `b2c_sec_kill_product_define`;
create table `b2c_sec_kill_product_define` (
  `skpro_id`       int(11)        not null auto_increment comment '秒杀商品规格ID',
  `sk_id`          int(11)        not null comment '秒杀活动定义ID',
  `product_id`     int(11)        not null comment '商品规格ID',
  `sec_kill_price` decimal(10, 2) not null default '0.00' comment '秒杀价',
  `stock`          smallint(6)    not null default '0' comment '库存',
  `sale_num`       smallint(6)    not null default '0' comment '销量',
  `total_stock`    smallint(6)    not null default '0' comment '总库存',
  primary key (`skpro_id`)
);

-- -- 用户访问商品记录表
-- drop table if exists `b2c_user_goods_record`;
create table `b2c_user_goods_record` (
  `id`            BIGINT(20)  not null              auto_increment,
  `user_id`       INT(11)      not null                 default '0' comment '用户id',
  `goods_id`      INT(11)     not null                  default '0' comment '商品id',
  `active_id`     INT(11)      null                     default '0' comment '活动id',
  `active_type`   smallint(3)      null                  default '0' comment '活动类型',
  `add_time`      timestamp        null                  default null comment '时间',
  `user_ip`       varchar(64)      null                  default null comment '用户ip',
  `province_code` varchar(20) collate utf8mb4_unicode_ci default null comment '省',
  `province`      varchar(20) collate utf8mb4_unicode_ci default null comment '省',
  `city_code`     varchar(20) collate utf8mb4_unicode_ci default null comment '市',
  `city`          varchar(20) collate utf8mb4_unicode_ci default null comment '市',
  `district_code` varchar(20) collate utf8mb4_unicode_ci default null comment '区',
  `district`      varchar(20) collate utf8mb4_unicode_ci default null comment '区',
  `lat`           varchar(64)      null                  default null comment '经度',
  `lng`           varchar(64)      null                  default null comment '纬度',
  `count`         smallint(3)      null                  default '0' comment '次数',
  primary key (`id`),
  key `add_time` (`add_time`) using btree
);

-- -- 用户添加购物车商品记录表
-- drop table if exists `b2c_user_cart_record`;
create table `b2c_user_cart_record` (
  `id`            int(20)  not null              auto_increment,
  `user_id`       INT(11)      not null              default '0' comment '用户id',
  `goods_id`      INT(11)      not null              default '0' comment '商品id',
  `prd_id`        INT(11)      not null              default '0' comment '规格id',
  `num`           smallint(3)      not null              default '1' comment '件数',
  `is_delete`     smallint(3)      null                  default '0' comment '0：添加，1：删除标记',
  `add_time`      timestamp        null                  default null comment '时间',
  `user_ip`       varchar(64)      null                  default null comment '用户ip',
  `province_code` varchar(20) collate utf8mb4_unicode_ci default null comment '省',
  `province`      varchar(20) collate utf8mb4_unicode_ci default null comment '省',
  `city_code`     varchar(20) collate utf8mb4_unicode_ci default null comment '市',
  `city`          varchar(20) collate utf8mb4_unicode_ci default null comment '市',
  `district_code` varchar(20) collate utf8mb4_unicode_ci default null comment '区',
  `district`      varchar(20) collate utf8mb4_unicode_ci default null comment '区',
  `lat`           varchar(64)      null                  default null comment '经度',
  `lng`           varchar(64)      null                  default null comment '纬度',
  `count`         smallint(3)      null                  default '0' comment '次数',
  primary key (`id`),
  key `add_time` (`add_time`) using btree,
  key `goods_add_time` (`goods_id`, `add_time`) using btree,
  key `user_add_time` (`user_id`, `add_time`) using btree
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
  `goods_user_visit`  int(11)            default null comment 'UV',
  `goods_visit`       int(11)            default null comment 'goods pv',
  `cart_user_number`  int(11)            default null comment '加购人数',
  `cart_goods_number` int(11)            default null comment '加购件数',
  `paid_goods_number` int(11)            default null comment '付款人数',
  `paid_user_number`  int(11)            default null comment '付费用户数',
  `add_time`          timestamp null     default null,
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
  `add_time`          timestamp null     default null,
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
  `add_time`             timestamp      not null comment '统计时间',
  primary key (`id`),
  key `ref_type` (`ref_date`, `type`) using btree
);

-- 标签用户下单统计
-- drop table if exists `b2c_distribution_tag`;
create table `b2c_distribution_tag` (
  `ref_date`         date                                   default null comment '日期',
  `type`             tinyint(2)                             default null comment '1,7,30',
  `tag`              varchar(50) collate utf8mb4_unicode_ci default null comment '标签',
  `pay_order_num`    int(11)                                default null comment '付款订单数',
  `pay_order_money`  decimal(10, 2)                         default null comment '付款金额',
  `pay_user_num`     int(11)                                default null comment '付款人数',
  `pay_goods_number` int(11)                                default null comment '付款商品件数',
  `has_mobile_num`   int(11)                                default null comment '下单有手机号的用户',
  `has_user_num`     int null                               default 0 comment '用户数',
  `add_time`         datetime                               default null comment '添加时间',
  key `date_type` (`ref_date`, `type`) using btree
);
-- 交易订单地区分布
-- drop table if exists `b2c_distribution_order`;
create table `b2c_distribution_order` (
  `ref_date`        char(7) collate utf8mb4_unicode_ci     default null comment '2018-07',
  `province`        varchar(20) collate utf8mb4_unicode_ci default null comment '省',
  `city`            varchar(20) collate utf8mb4_unicode_ci default null comment '市',
  `district`        varchar(20) collate utf8mb4_unicode_ci default null comment '区',
  `province_code`   mediumint(10)  not null        default '0' comment '省份编号',
  `city_code`       mediumint(10)  not null        default '0' comment '城市编号',
  `district_code`   mediumint(10)  not null        default '0' comment '区县编号',
  `pay_order_money` decimal(10, 2)                         default null comment '付款金额',
  `pay_user_num`    int(11)                                default null comment '付款人数',
  `uv`              int(11)                                default null comment '访客数',
  `uv_pay_ratio`    decimal(4, 2)                          default null comment '转化率',
  `order_num`       int(11)                                default null comment '订单数',
  `add_time`        datetime                               default null comment '添加时间',
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
  `add_time`         datetime       default null comment '添加时间',
  key `ref_date` (`ref_date`) using btree,
  key `ref_hour` (`ref_date`, `hour`) using btree
);

-- goods备份
-- drop table if exists `b2c_goods_bak`;
create table `b2c_goods_bak` (
  `id`           int(10)                not null auto_increment,
  `bak_date`     date                                    default null comment '备份日期：例2018-09-05',
  `goods_id`     int(8)                         not null,
  `shop_id`      int(11)                        null     default '0' comment '店铺ID',
  `cat_id`       int(5)                 not null default '0',
  `sort_id`      int(11)                        null     default '0',
  `market_price` decimal(10, 2)         null     default '0.00',
  `shop_price`   decimal(10, 2)         not null default '0.00',
  `goods_number` int(11)                        not null default '0' comment '库存',
  `is_on_sale`   tinyint(1)             null     default '1' comment '是否在售，1在售，0下架',
  `goods_type`   tinyint(2)  default 0  null comment '商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品',
  primary key (`id`)
);

-- --  商品规格组合的产品表备份 `b2c_goods_spec_product_bak`
-- drop table if exists `b2c_goods_spec_product_bak`;
create table `b2c_goods_spec_product_bak` (
  `id`               int(10)         not null auto_increment,
  `bak_date`         date                             default null comment '备份日期：例2018-09-05',
  `prd_id`           int(10)         not null,
  `shop_id`          int(11)                 not null default '0',
  `goods_id`         int(10)         not null default '0',
  `prd_price`        decimal(10, 2)  not null default '0.00',
  `prd_market_price` decimal(10, 2)  not null default '0.00' comment '市场价',
  `prd_number`       int(11)                 not null default '1' comment '当前规格组合产品库存',
  primary key (`id`)
);
-- 小程序初始化场景值
-- drop table if exists `b2c_mp_scene_record`;
create table `b2c_mp_scene_record` (
  `user_id`       int                                  null comment '用户ID',
  `path`          varchar(50) comment '打开小程序的路径',
  `path_query`    varchar(500) comment '打开小程序的query',
  `scene`         smallint comment '场景值',
  `share_ticket`  varchar(500) comment '转发的ticket',
  `referrer_info` text comment 'referrer信息',
  `count`         int                                  null comment '记录次数',
  `add_time`      datetime,
  `update_time`   datetime on update CURRENT_TIMESTAMP null,
  index `user_path` (`user_id`, `path`, `scene`),
  index `scene_add_time` (`scene`, `add_time`),
  index `path_add_time` (`path`, `add_time`)
);

--  分享记录
-- DROP TABLE IF EXISTS `b2c_share_record`;
create table `b2c_share_record` (
  `id`            int(11)  not null auto_increment,
  `activity_id`   int(11)  null comment '活动id或商品id',
  `user_id`       int(11)  null,
  `activity_type` int(11)  null comment '活动类型',
  `add_time`      datetime null comment '浏览时间',
  `count`         int(11)  null comment '次数',
  primary key (`id`)
);

--  商品标签
-- DROP TABLE IF EXISTS `b2c_goods_label`;
create table `b2c_goods_label` (
  `id`           int(11)                                 not null auto_increment comment '标签ID',
  `name`         varchar(100) collate utf8mb4_unicode_ci not null comment '标签名称',
  `goods_detail` tinyint(1)                                       default '0' comment '是否应用于商品详情页： 1：是  0： 否',
  `goods_list`   tinyint(1)                                       default '0' comment '是否应用于商品列表页： 1：是  0： 否',
  `is_all`       tinyint(1)                                       default '0' comment '是否适用于全部商品： 1：是  0： 否',
  `level`        smallint(5)                             not null default '0',
  `add_time`     datetime                                not null default CURRENT_TIMESTAMP,
  `update_time`  datetime                                not null default CURRENT_TIMESTAMP,
  `del_time`     datetime                                         default null,
  `del_flag`     int(1)                                  not null default '0',
  `list_pattern` smallint(5)                             not null default '0' comment '列表样式',
  `goods_select` tinyint(1)                                       default '0' comment '是否应用于商品筛选页： 1：是  0： 否',
  primary key (`id`)
);

--  商品标签对关联表
-- DROP TABLE IF EXISTS `b2c_goods_label_couple`;
create table `b2c_goods_label_couple` (
  `id`       int(11)                                 not null auto_increment comment '标签ID',
  `label_id` varchar(100) collate utf8mb4_unicode_ci not null comment '标签ID',
  `gta_id`   int(11)                                          default '0' comment '商品或类型ID',
  `type`     tinyint(1)                                       default '0' comment '标签关联类型： 1：关联商品 2：关联类型 3：全部商品',
  primary key (`id`)
);
-- 限时减价活动
-- DROP TABLE IF EXISTS `b2c_reduce_price`;
create table `b2c_reduce_price` (
  `id`                int(11)                                not null auto_increment,
  `name`              varchar(50) collate utf8mb4_unicode_ci not null comment '活动名称',
  `start_time`        datetime                               not null comment '开始时间',
  `end_time`          datetime                               not null comment '结束日期',
  `period_action`     tinyint(1)                             null     default '1' comment '周期类型：1:每天 2:每月 3:每周',
  `point_time`        varchar(20) collate utf8mb4_unicode_ci null comment '时间段',
  `extend_time`       tinyint(20)                                     default null comment '每月第几日；每周第几天',
  `batch_discount`    tinyint(1)                                      default '0' comment '批量打几折',
  `batch_reduce`      decimal(10, 2)                                  default null comment '批量减多少',
  `batch_final_price` decimal(10, 2)                                  default null comment '批量折后价',
  `is_batch_integer`  tinyint(1)                                      default '0' comment '是否批量取整',
  `status`            tinyint(1)                                      default '1' comment '状态：1：启用 0：禁用',
  `add_time`          datetime                                        default null,
  `update_time`       datetime                                        default null on update CURRENT_TIMESTAMP,
  `del_flag`          tinyint(1)                                      default '0',
  `del_time`          int(11)                                         default null,
  `limit_amount`      int(11)                                         default 0,
  `add_type`          tinyint(1)                                      default '0' comment '新建方式：0正常，1批量改价，2批量加价率',
  `share_config`      TEXT NULL   COMMENT '分享设置',
  primary key (`id`)
);
--  限时减价活动商品
-- DROP TABLE IF EXISTS `b2c_reduce_price_goods`;
create table `b2c_reduce_price_goods` (
  `id`              int(11) not null auto_increment,
  `reduce_price_id` int(11) not null comment '限时减价活动ID',
  `goods_id`        int(11) not null comment '商品ID',
  `discount`        decimal(10, 2)   default null comment '打几折',
  `reduce_price`    decimal(10, 2)   default null comment '减多少钱',
  `goods_price`     decimal(10, 2)   default null comment '折后价格',
  `is_checked`      tinyint(1)       default null comment '是否选中',
  primary key (`id`),
  UNIQUE KEY `reduce_goods` (`reduce_price_id`, `goods_id`)
);
--  限时减价活动商品规格
-- DROP TABLE IF EXISTS `b2c_reduce_price_product`;
create table `b2c_reduce_price_product` (
  `id`              int(11) not null auto_increment,
  `reduce_price_id` int(11) not null comment '限时减价活动ID',
  `goods_id`        int(11) not null comment '商品ID',
  `product_id`      int(11) not null comment '规格ID',
  `prd_price`       decimal(10, 2)   default null comment '折后价格',
  primary key (`id`),
  UNIQUE KEY `reduce_product` (`reduce_price_id`,`goods_id`,`product_id`)
);

--  分销员等级表
-- DROP TABLE IF EXISTS `b2c_distributor_level`;
create table `b2c_distributor_level` (
  `id`                       int(6)  not null               auto_increment,
  `level_id`                 tinyint(2)      not null               default '0' comment '等级',
  `level_name`               varchar(32) collate utf8mb4_unicode_ci default null comment '等级名称',
  `level_up_route`           tinyint(1)      not null               default '0' comment '升级类型：0自动，1手动',
  `invite_number`            int(10)                                default null comment '邀请人数量（uo_route=0有效）',
  `total_distribution_money` decimal(10, 2)                         default null comment '推广金额（uo_route=0有效）',
  `total_buy_money`          decimal(10, 2)                         default null comment '推广和消费总额（uo_route=0有效）',
  `level_user_ids`           text collate utf8mb4_unicode_ci comment '等级用户ID（uo_route=1有效）',
  `level_status`             tinyint(2)                             default '0' comment '状态:0停用，1启用',
  primary key (`id`),
  unique key `level_id` (`level_id`)
);

--  分销员等级变更表
-- DROP TABLE IF EXISTS `b2c_distributor_level_record`;
create table `b2c_distributor_level_record` (
  `id`             int(11)  not null               auto_increment,
  `user_id`        int(10)  not null               default '0' comment '用户ID',
  `is_go_up`       tinyint(1)       not null               default '0' comment '升降：0降，1升',
  `old_level`      tinyint(2)       not null               default '1' comment '旧等级',
  `old_level_name` varchar(32)      null comment '旧等级名字',
  `new_level`      tinyint(2)       not null               default '1' comment '新等级',
  `new_level_name` varchar(32) collate utf8mb4_unicode_ci  default null comment '新等级名字',
  `update_note`    varchar(120) collate utf8mb4_unicode_ci default null comment '更新备注',
  `update_time`    timestamp        not null               default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
  primary key (`id`)
);

-- -- 欧派记录价格变化表
-- DROP TABLE IF EXISTS `b2c_goods_opai_spec`;
create table `b2c_goods_opai_spec` (
  `id`         bigint(20) not null                    auto_increment,
  `goods_id`   int(11)                                default null,
  `prd_sn`     varchar(64) collate utf8mb4_unicode_ci default null,
  `prd_price`  decimal(10, 2)                         default null,
  `is_on_sale` tinyint(1)                             default '0' comment '1:上架，0:下架',
  `is_delete`  tinyint(1)                             default '0' comment '1:删除',
  `add_time`   datetime                               default null,
  primary key (`id`)
);

-- -- 订单商品返利表
-- DROP TABLE IF EXISTS `b2c_order_goods_rebate`;
create table `b2c_order_goods_rebate` (
  `rebate_id`          int(10)                        not null auto_increment,
  `order_sn`           varchar(20) collate utf8mb4_unicode_ci not null default '0' comment '订单UUID',
  `goods_id`           mediumint(8) default 0                 null comment '商品ID',
  `product_id`         mediumint(8)                           not null default '0' comment '产品ID',
  `rebate_level`       tinyint(2)                                      default '0' comment '返利级别：0自购，2二级',
  `rebate_user_id`     int(11)                                         default '0' comment '返利用户ID',
  `rebate_percent`     decimal(6, 4)                                   default '0.0000' comment '返利比例',
  `rebate_money`       decimal(10, 4) default 0.0000          null comment '单商品返利金额',
  `total_rebate_money` decimal(10, 4) default 0.0000          null comment '总返利金额',
  `add_time`           timestamp                              null     default '0000-00-00 00:00:00' comment '添加时间',
  `update_time`        timestamp                              not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
  primary key (`rebate_id`),
  key `order_sn` (`order_sn`, `product_id`),
  key `rebate_user_id` (`rebate_user_id`)
);

-- 拼团抽奖配置页
-- DROP TABLE IF EXISTS `b2c_group_draw`;
create table `b2c_group_draw` (
  `id`           int(11)                                not null auto_increment,
  `name`         varchar(50) collate utf8mb4_unicode_ci not null comment '活动名称',
  `start_time`   datetime                               not null comment '开始时间',
  `end_time`     datetime                               not null comment '结束时间',
  `goods_id`     text collate utf8mb4_unicode_ci        not null comment '参与抽奖的商品ID',
  `min_join_num` smallint(6)                            not null comment '开奖最小参与人数',
  `pay_money`    decimal(10, 2)                         not null comment '下单支付金额',
  `join_limit`   smallint(6)                            not null comment '参团限制',
  `open_limit`   smallint(6)                            not null comment '开团限制',
  `limit_amount` smallint(6)                            not null comment '最小成团人数',
  `to_num_show`  smallint(6)                                     default null comment '参与用户达到多少前端展示',
  `status`       tinyint(1)                             not null default '1' comment '1：启用 0：禁用',
  `is_draw`      tinyint(1) default 0                   null comment '是否已开奖',
  `add_time`     datetime                                        default null,
  `update_time`  datetime                                        default null on update CURRENT_TIMESTAMP,
  `del_flag`     tinyint(1)                                      default '0',
  `del_time`     int(11)                                         default null,
  `reward_coupon_id` VARCHAR(200) NULL   COMMENT '拼团失败发放优惠券',
  primary key (`id`)
);
--  拼团抽奖邀请表
-- DROP TABLE IF EXISTS `b2c_group_draw_invite`;
create table `b2c_group_draw_invite` (
  `id`             int(11)                                 not null auto_increment,
  `action`         int(11)                                 not null default '1' comment '活动类型：1 拼团抽奖',
  `identity_id`    int(11)                                 not null comment '活动ID',
  `path`           varchar(100) collate utf8mb4_unicode_ci not null comment '页面路径',
  `goods_id`       int(11)                                 not null comment '商品ID',
  `group_id`       int(11)                                          default null comment '团ID',
  `invite_user_id` int(11)                                 not null comment '邀请用户ID',
  `user_id`        int(11)                                 not null comment '用户ID',
  `is_new`         tinyint(4)                                       default '0' comment '是否是新用户',
  `is_used`        tinyint(4)                                       default '0' comment '记录是否已处理(已记录拼团信息)',
  `add_time`       datetime                                         default null,
  primary key (`id`)
);
--  拼团抽奖码记录
-- DROP TABLE IF EXISTS `b2c_join_draw_list`;
create table `b2c_join_draw_list` (
  `id`            int(11)    not null auto_increment,
  `group_draw_id` int(11)    not null comment '拼团抽奖ID',
  `goods_id`      int(11)    not null comment '商品ID',
  `group_id`      int(11)    not null comment '拼团ID',
  `user_id`       int(11)    not null comment '抽奖用户ID',
  `draw_id`       int(11)    not null comment '抽奖序列ID',
  `is_win_draw`   tinyint(1) not null default '0' comment '是否已中奖',
  `add_time`      datetime   not null comment '添加时间',
  primary key (`id`)
);
--  参团列表
-- DROP TABLE IF EXISTS `b2c_join_group_list`;
create table `b2c_join_group_list` (
  `id`              int(11)        not null                       auto_increment,
  `group_draw_id`   int(11)        not null comment '拼团抽奖ID',
  `goods_id`        int(11)        not null comment '商品ID',
  `group_id`        int(11)                                       default null comment '拼团ID',
  `user_id`         int(11)                                       default null comment '用户ID',
  `is_grouper`      tinyint(1)                                    default '0' comment '是否是团长 1是 0不是',
  `invite_user_id`  int(11)                                       default null comment '邀请人',
  `order_sn`        varchar(20) collate utf8mb4_unicode_ci        default null comment '订单编号',
  `status`          tinyint(1)                                    default '0' comment '0:拼团中 1：已成团 2：未成团',
  `draw_status`     tinyint(1)                                    default '0' comment '0:未开奖 1：已开奖',
  `is_win_draw`     tinyint(1)                                    default '0' comment '是否已中奖 1：已中奖',
  `open_time`       datetime                                      default null comment '开团时间',
  `end_time`        datetime                                      default null comment '成团时间(达到最小成团数量就记录)',
  `draw_time`       datetime                                      default null comment '开奖时间',
  `invite_user_num` int default 0  null comment '邀请用户数',
  primary key (`id`)
);
--  瓜分积分活动配置
-- DROP TABLE IF EXISTS `b2c_pin_integration_define`;
create table `b2c_pin_integration_define` (
  `id`            int(11)                                 not null auto_increment,
  `shop_id`       int(11)                                 not null comment '店铺ID',
  `name`          varchar(100) collate utf8mb4_unicode_ci not null comment '活动名称',
  `inte_total`    int(11)                                 not null default '0' comment '总抽奖积分',
  `inte_group`    int(11)                                 not null default '0' comment '每个团总积分',
  `limit_amount`  smallint(6)                             not null comment '成团人数',
  `join_limit`    smallint(6)                             not null comment '参团限制',
  `divide_type`   tinyint(1)                              not null default '0' comment '瓜分方式：0：按邀请好友数量瓜分，1：好友均分，2：随机瓜分',
  `start_time`    datetime                                not null comment '开始时间',
  `end_time`      datetime                                not null comment '结束时间',
  `status`        tinyint(1)                                       default '1' comment '状态： 1：启用  0： 禁用',
  `add_time`      datetime                                not null,
  `update_time`   datetime                                not null,
  `del_flag`      tinyint(1)                                       default '0',
  `del_time`      int(11)                                          default '0',
  `inte_remain`   int(11)                                 not null default '0' comment '剩余积分',
  `is_day_divide` tinyint(1)                              not null comment '是否开团24小时自动开奖',
  `param_n`       float                                   not null default '0' comment '常数n',
  `is_continue`   tinyint(1)                                       default '1' comment '继续： 1：继续  0： 结束',
  `advertise`     varchar(100) collate utf8mb4_unicode_ci not null comment '活动宣传语',
  primary key (`id`)
);
--  参团列表
-- DROP TABLE IF EXISTS `b2c_pin_integration_list`;
create table `b2c_pin_integration_list` (
  `id`               int(11)                                not null auto_increment,
  `inte_activity_id` int(11)                                not null comment '瓜分积分活动定义ID',
  `group_id`         varchar(60) collate utf8mb4_unicode_ci not null default '' comment '拼团ID',
  `user_id`          int(11)                                not null,
  `is_grouper`       tinyint(1)                             not null default '0' comment '是否为团长 1：是 0：否',
  `status`           tinyint(1)                             not null default '0' comment '0: 拼团中 1:拼团成功 2:拼团失败',
  `start_time`       datetime                               not null comment '参团时间',
  `end_time`         datetime                               not null comment '成团时间',
  `integration`      int(11)                                not null default '0' comment '瓜分到的积分',
  `invite_num`       smallint(6)                            not null default '0' comment '邀请人的数量',
  `invite_user`      int(11)                                not null default '0' comment '邀请人（被谁邀请）',
  `is_new`           tinyint(1)                             not null default '0' comment '是否是新用户：0：不是，1：是',
  `is_look`          tinyint(1)                             not null default '0' comment '是否看过开奖结果',
  `can_integration`  int(11)                                not null default '0' comment '该团可瓜分积分池',
  primary key (`id`)
);
--  小程序链接列表
-- DROP TABLE IF EXISTS `b2c_mp_jump`;
create table `b2c_mp_jump` (
  `id`          int(11)                                 not null auto_increment,
  `app_id`      varchar(64) collate utf8mb4_unicode_ci  not null,
  `app_name`    varchar(200) collate utf8mb4_unicode_ci not null,
  `flag`        tinyint(1)                              not null default '0' comment '0:可用，1:停用',
  `is_delete`   tinyint(1)                              not null default '0' comment '0:未删除，1:已删除',
  `add_time`    timestamp                               null     default null,
  `update_time` timestamp                               null     default null,
  primary key (`id`)
);
-- 小程序可用appid记录
-- DROP TABLE IF EXISTS `b2c_mp_jump_usable`;
create table `b2c_mp_jump_usable` (
  `id`          int(11)                                not null auto_increment,
  `template_id` int(11)                                not null comment '小程序模板ID',
  `app_id`      varchar(64) collate utf8mb4_unicode_ci not null comment '跳转小程序APPID',
  `usable`      tinyint(1)                             not null default '0' comment '0:不可用，1：可用',
  `add_time`    timestamp                              null     default null,
  `update_time` timestamp                              null     default null,
  primary key (`id`)
);
-- 资产变动记录
-- DROP TABLE IF EXISTS `b2c_trades_record`;
create table `b2c_trades_record` (
  `id`            mediumint(8)                   not null auto_increment comment '交易记录ID',
  `trade_time`    datetime                                        default null comment '交易时间',
  `trade_num`     decimal(10, 2)                                  default 0.00 comment '交易额',
  `user_id`       mediumint(8)                   not null default '0' comment '交易用户ID',
  `trade_content` tinyint(1)                     not null default '0' comment '交易内容：0：现金，1：积分',
  `trade_type`    tinyint(2)                             not null default '0' comment '交易类型说明',
  `trade_flow`    tinyint(1)                     not null default '0' comment '资金流向：0：收入，1：支出，2：待确认收入',
  `trade_status`  tinyint(1)                     not null default '0' comment '交易状态：0：已入账，1：已到账',
  `trade_sn`      varchar(20) collate utf8mb4_unicode_ci not null default '' comment '交易单号',
  primary key (`id`)
);
/*
*/

-- 资产变动记录统计
-- DROP TABLE IF EXISTS `b2c_trades_record_summary`;
create table `b2c_trades_record_summary` (
  `id`                 mediumint(8)  not null auto_increment,
  `type`               tinyint(2)            not null default '1' comment '统计类型：1,7,30',
  `income_total_money` decimal(10, 2)                 default 0.00 comment '总现金收入',
  `outgo_money`        decimal(10, 2)                 default 0.00 comment '现金支出',
  `income_real_money`  decimal(10, 2)                 default 0.00 comment '净现金收入',
  `income_total_score` decimal(10, 2)                 default 0.00 comment '总积分收入',
  `outgo_score`        decimal(10, 2)                 default 0.00 comment '积分支出',
  `income_real_score`  decimal(10, 2)                 default 0.00 comment '净积分收入',
  `add_time`           timestamp             not null comment '统计时间',
  `ref_date`           date                  not null comment '2018-09-04',
  primary key (`id`)
);
/*
*/
-- 分销提现记录
-- DROP TABLE IF EXISTS `b2c_distribution_withdraw`;
create table `b2c_distribution_withdraw` (
  `id`                int(11)                                not null auto_increment,
  `user_id`           int(11)                                not null comment '用户id',
  `add_time`          datetime                               not null comment '申请时间',
  `type`              tinyint(1)                             null comment '提现类型  1微信公众号钱包提现 2小程序',
  `status`            tinyint(1)                             not null default 1 comment '处理状态 1待审核 2拒绝 3已审核待出账 4出账成功 5失败',
  `order_sn`          varchar(20)                            not null comment '提现单号',
  `withdraw_user_num` varchar(20)                            not null comment '用户提现序号',
  `withdraw_num`      varchar(20)                            not null comment '流水号',
  `withdraw_cash`     decimal(10, 2)                         not null comment '提现金额',
  `withdraw`          decimal(10, 2)                         not null comment '可提现金额',
  `update_time`       datetime                                        default null on update CURRENT_TIMESTAMP,
  `desc`              text                                   null comment '备注',
  `refuse_desc`       text                                   null comment '驳回原因',
  `check_time`        datetime                                        default null on update CURRENT_TIMESTAMP comment '审核时间',
  `refuse_time`       datetime                                        default null on update CURRENT_TIMESTAMP comment '驳回时间',
  `billing_time`      datetime                                        default null on update CURRENT_TIMESTAMP comment '出账时间',
  `fail_time`         datetime                                        default null on update CURRENT_TIMESTAMP comment '失败时间',
  `desc_time`         datetime                                        default null on update CURRENT_TIMESTAMP comment '备注时间',
  `del_flag`          tinyint(1)                                      default '0',
  `del_time`          int(11)                                         default null,
  `withdraw_source`   varchar(20) collate utf8mb4_unicode_ci not null default '' comment '申请时提现配置',
  `real_name`         varchar(32)                            null comment '真实姓名',
  primary key (`id`),
  unique key `order_sn` (`order_sn`)
) engine = InnoDB default charset = utf8mb4 collate = utf8mb4_unicode_ci;

/*
 */
-- -- 商品品牌
-- drop table if exists `b2c_goods_brand`;
create table `b2c_goods_brand` (
  `id`          int(11)     not null auto_increment,
  `brand_name`  varchar(500) not null comment '品牌名称',
  `e_name`      varchar(500) not null default '' comment '品牌英文名称',
  `logo`        varchar(255)         default null comment '品牌Logo',
  `first`       tinyint(3)  not null default '0' comment '优先级',
  `add_time`    datetime    not null,
  `update_time` datetime             default null,
  `is_delete`   tinyint(1)  not null default '0' comment '0为未删除 1为删除',
  `desc`        text                 default null comment '品牌介绍',
  primary key (`id`)
);
-- -- 退货退款/退款状态记录
-- DROP TABLE IF EXISTS `b2c_return_status_change`;
create table `b2c_return_status_change` (
  `id`       int(11)     not null auto_increment,
  `ret_id`   int(11)     not null comment '退货申请id',
  `user_id`  int(11)     not null comment '用户id',
  `type`     tinyint(1)  null     default null comment '0 商家触发   1是用户触发  2系统自动处理',
  `status`   tinyint(1)  not null default '1' comment '更改状态',
  `order_sn` varchar(20) not null comment '订单号',
  `add_time` datetime    not null comment '变化时间',
  `desc`     text        null comment '备注' collate 'utf8mb4_unicode_ci',
  primary key (`id`)
) collate = 'utf8mb4_unicode_ci' engine = InnoDB;

-- unlimit小程序码
-- DROP TABLE IF EXISTS `b2c_wxp_unlimit_code`;
create table `b2c_wxp_unlimit_code` (
  `code_id`   int(10)                                not null auto_increment comment '小程序码id',
  `scene_id`  varchar(32) collate utf8mb4_unicode_ci not null comment '对应b2c_wxp_unlimit_scene的scene_id',
  `code_page` varchar(191) collate utf8mb4_unicode_ci         default null comment '必须是已经发布的小程序存在的页面（否则报错），例如 pages/index/index, 根路径前不要填加 /,不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面',
  `code_url`  varchar(191) collate utf8mb4_unicode_ci         default null comment '小程序码URL',
  `code_path` varchar(191) collate utf8mb4_unicode_ci         default null comment '小程序码本地地址',
  `add_time`  timestamp                              not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flag` TINYINT(1) DEFAULT 0  NULL,
  `del_time` TIMESTAMP DEFAULT '0000-00-00 00:00:00'  NULL,
  primary key (`code_id`),
  key `scene_id` (`scene_id`, `code_page`)
);
/*
 */

-- unlimit小程序码scene值保存表
-- DROP TABLE IF EXISTS `b2c_wxp_unlimit_scene`;
create table `b2c_wxp_unlimit_scene` (
  `scene_id`    int(10) not null                         auto_increment,
  `scene_value` varchar(1200) collate utf8mb4_unicode_ci default null,
  primary key (`scene_id`),
  key `scene_value` (`scene_value`)
);

-- 一口价活动
-- DROP TABLE IF EXISTS `b2c_package_sale`;
create table `b2c_package_sale` (
  `id`             int(10)   not null                        auto_increment,
  `package_name`   varchar(120) collate utf8mb4_unicode_ci   default null comment '名称',
  `start_time`     timestamp not null                        default '0000-00-00 00:00:00' comment '开始时间',
  `end_time`       timestamp not null                        default '0000-00-00 00:00:00' comment '结束时间',
  `total_money`    decimal(10, 2)                            default '0.00' comment '结算总价格',
  `goods_group_1`  tinyint(2)                                default '0' comment '分组一，1启用',
  `group_name_1`   varchar(20) collate utf8mb4_unicode_ci    default null comment '分组名称',
  `goods_number_1` mediumint(9)                              default '0' comment '分组商品数',
  `goods_ids_1`    text collate utf8mb4_unicode_ci comment '分组goodsIDs',
  `cat_ids_1`      text collate utf8mb4_unicode_ci comment '分组平台分类id',
  `sort_ids_1`     text collate utf8mb4_unicode_ci comment '分组商家分类id',
  `goods_group_2`  tinyint(2)                                default '0' comment '分组二，1启用',
  `group_name_2`   varchar(20) collate utf8mb4_unicode_ci    default null comment '分组名称',
  `goods_number_2` mediumint(9)                              default '0' comment '分组商品数',
  `goods_ids_2`    text collate utf8mb4_unicode_ci comment '分组goodsIDs',
  `cat_ids_2`      text collate utf8mb4_unicode_ci comment '分组平台分类id',
  `sort_ids_2`     text collate utf8mb4_unicode_ci comment '分组商家分类id',
  `goods_group_3`  tinyint(2)                                default '0' comment '分组三，1启用',
  `group_name_3`   varchar(20) collate utf8mb4_unicode_ci    default null comment '分组名称',
  `goods_number_3` mediumint(9)                              default '0' comment '分组商品数',
  `goods_ids_3`    text collate utf8mb4_unicode_ci comment '分组goodsIDs',
  `cat_ids_3`      text collate utf8mb4_unicode_ci comment '分组平台分类id',
  `sort_ids_3`     text collate utf8mb4_unicode_ci comment '分组商家分类id',
  `status`         tinyint(2)                                default '1' comment '活动状态1启用',
  `del_flag`       tinyint(2)                                default '0' comment '删除状态',
  `add_time`       timestamp not null                        default '0000-00-00 00:00:00',
  `update_time`    timestamp not null                        default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  primary key (`id`)
);

-- 一口价已选商品
-- DROP TABLE IF EXISTS `b2c_package_goods_cart`;
create table `b2c_package_goods_cart` (
  `id`           int(10)  not null auto_increment,
  `user_id`      int(9)                    default '0' comment '用户id',
  `package_id`   int(9)                    default '0' comment '一口价活动ID',
  `group_id`     tinyint(2)                default '1' comment '商品组ID',
  `goods_id`     int(9)                    default '0' comment '商品ID',
  `product_id`   int(9)                    default '0' comment '产品ID',
  `goods_number` int(6)                    default '1' comment '商品数量',
  `add_time`     timestamp        not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `update_time`  timestamp        not null default '0000-00-00 00:00:00',
  primary key (`id`),
  key `user_id` (`user_id`, `package_id`, `group_id`)
);

-- -- 会员卡订单表
-- drop table if exists `b2c_card_order`;
create table `b2c_card_order` (
  `order_id`          mediumint(8)         not null auto_increment comment '订单id',
  `card_id`           int(11)                      not null default '0' comment '会云卡id',
  `card_no`           varchar(32) default '0'      null comment '会员卡NO',
  `order_sn`          varchar(20)                  not null default '' comment '订单编号',
  `user_id`           mediumint(8)         not null default '0' comment '用户id',
  `order_status`      tinyint(1)                   not null default '0' comment '订单状态',
  `order_status_name` varchar(32) default ''       not null comment '订单状态名称',
  `invoice_id`        int(11)                               default '0' comment '发票id',
  `invoice_detail`    text comment '发票内容：json存储',
  `add_message`       varchar(191)                          default '' comment '客户留言',
  `pay_code`          varchar(30)                           default null comment '支付代号',
  `pay_name`          varchar(120)                          default null comment '支付名称',
  `prepay_id`         varchar(191)                          default null comment '微信支付Id，用于发送模板消息',
  `pay_sn`            varchar(32)                           default null comment '支付流水号',
  `money_paid`        decimal(10, 2)                        default '0.00' comment '订单应付金额',
  `use_account`       decimal(10, 2)                        default '0.00' comment '用户消费余额',
  `use_score`         decimal(10, 2)                        default '0.00' comment '用户消费余额',
  `order_amount`      decimal(10, 2)                        default '0.00' comment '订单总金额',
  `add_time`          timestamp                    null     default CURRENT_TIMESTAMP comment '订单提交时间',
  `pay_time`          timestamp                    null comment '支付时间',
  `seller_remark`     varchar(512)                 null     default '' comment '卖家备注',
  `star_flag`         tinyint(1)                   null     default 0 comment '标星订单：0 未标星 1 标星',
  `del_flag`          tinyint(1)                            default '0' comment '删除',
  `ali_trade_no`      varchar(60)                  null     default '' comment '支付宝交易单号',
  `return_flag`       tinyint(1)                            default '0' comment '0:未申请退款，1：退款失败，2：退款成功',
  `return_score`      decimal(10, 2)       not null default '0.00' comment '退款积分',
  `return_account`    decimal(10, 2)       not null default '0.00' comment '退款余额',
  `return_money`      decimal(10, 2)       not null default '0.00' comment '退款余额',
  `return_time`       timestamp                    null comment '退款时间',
  primary key (`order_id`)
);
/**  */


-- -- 会员卡激活审核表
-- drop table if exists `b2c_card_examine`;
create table `b2c_card_examine` (
  `id`             mediumint(8)         not null auto_increment comment '订单id',
  `card_id`        int(11)                      not null default '0' comment '会云卡id',
  `card_no`        varchar(32) default '0'      null comment '会员卡NO',
  `user_id`        mediumint(8)         not null default '0' comment '用户id',
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
  `add_time`       timestamp                    null     default CURRENT_TIMESTAMP comment '提交时间',
  `pass_time`      timestamp                    null comment '通过时间',
  `refuse_time`    timestamp                    null comment '拒绝时间',
  `refuse_desc`    varchar(512)                 null comment '拒绝理由',
  `del_flag`       tinyint(1)                   not null default '0' comment '删除',
  `def_time`       timestamp                    null comment '删除时间',
  primary key (`id`)
);

-- -- 会员卡专享商品关联表
-- drop table if exists `b2c_goods_card_couple`;
create table `b2c_goods_card_couple` (
  `id`      int(11)                                 not null auto_increment comment '会员卡专属商品关联ID',
  `card_id` varchar(100) collate utf8mb4_unicode_ci not null comment '会员卡ID',
  `gcta_id` int(11)                                          default '0' comment '商品或类型ID',
  `type`    tinyint(1)                                       default '0' comment '标签关联类型： 1：关联商品 2：关联商家分类 3：关联平台分类',
  primary key (`id`)
);

-- -- 会员卡专享商品关联表
-- drop table if exists `b2c_presale`;
create table `b2c_presale` (
  `id`               int(9)                         not null auto_increment comment '活动ID',
  `presale_type`     tinyint(2)                             not null default '0' comment '预售类型1：全款',
  `presale_name`     varchar(32) collate utf8mb4_unicode_ci not null comment '预售活动名称',
  `pre_pay_step`     tinyint(2)                                      default '1' comment '定金期数1|2',
  `pre_start_time`   timestamp                              not null default '0000-00-00 00:00:00' comment '定金一期支付开始时间',
  `pre_end_time`     timestamp                              not null default '0000-00-00 00:00:00' comment '定金一期支付结束时间',
  `pre_start_time_2` timestamp                              null     default '0000-00-00 00:00:00' comment '定金二期支付开始时间',
  `pre_end_time_2`   timestamp                              null     default '0000-00-00 00:00:00' comment '定金二期支付结束时间',
  `start_time`       timestamp                              not null default '0000-00-00 00:00:00' comment '尾款支付开始时间',
  `end_time`         timestamp                              not null default '0000-00-00 00:00:00' comment '尾款支付结束时间',
  `goods_id`         int(9)                                          default '0' comment '商品ID',
  `deliver_type`     tinyint(2)                                      default '0' comment '发货时间模式1:deliver_time;2:deliver_days',
  `deliver_time`     timestamp                              null     default '0000-00-00 00:00:00' comment '发货日期',
  `deliver_days`     int(6)                                          default '0' comment '下单后几日发货',
  `discount_type`    tinyint(2)                                      default '0' comment '优惠策略1:可叠加0:不可叠加',
  `return_type`      tinyint(2)                                      default '0' comment '退定金模式1:自动退定金0:不退定金',
  `show_sale_number` int(6)                                          default '0' comment '是否显示销量1:显示',
  `buy_type`         tinyint(2)                                      default '0' comment '是否支持原价1:支持',
  `buy_number`       int(6)                                          default '0' comment '单用户最多可购买数量',
  `status`           tinyint(2)                                      default '1' comment '状态1:启用0:停用',
  `del_flag`         tinyint(2)                                      default '0' comment '删除状态1:删除',
  `add_time`         timestamp                              null     default '0000-00-00 00:00:00',
  `update_time`      timestamp                              not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `share_config`      TEXT                                  NULL   COMMENT '分享设置',
  primary key (`id`)
);

-- -- 会员卡专享商品关联表
-- drop table if exists `b2c_presale_product`;
create table `b2c_presale_product` (
  `id`                   int(10)  not null auto_increment,
  `presale_id`           int(9)           not null default '0' comment '预售ID',
  `goods_id`             int(9)           not null default '0' comment '商品ID',
  `product_id`           int(9)           not null default '0' comment '产品ID',
  `presale_price`        decimal(10, 2)            default '0.00' comment '预售价格',
  `presale_number`       int(9)                    default '0' comment '预售商品数量',
  `sale_number`          int(6)                    default '0',
  `presale_money`        decimal(10, 2)            default '0.00' comment '预售定金金额',
  `pre_discount_money_1` decimal(10, 2)            default '0.00' comment '预售一阶段定金抵扣金额',
  `pre_discount_money_2` decimal(10, 2)            default '0.00' comment '预售二阶段定金抵扣金额',
  `add_time`             timestamp        not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  primary key (`id`),
  key `presale_id` (`presale_id`, `goods_id`, `product_id`),
  key `presale_id_2` (`presale_id`, `product_id`)
);

-- -- 会员卡订单退款记录
-- drop table if exists `b2c_refund_card_record`;
create table `b2c_refund_card_record` (
  `rec_id`      int(11)                        not null auto_increment,
  `order_sn`    varchar(30) collate utf8mb4_unicode_ci not null default '',
  `user_id`     int(11)                        not null default '0',
  `use_score`   decimal(10, 2)                 not null default '0.00' comment '积分抵扣金额',
  `use_account` decimal(10, 2)                 not null default '0.00' comment '退款余额',
  `money_paid`  decimal(10, 2)                 not null default '0.00' comment '退款余额',
  `refund_time` timestamp                              not null default CURRENT_TIMESTAMP comment '订单退款时间',
  `is_success`  tinyint(1)                     not null default '0' comment '人工处理状态，1：退款失败，2：退款成功',
  primary key (`rec_id`),
  key `order_sn` (`order_sn`)
);

-- --  页面分类
-- drop table if exists `b2c_page_classification`;
create table `b2c_page_classification` (
  `id`          int(10)  not null auto_increment comment 'ID',
  `shop_id`     int(11)          not null default 0 comment '店铺ID',
  `name`        varchar(60)      not null default '' comment '分类名称',
  `create_time` timestamp                 default now(),
  primary key (`id`),
  key (`shop_id`)
);

--  支付送券记录表
-- DROP TABLE IF EXISTS `b2c_coupon_payreward_record`;
create table `b2c_coupon_payreward_record` (
  `id`                int(11)     not null auto_increment,
  `activity_id`       int(11)     not null comment '活动ID',
  `user_id`           int(11)     not null,
  `receive_time`      datetime    not null comment '领取时间',
  `mrking_voucher_id` varchar(500)         default null comment '已领取的优惠券',
  `order_sn`          varchar(20) not null default '' comment '订单编号',
  primary key (`id`)
);
/**  */
-- --  短信发送记录
-- drop table if exists `b2c_sms_send_record`;
create table `b2c_sms_send_record` (
  `id`            int         not null auto_increment,
  `account_name`  varchar(50) not null comment '账号',
  `user_id`       int         not null,
  `mobile`        varchar(20) not null,
  `request_msg`   text        not null comment '请求内容',
  `response_code` varchar(50) comment '响应码',
  `response_msg`  text comment '响应内容',
  `ext`           varchar(20) comment '行业账号 默认:行业 market:营销,checkcode:验证码',
  `sms`           varchar(20) comment '短信通道 默认短信策略:mxt',
  `add_time`      datetime,
  primary key (`id`)
);
-- --  代付表
-- drop table if exists `b2c_sub_order_info`;
create table `b2c_sub_order_info` (
  `id`            int            not null auto_increment,
  `sub_order_sn`  varchar(20)    not null comment '代付订单号',
  `main_order_sn` varchar(20)    not null comment '主订单号',
  `user_id`       int            not null comment '用户ID',
  `username`      varchar(50)    null comment '用户昵称',
  `order_status`  tinyint(1)     not null default 0 comment '代付订单状态',
  `money_paid`    decimal(10, 2) not null default 0.00 comment '付款金额',
  `refund_money`  decimal(10, 2) not null default 0.00 comment '退款金额',
  `pay_code`      varchar(20)    null comment '支付代号',
  `pay_name`      varchar(100)   null comment '支付名称',
  `pay_sn`        varchar(32) comment '支付流水号',
  `prepay_id`     varchar(200) comment '微信支付Id',
  `message`       varchar(200) comment '留言',
  `pay_time`      datetime comment '支付时间',
  `add_time`      datetime comment '提交订单时间',
  `refund_time`   datetime comment '最近一次退款时间',
  `del_flag`      tinyint(1)              default 0,
  `del_time`      datetime,
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
  `card_id`     varchar(100) comment '会员卡ID',
  `total_num`   int          default 0 comment '总数',
  `success_num` int          default 0 comment '成功数',
  `add_time`    datetime,
  `update_time` datetime on update CURRENT_TIMESTAMP,
  `del_flag`    tinyint      default 0,
  `del_time`    datetime,
  primary key (`id`)
);

-- --  用户导入明细表
-- drop table if exists `b2c_user_import_detail`;
create table `b2c_user_import_detail` (
  `id`                 int(11) not null auto_increment,
  `batch_id`           int(11) not null comment '主表ID',
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
  `card_id`            varchar(100)     default null comment '会员卡ID',
  `is_activate`        tinyint(1)       default '0' comment '是否已激活',
  `add_time`           datetime         default null comment '添加时间',
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
  unique index `order_sn` (`order_sn`),
  primary key (`id`)
);

-- --  用户导入主表
-- drop table if exists `b2c_import_goods`;
create table `b2c_import_goods` (
  `id`           int not null auto_increment,
  `imp_batch_no` varchar(255) comment '导入批次号',
  `site_type`    varchar(255) comment '导入网站类型:taobao,tmall',
  `imp_url`      varchar(255) comment '导入URL',
  `item_id`      varchar(255) comment '源商品ID',
  `goods_id`     int          default 0 comment '导入后商品ID',
  `goods_name`   varchar(255) comment '商品名称',
  `imp_time`     timestamp    default now(),
  key (imp_batch_no),
  key (item_id),
  key (goods_id),
  key (imp_time),
  primary key (`id`)
);

-- --  会员卡批次表
-- drop table if exists b2c_card_batch;
CREATE TABLE `b2c_card_batch`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `card_id` INT DEFAULT 0 COMMENT '卡号ID',
  `action` TINYINT(1) DEFAULT 1 COMMENT '领取码获得方式 1：自动生成 2：导入',
  `name` VARCHAR(200) NOT NULL COMMENT '批次名称',
  `code_size` TINYINT DEFAULT 0 COMMENT '领取码位数',
  `card_size` TINYINT COMMENT '卡号位数',
  `card_pwd_size` TINYINT COMMENT '卡密码位数',
  `number` INT DEFAULT 0 COMMENT '发放数量',
  `code_prefix` VARCHAR(10) NULL   COMMENT '领取码前缀',
  `card_prefix` VARCHAR(10) NULL   COMMENT '卡前缀',
  `add_time` DATETIME,
  `update_time` DATETIME ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` TINYINT(1) DEFAULT 0,
  `del_time` DATETIME,
  PRIMARY KEY (`id`),
  INDEX `card_id` (`card_id`),
  INDEX `action` (`action`)
);

-- --  会员卡领取码表
-- drop table if exists b2c_card_receive_code;
CREATE TABLE `b2c_card_receive_code`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `card_id` INT DEFAULT 0 COMMENT '卡号ID',
  `batch_id` INT NOT NULL COMMENT '批次ID',
  `group_id` INT(11) DEFAULT 1  NULL   COMMENT '分组ID',
  `code` VARCHAR(15) COMMENT '领取码',
  `card_no` VARCHAR(30) COMMENT '卡号',
  `card_pwd` VARCHAR(20) COMMENT '卡密码',
  `user_id` INT DEFAULT 0  NULL   COMMENT '领取人',
  `receive_time` DATETIME NULL   COMMENT '领取时间',
  `error_msg` VARCHAR(200) NULL   COMMENT '错误说明',
  `add_time` DATETIME,
  `update_time` DATETIME ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` TINYINT(1) DEFAULT 0,
  `del_time` DATETIME,
  PRIMARY KEY (`id`),
  INDEX `card_id` (`card_id`),
  INDEX `batch_id` (`batch_id`)
);


-- --  分销员分组表
-- drop table if exists b2c_distributor_group;
CREATE TABLE `b2c_distributor_group` (
  `id` int(6)  NOT NULL AUTO_INCREMENT COMMENT '分组ID',
  `group_name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分组名字',
  `is_default` tinyint(1) DEFAULT '0' COMMENT '是否为默认',
  `add_time` timestamp NULL DEFAULT NULL COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
);

-- --  赠品活动
-- drop table if exists b2c_gift;
CREATE TABLE `b2c_gift`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '活动名称',
  `level` SMALLINT NOT NULL DEFAULT 0 COMMENT '优先级',
  `start_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME NOT NULL COMMENT '结束时间',
  `goods_id` TEXT COMMENT '活动商品',
  `rule` TEXT NOT NULL COMMENT '赠品策略',
  `explain` TEXT COMMENT '说明',
  `status` TINYINT(1) DEFAULT 1,
  `add_time` DATETIME,
  `update_time` DATETIME ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` TINYINT(1) DEFAULT 0,
  `del_time` DATETIME,
  PRIMARY KEY (`id`),
  INDEX `level` (`level`)
);
-- --  赠品规格商品
-- drop table if exists b2c_gift_product;
CREATE TABLE `b2c_gift_product`(
  `id` INT NOT NULL AUTO_INCREMENT,
  `gift_id` INT NOT NULL COMMENT '赠品活动ID',
  `product_id` INT NOT NULL COMMENT '规格ID',
  `product_number` INT NOT NULL COMMENT '库存',
  PRIMARY KEY (`id`),
  INDEX `gift_id` (`gift_id`),
  INDEX `product_id` (`product_id`)
);

-- --  商品导入主表
-- drop table if exists `b2c_goods_import`;
create table `b2c_goods_import` (
  `id`          int not null auto_increment,
  `total_num`   int          default 0 comment '导入总数',
  `success_num` int          default 0 comment '导入成功数',
  `add_time`    datetime,
  `update_time` datetime on update CURRENT_TIMESTAMP,
  `del_flag`    tinyint      default 0,
  `del_time`    datetime,
  `import_file_path` varchar(120) NOT NULL COMMENT '导入源文件地址',
  `is_update`    tinyint(1)            not null default 0 comment '是否更新：0新增，1更新',
  primary key (`id`)
);

-- --  商品导入明细表
-- drop table if exists `b2c_goods_import_detail`;
create table `b2c_goods_import_detail` (
  `id`                 int(11) not null auto_increment,
  `batch_id`           int(11) not null comment '主表ID',
  `goods_sn`           varchar(32)      default null comment '商品sn',
  `prd_sn`             varchar(32)      default null comment '规格sn',
  `goods_name`         varchar(120)     default null comment '商品名称',
  `prd_desc`           varchar(120)     default null comment '规格描述',
  `error_msg`          varchar(100)     default null comment '错误内容',
  `add_time`           datetime         default null comment '添加时间',
  `is_success`         tinyint          default 0 comment '导入成功标识：0不成功，1成功',
  primary key (`id`),
  key `goods_sn` (`goods_sn`),
  key `prd_sn` (`prd_sn`)
);

-- -- 渠道页面
-- drop table if exists `b2c_channel`;
create table `b2c_channel` (
  `id`                   int(11)  not null auto_increment comment '渠道页ID',
     `page_id`                   int(11)  not null  comment 'page_id',
  `goods_id`                   int(11)  not null  comment 'goods_id',
  `channel_name`      varchar(100)          not null default '' comment '渠道页名称',
  `source_type`          tinyint(2)          not null default 0 comment '来源类型 0自定义 1商品',
  `share`          varchar(191)          not null default '' comment '分享码',
  `del_flag`             tinyint(1)            not null default '0' comment '0正常，1废除',
  `add_time`             datetime             null     default null,
  primary key (`id`)
);
-- -- 渠道统计
-- drop table if exists `b2c_channel_record`;
create table `b2c_channel_record` (
  `id`                   int(11)  not null auto_increment comment 'id',
  `channel_id`                   int(11)  not null  comment '渠道页ID',
  `user_id`                   int(11)  not null  comment 'userID',
  `type`          tinyint(2)          not null default 0 comment '类型 1新用 0老用户',
  `count`                   int(11)  not null  comment '访问次数',
  `add_time`             datetime             null     default null,
  primary key (`id`),
  key (`user_id`)
);

-- -- 核销员表
-- drop table if exists `b2c_order_verifier`;
CREATE TABLE `b2c_order_verifier` (
  `id` int(8)  NOT NULL AUTO_INCREMENT,
  `store_id` int(8)  DEFAULT NULL COMMENT '门店ID',
  `user_id` int(8)  DEFAULT NULL COMMENT '用户ID',
  `verify_orders` int(6) DEFAULT NULL COMMENT '核销订单数',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除',
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `store_id` (`store_id`,`user_id`)
);
-- --  好物推荐
-- drop table if exists `b2c_wx_shopping_recommend`;
CREATE TABLE `b2c_wx_shopping_recommend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `goods_id` int(11) NOT NULL COMMENT '商品ID',
  `order_sn` int(11) DEFAULT NULL COMMENT '订单orderSn',
  `click_num` INT DEFAULT 1  NULL   COMMENT '点击次数',
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `goods_id` (`goods_id`)
);
-- --  自定义足迹
-- DROP TABLE IF EXISTS `b2c_index_foot_record`;
create table `b2c_index_foot_record` (
  `id`          int(11)  not null auto_increment,
  `page_id`    int(11)  not null comment '自定义页面ID',
  `user_id`     int(11)  not null,
  `update_time` datetime not null comment '浏览时间',
  `count` int(11) DEFAULT 1 NULL COMMENT '浏览次数',
  `type` TINYINT(2) DEFAULT 0  NULL   COMMENT '0 老用户 1新用户',
  primary key (`id`)
);

-- drop table if exists `b2c_channel_statistical`;
create table `b2c_channel_statistical` (
  `id`                            mediumint(10)  not null auto_increment,
  `page_id`                     mediumint(8)    null default 0 comment '',
  `goods_id`                     mediumint(8)    null default 0 comment '',
  `channel_id`                         text null default '' comment '渠道ID',
  `channel_all_pv`                     text null default '',
  `channel_all_uv`                     text null default '',
  `channel_new_pv`                     text null default '',
  `channel_new_uv`                     text null default '',
  `channel_old_pv`                     text null default '',
  `channel_old_uv`                     text null default '',
  `all_pv`                    text null default '',
  `all_uv`                    text null default '',
  `new_pv`                     text null default '',
  `new_uv`                     text null default '',
  `old_pv`                    text null default '',
  `old_uv`                     text null default '',
  `ref_date`           date                  not null comment '2019-03-04',
  `ref_type`             tinyint(1)            not null default '0' comment '1昨天 7天 30天',
  primary key (`id`),
  key `ref_date` (`ref_date`)
);

-- --  分销改价价格表
-- drop table if exists `b2c_goods_rebate_price`;
CREATE TABLE `b2c_goods_rebate_price` (
  `id` int(9)  NOT NULL AUTO_INCREMENT,
  `goods_id` int(9) DEFAULT NULL COMMENT '商品ID',
  `product_id` int(9) DEFAULT NULL,
  `advise_price` decimal(10,2) DEFAULT 0.00,
  `min_price` decimal(10,2) DEFAULT NULL,
  `max_price` decimal(10,2) DEFAULT NULL,
  `add_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- --  分销改价价格表
-- drop table if exists `b2c_user_rebate_price`;
CREATE TABLE `b2c_user_rebate_price` (
  `id` int(9)  NOT NULL AUTO_INCREMENT,
  `user_id` int(9) DEFAULT '0' COMMENT '用户ID',
  `goods_id` int(9) DEFAULT '0' COMMENT '商品ID',
  `product_id` int(9) DEFAULT '0' COMMENT '产品ID',
  `advice_price` DECIMAL(10,2) DEFAULT 0.00  NULL   COMMENT '分销价格',
  `add_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '添加时间',
  `expire_time` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '过期时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
);
/*
 */

-- --  搜索热词表
-- drop table if exists `b2c_search_history`;
CREATE TABLE `b2c_search_history`(
  `user_id` INT NOT NULL,
  `hot_words` VARCHAR(100) NOT NULL COMMENT '热词',
  `search_count` INT DEFAULT 1  NULL   COMMENT '搜索次数',
  `is_hot_words` TINYINT DEFAULT 0  NULL   COMMENT '是否是热词搜索',
  `add_time` DATETIME NOT NULL,
  `update_time` DATETIME NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` TINYINT(1) DEFAULT 0  NULL,
  INDEX `user_id` (`user_id`),
  INDEX `hot_words` (`hot_words`)
);
-- --  请求外部记录表
-- drop table if exists `b2c_request_record`;
CREATE TABLE `b2c_request_record`(
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `service_action` TINYINT DEFAULT 0 COMMENT '服务类型 1 POS 2 CRM',
  `service_interface` VARCHAR(50) NOT NULL COMMENT '服务接口',
  `request_content` TEXT NOT NULL COMMENT '请求内容',
  `response_code` VARCHAR(10) COMMENT '响应码',
  `response_msg` VARCHAR(100) COMMENT '响应码对照',
  `response_content` TEXT COMMENT '响应内容',
  `request_time` DATETIME COMMENT '请求时间',
  `response_time` DATETIME COMMENT '响应时间',
  `error_is_deal` TINYINT(1) DEFAULT 0  NULL   COMMENT '错误是否已处理',
  PRIMARY KEY (`id`),
  INDEX `service_action` (`service_action`),
  INDEX `service_interface` (`service_interface`)
);
-- --  请求外部记录表
-- drop table if exists `b2c_rebate_price_record`;
CREATE TABLE `b2c_rebate_price_record` (
  `id` int(9)  NOT NULL AUTO_INCREMENT,
  `user_id` int(9) DEFAULT '0' COMMENT '用户ID',
  `data_sign` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'md5(rebate_data)',
  `rebate_data` varchar(5000) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '分享内容',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '分享时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`data_sign`)
);
/*
 */
 -- --  店铺自定义品牌分类
-- drop table if exists `b2c_brand_classify`;
create table `b2c_brand_classify` (
  `classify_id`     int(11)  not null auto_increment,
  `classify_name`   varchar(90)               default '',
  `create_time` timestamp                 default now(),
  `first`       smallint(2)      not null default '0' comment '优先级',
  `update_time` timestamp                 default now(),
  `is_delete`           tinyint(1)             null     default '0',
   primary key (`classify_id`)
);
/*
*/

 -- --  批量改价活动
-- drop table if exists `b2c_batch_price_define`;
CREATE TABLE `b2c_batch_price_define` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '批量改价活动ID',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '活动名称',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `file_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件名称',
  `del_flag` tinyint(1) DEFAULT '0',
  `status` tinyint(1) DEFAULT '1' COMMENT '状态： 1：启用  0： 禁用',
  `add_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `del_time` int(11) DEFAULT '0',
  `is_start_end` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:未开始，1：已开始，2：已结束（商品价格修改）',
  PRIMARY KEY (`id`)
);

 -- --  批量改价规格价格对
-- drop table if exists `b2c_batch_price`;
CREATE TABLE `b2c_batch_price` (
   `id` int(9)  NOT NULL AUTO_INCREMENT,
   `prd_sn` varchar(65) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '商家编码',
   `price` decimal(10,2) DEFAULT '0.00' COMMENT '规格价格',
   `act_id` int(9) DEFAULT '1' COMMENT '导入批次',
   PRIMARY KEY (`id`)
);
 -- --  品牌分类毛利对
-- drop table if exists `b2c_batch_profit`;
CREATE TABLE `b2c_batch_profit` (
  `id` int(9)  NOT NULL AUTO_INCREMENT,
  `brand_id` int(9) DEFAULT '0' COMMENT '品牌ID',
  `sort_id` int(9) DEFAULT '0' COMMENT '分类ID',
  `act_id` int(9) DEFAULT '1' COMMENT '导入批次',
  `profit_per` decimal(10,2) DEFAULT '0.00',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `file_name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '文件名称',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

-- -- 好友助力活动表
-- drop table if exists `b2c_friend_promote_activity`;
create table `b2c_friend_promote_activity` (
  `id`                   mediumint(8)  not null auto_increment,
  `shop_id`              int(11)               null     default 0 comment '店铺ID',
  `act_code`             varchar(32)           null     default '' comment '活动编码',
  `act_name`             varchar(120)          not null default '' comment '活动名称',
  `start_time`           datetime              not null             comment '活动起始时间',
  `end_time`             datetime              not null    comment '活动截止时间',
  `reward_type`          tinyint(1)            null     default 0 comment '奖励类型：0赠送商品，1折扣商品，2赠送优惠券',
  `reward_content`       text                  comment '奖励内容',
  `reward_duration`      int(8)               null     default 0 comment '奖励有效期',
  `reward_duration_unit` tinyint(1)            null     default 0 comment '奖励有效期单位：0小时，1天，2周，3月，4年',
  `promote_amount`        decimal(10,2)         null     default '0.00' comment '所需助力值',
  `promote_times`         int(5)        null     default 1 comment '所需助力次数',
  `launch_limit_duration` int(5)        null     default 0 comment '发起次数限制时长，0不限制',
  `launch_limit_unit`    tinyint(1)            null     default 0 comment '发起次数限制时长单位：0天，1周，2月，3年',
  `launch_limit_times`   tinyint(3)    null     default 0 comment '发起限制次数，0不限制',
  `share_add_times`      tinyint(3)    null     default 1 comment '好友分享可获得助力次数',
  `promote_type`         tinyint(1)            null     default 0 comment '单次助力值类型：0平均，1随机',
  `promote_condition`     tinyint(1)    null     default 0 comment '好友助力条件：0可不授权个人信息，1必须授权',
  `failed_send_type`     tinyint(1)    null     default 0 comment '助力失败赠送类型：0不赠送，1优惠券，2积分',
  `failed_send_content`  int(8)                null     default 0 comment '助力失败赠送内容',
  `activity_share_type`  tinyint(1)    null     default 0 comment '助力活动分享样式类型：0默认样式，1自定义样式',
  `custom_share_word`    varchar(400)          null     default '' comment '自定义分享样式文案',
  `share_img_type`    tinyint(1)            null     default 0 comment '自定义分享图片类型：0首页截图，1自定义图片',
  `custom_img_path`      varchar(100)          null     default '' comment '自定义分享样式图片路径',
  `is_block`             tinyint(1)            null     default 0 comment '活动状态：0未停用，1已停用',
  `del_flag`             tinyint(1)            null     default 0 comment '删除标识：0未删除，1已删除',
  `in_time`              datetime              not null comment '添加时间',
  `up_time`              datetime              not null comment '更新时间',
  `use_discount`          tinyint(1)            null     default 0 comment '是否可与会员卡折扣、优惠券叠加使用：0不可叠加，1可叠加',
  `use_score`            tinyint(1)            null     default 1 comment '是否可使用积分抵扣部分金额：0不可抵扣，1可抵扣',
  primary key (`id`),
  unique key `act_code` (`act_code`),
  key `act_name` (`act_name`),
  key (`shop_id`)
);

-- -- 好友助力发起表
-- drop table if exists `b2c_friend_promote_launch`;
create table `b2c_friend_promote_launch` (
  `id`                   mediumint(8)  not null auto_increment,
  `user_id`              int(11)               null     default 0  comment '发起会员ID',
  `promote_id`           int(11)               null     default 0  comment '助力活动ID',
  `promote_status`       tinyint(1)            null     default 0  comment '助力状态：0助力中，1助力完成待领取，2助力完成已领取，3助力失效，4助力未完成失败',
  `order_sn`             varchar(32)           null     default '' comment '助力完成生产的订单编码',
  `launch_time`          timestamp                      default now() comment '发起时间',
  `update_time`          timestamp                      default now() comment '更新时间',
  `success_time`         timestamp             null     default null comment '助力成功时间',
  `del_flag`             tinyint(1)            null     default 0 comment '删除标识：0未删除，1已删除',
  primary key (`id`),
  key (`order_sn`),
  key (`user_id`),
  key (`promote_id`)
);

-- -- 好友助力明细表
-- drop table if exists `b2c_friend_promote_detail`;
create table `b2c_friend_promote_detail` (
  `id`                   mediumint(8)  not null auto_increment,
  `launch_id`            int(11)               null     default 0 comment '助力活动发起ID',
  `user_id`              int(11)               null     default 0 comment '助力会员ID',
  `promote_id`           int(11)               null     default 0 comment '助力活动ID',
  `promote_value`        int(8)                null     default 0 comment '助力值',
  `promote_time`         timestamp                      default now() comment '助力时间',
  `update_time`          timestamp                      default now() comment '更新时间',
  primary key (`id`),
  key (`launch_id`),
  key (`user_id`),
  key (`promote_id`)
);

-- -- 可助力次数表
-- drop table if exists `b2c_friend_promote_times`;
create table `b2c_friend_promote_times` (
  `id`                   mediumint(8)  not null auto_increment,
  `launch_id`            int(11)               null     default 0 comment '助力活动发起ID',
  `user_id`              int(11)               null     default 0 comment '助力会员ID',
  `share_times`          int(8)                null     default 0 comment '分享的次数',
  `own_promote_times`    int(8)                null     default 0 comment '总的所有助力次数',
  `in_time`              timestamp                      default now() comment '助力时间',
  `up_time`              timestamp                      default now() comment '更新时间',
  `is_auth`              tinyint(1)             null     default 0 comment '是否有授权增加次数：0没有，1有',
  primary key (`id`),
  key (`launch_id`),
  key (`user_id`)
);

-- -- 操作记录表
-- drop table if exists `b2c_record_admin_action`;
CREATE TABLE `b2c_record_admin_action` (
  `id` int(9)  NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `shop_id` int(6)  DEFAULT '0' COMMENT '店铺ID',
  `sys_id` int(9)  DEFAULT NULL COMMENT '操作员ID',
  `account_id` int(9)  DEFAULT NULL COMMENT 'SUB操作员ID',
  `action_type` tinyint(2)  DEFAULT '0' COMMENT '操作类型',
  `action_desc` varchar(3000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作日志',
  `add_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
);

commit;
