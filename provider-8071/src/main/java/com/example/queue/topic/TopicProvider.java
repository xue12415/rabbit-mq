package com.example.queue.topic;

import com.example.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @Author xue
 * @Date 2020/6/15 16:22
 * @Version 1.0
 * Buddha bless, never bug!
 */
public class TopicProvider {
    public static void main(String[] args) throws IOException {
        Connection connection= RabbitMQUtils.getConnection();
        Channel channel=connection.createChannel();

        //声明交换机及类型
        channel.exchangeDeclare("topics","topic");
        String routingKey="user.name.age";

        //发送消息
        channel.basicPublish("topics",routingKey,null,("Topics测试，routingKey:["+routingKey+"]").getBytes());
        //关闭资源
        RabbitMQUtils.closeChanelAndConnection(channel,connection);
    }
}
