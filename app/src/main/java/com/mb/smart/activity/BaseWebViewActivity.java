package com.mb.smart.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mb.smart.R;


/**
 * webview基类
 * @author cgy
 *
 */
public class BaseWebViewActivity extends BaseActivity{
	/**
	 * WebView
	 */
	private WebView mWebView;
//	private TextView mTvTitle;
	private String mTitle = "详情页";
	private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
        setContentView(R.layout.common_web_detail);
        initView();
    }


	protected void initView() {
//        mTvTitle = findViewById(R.id.tv_title);
//        mTvTitle.setText(mTitle==null?"详情页":mTitle);
        mWebView = findViewById(R.id.web_detail);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptEnabled(true);//允许使用js
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //点击链接由自己处理，而不是新开Android的系统browser中响应该链接。
        mWebView.setWebViewClient(webViewClient);
        mWebView.loadUrl(mUrl);
//        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
	}

	@Override
	public void onBackPressed() {
		if (mWebView.canGoBack()) {
			mWebView.goBack();
		}else {
			finish();
		}
	}



    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            if (!TextUtils.isEmpty(view.getTitle())){
//                mTvTitle.setText(view.getTitle()==null?"详情页":view.getTitle());
//            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            mWebView.loadUrl("about:blank");
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return TextUtils.isEmpty(url) || super.shouldOverrideUrlLoading(mWebView, url);
        }
    };
}
