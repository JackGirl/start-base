package cn.ulyer.common.oauth;

import org.springframework.core.serializer.support.SerializationFailedException;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class OauthJdkSerialize extends JdkSerializationStrategy {


    @Override
    protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
        if (bytes != null && bytes.length != 0) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
                return (T) objectInputStream.readObject();
            } catch (Exception var4) {
                throw new SerializationFailedException("Failed to deserialize payload", var4);
            }
        } else {
            return null;
        }
    }
}
