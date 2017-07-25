package com.brioal.zhihudaily.content;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.zhihudaily.R;
import com.brioal.zhihudaily.base.BaseActivity;
import com.brioal.zhihudaily.content.contract.ContentContract;
import com.brioal.zhihudaily.content.presenter.ContentPresenter;
import com.brioal.zhihudaily.entity.ContentDetailEntity;
import com.bumptech.glide.Glide;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentActivity extends BaseActivity implements ContentContract.View {
    @BindView(R.id.detail_toolBarBg)
    ImageView mBarBg;
    @BindView(R.id.detail_toolBar)
    Toolbar mToolBar;
    @BindView(R.id.detail_toolBarLayout)
    CollapsingToolbarLayout mToolBarLayout;
    @BindView(R.id.detail_appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.detail_webView)
    WebView mWebView;
//    @BindView(R.id.detail_refreshLayout)
//    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.detail_image_resource)
    TextView mTvResource;
    private ContentContract.Presenter mPresenter;
    private String mId = "";
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_content);
        ButterKnife.bind(this);
        initData();
        initPresenter();
        initView();
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initData() {
        mId = getIntent().getStringExtra("id");
        KLog.e("ID:" + mId);
    }

    private void initView() {
        //设置标题栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Webview设置
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void initPresenter() {
        mPresenter = new ContentPresenter(this);
        mPresenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            CollapsingToolbarLayout collapsing_toolbar_layout = (CollapsingToolbarLayout) findViewById(R.id.detail_toolBarLayout);
            collapsing_toolbar_layout.setExpandedTitleTextColor(ColorStateList.valueOf(Color.TRANSPARENT));
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void showRefresh() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void showDetail(ContentDetailEntity entity) {
        //显示标题
        mToolBarLayout.setTitle(entity.getTitle());
        //显示图片
        Glide.with(mContext).load(entity.getCover()).error(R.mipmap.ic_temp_pic).into(mBarBg);
        //版权信息显示
        mTvResource.setText(entity.getImageResource());
        //将内容显示到网页上面
        String content = entity.getContent();
        mWebView.loadDataWithBaseURL(null, content, "text/html", "utf8", null);
//        mWebView.loadUrl("http://www.baidu.com");
        KLog.e(content);
    }

    @Override
    public void showFailed(String errorMsg) {
//        mRefreshLayout.setRefreshing(false);
        showToast(errorMsg);
    }

    @Override
    public String getId() {
        return mId;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
