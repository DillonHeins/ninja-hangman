package services;

import com.google.inject.Inject;
import dao.HangmanUserDao;
import models.HangmanUser;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by dillon on 2017/01/19.
 * AuthenticationService - provides register, login, password hashing and salting services
 */
public class AuthenticationService {
    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    @Inject
    private HangmanUserDao hangmanUserDao;

    private static byte[] getNextSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    private static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    private static boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
        byte[] pwdHash = hash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++) {
            if (pwdHash[i] != expectedHash[i]) return false;
        }
        return true;
    }

    public boolean login(String username, String password) {
        HangmanUser hangmanUser = hangmanUserDao.getUserByUsername(username);

        return isExpectedPassword(password.toCharArray(), hangmanUser.getSalt(), hangmanUser.getPassword());
    }

    public boolean register(String username, String password) {
        byte[] salt = getNextSalt();
        byte[] hashedPassword = hash(password.toCharArray(), salt);

        return hangmanUserDao.registerUser(username, hashedPassword, salt);
    }
}
