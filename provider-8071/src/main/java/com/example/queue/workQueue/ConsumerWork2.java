package com.example.queue.workQueue;

import com.example.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @Author xue
 * @Date 2020/6/12 18:03
 * @Version 1.0
 * Buddha bless, never bug!
 */
public class ConsumerWork2 {
    public static void main(String[] args) throws IOException {
        //创建连接
        Connection connection= RabbitMQUtils.getConnection();
        //创建队列
        Channel channel=connection.createChannel();
        //通道绑定对象（必须和生产者参数一致，否则可能出现异常（非持久化队列不能消费持久化的数据））
        channel.queueDeclare("hello", true, false, false, null);

        //每次只允许消费一个
        channel.basicQos(1);
        channel.basicConsume("hello",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(100);
                    System.out.println("消费者2-"+new String(body));
                    //每次消费完  提交确认
                    //参数1：提交的哪个消息（消息名字）     参数2：是否允许同时提交多个消息确认
                    channel.basicAck(envelope.getDeliveryTag(),false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
