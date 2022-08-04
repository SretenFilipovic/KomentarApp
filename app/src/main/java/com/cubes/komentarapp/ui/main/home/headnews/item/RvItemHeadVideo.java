package com.cubes.komentarapp.ui.main.home.headnews.item;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.cubes.komentarapp.databinding.RvItemHeadTopBinding;
import com.cubes.komentarapp.data.model.News;
import com.cubes.komentarapp.ui.detail.NewsDetailActivity;
import com.cubes.komentarapp.ui.tools.NewsListener;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsAdapter;
import com.cubes.komentarapp.ui.main.home.headnews.HeadNewsVideoAdapter;

import java.util.ArrayList;

public class RvItemHeadVideo implements RvItemHead{

    private ArrayList<News> videoNews;
    private HeadNewsVideoAdapter adapter;

    public RvItemHeadVideo(ArrayList<News> videoNews) {
        this.videoNews = videoNews;
    }

    @Override
    public int getType() {
        return 5;
    }

    @Override
    public void bind(HeadNewsAdapter.HeadNewsViewHolder holder) {

        RvItemHeadTopBinding binding = (RvItemHeadTopBinding) holder.binding;

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        adapter = new HeadNewsVideoAdapter(videoNews);

        adapter.setNewsListener(new NewsListener() {
            @Override
            public void onNewsClicked(News news) {

                Intent i = new Intent(holder.itemView.getContext(), NewsDetailActivity.class);
                i.putExtra("news",news.id);
                holder.itemView.getContext().startActivity(i);

           }
        });
        binding.recyclerView.setAdapter(adapter);

    }
}