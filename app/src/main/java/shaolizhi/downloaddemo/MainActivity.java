package shaolizhi.downloaddemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Get().execute();
        new Post().execute();
    }

    private static class Get extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            OkHttpUtils okHttpUtils = new OkHttpUtils();
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
            OkHttpUtils okHttpUtils = new OkHttpUtils();
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
