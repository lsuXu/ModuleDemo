package xu.test.moduledemo.webView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import xu.test.moduledemo.R;
import xu.test.moduledemo.compress.CompressTools;

/**
 * Created by 12852 on 2018/1/8.
 */

public class WebViewActivity extends AppCompatActivity {

    private WebView baiduWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        baiduWebView = (WebView) findViewById(R.id.baiduWebView);
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
}
