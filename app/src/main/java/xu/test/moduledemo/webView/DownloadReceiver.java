package xu.test.moduledemo.webView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

/**
 * Created by 12852 on 2018/1/12.
 */

public class DownloadReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("click","下载完成");
        String zipPath = Environment.getExternalStorageDirectory() + "/AAA/web.zip";
        String targetPath = Environment.getExternalStorageDirectory() + "/AAA/web";
        try {
            ZipUtils.UnZipFolder(zipPath,targetPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("click","解压完成");
    }
}
