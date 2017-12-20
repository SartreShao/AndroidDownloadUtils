package shaolizhi.downloaddemo;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * 由邵励治于2017/12/18创造.
 */

class IOUtils {

    private static final String FILE_NAME = Environment.getExternalStorageDirectory().getPath();

    private static File directory = new File(FILE_NAME, "FUCK");

    static Boolean createDirectory() {
        return directory.exists() || directory.mkdirs();
    }

    static Boolean createFile(String fileName) throws IOException {
        File file = new File(directory, fileName);
        if (!file.exists()) {
            return file.createNewFile();
        } else {
            return true;
        }
    }

}
