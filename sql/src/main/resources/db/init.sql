create table if not exists main_sql (
    `id` bigint(18) NOT NULL,
    `sql_md5` varchar(128) DEFAULT NULL,
    `sql_detail` text ,
    `shop_id` int(12) NOT NULL,
    `add_time` timestamp NOT NULL default current_timestamp ,
    PRIMARY KEY (`id`)
);
CREATE INDEX if not exists `ms_id_md5` ON `main_sql` (`shop_id`,`sql_md5`);

create table if not exists shop_sql (
    `id` bigint(18) NOT NULL,
    `sql_md5` varchar(128) DEFAULT NULL,
    `sql_detail` text ,
    `shop_id` int(12) NOT NULL,
    `add_time` timestamp NOT NULL default current_timestamp ,
    PRIMARY KEY (`id`)
);
CREATE INDEX if not exists `ss_id_md5` ON `shop_sql` (`shop_id`,`sql_md5`);

drop table if exists main_sql_temp;

drop table if exists shop_sql_temp;

CREATE TABLE main_sql_temp as SELECT * FROM main_sql;


CREATE TABLE shop_sql_temp as SELECT * FROM shop_sql;


delete from main_sql;

delete from shop_sql;