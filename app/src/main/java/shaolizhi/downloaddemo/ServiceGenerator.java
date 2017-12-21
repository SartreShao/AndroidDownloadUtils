package shaolizhi.downloaddemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 由邵励治于2017/12/19创造.
 */

class ServiceGenerator {

    private static final String BASE_URL = "http://www.baidu.com";

    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .callbackExecutor(executorService)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
