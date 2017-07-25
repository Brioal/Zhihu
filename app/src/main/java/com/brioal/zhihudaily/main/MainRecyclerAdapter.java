package com.brioal.zhihudaily.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brioal.bannerview.BannerBean;
import com.brioal.bannerview.BannerView;
import com.brioal.bannerview.DefaultIndexView;
import com.brioal.bannerview.OnBannerClickListener;
import com.brioal.zhihudaily.R;
import com.brioal.zhihudaily.base.BaseViewHolder;
import com.brioal.zhihudaily.content.ContentActivity;
import com.brioal.zhihudaily.entity.BannerEntity;
import com.brioal.zhihudaily.entity.ContentEntity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * email:brioal@foxmail.com
 * github:https://github.com/Brioal
 * Created by Brioal on 2017/7/25.
 */

public class MainRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final int TYPE_BANNER = 0;//Banner类型
    private final int TYPE_CONTENT = 1;//文章类型
    private Context mContext;
    private FragmentManager mFragmentManager;
    private List<BannerEntity> mBanners = new ArrayList<>();
    private List<ContentEntity> mContents = new ArrayList<>();

    public MainRecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void setBanners(List<BannerEntity> banners) {
        mBanners.clear();
        mBanners.addAll(banners);
        notifyDataSetChanged();
    }


    public void setContents(List<ContentEntity> contents) {
        mContents.clear();
        mContents.addAll(contents);
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) {
            return new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_banners, parent, false));
        } else if (viewType == TYPE_CONTENT) {
            return new ContentViewHoder(LayoutInflater.from(mContext).inflate(R.layout.item_content, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindView(position == 0 ? mBanners : mContents.get(position - 1), position);
    }

    @Override
    public int getItemCount() {
        return mContents.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        }
        return TYPE_CONTENT;
    }

    //轮播图ViewHolder
    class BannerViewHolder extends BaseViewHolder<List<BannerEntity>> {
        private BannerView mBannerView;

        public BannerViewHolder(View itemView) {
            super(itemView);
            mBannerView = rootView.findViewById(R.id.item_banner_bannerView);
        }

        @Override
        public void bindView(List<BannerEntity> bannerEntities, int position) {
            if (bannerEntities.size() < 3) {
                return;
            }
            //数据显示道Banner
            List<BannerBean> bannerBeen = new ArrayList<>();
            for (int i = 0; i < bannerEntities.size(); i++) {
                BannerEntity entity = bannerEntities.get(i);
                BannerBean bean = new BannerBean(entity.getPicUrl(), entity.getText(), entity.getId());
                bannerBeen.add(bean);
            }
            DefaultIndexView defaultIndexView = new DefaultIndexView(mContext);
            defaultIndexView.setPointSize(8).setColorNormal(Color.GRAY).setColorSelect(Color.WHITE);
            defaultIndexView.setGravity(Gravity.CENTER);
            mBannerView.initViewPager(bannerBeen, 2000, new OnBannerClickListener() {
                @Override
                public void click(BannerBean bean, int position) {
                    enterContentDetail(bean.getIndex());
                }
            })
                    .initIndex(defaultIndexView)
                    .setGallery(false)
                    .build(mFragmentManager);
            mBannerView.setGravity(Gravity.CENTER);
        }
    }

    //文章内容ViewHolder
    class ContentViewHoder extends BaseViewHolder<ContentEntity> {
        private TextView tvTitle;
        private ImageView ivImage;

        public ContentViewHoder(View itemView) {
            super(itemView);
            tvTitle = rootView.findViewById(R.id.item_content_title);
            ivImage = rootView.findViewById(R.id.item_content_image);
        }

        @Override
        public void bindView(final ContentEntity contentEntity, int position) {
            //显示文字
            tvTitle.setText(contentEntity.getTitle());
            //显示图片
            Glide.with(mContext).load(contentEntity.getPicUrl()).into(ivImage);
            //点击事件
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enterContentDetail(contentEntity.getID());
                }
            });
        }
    }

    /**
     * 跳转到详情界面
     *
     * @param id
     */
    private void enterContentDetail(String id) {
        Intent intent = new Intent(mContext, ContentActivity.class);
        intent.putExtra("id", id);
        mContext.startActivity(intent);
    }
}
