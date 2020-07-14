package com.example.queue.workQueue;

import com.example.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @Author xue
 * @Date 2020/6/12 18:04
 * @Version 1.0
 * Buddha bless, never bug!
 */
public class ProvideWork {
    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection=RabbitMQUtils.getConnection();
        //创建通道
        Channel channel=connection.createChannel();
        channel.queueDeclare("hello",true,false,false,null);

        //发送消息
        for (int i=1;i<=100;i++){
            channel.basicPublish("","hello",null,(i+"-work queue").getBytes());
        }
        //关闭流
        RabbitMQUtils.closeChanelAndConnection(channel,connection);
    }
}
