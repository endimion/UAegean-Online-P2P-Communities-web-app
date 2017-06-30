/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.ntua.swellrt.utils;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * most of the implementation taken from @author josephg@gmail.com (Joseph Gentle)
 * @author nikos
 */
public class PasswordDigest implements Serializable {

    public static final int DEFAULT_SALT_LENGTH = 16;
    public static final int MINIMUM_SALT_LENGTH = 10;
    public static final String DIGEST_HASHING_ALGORITHM = "SHA-512";

    private final byte[] salt;
    private final byte[] digest;

    // The random number generator is reused, as allocating the RNG each time we
    // need one requires entropy, and is thus quite expensive.
    private static ThreadLocal<SecureRandom> rng = new ThreadLocal<SecureRandom>() {
        @Override
        protected SecureRandom initialValue() {
            return new SecureRandom();
        }
    };

    
    public PasswordDigest(){
        this.salt= new byte[0];
        this.digest=new byte[0];
    }
    /**
     * Create a new password object using the password provided.
     *
     * Callers should clear the bytes in the password array when they're done
     * with it.
     */
    public PasswordDigest(char[] password) {
        salt = generateSalt();
        digest = createPasswordDigest(password, salt);
    }

    private static byte[] createPasswordDigest(char[] password, byte[] salt) {
        MessageDigest hash;
        try {
            hash = MessageDigest.getInstance(DIGEST_HASHING_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "Your java environment doesn't support the expected cryptographic hash function: "
                    + DIGEST_HASHING_ALGORITHM, e);
        }

        hash.update(salt);
        ByteBuffer passwordBytes = Charset.forName("UTF-8").encode(CharBuffer.wrap(password));
        hash.update(passwordBytes);
        return hash.digest();
    }

    private static byte[] generateSalt(int length) {
        byte[] new_salt = new byte[length];
        rng.get().nextBytes(new_salt);
        return new_salt;
    }

    private static byte[] generateSalt() {
        return generateSalt(DEFAULT_SALT_LENGTH);
    }

    /**
     * Get the salt bytes.
     *
     * @return A copy of the password's salt
     */
    public byte[] getSalt() {
        return salt.clone();
    }

    /**
     * Get the password digest.
     *
     * @return A copy of the password's digest
     */
    public byte[] getDigest() {
        return digest.clone();
    }

}
