package com.brioal.zhihudaily.main.model;

import com.brioal.zhihudaily.entity.BannerEntity;
import com.brioal.zhihudaily.entity.ContentEntity;
import com.brioal.zhihudaily.main.contract.MainContract;
import com.socks.library.KLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class MainModel implements MainContract.Model {
    private String URL_MAIN = "http://news-at.zhihu.com/api/4/news/latest";

    @Override
    public void loadMainNetData(final MainContract.OnMainDataLoadListener loadListener) {
        //加载网络数据
        if (loadListener == null) {
            return;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL_MAIN)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadListener.failed(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                KLog.e("加载完成");
                String content = response.body().string();
                KLog.json(content);
                List<BannerEntity> bannerList = new ArrayList<BannerEntity>();
                List<ContentEntity> contentList = new ArrayList<ContentEntity>();
                try {
                    JSONObject object = new JSONObject(content);
                    //轮播图的Array
                    JSONArray bannerArray = object.getJSONArray("top_stories");
                    for (int i = 0; i < bannerArray.length(); i++) {
                        JSONObject bannerObject = bannerArray.getJSONObject(i);
                        String bannerPicUrl = bannerObject.getString("image");
                        String bannerId = bannerObject.getString("id");
                        String bannerTitle = bannerObject.getString("title");
                        BannerEntity bannerEntity = new BannerEntity(bannerId, bannerPicUrl, bannerTitle);
                        bannerList.add(bannerEntity);
                    }
                    //文章的Array
                    JSONArray contentArray = object.getJSONArray("stories");
                    for (int i = 0; i < contentArray.length(); i++) {
                        JSONObject contentObject = contentArray.getJSONObject(i);
                        String contentTitle = contentObject.getString("title");
                        String contentId = contentObject.getString("id");
                        String contentPicUrl = contentObject.getJSONArray("images").get(0).toString();
                        ContentEntity contentEntity = new ContentEntity(contentId, contentPicUrl, contentTitle);
                        contentList.add(contentEntity);
                    }
                    //解析完成
                    loadListener.success(bannerList, contentList);
                } catch (Exception e) {
                    e.printStackTrace();
                    loadListener.failed(e.getMessage());
                }
            }
        });
    }
}
