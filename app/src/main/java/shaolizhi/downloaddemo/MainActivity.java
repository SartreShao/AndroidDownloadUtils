package shaolizhi.downloaddemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ApiService apiService;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IOUtils.createDirectory();
        try {
            IOUtils.createFile("SHIT");
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Get().execute();
        new Post().execute();
        url = "http://sunshinebox-1255613827.file.myqcloud.com/%E5%84%BF%E6%AD%8C/%E5%B0%8F%E8%9D%8C%E8%9A%AA/01%E5%B0%8F%E8%9D%8C%E8%9A%AA.mp4";

        apiService = ServiceGenerator.createService(ApiService.class);

        downloadFile.execute();

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        };
//
//        Thread thread = new Thread(runnable);
//        thread.start();
    }

    private static boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "fuck.mp4");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    Log.e(TAG, "read:" + String.valueOf(read));
                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

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

    private final DownloadFile downloadFile = new DownloadFile(this);

    private static class DownloadFile extends AsyncTask<Void, Long, Void> {

        private WeakReference<MainActivity> mainActivityWeakReference;

        DownloadFile(MainActivity activity) {
            this.mainActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MainActivity mainActivity = mainActivityWeakReference.get();
            if (mainActivity != null) {
                Call<ResponseBody> call = mainActivity.apiService.downloadFileWithDynamicUrl(mainActivity.url);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.e(TAG, "server contacted and has file");

                            boolean writtenToDisk = writeResponseBodyToDisk(response.body());

                            Log.d(TAG, "file download was a success?" + writtenToDisk);
                        } else {
                            Log.d(TAG, "server contact failed");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                    }
                });
            }
            return null;
        }
    }
}
