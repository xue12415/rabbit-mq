package com.example.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author xue
 * @Date 2020/6/12 16:49
 * @Version 1.0
 * Buddha bless, never bug!
 */
public class RabbitMQUtils {
    private static ConnectionFactory connectionFactory;

    static {//重量级资源，放入静态代码块，只在类创建时执行一次
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);

    }

    public static Connection getConnection(String virtualHost, String userName, String password) {
        try {
            connectionFactory.setVirtualHost(virtualHost);
            connectionFactory.setUsername(userName);
            connectionFactory.setPassword(password);
            return connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Connection getConnection() {
        try {
            connectionFactory.setVirtualHost("/msg");
            connectionFactory.setUsername("admin");
            connectionFactory.setPassword("123456");
            return connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeChanelAndConnection(Channel channel, Connection connection) {
        try {
            if (channel != null)
                channel.close();
            if (connection != null)
                connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
