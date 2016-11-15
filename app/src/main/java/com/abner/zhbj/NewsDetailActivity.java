package com.abner.zhbj;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.abner.zhbj.utils.ConstantValue;
import com.abner.zhbj.utils.SpUtils;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView ivback;
    private ImageView ivtextsize;
    private ImageView ivshare;
    private ImageButton ibmenu;
    private LinearLayout lltitle;
    private WebView wvpager;
    private ProgressBar pbreadbar;
    private int mTextSizeChoose;
    private WebSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        ShareSDK.initSDK(this, "");
        initView();
    }

    private void initView() {
        ivback = (ImageView) findViewById(R.id.iv_back);
        ivtextsize = (ImageView) findViewById(R.id.iv_text_size);
        ivshare = (ImageView) findViewById(R.id.iv_share);
        ibmenu = (ImageButton) findViewById(R.id.ib_menu);
        lltitle = (LinearLayout) findViewById(R.id.ll_title);
        wvpager = (WebView) findViewById(R.id.wv_news_detail);
        pbreadbar = (ProgressBar) findViewById(R.id.pb_read_bar);

        ibmenu.setVisibility(View.GONE);
        ivback.setVisibility(View.VISIBLE);
        lltitle.setVisibility(View.VISIBLE);

        String url = getIntent().getStringExtra("url");
        Log.i("-----: ", url);

        wvpager.loadUrl(url);
        WebSettings settings = wvpager.getSettings();
        settings.setJavaScriptEnabled(true);

        wvpager.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pbreadbar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pbreadbar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl("url");
                return true;
            }
        });
        //   wvpager.goBack();
        //   wvpager.goForward();


        wvpager.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pbreadbar.setProgress(newProgress);
            }
        });

        ivback.setOnClickListener(this);
        ivshare.setOnClickListener(this);
        ivtextsize.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                break;
            case R.id.iv_share:
                showShare();
                break;
            case R.id.iv_text_size:
                showTextChooseDialog();
                break;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    private void showTextChooseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("字体设置");
        String[] items = {"超大号字体", "大号字体", "正常字体", "小号字体", "超小号字体"};
        int currentTextSize = SpUtils.getInt(getApplicationContext(), ConstantValue.VP_TEXT_SISE, 2);
        builder.setSingleChoiceItems(items, currentTextSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTextSizeChoose = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mSettings = wvpager.getSettings();
                SpUtils.setInt(getApplicationContext(), ConstantValue.VP_TEXT_SISE, mTextSizeChoose);
                switch (mTextSizeChoose) {
                    case 0:
                        mSettings.setTextZoom(180);
                        break;
                    case 1:
                        mSettings.setTextZoom(140);
                        break;
                    case 2:
                        mSettings.setTextZoom(100);
                        break;
                    case 3:
                        mSettings.setTextZoom(60);
                        break;
                    case 4:
                        mSettings.setTextZoom(20);
                        break;
                }
            }
        });
        builder.setNegativeButton("取消", null);

        builder.show();
    }
}
