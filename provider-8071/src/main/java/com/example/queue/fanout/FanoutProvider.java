package com.example.queue.fanout;

import com.example.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @Author xue
 * @Date 2020/6/15 14:23
 * @Version 1.0
 * Buddha bless, never bug!
 */
public class FanoutProvider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机     参数1：交换机名称   参数2：交换机类型（这里做广播测试，所以是fanout）
        channel.exchangeDeclare("logs","fanout");
        //发布消息 参数1：交换机名称    参数2：路由Key（广播中无意义）   参数3：持久化方式
        channel.basicPublish("logs","",null,"hello_fanout".getBytes());
        RabbitMQUtils.closeChanelAndConnection(channel,connection);
    }
}
