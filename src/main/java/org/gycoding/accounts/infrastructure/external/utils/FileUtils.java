package org.gycoding.accounts.infrastructure.external.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class FileUtils {
    public static MultipartFile download(String imageUrl) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return new MockMultipartFile("file", "image.jpg", "image/jpeg", outputStream.toByteArray());
        } catch(IOException e) {
            log.error("Error downloading image from URL: {}", imageUrl);
            return null;
        } finally {
            assert connection != null;
            connection.disconnect();
        }
    }
}