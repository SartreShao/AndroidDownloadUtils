package shaolizhi.downloaddemo;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 由邵励治于2017/12/14创造.
 */

class OkHttpUtils {
    private OkHttpClient client = new OkHttpClient();

    //若要提交表单请求请用此MediaType
    private static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //若要提交JSON的话请用此MediaType
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    String post(String url, String content) throws IOException {
        RequestBody body = RequestBody.create(FORM, content);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
