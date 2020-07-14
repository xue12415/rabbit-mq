package com.example.queue.simple;

import com.example.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author xue
 * @Date 2020/6/12 15:01
 * @Version 1.0
 * Buddha bless, never bug!
 */
public class ProviderSimple {

    public static void testSend() throws IOException, TimeoutException {
      /*  //创建一个mq连接工厂
        ConnectionFactory connectionFactory=new ConnectionFactory();
        //设置连接rabbitmq的主机
        connectionFactory.setHost("127.0.0.1");
        //设置端口号
        connectionFactory.setPort(5672);
        //设置连接哪个虚拟主机
        connectionFactory.setVirtualHost("/msg");
        //设置虚拟主机的用户名和密码
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");

        //获取连接对象
        Connection connection=connectionFactory.newConnection();*/

        //通过工具类创建连接
        Connection connection = RabbitMQUtils.getConnection("/msg", "admin", "123456");

        //获取连接中的通道对象
        Channel channel=connection.createChannel();

        //通道绑定相应的消息队列（创建队列（通道），不一定非得向这个队列发消息）
        //参数1:队列名称，如果队列不存在则自动创建该队列
        //参数2：队列是否需要持久化（false   在rabbitmq重启之后  未被消费的消息将会丢失，设置为true则保留队列不保留消息）
        //参数3：是否独占队列
        //参数4：是否在消费完成后自动删除队列（如果不关闭消费者，，则无法删除队列，因为通道被占用）
        //参数5：附加参数
        channel.queueDeclare("hello",true,false,false,null);

        //发布消息（真正发送消息到的队列）
        //参数1：交换机名称 参数2：队列名称    参数3：消息额外参数（可设置持久化方式）保留消息    参数4：消息内容
        channel.basicPublish("","hello", MessageProperties.PERSISTENT_TEXT_PLAIN,"hello rabbit_mq".getBytes());

        //关闭通道
        channel.close();
        //关闭连接
        connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        testSend();
    }
}
