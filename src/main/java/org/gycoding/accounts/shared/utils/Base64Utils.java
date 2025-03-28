package org.gycoding.accounts.shared.utils;

import java.util.Base64;
import java.security.SecureRandom;

public class Base64Utils {
    private static final int BASE64_KEY_LENGTH = 32;

    public static byte[] decodeFromString(String base64Data) {
        return Base64.getDecoder().decode(base64Data);
    }

    public static String generateApiKey() {
        final var random = new SecureRandom();
        final var keyBytes = new byte[BASE64_KEY_LENGTH];

        random.nextBytes(keyBytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes);
    }
}
