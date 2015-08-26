package com.messaging;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class RedisConsumer implements MessageListener {

    MessageSerializer serializer = new MessageSerializer();
    AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void onMessage(Message message, byte[] pattern) {

        Object obj = serializer.deserialize(message.getBody());
        if(obj != null && obj instanceof RedisMessage) {
            System.err.println("Received message(" + atomicInteger.incrementAndGet() + ") " + obj.toString());
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