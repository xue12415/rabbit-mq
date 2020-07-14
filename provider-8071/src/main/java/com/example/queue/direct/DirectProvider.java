package com.example.queue.direct;

import com.example.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @Author xue
 * @Date 2020/6/15 15:23
 * @Version 1.0
 * Buddha bless, never bug!
 */
public class DirectProvider {
    public static void main(String[] args) throws IOException {
        Connection connection= RabbitMQUtils.getConnection();
        Channel channel=connection.createChannel();
        //通道声明交换机   参数1：交换机名称   参数2：交换机类型
        channel.exchangeDeclare("logs_direct","direct");
        //路由key 发送消息
        String routingKey="error";
        channel.basicPublish("logs_direct",routingKey,null,("Direct模型发布的基于route key:["+routingKey+"]").getBytes());
        //关闭资源
        RabbitMQUtils.closeChanelAndConnection(channel,connection);
    }
}
