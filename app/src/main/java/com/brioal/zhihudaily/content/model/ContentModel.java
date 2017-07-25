package com.brioal.zhihudaily.content.model;

import com.brioal.zhihudaily.content.contract.ContentContract;
import com.brioal.zhihudaily.entity.ContentDetailEntity;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/25.
 */

public class ContentModel implements ContentContract.Model {
    private String URL_DETAIL = "http://news-at.zhihu.com/api/4/news/%s";

    @Override
    public void loadDetail(String contentId, final ContentContract.OnContentDetailLoadListener loadListener) {
        if (contentId == null || contentId.isEmpty() || loadListener == null) {
            return;
        }
        try {
            String realUrl = String.format(URL_DETAIL, contentId);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(realUrl)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    loadListener.failed(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String content = response.body().string();
                        JSONObject contentObject = new JSONObject(content);
                        String title = contentObject.getString("title");
                        String image = contentObject.getString("image");
                        String image_source = contentObject.getString("image_source");
                        String share_url = contentObject.getString("share_url");
                        String body = contentObject.getString("body");
                        ContentDetailEntity entity = new ContentDetailEntity();
                        entity.setTitle(title).setImageResource(image_source).setContent(body).setCover(image).setShareUrl(share_url);
                        loadListener.success(entity);
                    } catch (Exception e) {
                        e.printStackTrace();
                        loadListener.failed(e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            loadListener.failed(e.getMessage());
        }
    }
}
