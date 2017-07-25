package com.brioal.zhihudaily;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.brioal.zhihudaily.base.BaseActivity;
import com.brioal.zhihudaily.entity.BannerEntity;
import com.brioal.zhihudaily.entity.ContentEntity;
import com.brioal.zhihudaily.main.MainRecyclerAdapter;
import com.brioal.zhihudaily.main.contract.MainContract;
import com.brioal.zhihudaily.main.presenter.MainPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainContract.View {
    @BindView(R.id.main_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_swipeRefreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    private MainContract.Presenter mPresenter;
    private Handler mHandler = new Handler();
    private MainRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initPresenter();
    }

    private void initView() {
        //下拉样式
        mRefreshLayout.setColorSchemeColors(Color.GREEN, Color.BLUE, Color.RED);
        //下拉刷新
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refresh();
            }
        });
        //初始化Adapter
        mAdapter = new MainRecyclerAdapter(mContext);
        mAdapter.setFragmentManager(getSupportFragmentManager());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initPresenter() {
        mPresenter = new MainPresenter(this);
        mPresenter.start();
    }

    @Override
    public void showRefreshing() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void showBanners(List<BannerEntity> banners) {
        mRefreshLayout.setRefreshing(false);
        mAdapter.setBanners(banners);
    }

    @Override
    public void showContents(List<ContentEntity> contents) {
        mRefreshLayout.setRefreshing(false);
        mAdapter.setContents(contents);
    }

    @Override
    public void showRefreshFailed(String errorMsg) {
        mRefreshLayout.setRefreshing(false);
        showToast(errorMsg);
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
