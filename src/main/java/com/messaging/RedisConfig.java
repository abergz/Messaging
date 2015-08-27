package com.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.LinkedBlockingQueue;


@Configuration
public class RedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {

        RedisSentinelConfiguration sentinelConfig =
                new RedisSentinelConfiguration()
                        .master("mymaster")
                        .sentinel("127.0.0.1", 26379)
                        .sentinel("127.0.0.1", 26380);

        //return new JedisConnectionFactory(sentinelConfig);

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig); //(sentinel,pool)
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        return jedisConnectionFactory;
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    LinkedBlockingQueue<Message> linkedQueue() {
        return new LinkedBlockingQueue<Message>(100);
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new RedisConsumer(linkedQueue()));
    }

    @Bean
    RedisMessageListenerContainer redisContainer() { //JedisConnectionFactory jedisConnectionFactory, MessageListener messageListenerAdapter) {
        final RedisMessageListenerContainer redisContainer = new RedisMessageListenerContainer();
        redisContainer.setConnectionFactory(jedisConnectionFactory());
        //redisContainer.addMessageListener(messageListenerAdapter, channelTopic2());
        return redisContainer;
    }
/*
    @Bean
    ChannelTopic channelTopic2() {
        return new ChannelTopic("pubsub:queue2");
    }*/


}
