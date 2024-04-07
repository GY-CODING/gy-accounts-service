package org.gycoding.accounts.model.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * Singleton for string encryption. Uses a salt (encrypted value for key encryption randomization) associated with a user to generate a unique key encryption for each user.
 * @author <a href="https://toxyc.dev">Iván Vicente Morales</a>
 */
public class Cipher {
    /**
     * Generates a random salt.
     * @return Salt in byte array format.
     * @author <a href="https://toxyc.dev">Iván Vicente Morales</a>
     */
    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    /**
     * Encrypts a password using a SHA-256 hash function and the generated salt associated with the user.
     * @param password User's password.
     * @param salt User's salt.
     * @return Encrypted password in byte array format.
     * @author <a href="https://toxyc.dev">Iván Vicente Morales</a>
     */
    public static byte[] hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return hashedPassword;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Evaluates the equality of an input password against a user's password, along with its associated salt.
     * @param enteredPassword Input password.
     * @param salt User's salt in byte array format.
     * @param storedHashedPassword User's password in byte array format.
     * @return Evaluation of equality.
     * @author <a href="https://toxyc.dev">Iván Vicente Morales</a>
     */
    public static boolean verifyPassword(String enteredPassword, byte[] salt, byte[] storedHashedPassword) {
        byte[] calculatedHash = hashPassword(enteredPassword, salt);
        return MessageDigest.isEqual(calculatedHash, storedHashedPassword);
    }

    /**
     * Generates a random account number.
     * @return The account number.
     * @author <a href="https://toxyc.dev">Iván Vicente Morales</a>
     */
    public static String generateAccountNumber() {
        final String PREFIX = "ES24";

        String accountNumber = "";

        for (int i = 0; i < 20; i++) {
            accountNumber += (int) (Math.random() * 10);
        }
        
        return PREFIX + accountNumber;
    }
}