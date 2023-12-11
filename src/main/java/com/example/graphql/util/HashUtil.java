package com.example.graphql.util;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

import java.nio.charset.StandardCharsets;

public class HashUtil {

  private HashUtil() {
  }

  public static boolean isBcryptMatch(String original, String hashValue) {
    return OpenBSDBCrypt.checkPassword(hashValue, original.getBytes(StandardCharsets.UTF_8));
  }
}
