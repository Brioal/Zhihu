package com.brioal.zhihudaily.entity;

/**文章详情实体
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/25.
 */

public class ContentDetailEntity {
    private String mTitle;//文章标题
    private String mCover;//文章封面
    private String mShareUrl;//文章分享的地址
    private String mImageResource;//图片源
    private String mContent;//网页代码

    public ContentDetailEntity() {
    }

    public String getContent() {
        return mContent;
    }

    public ContentDetailEntity setContent(String content) {
        mContent = content;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public ContentDetailEntity setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getCover() {
        return mCover;
    }

    public ContentDetailEntity setCover(String cover) {
        mCover = cover;
        return this;
    }

    public String getShareUrl() {
        return mShareUrl;
    }

    public ContentDetailEntity setShareUrl(String shareUrl) {
        mShareUrl = shareUrl;
        return this;
    }

    public String getImageResource() {
        return mImageResource;
    }

    public ContentDetailEntity setImageResource(String imageResource) {
        mImageResource = imageResource;
        return this;
    }
}
