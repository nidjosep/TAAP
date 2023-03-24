package com.unb.taap.common;

public class Utils {

  public static String getEmitterKey(String name, String token, String userId) {
    if (userId == null) {
      return null;
    }
    return String.format("%s-%s-%s", name, token.split("\\.")[0], userId);
  }
}
