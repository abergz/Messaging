package com.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class RedisPublisher<K, V> {

    private RedisTemplate<K, V> redisTemplate;

    public RedisPublisher(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    ChannelTopic channelTopic;


    AtomicLong atomicCounter = new AtomicLong(0);

    public void publish() {
        redisTemplate.convertAndSend(channelTopic.getTopic(), new SendObj());
        /*redisTemplate.convertAndSend( channelTopic.getTopic(), "Message " + atomicCounter.incrementAndGet() +
                ", " + Thread.currentThread().getName() );*/
    }
}
