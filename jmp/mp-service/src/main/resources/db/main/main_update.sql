/*********************3.3***********************BEGIN*/
-- 店铺短信配置表
create table if not exists `b2c_shop_sms_config` (
    `id` int(11) not null auto_increment,
    `shop_id` int(11) not null comment '店铺id',
    `user_check_code_num` int(11) not null default 15 comment '每个用户可发送验证码数量',
    `patient_check_code_num` int(11) not null default 15 comment '每个患者可发送验证码数量',
    `marketing_num` int(11) not null default 2000 comment '每天可发送营销短信数量',
    `industry_num` int(11) not null default 2000 comment '每天可发送行业短信数量',
    `is_delete`     tinyint(1)   not null default '0',
    `create_time`   timestamp    not null default current_timestamp,
    `update_time`   timestamp    not null default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key (`id`)
)comment = '店铺短信配置表';
/*********************3.3*************************END*/