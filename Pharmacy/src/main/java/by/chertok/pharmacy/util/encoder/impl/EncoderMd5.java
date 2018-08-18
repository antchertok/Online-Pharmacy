package by.chertok.pharmacy.util.encoder.impl;

import by.chertok.pharmacy.util.encoder.Encoder;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Encodes text using MD5
 */
public class EncoderMd5 implements Encoder {
    private static final Encoder INSTANCE = new EncoderMd5();

    public static Encoder getInstance(){
        return INSTANCE;
    }

    @Override
    public String encode(String text) {
        return DigestUtils.md5Hex(text);
    }
}
