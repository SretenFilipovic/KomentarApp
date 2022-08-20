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
import com.cubes.komentarapp.databinding.RvItemBigNewsHomepageBinding;
import com.cubes.komentarapp.databinding.RvItemHeadMostReadBinding;
import com.cubes.komentarapp.databinding.RvItemHeadSliderBinding;
import com.cubes.komentarapp.databinding.RvItemHeadVideoBinding;
import com.cubes.komentarapp.databinding.RvItemHeadVideoTitleBinding;
import com.cubes.komentarapp.databinding.RvItemSmallNewsBinding;
import com.cubes.komentarapp.databinding.RvItemSmallNewsHomepageBinding;
import com.cubes.komentarapp.ui.ViewHolder.ViewHolder;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHead;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadCategoryBigNews;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadCategorySmallNews;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadEditorsChoiceSlider;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadMostRead;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadSlider;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadTop;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadVideo;
import com.cubes.komentarapp.ui.main.home.headnews.item.RvItemHeadVideoTitle;
import com.cubes.komentarapp.ui.tools.listeners.NewsListener;

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
            case R.layout.rv_item_big_news_homepage:
                binding = RvItemBigNewsHomepageBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_small_news_homepage:
                binding = RvItemSmallNewsHomepageBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_head_video:
                binding = RvItemHeadVideoBinding.inflate(inflater, parent, false);
                break;
            case R.layout.rv_item_head_video_title:
                binding = RvItemHeadVideoTitleBinding.inflate(inflater, parent, false);
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


    public void setData(NewsList response, NewsListener listener) {

        items.add(new RvItemHeadSlider(response.slider));

        for (News news : response.top){
            items.add(new RvItemHeadTop(news, listener));
        }

        items.add(new RvItemHeadMostRead(response.latest, response.most_read, response.most_comented));

        for (CategoryBox category : response.category) {
            if (category.title.equalsIgnoreCase("Sport")) {
                items.add(new RvItemHeadCategoryBigNews(category.news.get(0), listener, category));
                for (int i = 1; i < 5; i++) {
                    items.add(new RvItemHeadCategorySmallNews(category.news.get(i),listener));
                }
            }
        }

        items.add(new RvItemHeadEditorsChoiceSlider(response.editors_choice));

        items.add(new RvItemHeadVideoTitle());
        for (News news : response.videos){
            items.add(new RvItemHeadVideo(news, listener));
        }

        for (CategoryBox category : response.category) {
            if (!category.title.equalsIgnoreCase("Sport")) {
                items.add(new RvItemHeadCategoryBigNews(category.news.get(0), listener, category));
                for (int i = 1; i < 5; i++) {
                    items.add(new RvItemHeadCategorySmallNews(category.news.get(i),listener));
                }
            }
        }
        notifyDataSetChanged();
    }

}
