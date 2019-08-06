package com.vpu.mp.service.foundation.mq.handler;

import com.rabbitmq.client.Channel;

import java.io.IOException;

/**
 * MQ消费者默认实现接口
 * 所有消费者都要手动ACK，确保消息被消费
 * @author 卢光耀
 * @date 2019-08-05 15:58
 *
*/
public interface BaseRabbitHandler {
    /**
     * 发送成功
     * @param channel mq连接信道
     * @param tag 消息的唯一标识
     * @throws IOException 异常
     */
    default void success(Channel channel, Long tag) throws IOException {
        channel.basicAck(tag,false);
    }

    /**
     * 发送失败(消息返回原队列)
     * @param channel mq连接信道
     * @param tag 消息的唯一标识
     * @throws IOException 异常
     */
    default void failReturn(Channel channel,Long tag) throws IOException{
        channel.basicNack(tag,false,true);
    }

    /**
     * 发送失败(消息不返回原队列,但自己创建的的队列需要设相应的死信队列)
     * @param channel mq连接信道
     * @param tag 消息的唯一标识
     * @throws IOException 异常
     */
    default void failNotReturn(Channel channel,Long tag) throws IOException{
        channel.basicNack(tag,false,false);
    }
}
