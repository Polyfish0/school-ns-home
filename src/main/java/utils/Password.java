package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
    /**
     * This is a helper function to hash a password
     * @param password The password that has to be hashed
     * @return The hashed password
     */
    public static String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte b : md.digest()) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A helper function to check if a password is correct
     * @param password The entered password
     * @param expectedHash The expected password Hash
     * @return True => Password is correct, False => Password is incorrect
     */
    public static boolean checkPassword(String password, String expectedHash) {
        return hash(password).equals(expectedHash);
    }
}
