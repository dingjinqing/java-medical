/***********************2.8********************BEGIN*/

-- 2020年1月10日 16:06:24 购物车表增加商品规格
ALTER TABLE `b2c_cart`
    CHANGE COLUMN `goods_specs` `prd_desc` text NULL COMMENT '例如,颜色:黑色' AFTER `goods_name`;
ALTER TABLE `b2c_cart`
    MODIFY COLUMN `prd_desc` varchar(1024) NOT NULL DEFAULT '' COMMENT '规格描述，格式例子：颜色:红色 尺码:s' AFTER `goods_name`;

/***********************2.8*********************END*/
