package com.vpu.mp.config.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitBootstrapConfiguration;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitMq相关配置
 * @author 卢光耀
 * @date 2019-07-30 10:40
 *
*/
@Import(RabbitBootstrapConfiguration.class)
@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    private Integer port;

    @Value("${rabbitmq.username}")
    private String userName;

    @Value("${rabbitmq.password}")
    private String password;


    /** 发送失败消息默认存放队列 */
    public static final String QUEUE_ERROR_SEND = "error.send";
    /** 处理失败消息默认存放队列 */
    public static final String QUEUE_ERROR_DEAL = "error.deal";
    /** 发送优惠券默认存放队列 */
    public static final String QUEUE_COUPON_SEND = "marketing.coupon";
    /** 发送消息默认存放队列 */
    public static final String QUEUE_MESSAGE_SEND = "marketing.message";
    /** 获取公众号关注用户*/
    public static final String QUEUE_MA_MAP_BIND="bind.mamp.queue";
    /**批量提交小程序 */
    public static final String QUEUE_BATCH_UPLOAD="batch.upload.queue";
    /** ES的路由 */
    public static final String QUEUE_ES_GOODS = "es.goods";
    /*************好物圈队列start************/
    /** 订单*/
    public static final String QUEUE_WX_MALL_IMPORTORDER = "wx.mall.importorder";
    /** 收藏*/
    public static final String QUEUE_WX_MALL_ADDSHOPPINGLIST = "wx.mall.addshoppinglist";
    /** 物品信息*/
    public static final String QUEUE_WX_MALL_IMPORTPRODUCT = "wx.mall.importproduct";
    /*************好物圈队列end************/
    /**ElasticSearch--商品标签*/
    public static final String QUEUE_ES_LABEL = "es.label";

    /**
     * 路由和队列的对应关系是1:n不是1:1(路由按照模块区分)
     */
    /** 发送失败队列存储的路由 */
    public static final String EXCHANGE_ERROR = "direct.error";
    /** 营销功能的路由 */
    public static final String EXCHANGE_MARKETING = "direct.marketing";
    /** ES的路由 */
    public static final String EXCHANGE_ES = "direct.es";
    /** 微信的路由(所有跟微信相关的队列都由这个路由转发) */
    public static final String EXCHANGE_WX = "direct.wx";
    /**订单的路由*/
    public static final String EXCHANGE_ORDER = "direct.order";




    /** 发送失败路由键 */
    public static final String BINDING_EXCHANGE_ERROR_KEY = "direct.error.send";
    /** 处理失败路由键 */
    public static final String BINDING_EXCHANGE_DEAL_KEY = "direct.error.deal";
    /** 发送优惠券路由键 */
    public static final String BINDING_EXCHANGE_COUPON_KEY = "direct.marketing.coupon";
    /** 发送消息路由键 */
    public static final String BINDING_EXCHANGE_MESSAGE_KEY = "direct.marketing.message";
    /** 获取公众号关注用户的路由键*/
    public static final String BINDING_MA_MAP_BIND_KEY="bind.mamp.key";
    /** 批量提交小程序*/
    public static final String BINDING_BATCH_UPLOAD_KEY="bind.batch.upload";
    /** 批量提交小程序*/
    public static final String BINDING_ES_GOODS_KEY = "bind.es.goods";
    /*************好物圈路由键start************/
    /**
     * 好物圈订单
     */
	public static final String BINDING_EXCHANGE_IMPORTORDER_KEY = "bind.wx.importorders";
	public static final String BINDING_EXCHANGE_ADDSHOPPINGLIST_KEY = "bind.wx.addshoppinglist";
	public static final String BINDING_EXCHANGE_IMPORTPRODUCT_KEY = "bind.wx.importproducts";
    /*************好物圈队列end************/
    /**商品标签缓存数据一致性*/
    public static final String BINDING_EXCHANGE_ES_GOODS_LABEL_KEY = "bind.es.goods.label";
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }
    @Bean
    public SimpleRabbitListenerContainerFactory
    simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory =
                new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        simpleRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return simpleRabbitListenerContainerFactory;
    }
    @Bean(name="currentSimpleRabbitListenerFactory")
    public SimpleRabbitListenerContainerFactory
    currentSimpleRabbitListenerFactory(ConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory =
            new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConcurrentConsumers(5);
        simpleRabbitListenerContainerFactory.setPrefetchCount(5);
        simpleRabbitListenerContainerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        simpleRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return simpleRabbitListenerContainerFactory;
    }
    @Lazy
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         ErrorCallback errorCallback){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(errorCallback);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    /**
     * 1.队列名字
     * 2.durable="true" 是否持久化 rabbitmq重启的时候不需要创建新的队列
     * 3.auto-delete    表示消息队列没有在使用时将被自动删除 默认是false
     * 4.exclusive      表示该消息队列是否只在当前connection生效,默认是false
     * @return 发送错误存储队列
     */
    @Bean
    public Queue errorSendQueue() {
        Map<String,Object> args = new HashMap<String,Object>(1);
        args.put("x-max-length",10000);
        return new Queue(QUEUE_ERROR_SEND,true,false,false,args);
    }
    /**
     * @return 处理错误存储队列
     */
    @Bean
    public Queue errorDealWithQueue() {
        Map<String,Object> args = new HashMap<String,Object>(1);
        args.put("x-max-length",10000);
        return new Queue(QUEUE_ERROR_DEAL,true,false,false,args);
    }
    /**
     * @return 发送模版消息队列
     */
    @Bean
    public Queue sendMessageQueue() {
        return new Queue(QUEUE_MESSAGE_SEND,true,false,false);
    }
    /**
     * @return 发送优惠券队列
     */
    @Bean
    public Queue sendCouponWithQueue() {
        return new Queue(QUEUE_COUPON_SEND, true, false, false);
    }

    /**
     * @return 发送优惠券队列
     */
    @Bean
    public Queue sendEsGoodsWithQueue() {
        return new Queue(QUEUE_ES_GOODS,true,false,false);
    }

    /**
     * @return 获取关注公众号的用户信息
     */
    @Bean
    public Queue sendMpMABindQueue() {
    	return new Queue(QUEUE_MA_MAP_BIND,true);
    }

    /**
     * @return 批量提交小程序和公众号
     */
    @Bean
    public Queue batchUploadQueue() {
    	return new Queue(QUEUE_BATCH_UPLOAD,true);
    }

    /**
     * @return 微信好物圈
     */
    @Bean
    public Queue sendWxMallTdQueue() {
    	return new Queue(QUEUE_WX_MALL_IMPORTORDER,true);
    }
    @Bean
    public Queue sendWxMallScQueue() {
    	return new Queue(QUEUE_WX_MALL_ADDSHOPPINGLIST,true);
    }
    @Bean
    public Queue sendWxMallWpQueue() {
    	return new Queue(QUEUE_WX_MALL_IMPORTPRODUCT,true);
    }
    /**
     * @return es label queue
     */
    @Bean
    public Queue cacheGoodsLabelWithQueue() {
        return new Queue(QUEUE_ES_LABEL,true,false,false);
    }
    /**
     * 1.路由名字
     * 2.durable="true" 是否持久化 rabbitmq重启的时候不需要创建新的交换机
     * 3.autoDelete    当所有消费客户端连接断开后，是否自动删除队列
     * @return 错误存储路由
     */
    @Bean
    public DirectExchange errorExchange(){
        return new DirectExchange(EXCHANGE_ERROR,true,false);
    }
    /**
     * @return es路由
     */
    @Bean
    public DirectExchange esExchange(){
        return new DirectExchange(EXCHANGE_ES,true,false);
    }
    /**
     * @return 营销存储路由
     */
    @Bean
    public DirectExchange marketingExchange(){
        return new DirectExchange(EXCHANGE_MARKETING, true, false);
    }

    /**
     * @return 订单支付模板消息路由
     */
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(EXCHANGE_ORDER,true,false);
    }

    @Bean
    public DirectExchange wxExchange() {
        return new DirectExchange(EXCHANGE_WX,true,false);
    }



    /**
     * @return 路由和队列绑定
     */
    @Bean
    public Binding bindingErrorSend(){
        return BindingBuilder.bind(errorSendQueue()).to(errorExchange()).with(BINDING_EXCHANGE_ERROR_KEY);
    }
    @Bean
    public Binding bindingErrorDeal(){
        return BindingBuilder.bind(errorDealWithQueue()).to(errorExchange()).with(BINDING_EXCHANGE_DEAL_KEY);
    }
    @Bean
    public Binding bindingEs(){
        return BindingBuilder.bind(sendEsGoodsWithQueue()).to(esExchange()).with(BINDING_ES_GOODS_KEY);
    }
    @Bean
    public Binding bindingCouponSend(){
        return BindingBuilder.bind(sendCouponWithQueue()).to(marketingExchange()).with(BINDING_EXCHANGE_COUPON_KEY);
    }

    @Bean
    public Binding bindingMessageSend(){
        return BindingBuilder.bind(sendMessageQueue()).to(marketingExchange()).with(BINDING_EXCHANGE_MESSAGE_KEY);
    }
    /**
     * 获取关注公众号的用户信息
     * @return
     */
    @Bean
    public Binding bindingTemplateSend() {
    	return BindingBuilder.bind(sendMpMABindQueue()).to(wxExchange()).with(BINDING_MA_MAP_BIND_KEY);
    }


    @Bean
    public Binding batchUpload() {
    	return BindingBuilder.bind(batchUploadQueue()).to(wxExchange()).with(BINDING_BATCH_UPLOAD_KEY);
    }

    /**
     * 微信好物圈 订单  收藏  物品信息
     * @return
     */
    @Bean
    public Binding bindingWxMallTd() {
    	return BindingBuilder.bind(sendWxMallTdQueue()).to(wxExchange()).with(BINDING_EXCHANGE_IMPORTORDER_KEY);
    }
    @Bean
    public Binding bindingWxMallSc() {
    	return BindingBuilder.bind(sendWxMallScQueue()).to(wxExchange()).with(BINDING_EXCHANGE_ADDSHOPPINGLIST_KEY);
    }
    @Bean
    public Binding bindingWxMallWp() {
    	return BindingBuilder.bind(sendWxMallWpQueue()).to(wxExchange()).with(BINDING_EXCHANGE_IMPORTPRODUCT_KEY);
    }
    @Bean
    public Binding bindingCacheGoodsLabel() {
        return BindingBuilder.bind(cacheGoodsLabelWithQueue()).to(esExchange()).with(BINDING_EXCHANGE_ES_GOODS_LABEL_KEY);
    }
}
