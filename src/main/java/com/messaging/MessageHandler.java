package com.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@Component
public class MessageHandler {

    private final LinkedBlockingQueue<Message> messageReceiveQueue;

    @Autowired
    public MessageHandler(LinkedBlockingQueue<Message> messageReceiveQueue) {
        this.messageReceiveQueue = messageReceiveQueue;
    }
}
