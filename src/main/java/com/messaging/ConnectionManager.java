package com.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class ConnectionManager {

    //@Autowired
    private final RedisMessageListenerContainer redisContainer;
    private final MessageListenerAdapter messageListenerAdapter;

    @Autowired
    public ConnectionManager(RedisMessageListenerContainer redisContainer, MessageListenerAdapter messageListenerAdapter) {
        this.redisContainer = redisContainer;
        this.messageListenerAdapter = messageListenerAdapter;
    }

    public ChannelTopic subscribe(String channelTopic) {
        ChannelTopic channel = new ChannelTopic(channelTopic);
        redisContainer.addMessageListener(messageListenerAdapter, channel);
        return channel;
    }
}
