/***********************2.8********************BEGIN*/




/***********************2.8*********************END*/

/***********************2.10********************BEGIN*/
ALTER TABLE `b2c_shop` ADD COLUMN `expire_time` timestamp NULL COMMENT '到期时间';
-- 20200527 李晓冰 扩充b2c_task_job_content content字段长度
ALTER TABLE b2c_task_job_content MODIFY COLUMN content MEDIUMTEXT COMMENT '消息内容';
/***********************2.10*********************END*/