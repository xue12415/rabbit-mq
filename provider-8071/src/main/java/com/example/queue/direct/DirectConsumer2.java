package com.example.queue.direct;

import com.example.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author xue
 * @Date 2020/6/15 15:41
 * @Version 1.0
 * Buddha bless, never bug!
 */
public class DirectConsumer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel=connection.createChannel();
        channel.exchangeDeclare("logs_direct","direct");
        String queue=channel.queueDeclare().getQueue();
        channel.queueBind(queue,"logs_direct","info");
        channel.queueBind(queue,"logs_direct","warning");
        channel.queueBind(queue,"logs_direct","error");
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2====="+new String(body));
            }
        });
    }
}
