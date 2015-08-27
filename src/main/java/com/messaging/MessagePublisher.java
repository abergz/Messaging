package com.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic channelTopic, Object channelMessage) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), channelMessage);
    }
}
