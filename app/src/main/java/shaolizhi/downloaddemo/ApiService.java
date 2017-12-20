package shaolizhi.downloaddemo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 由邵励治于2017/12/19创造.
 */

public interface ApiService {

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrl(@Url String url);

}
