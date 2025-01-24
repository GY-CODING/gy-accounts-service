package org.gycoding.accounts.shared.utils;

import java.util.Base64;

public class Base64Utils {
    public static byte[] decodeFromString(String base64Data) {
        return Base64.getDecoder().decode(base64Data);
    }
}
