package main.java.by.chertok.pharmacy.util.md5.impl;

import main.java.by.chertok.pharmacy.util.md5.Encoder;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Encodes text using MD5
 */
public class EncoderMd5 implements Encoder {

    @Override
    public String encode(String text){
        return DigestUtils.md5Hex(text);
    }
}
