package com.brioal.zhihudaily.main.contract;

import android.content.Context;

import com.brioal.zhihudaily.entity.BannerEntity;
import com.brioal.zhihudaily.entity.ContentEntity;

import java.util.List;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/25.
 */

public interface MainContract {
    interface Model {
        //加载首页数据
        void loadMainNetData(OnMainDataLoadListener loadListener);
    }

    interface View {
        void showRefreshing();//显示正在刷新

        void showBanners(List<BannerEntity> banners);//显示轮播图

        void showContents(List<ContentEntity> contents);//显示文章内容

        void showRefreshFailed(String errorMsg);//显示加载失败

        Context getContext();//返回上下文对象
    }

    interface Presenter {
        void start();//首次进入

        void refresh();//刷新
    }

    public interface OnMainDataLoadListener {
        void success(List<BannerEntity> banners, List<ContentEntity> contents);//加载成功

        void failed(String errorMsg);//加载失败
    }
}
