package com.questionnaire.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created with IntelliJ IDEA.
 * User: Alejandro
 * Date: 8/15/12
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class AESTransformer {
    private static final String ALGORITHM = "AES";
    private static final int ITERATIONS = 2;
    private static final byte[] keyValue =
            new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};
    private final static String SALT="SALTY";

    public static String encrypt(String value) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);

        String valueToEnc = null;
        String eValue = value;
        for (int i = 0; i < ITERATIONS; i++) {
            valueToEnc = SALT + eValue;
            byte[] encValue = c.doFinal(valueToEnc.getBytes());
            eValue = new BASE64Encoder().encode(encValue);
        }
        return eValue;
    }

    public static String decrypt(String value) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);

        String dValue = null;
        String valueToDecrypt = value;
        for (int i = 0; i < ITERATIONS; i++) {
            byte[] decordedValue = new BASE64Decoder().decodeBuffer(valueToDecrypt);
            byte[] decValue = c.doFinal(decordedValue);
            dValue = new String(decValue).substring(SALT.length());
            valueToDecrypt = dValue;
        }
        return dValue;
    }
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        return key;
    }

    public static void main(String[] args) throws Exception {
        String password = "mypassword";
        AESTransformer transformer = new AESTransformer();
        String salt = "I think this will be the message's subject";
        String passwordEnc = transformer.encrypt(password);
        String passwordDec = transformer.decrypt(passwordEnc);

        System.out.println("Salt Text : " + SALT);
        System.out.println("Plain Text : " + password);
        System.out.println("Encrypted : " + passwordEnc);
        System.out.println("Decrypted : " + passwordDec);
    }
}
