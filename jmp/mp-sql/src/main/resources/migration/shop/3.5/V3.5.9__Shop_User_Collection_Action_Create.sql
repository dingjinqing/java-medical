create table `b2c_user_collection_action`(
    `id`   int(11)      not null auto_increment,
    `user_id` int(11) not null comment '用户ID',
    `goods_id` int(11) not null comment '商品ID',
    `collection_type`     tinyint(1)   not null default '1' comment '收藏/取消：1：收藏，-1：取消',
    `create_time`   timestamp    not null default current_timestamp,
    `update_time`   timestamp    not null default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key(`id`)
)comment ='用户收藏药品记录';