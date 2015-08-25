package com.messaging;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;


@Component
public class Consumer implements MessageListener {

    int count = 0;
    //protected  CountDownLatch latch = new CountDownLatch(10);


    @Override
    public void onMessage(Message message, byte[] pattern) {
        count++;
        System.out.println("Received message(" + count + "): " + message.toString());
        //latch.countDown();
    }
}