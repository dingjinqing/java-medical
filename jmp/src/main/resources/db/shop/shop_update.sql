-- 李晓冰 b2c_goods_brand 将记录创建时间添加默认值为当前时间戳，记录修改时间设置为当前记录变化时间戳
ALTER TABLE b2c_goods_brand MODIFY COLUMN add_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间';
ALTER TABLE b2c_goods_brand MODIFY COLUMN update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间';

-- b2c_sort 添加update_time 字段
ALTER TABLE b2c_sort ADD COLUMN update_time TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间' AFTER create_time;
-- b2c_sort 删除字段keywords
ALTER TABLE b2c_sort DROP COLUMN keywords;
-- b2c_sort 修改parent_id字段类型，使其和sort_id类型一致
ALTER TABLE b2c_sort MODIFY COLUMN parent_id INT NOT NULL;

-- 7月3号 李晓冰 设置goods_brand goods_sort 部分字段默认值和非空约束
ALTER TABLE b2c_goods_brand MODIFY COLUMN is_recommend TINYINT NOT NULL DEFAULT 0 COMMENT '是否为推荐品牌 0否 1是';
ALTER TABLE b2c_goods_brand MODIFY COLUMN classify_id TINYINT NOT NULL DEFAULT 0 COMMENT '品牌所属分类 0未分类 否则是分类id';
ALTER TABLE b2c_goods_brand MODIFY COLUMN update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间';

ALTER TABLE b2c_sort MODIFY COLUMN sort_name VARCHAR(90) NOT NULL;
ALTER TABLE b2c_sort MODIFY COLUMN parent_id INT(11) NOT NULL DEFAULT 0 COMMENT '分类父节点，0表示一级';
ALTER TABLE b2c_sort MODIFY COLUMN sort_name VARCHAR(90) NOT NULL;