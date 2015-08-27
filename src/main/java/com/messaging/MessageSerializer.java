package com.messaging;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.*;

public class MessageSerializer  {

    private static MessageSerializerImpl serializer = null;
    
    private MessageSerializer() {
        serializer = new MessageSerializerImpl();
    }

    public static byte[] serialize(Object object) {
        if(serializer == null) new MessageSerializer();
        return serializer.serialize(object);
    }

    public static Object deserialize(byte[] objectBytes) {
        if(serializer == null) new MessageSerializer();
        return serializer.deserialize(objectBytes);
    }

    private class MessageSerializerImpl implements RedisSerializer<Object> {
        @Override
        public byte[] serialize(Object object) throws SerializationException {
            try {
                ByteArrayOutputStream boas = new ByteArrayOutputStream();
                ObjectOutputStream ous = new ObjectOutputStream(boas);
                ous.writeObject(object);
                byte[] bytes = boas.toByteArray();
                ous.close();
                return bytes;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        public Object deserialize(byte[] bytes) throws SerializationException {
            try {
                ByteArrayInputStream boas = new ByteArrayInputStream(bytes);
                ObjectInputStream ous = new ObjectInputStream(boas);
                Object object = ous.readObject();
                ous.close();
                return object;
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;

        }
    }
}
