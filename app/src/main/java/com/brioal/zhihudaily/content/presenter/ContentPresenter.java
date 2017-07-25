package com.brioal.zhihudaily.content.presenter;

import android.os.Handler;

import com.brioal.zhihudaily.content.contract.ContentContract;
import com.brioal.zhihudaily.content.model.ContentModel;
import com.brioal.zhihudaily.entity.ContentDetailEntity;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/25.
 */

public class ContentPresenter implements ContentContract.Presenter {
    private ContentContract.Model mModel;
    private ContentContract.View mView;
    private Handler mHandler = new Handler();

    public ContentPresenter(ContentContract.View view) {
        mView = view;
        mModel = new ContentModel();
    }

    @Override
    public void start() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mView.showRefresh();
            }
        });
        refresh();
    }

    @Override
    public void refresh() {
        mModel.loadDetail(mView.getId(), new ContentContract.OnContentDetailLoadListener() {
            @Override
            public void success(final ContentDetailEntity detailEntity) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showDetail(detailEntity);
                    }
                });
            }

            @Override
            public void failed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.showFailed(errorMsg);
                    }
                });
            }
        });
    }
}
