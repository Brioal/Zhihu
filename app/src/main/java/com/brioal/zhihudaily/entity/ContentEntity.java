package com.brioal.zhihudaily.entity;

/**
 * 文章数据实体
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/25.
 */

public class ContentEntity {
    private String mID;//文章唯一标识符
    private String mPicUrl;//图片地址
    private String mTitle;//文章标题

    public ContentEntity() {
    }

    public ContentEntity(String ID, String picUrl, String title) {
        mID = ID;
        mPicUrl = picUrl;
        mTitle = title;
    }


    public String getID() {
        return mID;
    }

    public ContentEntity setID(String ID) {
        mID = ID;
        return this;
    }

    public String getPicUrl() {
        return mPicUrl;
    }

    public ContentEntity setPicUrl(String picUrl) {
        mPicUrl = picUrl;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public ContentEntity setTitle(String title) {
        mTitle = title;
        return this;
    }
}
