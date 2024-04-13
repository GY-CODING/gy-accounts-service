package org.gycoding.accountsv2.infrastructure.adapter.cipher;

import org.springframework.stereotype.Service;

/**
 * Convierte un array de bytes a una cadena hexadecimal y viceversa.
 * @author <a href="https://toxyc.dev">Iván Vicente Morales</a>
 */
@Service
public class ByteHexConverter {
    /**
     * Convierte un array de bytes a una cadena hexadecimal.
     * @param bytes Array de bytes.
     * @return Cadena hexadecimal.
     * @author <a href="https://toxyc.dev">Iván Vicente Morales</a>
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }

    /**
     * Convierte una cadena hexadecimal en un array de bytes.
     * @param hex Cadena hexadecimal.
     * @return Array de bytes.
     * @author <a href="https://toxyc.dev">Iván Vicente Morales</a>
     */
    public static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] result = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            int byteValue = (Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16);
            result[i / 2] = (byte) byteValue;
        }

        return result;
    }
}