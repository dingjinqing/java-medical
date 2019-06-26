#b2c_article_category表结构修改
ALTER TABLE `b2c_article_category`
DROP COLUMN `use_footer_nav`,
ADD COLUMN `del_state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0未删除,1删除',
ADD COLUMN `add_time`  timestamp NULL DEFAULT current_timestamp() COMMENT '添加时间',
ADD COLUMN `update_time`  timestamp NULL DEFAULT current_timestamp() ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

