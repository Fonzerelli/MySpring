package com.nixsolutions.ivashyn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Dmytro Ivashyn
 * Date: 4/3/15
 */
public class HashUtil {

    private static final Logger logger = LoggerFactory.getLogger(HashUtil.class);

    private static MessageDigest messageDigest;


    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Can't create message digest instance", e);
        }
    }

    public static String getEncryptedText(String plainText) {

        messageDigest.reset();
        messageDigest.update(plainText.getBytes(Charset.forName("UTF8")));
        byte[] digest = messageDigest.digest();
        final byte[] resultByte = messageDigest.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashText = bigInt.toString(16);
        while (hashText.length() < 32) {
            hashText = "0" + hashText;
        }

        return hashText;
    }

}