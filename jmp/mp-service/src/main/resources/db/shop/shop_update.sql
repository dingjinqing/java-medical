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
-- CREATE TABLE if not exists `b2c_pledge_related` (
--   `id` int(9) NOT NULL AUTO_INCREMENT COMMENT 'ID',
--   `pledge_id` int(9) NOT NULL DEFAULT '0' COMMENT '承诺id',
--   `type` tinyint(1) DEFAULT NULL COMMENT '指定商品范围:1 商品id,2 商家分类id,3 商品品牌id',
--   `related_id` int(9) NOT NULL DEFAULT '0' COMMENT '相关的id',
--   `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
--   `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
--   PRIMARY KEY (`id`),
--   key `bpr_pledge_id`(`pledge_id`)
-- )COMMENT='服务承诺关联表';
ALTER TABLE `b2c_pledge` ADD COLUMN `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '商品范围:1全部商品,2指定商品' ;
ALTER TABLE `b2c_pledge` ADD COLUMN `level` int(6) NOT NULL DEFAULT 0 COMMENT '商品优先级' ;

/***********************2.9*********************END*/
