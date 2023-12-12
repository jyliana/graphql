package com.example.graphql.util;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class HashUtil {

  private HashUtil() {
  }

  public static boolean isBcryptMatch(String original, String hashValue) {
    return OpenBSDBCrypt.checkPassword(hashValue, original.getBytes(StandardCharsets.UTF_8));
  }

  public static String hashBcrypt(String original) {
    return OpenBSDBCrypt.generate(original.getBytes(StandardCharsets.UTF_8), getSalt(), 5);
  }

  private static byte[] getSalt() {
    byte[] salt = new byte[16];
    new SecureRandom().nextBytes(salt);
    return salt;
  }
}
