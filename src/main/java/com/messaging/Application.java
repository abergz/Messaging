package com.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    RedisPublisher<String, Object> redisPublisher;
    //Producer producer;

    @Autowired
    Consumer consumer;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(args != null && args[0].equals("produce")) {
            System.out.println("Producing messages...");
            for(int i = 0; i < 10; i++) {
                redisPublisher.publish();
            }
            System.out.println("Done producing messages.");
            //consumer.latch.await(10000, TimeUnit.MILLISECONDS);
            System.exit(0);
        }
    }
}
