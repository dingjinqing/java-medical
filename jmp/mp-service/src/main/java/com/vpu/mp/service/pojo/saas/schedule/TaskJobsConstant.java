package com.vpu.mp.service.pojo.saas.schedule;

import com.vpu.mp.config.mq.RabbitConfig;

/**
 * TaskJobsMain的TypeConstant
 * @author 卢光耀
 * @date 2019-08-13 15:53
 *
*/
public class TaskJobsConstant {


    public static final Byte STATUS_NEW = 0;

    public static final Byte STATUS_EXECUTING = 1;

    public static final Byte STATUS_COMPLETE = 2;

    public static final Byte STATUS_TERMINATION = 3;

    /** 立即执行 */
    public static final Byte TYPE_ONCE = 1;
    /** 循环执行（但只执行一次）且schedule值不能为NULL */
    public static final Byte TYPE_CYCLE_ONCE = 2;

    @Deprecated
    /** 循环执行（每天都执行）且schedule值不能为NULL */
    public static final Byte TYPE_CYCLE_EVERYDAY = 3;
    /** 定时执行 startTime不能为NULL */
    public static final Byte EXECUTION_TIMING = 4;


    /**
     * 定时任务路由绑定
     * executionType和{@link com.vpu.mp.service.pojo.saas.schedule.TaskJobsConstant}
     * 保持一致
     * @author 卢光耀
     * @date 2019-08-16 14:11
     *
     */
    public enum  TaskJobEnum {
        /** 消息推送任务 */
        SEND_MESSAGE(1001, RabbitConfig.EXCHANGE_MARKETING,RabbitConfig.BINDING_EXCHANGE_MESSAGE_KEY),
    	/**获取关注公众号的用户信息*/
        MP_BIND_MA(1002, RabbitConfig.EXCHANGE_WX, RabbitConfig.BINDING_MA_MAP_BIND_KEY),
    	/**批量提交小程序*/
    	BATCH_UPLOAD(1003, RabbitConfig.EXCHANGE_WX, RabbitConfig.BINDING_BATCH_UPLOAD_KEY),
    	/**定向发券*/
        GIVE_COUPON(1004, RabbitConfig.EXCHANGE_MARKETING, RabbitConfig.BINDING_EXCHANGE_COUPON_KEY),
    	/**订单 */
    	WX_IMPORTORDER(1006, RabbitConfig.EXCHANGE_WX, RabbitConfig.BINDING_EXCHANGE_IMPORTORDER_KEY),
        WX_CLOSEORDER(1010, RabbitConfig.EXCHANGE_WX, RabbitConfig.BINDING_EXCHANGE_CLOSE_ORDER_KEY),
    	/**收藏 */
    	WX_ADDSHOPPINGLIST(1007, RabbitConfig.EXCHANGE_WX, RabbitConfig.BINDING_EXCHANGE_ADDSHOPPINGLIST_KEY),
    	/**物品信息 */
    	WX_IMPORTPRODUCT(1008, RabbitConfig.EXCHANGE_WX, RabbitConfig.BINDING_EXCHANGE_IMPORTPRODUCT_KEY),
    	/** 读取Excel用，也可以其他未分类异步任务使用*/
    	OTHER_MQ(1009, RabbitConfig.EXCHANGE_MARKETING, RabbitConfig.BINDING_EXCHANGE_OTHER_KEY);
    	
        private Integer executionType;
        private String exchangeName;
        private String routingKey;
        private TaskJobEnum(Integer executionType,String exchangeName,String routingKey){
            this.exchangeName = exchangeName;
            this.executionType = executionType;
            this.routingKey = routingKey;
        }
        public  Integer getExecutionType(){
            return this.executionType;
        }

        public String getRoutingKey() {
            return routingKey;
        }

        public String getExchangeName() {
            return exchangeName;
        }

        public static TaskJobEnum getTaskJobEnumByExecutionType(Integer executionType){
            for (TaskJobEnum job: TaskJobEnum.values()) {
                if(job.getExecutionType().equals(executionType)){
                    return job;
                }
            }
            return null;
        }
    }
}
