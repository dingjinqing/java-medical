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

--7月4号 修改b2c_group表名为b2c_store_group,字段in_time改为create_time
ALTER  TABLE b2c_group RENAME TO b2c_store_group;
ALTER TABLE b2c_store_group CHANGE column in_time create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

-- 7月5日 李晓冰  修改b2c_spec和 b2c_spec_val表，添加非空约束，和唯一索引
ALTER TABLE b2c_spec
MODIFY COLUMN create_time timestamp   not null 	default current_timestamp,
MODIFY COLUMN update_time  timestamp   not null  	default current_timestamp on update current_timestamp comment '最后修改时间',
ADD UNIQUE INDEX unique_spec_name_goods_id (spec_name,goods_id);

ALTER TABLE b2c_spec_vals
MODIFY COLUMN create_time timestamp   not null 	default current_timestamp,
MODIFY COLUMN update_time  timestamp   not null  	default current_timestamp on update current_timestamp comment '最后修改时间',
CHANGE COLUMN specvalid spec_val_id int(11) 	not null auto_increment ,
CHANGE COLUMN specvalname spec_val_name VARCHAR(60) not null DEFAULT '',
ADD UNIQUE INDEX unique_spec_id_spec_val_name (spec_id,spec_val_name);

-- 7月9日添加
-- 修改标签组关系表
ALTER TABLE b2c_goods_label_couple MODIFY COLUMN label_id INT(11) NOT NULL;
