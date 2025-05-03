package com.fiap.tech.challenge.global.util;

import com.fiap.tech.challenge.global.exception.CryptoException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class CryptoUtil implements PasswordEncoder {

    private final Cipher cipher;
    private final byte[] encryptKey;
    private final KeySpec keySpec;
    private final SecretKeyFactory secretKeyFactory;
    private final SecretKey secretKey;

    public static CryptoUtil newInstance(String key) {
        try {
            if (ValidationUtil.isBlank(key)) {
                throw new CryptoException("Erro na recuperação da chave de criptografia de senha.");
            }
            return new CryptoUtil(key);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException e) {
            throw new CryptoException("Erro ao gerar senha criptografada.");
        } catch (Exception exception) {
            throw new CryptoException("Erro ao alterar senha criptografada.");
        }
    }

    private CryptoUtil(String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        encryptKey = key.getBytes(StandardCharsets.UTF_8);
        cipher = Cipher.getInstance("DESede");
        keySpec = new DESedeKeySpec(encryptKey);
        secretKeyFactory = SecretKeyFactory.getInstance("DESede");
        secretKey = secretKeyFactory.generateSecret(keySpec);
    }

    public String encrypt(String value) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherText = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new CryptoException();
        }
    }

    public String decrypt(String value) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decipherText = cipher.doFinal(Base64.getDecoder().decode(value));
        return new String(decipherText);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return encrypt(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            return rawPassword.toString().equals(decrypt(encodedPassword));
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new CryptoException();
        }
    }
}
