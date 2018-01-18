package xu.test.moduledemo.webView;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import xu.test.moduledemo.BaseActivity;
import xu.test.moduledemo.R;
import xu.test.moduledemo.compress.CompressTools;

/**
 * Created by 12852 on 2018/1/8.
 */

public class WebViewActivity extends BaseActivity {

    private Button downloadBtn;
    private Button changeWebViewBtn;

    private WebView baiduWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int setViewId() {
        return R.layout.webview_activity;
    }

    @Override
     protected void initView(){
        baiduWebView = (WebView) findViewById(R.id.baiduWebView);
        downloadBtn = (Button) findViewById(R.id.downloadBtn);
        changeWebViewBtn = (Button) findViewById(R.id.changeWebView);
        downloadBtn.setOnClickListener(new EventClick());
        changeWebViewBtn.setOnClickListener(new EventClick());

        //加载url
        String url = CompressTools.getFilePath("html","index.html");
        String fullUrl = "file://" + url;
        baiduWebView.loadUrl(fullUrl);
        Log.i("webViewTest",fullUrl);
        baiduWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        baiduWebView.getSettings().setJavaScriptEnabled(true);
//        开启 DOM storage API 功能
        baiduWebView.getSettings().setDomStorageEnabled(true);
    }

    private class EventClick extends TotalClickListener{
        @Override
        public void onClick(View v) {
            super.onClick(v);
            switch(v.getId()){
                case R.id.downloadBtn:
                    String path = Environment.getExternalStorageDirectory() + "/AAA/";
//                    DownloadFileService.getInstance(WebViewActivity.this).startDownloadFile("http://211.159.187.229:8080/html.zip", path);
                    String result = ZipUtils.findIndexDir(path);
                    Log.i("findIndex","result=" + result);
                    break;
                case R.id.changeWebView:
                    downloadBtn.setVisibility(View.GONE);
                    changeWebViewBtn.setVisibility(View.GONE);
                    baiduWebView.loadUrl("file://" + Environment.getExternalStorageDirectory() + "/AAA/web/html/index.html");
                    baiduWebView.getSettings().setUseWideViewPort(true);
                    baiduWebView.getSettings().setLoadWithOverviewMode(true);
                    break;
            }
        }
    }

}
