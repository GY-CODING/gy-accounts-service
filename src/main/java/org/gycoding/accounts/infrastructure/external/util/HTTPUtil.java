package org.gycoding.accounts.infrastructure.external.util;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

public class HTTPUtil {
    public static void fetch() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://localhost:8003/achievements/get?id=1")
                .method("GET", body)
                .addHeader("Cookie", "JSESSIONID=434E60F10F4655203DEB9439F7F9FFD7")
                .build();
        Response response = client.newCall(request).execute();
    }
}
