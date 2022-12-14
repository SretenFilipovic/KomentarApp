package com.cubes.komentarapp.ui.main.home.headnews;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.cubes.komentarapp.R;
import com.cubes.komentarapp.data.model.domain.CategoryBox;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.model.domain.NewsList;
import com.cubes.komentarapp.databinding.RvItemAdviewBinding;
import com.cubes.komentarapp.databinding.RvItemBigNewsBinding;
import com.cubes.komentarapp.databinding.RvItemHeadBigNewsTitleBinding;
import com.cubes.komentarapp.databinding.RvItemHeadMostReadBinding;
import com.cubes.komentarapp.databinding.RvItemHeadSliderBinding;
import com.cubes.komentarapp.databinding.RvItemHeadVideoBinding;
import com.cubes.komentarapp.databinding.RvItemHeadVideoTitleBinding;
import com.cubes.komentarapp.databinding.RvItemSmallNewsBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHead;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadAdView;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadCategoryBigNews;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadCategoryBigNewsTitle;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadCategorySmallNews;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadEditorsChoiceSlider;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadMostRead;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadSlider;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadTop;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadVideo;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadVideoTitle;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;
import com.google.android.gms.ads.AdRequest;

import java.util.ArrayList;

public class HeadNewsAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final ArrayList<RvItemHead> items = new ArrayList<>();

    public HeadNewsAdapter() {
    }

    @SuppressLint("NonConstantResourceId")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ViewBinding binding;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case R.layout.rv_item_head_slider:
                binding = RvItemHeadSliderBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_head_most_read:
                binding = RvItemHeadMostReadBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_big_news:
                binding = RvItemBigNewsBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_head_video:
                binding = RvItemHeadVideoBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_head_video_title:
                binding = RvItemHeadVideoTitleBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_head_big_news_title:
                binding = RvItemHeadBigNewsTitleBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_adview:
                binding = RvItemAdviewBinding.inflate(inflater, parent, false);
                AdRequest adRequest = new AdRequest.Builder().build();
                ((RvItemAdviewBinding) binding).adView.loadAd(adRequest);
                break;
            default:
                binding = RvItemSmallNewsBinding.inflate(inflater, parent, false);
        }

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.items.get(position).bind(holder);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.items.get(position).getType();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setData(NewsList response, NewsListener listener) {

        items.add(new RvItemHeadSlider(response));

        items.add(new RvItemHeadAdView());

        for (News news : response.top) {
            items.add(new RvItemHeadTop(news, response.top, listener));
        }

        items.add(new RvItemHeadMostRead(response));

        items.add(new RvItemHeadAdView());

        for (CategoryBox category : response.category) {
            if (category.title.equalsIgnoreCase("Sport")) {
                items.add(new RvItemHeadCategoryBigNewsTitle(category));
                items.add(new RvItemHeadCategoryBigNews(category.news.get(0), category.news, listener));
                for (int i = 1; i < 5; i++) {
                    items.add(new RvItemHeadCategorySmallNews(category.news.get(i), category.news, listener));
                }
            }
        }

        items.add(new RvItemHeadEditorsChoiceSlider(response));

        items.add(new RvItemHeadAdView());

        items.add(new RvItemHeadVideoTitle());
        for (News news : response.videos) {
            items.add(new RvItemHeadVideo(news, response.videos, listener));
        }

        items.add(new RvItemHeadAdView());

        for (CategoryBox category : response.category) {
            if (!category.title.equalsIgnoreCase("Sport")) {
                items.add(new RvItemHeadCategoryBigNewsTitle(category));
                items.add(new RvItemHeadCategoryBigNews(category.news.get(0), category.news, listener));
                for (int i = 1; i < 5; i++) {
                    items.add(new RvItemHeadCategorySmallNews(category.news.get(i), category.news, listener));
                }
            }
        }
        notifyDataSetChanged();
    }


}
