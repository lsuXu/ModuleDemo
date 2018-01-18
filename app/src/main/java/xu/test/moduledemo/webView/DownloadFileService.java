package xu.test.moduledemo.webView;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 *  下载文件（只下载保存）
 */
public class DownloadFileService {
    private String TAG = "DownloadFileService";
    private Context mContext;
    private volatile boolean mDownloading;
    private static DownloadFileService mInstance;
    private DownloadManager dm;
    private ArrayList<Long> enqueueList = new ArrayList<Long>();
    private Subscription mIntervalSubscription;

    private DownloadFileService(Context context) {
        this.mContext = context.getApplicationContext();
        registerReceiver();
    }

    public static DownloadFileService getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DownloadFileService(context);
        }
        return mInstance;
    }

    private void registerReceiver(){
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                for(int i=0;i<enqueueList.size();i++){
                    if(enqueueList.get(i) == reference){
                        Log.i(TAG, "download success " + reference);
                        enqueueList.remove(i);
                        if(enqueueList.size() == 0){
                            mDownloading = false;
                            if (mIntervalSubscription != null && !mIntervalSubscription.isUnsubscribed()) {
                                mIntervalSubscription.unsubscribe();
                            }
                        }
                        break;
                    }
                }
            }
        };
        mContext.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void startDownloadFile(String url, String filePath) {
        File ApkFile = new File(filePath);
        if (ApkFile.exists()) {
            return;
        }
        dm = (DownloadManager) mContext.getSystemService(mContext.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        /**设置通知栏是否可见*/
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setMimeType("application/vnd.android.package-archive");
        File file = new File(filePath);
        request.setDestinationUri(Uri.fromFile(file));
        try {
            enqueueList.add(dm.enqueue(request));

            mDownloading = true;

            if (mIntervalSubscription == null || mIntervalSubscription.isUnsubscribed()) {
                mIntervalSubscription = Observable.interval(10, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        for (int i = 0; i < enqueueList.size(); i++) {
                            Log.i(TAG, "Download Progress -->" + getDownloadStatus(enqueueList.get(i)));
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    }
                });
            }
        }catch (Exception e){
            Log.i("click",e.getMessage());
        }
    }

    private int getDownloadStatus(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = dm.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    int bytes_downloaded = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    int progress = (int) ((bytes_downloaded * 100) / bytes_total);
                    return progress;

                }
            } finally {
                c.close();
            }
        }
        return -1;
    }
}
