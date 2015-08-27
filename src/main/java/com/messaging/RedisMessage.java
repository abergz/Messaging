package com.messaging;

import java.io.Serializable;

public class RedisMessage implements Serializable {

    String message;

    public int getId() {
        return id;
    }

    int id;

    public RedisMessage(int id) {
        this.id = id;
        message = "message id: " + id;
    }

    @Override
    public String toString() {
        return message;
    }

}
