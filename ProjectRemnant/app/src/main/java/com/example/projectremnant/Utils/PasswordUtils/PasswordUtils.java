package com.example.projectremnant.Utils.PasswordUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {

    //TODO: Once I know how this works, I need to comment it.

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public static String getSalt(int _length) {
        StringBuilder returnString = new StringBuilder(_length);
        for (int i = 0; i < _length; i++) {
            returnString.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnString);
    }


    public static byte[] hash(char[] _password, byte[] _salt) {
        PBEKeySpec spec = new PBEKeySpec(_password, _salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(_password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static String generateSecurePassword(String _password, String _salt) {
        String returnValue = null;
        byte[] securePassword = hash(_password.toCharArray(), _salt.getBytes());

        //TODO: NOTE: TO get the getEncoder method i need to be on api 26, maybe i will want to revert to api 21 and find a way to encode passwords for that version.
        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    public static boolean verifyUserPassword(String providedPassword, String securedPassword, String salt) {
        boolean returnValue = false;

        // Generate New secure password with the same salt
        String newSecurePassword = generateSecurePassword(providedPassword, salt);

        // Check if two passwords are equal
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

        return returnValue;
    }



}
