package com.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.listener.ChannelTopic;


@SpringBootApplication
@ComponentScan
public class Application implements CommandLineRunner {

    @Autowired
    RedisPublisher redisPublisher;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

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
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < 10; i++) {
                        redisPublisher.publish(channelTopic, new RedisMessage(i + 1));
                    }
                }
            }).run();
        }


    }
}
