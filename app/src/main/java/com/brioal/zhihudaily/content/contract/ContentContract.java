package com.brioal.zhihudaily.content.contract;

import com.brioal.zhihudaily.entity.ContentDetailEntity;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/25.
 */

public interface ContentContract {
    interface Model {
        //加载详情数据
        void loadDetail(String contentId, OnContentDetailLoadListener loadListener);
    }

    interface View {
        void showRefresh();//显示正在刷新

        void showDetail(ContentDetailEntity entity);//显示数据内容

        void showFailed(String errorMsg);//显示加载失败

        String getId();//当前文章的ID

    }

    interface Presenter {
        void start();

        void refresh();
    }

    public interface OnContentDetailLoadListener {
        void success(ContentDetailEntity detailEntity);//加载成功

        void failed(String errorMsg);//加载失败
    }
}
