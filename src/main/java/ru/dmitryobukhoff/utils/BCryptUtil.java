package ru.dmitryobukhoff.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class BCryptUtil {
    public static String hash(String string){
        return BCrypt.withDefaults().hashToString(12, string.toCharArray());
    }

    public static boolean verified(String s1, String s2){
        return BCrypt.verifyer().verify(s1.toCharArray(), s2).verified;
    }
}
