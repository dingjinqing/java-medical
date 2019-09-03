package com.vpu.mp.config.mq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * rabbitMq相关配置
 * @author 卢光耀
 * @date 2019-07-30 10:40
 *
*/
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



    /** 发送失败队列存储的路由 */
    public static final String EXCHANGE_ERROR = "direct.error";
    
    /** 营销功能的路由 */
    public static final String EXCHANGE_MARKETING = "direct.marketing";
    /**获取关注公众号的用户信息*/
    public static final String EXCHANGE_MA_MAP_BIND="bind.mamp.template";

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
     * @return 发送消息队列
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
        return new Queue(QUEUE_COUPON_SEND,true,false,false);
    }
    
    /**
     * 	获取关注公众号的用户信息
     * @return
     */
    @Bean
    public Queue sendMpMABindQueue() {
    	return new Queue(QUEUE_MA_MAP_BIND,true);
    }
    /**
     * 1.交换机名字
     * 2.durable="true" 是否持久化 rabbitmq重启的时候不需要创建新的交换机
     * 3.autoDelete    当所有消费客户端连接断开后，是否自动删除队列
     * @return 错误存储路由
     */
    @Bean
    public DirectExchange errorExchange(){
        return new DirectExchange(EXCHANGE_ERROR,true,false);
    }
    
    /**
     * @return 营销存储路由
     */
    @Bean
    public DirectExchange marketingExchange(){
        return new DirectExchange(EXCHANGE_MARKETING,true,false);
    }
    
    @Bean
    public DirectExchange mpTemplateExchange() {
    	return new DirectExchange(EXCHANGE_MA_MAP_BIND,true,false);    }
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
    	return BindingBuilder.bind(sendMpMABindQueue()).to(mpTemplateExchange()).with(BINDING_MA_MAP_BIND_KEY);
    }
}
