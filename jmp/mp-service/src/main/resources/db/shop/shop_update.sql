/***********************2.8********************BEGIN/
--20200107 常乐 修改user表 invitation_code字段类型;
alter table `b2c_user` modify `invitation_code` varchar(32);
alter table `b2c_distributor_apply` add column `is_auto_pass` tinyint(2) not null default 0 comment'审核类型 0：手动审核；1"自动审核';



/***********************2.8*********************END/