package com.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;


@Component
public class MessageSubscriber implements MessageListener {

    private final LinkedBlockingQueue<Message> messageReceiveQueue;

    @Autowired
    public MessageSubscriber(LinkedBlockingQueue<Message> messageReceiveQueue) {
        this.messageReceiveQueue = messageReceiveQueue;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            messageReceiveQueue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       /* Object obj = serializer.deserialize(message.getBody());
        if(obj != null && obj instanceof RedisMessage) {
            System.err.println("Received message(" + atomicInteger.incrementAndGet() + ") " + obj.toString());
        }
*/
    }


}