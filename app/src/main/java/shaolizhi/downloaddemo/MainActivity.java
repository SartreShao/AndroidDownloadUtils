package shaolizhi.downloaddemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Boolean fileFlag;
    Boolean dirFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Get().execute();
        new Post().execute();
        IOUtils.createDirectory();
        try {
            IOUtils.createFile("SHIT");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        String url = "";

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrl(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

//        StringBuilder log = new StringBuilder();
//        String inPath = getInnerSDCardPath();
//        log.append("内置SD卡路径：" + inPath + "\r\n");
//
//        List<String> extPaths = getExtSDCardPath();
//        for (String path : extPaths) {
//            log.append("外置SD卡路径：" + path + "\r\n");
//        }
//        Log.i("FUCK", log.toString());
    }

//    /**
//     * 获取内置SD卡路径
//     * @return
//     */
//    public String getInnerSDCardPath() {
//        return Environment.getExternalStorageDirectory().getPath();
//    }
//
//    /**
//     * 获取外置SD卡路径
//     * @return  应该就一条记录或空
//     */
//    public List<String> getExtSDCardPath()
//    {
//        List<String> lResult = new ArrayList<>();
//        try {
//            Runtime rt = Runtime.getRuntime();
//            Process proc = rt.exec("mount");
//            InputStream is = proc.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String line;
//            while ((line = br.readLine()) != null) {
//                if (line.contains("extSdCard"))
//                {
//                    String [] arr = line.split(" ");
//                    String path = arr[1];
//                    File file = new File(path);
//                    if (file.isDirectory())
//                    {
//                        lResult.add(path);
//                    }
//                }
//            }
//            isr.close();
//        } catch (Exception e) {
//        }
//        return lResult;
//    }

    private static class Get extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
            try {
                String response = okHttpUtils.get("http://111.231.71.150/sunshinebox/home_page/GetIndexData.php");
                Log.i(TAG, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private static class Post extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
            try {
                String response = okHttpUtils.post("http://111.231.71.150/sunshinebox/home_page/GetIndexData.php", "course_type=music");
                Log.i(TAG, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
