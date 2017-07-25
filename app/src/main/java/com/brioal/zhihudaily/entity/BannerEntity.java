package com.brioal.zhihudaily.entity;

/**
 * 轮播图数据实体
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/25.
 */

public class BannerEntity {
    private String mId;//文章的唯一标识符
    private String mPicUrl;//图片地址
    private String mText;//文字内容

    public BannerEntity() {
    }

    public BannerEntity(String id, String picUrl, String text) {
        mId = id;
        mPicUrl = picUrl;
        mText = text;
    }

    public String getId() {
        return mId;
    }

    public BannerEntity setId(String id) {
        mId = id;
        return this;
    }

    public String getPicUrl() {
        return mPicUrl;
    }

    public BannerEntity setPicUrl(String picUrl) {
        mPicUrl = picUrl;
        return this;
    }

    public String getText() {
        return mText;
    }

    public BannerEntity setText(String text) {
        mText = text;
        return this;
    }
}
