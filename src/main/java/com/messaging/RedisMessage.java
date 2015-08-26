package com.messaging;

import java.io.Serializable;
import java.util.Random;

public class RedisMessage implements Serializable {

    protected byte[] bytes;
    int id;

    public RedisMessage(int id) {
        this.id = id;
        bytes = new byte[10];
        new Random().nextBytes(bytes);
    }

    @Override
    public String toString() {
        String s = " <" + id + "> ";
        for (int i = 0; i < 5; i++) {
            s += String.valueOf(bytes[i]) + " ";
        }
        return s;
    }

}
