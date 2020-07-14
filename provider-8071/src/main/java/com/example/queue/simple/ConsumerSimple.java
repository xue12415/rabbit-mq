package com.example.queue.simple;

import com.example.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author xue
 * @Date 2020/6/12 16:18
 * @Version 1.0
 * Buddha bless, never bug!
 */
public class ConsumerSimple {
    public static void testConsumer() throws IOException, TimeoutException {
       /* //创建工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/msg");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        //创建连接
        Connection connection = connectionFactory.newConnection();*/

        //通过工具类创建连接
        Connection connection = RabbitMQUtils.getConnection("/msg", "admin", "123456");
        //创建通道
        Channel channel = connection.createChannel();
        //通道绑定对象（必须和生产者参数一致，否则可能出现异常（非持久化队列不能消费持久化的数据））
        channel.queueDeclare("hello", true, false, false, null);
        //消费消息
        //参数1：消费队列名称
        //参数2：开启消息自动确认机制
        //参数3：消费时的回掉接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            //最后一个参数：消息队列中取出的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("message:" + new String(body));
            }
        });
        //一直运行，不建议关闭
        //channel.close();
        //connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        testConsumer();
    }
}
