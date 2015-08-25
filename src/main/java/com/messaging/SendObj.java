package com.messaging;

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by erik on 19/08/15.
 */
public class SendObj implements RedisMessage {
    private byte[] asd;
    public SendObj() {
        asd = new byte[1000];
        new Random().nextBytes(asd);
    }

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < 5; i++) {
            s += asd[i] + " ";
        }
        return s;
    }
}
