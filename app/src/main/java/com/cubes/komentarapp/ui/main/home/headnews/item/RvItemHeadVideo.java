package com.cubes.komentarapp.ui.main.home.headnews.item;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.databinding.RvItemHeadTopBinding;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsVideoAdapter;
import com.cubes.komentarapp.ui.tools.NewsListener;

import java.util.ArrayList;

public class RvItemHeadVideo implements RvItemHead {

    private final ArrayList<News> videoNews;
    private final NewsListener listener;

    public RvItemHeadVideo(ArrayList<News> videoNews, NewsListener listener) {
        this.videoNews = videoNews;
        this.listener = listener;
    }

    @Override
    public int getType() {
        return 5;
    }

    @Override
    public void bind(HeadNewsAdapter.HeadNewsViewHolder holder) {

        RvItemHeadTopBinding binding = (RvItemHeadTopBinding) holder.binding;

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        HeadNewsVideoAdapter adapter = new HeadNewsVideoAdapter();
        binding.recyclerView.setAdapter(adapter);

        adapter.setVideoData(videoNews);
        adapter.setNewsListener(listener);

    }
}