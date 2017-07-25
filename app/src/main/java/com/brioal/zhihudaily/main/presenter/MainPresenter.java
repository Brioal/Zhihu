package com.brioal.zhihudaily.main.presenter;

import android.os.Handler;

import com.brioal.zhihudaily.entity.BannerEntity;
import com.brioal.zhihudaily.entity.ContentEntity;
import com.brioal.zhihudaily.main.contract.MainContract;
import com.brioal.zhihudaily.main.model.MainModel;

import java.util.List;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/25.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.Model mModel;
    private MainContract.View mView;
    private Handler mHandler = new Handler();

    public MainPresenter(MainContract.View view) {
        mView = view;
        mModel = new MainModel();
    }

    @Override
    public void start() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showRefreshing();
            }
        });
        refresh();
    }

    @Override
    public void refresh() {
        mModel.loadMainNetData(new MainContract.OnMainDataLoadListener() {
            @Override
            public void success(final List<BannerEntity> banners, final List<ContentEntity> contents) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showBanners(banners);
                        mView.showContents(contents);
                    }
                });
            }

            @Override
            public void failed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showRefreshFailed(errorMsg);
                    }
                });
            }
        });
    }
}
