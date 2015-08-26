package com.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        RedisSentinelConfiguration sentinelConfig =
                new RedisSentinelConfiguration()
                        .master("mymaster")
                        .sentinel("127.0.0.1", 26379)
                        .sentinel("127.0.0.1", 26380);

        //return new JedisConnectionFactory(sentinelConfig);
/*
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
*/
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();//poolConfig); //(sentinel,pool)
        jedisConnectionFactory.setUsePool(false);
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        return jedisConnectionFactory;
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new RedisConsumer());
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory redisConnectionFactory) {//, MessageListener messageListenerAdapter) {
        final RedisMessageListenerContainer redisContainer = new RedisMessageListenerContainer();
        redisContainer.setConnectionFactory(redisConnectionFactory);
        //redisContainer.addMessageListener(messageListenerAdapter, topic());
        return redisContainer;
    }
/*
    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("pubsub:queue2");
    }*/


}
