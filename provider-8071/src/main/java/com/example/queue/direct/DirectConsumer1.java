package com.example.queue.direct;

import com.example.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author xue
 * @Date 2020/6/15 15:34
 * @Version 1.0
 * Buddha bless, never bug!
 */
public class DirectConsumer1 {
    public static void main(String[] args) throws IOException {
        Connection connection= RabbitMQUtils.getConnection();
        Channel channel=connection.createChannel();
        //通道声明交换机以及类型
        channel.exchangeDeclare("logs_direct","direct");
        //创建临时队列
        String queue=channel.queueDeclare().getQueue();
        //队列绑定交换机
        channel.queueBind(queue,"logs_direct","error");
        //获取消息病消费
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1======"+new String(body));
            }
        });
    }
}
