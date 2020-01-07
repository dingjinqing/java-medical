/***********************2.8********************BEGIN*/


-- 20200107 常乐 修改user表 invitation_code字段类型;
alter table `b2c_user` modify `invitation_code` varchar(32);
alter table `b2c_distributor_apply` add column `is_auto_pass` tinyint(2) not null default 0 comment'审核类型 0：手动审核；1"自动审核';

-- 2020年1月7日 16:21:58 孔德成 修改商品范围备注
ALTER TABLE `b2c_free_shipping` MODIFY COLUMN `type` tinyint(1) NOT NULL COMMENT '条件 1全部 0部分' AFTER `end_time`;

/***********************2.8*********************END*/
