package com.fiap.tech.challenge.global.util;

import com.fiap.tech.challenge.global.exception.CriptoException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class CriptoUtil implements PasswordEncoder {

    private final Cipher cipher;
    private final byte[] encryptKey;
    private final KeySpec keySpec;
    private final SecretKeyFactory secretKeyFactory;
    private final SecretKey secretKey;

    public static CriptoUtil newInstance(String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        return new CriptoUtil(key);
    }

    private CriptoUtil(String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        encryptKey = key.getBytes(StandardCharsets.UTF_8);
        cipher = Cipher.getInstance("DESede");
        keySpec = new DESedeKeySpec(encryptKey);
        secretKeyFactory = SecretKeyFactory.getInstance("DESede");
        secretKey = secretKeyFactory.generateSecret(keySpec);
    }

    public String encrypt(String value) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherText = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String decrypt(String value) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decipherText = cipher.doFinal(Base64.getDecoder().decode(value));
        return new String(decipherText);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            return encrypt(rawPassword.toString());
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            throw new CriptoException();
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            return rawPassword.toString().equals(decrypt(encodedPassword));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new CriptoException();
        }
    }
}
