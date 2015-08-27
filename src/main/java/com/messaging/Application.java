package com.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;


@SpringBootApplication
@ComponentScan
public class Application implements CommandLineRunner {

    @Autowired
    RedisPublisher redisPublisher;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
/*
    @Autowired
    LinkedBlockingQueue<Message> linkedQueue;
*/
    @Autowired
    ConnectionManager connectionManager;


    @Override
    public void run(final String... args) throws Exception {
        final ChannelTopic channelTopic = connectionManager.subscribe("pubsub:queue");
        if (args != null && args[0].equals("produce")) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        MessageSerializer serializer = new MessageSerializer();

                        Thread.sleep(100);

                        for (int i = 0; i < 100; i++) {
                            redisPublisher.publish(channelTopic, new RedisMessage(i + 1));
                        }
                        System.err.println("done");
                        Thread.sleep(100);

                        //System.err.println("Num elements: " + linkedQueue.size());

                        int messageCount = 0;
/*
                        while (linkedQueue.size() > 0) {

                            Message message = linkedQueue.take();
                            RedisMessage redisMessage = (RedisMessage) serializer.deserialize(message.getBody());

                            System.err.println("Received message(" + ++messageCount + ") " + redisMessage.toString());

                            if (messageCount != redisMessage.getId()) {
                                System.err.println("Message arrived in wrong order");
                                break;
                            }
                        }
*/
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).run();
        }


    }

    class MessageSerializer implements RedisSerializer<Object>

    {
        @Override
        public byte[] serialize(Object object) throws SerializationException {
            try {
                ByteArrayOutputStream boas = new ByteArrayOutputStream();
                ObjectOutputStream ous = new ObjectOutputStream(boas);
                ous.writeObject(object);
                byte[] bytes = boas.toByteArray();
                ous.close();
                return bytes;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        public Object deserialize(byte[] bytes) throws SerializationException {
            try {
                ByteArrayInputStream boas = new ByteArrayInputStream(bytes);
                ObjectInputStream ous = new ObjectInputStream(boas);
                Object object = ous.readObject();
                ous.close();
                return object;
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;

        }
    }
}
