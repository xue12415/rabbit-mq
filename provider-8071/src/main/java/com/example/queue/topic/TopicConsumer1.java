package com.example.queue.topic;

import com.example.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author xue
 * @Date 2020/6/15 16:22
 * @Version 1.0
 * Buddha bless, never bug!
 */
public class TopicConsumer1 {
    public static void main(String[] args) throws IOException {
        Connection connection= RabbitMQUtils.getConnection();
        Channel channel =connection.createChannel();

        //创建临时队列
        String queue=channel.queueDeclare().getQueue();
        //声明交换机及类型
        channel.exchangeDeclare("topics","topic");
        String routingKey="user.*";
        //绑定队列和交换机
        channel.queueBind(queue,"topics",routingKey);

        //消费消息
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1 ---"+routingKey+"========="+new String(body));
            }
        });

    }
}
