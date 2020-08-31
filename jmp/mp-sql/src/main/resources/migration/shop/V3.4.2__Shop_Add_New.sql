/*********************3.4***********************BEGIN*/
-- 埋点表
create table if not exists `b2c_anchor_points` (
    `id`          int(11)      not null auto_increment comment 'id',
    `event`       varchar(255) not null default '' comment '事件',
    `event_name`       varchar(255) not null default '' comment '事件名称',
    `event_type`       tinyint(1) not null default  0 comment '事件类型 0前段 1后端',
    `page`        varchar(255) not null default '' comment '页面',
    `module`    varchar(255) not null default '' comment '功能模块',
    `platform`    tinyint(1)   not null default 0 comment '平台 1 wxapp 2admin',
    `device`      tinyint(1)   not null default 0 comment '设备 1 android 2 ios  3 pc',
    `store_id`    int(11)      not null default 0 comment '门店id',
    `user_id`     int(11)      not null default 0 comment '用户id',
    `key`         varchar(255) not null default '' comment '参数',
    `value`       varchar(255) not null default '' comment '参数值',
    `update_time` datetime     not null default current_timestamp on update current_timestamp comment '更新时间',
    `create_time` datetime     not null default current_timestamp comment '添加时间',
    primary key (`id`)
) comment ='埋点';

/*********************3.4*************************END*/
